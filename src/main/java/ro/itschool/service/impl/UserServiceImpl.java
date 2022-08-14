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
import ro.itschool.repository.LocationRepository;
import ro.itschool.repository.RoleRepository;
import ro.itschool.repository.UserRepository;
import ro.itschool.service.UserService;
import ro.itschool.service.email.EmailBodyService;
import ro.itschool.service.email.EmailSender;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    EmailBodyService emailBodyService;

    @Autowired
    EmailSender emailSender;

    @Autowired
    LocationRepository locationRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User findUserByUserName(String username) {
        return userRepository.findByUsernameIgnoreCase(username);
    }

    @Override
    public User findUserByRandomToken(String randomToken) {
        return userRepository.findByRandomToken(randomToken);
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
    public void deleteById(Integer id) {
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

    @Override
    public void updateUser(User user) {
        List<GrantedAuthority> actualAuthorities = getUserAuthority(user.getRoles());
        Authentication newAuth = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), actualAuthorities);
        SecurityContextHolder.getContext().setAuthentication(newAuth);
        userRepository.save(user);
    }

    @Override
    public List<User> searchUser(String keyword) {
        return null;
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
