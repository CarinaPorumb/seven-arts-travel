package ro.project.service.impl;

import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import ro.project.model.ArtObjectCSV;
import ro.project.model.ArtObjectDTO;
import ro.project.model.mapper.ArtObjectMapper;
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
    private final ArtObjectMapper artObjectMapper;

    @Override
    public Optional<ArtObjectDTO> getById(Integer id) {
        return Optional.of(artObjectMapper.artObjectToDto(artObjectRepository.findById(id).orElse(null)));
    }

    @Override
    public Optional<ArtObjectDTO> getByName(String name) {
        return Optional.of(artObjectMapper.artObjectToDto(artObjectRepository.findByName(name)));
    }

    @Override
    public List<ArtObjectDTO> getAll() {
        return artObjectRepository.findAll().stream().map(artObjectMapper::artObjectToDto).toList();
    }

    @Override
    public void save(ArtObjectDTO artObjectDTO) {
        artObjectMapper.artObjectToDto(artObjectRepository.save(artObjectMapper.dtoToArtObject(artObjectDTO)));
    }

    @Override
    public void deleteById(Integer id) {
        if (artObjectRepository.existsById(id))
            artObjectRepository.deleteById(id);
        else throw new NotFoundException("Id not found!");
    }

    @Override
    public List<ArtObjectDTO> searchArtObject(String keyword) {
        return artObjectRepository.searchArtObject(keyword).stream().map(artObjectMapper::artObjectToDto).toList();
    }

    @Override
    public List<ArtObjectDTO> displayArchitecture(String keyword) {
        return artObjectRepository.displayArchitecture(keyword).stream().map(artObjectMapper::artObjectToDto).toList();
    }

    @Override
    public List<ArtObjectDTO> displaySculpture(String keyword) {
        return artObjectRepository.displaySculpture(keyword).stream().map(artObjectMapper::artObjectToDto).toList();
    }

    @Override
    public List<ArtObjectDTO> displayLiterature(String keyword) {
        return artObjectRepository.displayLiterature(keyword).stream().map(artObjectMapper::artObjectToDto).toList();
    }

    @Override
    public List<ArtObjectDTO> displayPainting(String keyword) {
        return artObjectRepository.displayPainting(keyword).stream().map(artObjectMapper::artObjectToDto).toList();

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