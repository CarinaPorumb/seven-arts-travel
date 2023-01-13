package ro.itschool.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ro.itschool.entity.Literature;

import java.util.List;
import java.util.UUID;

@Repository
public interface LiteratureRepository extends JpaRepository<Literature, UUID> {

    Literature findByName(String name);

    List<Literature> findAll(Sort sort);

    @Transactional
    void deleteByName(String name);

    @Query(value = "SELECT * FROM literature WHERE user_id = ?",
            nativeQuery = true)
    List<Literature> findByUserId(Integer userId);

    @Query(value = "SELECT * FROM literature l WHERE l.name LIKE %:keyword% OR l.author LIKE %:keyword% OR l.location LIKE %:keyword% OR l.movement LIKE %:keyword% " +
                    "OR l.is_temporary LIKE %:keyword% OR l.id LIKE %:keyword% OR l.user_id LIKE %:keyword% OR l.year LIKE %:keyword%",
            nativeQuery = true)
    List<Literature> searchLiterature(@Param("keyword") String keyword);
}