package ro.project.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.project.exception.NotFoundException;
import ro.project.model.ContactDTO;
import ro.project.service.crud.CrudService;
import ro.project.service.entity.ContactService;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/contacts")
@RequiredArgsConstructor
public class ContactController {

    private final ContactService contactService;
    private final CrudService<ContactDTO, UUID> crudService;

    @GetMapping
    public ResponseEntity<Page<ContactDTO>> getAllContacts(
            @RequestParam(required = false, defaultValue = "") String search,
            @RequestParam(required = false, defaultValue = "") String username,
            @RequestParam(required = false, defaultValue = "") String email,
            @RequestParam(required = false, defaultValue = "") String subject,
            @RequestParam(required = false, defaultValue = "") String message,
            @RequestParam(required = false) LocalDate date,
            @RequestParam(required = false, defaultValue = "0") Integer pageNumber,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize
    ) {
        log.info("Request to list or search artists");
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("username").ascending());

        if (search != null && !search.isEmpty()) {
            return ResponseEntity.ok(contactService.searchContacts(search, pageable));
        } else if ((username != null && !username.isEmpty())
                || (email != null && !email.isEmpty())
                || (subject != null && !subject.isEmpty())
                || (message != null && !message.isEmpty())
                || date != null) {
            return ResponseEntity.ok(contactService.getAllContacts(username, email, subject, message, date, pageNumber, pageSize));
        } else {
            return ResponseEntity.ok(crudService.getAll(pageable));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContactDTO> getById(@PathVariable("id") UUID id) {
        log.info("Request to get contact by id: {}", id);
        return ResponseEntity.ok(crudService.getById(id)
                .orElseThrow(() -> new NotFoundException("Contact with id " + id + " not found")));
    }

    @GetMapping("/name")
    public ResponseEntity<List<ContactDTO>> getAllByUsername(@RequestParam("username") String username) {
        log.info("Request to get contacts by username: {}", username);
        return ResponseEntity.ok(contactService.getAllContactsByUsername(username));
    }

    @GetMapping("/email")
    public ResponseEntity<List<ContactDTO>> getAllByEmail(@RequestParam("email") String email) {
        log.info("Request to get contacts by email: {}", email);
        return ResponseEntity.ok(contactService.getAllContactsByEmail(email));
    }

    @GetMapping("/subject")
    public ResponseEntity<List<ContactDTO>> getAllBySubject(@RequestParam("subject") String subject) {
        log.info("Request to get contacts by subject: {}", subject);
        return ResponseEntity.ok(contactService.getAllContactsBySubject(subject));
    }

    @GetMapping("/message")
    public ResponseEntity<List<ContactDTO>> getAllByMessage(@RequestParam("message") String message) {
        log.info("Request to get contacts by message: {}", message);
        return ResponseEntity.ok(contactService.getAllContactsByMessage(message));
    }

    @GetMapping("/date")
    public ResponseEntity<List<ContactDTO>> getAllByDate(@RequestParam("date") LocalDate date) {
        log.info("Request to get contacts by date: {}", date);
        return ResponseEntity.ok(contactService.getAllContactsByDate(date));
    }

    @PostMapping
    public ResponseEntity<ContactDTO> create(@Valid @RequestBody ContactDTO contactDTO) {
        log.info("Request to create contact: {}", contactDTO);
        ContactDTO createdContact = crudService.save(contactDTO);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add(HttpHeaders.LOCATION, "/contacts/" + createdContact.getId().toString());
        return new ResponseEntity<>(createdContact, responseHeaders, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContactDTO> update(@PathVariable("id") UUID id, @Valid @RequestBody ContactDTO contactDTO) {

        log.info("Request to update contact: {}", contactDTO);
        return ResponseEntity.ok(crudService.update(id, contactDTO)
                .orElseThrow(() -> new NotFoundException("Contact with id " + id + " not found")));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") UUID id) {
        log.info("Request to delete contact by id: {}", id);
        boolean deleted = crudService.delete(id);
        if (!deleted) {
            throw new NotFoundException("Contact with id " + id + " not found");
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ContactDTO> patch(@PathVariable("id") UUID id, @Valid @RequestBody ContactDTO contactDTO) {
        log.info("Request to patch artist: {}", contactDTO);
        return ResponseEntity.ok(crudService.patch(id, contactDTO)
                .orElseThrow(() -> new NotFoundException("Contact with id " + id + " not found")));
    }

}