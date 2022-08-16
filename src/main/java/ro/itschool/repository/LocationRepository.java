package ro.itschool.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ro.itschool.entity.Location;
import ro.itschool.exception.LocationNotFound;
import java.util.List;
import java.util.UUID;

public interface LocationRepository extends JpaRepository<Location, UUID> {

    Location findByName(String name) throws LocationNotFound;

    List<Location> findAll(Sort sort);

    @Transactional
    void deleteByName(String name) throws LocationNotFound;

    @Query(
            value = "SELECT * FROM location WHERE user_id = ?",
            nativeQuery = true)
    List<Location> findByUserId(Integer userId);

}
