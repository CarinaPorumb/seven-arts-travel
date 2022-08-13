package ro.itschool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.itschool.entity.Role;
import ro.itschool.entity.User;

import java.util.Set;

public interface RoleRepository extends JpaRepository <Role, Integer> {

    Role findByName(String name);

    Set<Role> findByUsers(User user);

}
