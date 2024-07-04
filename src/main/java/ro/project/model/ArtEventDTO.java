package ro.project.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import ro.project.enums.Category;
import ro.project.enums.Status;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
public class ArtEventDTO {

    private UUID id;
    @NotBlank(message = "Name must not be blank")
    private String name;
    private String imageLink;
    private String description;
    private String location;
    private Status status;
    private Category category;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Boolean isTemporary;

    private Set<Long> movementIds = new HashSet<>();
    private Set<Long> artistIds = new HashSet<>();
    private Set<Long> favoritedByIds = new HashSet<>();

}