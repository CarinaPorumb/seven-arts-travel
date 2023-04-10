package ro.project.model;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;

@Data
public class ArtObjectCSV {

    @CsvBindByName
    private String name;

    @CsvBindByName
    private String author;

    @CsvBindByName
    private String location;

    @CsvBindByName
    private Integer year;

    @CsvBindByName
    private String category;

    @CsvBindByName
    private String movement;

    @CsvBindByName
    private boolean isTemporary;

    @CsvBindByName
    private String imageLink;
}