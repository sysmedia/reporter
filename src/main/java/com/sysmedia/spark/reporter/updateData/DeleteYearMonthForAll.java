package com.sysmedia.spark.reporter.updateData;

import com.sysmedia.spark.reporter.util.DataBaseConnection;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * 删除 year and month for all shop
 * parameter:  shop, year1, month1, year2, month2
 * 更新单店的数据
 */

public class DeleteYearMonthForAll {
    public static void  main(String[] args) {
        System.out.println("args length is " + args.length);
        DataBaseConnection conn = new DataBaseConnection();

        //删除2017年11月数据，把2018年11月数据，更新成2017年11月数据
        //1313 2017 11
        String shopId =  args[0];
        //1313 2017 11 2017 12

        String old_year1 = "2018";
        String old_month1 = "11";
        String old_year2 = "2017";
        String old_month2 = "12";
        String sql;

        ArrayList<String> tableList = new ArrayList<String>(Arrays.asList("t_shop_count", "t_shop_customer_count",
                "t_customer_number", "t_customertype", "t_customerconcerns", "t_shop_day_count", "t_pinpai",
                "t_pinpai_sale", "t_pinlei_first", "t_pinlei_second", "t_pinlei_third" , "t_paytype"));
        long start = System.currentTimeMillis();
       for(String table : tableList) {
          /* sql = "delete from  " + table +   " where  " + " year = " + old_year1 + " and month = " + old_month1 ;
           System.out.println(sql);
           conn.update(sql);*/

           sql = "update " + table + " set year = " + old_year1 + " , month = " + old_month1 + " where   " +
                     "   year = " + old_year2 + " and month = " + old_month2 ;
           System.out.println(sql);
           conn.update(sql);
       }
        long end = System.currentTimeMillis();
        System.out.println("It costs " + (end - start) / 1000);

    }
}
