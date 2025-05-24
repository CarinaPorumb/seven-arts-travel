package ro.project.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ro.project.dto.ContactMessageDTO;
import ro.project.entity.ContactMessage;
import ro.project.service.crud.CrudMapper;

@Mapper(componentModel = "spring")
public interface ContactMessageMapper extends CrudMapper<ContactMessageDTO, ContactMessage> {

    @Override
    ContactMessage toEntity(ContactMessageDTO dto);

    @Override
    ContactMessageDTO toDTO(ContactMessage contactMessage);

    @Override
    void updateEntity(@MappingTarget ContactMessage entity, ContactMessageDTO dto);

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void patchEntity(@MappingTarget ContactMessage entity, ContactMessageDTO dto);

}