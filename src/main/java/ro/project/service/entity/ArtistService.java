package ro.project.service.entity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ro.project.model.ArtistDTO;

import java.util.List;

public interface ArtistService {

    Page<ArtistDTO> getAllArtists(String name, String biography, Integer birthYear, Integer deathYear, String nationality, Integer pageNumber, Integer pageSize);

    List<ArtistDTO> getByName(String name);

    Page<ArtistDTO> searchArtist(String keyword, Pageable pageable);

    List<ArtistDTO> getArtistByBirthYearAndDeathYear(Integer birthYear, Integer deathYear);

    List<ArtistDTO> getArtistByBirthYear(Integer birthYear);

    List<ArtistDTO> getArtistByDeathYear(Integer deathYear);

}