package ro.project.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
public class ArtistDTO {

    private UUID id;
    @NotBlank(message = "Name must not be blank")
    private String name;
    private String biography;
    private String imageLink;
    private Integer birthYear;
    private Integer deathYear;
    private String nationality;

    private Set<Long> artWorkIds = new HashSet<>();
    private Set<Long> artEventIds = new HashSet<>();

}