package ro.itschool.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ro.itschool.entity.BalletAndTheatre;
import ro.itschool.exception.BalletAndTheatreNotFound;

import java.util.List;
import java.util.UUID;

public interface BalletAndTheatreRepository extends JpaRepository<BalletAndTheatre, UUID> {

    BalletAndTheatre findByName(String name) throws BalletAndTheatreNotFound;

    List<BalletAndTheatre> findAll(Sort sort);

    @Transactional
    void deleteByName(String name) throws BalletAndTheatreNotFound;

    @Query(
            value = "SELECT * FROM BalletAndTheatre WHERE user_id = ?",
            nativeQuery = true)
    List<BalletAndTheatre> findByUserId(Integer userId);
}
