package ro.sevenartstravel.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ro.sevenartstravel.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID>, JpaSpecificationExecutor<User> {

    Optional<User> findByUsernameIgnoreCase(String username);

    Optional<User> findByRandomToken(String randomToken);

    Optional<User> findByEmail(String email);

    @Query(value = "SELECT * FROM user u WHERE " +
            "u.username LIKE CONCAT('%', :keyword, '%') " +
            "OR u.full_name LIKE CONCAT('%', :keyword, '%') " +
            "OR u.email LIKE CONCAT('%', :keyword, '%') " +
            "OR u.user_id LIKE CONCAT('%', :keyword, '%')",
            nativeQuery = true)
    Page<User> searchUser(@Param("keyword") String keyword, Pageable pageable);

    boolean existsByUsernameIgnoreCase(String username);

    boolean existsByEmailIgnoreCase(String email);

}