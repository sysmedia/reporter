package com.sysmedia.spark.reporter.updateData;

import com.mysql.jdbc.StringUtils;
import com.sysmedia.spark.reporter.App;
import com.sysmedia.spark.reporter.util.DataBaseConnection;
import com.sysmedia.spark.reporter.util.DataCollection;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * 检查支付方式中便利店的现金占比为0
 * 找出这些数据，进行处理
 */

public class CheckStoreSaleData {
    private static String year1 = "2017";
    private static String month1 = "11";
    private static String year2 = "2018";
    private static String month2 = "11";
    private static String sm = "28";
    private static DataBaseConnection conn = new DataBaseConnection();
    private static  DataCollection collection = new DataCollection();
    private static ArrayList<String> yearList = new ArrayList<String>(Arrays.asList(year2, year1));
    private static ArrayList<String> typeList = new ArrayList<String>(Arrays.asList("全部","工作日", "周末"));
    private static ArrayList<String> payTypeList = new ArrayList<String>(Arrays.asList("现金"));
    //要替换的便利店数据，从下面的list中导入
    private static ArrayList<String> list = new ArrayList<String>(Arrays.asList("1118","1021","1023","1024","1025","1027","1028","1035","1041", "1081", "1133", "1151", "1294", "1384","1386", "1486", "1549", "1578"));
        public static void main(String[] args){
            ArrayList<String> checkList = new ArrayList<String>();;
            //便利店列表102
            ArrayList<String> shops = new ArrayList<String>(Arrays.asList("1045","1048","1049","1055","1059","1066","1069","1070","1071","1072","1075","1079","1081","1082","1088","1100","1102","1103","1118","1132","1133","1135","1144","1146","1150","1151","1233","1278","1280","1281","1282","1293","1294","1295","1299","1358","1366","1376","1380","1381","1384","1385","1386","1398","1407","1413","1415","1426","1432","1441","1458","1470","1471","1478","1480","1486","1493","1497","1498","1501","1503","1504","1519","1521","1523","1536","1543","1549","1566","1578","1585","1587","1589","1590","1591","1592","1594","1603","1607","1608","1610","1611","1612","1620","1625","1626","1630","1636","1638","1640","1641","1642","1646","1648","1649","1650","1652","1653","1655","1657","1664","1669"));
            //标中15
            ArrayList<String> shops1 = new ArrayList<String>(Arrays.asList("1020","1044","1067","1074","1077","1080","1084","1099","1122","1411","1463","1496","1510","1537","1557"));
            shops.addAll(shops1);

            System.out.println(shops.size());
       //    checkList = getCheckList(shops);
         //   App.genearteReport(checkList);
          App.genearteReport(shops);

        }

    public static ArrayList<String> getCheckList(ArrayList<String> shops){
        ArrayList<String> checkList = new ArrayList<String>();

        int count = 0;
        int count1 = 0;
        String newValue;
        String origShopId = "0";
        for(String shopId: shops) {
           //  System.out.println("begin to handle " + shopId + " cash data");
              //取得2018年全部销售额
             String number = collection.getSingleLineInfoByType("t_shop_count", "totalPrice", shopId, "2018", "全部");


            if(StringUtils.isEmptyOrWhitespaceOnly(number)) number = "0";
            long value = Long.parseLong(number);
            if(value>= 2600000) {
                System.out.println(shopId + ":" + " 2018 year total price is " +  number);
               UpdateTwoYearDataForOneShop.updateTwoYearSaleDate(shopId, 0.72f);
                count++;
                checkList.add(shopId);
            } else if(value <= 100000){
                System.out.println(shopId + ":" + " 2018 year total price is " +  number);
                UpdateTwoYearDataForOneShop.updateTwoYearSaleDate(shopId, 8.19f);
                count1++;
                checkList.add(shopId);
            }
        }
        System.out.println("count is " + count);
        System.out.println("count1 is " + count1);
        System.out.println("list is " + checkList);
        return checkList;

    }
}
