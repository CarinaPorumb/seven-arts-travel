package ro.sevenartstravel.service.entity.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.sevenartstravel.dto.UserCreateDTO;
import ro.sevenartstravel.dto.UserDTO;
import ro.sevenartstravel.dto.UserUpdateDTO;
import ro.sevenartstravel.entity.Role;
import ro.sevenartstravel.entity.User;
import ro.sevenartstravel.exception.NotFoundException;
import ro.sevenartstravel.mapper.UserMapper;
import ro.sevenartstravel.repository.RoleRepository;
import ro.sevenartstravel.repository.UserRepository;
import ro.sevenartstravel.service.crud.CrudServiceImpl;
import ro.sevenartstravel.service.email.EmailBodyService;
import ro.sevenartstravel.service.email.EmailSender;
import ro.sevenartstravel.service.entity.UserService;
import ro.sevenartstravel.service.util.SpecificationUtils;

import java.util.*;

@Slf4j
@Service
public class UserServiceImpl extends CrudServiceImpl<UserDTO, User, UUID> implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final EmailBodyService emailBodyService;
    private final EmailSender emailSender;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, EmailBodyService emailBodyService, EmailSender emailSender, UserMapper userMapper, BCryptPasswordEncoder passwordEncoder) {
        super(userRepository, userMapper);
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.emailBodyService = emailBodyService;
        this.emailSender = emailSender;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    private Specification<User> buildSpecification(String username, String fullName, String email) {
        return Specification.where(SpecificationUtils.<User>attributeLike("username", username))
                .and(SpecificationUtils.attributeLike("fullName", fullName))
                .and(SpecificationUtils.attributeLike("email", email));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserDTO> getAllUsers(String username, String fullName, String email, Pageable pageable) {
        Specification<User> specification = buildSpecification(username, fullName, email);
        log.debug("Listing user with filters - Username: {}, FullName: {}, Email {} ", username, fullName, email);
        return userRepository.findAll(specification, pageable)
                .map(userMapper::toDTO);
    }

    @Override
    @Transactional
    public UserDTO saveUser(UserCreateDTO receivedUser) {
        if (!receivedUser.getPassword().equals(receivedUser.getPasswordConfirm())) {
            throw new IllegalArgumentException("Password and password confirmation must be the same");
        }

        User user = userMapper.toEntity(receivedUser);
        user.setPassword(passwordEncoder.encode(receivedUser.getPassword()));
        user.setRandomToken(UUID.randomUUID().toString());
        user.setEnabled(false);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);

        Role userRole = roleRepository.findByName("ROLE_USER");
        if (userRole == null) {
            userRole = new Role();
            userRole.setName("ROLE_USER");
            userRole = roleRepository.save(userRole);
        }
        user.setRoles(Set.of(userRole));
        userRepository.save(user);
        emailSender.sendEmail(user.getEmail(), "Activate your Account", emailBodyService.emailBody(user));

        return userMapper.toDTO(user);
    }

    @Transactional
    @Override
    public void activateUser(String token) {
        User user = userRepository.findByRandomToken(token)
                .orElseThrow(() -> new NotFoundException("User", token));
        if (user.isEnabled()) {
            log.warn("User {} is already activated", user.getEmail());
        } else {
            user.setEnabled(true);
            user.setRandomToken(null); // opțional
            userRepository.save(user);
            log.info("User {} successfully activated", user.getEmail());
        }
    }


    @Override
    @Transactional
    public void updateUserByAdmin(UserUpdateDTO userDTO) {
        User existingUser = userRepository.findById(userDTO.getId())
                .orElseThrow(() -> new NotFoundException("User", userDTO.getId()));
        userMapper.updateEntity(existingUser, userDTO);

        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }

        // Refresh Security Context if user is logged in and updates themselves
        List<GrantedAuthority> actualAuthorities = convertRolesToAuthorities(existingUser.getRoles());
        Authentication newAuth = new UsernamePasswordAuthenticationToken(
                existingUser.getUsername(),
                existingUser.getPassword(),
                actualAuthorities
        );
        SecurityContextHolder.getContext().setAuthentication(newAuth);
        userRepository.save(existingUser);
    }

    @Override
    @Transactional
    public void updateProfile(UserUpdateDTO dto) {
        User user = userRepository.findById(dto.getId())
                .orElseThrow(() -> new NotFoundException("User", dto.getId()));
        userMapper.updateEntity(user, dto);
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserDTO> findUserByUsername(String username) {
        log.debug("Fetching user by username {}", username);
        return userRepository.findByUsernameIgnoreCase(username)
                .map(userMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findUserEntityByUsername(String username) {
        return userRepository.findByUsernameIgnoreCase(username);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO findUserByRandomToken(String randomToken) throws NotFoundException {
        log.debug("Fetching user by random token {}", randomToken);
        return userRepository.findByRandomToken(randomToken)
                .map(userMapper::toDTO)
                .orElseThrow(() -> new NotFoundException("User", randomToken));
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO findByEmail(String emailConfirm) {
        log.debug("Fetching user by email {}", emailConfirm);
        return userRepository.findByEmail(emailConfirm)
                .map(userMapper::toDTO)
                .orElseThrow(() -> new NotFoundException("User", emailConfirm));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserDTO> searchUser(String keyword, Pageable pageable) {
        log.debug("Searching user with keyword {}", keyword);
        return userRepository.searchUser(keyword, pageable)
                .map(userMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByUsernameOrEmail(String username, String email) {
        return userRepository.existsByUsernameIgnoreCase(username) || userRepository.existsByEmailIgnoreCase(email);
    }

    @Override
    @Transactional
    public void deleteUser(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User", id));
        userRepository.delete(user);
        log.info("Deleted user with id {}", id);
    }

    @Override
    @Transactional
    public void addAdminRole(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User", userId));

        Role adminRole = roleRepository.findByName("ROLE_ADMIN");

        if (adminRole == null) {
            adminRole = roleRepository.save(new Role("ROLE_ADMIN"));
            log.info("Created new role ROLE_ADMIN");
        }

        if (user.getRoles().add(adminRole)) {
            userRepository.save(user);
            log.info("Added ADMIN role to user {}", user.getUsername());
        } else {
            log.warn("User {} already has ADMIN role", user.getUsername());
        }
    }

    @Override
    @Transactional
    public void removeAdminRole(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User", userId));

        Role adminRole = roleRepository.findByName("ROLE_ADMIN");

        if (adminRole == null) {
            log.warn("Role ROLE_ADMIN does not exist in database.");
        }
        if (user.getRoles().remove(adminRole)) {
            userRepository.save(user);
            log.info("Removed ADMIN role from user {}", user.getUsername());
        } else {
            log.warn("User {} does not have ADMIN role", user.getUsername());
        }
    }

    @Override
    @Transactional
    public void addRoleToUser(UUID userId, String roleName) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User", userId));

        Role role = roleRepository.findByName(roleName);

        if (role == null) {
            throw new NotFoundException("Role " + roleName + " does not exist.");
        }

        if (user.getRoles().add(role)) {
            userRepository.save(user);
            log.info("Added role {} to user {}", roleName, user.getUsername());
        } else {
            log.warn("User {} already has role {}", user.getUsername(), roleName);
        }
    }

    private List<GrantedAuthority> convertRolesToAuthorities(Set<Role> userRoles) {
        Set<GrantedAuthority> roles = new HashSet<>();
        for (Role role : userRoles) {
            roles.add(new SimpleGrantedAuthority(role.getName()));
        }
        return new ArrayList<>(roles);
    }
}