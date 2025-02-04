package ro.project.model.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ro.project.entity.Artist;
import ro.project.model.ArtistDTO;
import ro.project.service.crud.CrudMapper;

@Mapper(componentModel = "spring")
public interface ArtistMapper extends CrudMapper<ArtistDTO, Artist> {

    @Override
    Artist toEntity(ArtistDTO dto);

    @Override
    ArtistDTO toDTO(Artist entity);

    @Override
    void updateEntity(@MappingTarget Artist entity, ArtistDTO dto);

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void patchEntity(@MappingTarget Artist entity, ArtistDTO dto);

}