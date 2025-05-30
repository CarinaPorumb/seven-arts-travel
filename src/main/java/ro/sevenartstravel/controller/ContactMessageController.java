package ro.sevenartstravel.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ro.sevenartstravel.dto.ContactMessageDTO;
import ro.sevenartstravel.exception.NotFoundException;
import ro.sevenartstravel.service.crud.CrudService;
import ro.sevenartstravel.service.entity.ContactMessageService;

import java.net.URI;
import java.time.LocalDate;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/contact-messages")
@RequiredArgsConstructor
public class ContactMessageController {

    private final ContactMessageService contactService;
    private final CrudService<ContactMessageDTO, UUID> crudService;

    @GetMapping
    public ResponseEntity<Page<ContactMessageDTO>> getContactMessages(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String subject,
            @RequestParam(required = false) String message,
            @RequestParam(required = false) LocalDate createdDate,
            Pageable pageable) {

        if (search != null && !search.isEmpty()) {
            log.info("Searching contact messages with keyword: {}", search);
            return ResponseEntity.ok(contactService.searchContactMessages(search, pageable));
        } else if ((username != null && !username.isEmpty())
                || (email != null && !email.isEmpty())
                || (subject != null && !subject.isEmpty())
                || (message != null && !message.isEmpty())
                || createdDate != null) {
            log.info("Listing contact messages with filters: username='{}', email='{}', subject='{}', message='{}', createdDate='{}'"
                    , username, email, subject, message, createdDate);
            return ResponseEntity.ok(contactService.getAllContactMessages(username, email, subject, message, createdDate, pageable));
        } else {
            log.info("Returning all contact messages without filters.");
            return ResponseEntity.ok(crudService.getAll(pageable));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContactMessageDTO> getContactMessageById(@PathVariable("id") UUID id) {
        log.info("Request to get contact message by id: {}", id);
        return ResponseEntity.ok(crudService.getById(id)
                .orElseThrow(() -> new NotFoundException("ContactMessage", id)));
    }

    @GetMapping("/username")
    public ResponseEntity<Page<ContactMessageDTO>> getContactMessagesByUsername(@RequestParam("username") String username, Pageable pageable) {
        log.info("Request to get contact messages by username: {}", username);
        return ResponseEntity.ok(contactService.getContactMessagesByUsername(username, pageable));
    }

    @GetMapping("/email")
    public ResponseEntity<Page<ContactMessageDTO>> getContactMessagesByEmail(@RequestParam("email") String email, Pageable pageable) {
        log.info("Request to get contact messages by email: {}", email);
        return ResponseEntity.ok(contactService.getContactMessagesByEmail(email, pageable));
    }

    @GetMapping("/subject")
    public ResponseEntity<Page<ContactMessageDTO>> getContactMessagesBySubject(@RequestParam("subject") String subject, Pageable pageable) {
        log.info("Request to get contact messages by subject: {}", subject);
        return ResponseEntity.ok(contactService.getContactMessagesBySubject(subject, pageable));
    }

    @GetMapping("/created-date")
    public ResponseEntity<Page<ContactMessageDTO>> getContactMessagesByCreatedDate(@RequestParam("createdDate") LocalDate createdDate, Pageable pageable) {
        log.info("Request to get contact messages by date: {}", createdDate);
        return ResponseEntity.ok(contactService.getContactMessagesByDate(createdDate, pageable));
    }

    @GetMapping("/date-range")
    public ResponseEntity<Page<ContactMessageDTO>> getContactMessagesByDateRange(@RequestParam("startDate") LocalDate startDate,
                                                                                 @RequestParam("endDate") LocalDate endDate,
                                                                                 Pageable pageable) {
        log.info("Request to get contact messages between: {} and {}", startDate, endDate);
        return ResponseEntity.ok(contactService.getContactMessagesByDateBetween(startDate, endDate, pageable));
    }

    @PostMapping
    public ResponseEntity<ContactMessageDTO> create(@Valid @RequestBody ContactMessageDTO contactMessageDTO, UriComponentsBuilder uriBuilder) {
        log.info("Request to create contact message: {}", contactMessageDTO);
        ContactMessageDTO created = crudService.save(contactMessageDTO);
        URI location = uriBuilder.path("/api/contact-messages/{id}").buildAndExpand(created.getId()).toUri(); // URI for Location header in 201 Created response, pointing to the newly created resource
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContactMessageDTO> update(@PathVariable("id") UUID id, @Valid @RequestBody ContactMessageDTO contactMessageDTO) {
        log.info("Request to update contact message with id {}: {}", id, contactMessageDTO);
        return ResponseEntity.ok(crudService.update(id, contactMessageDTO)
                .orElseThrow(() -> new NotFoundException("ContactMessage", id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        log.info("Request to delete contact message with id {}", id);
        boolean deleted = crudService.delete(id);
        if (!deleted) {
            throw new NotFoundException("ContactMessage", id);
        }
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ContactMessageDTO> patch(@PathVariable("id") UUID id, @Valid @RequestBody ContactMessageDTO contactMessageDTO) {
        log.info("Request to patch contact message with id {}: {}", id, contactMessageDTO);
        return ResponseEntity.ok(crudService.patch(id, contactMessageDTO)
                .orElseThrow(() -> new NotFoundException("ContactMessage", id)));
    }

}