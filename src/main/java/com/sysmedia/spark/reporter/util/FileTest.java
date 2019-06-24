package com.sysmedia.spark.reporter.util;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class FileTest {
    public static void main(String[] args) {
        File root = new File("C:\\project\\new\\ip-list.txt");
        String str  = null;
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        try {
            BufferedReader br=new BufferedReader(new FileReader("C:\\project\\new\\ip-list.txt"));
            do{
                str = br.readLine();
                if(map.get(str) != null) {
                    map.put(str, map.get(str) + 1);
                } else {
                    map.put(str, 1);
                }

                System.out.println(str);
            }while(br.read()!=-1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(map);



        try {
            FileOutputStream out = new FileOutputStream("C:\\project\\new\\result1.txt");
            OutputStreamWriter outWriter = new OutputStreamWriter(out, "UTF-8");
            BufferedWriter bufWrite = new BufferedWriter(outWriter);
            for(Map.Entry entry : map.entrySet()){

                bufWrite.write(entry.getKey() + " : " + entry.getValue() + "\r\n");
            }

            bufWrite.close();
            outWriter.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("读取"  + "出错！");
        }


    }
}
