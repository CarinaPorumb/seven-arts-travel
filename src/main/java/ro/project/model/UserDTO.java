package ro.project.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import ro.project.entity.Role;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
public class UserDTO {

    private UUID id;

    @NotBlank(message = "Username must not be blank")
    private String username;

    @NotBlank(message = "Full name must not be blank")
    private String fullName;

    @NotBlank(message = "Email must not be blank")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Password must not be blank")
    private String password;

    private Set<Role> roles = new HashSet<>();
    private Set<ArtWorkDTO> artWorks = new HashSet<>();
    private Set<ArtEventDTO> artEvents = new HashSet<>();

}