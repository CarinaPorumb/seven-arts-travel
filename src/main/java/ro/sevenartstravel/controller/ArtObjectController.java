package ro.sevenartstravel.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ro.sevenartstravel.dto.ArtObjectDTO;
import ro.sevenartstravel.enums.ArtCategory;
import ro.sevenartstravel.enums.ArtObjectType;
import ro.sevenartstravel.exception.NotFoundException;
import ro.sevenartstravel.service.crud.CrudService;
import ro.sevenartstravel.service.entity.ArtObjectService;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/art-objects")
@RequiredArgsConstructor
public class ArtObjectController {

    private final ArtObjectService artObjectService;
    private final CrudService<ArtObjectDTO, UUID> crudService;

    @GetMapping
    public ResponseEntity<Page<ArtObjectDTO>> getArtObjects(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) ArtCategory artCategory,
            @RequestParam(required = false) Integer year,
            Pageable pageable) {

        if (search != null && !search.isEmpty()) {
            log.info("Searching art objects with keyword: {}", search);
            return ResponseEntity.ok(artObjectService.searchArtObject(search, pageable));
        } else if ((title != null && !title.isEmpty()) || year != null || (location != null && !location.isEmpty()) || artCategory != null) {
            log.info("Listing art objects with filters: title='{}', description='{}', location='{}', artCategory='{}', year='{}'",
                    title, description, location, artCategory, year);
            return ResponseEntity.ok(artObjectService.getAll(title, description, location, artCategory, year, pageable));
        } else {
            log.info("Returning all art objects without filters.");
            return ResponseEntity.ok(crudService.getAll(pageable));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArtObjectDTO> getArtObjectById(@PathVariable("id") UUID id) {
        log.info("Request to get art object by id: {}", id);
        return ResponseEntity.ok(crudService.getById(id)
                .orElseThrow(() -> new NotFoundException("ArtObject", id)));
    }

    @GetMapping("/title")
    public ResponseEntity<Page<ArtObjectDTO>> getArtObjectsByTitle(@RequestParam("title") String title, Pageable pageable) {
        log.info("Request to get art objects by title: {}", title);
        return ResponseEntity.ok(artObjectService.getByTitle(title, pageable));
    }

    @PostMapping
    public ResponseEntity<ArtObjectDTO> create(@Valid @RequestBody ArtObjectDTO artObjectDTO, UriComponentsBuilder uriBuilder) {
        log.info("Request to create art object: {}", artObjectDTO);
        ArtObjectDTO created = crudService.save(artObjectDTO);
        URI location = uriBuilder.path("/api/art-objects/{id}").buildAndExpand(created.getId()).toUri(); // URI for Location header in 201 Created response, pointing to the newly created resource

        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArtObjectDTO> update(@PathVariable("id") UUID id, @Valid @RequestBody ArtObjectDTO artObjectDTO) {
        log.info("Request to update art with id {}: {}", id, artObjectDTO);
        return ResponseEntity.ok(crudService.update(id, artObjectDTO)
                .orElseThrow(() -> new NotFoundException("ArtObject", id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        log.info("Request to delete art object with id {}", id);
        boolean deleted = crudService.delete(id);
        if (!deleted) {
            throw new NotFoundException("ArtObject", id);
        }
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ArtObjectDTO> patch(@PathVariable("id") UUID id, @Valid @RequestBody ArtObjectDTO artObjectDTO) {
        log.info("Request to patch art object with id {}: {}", id, artObjectDTO);
        return ResponseEntity.ok(crudService.patch(id, artObjectDTO)
                .orElseThrow(() -> new NotFoundException("ArtObject", id)));
    }

    @GetMapping("/location")
    public ResponseEntity<Page<ArtObjectDTO>> getArtObjectsByLocation(@RequestParam("location") String location, Pageable pageable) {
        log.info("Request to get art objects by location: {}", location);
        return ResponseEntity.ok(artObjectService.getByLocation(location, pageable));
    }

    @GetMapping("/category")
    public ResponseEntity<Page<ArtObjectDTO>> getArtObjectsByCategory(@RequestParam("category") ArtCategory category, Pageable pageable) {
        log.info("Request to get art objects by artCategory: {}", category);
        return ResponseEntity.ok(artObjectService.getByCategory(category, pageable));
    }

    @GetMapping("/year")
    public ResponseEntity<Page<ArtObjectDTO>> getArtObjectsByYear(@RequestParam("year") int year, Pageable pageable) {
        log.info("Request to get art objects by year: {}", Optional.of(year));
        return ResponseEntity.ok(artObjectService.getByYear(year, pageable));
    }

    @GetMapping("/year-range")
    public ResponseEntity<Page<ArtObjectDTO>> getArtObjectsByYearRange(@RequestParam("startYear") int startYear, @RequestParam("endYear") int endYear, Pageable pageable) {
        log.info("Request to get art objects by year range: {} to {}", Optional.of(startYear), Optional.of(endYear));
        return ResponseEntity.ok(artObjectService.getByYearRange(startYear, endYear, pageable));
    }

    @GetMapping("/type")
    public ResponseEntity<Page<ArtObjectDTO>> getByArtObjectType(@RequestParam("artObjectType") ArtObjectType artObjectType, Pageable pageable) {
       log.info("Request to get art objects by artObjectType: {}", artObjectType);
        return ResponseEntity.ok(artObjectService.getByArtObjectType(artObjectType, pageable));
    }

}