package ro.project.model.mapper;

import org.mapstruct.Mapper;
import ro.project.entity.ArtEvent;
import ro.project.model.ArtEventDTO;

@Mapper
public interface ArtEventMapper {

    ArtEvent dtoToArtEvent(ArtEventDTO dto);

    ArtEventDTO artEventToDto(ArtEvent artEvent);

}