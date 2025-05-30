package ro.sevenartstravel.service.entity.impl;

import com.opencsv.bean.CsvToBeanBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.sevenartstravel.csv.ArtistCSV;
import ro.sevenartstravel.dto.ArtistDTO;
import ro.sevenartstravel.entity.Artist;
import ro.sevenartstravel.enums.Nationality;
import ro.sevenartstravel.exception.CsvProcessingException;
import ro.sevenartstravel.mapper.ArtistMapper;
import ro.sevenartstravel.repository.ArtistRepository;
import ro.sevenartstravel.service.crud.CrudServiceImpl;
import ro.sevenartstravel.service.entity.ArtistService;
import ro.sevenartstravel.service.util.CsvService;
import ro.sevenartstravel.service.util.SpecificationUtils;

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
        log.debug("Listing artists with filters - Name: {}, Biography: {}, Birth year {}, Death year: {}, Nationality: {}, ", name, biography, birthYear, deathYear, nationality);
        return artistRepository.findAll(specification, pageable).map(artistMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ArtistDTO> getByName(String name, Pageable pageable) {
        log.debug("Fetching artists by name: {}", name);
        return artistRepository.findByNameIgnoreCase(name, pageable)
                .map(artistMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ArtistDTO> searchArtist(String keyword, Pageable pageable) {
        log.debug("Searching artists with keyword: {}", keyword);
        return artistRepository.searchArtist(keyword, pageable).map(artistMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ArtistDTO> getArtistByBirthYear(Integer birthYear, Pageable pageable) {
        log.debug("Fetching artists by birth year: {}", birthYear);
        return artistRepository.findArtistByBirthYear(birthYear, pageable)
                .map(artistMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ArtistDTO> getArtistByDeathYear(Integer deathYear, Pageable pageable) {
        log.debug("Fetching artists by death year: {}", deathYear);
        return artistRepository.findArtistByDeathYear(deathYear, pageable)
                .map(artistMapper::toDTO);
    }

    @Override
    public Page<ArtistDTO> getArtistByNationality(Nationality nationality, Pageable pageable) {
        log.debug("Fetching artists by nationality: {}", nationality);
        return artistRepository.findArtistByNationality(nationality, pageable)
                .map(artistMapper::toDTO);
    }

    @Override
    public void convertCSV(File csvFile) {
        log.debug("Processing artist CSV file: {}", csvFile);

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
                    log.error("CSV error for artist {}: {}", elem.getName(), e.getMessage());
                }
            });
        } catch (IOException e) {
            log.error("Could not read CSV file: {}", csvFile, e);
            throw new CsvProcessingException("CSV read failure", e);
        }
    }

    private ArtistDTO convertCsvToDto(ArtistCSV csv) {
        Artist existingArtist = artistRepository
                .findByNameIgnoreCase(csv.getName(), Pageable.unpaged())
                .stream()
                .filter(artist -> artist.getNationality() == csv.getNationality()
                        && artist.getBirthYear().equals(csv.getBirthYear()))
                .findFirst()
                .orElse(null);

        ArtistDTO dto = new ArtistDTO();

        if (existingArtist != null) {
            dto.setId(existingArtist.getId());
        }

        dto.setName(csv.getName());
        dto.setBiography(csv.getBiography());
        dto.setBirthYear(csv.getBirthYear());
        dto.setDeathYear(csv.getDeathYear());
        dto.setNationality(csv.getNationality());
        return dto;
    }

    private void validateCsvFields(ArtistCSV csv) {
        int currentYear = java.time.Year.now().getValue();

        if (csv.getBirthYear() == null) {
            csv.setBirthYear(0);
            log.warn("Birth year is unknown for artist: {}. Setting default: 0", csv.getName());
        }

        if (csv.getDeathYear() == null) {
            csv.setDeathYear(0);
            log.info("Death year is unknown for artist: {}. Setting default: 0", csv.getName());
        }

        if (csv.getBirthYear() > currentYear) {
            throw new CsvProcessingException("Birth year cannot be in the future for artist: " + csv.getName());
        }

        if (csv.getDeathYear() > currentYear) {
            throw new CsvProcessingException("Death year cannot be in the future for artist: " + csv.getName());
        }

        if (csv.getBirthYear() > 0 && csv.getDeathYear() > 0 && csv.getBirthYear() > csv.getDeathYear()) {
            throw new CsvProcessingException("Birth year cannot be after death year for artist: " + csv.getName());
        }

    }
}