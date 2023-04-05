package ro.project.model;

import lombok.Data;
import ro.project.entity.Role;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserDTO {

    private Long id;
    private String username;
    private String fullName;
    private String email;

    private List<Role> roles = new ArrayList<>();
    private List<ArtObjectDTO> artObjectDTOList = new ArrayList<>();
    private List<ArtEventDTO> artEventDTOList = new ArrayList<>();

}