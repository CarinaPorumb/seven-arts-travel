package ro.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ro.project.entity.Contact;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ContactRepository extends JpaRepository<Contact, UUID> {

    @Query(value = "SELECT * FROM contact c WHERE c.username LIKE CONCAT('%', :keyword, '%')" +
            "OR c.email LIKE CONCAT('%', :keyword, '%')" +
            "OR c.subject LIKE CONCAT('%', :keyword, '%')" +
            "OR c.message LIKE CONCAT('%', :keyword, '%')",
            nativeQuery = true)
    List<Contact> searchContact(@Param("keyword") String keyword);

    List<Contact> findByDate(LocalDate date);

    List<Contact> findByDateBetween(LocalDate start, LocalDate end);

}