package ro.sevenartstravel.service.entity.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.sevenartstravel.dto.MovementDTO;
import ro.sevenartstravel.entity.Movement;
import ro.sevenartstravel.mapper.MovementMapper;
import ro.sevenartstravel.repository.MovementRepository;
import ro.sevenartstravel.service.crud.CrudServiceImpl;
import ro.sevenartstravel.service.entity.MovementService;
import ro.sevenartstravel.service.util.SpecificationUtils;

import java.util.UUID;

@Slf4j
@Service
public class MovementServiceImpl extends CrudServiceImpl<MovementDTO, Movement, UUID> implements MovementService {

    private final MovementRepository movementRepository;
    private final MovementMapper movementMapper;

    public MovementServiceImpl(MovementRepository movementRepository, MovementMapper movementMapper) {
        super(movementRepository, movementMapper);
        this.movementRepository = movementRepository;
        this.movementMapper = movementMapper;
    }

    private Specification<Movement> buildSpecification(String name, String description, Integer startYear, Integer endYear, String originRegion) {
        return Specification.where(SpecificationUtils.<Movement>attributeLike("name", name))
                .and(SpecificationUtils.attributeLike("description", description))
                .and(SpecificationUtils.attributeEquals("startYear", startYear))
                .and(SpecificationUtils.attributeEquals("endYear", endYear))
                .and(SpecificationUtils.attributeEquals("originRegion", originRegion));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MovementDTO> getAllMovements(String name, String description, Integer startYear, Integer endYear, String originRegion, Pageable pageable) {
        Specification<Movement> specification = buildSpecification(name, description, startYear, endYear, originRegion);
        log.debug("Listing movements with filters - Name: {}, Description: {}, Start year {}, End year: {}, Origin Region: {}, ", name, description, startYear, endYear, originRegion);
        return movementRepository.findAll(specification, pageable)
                .map(movementMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MovementDTO> getMovementsByName(String name, Pageable pageable) {
        log.debug("Fetching movements by name: {}", name);
        return movementRepository.findByNameContainingIgnoreCase(name, pageable)
                .map(movementMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MovementDTO> getMovementsByStartYear(Integer startYear, Pageable pageable) {
        log.debug("Fetching movements by startYear: {}", startYear);
        return movementRepository.findByStartYear(startYear, pageable)
                .map(movementMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MovementDTO> getMovementsByEndYear(Integer endYear, Pageable pageable) {
        log.debug("Fetching movements by endYear: {}", endYear);
        return movementRepository.findByEndYear(endYear, pageable)
                .map(movementMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MovementDTO> getMovementsBetweenYears(Integer startYear, Integer endYear, Pageable pageable) {
        log.debug("Fetching movements by year between: {} - {}", startYear, endYear);
        return movementRepository.findByEndYearBetween(startYear, endYear, pageable)
                .map(movementMapper::toDTO);
    }

    @Override
    public Page<MovementDTO> getMovementsByOriginRegion(String originRegion, Pageable pageable) {
        log.debug("Fetching movements by originRegion: {}", originRegion);
        return movementRepository.findByOriginRegionContainingIgnoreCase(originRegion, pageable)
                .map(movementMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MovementDTO> searchMovements(String keyword, Pageable pageable) {
        log.debug("Searching movements with keyword: {}", keyword);
        return movementRepository.searchMovements(keyword, pageable)
                .map(movementMapper::toDTO);
    }

}