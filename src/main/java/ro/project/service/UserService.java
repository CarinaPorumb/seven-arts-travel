package ro.project.service;

import ro.project.entity.User;
import ro.project.exception.TokenNotFound;
import ro.project.exception.UserNotFound;
import ro.project.model.UserDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<UserDTO> getById(Long id);

    List<UserDTO> searchUser(String keyword);

    List<UserDTO> getAllUsers();

    User saveUser(User user);

    void updateUser(User user);

    void deleteById(Long id) throws UserNotFound;

    User findUserByUserName(String username) throws UserNotFound;

    User findUserByRandomToken(String randomToken) throws TokenNotFound;

    User findByEmail(String emailConfirm);

}