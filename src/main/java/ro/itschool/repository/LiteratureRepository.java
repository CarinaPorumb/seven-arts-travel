package ro.itschool.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ro.itschool.entity.Literature;
import ro.itschool.exception.LiteratureNotFound;

import java.util.List;
import java.util.UUID;

public interface LiteratureRepository extends JpaRepository<Literature, UUID> {

    Literature findByName(String name) throws LiteratureNotFound;

    List<Literature> findAll(Sort sort);

    @Transactional
    void deleteByName(String name) throws LiteratureNotFound;

    @Query(
            value = "SELECT * FROM literature WHERE user_id = ?",
            nativeQuery = true)
    List<Literature> findByUserId(Integer userId);

}
