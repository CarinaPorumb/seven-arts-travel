package ro.project.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import ro.project.entity.Role;

import java.util.*;

@Data
public class UserDTO {

    private UUID id;
    @NotBlank(message = "Username must not be blank ")
    private String username;
    @NotBlank(message = "Full name must not be blank ")
    private String fullName;
    @NotBlank(message = "Email must not be blank ")
    private String email;

    private List<Role> roles = new ArrayList<>();
    private Set<ArtWorkDTO> artObjectSet = new HashSet<>();
    private List<ArtEventDTO> artEventDTOList = new ArrayList<>();

}