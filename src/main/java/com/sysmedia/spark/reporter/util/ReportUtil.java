package com.sysmedia.spark.reporter.util;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.ArrayList;
import java.util.Arrays;

public class ReportUtil {
    public static ArrayList<String> category_class_one = new ArrayList<String>(Arrays.asList("一级品类", " "," "," "," "," "," "));
    public static ArrayList<String> category_class_two = new ArrayList<String>(Arrays.asList("二级品类", " "," "," "," "," "," "));
    public static ArrayList<String> category_class_three = new ArrayList<String>(Arrays.asList("三级品类", " "," "," "," "," "," "));
    public static int num1 = 16;
    public static int num2 = 100;
    public static int num3 = 500;
    public static ArrayList<String> secondLine = new ArrayList<String>(Arrays.asList("数据日期", "2017年11月","2018年11月","2017年11月工作日",
            "2018年11月工作日","2017年11月周末","2018年11月周末"));


    public static XSSFWorkbook generateReportByShop(String shopId, ArrayList<String> list) {
        DataCollection collection = new DataCollection();
        ArrayList<ArrayList<String>> lists = new ArrayList<ArrayList<String>>();
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

        return wb;
    }
}
