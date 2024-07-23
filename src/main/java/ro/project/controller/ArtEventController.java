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
import ro.project.enums.Category;
import ro.project.enums.Status;
import ro.project.exception.NotFoundException;
import ro.project.model.ArtEventDTO;
import ro.project.service.crud.CrudService;
import ro.project.service.entity.ArtEventService;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(value = "/api/artevents")
@RequiredArgsConstructor
public class ArtEventController {

    private final ArtEventService artEventService;
    private final CrudService<ArtEventDTO, UUID> crudService;

    @GetMapping
    public ResponseEntity<Page<ArtEventDTO>> getArtEventList(
            @RequestParam(required = false, defaultValue = "") String search,
            @RequestParam(required = false, defaultValue = "") String name,
            @RequestParam(required = false, defaultValue = "") String location,
            @RequestParam(required = false) Category category,
            @RequestParam(required = false) Status status,
            @RequestParam(required = false, defaultValue = "0") Integer pageNumber,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        log.info("Request to list or search art events");

        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("name").ascending());

        if (search != null && !search.isEmpty()) {
            return ResponseEntity.ok(artEventService.search(search, pageable));
        } else if ((name != null && !name.isEmpty()) || (location != null && !location.isEmpty()) || category != null || status != null) {
            return ResponseEntity.ok(artEventService.getAllArtEvents(name, location, category, status, pageNumber, pageSize));
        } else {
            return ResponseEntity.ok(crudService.getAll(pageable));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArtEventDTO> getById(@PathVariable("id") UUID id) {
        log.info("Request to get art event by id: {}", id);
        return ResponseEntity.ok(crudService.getById(id)
                .orElseThrow(() -> new NotFoundException("Art event with id " + id + " not found")));
    }

    @GetMapping("/name")
    public ResponseEntity<List<ArtEventDTO>> getByName(@RequestParam("name") String name) {
        log.info("Request to get art events by name: {}", name);
        return ResponseEntity.ok(artEventService.getByName(name));
    }

    @PostMapping
    public ResponseEntity<ArtEventDTO> create(@Valid @RequestBody ArtEventDTO artEventDTO) {
        log.info("Request to create art event: {}", artEventDTO);
        ArtEventDTO createdArtEvent = crudService.save(artEventDTO);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.LOCATION, "/artevents/" + createdArtEvent.getId().toString());
        return new ResponseEntity<>(createdArtEvent, headers, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArtEventDTO> update(@PathVariable("id") UUID id, @Valid @RequestBody ArtEventDTO artEventDTO) {
        log.info("Request to update art event: {}", artEventDTO);
        return ResponseEntity.ok(crudService.update(id, artEventDTO)
                .orElseThrow(() -> new NotFoundException("Art event with id " + id + " not found")));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") UUID id) {
        log.info("Request to delete art event: {}", id);
        boolean deleted = crudService.delete(id);

        if (!deleted) {
            throw new NotFoundException("Art event with id " + id + " not found");
        }
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ArtEventDTO> patch(@PathVariable("id") UUID id, @RequestBody ArtEventDTO artEventDTO) {
        log.info("Request to patch art event: {}", artEventDTO);
        return ResponseEntity.ok(crudService.patch(id, artEventDTO)
                .orElseThrow(() -> new NotFoundException("Art event with id " + id + " not found")));
    }

    @GetMapping("/display")
    public ResponseEntity<List<ArtEventDTO>> displayByCategory(@RequestParam("category") Category category) {
        log.info("Request to display art events by category: {}", category);
        return ResponseEntity.ok(artEventService.displayByCategory(category));
    }

}