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
import ro.project.model.ArtistDTO;
import ro.project.service.crud.CrudService;
import ro.project.service.entity.ArtistService;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/artists")
@RequiredArgsConstructor
public class ArtistController {

    private final ArtistService artistService;
    private final CrudService<ArtistDTO, UUID> crudService;

    @GetMapping
    public ResponseEntity<Page<ArtistDTO>> getAllArtists(
            @RequestParam(required = false, defaultValue = "") String search,
            @RequestParam(required = false, defaultValue = "") String name,
            @RequestParam(required = false, defaultValue = "") String biography,
            @RequestParam(required = false) Integer birthYear,
            @RequestParam(required = false) Integer deathYear,
            @RequestParam(required = false, defaultValue = "") String nationality,
            @RequestParam(required = false, defaultValue = "0") Integer pageNumber,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize
    ) {
        log.info("Request to list or search artists");

        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("name").ascending());

        if (search != null && !search.isEmpty()) {
            return ResponseEntity.ok(artistService.searchArtist(search, pageable));
        } else if ((name != null && !name.isEmpty()) || (biography != null && !biography.isEmpty())
                || birthYear != null || deathYear != null || (nationality != null && !nationality.isEmpty())) {
            return ResponseEntity.ok(artistService.getAllArtists(name, biography, birthYear, deathYear, nationality, pageNumber, pageSize));
        } else {
            return ResponseEntity.ok(crudService.getAll(pageable));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArtistDTO> getById(@PathVariable("id") UUID id) {
        log.info("Request to get artist by id: {}", id);
        return ResponseEntity.ok(crudService.getById(id)
                .orElseThrow(() -> new NotFoundException("Artist with id " + id + " not found")));
    }

    @GetMapping("/name")
    public ResponseEntity<List<ArtistDTO>> getByName(@RequestParam("name") String name) {
        log.info("Request to get artists by name: {}", name);
        return ResponseEntity.ok(artistService.getByName(name));
    }

    @GetMapping("/year")
    public ResponseEntity<List<ArtistDTO>> getByYear(
            @RequestParam(required = false) Integer birthYear,
            @RequestParam(required = false) Integer deathYear
    ) {
        log.info("Request to get artist by birth year and/or death year.");

        if (birthYear != null && deathYear != null) {
            return ResponseEntity.ok(artistService.getArtistByBirthYearAndDeathYear(birthYear, deathYear));
        } else if (birthYear != null) {
            return ResponseEntity.ok(artistService.getArtistByBirthYear(birthYear));
        } else if (deathYear != null) {
            return ResponseEntity.ok(artistService.getArtistByDeathYear(deathYear));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping
    public ResponseEntity<ArtistDTO> create(@Valid @RequestBody ArtistDTO artistDTO) {
        log.info("Request to create artist: {}", artistDTO);
        ArtistDTO createdArtist = crudService.save(artistDTO);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add(HttpHeaders.LOCATION, "/artists/" + createdArtist.getId().toString());
        return new ResponseEntity<>(createdArtist, responseHeaders, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArtistDTO> update(@PathVariable("id") UUID id, @Valid @RequestBody ArtistDTO artistDTO) {
        log.info("Request to update artist: {}", artistDTO);
        return ResponseEntity.ok(crudService.update(id, artistDTO)
                .orElseThrow(() -> new NotFoundException("Artist with id " + id + " not found")));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") UUID id) {
        log.info("Request to delete artist: {}", id);
        boolean deleted = crudService.delete(id);
        if (!deleted) {
            throw new NotFoundException("Artist with id " + id + " not found");
        } else {
            log.warn("Bad request!");
            return ResponseEntity.noContent().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ArtistDTO> patch(@PathVariable("id") UUID id, @Valid @RequestBody ArtistDTO artistDTO) {
        log.info("Request to patch artist: {}", artistDTO);
        return ResponseEntity.ok(crudService.patch(id, artistDTO)
                .orElseThrow(() -> new NotFoundException("Artist with id " + id + " not found")));
    }

}