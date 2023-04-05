package ro.project.model.mapper;

import org.mapstruct.Mapper;
import ro.project.entity.User;
import ro.project.model.UserDTO;

@Mapper
public interface UserMapper {

    User dtoToUser(UserDTO dto);

    UserDTO userToDto(User user);

}