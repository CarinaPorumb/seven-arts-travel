package ro.project.model;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import ro.project.enums.Category;
import ro.project.enums.Style;

import java.util.List;

@Data
public class ArtObjectDTO {

    private Long id;
    private String name;
    private String imageLink;
    private String author;
    private Integer year;
    @Enumerated(EnumType.STRING)
    private Category category;
    @Enumerated(EnumType.STRING)
    private Style movement;
    private Boolean isTemporary;
    private String location;
    private List<UserDTO> users;

}