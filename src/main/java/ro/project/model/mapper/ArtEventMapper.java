package ro.project.model.mapper;

import org.mapstruct.Mapper;
import ro.project.entity.ArtEvent;
import ro.project.model.ArtEventDTO;

@Mapper(componentModel = "spring")
public interface ArtEventMapper {

    ArtEvent toEntity(ArtEventDTO dto);

    ArtEventDTO toDTO(ArtEvent artEvent);

}