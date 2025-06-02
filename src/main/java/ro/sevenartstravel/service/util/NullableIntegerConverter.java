package ro.sevenartstravel.service.util;

import com.opencsv.bean.AbstractBeanField;

public class NullableIntegerConverter extends AbstractBeanField<Integer, String> {

    @Override
    protected Integer convert(String value) {

        if (value == null || value.trim().isEmpty() || value.trim().equalsIgnoreCase("null")) {
            return null;
        }

        return Integer.parseInt(value.trim());
    }

}