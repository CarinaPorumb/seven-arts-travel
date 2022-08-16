package ro.itschool.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ro.itschool.entity.Music;
import ro.itschool.exception.MusicNotFound;

import java.util.List;
import java.util.UUID;

public interface MusicRepository extends JpaRepository<Music, UUID> {


    Music findByName(String name) throws MusicNotFound;

    List<Music> findAll(Sort sort);

    @Transactional
    void deleteByName(String name) throws MusicNotFound;

    @Query(
            value = "SELECT * FROM music WHERE user_id = ?",
            nativeQuery = true)
    List<Music> findByUserId(Integer userId);

}
