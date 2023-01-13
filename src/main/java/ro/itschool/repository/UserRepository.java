package ro.itschool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ro.itschool.entity.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsernameIgnoreCase(String username);

    User findByRandomToken(String randomToken);

    @Query(value = "SELECT * FROM user u WHERE u.username LIKE %:keyword% OR u.full_name LIKE %:keyword% OR u.email LIKE %:keyword% " +
                    "OR u.user_id LIKE %:keyword%",
            nativeQuery = true)
    List<User> searchUser(@Param("keyword") String keyword);
}