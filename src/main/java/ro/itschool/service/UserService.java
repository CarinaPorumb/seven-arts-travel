package ro.itschool.service;

import org.springframework.stereotype.Service;
import ro.itschool.entity.User;
import ro.itschool.exception.EmailNotFound;
import ro.itschool.exception.TokenNotFound;
import ro.itschool.exception.UserNotFound;

import java.util.List;

@Service
public interface UserService {

    User findUserByEmail(String email) throws EmailNotFound;

    User findUserByUserName(String username) throws UserNotFound;

    User findUserByRandomToken(String randomToken) throws TokenNotFound;

    boolean findUserByUserNameAndPassword(String userName, String password);

    List<User> findAll();

    void deleteById(Integer id) throws UserNotFound;

    void saveUser(User u);
    void updateUser(User user);

    List<User> searchUser(String keyword);

}
