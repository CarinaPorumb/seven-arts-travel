package ro.project.service.entity.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.project.entity.Artist;
import ro.project.model.ArtistDTO;
import ro.project.model.mapper.ArtistMapper;
import ro.project.repository.ArtistRepository;
import ro.project.service.crud.CrudServiceImpl;
import ro.project.service.entity.ArtistService;
import ro.project.service.util.SpecificationUtils;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class ArtistServiceImpl extends CrudServiceImpl<ArtistDTO, Artist, UUID> implements ArtistService {

    private final ArtistRepository artistRepository;
    private final ArtistMapper artistMapper;

    public ArtistServiceImpl(ArtistRepository artistRepository, ArtistMapper artistMapper) {
        super(artistRepository);
        this.artistRepository = artistRepository;
        this.artistMapper = artistMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ArtistDTO> getAllArtists(String name, String biography, Integer birthYear, Integer deathYear, String nationality, Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("name").ascending());

        Specification<Artist> specification = Specification.where(SpecificationUtils.<Artist>attributeLike("name", name))
                .and(SpecificationUtils.attributeLike(biography, "biography"))
                .and(SpecificationUtils.attributeEquals(String.valueOf(birthYear), "birthYear"))
                .and(SpecificationUtils.attributeEquals(String.valueOf(deathYear), "deathYear"))
                .and(SpecificationUtils.attributeLike(nationality, "nationality"));

        log.debug("Listing artist with filters - Name: {}, Biography: {}, Birth year {}, Death year: {}, Nationality: {}, ", name, biography, birthYear, deathYear, nationality);
        return artistRepository.findAll(specification, pageable).map(artistMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ArtistDTO> getByName(String name) {
        log.debug("Attempting to get Artist by name: {}", name);
        return artistRepository.findByNameContainingIgnoreCase(name).stream()
                .map(artistMapper::toDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ArtistDTO> searchArtist(String keyword, Pageable pageable) {
        log.debug("Searching Artist by keyword: {}", keyword);
        return artistRepository.searchArtist(keyword, pageable).map(artistMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ArtistDTO> getArtistByBirthYearAndDeathYear(Integer birthYear, Integer deathYear) {
        log.debug("Attempting to get Artist by Birth Year and Death Year: {}", birthYear);
        return artistRepository.findArtistByBirthYearAndDeathYear(birthYear, deathYear).stream()
                .map(artistMapper::toDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ArtistDTO> getArtistByBirthYear(Integer birthYear) {
        log.debug("Attempting to get Artist by Birth Year: {}", birthYear);
        return artistRepository.findArtistByBirthYear(birthYear).stream()
                .map(artistMapper::toDTO)
                .toList();
    }

    @Override
    public List<ArtistDTO> getArtistByDeathYear(Integer deathYear) {
        log.debug("Attempting to get Artist by Death Year: {}", deathYear);
        return artistRepository.findArtistByDeathYear(deathYear).stream()
                .map(artistMapper::toDTO)
                .toList();
    }

    @Override
    protected Artist toEntity(ArtistDTO dto) {
        return artistMapper.toEntity(dto);
    }

    @Override
    protected ArtistDTO toDTO(Artist entity) {
        return artistMapper.toDTO(entity);
    }

    @Override
    protected void updateEntity(Artist entity, ArtistDTO dto) {
        artistMapper.updateEntity(entity, dto);
    }

    @Override
    protected void patchEntity(Artist entity, ArtistDTO dto) {
        artistMapper.patchEntity(entity, dto);
    }

}