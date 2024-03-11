package ro.project.service;

import ro.project.model.ArtObjectCSV;
import ro.project.model.ArtWorkDTO;

import java.io.File;
import java.util.List;
import java.util.Optional;

public interface ArtObjectService {

    Optional<ArtWorkDTO> getById(Long id);

    Optional<ArtWorkDTO> getByName(String name);

    List<ArtWorkDTO> getAll();

    void save(ArtWorkDTO artWorkDTO);

    void deleteById(Integer id) throws Exception;

    List<ArtWorkDTO> searchArtObject(String keyword);

    List<ArtWorkDTO> displayArchitecture(String keyword);

    List<ArtWorkDTO> displaySculpture(String keyword);

    List<ArtWorkDTO> displayLiterature(String keyword);

    List<ArtWorkDTO> displayPainting(String keyword);

    List<ArtObjectCSV> convertCSV(File csvFile);


}