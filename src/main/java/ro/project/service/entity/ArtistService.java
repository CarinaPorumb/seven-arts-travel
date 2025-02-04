package ro.project.service.entity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ro.project.enums.Nationality;
import ro.project.model.ArtistDTO;

public interface ArtistService {

    Page<ArtistDTO> getAllArtists(String name, String biography, Integer birthYear, Integer deathYear, Nationality nationality, Pageable pageable);

    Page<ArtistDTO> getByName(String name, Pageable pageable);

    Page<ArtistDTO> searchArtist(String keyword, Pageable pageable);

    Page<ArtistDTO> getArtistByBirthYearAndDeathYear(Integer birthYear, Integer deathYear, Pageable pageable);

    Page<ArtistDTO> getArtistByBirthYear(Integer birthYear, Pageable pageable);

    Page<ArtistDTO> getArtistByDeathYear(Integer deathYear, Pageable pageable);

    Page<ArtistDTO> getArtistsAlive(Pageable pageable);

}