package ro.project.service.entity.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.project.entity.Movement;
import ro.project.model.MovementDTO;
import ro.project.model.mapper.MovementMapper;
import ro.project.repository.MovementRepository;
import ro.project.service.crud.CrudServiceImpl;
import ro.project.service.entity.MovementService;
import ro.project.service.util.SpecificationUtils;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class MovementServiceImpl extends CrudServiceImpl<MovementDTO, Movement, UUID> implements MovementService {

    private final MovementRepository movementRepository;
    private final MovementMapper movementMapper;

    public MovementServiceImpl(MovementRepository movementRepository, MovementMapper movementMapper) {
        super(movementRepository);
        this.movementRepository = movementRepository;
        this.movementMapper = movementMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MovementDTO> getAllMovements(String name, String description, Integer startYear, Integer endYear, Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("name").ascending());

        Specification<Movement> specification = Specification.where(SpecificationUtils.<Movement>attributeLike("name", name))
                .and(SpecificationUtils.attributeLike("description", description))
                .and(SpecificationUtils.attributeEquals(String.valueOf(startYear), startYear))
                .and(SpecificationUtils.attributeEquals(String.valueOf(endYear), endYear));

        log.debug("Listing movements with filters - Name: {}, Description: {}, Start year {}, End year: {},", name, description, startYear, endYear);
        return movementRepository.findAll(specification, pageable).map(movementMapper::toDTO);
    }


    @Override
    @Transactional(readOnly = true)
    public List<MovementDTO> getMovementsByName(String name) {
        log.debug("Attempting to get movement by name: {}", name);
        return movementRepository.findByNameContainingIgnoreCase(name).stream()
                .map(movementMapper::toDTO).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<MovementDTO> getMovementsByStartYear(Integer startYear) {
        log.debug("Attempting to get movement by startYear: {}", startYear);
        return movementRepository.findByStartYear(startYear).stream()
                .map(movementMapper::toDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<MovementDTO> getMovementsByEndYear(Integer endYear) {
        log.debug("Attempting to get movement by endYear: {}", endYear);
        return movementRepository.findByEndYear(endYear).stream()
                .map(movementMapper::toDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<MovementDTO> getMovementsByYearBetween(Integer startYear, Integer endYear) {
        log.debug("Attempting to get movement by year between: {} - {}", startYear, endYear);
        return movementRepository.findByEndYearBetween(startYear, endYear).stream()
                .map(movementMapper::toDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MovementDTO> searchMovements(String keyword, Pageable pageable) {
        log.debug("Searching movement by keyword {}", keyword);
        return movementRepository.searchMovements(keyword, pageable).map(movementMapper::toDTO);
    }

    @Override
    protected Movement toEntity(MovementDTO dto) {
        return movementMapper.toEntity(dto);
    }

    @Override
    protected MovementDTO toDTO(Movement entity) {
        return movementMapper.toDTO(entity);
    }

    @Override
    protected void updateEntity(Movement entity, MovementDTO dto) {
        movementMapper.updateEntity(entity, dto);
    }

    @Override
    protected void patchEntity(Movement entity, MovementDTO dto) {
        movementMapper.patchEntity(entity, dto);
    }

}