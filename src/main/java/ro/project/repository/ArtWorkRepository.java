package ro.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ro.project.entity.ArtWork;
import ro.project.enums.Category;

import java.util.List;
import java.util.UUID;

public interface ArtWorkRepository extends JpaRepository<ArtWork, UUID> {

    ArtWork findByNameContainingIgnoreCase(String name);

    //TODO join for artist and movement
    @Query(value = "SELECT * FROM art_work a WHERE a.name LIKE CONCAT('%', :keyword, '%')" +
            "OR a.description LIKE CONCAT('%', :keyword, '%')" +
            "OR a.location LIKE CONCAT('%', :keyword, '%')" +
            "OR a.category LIKE CONCAT('%', :keyword, '%')",
            nativeQuery = true)
    List<ArtWork> searchArtWork(@Param("keyword") String keyword);

    List<ArtWork> findByYear(int year);

    @Query(value = "SELECT * FROM art_work a WHERE a.category = :category", nativeQuery = true)
    List<ArtWork> findByCategory(Category category);

}