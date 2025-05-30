package ro.sevenartstravel.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ro.sevenartstravel.dto.ArtistDTO;
import ro.sevenartstravel.enums.Nationality;
import ro.sevenartstravel.exception.NotFoundException;
import ro.sevenartstravel.service.crud.CrudService;
import ro.sevenartstravel.service.entity.ArtistService;

import java.net.URI;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/artists")
@RequiredArgsConstructor
public class ArtistController {

    private final ArtistService artistService;
    private final CrudService<ArtistDTO, UUID> crudService;

    @GetMapping
    public ResponseEntity<Page<ArtistDTO>> getArtists(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String biography,
            @RequestParam(required = false) Integer birthYear,
            @RequestParam(required = false) Integer deathYear,
            @RequestParam(required = false) Nationality nationality,
            Pageable pageable) {

        if (search != null && !search.isEmpty()) {
            log.info("Searching artists with keyword: {}", search);
            return ResponseEntity.ok(artistService.searchArtist(search, pageable));
        } else if ((name != null && !name.isEmpty()) || (biography != null && !biography.isEmpty())
                || birthYear != null || deathYear != null || nationality != null) {
            log.info("Listing artists with filters: name={}, biography={}, birthYear={}, deathYear={}, nationality={}",
                    name, biography, birthYear, deathYear, nationality);
            return ResponseEntity.ok(artistService.getAllArtists(name, biography, birthYear, deathYear, nationality, pageable));
        } else {
            log.info("Returning all artists without filters.");
            return ResponseEntity.ok(crudService.getAll(pageable));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArtistDTO> getArtistById(@PathVariable("id") UUID id) {
        log.info("Request to get artist by id: {}", id);
        return ResponseEntity.ok(crudService.getById(id)
                .orElseThrow(() -> new NotFoundException("Artist", id)));
    }

    @GetMapping("/name")
    public ResponseEntity<Page<ArtistDTO>> getArtistsByName(@RequestParam("name") String name, Pageable pageable) {
        log.info("Request to get artists by name: {}", name);
        return ResponseEntity.ok(artistService.getByName(name, pageable));
    }

    @GetMapping("/birth-year")
    public ResponseEntity<Page<ArtistDTO>> getArtistsByBirthYear(@RequestParam("birthYear") int birthYear, Pageable pageable) {
        log.info("Request to display artists by birth year: {}", birthYear);
        return ResponseEntity.ok(artistService.getArtistByBirthYear(birthYear, pageable));
    }

    @GetMapping("/death-year")
    public ResponseEntity<Page<ArtistDTO>> getArtistsByDeathYear(@RequestParam("deathYear") int deathYear, Pageable pageable) {
        log.info("Request to display artists by death year: {}", deathYear);
        return ResponseEntity.ok(artistService.getArtistByDeathYear(deathYear, pageable));
    }

    @GetMapping("/nationality")
    public ResponseEntity<Page<ArtistDTO>> getArtistsByNationality(@RequestParam("nationality") Nationality nationality, Pageable pageable) {
        log.info("Request to display artists by nationality: {}", nationality);
        return ResponseEntity.ok(artistService.getArtistByNationality(nationality, pageable));
    }

    @PostMapping
    public ResponseEntity<ArtistDTO> create(@Valid @RequestBody ArtistDTO artistDTO, UriComponentsBuilder uriBuilder) {
        log.info("Request to create artist: {}", artistDTO);
        ArtistDTO created = crudService.save(artistDTO);
        URI location = uriBuilder.path("/api/artists/{id}").buildAndExpand(created.getId()).toUri(); // URI for Location header in 201 Created response, pointing to the newly created resource
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArtistDTO> update(@PathVariable("id") UUID id, @Valid @RequestBody ArtistDTO artistDTO) {
        log.info("Request to update artist with id {}: {}", id, artistDTO);
        return ResponseEntity.ok(crudService.update(id, artistDTO)
                .orElseThrow(() -> new NotFoundException("Artist", id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        log.info("Request to delete artist with id {}", id);
        boolean deleted = crudService.delete(id);
        if (!deleted) {
            throw new NotFoundException("Artist", id);
        }
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ArtistDTO> patch(@PathVariable("id") UUID id, @Valid @RequestBody ArtistDTO artistDTO) {
        log.info("Request to patch artist with id {}: {}", id, artistDTO);
        return ResponseEntity.ok(crudService.patch(id, artistDTO)
                .orElseThrow(() -> new NotFoundException("Artist", id)));
    }

}