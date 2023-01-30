package ro.itschool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ro.itschool.entity.ArtEvent;

import java.util.List;

@Repository
public interface ArtEventRepository extends JpaRepository<ArtEvent, Integer> {

    @Query(value = "SELECT * FROM art_event WHERE name = ?",
            nativeQuery = true)
    ArtEvent findByName(String name);

    List<ArtEvent> findAll();

    @Transactional
    void deleteByName(String name);

    @Query(value = "SELECT * FROM art_event WHERE user_id = ?",
            nativeQuery = true)
    List<ArtEvent> findByUserId(Long userId);

    @Query(value = "SELECT * FROM art_event a WHERE a.name LIKE %:keyword% OR a.location LIKE %:keyword% " +
            "OR a.movement LIKE %:keyword% OR a.is_temporary LIKE %:keyword% OR a.id LIKE %:keyword% OR " +
            "a.category LIKE %:keyword% OR a.event_time LIKE %:keyword%",
            nativeQuery = true)
    List<ArtEvent> searchArtEvent(@Param("keyword") String keyword);

    @Query(value = "SELECT * FROM art_event a WHERE a.category = 'music' having a.name LIKE %:keyword% OR " +
            "a.location LIKE %:keyword% OR a.movement LIKE %:keyword% OR a.is_temporary LIKE %:keyword% OR " +
            "a.id LIKE %:keyword% OR a.event_time LIKE %:keyword%",
            nativeQuery = true)
    List<ArtEvent> displayMusic(@Param("keyword") String keyword);

    @Query(value = "SELECT * FROM art_event a WHERE a.category = 'cinema' having a.name LIKE %:keyword% OR " +
            "a.location LIKE %:keyword% OR a.movement LIKE %:keyword% OR a.is_temporary LIKE %:keyword% OR " +
            "a.id LIKE %:keyword% OR a.event_time LIKE %:keyword%",
            nativeQuery = true)
    List<ArtEvent> displayCinema(@Param("keyword") String keyword);

    @Query(value = "SELECT * FROM art_event a WHERE a.category = 'ballet_and_theatre' having a.name LIKE %:keyword% OR " +
            "a.location LIKE %:keyword% OR a.movement LIKE %:keyword% OR a.is_temporary LIKE %:keyword% OR " +
            "a.id LIKE %:keyword% OR a.event_time LIKE %:keyword%",
            nativeQuery = true)
    List<ArtEvent> displayBalletAndTheatre(@Param("keyword") String keyword);

}