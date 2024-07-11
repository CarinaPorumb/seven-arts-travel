package ro.project.model.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ro.project.entity.ArtWork;
import ro.project.model.ArtWorkDTO;

@Mapper(componentModel = "spring")
public interface ArtWorkMapper extends GenericMapper<ArtWorkDTO, ArtWork> {

    @Override
    ArtWork toEntity(ArtWorkDTO dto);

    @Override
    ArtWorkDTO toDTO(ArtWork artWork);

    @Override
    void updateEntity(@MappingTarget ArtWork artWork, ArtWorkDTO dto);

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void patchEntity(@MappingTarget ArtWork artWork, ArtWorkDTO dto);

}