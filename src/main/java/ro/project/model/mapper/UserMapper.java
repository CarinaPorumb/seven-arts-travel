package ro.project.model.mapper;

import org.mapstruct.Mapper;
import ro.project.entity.User;
import ro.project.model.UserDTO;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserDTO dto);

    UserDTO toDTO(User user);

}