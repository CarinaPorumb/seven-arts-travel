package ro.itschool.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ro.itschool.entity.BalletAndTheatre;

import java.util.List;
import java.util.UUID;

@Repository
public interface BalletAndTheatreRepository extends JpaRepository<BalletAndTheatre, UUID> {

    BalletAndTheatre findByName(String name);

    List<BalletAndTheatre> findAll(Sort sort);

    @Transactional
    void deleteByName(String name);

    @Query(value = "SELECT * FROM ballet_and_theatre WHERE user_id = ?",
            nativeQuery = true)
    List<BalletAndTheatre> findByUserId(Long userId);

    @Query(value = "SELECT * FROM ballet_and_theatre b WHERE b.name LIKE %:keyword% OR b.author OR b.location LIKE %:keyword% OR b.movement LIKE %:keyword% " +
                    "OR b.is_temporary LIKE %:keyword% OR b.id LIKE %:keyword% OR b.user_id LIKE %:keyword% OR b.event_time LIKE %:keyword%",
            nativeQuery = true)
    List<BalletAndTheatre> searchBalletAndTheatre(@Param("keyword") String keyword);
}