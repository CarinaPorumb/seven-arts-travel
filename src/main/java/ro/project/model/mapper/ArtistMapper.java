package ro.project.model.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ro.project.entity.Artist;
import ro.project.model.ArtistDTO;

@Mapper(componentModel = "spring")
public interface ArtistMapper {

    Artist toEntity(ArtistDTO dto);

    ArtistDTO toDTO(Artist entity);

    void updateEntity(@MappingTarget Artist entity, ArtistDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void patchEntity(@MappingTarget Artist entity, ArtistDTO dto);

}