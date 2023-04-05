package ro.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ro.project.entity.Contact;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Integer> {

    @Transactional
    void deleteById(Integer id);

    @Query(value = "SELECT * FROM contact c WHERE c.username LIKE %:keyword% OR c.id LIKE %:keyword% OR c.email LIKE %:keyword% OR c.subject LIKE %:keyword% " +
                    "OR c.message LIKE %:keyword%",
            nativeQuery = true)
    List<Contact> searchContact(@Param("keyword") String keyword);
}