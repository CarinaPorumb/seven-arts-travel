package ro.project.model;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;

@Data
public class ArtistCSV {

    @CsvBindByName
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
    private String nationality;

}