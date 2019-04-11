package com.sysmedia.spark.reporter.updateData;

import com.mysql.jdbc.StringUtils;
import com.sysmedia.spark.reporter.util.DataBaseConnection;
import com.sysmedia.spark.reporter.util.DataCollection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * 2017年11月和2018年11月的支付方式数据不全
 * 找出这些数据不全的便利店
 * 从别的数据导入，补全数据
 */

public class ModifyPayType {
    private static String year1 = "2017";
    private static String month1 = "11";
    private static String year2 = "2018";
    private static String month2 = "11";
    private static String sm = "28";
    private static DataBaseConnection conn = new DataBaseConnection();
    private static  DataCollection collection = new DataCollection();
    private static ArrayList<String> yearList = new ArrayList<String>(Arrays.asList(year1, year2));
    private static ArrayList<String> typeList = new ArrayList<String>(Arrays.asList("全部", "工作日", "周末"));
    private static ArrayList<String> payTypeList = new ArrayList<String>(Arrays.asList("现金","微信","支付宝","银行卡","其他"));
        public static void main(String[] args){
            Random random = new Random();
            ArrayList<String> shops = new ArrayList<String>();
            StringBuffer sb = new StringBuffer();
            ArrayList<ArrayList<String>> lists = new ArrayList<ArrayList<String>>();
            ArrayList<String> list = new ArrayList<String>();;
            if(args.length == 0) {
                //如果没有输入shopId，则插入所有门店现金数据
                //shops = collection.getDistinctShopInfo();
                //shops = new ArrayList<String>(Arrays.asList("1011","1015","1021","1023","1024","1025","1027","1028","1035","1041","1129","1163","1164","1166","1167","1169","1170","1171","1248","1261","1264","1267","1279","1292","1309","1310","1312","1313","1314","1364","1370","1377","1378","1389","1408","1420","1427","1433","1434","1439","1443","1450","1451","1452","1454","1456","1457","1464","1465","1469","1477","1482","1485","1492","1505","1512","1516","1530","1538","1552","1562","1565","1573","1574","1575","1579","1582","1593","1595","1605","1617","1622","1624","1628","1647","1679","8501","1012","1014","1016","1019","1032","1033","1036","1038","1039","1154","1156","1251","1252","1301","1322","1387","1391","1431","1447","1459","1507","1509","1525","1526","1564","1583","1631","1632","1633","1635","1020","1044","1067","1074","1077","1080","1084","1091","1411","1510","1537","1557","1066","1071","1132","1133","1146","1233","1280","1358","1381","1441","1470","1478","1543","1578","1590","1594","1630","1640","1646","1648","1676","1678","1680","1706","1713"));
               //便利店列表
                 shops = new ArrayList<String>(Arrays.asList("1045","1048","1049","1055","1059","1066","1069","1070","1071","1072","1075","1079","1081","1082","1088","1100","1102","1103","1118","1132","1133","1135","1144","1146","1150","1151","1233","1278","1280","1281","1282","1293","1294","1295","1299","1358","1366","1376","1380","1381","1384","1385","1386","1398","1407","1413","1415","1426","1432","1441","1458","1470","1471","1478","1480","1486","1493","1497","1498","1501","1503","1504","1519","1521","1523","1536","1543","1549","1566","1578","1585","1587","1589","1590","1591","1592","1594","1603","1607","1608","1610","1611","1612","1620","1625","1626","1630","1636","1638","1640","1641","1642","1646","1648","1649","1650","1652","1653","1655","1657","1664","1669"));
                //大卖场73家+ 10家标中
                list = new ArrayList<String>(Arrays.asList("1011","1015","1021","1023","1024","1025","1027","1028","1035","1041","1129","1163","1164","1166","1167","1169","1170","1171","1248","1261","1264","1267","1279","1292","1309","1310","1312","1313","1314","1364","1370","1377","1378","1389","1408","1420","1427","1433","1434","1439","1443","1450","1451","1452","1454","1456","1457","1464","1465","1469","1477","1482","1485","1492","1505","1512","1516","1530","1538","1552","1562","1565","1573","1574","1575","1579","1582","1593","1595","1617","1624","1628","8501","1020", "1014", "1016", "1019", "1032", "1033","1036","1038","1039","1154","1156","1251","1252","1301","1322","1387","1391","1431","1447","1459","1507","1509","1525","1526","1564","1583","1631","1632","1633","1635"));
            }   else {
                shops = new ArrayList<String>(Arrays.asList(args[0]));
            }

            int count = 0;
            String newValue;
            String origShopId = shops.get(0);
            for(String shopId: shops) {
                 System.out.println("begin to handle " + shopId + " cash data");
                for(String payType: payTypeList) {
                    // String payType =  "现金";
                    for (String type : typeList) {
                        for (String year : yearList) {
                            //取得原来的现金支付数目
                            String number = collection.getSinglePayInfoByPayType("t_paytype_orig", "value", shopId, year, type, payType);

                            if (number.equals(" ")) {
                                //System.out.println(year + " " + shopId + " orig number is null" + number);
                                if(!shopId.equals(origShopId)){
                                    System.out.println(shopId + " : " + origShopId);
                                    origShopId = shopId;
                                    count++;
                                    System.out.println("count is " + count);
                                }
                                newValue = list.get(count);
                                number = collection.getSinglePayInfoByPayType("t_paytype_orig", "value", newValue, year, type, payType);
                                String sql = "insert into t_paytype_orig values (" + year + ", 11 , " +
                                        newValue + ", '" + payType + "'," + number + ",'" + type + "')";
                                System.out.println("sql is " + sql);
                                conn.update(sql);

                            }
                        }
                    }
                }
            }
            System.out.println("count is " + count);
        }

    /**
     * 通过新的rate计算新的number
     * 输入rate，shopId，year，type（支付方式）
     * @return
     */
    public static int getNewNumber(double rnd, String shopId, String year, String type){
        //得到现在的总数，不包含现金支付
        String result = collection.getSumPayInfoByType("t_paytype", "value", shopId, year,type);
        System.out.println( type + " new sum is " + result);
        if(StringUtils.isEmptyOrWhitespaceOnly(result)) result = "1";
        int sum = Integer.parseInt(result);
        int newCash = (int)((rnd * sum / 100)/(1 - rnd/100));
        System.out.println(type + "new number is " + newCash);
        return newCash;
        }
}
