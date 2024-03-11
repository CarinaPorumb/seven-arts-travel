package ro.project.model;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import ro.project.enums.Category;

import java.time.LocalDateTime;

@Data
public class ArtEventDTO {

    private Integer id;
    private String name;
    private String imageLink;
    private String location;
    @Enumerated(EnumType.STRING)
    private Category category;
    private LocalDateTime eventTime;
    private Boolean isTemporary;

}