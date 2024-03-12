package ro.project.model.mapper;

import org.mapstruct.Mapper;
import ro.project.entity.Movement;
import ro.project.model.MovementDTO;

@Mapper
public interface MovementMapper {

    Movement dtoToMovement(MovementDTO dto);

    MovementDTO movementToDto(Movement movement);

}