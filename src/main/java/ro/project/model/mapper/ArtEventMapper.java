package ro.project.model.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ro.project.entity.ArtEvent;
import ro.project.model.ArtEventDTO;

@Mapper(componentModel = "spring")
public interface ArtEventMapper extends GenericMapper<ArtEventDTO, ArtEvent> {

    @Override
    ArtEvent toEntity(ArtEventDTO dto);

    @Override
    ArtEventDTO toDTO(ArtEvent artEvent);


    @Override
    void updateEntity(@MappingTarget ArtEvent artEvent, ArtEventDTO dto);

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void patchEntity(@MappingTarget ArtEvent artEvent, ArtEventDTO dto);
}