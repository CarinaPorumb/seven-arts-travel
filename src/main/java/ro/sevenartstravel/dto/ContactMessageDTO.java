package ro.sevenartstravel.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.UUID;

@Data
public class ContactMessageDTO {

    private UUID id;
    @NotBlank(message = "Username must not be blank")
    private String username;
    @NotBlank(message = "Email must not be blank")
    @Email(message = "Email should be valid")
    private String email;
    @NotBlank(message = "Subject must not be blank")
    private String subject;
    @NotBlank(message = "Message must not be blank")
    private String message;

}