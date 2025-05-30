package ro.sevenartstravel.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ro.sevenartstravel.dto.MovementDTO;
import ro.sevenartstravel.exception.NotFoundException;
import ro.sevenartstravel.service.crud.CrudService;
import ro.sevenartstravel.service.entity.MovementService;

import java.net.URI;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/movements")
@RequiredArgsConstructor
public class MovementController {

    private final MovementService movementService;
    private final CrudService<MovementDTO, UUID> crudService;

    @GetMapping
    public ResponseEntity<Page<MovementDTO>> getMovements(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) Integer startYear,
            @RequestParam(required = false) Integer endYear,
            @RequestParam(required = false) String originRegion,
            Pageable pageable) {

        if (search != null && !search.isEmpty()) {
            log.info("Searching movements with keyword: {}", search);
            return ResponseEntity.ok(movementService.searchMovements(search, pageable));
        } else if ((name != null && !name.isEmpty()) || (description != null && !description.isEmpty())
                || startYear != null || endYear != null || (originRegion != null && !originRegion.isEmpty())) {
            log.info("Listing movements with filters: name='{}', description='{}', startYear='{}', endYear='{}', originRegion='{}'",
                    name, description, startYear, endYear, originRegion);
            return ResponseEntity.ok(movementService.getAllMovements(name, description, startYear, endYear, originRegion, pageable));
        } else {
            log.info("Returning all movements without filters.");
            return ResponseEntity.ok(crudService.getAll(pageable));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovementDTO> getMovementById(@PathVariable("id") UUID id) {
        log.info("Request to get movement by id: {}", id);
        return ResponseEntity.ok(crudService.getById(id)
                .orElseThrow(() -> new NotFoundException("Movement", id)));
    }

    @GetMapping("/name")
    public ResponseEntity<Page<MovementDTO>> getMovementsByName(@RequestParam("name") String name, Pageable pageable) {
        log.info("Request to get movements by name: {}", name);
        return ResponseEntity.ok(movementService.getMovementsByName(name, pageable));
    }

    @GetMapping("/start-year")
    public ResponseEntity<Page<MovementDTO>> getMovementsByStartYear(@RequestParam("startYear") int startYear, Pageable pageable) {
        log.info("Request to get movements by start year: {}", startYear);
        return ResponseEntity.ok(movementService.getMovementsByStartYear(startYear, pageable));
    }

    @GetMapping("/end-year")
    public ResponseEntity<Page<MovementDTO>> getMovementsByEndYear(@RequestParam("endYear") int endYear, Pageable pageable) {
        log.info("Request to get movements by end year: {}", endYear);
        return ResponseEntity.ok(movementService.getMovementsByEndYear(endYear, pageable));
    }

    @GetMapping("/year-range")
    public ResponseEntity<Page<MovementDTO>> getMovementsByYearRange(@RequestParam("startYear") int startYear, @RequestParam("endYear") int endYear, Pageable pageable) {
        log.info("Request to get movements between years: {} - {}", startYear, endYear);
        return ResponseEntity.ok(movementService.getMovementsBetweenYears(startYear, endYear, pageable));
    }

    @GetMapping("/origin-region")
    public ResponseEntity<Page<MovementDTO>> getMovementsByOriginRegion(@RequestParam("originRegion") String originRegion, Pageable pageable) {
        log.info("Request to get movements by origin region: {}", originRegion);
        return ResponseEntity.ok(movementService.getMovementsByOriginRegion(originRegion, pageable));
    }

    @PostMapping
    public ResponseEntity<MovementDTO> create(@Valid @RequestBody MovementDTO movementDTO, UriComponentsBuilder uriBuilder) {
        log.info("Request to create movement: {}", movementDTO);
        MovementDTO created = crudService.save(movementDTO);
        URI location = uriBuilder.path("/api/movements/{id}").buildAndExpand(created.getId()).toUri(); // URI for Location header in 201 Created response, pointing to the newly created resource
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovementDTO> update(@PathVariable("id") UUID id, @Valid @RequestBody MovementDTO movementDTO) {
        log.info("Request to update movement with id {}: {}", id, movementDTO);
        return ResponseEntity.ok(crudService.update(id, movementDTO)
                .orElseThrow(() -> new NotFoundException("Movement", id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        log.info("Request to delete movement with id {}", id);
        boolean deleted = crudService.delete(id);
        if (!deleted) {
            throw new NotFoundException("Movement", id);
        }
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<MovementDTO> patch(@PathVariable("id") UUID id, @Valid @RequestBody MovementDTO movementDTO) {
        log.info("Request to patch movement with id {}: {}", id, movementDTO);
        return ResponseEntity.ok(crudService.patch(id, movementDTO)
                .orElseThrow(() -> new NotFoundException("Movement", id)));
    }


}