package ro.sevenartstravel.mapper;

import org.mapstruct.*;
import ro.sevenartstravel.dto.ArtObjectDTO;
import ro.sevenartstravel.entity.ArtObject;
import ro.sevenartstravel.entity.Artist;
import ro.sevenartstravel.service.crud.CrudMapper;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ArtObjectMapper extends CrudMapper<ArtObjectDTO, ArtObject> {

    @Override
    @Mapping(target = "artists", ignore = true)
    @Mapping(target = "movement", ignore = true)
    ArtObject toEntity(ArtObjectDTO dto);


    @Override
    @Mapping(target = "artistIds", expression = "java(mapArtistsToIds(artObject))")
    @Mapping(target = "movementId", expression = "java(mapMovementToId(artObject))")
    ArtObjectDTO toDTO(ArtObject artObject);

    @Override
    void updateEntity(@MappingTarget ArtObject entity, ArtObjectDTO dto);

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void patchEntity(@MappingTarget ArtObject entity, ArtObjectDTO dto);

    default Set<UUID> mapArtistsToIds(ArtObject entity) {
        if (entity.getArtists() == null) return Set.of();
        return entity.getArtists().stream()
                .map(Artist::getId)
                .collect(Collectors.toSet());
    }

    default UUID mapMovementToId(ArtObject entity) {
        return entity.getMovement() != null ? entity.getMovement().getId() : null;
    }
}
