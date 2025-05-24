package ro.project.csv;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;

@Data
public class ArtObjectCSV {

    @CsvBindByName
    private String title;

    @CsvBindByName
    private String artistNames; // ex: "Leonardo da Vinci, Michelangelo"

    @CsvBindByName
    private String description;

    @CsvBindByName
    private String location;

    @CsvBindByName
    private Integer year;

    @CsvBindByName
    private String artCategory;

    @CsvBindByName
    private String imageUrl;

    @CsvBindByName
    private String externalUrl;

    @CsvBindByName
    private String artObjectType;

    @CsvBindByName
    private String startTime;

    @CsvBindByName
    private String endTime;

    @CsvBindByName
    private Boolean isTemporary;

    @CsvBindByName
    private String status;

    @CsvBindByName
    private String movementName;

}