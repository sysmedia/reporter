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
        //2017-01-03大卖场75家
        /*ArrayList<String> list1 = new ArrayList<String>(Arrays.asList("1021","1025","1027","1028","1129","1163",
                "1166","1167","1169","1170","1248","1261","1264","1267","1279","1292","1309","1313","1314","1378","1389","1408","1420",
                "1434","1443","1450","1456","1457","1477","1485","1492","1512","1552","1573","1574","1579","1593"));*/
        ArrayList<String> list1 = new ArrayList<String>(Arrays.asList("1011","1015","1021","1023","1024","1025","1027","1028","1035","1041","1129","1163","1164","1166","1167","1169","1170","1171","1248","1261","1264","1267","1279","1292","1309","1310","1312","1313","1314","1364","1370","1377","1378","1389","1408","1420","1427","1433","1434","1439","1443","1450","1451","1452","1454","1456","1457","1464","1465","1469","1477","1482","1485","1492","1505","1512","1516","1530","1538","1552","1562","1565","1573","1574","1575","1579","1582","1593","1595","1617","1624","1628","8501"));
        System.out.println(list1.size());

       // sql = "SELECT id FROM  `t_new_shop` WHERE city = '重庆市'";
        sql = "SELECT id FROM  `t_new_shop` WHERE (`city` =  '广州市' or city = '深圳市' or city = '重庆市' ) AND flag =0 AND TYPE =  '大卖场'";
        ArrayList<String> list2 = DBUtil.getShopList(sql);
        System.out.println(list2.size());

        for(int i = 0 ; i < list2.size(); i++) {

            shopId = list2.get(i);
            origShopId = list1.get(i);
            System.out.println("shopid is " + shopId + " : " + origShopId);

            sql = "update t_new_shop set flag = 1 , shop1 = " + origShopId + ", year1 = 2017, month1 = '09', year2 = 2017, month2 = '10', "
                    + "shop2 = " + origShopId + " where id = '" + shopId + "'";
            System.out.println("sql is " + sql);
            conn.update(sql);

            shopName = collection.getNewDetailShopInfo(shopId, "name");
            shopType = collection.getNewDetailShopInfo(shopId, "type");
            shopSM = collection.getNewDetailShopInfo(shopId, "sm");
            city = collection.getNewDetailShopInfo(shopId, "city");
            title = shopType + "/" + shopSM + "/" + shopName;


            filename = "C:\\project\\new\\" + city + "\\" + shopSM + shopType + "\\" + shopId + "-" + shopType + "-" +  shopName + ".xlsx";
            System.out.println("Begin to get " + filename);

            ArrayList<String> list = new ArrayList<String>(Arrays.asList("门店名称", title));

            try {
                XSSFWorkbook wb = ReportUtil.generateReportByShop(origShopId, list);  //创建工作薄
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
}
