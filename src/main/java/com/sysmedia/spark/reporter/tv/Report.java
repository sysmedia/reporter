package com.sysmedia.spark.reporter.tv;

import com.sysmedia.spark.reporter.util.ReportUtil;
import com.sysmedia.spark.reporter.util.TVReportUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;

public class Report {

    public static void main(String[] args) {
        String day = "2019-04-25";
        System.out.println("begin to create new report");
        String filename = "C:\\project\\tv\\" + day + ".xlsx";
        try {
            XSSFWorkbook wb = TVReportUtil.generateTVReport(day);  //创建工作薄

            FileOutputStream out = new FileOutputStream(filename);
            System.out.println(filename + " is created");
            wb.write(out);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
