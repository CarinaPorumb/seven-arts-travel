package ro.sevenartstravel.mapper;

import org.mapstruct.*;
import ro.sevenartstravel.dto.MovementDTO;
import ro.sevenartstravel.entity.Movement;
import ro.sevenartstravel.service.crud.CrudMapper;

@Mapper(componentModel = "spring")
public interface MovementMapper extends CrudMapper<MovementDTO, Movement> {

    @Override
    @Mapping(target = "artObjects", ignore = true)
    Movement toEntity(MovementDTO dto);

    @Override
    MovementDTO toDTO(Movement entity);

    @Override
    void updateEntity(@MappingTarget Movement entity, MovementDTO dto);

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void patchEntity(@MappingTarget Movement entity, MovementDTO dto);

}