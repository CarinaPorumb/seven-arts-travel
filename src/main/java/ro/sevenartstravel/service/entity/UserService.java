package ro.sevenartstravel.service.entity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ro.sevenartstravel.dto.UserCreateDTO;
import ro.sevenartstravel.dto.UserDTO;
import ro.sevenartstravel.dto.UserUpdateDTO;
import ro.sevenartstravel.entity.User;
import ro.sevenartstravel.exception.NotFoundException;

import java.util.Optional;
import java.util.UUID;

public interface UserService {

    Page<UserDTO> searchUser(String keyword, Pageable pageable);

    Page<UserDTO> getAllUsers(String username, String fullName, String email, Pageable pageable);

    UserDTO saveUser(UserCreateDTO user);

    void updateProfile(UserUpdateDTO userDTO);        // user logged-in

    void activateUser(String token);

    void updateUserByAdmin(UserUpdateDTO userDTO);

    Optional<UserDTO> findUserByUsername(String username) throws NotFoundException;

    Optional<User> findUserEntityByUsername(String username);

    UserDTO findUserByRandomToken(String randomToken) throws NotFoundException;

    UserDTO findByEmail(String emailConfirm);

    boolean existsByUsernameOrEmail(String username, String email);

    void deleteUser(UUID id);

    void addAdminRole(UUID userId);

    void removeAdminRole(UUID userId);

    void addRoleToUser(UUID userId, String roleName);
}