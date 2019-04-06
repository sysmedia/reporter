package com.sysmedia.spark.reporter.util;

import java.util.ArrayList;

public class DBUtil {
    public static DataBaseConnection conn = new DataBaseConnection();
    public static ArrayList<String> getShopList(String sql){
        ArrayList<String>  result =   conn.getQueryResultList(sql);
        return result;
    }

    public static void main(String[] args) {
        String sql = "select id from t_new_shop where flag = 1 and (type = '北京标大' or type = '北京大卖场' or type = '北京标中')";
        ArrayList<String> list = getShopList(sql);
        System.out.println(list);

        sql = "select id  from t_new_shop where city = '重庆市'";
        list = getShopList(sql);
        System.out.println(list);
    }
}
