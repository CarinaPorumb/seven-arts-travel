package ro.project.model;

import lombok.Data;

import java.util.Set;
@Data
public class ArtistDTO {

    private Long id;
    private String name;
    private String biography;
    private String imageLink;
    private Integer birthYear;
    private Integer deathYear;
    private String nationality;

    private Set<Long> artWorkIds;
    private Set<Long> artEventIds;

}