package ro.project.model.mapper;

import org.mapstruct.Mapper;
import ro.project.entity.ArtObject;
import ro.project.model.ArtObjectDTO;

@Mapper
public interface ArtObjectMapper {

    ArtObject dtoToArtObject(ArtObjectDTO dto);

    ArtObjectDTO artObjectToDto(ArtObject artObject);

}