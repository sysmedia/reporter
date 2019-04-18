package com.sysmedia.spark.reporter.util;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class TVReportUtil {
    private static  DataBaseConnection conn = new DataBaseConnection();;
    public static XSSFWorkbook generateTVReport(String day) {
        XSSFWorkbook wb = new XSSFWorkbook();  //创建工作薄
        XSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setBorderBottom(BorderStyle.MEDIUM);
        cellStyle.setBorderLeft(BorderStyle.MEDIUM);
        cellStyle.setBorderRight(BorderStyle.MEDIUM);
        cellStyle.setBorderTop(BorderStyle.MEDIUM);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setWrapText(true);

        createBrowseSheet(wb, day, cellStyle);
        createRegisterSheet(wb, day, cellStyle);
        wb.createSheet("发券");
        wb.createSheet("用券");
        wb.createSheet("销售量销售额");
        wb.createSheet("竞品销售量销售额");
        return wb;
    }

    public static void createBrowseSheet(XSSFWorkbook wb, String  day, XSSFCellStyle cellStyle) {
        XSSFSheet sheet = wb.createSheet("浏览");
        sheet.setDefaultColumnWidth(15);
        ArrayList<String> list = new ArrayList<String>(Arrays.asList("日期","渠道","浏览量"));
        int line = ExcelUtil.inputSingleLine(sheet, list, cellStyle, 0);
        String query = "select * from ic_user_view_count where day = '" + day + "' order by view_count";
        ArrayList<ArrayList<String>> results = conn.getQueryResultList2(query, 3);
        HashMap<String, String> map = getChannelMap();
        ArrayList<ArrayList<String>> lists = getChannelList(results, map);
        ExcelUtil.inputMultiLine(sheet, lists, cellStyle, line);
    }

    public static void createRegisterSheet(XSSFWorkbook wb, String  day, XSSFCellStyle cellStyle) {
        XSSFSheet sheet = wb.createSheet("注册");
        sheet.setDefaultColumnWidth(15);
        ArrayList<String> list = new ArrayList<String>(Arrays.asList("日期","注册量"));
        int line = ExcelUtil.inputSingleLine(sheet, list, cellStyle, 0);
        String query = "select register_count from ic_user_register_count where day = '" + day + "'";
        String result = conn.getQueryResult(query);
        list = new ArrayList<String>(Arrays.asList(day,result));
        ExcelUtil.inputSingleLine(sheet, list, cellStyle, line);
    }

    public static HashMap<String, String> getChannelMap(){
        HashMap<String, String> map = new HashMap<String, String>();
        ArrayList<ArrayList<String>> lists = conn.getQueryResultList2("select * from t_channel_info", 2);
        for(ArrayList<String> list: lists){
            map.put(list.get(0), list.get(1));
        }
        return map;
    }

    public static ArrayList<ArrayList<String>> getChannelList(ArrayList<ArrayList<String>> results, HashMap<String, String> map){
        ArrayList<ArrayList<String>> lists = new ArrayList<ArrayList<String>>();
        ArrayList<String> result ;
        for(ArrayList<String> list: results){
            result = new ArrayList<String>();
            result.add(list.get(0));
            String channelName = map.get(list.get(1));
            result.add(channelName);
            result.add(list.get(2));
            lists.add(result);
        }
        return lists;
    }
}
