package ro.project.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ro.project.entity.Contact;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ContactRepository extends JpaRepository<Contact, UUID>, JpaSpecificationExecutor<Contact> {

    @Query(value = "SELECT * FROM contact c WHERE c.username LIKE CONCAT('%', :keyword, '%')" +
            "OR c.email LIKE CONCAT('%', :keyword, '%')" +
            "OR c.subject LIKE CONCAT('%', :keyword, '%')" +
            "OR c.message LIKE CONCAT('%', :keyword, '%')",
            nativeQuery = true)
    Page<Contact> searchContact(@Param("keyword") String keyword, Pageable pageable);

    List<Contact> findByDate(LocalDate date);

    List<Contact> findByDateBetween(LocalDate start, LocalDate end);

    List<Contact> findByUsernameContainsIgnoreCase(String username);

    List<Contact> findByEmailContainsIgnoreCase(String email);

    List<Contact> findBySubjectContainsIgnoreCase(String subject);

    List<Contact> findByMessageContainsIgnoreCase(String message);

}