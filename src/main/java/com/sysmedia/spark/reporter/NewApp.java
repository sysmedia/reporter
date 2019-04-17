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
        String shopId = "1101";
        String origShopId = "2018";



        String sql = "select id from t_new_shop where flag = 1 and (type = '北京标大' or type = '北京大卖场' or type = '北京标中')";
        //2017-01-03大卖场75家
        /*ArrayList<String> list1 = new ArrayList<String>(Arrays.asList("1021","1025","1027","1028","1129","1163",
                "1166","1167","1169","1170","1248","1261","1264","1267","1279","1292","1309","1313","1314","1378","1389","1408","1420",
                "1434","1443","1450","1456","1457","1477","1485","1492","1512","1552","1573","1574","1579","1593"));*/
        //73家大卖场列表
//       ArrayList<String> list1 = new ArrayList<String>(Arrays.asList("1011","1015","1021","1023","1024","1025","1027","1028","1035","1041","1129","1163","1164","1166","1167","1169","1170","1171","1248","1261","1264","1267","1279","1292","1309","1310","1312","1313","1314","1364","1370","1377","1378","1389","1408","1420","1427","1433","1434","1439","1443","1450","1451","1452","1454","1456","1457","1464","1465","1469","1477","1482","1485","1492","1505","1512","1516","1530","1538","1552","1562","1565","1573","1574","1575","1579","1582","1593","1595","1617","1624","1628","8501"));

        //便利店列表102
        ArrayList<String> list1 = new ArrayList<String>(Arrays.asList("1045","1048","1049","1055","1059","1066","1069","1070","1071","1072","1075","1079","1081","1082","1088","1100","1102","1103","1118","1132","1133","1135","1144","1146","1150","1151","1233","1278","1280","1281","1282","1293","1294","1295","1299","1358","1366","1376","1380","1381","1384","1385","1386","1398","1407","1413","1415","1426","1432","1441","1458","1470","1471","1478","1480","1486","1493","1497","1498","1501","1503","1504","1519","1521","1523","1536","1543","1549","1566","1578","1585","1587","1589","1590","1591","1592","1594","1603","1607","1608","1610","1611","1612","1620","1625","1626","1630","1636","1638","1640","1641","1642","1646","1648","1649","1650","1652","1653","1655","1657","1664","1669"));
        //标中15
        ArrayList<String> list3 = new ArrayList<String>(Arrays.asList("1020","1044","1067","1074","1077","1080","1084","1099","1122","1411","1463","1496","1510","1537","1557"));

      //  list1.addAll(list3);
        ArrayList<String> list4 = new ArrayList<String>(Arrays.asList("1103","1463","1278"));
        list1.removeAll(list4);
        System.out.println("list 1 size is " + list1.size() );

       // sql = "SELECT id FROM  `t_new_shop` WHERE city = '重庆市'";
      //  sql = "SELECT id FROM  `t_new_shop` WHERE (`city` =  '广州市' or city = '深圳市' or city = '重庆市' ) AND flag =0 AND TYPE =  '大卖场'";
     //   sql = "SELECT id FROM  `t_new_shop` WHERE (`city` =  '深圳市')  AND flag = 0 AND TYPE =  '便利店' AND sm =  '天福'";
     // sql= "select id from t_new_shop where city = '北京' and flag = 0 and   (sm = '永辉' or sm = '美廉美' or sm = '卜蜂莲花')";
       sql = " select id from t_new_shop where city = '北京' and flag = 0 and sm = '物美'  and type = '便利店' ";
        ArrayList<String> list2 = DBUtil.getShopList(sql);
        System.out.println("list 2 size is " + list2.size());
        int count = 0;
        for(int i = 0 ; i < list2.size(); i++) {

            shopId = list2.get(i);
            origShopId = list1.get(96 - i);
            System.out.println("shopid is " + shopId + " : " + origShopId);



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
                sql = "update t_new_shop set flag = 1 , shop1 = " + origShopId + ", year1 = 2018, month1 = '03', year2 = 2018, month2 = '04', "
                        + "shop2 = " + origShopId + " where id = '" + shopId + "'";
                System.out.println("sql is " + sql);
                conn.update(sql);
                count++;
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(shopId + " create file error , name is " + filename);
            }

            long end = System.currentTimeMillis();
            System.out.println("It costs " + (end - start) / 1000);
        }
        System.out.println("count is " + count);
    }
}
