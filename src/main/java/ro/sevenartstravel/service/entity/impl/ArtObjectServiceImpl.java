package ro.sevenartstravel.service.entity.impl;

import com.opencsv.bean.CsvToBeanBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.sevenartstravel.csv.ArtObjectCSV;
import ro.sevenartstravel.dto.ArtObjectDTO;
import ro.sevenartstravel.entity.ArtObject;
import ro.sevenartstravel.entity.Artist;
import ro.sevenartstravel.enums.ArtCategory;
import ro.sevenartstravel.enums.ArtObjectType;
import ro.sevenartstravel.enums.Status;
import ro.sevenartstravel.exception.CsvProcessingException;
import ro.sevenartstravel.exception.NotFoundException;
import ro.sevenartstravel.mapper.ArtObjectMapper;
import ro.sevenartstravel.repository.ArtObjectRepository;
import ro.sevenartstravel.repository.ArtistRepository;
import ro.sevenartstravel.repository.MovementRepository;
import ro.sevenartstravel.service.crud.CrudServiceImpl;
import ro.sevenartstravel.service.entity.ArtObjectService;
import ro.sevenartstravel.service.util.CsvService;
import ro.sevenartstravel.service.util.SpecificationUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ArtObjectServiceImpl extends CrudServiceImpl<ArtObjectDTO, ArtObject, UUID> implements ArtObjectService, CsvService<ArtObjectCSV> {

    private final ArtObjectRepository artObjectRepository;
    private final ArtObjectMapper artObjectMapper;
    private final MovementRepository movementRepository;
    private final ArtistRepository artistRepository;

    public ArtObjectServiceImpl(ArtObjectRepository artObjectRepository, ArtObjectMapper artObjectMapper, MovementRepository movementRepository, ArtistRepository artistRepository) {
        super(artObjectRepository, artObjectMapper);
        this.artObjectRepository = artObjectRepository;
        this.artObjectMapper = artObjectMapper;
        this.movementRepository = movementRepository;
        this.artistRepository = artistRepository;
    }

    private Specification<ArtObject> buildSpecification(String title, String shortDescription, String longDescription, String location, ArtCategory artCategory, Integer year) {
        return Specification.where(SpecificationUtils.<ArtObject>attributeLike("title", title))
                .and(SpecificationUtils.attributeLike("shortDescription", shortDescription))
                .and(SpecificationUtils.attributeLike("longDescription", longDescription))
                .and(SpecificationUtils.attributeLike("location", location))
                .and(SpecificationUtils.attributeEquals("artCategory", artCategory))
                .and(SpecificationUtils.attributeEquals("year", year));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ArtObjectDTO> getAll(String title, String shortDescription, String longDescription, String location, ArtCategory artCategory, Integer year, Pageable pageable) {
        Specification<ArtObject> specification = buildSpecification(title, shortDescription, longDescription, location, artCategory, year);
        log.debug("Listing art objects with filters - Name: {}, Year: {}, Location {}, ArtCategory: {},", title, year, location, artCategory);
        return artObjectRepository.findAll(specification, pageable).map(artObjectMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ArtObjectDTO> getByTitle(String title, Pageable pageable) {
        log.debug("Fetching art objects by name: {}", title);
        return artObjectRepository.findByTitleContainingIgnoreCase(title, pageable)
                .map(artObjectMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ArtObjectDTO> getByYear(int year, Pageable pageable) {
        log.debug("Fetching art objects by year: {}", year);
        return artObjectRepository.findByYear(year, pageable)
                .map(artObjectMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ArtObjectDTO> getByCategory(ArtCategory artCategory, Pageable pageable) {
        log.debug("Fetching art objects by category: {}", artCategory);
        return artObjectRepository.findByArtCategory(artCategory, pageable)
                .map(artObjectMapper::toDTO);
    }

    @Override
    public Page<ArtObjectDTO> getByLocation(String location, Pageable pageable) {
        log.debug("Fetching art objects by location: {}", location);
        return artObjectRepository.findByLocation(location, pageable)
                .map(artObjectMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ArtObjectDTO> getByYearRange(int startYear, int endYear, Pageable pageable) {
        log.debug("Fetching art objects from year {} to {}", startYear, endYear);
        return artObjectRepository.findByYearBetween(startYear, endYear, pageable)
                .map(artObjectMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ArtObjectDTO> searchArtObject(String keyword, Pageable pageable) {
        log.debug("Searching artObject with keyword: {}", keyword);
        return artObjectRepository.searchArtObjects(keyword, pageable)
                .map(artObjectMapper::toDTO);
    }

    @Override
    public Page<ArtObjectDTO> getByArtObjectType(ArtObjectType artObjectType, Pageable pageable) {
        log.debug("Fetching art objects by type: {}", artObjectType);
        return artObjectRepository.findByArtObjectType(artObjectType, pageable)
                .map(artObjectMapper::toDTO);
    }

    private void validateArtObjectFields(ArtObjectDTO dto) {
        ArtObjectType type = dto.getArtObjectType();

        if (type == ArtObjectType.EVENT) {
            if (dto.getStartTime() == null || dto.getEndTime() == null) {
                throw new CsvProcessingException("Event must have start and end time.");
            }
            if (dto.getStartTime().isAfter(dto.getEndTime())) {
                throw new CsvProcessingException("Start time must be before end time.");
            }
            if (dto.getStatus() == null) {
                throw new CsvProcessingException("Event status must not be null.");
            }
            if (dto.getIsTemporary() == null) {
                throw new CsvProcessingException("Event temporary flag must not be null.");
            }
        } else {
            // Warn about fields that should not be set
            if (dto.getStartTime() != null || dto.getEndTime() != null || dto.getStatus() != null || dto.getIsTemporary() != null) {
                log.warn("Ignoring event-only fields for non-event ArtObject: {}", dto.getTitle());
            }
        }

        if (type == ArtObjectType.WORK && dto.getYear() == null) {
            throw new CsvProcessingException("Work must have a year.");
        }

        if (dto.getLocation() == null || dto.getLocation().isBlank()) {
            throw new CsvProcessingException("Location is required for all ArtObjects.");
        }
    }

    @Override
    @Transactional
    public void convertCSV(File csvFile) {
        log.debug("Converting CSV file: {}", csvFile);

        try (FileReader fileReader = new FileReader(csvFile)) {

            List<ArtObjectCSV> artObjectCSV = new CsvToBeanBuilder<ArtObjectCSV>(fileReader)
                    .withType(ArtObjectCSV.class)
                    .build()
                    .parse();

            artObjectCSV.forEach(elem -> {
                try {
                    ArtObjectDTO dto = convertCSVToDTO(elem);
                    artObjectRepository.save(artObjectMapper.toEntity(dto));
                } catch (Exception e) {
                    log.error("Failed to process record: {}.  {}", elem, e.getMessage());
                }
            });
            log.info("Artobjects loaded from CSV and saved to database successfully.");
        } catch (FileNotFoundException e) {
            log.error("CSV file not found: {}", csvFile);
            throw new NotFoundException("CSV file not found: " + e);
        } catch (Exception e) {
            log.error("Error processing CSV file: {}", csvFile, e);
            throw new CsvProcessingException("Error processing CSV file", e);
        }
    }

    private ArtObjectDTO convertCSVToDTO(ArtObjectCSV csv) {
        if (csv.getTitle() == null || csv.getTitle().isBlank()) {
            throw new CsvProcessingException("ArtObject title cannot be null or empty");
        }

        if (csv.getYear() == null || csv.getYear() > LocalDate.now().getYear()) {
            csv.setYear(LocalDate.now().getYear());
        }

        ArtObjectDTO dto = new ArtObjectDTO();
        dto.setTitle(csv.getTitle());
        dto.setShortDescription(csv.getShortDescription());
        dto.setLongDescription(csv.getLongDescription());
        dto.setLocation(csv.getLocation());
        dto.setYear(csv.getYear());
        dto.setImageUrl(csv.getImageUrl());
        dto.setExternalUrl(csv.getExternalUrl());

        try {
            dto.setArtCategory(ArtCategory.valueOf(csv.getArtCategory().toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new CsvProcessingException("Invalid art category: " + csv.getArtCategory());
        }

        try {
            dto.setArtObjectType(ArtObjectType.valueOf(csv.getArtObjectType().toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new CsvProcessingException("Invalid art object type: " + csv.getArtObjectType());
        }

        //Only parse event fields if the type is EVENT
        if (dto.getArtObjectType() == ArtObjectType.EVENT) {
            try {

                if (csv.getStartTime() != null && !csv.getStartTime().isBlank()) {
                    dto.setStartTime(LocalDateTime.parse(csv.getStartTime()));
                } else {
                    log.warn("Missing startTime for event '{}'. Setting default now().", csv.getTitle());
                    dto.setStartTime(LocalDateTime.now());
                }

                if (csv.getEndTime() != null && !csv.getEndTime().isBlank()) {
                    dto.setEndTime(LocalDateTime.parse(csv.getEndTime()));
                } else {
                    log.warn("Missing endTime for event '{}'. Setting default now() + 2 hours.", csv.getTitle());
                    dto.setEndTime(LocalDateTime.now().plusHours(2));
                }
            } catch (Exception e) {
                throw new CsvProcessingException("Invalid date format for start/end time");
            }

            dto.setIsTemporary(csv.getIsTemporary());

            try {
                dto.setStatus(Status.valueOf(csv.getStatus().trim().toUpperCase()));
            } catch (Exception e) {
                throw new CsvProcessingException("Invalid status: " + csv.getStatus());
            }

            if (csv.getMovementName() != null && !csv.getMovementName().isBlank()) {
                movementRepository.findByNameIgnoreCase(csv.getMovementName().trim())
                        .ifPresentOrElse(movement -> dto.setMovementId(movement.getId()),
                                () -> log.warn("Movement not found for name: {}", csv.getMovementName()));
            }

            if (csv.getArtistNames() != null && !csv.getArtistNames().isBlank()) {
                Set<UUID> artistIds = Arrays.stream(csv.getArtistNames().split(","))
                        .map(String::trim)
                        .map(this::findOrCreateArtistByName)
                        .map(Artist::getId)
                        .collect(Collectors.toSet());

                dto.setArtistIds(artistIds);
            }
        }
        validateArtObjectFields(dto);
        return dto;
    }

    private Artist findOrCreateArtistByName(String name) {
        return artistRepository.findByNameIgnoreCase(name)
                .orElseGet(() -> artistRepository.save(
                        Artist.builder().name(name).build()));
    }

}