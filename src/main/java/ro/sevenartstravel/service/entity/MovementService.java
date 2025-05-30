package ro.sevenartstravel.service.entity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ro.sevenartstravel.dto.MovementDTO;

public interface MovementService {

    Page<MovementDTO> getAllMovements(String name, String description, Integer startYear, Integer endYear, String originRegion, Pageable pageable);

    Page<MovementDTO> getMovementsByName(String name,Pageable pageable);

    Page<MovementDTO> getMovementsByStartYear(Integer startYear, Pageable pageable);

    Page<MovementDTO> getMovementsByEndYear(Integer endYear, Pageable pageable);

    Page<MovementDTO> getMovementsBetweenYears(Integer startYear, Integer endYear, Pageable pageable);

    Page<MovementDTO> getMovementsByOriginRegion(String originRegion, Pageable pageable);

    Page<MovementDTO> searchMovements(String keyword, Pageable pageable);

}