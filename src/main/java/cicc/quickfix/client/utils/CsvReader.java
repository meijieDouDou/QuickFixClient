package cicc.quickfix.client.utils;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvReader {
    public static List<String> getCsvData(){
        String file = "D:/test.csv";
        List<String> dataString = new ArrayList<String>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            // 不输出第一行
            reader.readLine();
            String line ="";
            while ((line = reader.readLine()) != null){
                dataString.add(line);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (reader!= null){
                try{
                    reader.close();
                    reader = null;
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        }

        // TODO 加入jmeter
        return dataString;
    }


}
