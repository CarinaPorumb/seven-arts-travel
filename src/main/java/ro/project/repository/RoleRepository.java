package ro.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.project.entity.Role;

import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {

    Role findByName(String name);

}