package ro.project.model;

import lombok.Data;

import java.util.Set;

@Data
public class MovementDTO {

    private Long id;
    private String name;
    private String description;
    private Integer startYear;
    private Integer endYear;
    private Set<Long> artWorkIds;
    private Set<Long> artEventIds;

}