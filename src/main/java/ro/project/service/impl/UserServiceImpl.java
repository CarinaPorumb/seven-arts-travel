package ro.project.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ro.project.entity.Role;
import ro.project.entity.User;
import ro.project.exception.TokenNotFound;
import ro.project.exception.UserNotFound;
import ro.project.model.UserDTO;
import ro.project.model.mapper.UserMapper;
import ro.project.repository.RoleRepository;
import ro.project.repository.UserRepository;
import ro.project.service.UserService;
import ro.project.service.email.EmailBodyService;
import ro.project.service.email.EmailSender;

import java.util.*;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final EmailBodyService emailBodyService;
    private final EmailSender emailSender;
    private final UserMapper userMapper;

    @Override
    public Optional<UserDTO> getById(Long id) {
        return Optional.ofNullable(userMapper.userToDto(userRepository.findById(id).orElse(null)));
    }

    @Override
    public List<UserDTO> searchUser(String keyword) {
        return userRepository.searchUser(keyword).stream().map(userMapper::userToDto).toList();
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream().map(userMapper::userToDto).toList();
    }

    @Override
    public User saveUser(User receivedUser) {
        User user = new User(receivedUser);
        user.setPassword(new BCryptPasswordEncoder().encode(receivedUser.getPassword()));
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
        return user;
    }

    @Override
    public void updateUser(User user) {
        List<GrantedAuthority> actualAuthorities = getUserAuthority(user.getRoles());
        Authentication newAuth = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), actualAuthorities);
        SecurityContextHolder.getContext().setAuthentication(newAuth);
        userRepository.save(user);
    }

    @Override
    public void deleteById(Long id) throws UserNotFound {
        if (userRepository.existsById(id))
            userRepository.deleteById(id);
        else throw new UserNotFound();
    }

    public User findUserByUserName(String username) throws UserNotFound {
        return Optional.ofNullable(userRepository.findByUsernameIgnoreCase(username)).orElseThrow(UserNotFound::new);
    }

    public User findUserByRandomToken(String randomToken) throws TokenNotFound {
        return Optional.ofNullable(userRepository.findByRandomToken(randomToken)).orElseThrow(TokenNotFound::new);
    }

    @Override
    public User findByEmail(String emailConfirm) {
        return Optional.ofNullable(userRepository.findByEmail(emailConfirm)).orElseThrow();
    }

    private List<GrantedAuthority> getUserAuthority(Set<Role> userRoles) {
        Set<GrantedAuthority> roles = new HashSet<>();
        for (Role role : userRoles) {
            roles.add(new SimpleGrantedAuthority(role.getName()));
        }
        return new ArrayList<>(roles);
    }

}