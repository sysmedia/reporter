package com.sysmedia.spark.reporter.updateData;

import com.sysmedia.spark.reporter.util.DataBaseConnection;
import com.sysmedia.spark.reporter.util.DataCollection;

/**
 * update 2017 data as some data were missed by duodian
 * parameter:  shop, ratio
 * 更新所有门店的2017数据，除了输入的shopid
 */

public class Update2017DataForAll {
    public static void  main(String[] args) {

    }
    public static void update(float ratio){

        DataBaseConnection conn = new DataBaseConnection();
        DataCollection collection = new DataCollection();
        String shopId =  "888888";
        String sql;

        //销售额  t_shop_count	totalPrice
        sql = "update t_shop_count set totalPrice = CONCAT((CONVERT(totalPrice,DECIMAL(16,0)) * " + ratio + "), '') where year = 2017 and shop !=" + shopId + " and month = 11";
        conn.update(sql);

        //销售量       t_shop_count	salecount
        sql = "update t_shop_count set salecount = CONCAT((CONVERT(salecount,DECIMAL(16,0)) * " + ratio + "), '') where year = 2017 and shop !=" + shopId + " and month = 11";
        conn.update(sql);

        // 查询 , 购物人次 信息， t_shop_count	totalcount
        sql = "update t_shop_count set totalcount = CONCAT((CONVERT(totalcount,DECIMAL(16,0)) * " + ratio + "), '') where year = 2017 and shop !=" + shopId + " and month = 11";
        conn.update(sql);

        //查询会员购物人次 ，t_shop_customer_count	totalcount
        sql = "update t_shop_customer_count set totalcount = CONCAT((CONVERT(totalcount,DECIMAL(16,0)) * " + ratio + "), '') where year = 2017 and shop !=" + shopId + " and month = 11";
        conn.update(sql);

        //平均购物频次 ,需要t_shop_customer_count(totalcount)/t_customer_number(value)
        sql = "update t_customer_number set value = CONCAT((CONVERT(value,DECIMAL(16,0)) * " + ratio + "), '') where year = 2017 and shop != " + shopId + " and month = 11";
        conn.update(sql);

        //净值等级 关注划分 t_customertype 	value
        sql = "update t_customertype set value = CONCAT(CONVERT((CONVERT(value,SIGNED integer) * " + ratio + "), DECIMAL(16,0)), '') where year = 2017 and shop !=" + shopId + " and month = 11";
        conn.update(sql);

        //关注划分 t_customerconcerns 	value
        sql = "update t_customerconcerns set value = CONCAT(CONVERT((CONVERT(value,SIGNED integer) * " + ratio + "), DECIMAL(16,0)), '') where year = 2017 and shop !=" + shopId + " and month = 11";
        conn.update(sql);

        //时段人流划分： t_shop_day_count	morning afternoon evening
        sql = "update t_shop_day_count set morning = CONCAT(CONVERT((CONVERT(morning,DECIMAL(16,0)) * " + ratio + "), DECIMAL(16,0)), '') where year = 2017 and shop !=" + shopId + " and month = 11";
        conn.update(sql);
        sql = "update t_shop_day_count set afternoon = CONCAT(CONVERT((CONVERT(afternoon,DECIMAL(16,0)) * " + ratio + "), DECIMAL(16,0)), '') where year = 2017 and shop !=" + shopId + " and month = 11";
        conn.update(sql);
        sql = "update t_shop_day_count set evening = CONCAT(CONVERT((CONVERT(evening,DECIMAL(16,0)) * " + ratio + "), DECIMAL(16,0)), '') where year = 2017 and shop !=" + shopId + " and month = 11";
        conn.update(sql);

        //品牌购买人次 : t_pinpai	totalCount
        sql = "update t_pinpai set totalCount = CONCAT(CONVERT((CONVERT(totalCount,SIGNED integer) * " + ratio + "), DECIMAL(16,0)), '') where year = 2017 and shop !=" + shopId + " and month = 11";
        conn.update(sql);

        //品牌销售额 : t_pinpai	totalPrice
        sql = "update t_pinpai set totalPrice = CONCAT(CONVERT((CONVERT(totalPrice,SIGNED integer) * " + ratio + "), DECIMAL(16,0)), '') where year = 2017 and shop !=" + shopId + " and month = 11";
        conn.update(sql);

        //品牌销售量 : t_pinpai_sale	saleCount
        sql = "update t_pinpai_sale set saleCount = CONCAT(CONVERT((CONVERT(saleCount,SIGNED integer) * " + ratio + "), DECIMAL(16,0)), '') where year = 2017 and shop !=" + shopId + " and month = 11";
        conn.update(sql);

        //一级品类购买人次 : t_pinlei_first	totalCount
        sql = "update t_pinlei_first set totalCount = CONCAT(CONVERT((CONVERT(totalCount,SIGNED integer) * " + ratio + "), DECIMAL(16,0)), '') where year = 2017 and shop !=" + shopId + " and month = 11";
        conn.update(sql);

        //一级品类销售额 : t_pinlei_first	totalPrice
        sql = "update t_pinlei_first set totalPrice = CONCAT(CONVERT((CONVERT(totalPrice,SIGNED integer) * " + ratio + "), DECIMAL(16,0)), '') where year = 2017 and shop !=" + shopId + " and month = 11";
        conn.update(sql);

        //一级品类销售量 : t_pinlei_first	saleCount
        sql = "update t_pinlei_first set saleCount = CONCAT(CONVERT((CONVERT(saleCount,SIGNED integer) * " + ratio + "), DECIMAL(16,0)), '') where year = 2017 and shop !=" + shopId + " and month = 11";
        conn.update(sql);

        //二级品类购买人次 : t_pinlei_second	totalCount
        sql = "update t_pinlei_second set totalCount = CONCAT(CONVERT((CONVERT(totalCount,SIGNED integer) * " + ratio + "), DECIMAL(16,0)), '') where year = 2017 and shop !=" + shopId + " and month = 11";
        conn.update(sql);

        //二级品类销售额 : t_pinlei_second	totalPrice
        sql = "update t_pinlei_second set totalPrice = CONCAT(CONVERT((CONVERT(totalPrice,SIGNED integer) * " + ratio + "), DECIMAL(16,0)), '') where year = 2017 and shop !=" + shopId + " and month = 11";
        conn.update(sql);

        //二级品类销售量 : t_pinlei_second	saleCount
        sql = "update t_pinlei_second set saleCount = CONCAT(CONVERT((CONVERT(saleCount,SIGNED integer) * " + ratio + "), DECIMAL(16,0)), '') where year = 2017 and shop !=" + shopId + " and month = 11";
        conn.update(sql);

        //三级品类购买人次 : t_pinlei_third	totalCount
        sql = "update t_pinlei_third set totalCount = CONCAT(CONVERT((CONVERT(totalCount,SIGNED integer) * " + ratio + "), DECIMAL(16,0)), '') where year = 2017 and shop !=" + shopId + " and month = 11";
        conn.update(sql);

        //三级品类销售额 : t_pinlei_third	totalPrice
        sql = "update t_pinlei_third set totalPrice = CONCAT(CONVERT((CONVERT(totalPrice,SIGNED integer) * " + ratio + "), DECIMAL(16,0)), '') where year = 2017 and shop !=" + shopId + " and month = 11";
        conn.update(sql);

        //三级品类销售量 : t_pinlei_third	saleCount
        sql = "update t_pinlei_third set saleCount = CONCAT(CONVERT((CONVERT(saleCount,SIGNED integer) * " + ratio + "), DECIMAL(16,0)), '') where year = 2017 and shop !=" + shopId + " and month = 11";
        conn.update(sql);
    }
}
