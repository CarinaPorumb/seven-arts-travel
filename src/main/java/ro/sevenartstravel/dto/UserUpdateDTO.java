package ro.sevenartstravel.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
public class UserUpdateDTO {

    @NotNull(message = "ID must not be null")
    private UUID id;

    @NotBlank(message = "Username must not be blank")
    private String username;

    @NotBlank(message = "Full name must not be blank")
    private String fullName;

    @NotBlank(message = "Email must not be blank")
    @Email(message = "Email must be valid")
    private String email;

    private String password;

    private Set<String> roles;

}