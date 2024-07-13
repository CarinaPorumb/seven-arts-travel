package ro.project.model.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ro.project.entity.ArtWork;
import ro.project.model.ArtWorkDTO;

@Mapper(componentModel = "spring")
public interface ArtWorkMapper {

    ArtWork toEntity(ArtWorkDTO dto);

    ArtWorkDTO toDTO(ArtWork entity);

    void updateEntity(@MappingTarget ArtWork entity, ArtWorkDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void patchEntity(@MappingTarget ArtWork entity, ArtWorkDTO dto);

}