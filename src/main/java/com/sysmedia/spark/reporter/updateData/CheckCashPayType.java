package com.sysmedia.spark.reporter.updateData;

import com.mysql.jdbc.StringUtils;
import com.sysmedia.spark.reporter.App;
import com.sysmedia.spark.reporter.util.DataBaseConnection;
import com.sysmedia.spark.reporter.util.DataCollection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * 检查支付方式中便利店的现金占比为0
 * 找出这些数据，进行处理
 */

public class CheckCashPayType {
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
/*            //便利店列表102
            ArrayList<String> shops = new ArrayList<String>(Arrays.asList("1045","1048","1049","1055","1059","1066","1069","1070","1071","1072","1075","1079","1081","1082","1088","1100","1102","1103","1118","1132","1133","1135","1144","1146","1150","1151","1233","1278","1280","1281","1282","1293","1294","1295","1299","1358","1366","1376","1380","1381","1384","1385","1386","1398","1407","1413","1415","1426","1432","1441","1458","1470","1471","1478","1480","1486","1493","1497","1498","1501","1503","1504","1519","1521","1523","1536","1543","1549","1566","1578","1585","1587","1589","1590","1591","1592","1594","1603","1607","1608","1610","1611","1612","1620","1625","1626","1630","1636","1638","1640","1641","1642","1646","1648","1649","1650","1652","1653","1655","1657","1664","1669"));
            //标中15
            ArrayList<String> shops1 = new ArrayList<String>(Arrays.asList("1020","1044","1067","1074","1077","1080","1084","1099","1122","1411","1463","1496","1510","1537","1557"));
            shops.addAll(shops1);*/
            //73家大卖场列表
            ArrayList<String> shops = new ArrayList<String>(Arrays.asList("1011","1015","1021","1023","1024","1025","1027","1028","1035","1041","1129","1163","1164","1166","1167","1169","1170","1171","1248","1261","1264","1267","1279","1292","1309","1310","1312","1313","1314","1364","1370","1377","1378","1389","1408","1420","1427","1433","1434","1439","1443","1450","1451","1452","1454","1456","1457","1464","1465","1469","1477","1482","1485","1492","1505","1512","1516","1530","1538","1552","1562","1565","1573","1574","1575","1579","1582","1593","1595","1617","1624","1628","8501"));

            System.out.println(shops.size());
       //     checkList = getCheckList(shops);
                        //  ModifyPayType.modifyPayTypeOrig(checkList, list);
           InsertPayTypeForOneShop.insertPayType(shops);
       //     App.genearteReport(checkList);
            App.genearteReport(shops);

        }

    public static ArrayList<String> getCheckList(ArrayList<String> shops){
        ArrayList<String> checkList = new ArrayList<String>();

        int count = 0;
        String newValue;
        String origShopId = "0";
        for(String shopId: shops) {
            // System.out.println("begin to handle " + shopId + " cash data");
            for(String payType: payTypeList) {
                // String payType =  "现金";
                for (String type : typeList) {
                    for (String year : yearList) {
                        //取得原来的现金支付数目
                        String number = collection.getSinglePayInfoByPayType("t_paytype", "value", shopId, year, type, payType);


                        if(StringUtils.isEmptyOrWhitespaceOnly(number)) number = "0";
                        int value = Integer.parseInt(number);

                        if(value <= 0) {
                            System.out.println(shopId + ":" + year + " paytype is " + payType + " type = " + type + " number is " + number);
                            if(!shopId.equals(origShopId)){
                                count++;
                                checkList.add(shopId);
                                origShopId = shopId;
                            }

                        }
                    }
                }
            }
        }
        System.out.println("count is " + count);
        System.out.println("list is " + checkList);
        return checkList;

    }
}
