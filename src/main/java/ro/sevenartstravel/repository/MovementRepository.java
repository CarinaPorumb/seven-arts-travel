package ro.sevenartstravel.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ro.sevenartstravel.entity.Movement;

import java.util.Optional;
import java.util.UUID;

public interface MovementRepository extends JpaRepository<Movement, UUID>, JpaSpecificationExecutor<Movement> {

    Page<Movement> findByNameContainingIgnoreCase(String name, Pageable pageable);

    Page<Movement> findByStartYear(Integer startYear, Pageable pageable);

    Page<Movement> findByEndYear(Integer endYear, Pageable pageable);

    Page<Movement> findByEndYearBetween(Integer startYear, Integer endYear, Pageable pageable);

    //TODO: extend search to include artObjects fields
    @Query(value = "SELECT * FROM movement m WHERE " +
            "m.name LIKE CONCAT('%', :keyword, '%') " +
            "OR m.description LIKE CONCAT('%', :keyword, '%') " +
            "OR m.origin_region LIKE CONCAT('%', :keyword, '%')",
            nativeQuery = true)
    Page<Movement> searchMovements(@Param("keyword") String keyword, Pageable pageable);

    Optional<Movement> findByNameIgnoreCase(String name);

    Page<Movement> findByOriginRegionContainingIgnoreCase(String region, Pageable pageable);

}