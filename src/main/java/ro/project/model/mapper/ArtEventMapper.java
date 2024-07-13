package ro.project.model.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ro.project.entity.ArtEvent;
import ro.project.model.ArtEventDTO;

@Mapper(componentModel = "spring")
public interface ArtEventMapper {

    ArtEvent toEntity(ArtEventDTO dto);

    ArtEventDTO toDTO(ArtEvent artEvent);

    void updateEntity(@MappingTarget ArtEvent artEvent, ArtEventDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void patchEntity(@MappingTarget ArtEvent artEvent, ArtEventDTO dto);

}