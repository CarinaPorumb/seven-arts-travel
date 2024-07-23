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
import ro.project.model.MovementDTO;
import ro.project.service.crud.CrudService;
import ro.project.service.entity.MovementService;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/movements")
@RequiredArgsConstructor
public class MovementController {

    private final MovementService movementService;
    private final CrudService<MovementDTO, UUID> crudService;

    @GetMapping
    public ResponseEntity<Page<MovementDTO>> getAllMovements(
            @RequestParam(required = false, defaultValue = "") String search,
            @RequestParam(required = false, defaultValue = "") String name,
            @RequestParam(required = false, defaultValue = "") String description,
            @RequestParam(required = false) Integer startYear,
            @RequestParam(required = false) Integer endYear,
            @RequestParam(required = false, defaultValue = "0") Integer pageNumber,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize
    ) {
        log.info("Request to list or search movements");

        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("name").ascending());

        if (search != null && !search.isEmpty()) {
            return ResponseEntity.ok(movementService.searchMovements(search, pageable));
        } else if ((name != null && !name.isEmpty()) || (description != null && !description.isEmpty())
                || startYear != null || endYear != null) {
            return ResponseEntity.ok(movementService.getAllMovements(name, description, startYear, endYear, pageNumber, pageSize));
        } else {
            return ResponseEntity.ok(crudService.getAll(pageable));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovementDTO> getById(@PathVariable("id") UUID id) {
        log.info("Request to get movement by id: {}", id);
        return ResponseEntity.ok(crudService.getById(id)
                .orElseThrow(() -> new NotFoundException("Movement with id " + id + " not found")));
    }

    @GetMapping("/name")
    public ResponseEntity<List<MovementDTO>> getByName(@RequestParam("name") String name) {
        log.info("Request to get movement by name: {}", name);
        return ResponseEntity.ok(movementService.getMovementsByName(name));
    }

    @GetMapping("/year")
    public ResponseEntity<List<MovementDTO>> getByYear(
            @RequestParam(required = false) Integer startYear,
            @RequestParam(required = false) Integer endYear
    ) {
        log.info("Request to get movement by year.");

        if (startYear != null && endYear != null) {
            return ResponseEntity.ok(movementService.getMovementsByYearBetween(startYear, endYear));
        } else if (startYear != null) {
            return ResponseEntity.ok(movementService.getMovementsByStartYear(startYear));
        } else if (endYear != null) {
            return ResponseEntity.ok(movementService.getMovementsByEndYear(endYear));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping
    public ResponseEntity<MovementDTO> create(@Valid @RequestBody MovementDTO movementDTO) {
        log.info("Request to create movement: {}", movementDTO);
        MovementDTO createdMovement = crudService.save(movementDTO);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add(HttpHeaders.LOCATION, "/movements/" + createdMovement.getId().toString());
        return new ResponseEntity<>(createdMovement, responseHeaders, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovementDTO> update(@PathVariable("id") UUID id, @Valid @RequestBody MovementDTO movementDTO) {
        log.info("Request to update movement: {}", movementDTO);
        return ResponseEntity.ok(crudService.update(id, movementDTO)
                .orElseThrow(() -> new NotFoundException("Movement with id " + id + " not found")));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") UUID id) {
        log.info("Request to delete movement: {}", id);
        boolean deleted = crudService.delete(id);
        if (!deleted) {
            throw new NotFoundException("Movement with id " + id + " not found");
        }
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<MovementDTO> patch(@PathVariable("id") UUID id, @Valid @RequestBody MovementDTO movementDTO) {
        log.info("Request to patch movement: {}", movementDTO);
        return ResponseEntity.ok(crudService.patch(id, movementDTO)
                .orElseThrow(() -> new NotFoundException("Movement with id " + id + " not found")));

    }

}