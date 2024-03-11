package ro.project.model;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import ro.project.enums.Category;

import java.util.List;

@Data
public class ArtWorkDTO {

    private Long id;
    private String name;
    private String imageLink;
    private String author;
    private Integer year;
    @Enumerated(EnumType.STRING)
    private Category category;
    private String location;
    private List<UserDTO> users;

}