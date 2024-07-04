package ro.project.model.mapper;

import org.mapstruct.Mapper;
import ro.project.entity.Contact;
import ro.project.model.ContactDTO;

@Mapper(componentModel = "spring")
public interface ContactMapper {

    Contact toEntity(ContactDTO dto);

    ContactDTO toDTO(Contact contact);

}