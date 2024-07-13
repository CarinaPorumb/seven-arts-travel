package ro.project.service.entity.impl;

import com.opencsv.bean.CsvToBeanBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.project.entity.ArtEvent;
import ro.project.enums.Category;
import ro.project.enums.Status;
import ro.project.exception.CsvProcessingException;
import ro.project.exception.NotFoundException;
import ro.project.model.ArtEventCSV;
import ro.project.model.ArtEventDTO;
import ro.project.model.mapper.ArtEventMapper;
import ro.project.repository.ArtEventRepository;
import ro.project.service.crud.CrudServiceImpl;
import ro.project.service.entity.ArtEventService;
import ro.project.service.util.CsvService;
import ro.project.service.util.SpecificationUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class ArtEventServiceImpl extends CrudServiceImpl<ArtEventDTO, ArtEvent, UUID> implements ArtEventService, CsvService<ArtEventCSV> {

    private final ArtEventRepository artEventRepository;
    private final ArtEventMapper artEventMapper;

    public ArtEventServiceImpl(ArtEventRepository artEventRepository, ArtEventMapper artEventMapper) {
        super(artEventRepository);
        this.artEventRepository = artEventRepository;
        this.artEventMapper = artEventMapper;
    }


    @Override
    @Transactional(readOnly = true)
    public Page<ArtEventDTO> getAllArtEvents(String name, String location, Category category, Status status, Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("name").ascending());

        Specification<ArtEvent> specification = Specification.where(SpecificationUtils.<ArtEvent>attributeLike("name", name))
                .and(SpecificationUtils.attributeLike("location", location))
                .and(SpecificationUtils.attributeEquals("category", category))
                .and(SpecificationUtils.attributeEquals("status", status));

        log.debug("Listing art events with filters - Name: {}, Location: {}, Category: {}, Status: {},", name, location, category, status);
        return artEventRepository.findAll(specification, pageable).map(artEventMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ArtEventDTO> getByName(String name) {
        log.debug("Attempting to get ArtEvents by name {}", name);
        return artEventRepository.findByNameContainingIgnoreCase(name).stream()
                .map(artEventMapper::toDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ArtEventDTO> displayByCategory(Category category) {
        log.debug("Retrieving ArtEvents by category {}", category);
        return artEventRepository.findEventsByCategory(category).stream()
                .map(artEventMapper::toDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ArtEventDTO> search(String keyword, Pageable pageable) {
        log.debug("Searching ArtEvents with keyword {}", keyword);
        return artEventRepository.searchArtEvent(keyword, pageable)
                .map(artEventMapper::toDTO);
    }

    @Override
    public void convertCSV(File csvFile) {
        log.debug("Converting CSV file: {}", csvFile);

        try (FileReader fileReader = new FileReader(csvFile)) {
            List<ArtEventCSV> artEventCSV = new CsvToBeanBuilder<ArtEventCSV>(fileReader)
                    .withType(ArtEventCSV.class)
                    .build()
                    .parse();

            artEventCSV.forEach(elem -> {
                ArtEventDTO dto = convertCSVToDTO(elem);
                ArtEvent entity = artEventMapper.toEntity(dto);
                artEventRepository.save(entity);
            });
            log.info("ArtEvents loaded from CSV and saved to database successfully.");

        } catch (FileNotFoundException e) {
            log.error("CSV file not found : {}", csvFile);
            throw new NotFoundException("CSV file not found!", e);
        } catch (Exception e) {
            log.error("Error processing CSV file: {}", csvFile, e);
            throw new CsvProcessingException("Error processing CSV file", e);
        }
    }

    //TODO Fix enum inputs: lowercase, replace spaces
    private ArtEventDTO convertCSVToDTO(ArtEventCSV csv) {
        ArtEventDTO dto = new ArtEventDTO();
        dto.setName(csv.getName());
        dto.setDescription(csv.getDescription());
        dto.setLocation(csv.getLocation());
        dto.setStatus(Status.valueOf(csv.getStatus()));
        dto.setCategory(Category.valueOf(csv.getCategory()));

        if (csv.getStartTime() != null && !csv.getStartTime().isEmpty()) {
            dto.setStartTime(LocalDateTime.parse(csv.getStartTime()));
        } else {
            log.error("Invalid start time format for {}", csv.getStartTime());
            dto.setStartTime(LocalDateTime.now());
        }

        if (csv.getEndTime() != null && !csv.getEndTime().isEmpty()) {
            dto.setEndTime(LocalDateTime.parse(csv.getEndTime()));
        } else {
            log.error("Invalid end time format for {}", csv.getEndTime());
            dto.setEndTime(LocalDateTime.now().plusDays(1));
        }
        return dto;
    }


    @Override
    protected ArtEvent toEntity(ArtEventDTO dto) {
        return artEventMapper.toEntity(dto);
    }

    @Override
    protected ArtEventDTO toDTO(ArtEvent entity) {
        return artEventMapper.toDTO(entity);
    }

    @Override
    protected void updateEntity(ArtEvent entity, ArtEventDTO dto) {
        artEventMapper.updateEntity(entity, dto);
    }

    @Override
    protected void patchEntity(ArtEvent entity, ArtEventDTO dto) {
        artEventMapper.patchEntity(entity, dto);
    }

}