package ro.sevenartstravel.service.entity.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.sevenartstravel.dto.ContactMessageDTO;
import ro.sevenartstravel.entity.ContactMessage;
import ro.sevenartstravel.mapper.ContactMessageMapper;
import ro.sevenartstravel.repository.ContactMessageRepository;
import ro.sevenartstravel.service.crud.CrudServiceImpl;
import ro.sevenartstravel.service.entity.ContactMessageService;
import ro.sevenartstravel.service.util.SpecificationUtils;

import java.time.LocalDate;
import java.util.UUID;

@Slf4j
@Service
public class ContactMessageServiceImpl extends CrudServiceImpl<ContactMessageDTO, ContactMessage, UUID> implements ContactMessageService {

    private final ContactMessageRepository contactMessageRepository;
    private final ContactMessageMapper contactMessageMapper;

    public ContactMessageServiceImpl(ContactMessageRepository contactMessageRepository, ContactMessageMapper contactMessageMapper) {
        super(contactMessageRepository, contactMessageMapper);
        this.contactMessageRepository = contactMessageRepository;
        this.contactMessageMapper = contactMessageMapper;
    }

    private Specification<ContactMessage> buildSpecification(String username, String email, String subject, String message, LocalDate createdDate) {
        return Specification.where(SpecificationUtils.<ContactMessage>attributeLike("username", username))
                .and(SpecificationUtils.attributeLike("email", email))
                .and(SpecificationUtils.attributeLike("subject", subject))
                .and(SpecificationUtils.attributeLike("message", message))
                .and(SpecificationUtils.attributeEquals("createdDate", createdDate));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ContactMessageDTO> getAllContactMessages(String username, String email, String subject, String message, LocalDate createdDate, Pageable pageable) {
        Specification<ContactMessage> specification = buildSpecification(username, email, subject, message, createdDate);
        log.debug("Listing all contact messages  with filters - Username: {}, Email: {}, Subject {}, Message: {}, Date: {},", username, email, subject, message, createdDate);
        return contactMessageRepository.findAll(specification, pageable).map(contactMessageMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ContactMessageDTO> getContactMessagesByUsername(String username, Pageable pageable) {
        log.debug("Fetching contact messages by username: {}", username);
        return contactMessageRepository.findByUsernameIgnoreCase(username, pageable)
                .map(contactMessageMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ContactMessageDTO> getContactMessagesByEmail(String email, Pageable pageable) {
        log.debug("Fetching contact messages by email: {}", email);
        return contactMessageRepository.findByEmailContainsIgnoreCase(email, pageable)
                .map(contactMessageMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ContactMessageDTO> getContactMessagesBySubject(String subject, Pageable pageable) {
        log.debug("Fetching contact messages by subject: {}", subject);
        return contactMessageRepository.findBySubjectContainsIgnoreCase(subject, pageable)
                .map(contactMessageMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ContactMessageDTO> getContactMessagesByDate(LocalDate createdDate, Pageable pageable) {
        log.debug("Fetching contact messages by created date: {}", createdDate);
        return contactMessageRepository.findByCreatedDate(createdDate, pageable)
                .map(contactMessageMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ContactMessageDTO> getContactMessagesByDateBetween(LocalDate startDate, LocalDate endDate, Pageable pageable) {
        log.debug("Fetching contact messages by between dates: {} - {}", startDate, endDate);
        return contactMessageRepository.findByCreatedDateBetween(startDate, endDate, pageable)
                .map(contactMessageMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ContactMessageDTO> searchContactMessages(String keyword, Pageable pageable) {
        log.debug("Searching contact messages with keyword: {}", keyword);
        return contactMessageRepository.searchContact(keyword, pageable)
                .map(contactMessageMapper::toDTO);
    }

}