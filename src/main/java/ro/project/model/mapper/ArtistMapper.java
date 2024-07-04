package ro.project.model.mapper;

import org.mapstruct.Mapper;
import ro.project.entity.Artist;
import ro.project.model.ArtistDTO;

@Mapper(componentModel = "spring")
public interface ArtistMapper {

    Artist toEntity(ArtistDTO dto);

    ArtistDTO toDTO(Artist artist);

}