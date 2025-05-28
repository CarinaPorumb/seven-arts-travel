package ro.sevenartstravel.service.util;

import java.io.File;

public interface CsvService<T> {

    void convertCSV(File file);

}