package ro.itschool.service;

import org.springframework.stereotype.Service;
import ro.itschool.controller.model.UserDTO;
import ro.itschool.entity.User;
import ro.itschool.exception.TokenNotFound;
import ro.itschool.exception.UserNotFound;

import java.util.List;

@Service
public interface UserService {

    User findUserByUserName(String username) throws UserNotFound;

    User findUserByRandomToken(String randomToken) throws TokenNotFound;

    List<UserDTO> findAll();

    void deleteById(long id) throws UserNotFound;

    User saveUser(User user);

    void updateUser(User user);

    List<UserDTO> searchUser(String keyword);
}