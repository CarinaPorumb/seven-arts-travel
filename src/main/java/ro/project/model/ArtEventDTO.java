package ro.project.model;

import lombok.Data;
import ro.project.enums.Category;
import ro.project.enums.Status;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class ArtEventDTO {

    private Long id;
    private String name;
    private String imageLink;
    private String description;
    private String location;
    private Status status;
    private Category category;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Boolean isTemporary;

    private Set<Long> movementIds;
    private Set<Long> artistIds;
    private Set<Long> favoritedByIds;

}
