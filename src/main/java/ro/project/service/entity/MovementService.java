package ro.project.service.entity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ro.project.model.MovementDTO;

import java.util.List;

public interface MovementService {

    Page<MovementDTO> getAllMovements(String name, String description, Integer startYear, Integer endYear, Integer pageNumber, Integer pageSize);

    List<MovementDTO> getMovementsByName(String name);

    List<MovementDTO> getMovementsByStartYear(Integer startYear);

    List<MovementDTO> getMovementsByEndYear(Integer endYear);

    List<MovementDTO> getMovementsByYearBetween(Integer startYear, Integer endYear);

    Page<MovementDTO> searchMovements(String keyword, Pageable pageable);

}