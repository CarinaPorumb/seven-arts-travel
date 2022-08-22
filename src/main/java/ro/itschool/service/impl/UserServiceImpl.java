package ro.itschool.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ro.itschool.entity.Role;
import ro.itschool.entity.User;
import ro.itschool.exception.EmailNotFound;
import ro.itschool.exception.TokenNotFound;
import ro.itschool.exception.UserNotFound;
import ro.itschool.repository.RoleRepository;
import ro.itschool.repository.UserRepository;
import ro.itschool.service.UserService;
import ro.itschool.service.email.EmailBodyService;
import ro.itschool.service.email.EmailSender;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    EmailBodyService emailBodyService;

    @Autowired
    EmailSender emailSender;


    @Override
    public User findUserByEmail(String email) throws EmailNotFound {
        return Optional.ofNullable(userRepository.findByEmail(email)).orElseThrow(EmailNotFound::new);
    }

    @Override
    public User findUserByUserName(String username) throws UserNotFound {
        return Optional.ofNullable(userRepository.findByUsernameIgnoreCase(username)).orElseThrow(UserNotFound::new);
    }

    @Override
    public User findUserByRandomToken(String randomToken) throws TokenNotFound {
        return Optional.ofNullable(userRepository.findByRandomToken(randomToken)).orElseThrow(TokenNotFound::new);
    }

    @Override
    public boolean findUserByUserNameAndPassword(String userName, String password) {
        final Optional<User> user = Optional.ofNullable(userRepository.findByUsernameIgnoreCase(userName));
        return user.filter(user1 -> BCrypt.checkpw(password, user1.getPassword())).isPresent();
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void deleteById(Integer id) throws UserNotFound {
        Optional.of(userRepository.findById(id)).orElseThrow(UserNotFound::new);
        userRepository.deleteById(id);
    }

    @Override
    public void saveUser(User receivedUser) {
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
    }


//  public User addLocationToUser(User receivedUser) {
//    User user = new User(receivedUser);
//        if (receivedUser.getArchitectures() != null)
//                receivedUser.getArchitectures()
//                .forEach(architecture -> {architecture.setUser(user);
//                architectureRepository.save(architecture);
//                });
//                return user;

    @Override
    public void updateUser(User user) {
        List<GrantedAuthority> actualAuthorities = getUserAuthority(user.getRoles());
        Authentication newAuth = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), actualAuthorities);
        SecurityContextHolder.getContext().setAuthentication(newAuth);
        userRepository.save(user);
    }

    @Override
    public List<User> searchUser(String keyword) {
        return userRepository.searchUser(keyword);
    }


//    @Override
//    public List<UserDTO> searchUser(String keyword) {
//        return userRepository.searchUser(keyword).stream().map(UserMapper::convertToDTO).toList();
//    }

    private List<GrantedAuthority> getUserAuthority(Set<Role> userRoles) {
        Set<GrantedAuthority> roles = new HashSet<>();
        for (Role role : userRoles) {
            roles.add(new SimpleGrantedAuthority(role.getName()));
        }
        return new ArrayList<>(roles);
    }
}
