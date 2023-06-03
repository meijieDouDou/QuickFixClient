package cicc.quickfix.client.utils;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class FileSelector {
    public static void main(String[] args) {
        File file = new File("src/main/resources");
        FilterBySuffix filter = new FilterBySuffix(".log");
        String[] files = file.list(filter);
        assert files != null;
        System.out.println(Arrays.toString(files));
    }

    public String[] dofileSelector(String pathname){
        File file = new File(pathname);
        FilterBySuffix filter = new FilterBySuffix(".log");
        return file.list(filter);
    }
}
