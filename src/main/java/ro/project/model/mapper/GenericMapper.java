package ro.project.model.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface GenericMapper<D, E> {

    E toEntity(D dto);

    D toDTO(E entity);

    void updateEntity(@MappingTarget E entity, D dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void patchEntity(@MappingTarget E entity, D dto);

}