package ro.project.service.impl;

import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import ro.project.model.ArtObjectCSV;
import ro.project.model.ArtWorkDTO;
import ro.project.model.mapper.ArtWorkMapper;
import ro.project.repository.ArtObjectRepository;
import ro.project.service.ArtObjectService;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ArtObjectServiceImpl implements ArtObjectService {

    private final ArtObjectRepository artObjectRepository;
    private final ArtWorkMapper artWorkMapper;

    @Override
    public Optional<ArtWorkDTO> getById(Long id) {
        return Optional.of(artWorkMapper.artWorkToDto(artObjectRepository.findById(Math.toIntExact(id)).orElse(null)));
    }

    @Override
    public Optional<ArtWorkDTO> getByName(String name) {
        return Optional.of(artWorkMapper.artWorkToDto(artObjectRepository.findByName(name)));
    }

    @Override
    public List<ArtWorkDTO> getAll() {
        return artObjectRepository.findAll().stream().map(artWorkMapper::artWorkToDto).toList();
    }

    @Override
    public void save(ArtWorkDTO artWorkDTO) {
        artWorkMapper.artWorkToDto(artObjectRepository.save(artWorkMapper.dtoToArtWork(artWorkDTO)));
    }

    @Override
    public void deleteById(Integer id) {
        if (artObjectRepository.existsById(id))
            artObjectRepository.deleteById(id);
        else throw new NotFoundException("Id not found!");
    }

    @Override
    public List<ArtWorkDTO> searchArtObject(String keyword) {
        return artObjectRepository.searchArtObject(keyword).stream().map(artWorkMapper::artWorkToDto).toList();
    }

    @Override
    public List<ArtWorkDTO> displayArchitecture(String keyword) {
        return artObjectRepository.displayArchitecture(keyword).stream().map(artWorkMapper::artWorkToDto).toList();
    }

    @Override
    public List<ArtWorkDTO> displaySculpture(String keyword) {
        return artObjectRepository.displaySculpture(keyword).stream().map(artWorkMapper::artWorkToDto).toList();
    }

    @Override
    public List<ArtWorkDTO> displayLiterature(String keyword) {
        return artObjectRepository.displayLiterature(keyword).stream().map(artWorkMapper::artWorkToDto).toList();
    }

    @Override
    public List<ArtWorkDTO> displayPainting(String keyword) {
        return artObjectRepository.displayPainting(keyword).stream().map(artWorkMapper::artWorkToDto).toList();

    }

    @Override
    public List<ArtObjectCSV> convertCSV(File csvFile) {
        try {
            return (new CsvToBeanBuilder<ArtObjectCSV>(new FileReader(csvFile))
                    .withType(ArtObjectCSV.class)
                    .build().parse());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}