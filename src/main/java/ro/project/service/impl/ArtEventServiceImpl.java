package ro.project.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import ro.project.model.ArtEventDTO;
import ro.project.model.mapper.ArtEventMapper;
import ro.project.repository.ArtEventRepository;
import ro.project.service.ArtEventService;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ArtEventServiceImpl implements ArtEventService {

    private final ArtEventRepository artEventRepository;
    private final ArtEventMapper artEventMapper;

    @Override
    public Optional<ArtEventDTO> getById(Integer id) {
        return Optional.of(artEventMapper.artEventToDto(artEventRepository.findById(id).orElse(null)));
    }

    @Override
    public Optional<ArtEventDTO> getByName(String name) {
        return Optional.of(artEventMapper.artEventToDto(artEventRepository.findByName(name)));
    }

    @Override
    public List<ArtEventDTO> getAll() {
        return artEventRepository.findAll().stream()
                .map(artEventMapper::artEventToDto)
                .toList();
    }

    @Override
    public void save(ArtEventDTO artEventDTO) {
        artEventMapper.artEventToDto(artEventRepository.save(artEventMapper.dtoToArtEvent(artEventDTO)));
    }

    @Override
    public void deleteById(Integer id) {
        if (artEventRepository.existsById(id))
            artEventRepository.deleteById(id);
        else throw new NotFoundException("Id not found!");
    }

    @Override
    public List<ArtEventDTO> searchArtEvent(String keyword) {
        return artEventRepository.searchArtEvent(keyword).stream().map(artEventMapper::artEventToDto).toList();
    }

    @Override
    public List<ArtEventDTO> displayMusic(String keyword) {
        return artEventRepository.displayMusic(keyword).stream().map(artEventMapper::artEventToDto).toList();
    }

    @Override
    public List<ArtEventDTO> displayCinema(String keyword) {
        return artEventRepository.displayCinema(keyword).stream().map(artEventMapper::artEventToDto).toList();
    }

    @Override
    public List<ArtEventDTO> displayBalletAndTheatre(String keyword) {
        return artEventRepository.displayBalletAndTheatre(keyword).stream().map(artEventMapper::artEventToDto).toList();
    }
}