package ro.project.model.mapper;

import org.mapstruct.Mapper;
import ro.project.entity.Artist;
import ro.project.model.ArtistDTO;

@Mapper
public interface ArtistMapper {

    Artist dtoToArtist(ArtistDTO dto);

    ArtistDTO artistToDto(Artist artist);

}