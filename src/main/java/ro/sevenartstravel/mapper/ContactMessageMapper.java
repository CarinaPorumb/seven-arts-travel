package ro.sevenartstravel.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ro.sevenartstravel.dto.ContactMessageDTO;
import ro.sevenartstravel.entity.ContactMessage;
import ro.sevenartstravel.service.crud.CrudMapper;

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