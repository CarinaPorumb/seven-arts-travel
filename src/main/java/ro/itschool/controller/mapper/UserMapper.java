package ro.itschool.controller.mapper;

import ro.itschool.controller.model.*;
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

        userDTO.setArchitectureDTOList(user.getArchitectureSet().stream()
                .map(architecture -> new ArchitectureDTO(architecture.getName(), architecture.getAuthor(), architecture.getMovement(), architecture.getTemporary(), architecture.getYear(), architecture.getLocation()))
                .toList());

        userDTO.setBalletAndTheatreDTOSList(user.getBalletAndTheatreSet().stream()
                .map(balletAndTheatre -> new BalletAndTheatreDTO(balletAndTheatre.getName(), balletAndTheatre.getAuthor(), balletAndTheatre.getMovement(), balletAndTheatre.getIsTemporary(), balletAndTheatre.getEventTime(), balletAndTheatre.getLocation()))
                .toList());

        userDTO.setCinemaDTOList(user.getCinemas().stream()
                .map(cinema -> new CinemaDTO(cinema.getName(), cinema.getMovement(), cinema.getIsTemporary(), cinema.getEventTime(), cinema.getLocation()))
                .toList());

        userDTO.setLiteratureDTOList(user.getLiteratureSet().stream()
                .map(literature -> new LiteratureDTO(literature.getName(), literature.getAuthor(), literature.getMovement(), literature.getIsTemporary(), literature.getYear(), literature.getLocation()))
                .toList());

        userDTO.setMusicDTOList(user.getMusicSet().stream()
                .map(music -> new MusicDTO(music.getName(), music.getMovement(), music.getIsTemporary(), music.getEventTime(), music.getLocation()))
                .toList());

        userDTO.setPaintingDTOList(user.getPaintings().stream()
                .map(painting -> new PaintingDTO(painting.getName(), painting.getAuthor(), painting.getMovement(), painting.getIsTemporary(), painting.getYear(), painting.getLocation()))
                .toList());

        userDTO.setSculptureDTOList(user.getSculptures().stream()
                .map(sculpture -> new SculptureDTO(sculpture.getName(), sculpture.getAuthor(), sculpture.getMovement(), sculpture.getIsTemporary(), sculpture.getYear(), sculpture.getLocation()))
                .toList());

        return userDTO;

    }
}
