package ro.project.service.entity.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.project.entity.Contact;
import ro.project.model.ContactDTO;
import ro.project.model.mapper.ContactMapper;
import ro.project.repository.ContactRepository;
import ro.project.service.crud.CrudServiceImpl;
import ro.project.service.entity.ContactService;
import ro.project.service.util.SpecificationUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class ContactServiceImpl extends CrudServiceImpl<ContactDTO, Contact, UUID> implements ContactService {

    private final ContactRepository contactRepository;
    private final ContactMapper contactMapper;

    public ContactServiceImpl(ContactRepository contactRepository, ContactMapper contactMapper) {
        super(contactRepository);
        this.contactRepository = contactRepository;
        this.contactMapper = contactMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ContactDTO> getAllContacts(String username, String email, String subject, String message, LocalDate date, Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("name").ascending());

        Specification<Contact> specification = Specification.where(SpecificationUtils.<Contact>attributeLike("username", username))
                .and(SpecificationUtils.attributeLike("email", email))
                .and(SpecificationUtils.attributeLike("subject", subject))
                .and(SpecificationUtils.attributeLike("message", message))
                .and(SpecificationUtils.attributeEquals("date", String.valueOf(date)));

        log.debug("Listing all contacts with filters - Username: {}, Email: {}, Subject {}, Message: {}, Date: {},", username, email, subject, message, date);
        return contactRepository.findAll(specification, pageable).map(contactMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContactDTO> getAllContactsByUsername(String username) {
        log.debug("Attempting to get all contacts by username: {}", username);
        return contactRepository.findByUsernameContainsIgnoreCase(username).stream()
                .map(contactMapper::toDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContactDTO> getAllContactsByEmail(String email) {
        log.debug("Attempting to get all contacts by email: {}", email);
        return contactRepository.findByEmailContainsIgnoreCase(email).stream()
                .map(contactMapper::toDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContactDTO> getAllContactsBySubject(String subject) {
        log.debug("Attempting to get all contacts by subject: {}", subject);
        return contactRepository.findBySubjectContainsIgnoreCase(subject).stream()
                .map(contactMapper::toDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContactDTO> getAllContactsByMessage(String message) {
        log.debug("Attempting to get all contacts by message: {}", message);
        return contactRepository.findByMessageContainsIgnoreCase(message).stream()
                .map(contactMapper::toDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContactDTO> getAllContactsByDate(LocalDate date) {
        log.debug("Attempting to get all contacts by date: {}", date);
        return contactRepository.findByDate(date).stream()
                .map(contactMapper::toDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ContactDTO> searchContacts(String keyword, Pageable pageable) {
        log.debug("Searching contacts by keyword: {}", keyword);
        return contactRepository.searchContact(keyword, pageable)
                .map(contactMapper::toDTO);
    }

    @Override
    protected Contact toEntity(ContactDTO dto) {
        return contactMapper.toEntity(dto);
    }

    @Override
    protected ContactDTO toDTO(Contact entity) {
        return contactMapper.toDTO(entity);
    }

    @Override
    protected void updateEntity(Contact entity, ContactDTO dto) {
        contactMapper.updateEntity(entity, dto);
    }

    @Override
    protected void patchEntity(Contact entity, ContactDTO dto) {
        contactMapper.patchEntity(entity, dto);
    }

}