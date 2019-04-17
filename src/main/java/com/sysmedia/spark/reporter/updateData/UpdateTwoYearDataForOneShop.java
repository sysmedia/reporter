package com.sysmedia.spark.reporter.updateData;

import com.sysmedia.spark.reporter.util.DataBaseConnection;
import com.sysmedia.spark.reporter.util.DataCollection;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * update 2017 and 2018 data as some data were missed by duodian
 * parameter:  shop, ratio
 * 更新单店的数据
 */

public class UpdateTwoYearDataForOneShop {
        public static DataBaseConnection conn = new DataBaseConnection();
        public static String sql;
    public static void  main(String[] args) {
        System.out.println("args length is " + args.length);

        /*String shopId =  args[0];
        float ratio = Float.valueOf(args[1]);*/
        float ratio = 4.25f;


        /*ArrayList<String> listAll = new ArrayList<String>(Arrays.asList("1011","1015","1021","1023","1024","1025","1027","1028","1035","1041","1129","1163","1164","1166","1167","1169","1170","1171","1248","1261","1264","1267","1279","1292","1309","1310","1312","1313","1314","1364","1370","1377","1378","1389","1408","1420","1427","1433","1434","1439","1443","1450","1451","1452","1454","1456","1457","1464","1465","1469","1477","1482","1485","1492","1505","1512","1516","1530","1538","1552","1562","1565","1573","1574","1575","1579","1582","1593","1595","1605","1617","1622","1624","1628","8501"));


        ArrayList<String> list1 = new ArrayList<String>(Arrays.asList("1021","1025","1027","1028","1129","1163",
                "1166","1167","1169","1170","1248","1261","1264","1267","1279","1292","1309","1313","1314","1378","1389","1408","1420",
                "1434","1443","1450","1456","1457","1477","1485","1492","1512","1552","1573","1574","1579","1593"));*/

        ArrayList<String> list1 = new ArrayList<String>(Arrays.asList("1045","1048","1049","1055","1059","1066","1069","1070","1071","1072","1075","1079","1081","1082","1088","1100","1102","1103","1118","1132","1133","1135","1144","1146","1150","1151","1233","1278","1280","1281","1282","1293","1294","1295","1299","1358","1366","1376","1380","1381","1384","1385","1386","1398","1407","1413","1415","1426","1432","1441","1458","1470","1471","1478","1480","1486","1493","1497","1498","1501","1503","1504","1519","1521","1523","1536","1543","1549","1566","1578","1585","1587","1589","1590","1591","1592","1594","1603","1607","1608","1610","1611","1612","1620","1625","1626","1630","1636","1638","1640","1641","1642","1646","1648","1649","1650","1652","1653","1655","1657","1664","1669"));

        for(String shopId : list1) {
                updateTwoYearSaleDate(shopId, ratio);
        }

    }

        public static void updateTwoYearSaleDate(String shopId, float ratio){
                System.out.println("shop id is " + shopId);
                //销售额  t_shop_count	totalPrice
                sql = "update t_shop_count set totalPrice = CONCAT((CONVERT(totalPrice,DECIMAL(16,0)) * " + ratio + "), '') where  shop = " + shopId;
                conn.update(sql);

                //销售量       t_shop_count	salecount
                sql = "update t_shop_count set salecount = CONCAT((CONVERT(salecount,DECIMAL(16,0)) * " + ratio + "), '') where  shop = " + shopId;
                conn.update(sql);

                // 查询 , 购物人次 信息， t_shop_count	totalcount
                sql = "update t_shop_count set totalcount = CONCAT((CONVERT(totalcount,DECIMAL(16,0)) * " + ratio + "), '') where  shop = " + shopId;
                conn.update(sql);

                //查询会员购物人次 ，t_shop_customer_count	totalcount
                sql = "update t_shop_customer_count set totalcount = CONCAT((CONVERT(totalcount,DECIMAL(16,0)) * " + ratio + "), '') where  shop = " + shopId;
                conn.update(sql);

                //平均购物频次 ,需要t_shop_customer_count(totalcount)/t_customer_number(value)
                sql = "update t_customer_number set value = CONCAT((CONVERT(value,DECIMAL(16,0)) * " + ratio + "), '') where shop = " + shopId;
                conn.update(sql);

                //净值等级 关注划分 t_customertype 	value
                sql = "update t_customertype set value = CONCAT(CONVERT((CONVERT(value,SIGNED integer) * " + ratio + "), DECIMAL(16,0)), '') where  shop = " + shopId;
                conn.update(sql);

                //关注划分 t_customerconcerns 	value
                sql = "update t_customerconcerns set value = CONCAT(CONVERT((CONVERT(value,SIGNED integer) * " + ratio + "), DECIMAL(16,0)), '') where  shop = " + shopId;
                conn.update(sql);

                //时段人流划分： t_shop_day_count	morning afternoon evening
                sql = "update t_shop_day_count set morning = CONCAT(CONVERT((CONVERT(morning,DECIMAL(16,0)) * " + ratio + "), DECIMAL(16,0)), '') where  shop = " + shopId;
                conn.update(sql);
                sql = "update t_shop_day_count set afternoon = CONCAT(CONVERT((CONVERT(afternoon,DECIMAL(16,0)) * " + ratio + "), DECIMAL(16,0)), '') where  shop = " + shopId;
                conn.update(sql);
                sql = "update t_shop_day_count set evening = CONCAT(CONVERT((CONVERT(evening,DECIMAL(16,0)) * " + ratio + "), DECIMAL(16,0)), '') where  shop = " + shopId;
                conn.update(sql);

                //品牌购买人次 : t_pinpai	totalCount
                sql = "update t_pinpai set totalCount = CONCAT(CONVERT((CONVERT(totalCount,SIGNED integer) * " + ratio + "), DECIMAL(16,0)), '') where  shop = " + shopId;
                conn.update(sql);

                //品牌销售额 : t_pinpai	totalPrice
                sql = "update t_pinpai set totalPrice = CONCAT(CONVERT((CONVERT(totalPrice,SIGNED integer) * " + ratio + "), DECIMAL(16,0)), '') where  shop = " + shopId;
                conn.update(sql);

                //品牌销售量 : t_pinpai_sale	saleCount
                sql = "update t_pinpai_sale set saleCount = CONCAT(CONVERT((CONVERT(saleCount,SIGNED integer) * " + ratio + "), DECIMAL(16,0)), '') where  shop = " + shopId;
                conn.update(sql);

                //一级品类购买人次 : t_pinlei_first	totalCount
                sql = "update t_pinlei_first set totalCount = CONCAT(CONVERT((CONVERT(totalCount,SIGNED integer) * " + ratio + "), DECIMAL(16,0)), '') where  shop = " + shopId;
                conn.update(sql);

                //一级品类销售额 : t_pinlei_first	totalPrice
                sql = "update t_pinlei_first set totalPrice = CONCAT(CONVERT((CONVERT(totalPrice,SIGNED integer) * " + ratio + "), DECIMAL(16,0)), '') where  shop = " + shopId;
                conn.update(sql);

                //一级品类销售量 : t_pinlei_first	saleCount
                sql = "update t_pinlei_first set saleCount = CONCAT(CONVERT((CONVERT(saleCount,SIGNED integer) * " + ratio + "), DECIMAL(16,0)), '') where  shop = " + shopId;
                conn.update(sql);

                //二级品类购买人次 : t_pinlei_second	totalCount
                sql = "update t_pinlei_second set totalCount = CONCAT(CONVERT((CONVERT(totalCount,SIGNED integer) * " + ratio + "), DECIMAL(16,0)), '') where  shop = " + shopId;
                conn.update(sql);

                //二级品类销售额 : t_pinlei_second	totalPrice
                sql = "update t_pinlei_second set totalPrice = CONCAT(CONVERT((CONVERT(totalPrice,SIGNED integer) * " + ratio + "), DECIMAL(16,0)), '') where  shop = " + shopId;
                conn.update(sql);

                //二级品类销售量 : t_pinlei_second	saleCount
                sql = "update t_pinlei_second set saleCount = CONCAT(CONVERT((CONVERT(saleCount,SIGNED integer) * " + ratio + "), DECIMAL(16,0)), '') where  shop = " + shopId;
                conn.update(sql);

                //三级品类购买人次 : t_pinlei_third	totalCount
                sql = "update t_pinlei_third set totalCount = CONCAT(CONVERT((CONVERT(totalCount,SIGNED integer) * " + ratio + "), DECIMAL(16,0)), '') where  shop = " + shopId;
                conn.update(sql);

                //三级品类销售额 : t_pinlei_third	totalPrice
                sql = "update t_pinlei_third set totalPrice = CONCAT(CONVERT((CONVERT(totalPrice,SIGNED integer) * " + ratio + "), DECIMAL(16,0)), '') where  shop = " + shopId;
                conn.update(sql);

                //三级品类销售量 : t_pinlei_third	saleCount
                sql = "update t_pinlei_third set saleCount = CONCAT(CONVERT((CONVERT(saleCount,SIGNED integer) * " + ratio + "), DECIMAL(16,0)), '') where  shop = " + shopId;
                conn.update(sql);
        }
}
