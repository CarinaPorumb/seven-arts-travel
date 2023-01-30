package ro.itschool.controller.model;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import ro.itschool.enums.Category;
import ro.itschool.enums.Style;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ArtObjectDTO {

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

}