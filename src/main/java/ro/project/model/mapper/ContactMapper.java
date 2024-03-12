package ro.project.model.mapper;

import org.mapstruct.Mapper;
import ro.project.entity.Contact;
import ro.project.model.ContactDTO;

@Mapper
public interface ContactMapper {

    Contact dtoToContact(ContactDTO dto);

    ContactDTO contactToDto(Contact contact);

}