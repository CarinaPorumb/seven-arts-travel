package ro.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ro.project.entity.ArtObject;

import java.util.List;

@Repository
public interface ArtObjectRepository extends JpaRepository<ArtObject, Integer> {

    @Query(value = "SELECT * FROM art_object WHERE name = ?",
            nativeQuery = true)
    ArtObject findByName(String name);

    @Query(value = "SELECT * FROM art_object WHERE user_id = ?",
            nativeQuery = true)
    List<ArtObject> findByUserId(Long userId);

    @Query(
            value = "SELECT * FROM art_object a WHERE a.id LIKE %:keyword% OR a.author LIKE %:keyword% " +
                    " OR a.is_temporary LIKE %:keyword% OR a.location LIKE %:keyword% OR a.movement LIKE %:keyword% " +
                    "OR a.name LIKE %:keyword% OR a.year LIKE %:keyword% OR a.category LIKE %:keyword%",
            nativeQuery = true)
    List<ArtObject> searchArtObject(@Param("keyword") String keyword);

    @Query(value = "SELECT * FROM art_object a WHERE a.category = 'architecture' having a.id LIKE %:keyword% " +
            "OR a.author LIKE %:keyword% OR a.is_temporary LIKE %:keyword% OR a.location LIKE %:keyword% " +
            "OR a.movement LIKE %:keyword% OR a.name LIKE %:keyword% OR a.year LIKE %:keyword%",
            nativeQuery = true)
    List<ArtObject> displayArchitecture(@Param("keyword") String keyword);

    @Query(value = "SELECT * FROM art_object a WHERE a.category = 'sculpture' having a.id LIKE %:keyword%" +
            " OR a.author LIKE %:keyword% OR a.is_temporary LIKE %:keyword% OR a.location LIKE %:keyword%" +
            " OR a.movement LIKE %:keyword% OR a.name LIKE %:keyword% OR a.year LIKE %:keyword%",
            nativeQuery = true)
    List<ArtObject> displaySculpture(@Param("keyword") String keyword);

    @Query(value = "SELECT * FROM art_object a WHERE a.category = 'painting' having a.id LIKE %:keyword%" +
            " OR a.author LIKE %:keyword% OR a.is_temporary LIKE %:keyword% OR a.location LIKE %:keyword%" +
            " OR a.movement LIKE %:keyword% OR a.name LIKE %:keyword% OR a.year LIKE %:keyword%",
            nativeQuery = true)
    List<ArtObject> displayPainting(@Param("keyword") String keyword);

    @Query(value = "SELECT * FROM art_object a WHERE a.category = 'literature' having a.id LIKE %:keyword%" +
            " OR a.author LIKE %:keyword% OR a.is_temporary LIKE %:keyword% OR a.location LIKE %:keyword%" +
            " OR a.movement LIKE %:keyword% OR a.name LIKE %:keyword% OR a.year LIKE %:keyword%",
            nativeQuery = true)
    List<ArtObject> displayLiterature(@Param("keyword") String keyword);

}