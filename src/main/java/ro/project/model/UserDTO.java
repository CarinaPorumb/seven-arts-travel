package ro.project.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import ro.project.entity.Role;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Data
public class UserDTO {

    private Long id;
    private String username;
    private String fullName;
    private String email;

    private List<Role> roles = new ArrayList<>();
    private Set<ArtWorkDTO> artObjectSet = new HashSet<>();
    private List<ArtEventDTO> artEventDTOList = new ArrayList<>();

}