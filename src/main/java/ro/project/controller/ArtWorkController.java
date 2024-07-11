package ro.project.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ro.project.enums.Category;
import ro.project.exception.NotFoundException;
import ro.project.model.ArtWorkDTO;
import ro.project.service.crud.CrudService;
import ro.project.service.entity.ArtWorkService;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/artworks")
@RequiredArgsConstructor
public class ArtWorkController {

    private final ArtWorkService artWorkService;
    private final CrudService<ArtWorkDTO, UUID> crudService;

    @GetMapping
    public ResponseEntity<Page<ArtWorkDTO>> getAll(
            @RequestParam(required = false, defaultValue = "") String search,
            @RequestParam(required = false, defaultValue = "") String name,
            @RequestParam(required = false, defaultValue = "") Integer year,
            @RequestParam(required = false, defaultValue = "") String location,
            @RequestParam(required = false, defaultValue = "") Category category,
            @RequestParam(required = false, defaultValue = "0") Integer pageNumber,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        log.info("Request to list or search art works");

        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("name").ascending());

        if (search != null && !search.isEmpty()) {
            return ResponseEntity.ok(artWorkService.search(search, pageable));
        } else if (name != null || year != null || location != null || category != null) {
            return ResponseEntity.ok(artWorkService.getAllArtWorks(name, year, location, category, pageNumber, pageSize));
        } else {
            return ResponseEntity.ok(crudService.getAll(pageable));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArtWorkDTO> getById(@PathVariable("id") UUID id) {
        log.info("Request to get art work by id: {}", id);
        return ResponseEntity.ok(crudService.getById(id)
                .orElseThrow(() -> new NotFoundException("Art work with id " + id + " not found")));
    }

    @GetMapping("/name")
    public ResponseEntity<List<ArtWorkDTO>> getByName(@RequestParam("name") String name) {
        log.info("Request to get art works by name: {}", name);
        return ResponseEntity.ok(artWorkService.getByName(name));
    }

    @PostMapping
    public ResponseEntity<ArtWorkDTO> create(@Validated @RequestBody ArtWorkDTO artWorkDTO) {
        log.info("Request to create artwork: {}", artWorkDTO);
        ArtWorkDTO createdArtWork = crudService.save(artWorkDTO);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add(HttpHeaders.LOCATION, "/artworks/" + createdArtWork.getId().toString());
        return new ResponseEntity<>(createdArtWork, responseHeaders, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArtWorkDTO> update(@PathVariable("id") UUID id, @Validated @RequestBody ArtWorkDTO artWorkDTO) {
        log.info("Request to update artwork: {}", artWorkDTO);
        return ResponseEntity.ok(crudService.update(id, artWorkDTO)
                .orElseThrow(() -> new NotFoundException("Art work with id " + id + " not found")));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") UUID id) {
        log.info("Request to delete artwork: {}", id);
        boolean deleted = crudService.delete(id);
        if (!deleted) {
            throw new NotFoundException("Art work with id " + id + " not found");
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ArtWorkDTO> patch(@PathVariable("id") UUID id, @Validated @RequestBody ArtWorkDTO artWorkDTO) {
        log.info("Request to patch artwork: {}", artWorkDTO);
        return ResponseEntity.ok(crudService.patch(id, artWorkDTO)
                .orElseThrow(() -> new NotFoundException("Art work with id " + id + " not found")));

    }

    @GetMapping("/display")
    public ResponseEntity<List<ArtWorkDTO>> displayByCategory(@RequestParam("category") Category category) {
        log.info("Request to display art works by category: {}", category);
        return ResponseEntity.ok(artWorkService.displayByCategory(category));
    }

}