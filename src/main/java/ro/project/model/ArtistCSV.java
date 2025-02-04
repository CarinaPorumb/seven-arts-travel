package ro.project.model;

import com.opencsv.bean.CsvBindByName;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import ro.project.enums.Nationality;

@Data
public class ArtistCSV {

    @CsvBindByName
    @NotBlank(message = "Name must not be blank")
    private String name;
    @CsvBindByName
    private String biography;
    @CsvBindByName
    private String imageLink;
    @CsvBindByName
    private Integer birthYear;
    @CsvBindByName
    private Integer deathYear;
    @CsvBindByName
    private Nationality nationality;

}