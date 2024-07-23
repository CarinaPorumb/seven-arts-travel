package ro.project.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ro.project.entity.Artist;

import java.util.List;
import java.util.UUID;

public interface ArtistRepository extends JpaRepository<Artist, UUID>, JpaSpecificationExecutor<Artist> {

    List<Artist> findByNameContainingIgnoreCase(String name);

    //TODO join for artWorks and artEvents
    @Query(value = "SELECT * FROM artist a WHERE a.name LIKE CONCAT('%', :keyword, '%')" +
            "OR a.biography LIKE CONCAT('%', :keyword, '%')" +
            "OR a.nationality LIKE CONCAT('%', :keyword, '%')",
            nativeQuery = true)
    Page<Artist> searchArtist(@Param("keyword") String keyword, Pageable pageable);

    List<Artist> findArtistByBirthYearAndDeathYear(int birthYear, int deathYear);
    List<Artist> findArtistByBirthYear(int birthYear);
    List<Artist> findArtistByDeathYear(int deathYear);
}