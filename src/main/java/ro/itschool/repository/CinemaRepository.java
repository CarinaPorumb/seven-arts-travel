package ro.itschool.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ro.itschool.entity.Cinema;
import ro.itschool.exception.CinemaNotFound;

import java.util.List;
import java.util.UUID;

@Repository
public interface CinemaRepository extends JpaRepository<Cinema, UUID> {

    Cinema findByName(String name) throws CinemaNotFound;

    List<Cinema> findAll(Sort sort);

    @Transactional
    void deleteByName(String name) throws CinemaNotFound;

    @Query(
            value = "SELECT * FROM cinema WHERE user_id = ?",
            nativeQuery = true)
    List<Cinema> findByUserId(Integer userId);

    @Query(
            value = "SELECT * FROM cinema c WHERE c.name LIKE %:keyword% OR c.location LIKE %:keyword% OR c.movement LIKE %:keyword% " +
                    "OR c.is_temporary LIKE %:keyword% OR c.id LIKE %:keyword% OR c.user_id LIKE %:keyword% OR c.event_time LIKE %:keyword%",
            nativeQuery = true)
    List<Cinema> searchCinema(@Param("keyword") String keyword);
}