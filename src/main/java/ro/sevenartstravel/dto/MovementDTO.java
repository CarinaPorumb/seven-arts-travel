package ro.sevenartstravel.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class MovementDTO {

    private UUID id;
    private String name;
    private String description;
    private Integer startYear;
    private Integer endYear;
    private String originRegion;

}