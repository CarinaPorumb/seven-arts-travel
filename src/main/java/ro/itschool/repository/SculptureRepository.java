package ro.itschool.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ro.itschool.entity.Sculpture;
import ro.itschool.exception.SculptureNotFound;

import java.util.List;
import java.util.UUID;

public interface SculptureRepository extends JpaRepository<Sculpture, UUID> {

    Sculpture findByName(String name) throws SculptureNotFound;

    List<Sculpture> findAll(Sort sort);

    @Transactional
    void deleteByName(String name) throws SculptureNotFound;

    @Query(
            value = "SELECT * FROM sculpture WHERE user_id = ?",
            nativeQuery = true)
    List<Sculpture> findByUserId(Integer userId);


    @Query(
            value = "SELECT * FROM sculpture s WHERE s.name LIKE %:keyword% OR s.author OR s.location LIKE %:keyword% OR s.movement LIKE %:keyword% " +
                    "OR s.is_temporary LIKE %:keyword% OR s.id LIKE %:keyword% OR s.user_id LIKE %:keyword% OR s.year LIKE %:keyword%",
            nativeQuery = true)
    List<Sculpture> searchSculpture(@Param("keyword") String keyword);

}
