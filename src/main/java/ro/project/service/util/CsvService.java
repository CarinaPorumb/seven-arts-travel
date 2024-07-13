package ro.project.service.util;

import java.io.File;

public interface CsvService<T> {

    void convertCSV(File file);

}