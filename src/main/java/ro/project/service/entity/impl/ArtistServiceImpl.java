package ro.project.service.entity.impl;

import com.opencsv.bean.CsvToBeanBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.project.entity.Artist;
import ro.project.enums.Nationality;
import ro.project.exception.CsvProcessingException;
import ro.project.model.ArtistCSV;
import ro.project.model.ArtistDTO;
import ro.project.model.mapper.ArtistMapper;
import ro.project.repository.ArtistRepository;
import ro.project.service.crud.CrudServiceImpl;
import ro.project.service.entity.ArtistService;
import ro.project.service.util.CsvService;
import ro.project.service.util.SpecificationUtils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class ArtistServiceImpl extends CrudServiceImpl<ArtistDTO, Artist, UUID> implements ArtistService, CsvService<ArtistCSV> {

    private final ArtistRepository artistRepository;
    private final ArtistMapper artistMapper;

    public ArtistServiceImpl(ArtistRepository artistRepository, ArtistMapper artistMapper) {
        super(artistRepository, artistMapper);
        this.artistRepository = artistRepository;
        this.artistMapper = artistMapper;
    }

    private Specification<Artist> buildSpecification(String name, String biography, Integer birthYear, Integer deathYear, Nationality nationality) {
        return Specification.where(SpecificationUtils.<Artist>attributeLike("name", name))
                .and(SpecificationUtils.attributeLike("biography", biography))
                .and(SpecificationUtils.attributeEquals("birthYear", birthYear))
                .and(SpecificationUtils.attributeEquals("deathYear", deathYear))
                .and(SpecificationUtils.attributeEquals("nationality", nationality));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ArtistDTO> getAllArtists(String name, String biography, Integer birthYear, Integer deathYear, Nationality nationality, Pageable pageable) {
        Specification<Artist> specification = buildSpecification(name, biography, birthYear, deathYear, nationality);
        log.debug("Listing artist with filters - Name: {}, Biography: {}, Birth year {}, Death year: {}, Nationality: {}, ", name, biography, birthYear, deathYear, nationality);
        return artistRepository.findAll(specification, pageable).map(artistMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ArtistDTO> getByName(String name, Pageable pageable) {
        log.debug("Attempting to get Artist by name: {}", name);
        return artistRepository.findByNameContainingIgnoreCase(name, pageable)
                .map(artistMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ArtistDTO> searchArtist(String keyword, Pageable pageable) {
        log.debug("Searching Artist by keyword: {}", keyword);
        return artistRepository.searchArtist(keyword, pageable).map(artistMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ArtistDTO> getArtistByBirthYearAndDeathYear(Integer birthYear, Integer deathYear, Pageable pageable) {
        log.debug("Attempting to get Artist by Birth Year and Death Year: {}", birthYear);
        return artistRepository.findArtistByBirthYearAndDeathYear(birthYear, deathYear, pageable)
                .map(artistMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ArtistDTO> getArtistByBirthYear(Integer birthYear, Pageable pageable) {
        log.debug("Attempting to get Artist by Birth Year: {}", birthYear);
        return artistRepository.findArtistByBirthYear(birthYear, pageable)
                .map(artistMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ArtistDTO> getArtistByDeathYear(Integer deathYear, Pageable pageable) {
        log.debug("Attempting to get Artist by Death Year: {}", deathYear);
        return artistRepository.findArtistByDeathYear(deathYear, pageable)
                .map(artistMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ArtistDTO> getArtistsAlive(Pageable pageable) {
        log.debug("Fetching artists who are alive (deathYear = 0)");
        return artistRepository.findArtistByDeathYear(0, pageable).map(artistMapper::toDTO);
    }

    @Override
    public void convertCSV(File csvFile) {
        log.debug("Processing CSV file: {}", csvFile);

        try (FileReader fileReader = new FileReader(csvFile)) {
            List<ArtistCSV> artistCSV = new CsvToBeanBuilder<ArtistCSV>(fileReader)
                    .withType(ArtistCSV.class)
                    .build()
                    .parse();

            artistCSV.forEach(elem -> {
                try {
                    validateCsvFields(elem);
                    ArtistDTO dto = convertCsvToDto(elem);
                    artistRepository.save(artistMapper.toEntity(dto));
                    log.info("Successfully saved artist: {}", dto.getName());
                } catch (Exception e) {
                    log.error("Error processing artist {}: {}", elem.getName(), e.getMessage());
                }
            });
        } catch (IOException e) {
            log.error("Error processing CSV file: {}", csvFile, e);
            throw new CsvProcessingException("Error processing CSV file", e);
        }
    }

    private ArtistDTO convertCsvToDto(ArtistCSV csv) {
        Artist existingArtist = artistRepository
                .findByNameContainingIgnoreCase(csv.getName(), Pageable.unpaged())
                .stream()
                .filter(artist -> artist.getNationality() == csv.getNationality() && artist.getBirthYear().equals(csv.getBirthYear()))
                .findFirst().orElse(null);

        ArtistDTO dto = new ArtistDTO();

        if (existingArtist != null) {
            dto.setId(existingArtist.getId());
        } else {
            dto.setId(UUID.randomUUID());
        }

        dto.setName(csv.getName());
        dto.setBiography(csv.getBiography());
        dto.setBirthYear(csv.getBirthYear());
        dto.setDeathYear(csv.getDeathYear());
        dto.setNationality(csv.getNationality());
        return dto;
    }

    private void validateCsvFields(ArtistCSV csv) {
        if (csv.getBirthYear() == null) {
            csv.setBirthYear(0);
            log.warn("Birth year is unknown for artist: {}. Setting default: 0", csv.getName());
        }

        if (csv.getDeathYear() == null) {
            csv.setDeathYear(0);
            log.info("Death year is unknown for artist: {}. Setting default: 0", csv.getName());
        }

        if (csv.getBirthYear() > 0 && csv.getDeathYear() != null && csv.getBirthYear() > csv.getDeathYear()) {
            log.warn("Birth year {} is greater than death year {} for artist: {}. Correcting it to match birth year.",
                    csv.getBirthYear(), csv.getDeathYear(), csv.getName());

            csv.setDeathYear(csv.getBirthYear());
        }
    }

}