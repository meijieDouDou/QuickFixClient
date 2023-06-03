package cicc.quickfix.client.utils;

import cicc.quickfix.client.common.constant.MapConstant;
import cicc.quickfix.client.reuse.NewOrderSingleNew;
import quickfix.field.*;

import java.io.*;
import java.util.*;

public class TxtReader {
    public static List<String> getTxtData(File file) throws IOException {
        List<String> dataString = new ArrayList<String>(100);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String line = null;
        while ((line=bufferedReader.readLine())!=null){
            dataString.add(line);
        }
        bufferedReader.close();
        return dataString;

    }

    public static void main(String[] args) throws IOException {


        TimeTransfer timeTransfer = new TimeTransfer();
        String pathname = "src/main/resources";
        FileSelector fs = new FileSelector();
        List<String> fileList = Arrays.asList(fs.dofileSelector(pathname));
        System.out.println(fileList);
        File file = new File(pathname + "/" + fileList.get(1));
        List<String> results = getTxtData(file);

        List<String> standardResults = new ArrayList<>();
        for (String standardTxt : results){
            if (standardTxt.contains("")){
                String resultTxt = standardTxt.replace("","|");
                standardResults.add(resultTxt);
            } else {
                standardResults.add(standardTxt);
            }
        }
//        System.out.println(standardResults);

        MapConstant mapConstant = new MapConstant();

        int count = 0;

//        System.out.println(results);
        for(String fixdata : standardResults) {
            // 转化为数组
            String[] split = fixdata.split("\\|");
            Map<String,String> map = new HashMap<>();

            // 筛选出35=D的数据的整体报文
            if (split[2].equals("35=D")) {
                System.out.println(Arrays.toString(split));
                // 按照每个Tag进行拆分
                for (String tag : split){
                    int index = tag.indexOf("=");
                    String tagNum = tag.substring(0,index);
                    String tagContent = tag.substring(index+1);
                    // 将每组数据插入map中
                    map.put(tagNum,tagContent);


                }
                System.out.println( (map.get("21")));


            }
        }








//            if (lineData.contains("35=D") || lineData.contains("35=G") || lineData.contains("35=F")){
////                System.out.println(lineData);
//                for(String strs : lineData){
//                    String[] splitData = strs.split(",");
//                    for(String ms : splitData){
//                        int count = 0;
//                        String[] splitms = ms.split("=");
//                        count++;



    }
}
