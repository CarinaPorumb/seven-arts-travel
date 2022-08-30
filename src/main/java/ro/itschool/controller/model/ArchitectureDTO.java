package ro.itschool.controller.model;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import ro.itschool.enums.Style;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ArchitectureDTO {

    private String name;

    private String imageLink;

    private String author;

    @Enumerated(EnumType.STRING)
    private Style movement;

    private Boolean isTemporary;

    private Integer year;

    private String location;


}
