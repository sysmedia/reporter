package com.sysmedia.spark.reporter.util;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class TVReportUtil {
    public static XSSFWorkbook generateTVReport(String day) {
        XSSFWorkbook wb = new XSSFWorkbook();  //创建工作薄
        createBrowseSheet(wb, day);
        wb.createSheet("注册");
        wb.createSheet("发券");
        wb.createSheet("用券");
        wb.createSheet("销售量销售额");
        wb.createSheet("竞品销售量销售额");
        return wb;
    }

    public static void createBrowseSheet(XSSFWorkbook wb, String  day) {
        wb.createSheet("浏览");
    }
}
