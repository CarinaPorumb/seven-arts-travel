package ro.project.model;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import ro.project.enums.Category;
import ro.project.enums.Style;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ArtEventDTO {

    private Integer id;
    private String name;
    private String imageLink;
    private String location;
    @Enumerated(EnumType.STRING)
    private Category category;
    @Enumerated(EnumType.STRING)
    private Style movement;
    private LocalDateTime eventTime;
    private Boolean isTemporary;
    private List<UserDTO> users;

}