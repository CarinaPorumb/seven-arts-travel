package ro.project.service.crud;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ro.project.model.mapper.GenericMapper;

import java.util.Optional;

@Slf4j
public abstract class CrudServiceImpl<D, E, ID> implements CrudService<D, ID> {

    private final JpaRepository<E, ID> repository;
    private final GenericMapper<D, E> mapper;
    private final String entityName;

    public CrudServiceImpl(JpaRepository<E, ID> repository, GenericMapper<D, E> mapper) {
        this.repository = repository;
        this.mapper = mapper;
        this.entityName = repository.getClass().getSimpleName();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<D> getAll(Pageable pageable) {
        log.debug("Retrieving all {} entities", entityName);
        return repository.findAll(pageable).map(mapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<D> getById(ID id) {
        log.debug("Retrieving {} entity with ID: {}", entityName, id);
        return repository.findById(id).map(mapper::toDTO);
    }

    @Override
    @Transactional
    public D save(D dto) {
        log.debug("Saving new {} entity: {}", entityName, dto);
        E entity = mapper.toEntity(dto);
        E savedEntity = repository.save(entity);
        log.debug("Successfully saved {} entity: {}", entityName, savedEntity);
        return mapper.toDTO(savedEntity);
    }

    @Override
    @Transactional
    public Optional<D> update(ID id, D dto) {
        log.debug("Updating {} entity with ID: {}", entityName, id);
        return repository.findById(id)
                .map(existingEntity -> {
                    mapper.updateEntity(existingEntity, dto);
                    E savedEntity = repository.save(existingEntity);
                    log.debug("Successfully updated {} entity: {}", entityName, savedEntity);

                    return mapper.toDTO(savedEntity);
                });
    }

    @Override
    public boolean delete(ID id) {
        log.debug("Attempting to delete {} entity with ID: {}", entityName, id);
        return repository.findById(id)
                .map(entity -> {
                    repository.delete(entity);
                    log.debug("Successfully deleted {} entity: {}", entityName, entity);
                    return true;
                }).orElse(false);
    }

    @Override
    @Transactional
    public Optional<D> patch(ID id, D dto) {
        log.debug("Patching {} entity with ID: {}", entityName, id);
        return repository.findById(id)
                .map(existingEntity -> {
                    mapper.patchEntity(existingEntity, dto);
                    repository.save(existingEntity);
                    log.debug("Successfully patched {} entity: {}", entityName, existingEntity);
                    return mapper.toDTO(existingEntity);
                });
    }

}