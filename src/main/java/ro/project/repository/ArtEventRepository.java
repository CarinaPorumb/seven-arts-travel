package ro.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ro.project.entity.ArtEvent;
import ro.project.enums.Category;

import java.util.List;
import java.util.UUID;

public interface ArtEventRepository extends JpaRepository<ArtEvent, UUID>, JpaSpecificationExecutor<ArtEvent> {

    List<ArtEvent> findByNameContainingIgnoreCase(String name);

    //TODO join for artist and movement
    @Query(value = "SELECT * FROM art_event a WHERE a.name LIKE CONCAT('%', :keyword, '%')" +
            "OR a.description LIKE CONCAT('%', :keyword, '%')" +
            "OR a.location LIKE CONCAT('%', :keyword, '%')" +
            "OR a.category LIKE CONCAT('%', :keyword, '%')" +
            "OR a.status LIKE CONCAT('%', :keyword, '%')",
            nativeQuery = true)
    List<ArtEvent> searchArtEvent(@Param("keyword") String keyword);

    @Query(value = "SELECT * FROM art_event a WHERE a.category = :category", nativeQuery = true)
    List<ArtEvent> findEventsByCategory(Category category);
}