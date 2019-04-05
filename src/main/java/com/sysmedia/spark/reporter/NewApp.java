package com.sysmedia.spark.reporter;

import com.sysmedia.spark.reporter.util.DataBaseConnection;
import com.sysmedia.spark.reporter.util.DataCollection;
import com.sysmedia.spark.reporter.util.ReportUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class NewApp {
    public static void main(String[] args) {
        System.out.println("args length is " + args.length);
        DataBaseConnection conn = new DataBaseConnection();
        DataCollection collection = new DataCollection();
        long start = System.currentTimeMillis();
       // String shopId = "1522";

        StringBuffer sb = new StringBuffer();
        String city, shopName, shopType, shopSM, title, filename;
        String shopId = args[0];

        shopName = collection.getNewDetailShopInfo(shopId, "name");
        shopType = collection.getNewDetailShopInfo(shopId, "type");
        shopSM = collection.getNewDetailShopInfo(shopId, "sm");
        city = collection.getNewDetailShopInfo(shopId, "city");
        title = shopType + "/" + shopSM + "/" + shopName;
        filename = "C:\\project\\new\\" + city + "\\" + shopSM + shopType + "\\" + shopId + "-" + shopType + "-" +  shopName + ".xlsx";
        System.out.println("Begin to get " + shopType + "--" + shopName);

        ArrayList<String> list = new ArrayList<String>(Arrays.asList("门店名称", title));

            try {
                XSSFWorkbook wb = ReportUtil.generateReportByShop(shopId, list);  //创建工作薄
                FileOutputStream out = new FileOutputStream(filename);
                wb.write(out);
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            long end = System.currentTimeMillis();
            System.out.println("It costs " + (end - start) / 1000);

    }
}
