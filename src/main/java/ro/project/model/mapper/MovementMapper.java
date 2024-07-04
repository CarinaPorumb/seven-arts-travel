package ro.project.model.mapper;

import org.mapstruct.Mapper;
import ro.project.entity.Movement;
import ro.project.model.MovementDTO;

@Mapper(componentModel = "spring")
public interface MovementMapper {

    Movement toEntity(MovementDTO dto);

    MovementDTO toDTO(Movement movement);

}