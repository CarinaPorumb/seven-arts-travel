package ro.project.service.util;

import java.io.File;
import java.util.List;

public interface CsvService<T> {

    List<T> convertCSV(File file);

}