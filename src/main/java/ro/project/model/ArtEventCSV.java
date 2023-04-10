package ro.project.model;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;

@Data
public class ArtEventCSV {

    @CsvBindByName
    private String name;

    @CsvBindByName
    private String location;

    @CsvBindByName
    private String category;

    @CsvBindByName
    private String movement;

    @CsvBindByName
    private boolean isTemporary;

    @CsvBindByName
    private String imageLink;
}