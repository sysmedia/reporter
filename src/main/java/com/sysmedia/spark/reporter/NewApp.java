package com.sysmedia.spark.reporter;

import com.sysmedia.spark.reporter.util.DBUtil;
import com.sysmedia.spark.reporter.util.DataBaseConnection;
import com.sysmedia.spark.reporter.util.DataCollection;
import com.sysmedia.spark.reporter.util.ReportUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;

import static com.sysmedia.spark.reporter.util.DBUtil.getShopList;

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
        String origShopId = args[1];



        String sql = "select id from t_new_shop where flag = 1 and (type = '北京标大' or type = '北京大卖场' or type = '北京标中')";
        ArrayList<String> list1 = DBUtil.getShopList(sql);
        System.out.println(list1);

        sql = "select id  from t_new_shop where city = '重庆市'";
        ArrayList<String> list2 = DBUtil.getShopList(sql);
        System.out.println(list2);

        for(int i = 0 ; i < list2.size(); i++) {

            shopId = list2.get(i);
            origShopId = list1.get(i);
            System.out.println("shopid is " + shopId);

            shopName = collection.getNewDetailShopInfo(shopId, "name");
            shopType = collection.getNewDetailShopInfo(shopId, "type");
            shopSM = collection.getNewDetailShopInfo(shopId, "sm");
            city = collection.getNewDetailShopInfo(shopId, "city");
            title = shopType + "/" + shopSM + "/" + shopName;


            filename = "C:\\project\\new\\" + city + "\\" + shopSM + shopType + "\\" + shopId + "-" + shopType + "-" +  shopName + ".xlsx";
            System.out.println("Begin to get " + filename);

            ArrayList<String> list = new ArrayList<String>(Arrays.asList("门店名称", title));

           /* try {
                XSSFWorkbook wb = ReportUtil.generateReportByShop(origShopId, list);  //创建工作薄
                FileOutputStream out = new FileOutputStream(filename);
                wb.write(out);
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }*/
            long end = System.currentTimeMillis();
            System.out.println("It costs " + (end - start) / 1000);
        }

    }
}
