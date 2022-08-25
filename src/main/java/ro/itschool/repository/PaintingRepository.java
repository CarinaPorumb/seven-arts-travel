package ro.itschool.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ro.itschool.entity.Painting;
import ro.itschool.exception.PaintingNotFound;

import java.util.List;
import java.util.UUID;

public interface PaintingRepository extends JpaRepository<Painting, UUID> {

    Painting findByName(String name) throws PaintingNotFound;

    List<Painting> findAll(Sort sort);

    @Transactional
    void deleteByName(String name) throws PaintingNotFound;

    @Query(
            value = "SELECT * FROM painting WHERE user_id = ?",
            nativeQuery = true)
    List<Painting> findByUserId(Integer userId);

    @Query(
            value = "SELECT * FROM painting p WHERE p.name LIKE %:keyword% OR p.author OR p.location LIKE %:keyword% OR p.movement LIKE %:keyword% " +
                    "OR p.is_temporary LIKE %:keyword% OR p.id LIKE %:keyword% OR p.user_id LIKE %:keyword% OR p.year LIKE %:keyword%",
            nativeQuery = true)
    List<Painting> searchPaintings(@Param("keyword") String keyword);

}
