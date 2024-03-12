package ro.project.model;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;

@Data
public class ArtEventCSV {

    @CsvBindByName
    private String name;

    @CsvBindByName
    private String description;

    @CsvBindByName
    private String location;

    @CsvBindByName
    private String category;

    @CsvBindByName
    private String status;

    @CsvBindByName
    private String startTime;

    @CsvBindByName
    private String endTime;

    @CsvBindByName
    private boolean isTemporary;

}