package ro.project.service.entity.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.project.entity.Role;
import ro.project.entity.User;
import ro.project.exception.NotFoundException;
import ro.project.model.UserDTO;
import ro.project.model.mapper.UserMapper;
import ro.project.repository.RoleRepository;
import ro.project.repository.UserRepository;
import ro.project.service.crud.CrudServiceImpl;
import ro.project.service.email.EmailBodyService;
import ro.project.service.email.EmailSender;
import ro.project.service.entity.UserService;
import ro.project.service.util.SpecificationUtils;

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
        super(userRepository);
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.emailBodyService = emailBodyService;
        this.emailSender = emailSender;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserDTO> searchUser(String keyword, Pageable pageable) {
        log.debug("Searching user by keyword {}", keyword);
        return userRepository.searchUser(keyword, pageable)
                .map(userMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserDTO> getAllUsers(String username, String fullName, String email, Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("username").ascending());

        Specification<User> specification = Specification.where(SpecificationUtils.<User>attributeLike("username", username))
                .and(SpecificationUtils.attributeLike("fullName", fullName))
                .and(SpecificationUtils.attributeLike("email", email));

        log.debug("Listing users with filters - Username: {}, FullName: {}, Email {} ", username, fullName, email);
        return userRepository.findAll(specification, pageable).map(userMapper::toDTO);
    }

    @Override
    @Transactional
    public UserDTO saveUser(UserDTO receivedUser) {

        User user = userMapper.toEntity(receivedUser);
        user.setPassword(passwordEncoder.encode(receivedUser.getPassword()));
        user.setRandomToken(UUID.randomUUID().toString());
        emailSender.sendEmail(user.getEmail(), "Activate your Account", emailBodyService.emailBody(user));

        receivedUser.getRoles().forEach(role -> {
            final Role roleByName = roleRepository.findByName(role.getName());
            if (roleByName == null)
                user.getRoles().add(roleRepository.save(role));
            else {
                role.setId(roleByName.getId());
            }
        });
        userRepository.save(user);
        return userMapper.toDTO(user);
    }

    @Override
    @Transactional
    public void updateUser(UserDTO userDTO) {
        User existingUser = userRepository.findById(userDTO.getId())
                .orElseThrow(() -> new NotFoundException("User not found"));

        userMapper.updateEntity(existingUser, userDTO);

        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }

        List<GrantedAuthority> actualAuthorities = getUserAuthority(existingUser.getRoles());
        Authentication newAuth = new UsernamePasswordAuthenticationToken(existingUser.getUsername(), existingUser.getPassword(), actualAuthorities);
        SecurityContextHolder.getContext().setAuthentication(newAuth);

        userRepository.save(existingUser);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserDTO> findUserByUserName(String username) throws NotFoundException {
        log.debug("Attempting to get User by username {}", username);
        return userRepository.findByUsernameIgnoreCase(username)
                .map(userMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO findUserByRandomToken(String randomToken) throws NotFoundException {
        log.debug("Attempting to get User by random token {}", randomToken);
        return userRepository.findByRandomToken(randomToken)
                .map(userMapper::toDTO)
                .orElseThrow(() -> new NotFoundException("User with random token " + randomToken + " not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO findByEmail(String emailConfirm) {
        log.debug("Attempting to get User by email {}", emailConfirm);
        return userRepository.findByEmail(emailConfirm)
                .map(userMapper::toDTO)
                .orElseThrow(() -> new NotFoundException("User with email " + emailConfirm + " not found"));
    }

    private List<GrantedAuthority> getUserAuthority(Set<Role> userRoles) {
        Set<GrantedAuthority> roles = new HashSet<>();
        for (Role role : userRoles) {
            roles.add(new SimpleGrantedAuthority(role.getName()));
        }
        return new ArrayList<>(roles);
    }

    @Override
    protected User toEntity(UserDTO dto) {
        return userMapper.toEntity(dto);
    }

    @Override
    protected UserDTO toDTO(User entity) {
        return userMapper.toDTO(entity);
    }

    @Override
    protected void updateEntity(User entity, UserDTO dto) {
        userMapper.updateEntity(entity, dto);
    }

    @Override
    protected void patchEntity(User entity, UserDTO dto) {
        userMapper.patchEntity(entity, dto);
    }

}