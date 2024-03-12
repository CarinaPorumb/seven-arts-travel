package ro.project.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ContactDTO {

    private Long id;
    private String username;
    private String email;
    private String subject;
    private String message;
    private LocalDate date;

}