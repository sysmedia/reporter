package com.sysmedia.spark.reporter.util;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.*;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class ExcelUtil {
    public static void main(String[] args) throws IOException {
        ArrayList<String> list = new ArrayList<String>(Arrays.asList("门店名称", "快消/连锁商超/物美/玉蜓桥店"));
        ArrayList<String> secondLine = new ArrayList<String>(Arrays.asList("数据日期", "2017年11月","2018年11月","2017年11月工作日",
                "2018年11月工作日","2017年11月周末","2018年11月周末"));
       // createExcel(list);

        XSSFWorkbook wb = new XSSFWorkbook();  //创建工作薄
        XSSFSheet sheet = wb.createSheet("sheet1"); //创建工作表
        sheet.setDefaultColumnWidth(15);
        XSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setBorderBottom(BorderStyle.MEDIUM);
        cellStyle.setBorderLeft(BorderStyle.MEDIUM);
        cellStyle.setBorderRight(BorderStyle.MEDIUM);
        cellStyle.setBorderTop(BorderStyle.MEDIUM);
        cellStyle.setWrapText(true);

    }

    public static int inputSingleLineExcel(int line, XSSFSheet sheet, ArrayList<String> list, XSSFCellStyle cellStyle){
        XSSFRow row = sheet.createRow(line); //行
        Cell cell;
        String value = list.get(0);
        for(int i = 0; i<2; i++) {
            cell = row.createCell(i);
            cell.setCellValue(value);
            cell.setCellStyle(cellStyle);
        }
        // 合并单元格
        CellRangeAddress cra =new CellRangeAddress(line, line, 0, 1); // 起始行, 终止行, 起始列, 终止列
        sheet.addMergedRegion(cra);
     //   setRegionBorder(cra, sheet);


        for(int i = 2; i<8; i++) {
            value = list.get(i - 1);
            cell = row.createCell(i);
            cell.setCellValue(value);
            cell.setCellStyle(cellStyle);
        }


        return line + 1;
    }

    public static int inputMultiLineExcel(int line, XSSFSheet sheet, ArrayList<ArrayList<String>> lists, XSSFCellStyle cellStyle){
        XSSFRow row ; //行
        Cell cell;
        ArrayList<String> list = new ArrayList<String>();
        String value ;
        int lastRow = line;
        for(int j = 0; j < lists.size(); j++) {
            row = sheet.createRow(line + j);
            list = lists.get(j);
            for(int i = 0; i<list.size(); i++) {
                value = list.get(i);
                cell = row.createCell(i);
                cell.setCellValue(value);
                cell.setCellStyle(cellStyle);
            }
            lastRow++;
        }

        // 合并单元格
        CellRangeAddress cra =new CellRangeAddress(line, lastRow - 1, 0, 0); // 起始行, 终止行, 起始列, 终止列
        sheet.addMergedRegion(cra);
      //  setRegionBorder(cra, sheet);
        return lastRow;
    }

    public static int inputFirstLineExcel(XSSFSheet sheet, ArrayList<String> list, XSSFCellStyle cellStyle){
        XSSFRow row = sheet.createRow(0); //行
        String value = list.get(0);
        Cell cell;
        for(int i = 0; i<2; i++) {
            cell = row.createCell(i);
            cell.setCellValue(value);
            cell.setCellStyle(cellStyle);
        }
        // 合并单元格
        CellRangeAddress cra =new CellRangeAddress(0, 0, 0, 1); // 起始行, 终止行, 起始列, 终止列
        sheet.addMergedRegion(cra);
       // setRegionBorder(cra, sheet);

        value = list.get(1);
        for(int i = 2; i<8; i++) {
            cell = row.createCell(i);
            cell.setCellValue(value);
            cell.setCellStyle(cellStyle);
        }
        // 合并单元格

        cra =new CellRangeAddress(0, 0, 2, 7); // 起始行, 终止行, 起始列, 终止列
        sheet.addMergedRegion(cra);
       // setRegionBorder(cra, sheet);

        return 1;
    }

   /* private static void setRegionBorder(CellRangeAddress cra, XSSFSheet sheet){
        RegionUtil.setBorderBottom(BorderStyle.MEDIUM, cra, sheet); // 下边框
        RegionUtil.setBorderLeft(BorderStyle.MEDIUM, cra, sheet); // 左边框
        RegionUtil.setBorderRight(BorderStyle.MEDIUM, cra, sheet); // 有边框
        RegionUtil.setBorderTop(BorderStyle.MEDIUM, cra, sheet); // 上边框)
    }*/

    private static void inputExcel(XSSFSheet sheet, ArrayList<String> list) {
        XSSFRow row = sheet.createRow(0); //行
        for (int k = 0; k < 3; k++) {
            //添加表头数据
            row = sheet.createRow(k);
            for (int i = 0; i < list.size(); i++) {
                //从前端接受到的参数封装成list集合，然后遍历下标从而取出对应的值
                String value = list.get(i);
                //将取到的值依次写到Excel的第一行的cell中
                row.createCell(i).setCellValue(value);
            }
        }
        // 合并单元格
        sheet.addMergedRegion(new CellRangeAddress(1, // 第一行（0）
                2, // last row（0-based）
                0, // 第一列（基于0）
                1 // 最后一列（基于0）
        ));


    }

    private static void createExcel(ArrayList<String> list) {

        //获取当前时间
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
        String date = df.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳

        //存储路径--获取桌面位置
        FileSystemView view = FileSystemView.getFileSystemView();
        File directory = view.getHomeDirectory();
        System.out.println(directory);
        //存储Excel的路径
        String path = directory+"\\"+date+".xlsx";
        System.out.println(path);
        try {

            //定义一个Excel表格
            XSSFWorkbook wb = new XSSFWorkbook();  //创建工作薄
            XSSFSheet sheet = wb.createSheet("sheet1"); //创建工作表
            XSSFRow row = sheet.createRow(0); //行
            XSSFCell cell;  //单元格
            for (int k = 0; k < 3; k++) {
                //添加表头数据
                row = sheet.createRow(k);
                for (int i = 0; i < list.size(); i++) {
                    //从前端接受到的参数封装成list集合，然后遍历下标从而取出对应的值
                    String value = list.get(i);
                    //将取到的值依次写到Excel的第一行的cell中
                    row.createCell(i).setCellValue(value);
                }
            }
            // 合并单元格
            sheet.addMergedRegion(new CellRangeAddress(1, // 第一行（0）
                    2, // last row（0-based）
                    0, // 第一列（基于0）
                    1 // 最后一列（基于0）
            ));

            //输出流,下载时候的位置
//            FileWriter outputStream1 = new FileWriter(path);
            FileOutputStream outputStream = new FileOutputStream(path);
            wb.write(outputStream);
            outputStream.flush();
            outputStream.close();
            System.out.println("写入成功");
        } catch (Exception e) {
            System.out.println("写入失败");
            e.printStackTrace();
        }
    }

}
