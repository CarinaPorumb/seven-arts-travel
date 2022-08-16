package ro.itschool.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ro.itschool.entity.Architecture;
import ro.itschool.exception.ArchitectureNotFound;
import java.util.List;
import java.util.UUID;

public interface ArchitectureRepository extends JpaRepository<Architecture, UUID> {

    Architecture findByName(String name) throws ArchitectureNotFound;

    List<Architecture> findAll(Sort sort);

    @Transactional
    void deleteByName(String name) throws ArchitectureNotFound;

    @Query(
            value = "SELECT * FROM architecture WHERE user_id = ?",
            nativeQuery = true)
    List<Architecture> findByUserId(Integer userId);

}
