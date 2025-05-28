package ro.sevenartstravel.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ro.sevenartstravel.entity.ContactMessage;

import java.time.LocalDate;
import java.util.UUID;

public interface ContactMessageRepository extends JpaRepository<ContactMessage, UUID>, JpaSpecificationExecutor<ContactMessage> {

    @Query(value = "SELECT * FROM contact_message c WHERE " +
            "c.username LIKE CONCAT('%', :keyword, '%') " +
            "OR c.email LIKE CONCAT('%', :keyword, '%') " +
            "OR c.subject LIKE CONCAT('%', :keyword, '%') " +
            "OR c.message LIKE CONCAT('%', :keyword, '%')",
            nativeQuery = true)
    Page<ContactMessage> searchContact(@Param("keyword") String keyword, Pageable pageable);

    Page<ContactMessage> findByCreatedDate(LocalDate date, Pageable pageable);

    Page<ContactMessage> findByCreatedDateBetween(LocalDate start, LocalDate end, Pageable pageable);

    Page<ContactMessage> findByUsernameIgnoreCase(String username, Pageable pageable);

    Page<ContactMessage> findByEmailContainsIgnoreCase(String email, Pageable pageable);

    Page<ContactMessage> findBySubjectContainsIgnoreCase(String subject, Pageable pageable);

}