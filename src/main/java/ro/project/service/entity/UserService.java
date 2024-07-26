package ro.project.service.entity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ro.project.exception.NotFoundException;
import ro.project.model.UserDTO;

import java.util.Optional;

public interface UserService {

    Page<UserDTO> searchUser(String keyword, Pageable pageable);

    Page<UserDTO> getAllUsers(String username, String fullName, String email, Integer pageNumber, Integer pageSize);

    UserDTO saveUser(UserDTO user);

    void updateUser(UserDTO user);

    Optional<UserDTO> findUserByUserName(String username) throws NotFoundException;

    UserDTO findUserByRandomToken(String randomToken) throws NotFoundException;

    UserDTO findByEmail(String emailConfirm);

}