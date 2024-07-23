package ro.project.service.entity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ro.project.model.ContactDTO;

import java.time.LocalDate;
import java.util.List;

public interface ContactService {

    Page<ContactDTO> getAllContacts(String username, String email, String subject, String message, LocalDate date, Integer pageNumber, Integer pageSize);

    List<ContactDTO> getAllContactsByUsername(String username);

    List<ContactDTO> getAllContactsByEmail(String email);

    List<ContactDTO> getAllContactsBySubject(String subject);

    List<ContactDTO> getAllContactsByMessage(String message);

    List<ContactDTO> getAllContactsByDate(LocalDate date);

    Page<ContactDTO> searchContacts(String keyword, Pageable pageable);

}