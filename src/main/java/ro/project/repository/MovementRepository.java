package ro.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ro.project.entity.Movement;

import java.util.List;
import java.util.UUID;

public interface MovementRepository extends JpaRepository<Movement, UUID> {

    List<Movement> findByName(String name);

    List<Movement> findByStartYear(Integer startYear);

    List<Movement> findByEndYear(Integer endYear);

    List<Movement> findByEndYearBetween(Integer startYear, Integer endYear);

    //TODO join for artWorks and artEvents
    @Query(value = "SELECT * FROM movement m WHERE m.name LIKE CONCAT('%', :keyword, '%')" +
            "OR m.description LIKE CONCAT('%', :keyword, '%')",
            nativeQuery = true)
    List<Movement> searchMovements(@Param("keyword") String keyword);

}