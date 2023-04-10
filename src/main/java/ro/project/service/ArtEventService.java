package ro.project.service;

import ro.project.model.ArtEventCSV;
import ro.project.model.ArtEventDTO;

import java.io.File;
import java.util.List;
import java.util.Optional;

public interface ArtEventService {

    Optional<ArtEventDTO> getById(Integer id);

    Optional<ArtEventDTO> getByName(String name);

    List<ArtEventDTO> getAll();

    void save(ArtEventDTO artEventDTO);

    void deleteById(Integer id);

    List<ArtEventDTO> searchArtEvent(String keyword);

    List<ArtEventDTO> displayMusic(String keyword);

    List<ArtEventDTO> displayCinema(String keyword);

    List<ArtEventDTO> displayBalletAndTheatre(String keyword);

    List<ArtEventCSV> convertCSV(File csvFile);

}