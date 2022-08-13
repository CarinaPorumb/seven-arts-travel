package ro.itschool.service;

import org.springframework.stereotype.Service;
import ro.itschool.entity.User;

import java.util.List;

@Service
public interface UserService {

    User findUserByEmail(String email);

    User findUserByUserName(String username);

    User findUserByRandomToken(String randomToken);

    boolean findUserByUserNameAndPassword(String userName, String password);

    List<User> findAll();

    void deleteById(Integer id);

    void saveUser(User u);

    void updateUser(User user);

    List<User> searchUser(String keyword);

}
