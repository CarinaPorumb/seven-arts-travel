package ro.sevenartstravel.mapper;

import org.mapstruct.*;
import ro.sevenartstravel.dto.ArtistDTO;
import ro.sevenartstravel.entity.ArtObject;
import ro.sevenartstravel.entity.Artist;
import ro.sevenartstravel.service.crud.CrudMapper;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ArtistMapper extends CrudMapper<ArtistDTO, Artist> {

    @Override
    @Mapping(target = "artObjects", ignore = true)
    Artist toEntity(ArtistDTO dto);

    @Override
    @Mapping(target = "artObjectIds", expression = "java(mapArtObjectsToIds(entity))")
    ArtistDTO toDTO(Artist entity);

    @Override
    void updateEntity(@MappingTarget Artist entity, ArtistDTO dto);

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void patchEntity(@MappingTarget Artist entity, ArtistDTO dto);

    default Set<UUID> mapArtObjectsToIds(Artist artist) {
        if (artist.getArtObjects() == null) return Set.of();
        return artist.getArtObjects().stream()
                .map(ArtObject::getId)
                .collect(Collectors.toSet());
    }
}