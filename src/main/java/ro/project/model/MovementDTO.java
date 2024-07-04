package ro.project.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
public class MovementDTO {

    private UUID id;
    @NotBlank(message = "Name must not be blank")
    private String name;
    private String description;
    private Integer startYear;
    private Integer endYear;
    private Set<Long> artWorkIds = new HashSet<>();
    private Set<Long> artEventIds = new HashSet<>();

}