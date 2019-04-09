package com.sysmedia.spark.reporter.updateData;

import com.mysql.jdbc.StringUtils;
import com.sysmedia.spark.reporter.util.DataBaseConnection;
import com.sysmedia.spark.reporter.util.DataCollection;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * 因为现金支付方式没有导入，通过2017年11月和2018年11月的数据生成其他月份的现金支付方式
 * 比较其他的支付方式，乘以一个ratio
 */

public class InsertPayTypeForOneShop {
    private static String year1 = "2017";
    private static String month1 = "11";
    private static String year2 = "2018";
    private static String month2 = "11";
    private static String sm = "28";
    private static DataBaseConnection conn = new DataBaseConnection();
    private static  DataCollection collection = new DataCollection();
    private static ArrayList<String> yearList = new ArrayList<String>(Arrays.asList(year1, year2));
    private static ArrayList<String> typeList = new ArrayList<String>(Arrays.asList("全部"));
    private static ArrayList<String> payTypeList = new ArrayList<String>(Arrays.asList("现金"));
        public static void main(String[] args){
            Random random = new Random();
            ArrayList<String> shops = new ArrayList<String>();
            StringBuffer sb = new StringBuffer();
            ArrayList<ArrayList<String>> lists = new ArrayList<ArrayList<String>>();
            ArrayList<String> list;
            if(args.length == 0) {
                //如果没有输入shopId，则插入所有门店现金数据
                shops = collection.getDistinctShopInfo();
                //shops = new ArrayList<String>(Arrays.asList("1011","1015","1021","1023","1024","1025","1027","1028","1035","1041","1129","1163","1164","1166","1167","1169","1170","1171","1248","1261","1264","1267","1279","1292","1309","1310","1312","1313","1314","1364","1370","1377","1378","1389","1408","1420","1427","1433","1434","1439","1443","1450","1451","1452","1454","1456","1457","1464","1465","1469","1477","1482","1485","1492","1505","1512","1516","1530","1538","1552","1562","1565","1573","1574","1575","1579","1582","1593","1595","1605","1617","1622","1624","1628","1647","1679","8501","1012","1014","1016","1019","1032","1033","1036","1038","1039","1154","1156","1251","1252","1301","1322","1387","1391","1431","1447","1459","1507","1509","1525","1526","1564","1583","1631","1632","1633","1635","1020","1044","1067","1074","1077","1080","1084","1091","1411","1510","1537","1557","1066","1071","1132","1133","1146","1233","1280","1358","1381","1441","1470","1478","1543","1578","1590","1594","1630","1640","1646","1648","1676","1678","1680","1706","1713"));
            }   else {
                shops = new ArrayList<String>(Arrays.asList(args[0]));
            }


            for(String shopId: shops) {
                String payType =  "现金";
                for (String type : typeList) {
                    for (String year : yearList) {
                        //取得原来的现金支付数目
                        String number = collection.getSinglePayInfoByPayType("t_paytype_orig", "value", shopId, year, type, payType);
                        System.out.println("orig number is " + number);
                        if(number.equals(" ")) number = "0";

                        int value = Integer.parseInt(number);
                        //取得原来的总数
                        String result = collection.getSumPayInfoByType("t_paytype_orig", "value", shopId, year,type);
                        if(StringUtils.isEmptyOrWhitespaceOnly(result)) result = "1";
                        int sum = Integer.parseInt(result);
                        //计算原来的现金占比
                        double rate = (double)value / sum * 100;
                        System.out.println(year + " rate is " + rate);
                        //计算新的现金占比，-3 ~ 3的随机数
                        double rnd = (double)(random.nextInt(600) - 300)/100 + rate;
                        System.out.println("new rate  is " + rnd);
                        //计算新的现金支付数目
                        int newNumber = getNewNumber(rnd, shopId, year, type);
                        String sql = "insert into t_paytype values (" + year + "," + "11, " +
                                shopId + ", " + "'现金', " + newNumber + "," + "'全部'" + ")";
                        conn.update(sql);
                        System.out.println("sql is " + sql);

                        //计算工作日rate， new rate + （1~4）的随机数
                        double workRate = (double)(random.nextInt(300) + 100)/100 + rnd;
                        int workNumber = getNewNumber(workRate, shopId, year, "工作日");
                        sql = "insert into t_paytype values (" + year + "," + "11, " +
                                shopId + ", " + "'现金', " + workNumber + "," + "'工作日'" + ")";
                        System.out.println("sql is " + sql);
                        conn.update(sql);

                        //计算周末rate， new rate + （-1~-4）的随机数
                        double weekendRate = (double)(random.nextInt(300) - 400)/100 + rnd;
                        int weekendNumber = getNewNumber(weekendRate, shopId, year, "周末");
                        sql = "insert into t_paytype values (" + year + "," + "11, " +
                                shopId + ", " + "'现金', " + weekendNumber + "," + "'周末'" + ")";
                        System.out.println("sql is " + sql);
                        conn.update(sql);
                    }
                }
            }
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
