package com.sysmedia.spark.reporter;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

import com.sysmedia.spark.reporter.util.*;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class App {
    public static void main(String[] args) {
        System.out.println("args length is " + args.length);
        DataBaseConnection conn = new DataBaseConnection();
        DataCollection collection = new DataCollection();
        long start = System.currentTimeMillis();
       // String shopId = "1522";
        int num1 = 16;
        int num2 = 100;
        int num3 = 500;
        StringBuffer sb = new StringBuffer();
        String result, shopName, shopType, shopSM, title, filename;
        ArrayList<ArrayList<String>> lists = new ArrayList<ArrayList<String>>();
        ArrayList<String> secondLine = new ArrayList<String>(Arrays.asList("数据日期", "2017年11月","2018年11月","2017年11月工作日",
                "2018年11月工作日","2017年11月周末","2018年11月周末"));
        ArrayList<String> category_class_one = new ArrayList<String>(Arrays.asList("一级品类", " "," "," "," "," "," "));
        ArrayList<String> category_class_two = new ArrayList<String>(Arrays.asList("二级品类", " "," "," "," "," "," "));
        ArrayList<String> category_class_three = new ArrayList<String>(Arrays.asList("三级品类", " "," "," "," "," "," "));

        ArrayList<String> shops = new ArrayList<String>();
        if(args.length == 0) {
            shops = collection.getDistinctShopInfo();
            System.out.println(shops.size());
        } else if("999999".equals(args[0])) {
            shops = new ArrayList<String>(Arrays.asList("1011","1015","1021","1023","1024","1025","1027","1028","1035","1041","1129","1163","1164","1166","1167","1169","1170","1171","1248","1261","1264","1267","1279","1292","1309","1310","1312","1313","1314","1364","1370","1377","1378","1389","1408","1420","1427","1433","1434","1439","1443","1450","1451","1452","1454","1456","1457","1464","1465","1469","1477","1482","1485","1492","1505","1512","1516","1530","1538","1552","1562","1565","1573","1574","1575","1579","1582","1593","1595","1605","1617","1622","1624","1628","1647","1679","8501","1012","1014","1016","1019","1032","1033","1036","1038","1039","1154","1156","1251","1252","1301","1322","1387","1391","1431","1447","1459","1507","1509","1525","1526","1564","1583","1631","1632","1633","1635","1020","1044","1067","1074","1077","1080","1084","1091","1411","1510","1537","1557","1066","1071","1132","1133","1146","1233","1280","1358","1381","1441","1470","1478","1543","1578","1590","1594","1630","1640","1646","1648","1676","1678","1680","1706","1713"));
        }   else {
            shops = new ArrayList<String>(Arrays.asList(args[0]));
        }

        for(String shopId: shops) {

            if (!shopId.equals("888888")) {
                shopName = collection.getDetailShopInfo(shopId, "name");
                shopType = collection.getDetailShopInfo(shopId, "type");
                shopSM = collection.getDetailShopInfo(shopId, "sm");
                title = shopType + "/" + shopSM + "/" + shopName;
                filename = "C:\\project\\" + shopType + "\\" + shopId + "-" + shopType + "-" +  shopName + ".xlsx";
                System.out.println("Begin to get " + shopType + "--" + shopName);
            } else {
                shopName = "全部门店";
                title = shopName;
                filename = "C:\\project\\" + shopName + ".xlsx";
                System.out.println("Begin to get " + shopName);
            }
            ArrayList<String> list = new ArrayList<String>(Arrays.asList("门店名称", title));

            try {
                XSSFWorkbook wb = new XSSFWorkbook();  //创建工作薄
                XSSFSheet sheet = wb.createSheet("sheet1"); //创建工作表
                sheet.setDefaultColumnWidth(15);
                XSSFCellStyle cellStyle = wb.createCellStyle();
                cellStyle.setBorderBottom(BorderStyle.MEDIUM);
                cellStyle.setBorderLeft(BorderStyle.MEDIUM);
                cellStyle.setBorderRight(BorderStyle.MEDIUM);
                cellStyle.setBorderTop(BorderStyle.MEDIUM);

                //设置垂直对齐的样式为居中对齐;
                cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
                cellStyle.setWrapText(true);

                int line = ExcelUtil.inputFirstLineExcel(sheet, list, cellStyle);
                line = ExcelUtil.inputSingleLineExcel(line, sheet, secondLine, cellStyle);
                // 保存


                //销售额  t_shop_count	totalPrice
                list = collection.getSingleLineInfo("t_shop_count", "totalPrice", shopId);
                System.out.println("销售额 is " + list);
                list.add(0, "销售额 ");
                line = ExcelUtil.inputSingleLineExcel(line, sheet, list, cellStyle);

                //销售量       t_shop_count	salecount
                list = collection.getSingleLineInfo("t_shop_count", "salecount", shopId);
                System.out.println("销售量 is " + list);
                list.add(0, "销售量 ");
                line = ExcelUtil.inputSingleLineExcel(line, sheet, list, cellStyle);

                // 查询 , 购物人次 信息， t_shop_count	totalcount
                list = collection.getSingleLineInfo("t_shop_count", "totalcount", shopId);
                System.out.println("购物人次 is " + list);
                list.add(0, "购物人次 ");
                line = ExcelUtil.inputSingleLineExcel(line, sheet, list, cellStyle);

                //查询会员购物人次 ，t_shop_customer_count	totalcount
                list = collection.getSingleLineInfo("t_shop_customer_count", "totalcount", shopId);
                System.out.println("会员购物人次 is " + list);
                list.add(0, "会员购物人次 ");
                line = ExcelUtil.inputSingleLineExcel(line, sheet, list, cellStyle);

                //查询客单价 t_shop_count	avgPrice
                list = collection.getSingleLineInfo("t_shop_count", "avgPrice", shopId);
                System.out.println("客单价 is " + list);
                list.add(0, "客单价 ");
                line = ExcelUtil.inputSingleLineExcel(line, sheet, list, cellStyle);

                //平均购物频次 ,需要t_shop_customer_count(totalcount)/t_customer_number(value)
                list = collection.getSingleLineAvgTotalCountInfo(shopId);
                System.out.println("平均购物频次 is " + list);
                list.add(0, "平均购物频次 ");
                line = ExcelUtil.inputSingleLineExcel(line, sheet, list, cellStyle);

                //支付方式占比 t_paytype	value
                lists = collection.getMultiPayInfo("t_paytype", "value", shopId);
                System.out.println("支付方式 is " + lists);
                line = ExcelUtil.inputMultiLineExcel(line, sheet, lists, cellStyle);


                //净值等级  t_customertype 	value
                lists = collection.getMultiCustomerTypeInfo("t_customertype", "value", shopId);
                System.out.println("净值等级 is " + lists);
                line = ExcelUtil.inputMultiLineExcel(line, sheet, lists, cellStyle);

                //关注划分： t_customerconcerns	value
                lists = collection.getMultiCustomerConcernsInfo("t_customerconcerns", "value", shopId);
                System.out.println("关注等级 is " + lists);
                line = ExcelUtil.inputMultiLineExcel(line, sheet, lists, cellStyle);

                //时段人流划分： t_shop_day_count	afternoon
                lists = collection.getMultiLineTimeInfo("t_shop_day_count", shopId);
                System.out.println("时段人流划分 is " + lists);
                line = ExcelUtil.inputMultiLineExcel(line, sheet, lists, cellStyle);

                //品牌偏好: t_pinpai_sale	saleCount
                lists = collection.getMultiTopBrandInfo("t_pinpai_sale", "saleCount", shopId);
                System.out.println("品牌偏好 is " + lists);
                line = ExcelUtil.inputMultiLineExcel(line, sheet, lists, cellStyle);

                //品牌购买人次 : t_pinpai	totalCount
                lists = collection.getMultiTopBrandPurchaseInfo("品牌购买人次", "t_pinpai", "totalCount", shopId);
                System.out.println("品牌购买人次 is " + lists);
                line = ExcelUtil.inputMultiLineExcel(line, sheet, lists, cellStyle);

                //品牌销售额 : t_pinpai	totalPrice
                lists = collection.getMultiTopBrandPurchaseInfo("品牌销售额", "t_pinpai", "totalPrice", shopId);
                System.out.println("品牌销售额 is " + lists);
                line = ExcelUtil.inputMultiLineExcel(line, sheet, lists, cellStyle);

                //品牌销售量 : t_pinpai_sale	saleCount
                lists = collection.getMultiTopBrandPurchaseInfo("品牌销售量", "t_pinpai_sale", "saleCount", shopId);
                System.out.println("品牌销售量 is " + lists);
                line = ExcelUtil.inputMultiLineExcel(line, sheet, lists, cellStyle);

                //一级品类
                line = ExcelUtil.inputSingleLineExcel(line, sheet, category_class_one, cellStyle);

                //一级品类购买人次 : t_pinlei_first	totalCount
                lists = collection.getMultiTopCategoryPurchaseInfo("品类购买人次", "t_pinlei_first", "category", "totalCount", shopId, num1);
                System.out.println("一级品类购买人次 is " + lists);
                line = ExcelUtil.inputMultiLineExcel(line, sheet, lists, cellStyle);

                //一级品类销售额 : t_pinlei_first	totalPrice
                lists = collection.getMultiTopCategoryPurchaseInfo("品类销售额", "t_pinlei_first", "category", "totalPrice", shopId, num1);
                System.out.println("一级品类销售额 is " + lists);
                line = ExcelUtil.inputMultiLineExcel(line, sheet, lists, cellStyle);

                //一级品类销售量 : t_pinlei_first	saleCount
                lists = collection.getMultiTopCategoryPurchaseInfo("品类销售量", "t_pinlei_first", "category", "saleCount", shopId, num1);
                System.out.println("一级品类销售量 is " + lists);
                line = ExcelUtil.inputMultiLineExcel(line, sheet, lists, cellStyle);

                //二级品类
                line = ExcelUtil.inputSingleLineExcel(line, sheet, category_class_two, cellStyle);

                //二级品类购买人次 : t_pinlei_second	totalCount
                lists = collection.getMultiTopCategoryPurchaseInfo("品类购买人次", "t_pinlei_second", "category2", "totalCount", shopId, num2);
                System.out.println("二级品类购买人次 is " + lists);
                line = ExcelUtil.inputMultiLineExcel(line, sheet, lists, cellStyle);

                //二级品类销售额 : t_pinlei_second	totalPrice
                lists = collection.getMultiTopCategoryPurchaseInfo("品类销售额", "t_pinlei_second", "category2", "totalPrice", shopId, num2);
                System.out.println("二级品类销售额 is " + lists);
                line = ExcelUtil.inputMultiLineExcel(line, sheet, lists, cellStyle);

                //二级品类销售量 : t_pinlei_second	saleCount
                lists = collection.getMultiTopCategoryPurchaseInfo("品类销售量", "t_pinlei_second", "category2", "saleCount", shopId, num2);
                System.out.println("二级品类销售量 is " + lists);
                line = ExcelUtil.inputMultiLineExcel(line, sheet, lists, cellStyle);

                //三级品类
                line = ExcelUtil.inputSingleLineExcel(line, sheet, category_class_three, cellStyle);

                //三级品类购买人次 : t_pinlei_third	totalCount
                lists = collection.getMultiTopCategoryPurchaseInfo("品类购买人次", "t_pinlei_third", "category3", "totalCount", shopId, num3);
                System.out.println("三级品类购买人次 is " + lists);
                line = ExcelUtil.inputMultiLineExcel(line, sheet, lists, cellStyle);

                //三级品类销售额 : t_pinlei_third	totalPrice
                lists = collection.getMultiTopCategoryPurchaseInfo("品类销售额", "t_pinlei_third", "category3", "totalPrice", shopId, num3);
                System.out.println("三级品类销售额 is " + lists);
                line = ExcelUtil.inputMultiLineExcel(line, sheet, lists, cellStyle);

                //三级品类销售量 : t_pinlei_third	saleCount
                lists = collection.getMultiTopCategoryPurchaseInfo("品类销售量", "t_pinlei_third", "category3", "saleCount", shopId, num3);
                System.out.println("三级品类销售量 is " + lists);
                line = ExcelUtil.inputMultiLineExcel(line, sheet, lists, cellStyle);

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
