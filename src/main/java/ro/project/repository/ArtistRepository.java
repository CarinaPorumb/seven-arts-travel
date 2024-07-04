package ro.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ro.project.entity.Artist;

import java.util.List;
import java.util.UUID;

public interface ArtistRepository extends JpaRepository<Artist, UUID>  {

    List<Artist> findByNameContainingIgnoreCase(String name);

    List<Artist> findByBirthYear(int birthYear);

    List<Artist> findByDeathYear(int deathYear);

    //TODO join for artWorks and artEvents
    @Query(value = "SELECT * FROM artist a WHERE a.name LIKE CONCAT('%', :keyword, '%')" +
            "OR a.biography LIKE CONCAT('%', :keyword, '%')" +
            "OR a.nationality LIKE CONCAT('%', :keyword, '%')",
            nativeQuery = true)
    List<Artist> searchArtist(@Param("keyword") String keyword);

}