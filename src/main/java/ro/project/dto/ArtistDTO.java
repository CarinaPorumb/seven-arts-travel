package ro.project.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import ro.project.enums.Nationality;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
public class ArtistDTO {

    private UUID id;
    @NotBlank(message = "Name must not be blank")
    private String name;
    private String biography;
    private String imageUrl;

    @Min(value = -1000, message = "Birth year must be a realistic historical value (min: 1000 BC written -1000)")
    @Max(value = 2025, message = "Birth year cannot be in future")
    private Integer birthYear;

    @Min(value = -1000, message = "Death year must be a realistic historical value (min: 1000 BC written -1000)")
    @Max(value = 2025, message = "Death year cannot be in future")
    private Integer deathYear;

    private Nationality nationality;

    private Set<UUID> artObjectIds = new HashSet<>();

}