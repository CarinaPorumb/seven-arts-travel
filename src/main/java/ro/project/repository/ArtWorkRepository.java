package ro.project.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ro.project.entity.ArtWork;
import ro.project.enums.Category;

import java.util.List;
import java.util.UUID;

public interface ArtWorkRepository extends JpaRepository<ArtWork, UUID>, JpaSpecificationExecutor<ArtWork> {

    List<ArtWork> findByNameContainingIgnoreCase(String name);

    //TODO join for artist and movement
    @Query(value = "SELECT * FROM art_work a WHERE a.name LIKE CONCAT('%', :keyword, '%')" +
            "OR a.description LIKE CONCAT('%', :keyword, '%')" +
            "OR a.location LIKE CONCAT('%', :keyword, '%')" +
            "OR a.category LIKE CONCAT('%', :keyword, '%')",
            nativeQuery = true)
    Page<ArtWork> searchArtWork(@Param("keyword") String keyword, Pageable pageable);

    List<ArtWork> findByYear(int year);

    List<ArtWork> findByCategory(Category category);

}