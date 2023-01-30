package ro.itschool.controller.mapper;

import ro.itschool.controller.model.ArtEventDTO;
import ro.itschool.controller.model.ArtObjectDTO;
import ro.itschool.controller.model.UserDTO;
import ro.itschool.entity.Role;
import ro.itschool.entity.User;

public class UserMapper {

    public static UserDTO convertToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setFullName(user.getFullName());

        userDTO.setRoles(user.getRoles().stream()
                .map(Role::getName)
                .toList());

        userDTO.setArtObjectDTOList(user.getArtObjectSet().stream()
                .map(artObject -> new ArtObjectDTO(artObject.getName(), artObject.getImageLink(), artObject.getAuthor(), artObject.getYear(), artObject.getCategory(), artObject.getMovement(), artObject.getIsTemporary(), artObject.getLocation()))
                .toList());

        userDTO.setArtEventDTOList(user.getArtEventSet().stream()
                .map(artEvent -> new ArtEventDTO(artEvent.getName(), artEvent.getImageLink(), artEvent.getLocation(), artEvent.getCategory(), artEvent.getMovement(), artEvent.getEventTime(), artEvent.getIsTemporary()))
                .toList());

        return userDTO;
    }
}