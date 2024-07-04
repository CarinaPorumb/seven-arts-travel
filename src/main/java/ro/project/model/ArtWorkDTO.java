package ro.project.model;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import ro.project.enums.Category;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
public class ArtWorkDTO {

    private UUID id;
    @NotBlank(message = "Name must not be blank")
    private String name;
    private String imageLink;
    private String description;
    private Integer year;
    private Category category;
    private String location;

    private Set<Long> movementIds = new HashSet<>();
    private Set<Long> artistIds = new HashSet<>();
    private Set<Long> favoritedByIds = new HashSet<>();

}