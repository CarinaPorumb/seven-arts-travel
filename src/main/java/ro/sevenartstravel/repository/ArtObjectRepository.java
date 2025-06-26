package ro.sevenartstravel.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ro.sevenartstravel.entity.ArtObject;
import ro.sevenartstravel.enums.ArtCategory;
import ro.sevenartstravel.enums.ArtObjectType;

import java.util.UUID;

public interface ArtObjectRepository extends JpaRepository<ArtObject, UUID>, JpaSpecificationExecutor<ArtObject> {

    Page<ArtObject> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    //TODO: Extend search to include movement and status fields
    @Query(value = "SELECT * FROM art_object a WHERE " +
            "a.title LIKE CONCAT('%', :keyword, '%') " +
            "OR a.short_description LIKE CONCAT('%', :keyword, '%') " +
            "OR a.long_description LIKE CONCAT('%', :keyword, '%') " +
            "OR a.location LIKE CONCAT('%', :keyword, '%')",
            nativeQuery = true)
    Page<ArtObject> searchArtObjects(@Param("keyword") String keyword, Pageable pageable);

    Page<ArtObject> findByYear(int year, Pageable pageable);

    Page<ArtObject> findByArtCategory(ArtCategory artCategory, Pageable pageable);

    Page<ArtObject> findByLocation(String location, Pageable pageable);

    Page<ArtObject> findByYearBetween(int startYear, int endYear, Pageable pageable);

    Page<ArtObject> findByArtObjectType(ArtObjectType artObjectType, Pageable pageable);

}