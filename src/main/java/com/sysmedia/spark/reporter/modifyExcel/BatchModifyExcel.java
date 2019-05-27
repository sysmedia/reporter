package com.sysmedia.spark.reporter.modifyExcel;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

public class BatchModifyExcel {

    public static void main(String[] args) {
        //模板路径
        String modelPath="C:\\project\\new\\sample.xlsx";
        //sheet的名字
        String sheetName="sheet1";
        XSSFSheet sheet ;
        HashMap<String , String > map = new HashMap<String, String>();
        map.put("康师傅", "老干妈");
        try {
            File file = new File(modelPath);
            if(!file.exists()){
                System.out.println("模板文件:"+modelPath+"不存在!");
            }
            FileInputStream fs = new FileInputStream(file);
            XSSFWorkbook wb = new XSSFWorkbook(fs);
            sheet = wb.getSheet(sheetName);
            replaceExcelDate(map, sheet);
            FileOutputStream out = new FileOutputStream("C:\\project\\new\\sample1.xlsx");
            wb.write(out);
            out.close();
        } catch ( Exception e) {
            e.printStackTrace();
        }
        //从heet中获取行数

    }



    public static void setCellStrValue(int rowIndex, int cellnum, String value, XSSFSheet sheet) {

        XSSFCell cell = sheet.getRow(rowIndex).getCell(cellnum);

        cell.setCellValue(value);

    }

    public static void replaceExcelDate(HashMap<String, String> param, XSSFSheet sheet){

        // 获取行数
        int rowNum = sheet.getLastRowNum();
        for (int i = 0; i < rowNum; i++) {
            XSSFRow row = sheet.getRow(i);
            // 获取行里面的总列数
            int columnNum = 0;
            if(row!=null){
                columnNum = row.getPhysicalNumberOfCells();
            }
            for (int j = 0; j < columnNum; j++) {
                XSSFCell cell = sheet.getRow(i).getCell(j);
                String cellValue = cell.getStringCellValue();
                for (Map.Entry<String, String> entry : param.entrySet()) {
                    String key = entry.getKey();
                    if(key.equals(cellValue)){
                        String value = entry.getValue();
                        setCellStrValue(i, j, value, sheet);
                    }
                }
            }
        }
    }
}
