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
import ro.project.entity.ArtWork;
import ro.project.enums.Category;
import ro.project.exception.CsvProcessingException;
import ro.project.exception.NotFoundException;
import ro.project.model.ArtWorkCSV;
import ro.project.model.ArtWorkDTO;
import ro.project.model.mapper.ArtWorkMapper;
import ro.project.repository.ArtWorkRepository;
import ro.project.service.crud.CrudServiceImpl;
import ro.project.service.entity.ArtWorkService;
import ro.project.service.util.CsvService;
import ro.project.service.util.SpecificationUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class ArtWorkServiceImpl extends CrudServiceImpl<ArtWorkDTO, ArtWork, UUID> implements ArtWorkService, CsvService<ArtWorkCSV> {

    private final ArtWorkRepository artWorkRepository;
    private final ArtWorkMapper artWorkMapper;

    public ArtWorkServiceImpl(ArtWorkRepository artWorkRepository, ArtWorkMapper artWorkMapper) {
        super(artWorkRepository);
        this.artWorkRepository = artWorkRepository;
        this.artWorkMapper = artWorkMapper;
    }


    @Override
    @Transactional(readOnly = true)
    public Page<ArtWorkDTO> getAllArtWorks(String name, Integer year, String location, Category category, Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("name").ascending());

        Specification<ArtWork> specification = Specification.where(SpecificationUtils.<ArtWork>attributeLike("name", name))
                .and(SpecificationUtils.attributeEquals("year", year))
                .and(SpecificationUtils.attributeLike("location", location))
                .and(SpecificationUtils.attributeEquals("category", category));

        log.debug("Listing art works with filters - Name: {}, Year: {}, Location {}, Category: {},", name, year, location, category);
        return artWorkRepository.findAll(specification, pageable).map(artWorkMapper::toDTO);
    }

    @Override
    @Transactional
    public List<ArtWorkDTO> getByName(String name) {
        log.debug("Attempting to get ArtWork by name: {}", name);
        return artWorkRepository.findByNameContainingIgnoreCase(name).stream()
                .map(artWorkMapper::toDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ArtWorkDTO> displayByCategory(Category category) {
        log.debug("Attempting to display ArtWork by Category: {}", category);
        return artWorkRepository.findByCategory(category).stream()
                .map(artWorkMapper::toDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ArtWorkDTO> search(String keyword, Pageable pageable) {
        log.debug("Searching ArtWork by keyword: {}", keyword);
        return artWorkRepository.searchArtWork(keyword, pageable).map(artWorkMapper::toDTO);
    }

    @Override
    public void convertCSV(File csvFile) {
        log.debug("Converting CSV file: {}", csvFile);

        try (FileReader fileReader = new FileReader(csvFile)) {

            List<ArtWorkCSV> artWorkCSV = new CsvToBeanBuilder<ArtWorkCSV>(fileReader)
                    .withType(ArtWorkCSV.class)
                    .build()
                    .parse();

            artWorkCSV.forEach(elem -> {
                ArtWorkDTO dto = convertCSVToDTO(elem);
                ArtWork entity = artWorkMapper.toEntity(dto);
                artWorkRepository.save(entity);
            });
            log.info("Artworks loaded from CSV and saved to database successfully.");
        } catch (FileNotFoundException e) {
            log.error("CSV file not found: {}", csvFile);
            throw new NotFoundException("CSV file not found: " + e);
        } catch (Exception e) {
            log.error("Error processing CSV file: {}", csvFile, e);
            throw new CsvProcessingException("Error processing CSV file", e);
        }
    }

    private ArtWorkDTO convertCSVToDTO(ArtWorkCSV csv) {
        ArtWorkDTO dto = new ArtWorkDTO();
        dto.setName(csv.getName());
        dto.setImageLink(csv.getImageLink());
        dto.setDescription(csv.getDescription());
        dto.setYear(csv.getYear());
        dto.setLocation(csv.getLocation());
        dto.setCategory(Category.valueOf(csv.getCategory()));
        return dto;
    }

    @Override
    protected ArtWork toEntity(ArtWorkDTO dto) {
        return artWorkMapper.toEntity(dto);
    }

    @Override
    protected ArtWorkDTO toDTO(ArtWork entity) {
        return artWorkMapper.toDTO(entity);
    }

    @Override
    protected void updateEntity(ArtWork entity, ArtWorkDTO dto) {
        artWorkMapper.updateEntity(entity, dto);
    }

    @Override
    protected void patchEntity(ArtWork entity, ArtWorkDTO dto) {
        artWorkMapper.patchEntity(entity, dto);
    }

}