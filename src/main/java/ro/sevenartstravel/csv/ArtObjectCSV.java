package ro.sevenartstravel.csv;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import lombok.Data;
import ro.sevenartstravel.service.util.NullableIntegerConverter;

@Data
public class ArtObjectCSV {

    @CsvBindByName
    private String title;

    @CsvBindByName
    private String artistNames; // ex: "Leonardo da Vinci, Michelangelo"

    @CsvBindByName
    private String shortDescription;

    @CsvBindByName
    private String longDescription;

    @CsvBindByName
    private String location;

    @CsvCustomBindByName(converter = NullableIntegerConverter.class)
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