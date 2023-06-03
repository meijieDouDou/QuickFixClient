package cicc.quickfix.client.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.File;
import java.io.FilenameFilter;
@Data
@AllArgsConstructor
public class FilterBySuffix implements FilenameFilter {
    private String suffix;


    @Override
    public boolean accept(File dir, String name) {
        return name.endsWith(suffix);
    }
}
