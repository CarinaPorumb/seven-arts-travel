package ro.sevenartstravel.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ro.sevenartstravel.entity.Artist;
import ro.sevenartstravel.enums.Nationality;

import java.util.Optional;
import java.util.UUID;

public interface ArtistRepository extends JpaRepository<Artist, UUID>, JpaSpecificationExecutor<Artist> {

    Page<Artist> findByNameIgnoreCase(String name, Pageable pageable);

    @Query(value = "SELECT * FROM artist a WHERE " +
            "a.name LIKE CONCAT('%', :keyword, '%') " +
            "OR a.biography LIKE CONCAT('%', :keyword, '%') " +
            "OR a.nationality LIKE CONCAT('%', :keyword, '%')",
            nativeQuery = true)
    Page<Artist> searchArtist(@Param("keyword") String keyword, Pageable pageable);

    Page<Artist> findArtistByBirthYear(int birthYear, Pageable pageable);

    Page<Artist> findArtistByDeathYear(int deathYear, Pageable pageable);

    Page<Artist> findArtistByNationality(Nationality nationality, Pageable pageable);

    Optional<Artist> findByNameIgnoreCase(String name);

}