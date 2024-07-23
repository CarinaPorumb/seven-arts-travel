package ro.project.model.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ro.project.entity.Contact;
import ro.project.model.ContactDTO;

@Mapper(componentModel = "spring")
public interface ContactMapper {

    Contact toEntity(ContactDTO dto);

    ContactDTO toDTO(Contact entity);

    void updateEntity(@MappingTarget Contact entity, ContactDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void patchEntity(@MappingTarget Contact entity, ContactDTO dto);

}