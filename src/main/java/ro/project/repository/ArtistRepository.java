package ro.project.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ro.project.entity.Artist;
import ro.project.enums.Nationality;

import java.util.UUID;

public interface ArtistRepository extends JpaRepository<Artist, UUID>, JpaSpecificationExecutor<Artist> {

    Page<Artist> findByNameContainingIgnoreCase(String name, Pageable pageable);

    Page<Artist> findByNationality(Nationality nationality, Pageable pageable);

    @Query(value = "SELECT * FROM artist a WHERE a.name LIKE CONCAT('%', :keyword, '%')" +
            "OR a.biography LIKE CONCAT('%', :keyword, '%')" +
            "OR a.nationality LIKE CONCAT('%', :keyword, '%')",
            nativeQuery = true)
    Page<Artist> searchArtist(@Param("keyword") String keyword, Pageable pageable);

    Page<Artist> findArtistByBirthYearAndDeathYear(int birthYear, int deathYear, Pageable pageable);

    Page<Artist> findArtistByBirthYear(int birthYear, Pageable pageable);

    Page<Artist> findArtistByDeathYear(int deathYear, Pageable pageable);
}