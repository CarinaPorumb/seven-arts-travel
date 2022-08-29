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
                .map(architecture -> new ArchitectureDTO(architecture.getName(), architecture.getImageLink(), architecture.getAuthor(), architecture.getMovement(), architecture.getIsTemporary(), architecture.getYear(), architecture.getLocation()))
                .toList());

        userDTO.setBalletAndTheatreDTOSList(user.getBalletAndTheatreSet().stream()
                .map(balletAndTheatre -> new BalletAndTheatreDTO(balletAndTheatre.getName(), balletAndTheatre.getImageLink(), balletAndTheatre.getAuthor(), balletAndTheatre.getMovement(), balletAndTheatre.getTemporary(), balletAndTheatre.getEventTime(), balletAndTheatre.getLocation()))
                .toList());

        userDTO.setCinemaDTOList(user.getCinemas().stream()
                .map(cinema -> new CinemaDTO(cinema.getName(), cinema.getImageLink(), cinema.getMovement(), cinema.getTemporary(), cinema.getEventTime(), cinema.getLocation()))
                .toList());

        userDTO.setLiteratureDTOList(user.getLiteratureSet().stream()
                .map(literature -> new LiteratureDTO(literature.getName(), literature.getImageLink(), literature.getAuthor(), literature.getMovement(), literature.getTemporary(), literature.getYear(), literature.getLocation()))
                .toList());

        userDTO.setMusicDTOList(user.getMusicSet().stream()
                .map(music -> new MusicDTO(music.getName(), music.getImageLink(), music.getAuthor(), music.getMovement(), music.getTemporary(), music.getEventTime(), music.getLocation()))
                .toList());

        userDTO.setPaintingDTOList(user.getPaintings().stream()
                .map(painting -> new PaintingDTO(painting.getName(), painting.getImageLink(), painting.getAuthor(), painting.getMovement(), painting.getTemporary(), painting.getYear(), painting.getLocation()))
                .toList());

        userDTO.setSculptureDTOList(user.getSculptures().stream()
                .map(sculpture -> new SculptureDTO(sculpture.getName(), sculpture.getImageLink(), sculpture.getAuthor(), sculpture.getMovement(), sculpture.getTemporary(), sculpture.getYear(), sculpture.getLocation()))
                .toList());

        return userDTO;

    }
}
