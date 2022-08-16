package ro.itschool.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ro.itschool.entity.Cinema;
import ro.itschool.exception.CinemaNotFound;

import java.util.List;
import java.util.UUID;

public interface CinemaRepository extends JpaRepository<Cinema, UUID> {

    Cinema findByName(String name) throws CinemaNotFound;

    List<Cinema> findAll(Sort sort);

    @Transactional
    void deleteByName(String name) throws CinemaNotFound;

    @Query(
            value = "SELECT * FROM cinema WHERE user_id = ?",
            nativeQuery = true)
    List<Cinema> findByUserId(Integer userId);
}
