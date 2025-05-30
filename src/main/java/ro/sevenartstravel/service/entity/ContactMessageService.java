package ro.sevenartstravel.service.entity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ro.sevenartstravel.dto.ContactMessageDTO;

import java.time.LocalDate;

public interface ContactMessageService {

    Page<ContactMessageDTO> getAllContactMessages(String username, String email, String subject, String message, LocalDate date, Pageable pageable);

    Page<ContactMessageDTO> getContactMessagesByUsername(String username, Pageable pageable);

    Page<ContactMessageDTO> getContactMessagesByEmail(String email, Pageable pageable);

    Page<ContactMessageDTO> getContactMessagesBySubject(String subject, Pageable pageable);

    Page<ContactMessageDTO> getContactMessagesByDate(LocalDate date, Pageable pageable);

    Page<ContactMessageDTO> getContactMessagesByDateBetween(LocalDate startDate, LocalDate endDate, Pageable pageable);

    Page<ContactMessageDTO> searchContactMessages(String keyword, Pageable pageable);

}