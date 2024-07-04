package ro.project.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class ContactDTO {

    private UUID id;
    @NotBlank(message = "Username must not be blank")
    private String username;
    @NotBlank(message = "Email must not be blank")
    private String email;
    @NotBlank(message = "Subject must not be blank")
    private String subject;
    @NotBlank(message = "Message must not be blank")
    private String message;
    private LocalDate date;

}