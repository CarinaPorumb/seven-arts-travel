package ro.project.service;

import ro.project.model.ArtObjectDTO;

import java.util.List;
import java.util.Optional;

public interface ArtObjectService {

    Optional<ArtObjectDTO> getById(Integer id);

    Optional<ArtObjectDTO> getByName(String name);

    List<ArtObjectDTO> getAll();

    void save(ArtObjectDTO artObjectDTO);

    void deleteById(Integer id) throws Exception;

    List<ArtObjectDTO> searchArtObject(String keyword);

    List<ArtObjectDTO> displayArchitecture(String keyword);

    List<ArtObjectDTO> displaySculpture(String keyword);

    List<ArtObjectDTO> displayLiterature(String keyword);

    List<ArtObjectDTO> displayPainting(String keyword);

}