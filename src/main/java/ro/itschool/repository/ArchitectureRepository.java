package ro.itschool.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ro.itschool.entity.Architecture;
import ro.itschool.exception.ArchitectureNotFound;

import java.util.List;
import java.util.UUID;

@Repository
public interface ArchitectureRepository extends JpaRepository<Architecture, UUID> {

    Architecture findByName(String name);

    List<Architecture> findAll(Sort sort);

    @Transactional
    void deleteByName(String name) throws ArchitectureNotFound;

    @Query(
            value = "SELECT * FROM architecture WHERE user_id = ?",
            nativeQuery = true)
    List<Architecture> findByUserId(Integer userId);

    @Query(
            value = "SELECT * FROM architecture a WHERE a.name LIKE %:keyword% OR a.author OR a.location LIKE %:keyword% OR a.movement LIKE %:keyword% " +
                    "OR a.is_temporary LIKE %:keyword% OR a.id LIKE %:keyword% OR a.user_id LIKE %:keyword% OR a.year LIKE %:keyword%",
            nativeQuery = true)
    List<Architecture> searchArchitecture(@Param("keyword") String keyword);

}
