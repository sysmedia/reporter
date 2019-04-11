package com.sysmedia.spark.reporter;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

import com.sysmedia.spark.reporter.util.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class App {
    public static void main(String[] args) {
        System.out.println("args length is " + args.length);
        DataBaseConnection conn = new DataBaseConnection();
        DataCollection collection = new DataCollection();
        long start = System.currentTimeMillis();
       // String shopId = "1522";

        StringBuffer sb = new StringBuffer();
        String result, shopName, shopType, shopSM, title, filename;
        ArrayList<ArrayList<String>> lists = new ArrayList<ArrayList<String>>();



        ArrayList<String> shops = new ArrayList<String>();
        if(args.length == 0) {
            shops = collection.getDistinctShopInfo();
            System.out.println(shops.size());
        } else if("999999".equals(args[0])) {
           // shops = new ArrayList<String>(Arrays.asList("1011","1015","1021","1023","1024","1025","1027","1028","1035","1041","1129","1163","1164","1166","1167","1169","1170","1171","1248","1261","1264","1267","1279","1292","1309","1310","1312","1313","1314","1364","1370","1377","1378","1389","1408","1420","1427","1433","1434","1439","1443","1450","1451","1452","1454","1456","1457","1464","1465","1469","1477","1482","1485","1492","1505","1512","1516","1530","1538","1552","1562","1565","1573","1574","1575","1579","1582","1593","1595","1605","1617","1622","1624","1628","1647","1679","8501","1012","1014","1016","1019","1032","1033","1036","1038","1039","1154","1156","1251","1252","1301","1322","1387","1391","1431","1447","1459","1507","1509","1525","1526","1564","1583","1631","1632","1633","1635","1020","1044","1067","1074","1077","1080","1084","1091","1411","1510","1537","1557","1066","1071","1132","1133","1146","1233","1280","1358","1381","1441","1470","1478","1543","1578","1590","1594","1630","1640","1646","1648","1676","1678","1680","1706","1713"));
           //73家大卖场列表
          //  shops = new ArrayList<String>(Arrays.asList("1011","1015","1021","1023","1024","1025","1027","1028","1035","1041","1129","1163","1164","1166","1167","1169","1170","1171","1248","1261","1264","1267","1279","1292","1309","1310","1312","1313","1314","1364","1370","1377","1378","1389","1408","1420","1427","1433","1434","1439","1443","1450","1451","1452","1454","1456","1457","1464","1465","1469","1477","1482","1485","1492","1505","1512","1516","1530","1538","1552","1562","1565","1573","1574","1575","1579","1582","1593","1595","1617","1624","1628","8501"));
            /*String sql = "SELECT id FROM  `shop` WHERE TYPE =  '北京便利店'";
            shops = DBUtil.getShopList(sql);*/
            //便利店列表104
            shops = new ArrayList<String>(Arrays.asList("1045","1048","1049","1055","1059","1066","1069","1070","1071","1072","1075","1079","1081","1082","1088","1100","1102","1103","1118","1132","1133","1135","1144","1146","1150","1151","1233","1278","1280","1281","1282","1293","1294","1295","1299","1358","1366","1376","1380","1381","1384","1385","1386","1398","1407","1413","1415","1426","1432","1441","1458","1470","1471","1478","1480","1486","1493","1497","1498","1501","1503","1504","1519","1521","1523","1536","1543","1549","1566","1578","1585","1587","1589","1590","1591","1592","1594","1603","1607","1608","1610","1611","1612","1620","1625","1626","1630","1636","1638","1640","1641","1642","1646","1648","1649","1650","1652","1653","1655","1657","1664","1669","1466", "1476"));
            shops = new ArrayList<String>(Arrays.asList("1585","1587","1591","1640","1642","1646","1650","1641"));
         //   System.out.println(shops);
            /*shops = new ArrayList<String>(Arrays.asList("1021","1025","1027","1028","1129","1163",
                    "1166","1167","1169","1170","1248","1261","1264","1267","1279","1292","1309","1313","1314","1378","1389","1408","1420",
                    "1434","1443","1450","1456","1457","1477","1485","1492","1512","1552","1573","1574","1579","1593"));*/
        }   else {
            shops = new ArrayList<String>(Arrays.asList(args[0]));
        }

        StringBuffer sbf = new StringBuffer();
        for(String shopId: shops) {

            if (!shopId.equals("888888")) {
                shopName = collection.getDetailShopInfo(shopId, "name");
                shopType = collection.getDetailShopInfo(shopId, "type");
                shopSM = collection.getDetailShopInfo(shopId, "sm");
                title = shopType + "/" + shopSM + "/" + shopName;
                filename = "C:\\project\\new\\" + shopType + "\\" + shopId + "-" + shopType + "-" +  shopName + ".xlsx";
                System.out.println("Begin to get " + shopType + "--" + shopName);
            } else {
                shopName = "全部门店";
                title = shopName;
                filename = "C:\\project\\new\\" + shopName + ".xlsx";
                System.out.println("Begin to get " + shopName);
            }
            ArrayList<String> list = new ArrayList<String>(Arrays.asList("门店名称", title));

            try {
                XSSFWorkbook wb = ReportUtil.generateReportByShop(shopId, list);  //创建工作薄


                FileOutputStream out = new FileOutputStream(filename);
                wb.write(out);
                out.close();
                sbf.append("\"" + shopId + "\",");
            } catch (Exception e) {
                e.printStackTrace();
            }

            long end = System.currentTimeMillis();
            System.out.println("It costs " + (end - start) / 1000);
        }
        System.out.println(sbf);
    }
}
