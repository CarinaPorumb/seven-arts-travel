package ro.project.model.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ro.project.entity.Movement;
import ro.project.model.MovementDTO;

@Mapper(componentModel = "spring")
public interface MovementMapper {

    Movement toEntity(MovementDTO dto);

    MovementDTO toDTO(Movement entity);

    void updateEntity(@MappingTarget Movement entity, MovementDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void patchEntity(@MappingTarget Movement entity, MovementDTO dto);

}