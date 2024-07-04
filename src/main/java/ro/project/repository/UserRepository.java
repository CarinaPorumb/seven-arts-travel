package ro.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ro.project.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    User findByUsernameIgnoreCase(String username);

    User findByRandomToken(String randomToken);

    User findByEmail(String email);

    @Query(value = "SELECT * FROM user u WHERE u.username LIKE CONCAT('%', :keyword, '%')" +
            "OR u.full_name LIKE CONCAT('%', :keyword, '%')" +
            "OR u.email LIKE CONCAT('%', :keyword, '%')" +
            "OR u.user_id LIKE CONCAT('%', :keyword, '%')",
            nativeQuery = true)
    List<User> searchUser(@Param("keyword") String keyword);

}