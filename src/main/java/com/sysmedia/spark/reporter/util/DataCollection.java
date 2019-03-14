package com.sysmedia.spark.reporter.util;

import com.mysql.jdbc.StringUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class DataCollection {
    private static DataBaseConnection conn = new DataBaseConnection();;
    private static String year1 = "2017";
    private static String month1 = "11";
    private static String year2 = "2018";
    private static String month2 = "11";
    private static String sm = "28";
    private ArrayList<String> yearList = new ArrayList<String>(Arrays.asList(year1, year2));
    private ArrayList<String> typeList = new ArrayList<String>(Arrays.asList("全部", "工作日", "周末"));
    private ArrayList<String> payTypeList = new ArrayList<String>(Arrays.asList("现金","微信","支付宝","银行卡","其他"));
    private ArrayList<String> customerTypeList = new ArrayList<String>(Arrays.asList("high", "medium", "low"));
    private ArrayList<String> customerConcernsList = new ArrayList<String>(Arrays.asList("品质关注型", "价格敏感型", "主流类型"));
    private ArrayList<String> timeTypeList = new ArrayList<String>(Arrays.asList("morning", "afternoon", "evening"));



    public String getShopCountInfo(String shopId) {
        //获得购买人次 表名：t_shop_count  字段：totalcount
        String query1 = "select totalcount from t_shop_count where year = " + year1 + " and month = " + month1
                + " and sm = " + sm + " and shop = " + shopId + " and type = '全部'";
        System.out.println("query is " + query1);
        String result = conn.getQueryResult(query1);
        System.out.println("result is " + result);
        return result;
    }

    /**
     * 通过表名，列名，门店号，类型来得到单行数据
     */

    public String getSingleLineInfoByType(String table, String column, String shopId, String year, String type) {
        String query1 = "select " + column + " from " +  table  + " where year = " + year + " and month = " + month1
                + " and sm = " + sm + " and shop = " + shopId + " and type = '" + type + "'";
       // System.out.println("query is " + query1);
        String result = conn.getQueryResult(query1);
       // System.out.println("result is " + result);
        if(result.contains(".")){
            double value = Double.parseDouble(result);
            result = String.valueOf((long)value);
        }

        return result;
    }

    /**
     * 通过表名，列名，门店号来得到单行数据
     * 一共得到6个数据，用tab分割
     */

    public ArrayList<String> getSingleLineInfo(String table, String column, String shopId) {
        //StringBuffer sb = new StringBuffer();
        ArrayList<String> list = new ArrayList<String>();
        for (String type : typeList) {
            for (String year : yearList) {
                list.add(getSingleLineInfoByType(table, column, shopId, year, type));
               /* sb.append(getSingleLineInfoByType(table, column, shopId, year, type));
                sb.append("\t");*/
            }
        }
        return list;
    }
    /**
     * 通过表名，列名，门店号来得到多行时段数据
     * 一共得到6个数据，用tab分割
     */

    public ArrayList<ArrayList<String>> getMultiLineTimeInfo(String table, String shopId) {
        ArrayList<String> level = new ArrayList<String>(Arrays.asList("早（0-11点）","中（11-18）","晚（18-24）"));
        StringBuffer sb = new StringBuffer();
        ArrayList<ArrayList<String>> lists = new ArrayList<ArrayList<String>>();
        ArrayList<String> list;
        int i = 0;
        for(String timeType: timeTypeList) {
            list = new ArrayList<String>();
            list.add("时段人流划分");
            list.add(level.get(i));
            i++;
            for (String type : typeList) {
                for (String year : yearList) {
                    list.add(getSingleLineInfoByType(table, timeType, shopId, year, type));
                }
            }
            lists.add(list);
        }
        return lists;
    }

    /**
     * 通过门店号来得到平均购买频次单行数据
     * 一共得到6个数据，用tab分割
     *  平均购物频次 ,需要t_shop_customer_count(totalCount)/t_customer_number(value)
     */

    public ArrayList<String> getSingleLineAvgTotalCountInfo(String shopId) {
        ArrayList<String> list = new ArrayList<String>();
        double totalCount = 1.0;
        int customerNumber = 1;
        DecimalFormat df = new DecimalFormat("#.00");
        for(String type: typeList){
            for(String year: yearList) {
                String count = getSingleLineInfoByType("t_shop_customer_count", "totalCount", shopId, year, type);
                System.out.println("count is " + count);
                if (StringUtils.isEmptyOrWhitespaceOnly(count))
                    count = "0";
                totalCount =  Double.parseDouble(count);

                String number = getSingleLineInfoByType("t_customer_number", "value", shopId, year, type);
                if (StringUtils.isEmptyOrWhitespaceOnly(number) || number.equals(" "))
                    number = "1";
                customerNumber =  Integer.parseInt(number);
                double avgTotalCount = (totalCount)/customerNumber;

                list.add(String.valueOf(df.format(avgTotalCount)));
            }
        }
        return list;
    }

    /**
     * 通过表名，列名，门店号，类型,支付类型来得到单列支付数据
     */
    public String getSinglePayInfoByPayType(String table, String column, String shopId, String year, String type, String payType) {
        String query1 = "select " + column + " from " +  table  + " where year = " + year + " and month = " + month1
                + " and shop = " + shopId + " and type = '" + type + "'" + " and payType = '" + payType + "'"; //+ " and sm = " + sm
//        System.out.println("query is " + query1);
        String result = conn.getQueryResult(query1);
//        System.out.println("result is " + result);
        return result;
    }

    /**
     * 通过表名，列名，门店号，类型,来得到支付数据sum
     */
    public String getSumPayInfoByType(String table, String column, String shopId, String year, String type) {
        String query1 = "select sum(" + column + ") from " +  table  + " where year = " + year + " and month = " + month1
                + " and shop = " + shopId + " and type = '" + type + "'" ; //+ " and sm = " + sm
//        System.out.println("query is " + query1);
        String result = conn.getQueryResult(query1);
//        System.out.println("result is " + result);
        return result;
    }


    /**
     * 通过表名，列名，门店号来得到多行行支付方式数据
     * 一共得到五行6列数据，用tab分割
     */

    public  ArrayList<ArrayList<String>> getMultiPayInfo(String table, String column, String shopId) {
        StringBuffer sb = new StringBuffer();
        ArrayList<ArrayList<String>> lists = new ArrayList<ArrayList<String>>();
        ArrayList<String> list;
        DecimalFormat df = new DecimalFormat("#.00");
        for(String payType: payTypeList) {
            list = new ArrayList<String>();
            list.add("支付方式占比");
            list.add(payType);
             for (String type : typeList) {
                for (String year : yearList) {
                    //list.add(getSinglePayInfoByPayType(table, column, shopId, year, type, payType));
                    String number = getSinglePayInfoByPayType(table, column, shopId, year, type, payType);
                    if(number.equals(" ")) number = "0";

                    int value = Integer.parseInt(number);

                    String result = getSumPayInfoByType(table, column, shopId, year,type);
                    if(StringUtils.isEmptyOrWhitespaceOnly(result)) result = "1";
                    int sum = Integer.parseInt(result);
                    double rate = (double)value / sum * 100;
                    list.add(String.valueOf(df.format(rate)) + "%");
                }
            }
            lists.add(list);
        }
        return lists;
    }



    /**
     * 通过表名，列名，门店号，类型,会员净值等级来得到单列会员净值数据
     */

    public String getSingleCustomerTypeInfoByCumstomerType(String table, String column, String shopId, String year, String type, String customerType) {
        String query1 = "select " + column + " from " +  table  + " where year = " + year + " and month = " + month1
                + " and shop = " + shopId + " and type = '" + type + "'" + " and level = '" + customerType + "'"; //+ " and sm = " + sm
//        System.out.println("query is " + query1);
        String result = conn.getQueryResult(query1);
//        System.out.println("result is " + result);
        return result;
    }

    /**
     * 通过表名，列名，门店号来得到多行会员净值等级数据
     * 一共得到3行6列数据，用tab分割
     */

    public  ArrayList<ArrayList<String>> getMultiCustomerTypeInfo(String table, String column, String shopId) {
        ArrayList<String> level = new ArrayList<String>(Arrays.asList("高级净值","中等净值","普通净值"));
        StringBuffer sb = new StringBuffer();
        ArrayList<ArrayList<String>> lists = new ArrayList<ArrayList<String>>();
        ArrayList<String> list;
        int i = 0;
        for(String customerType: customerTypeList) {
            list = new ArrayList<String>();
            list.add("净值等级");
            list.add(level.get(i));
            i++;
            for (String type : typeList) {
                for (String year : yearList) {
                    list.add(getSingleCustomerTypeInfoByCumstomerType(table, column, shopId, year, type, customerType));
                }
            }
            lists.add(list);
        }
        return lists;
    }

    /**
     * 通过表名，列名，门店号，类型,会员关注等级来得到单列会员关注数据
     */

    public String getSingleCustomerConcernsInfoByCumstomerConcerns(String table, String column, String shopId, String year, String type, String customerConcerns) {
        String query1 = "select " + column + " from " +  table  + " where year = " + year + " and month = " + month1
                + " and shop = " + shopId + " and type = '" + type + "'" + " and level = '" + customerConcerns + "'"; //+ " and sm = " + sm
//        System.out.println("query is " + query1);
        String result = conn.getQueryResult(query1);
//        System.out.println("result is " + result);
        return result;
    }

    /**
     * 通过表名，列名，门店号来得到多行会员关注等级数据
     * 一共得到3行6列数据，用tab分割
     */

    public ArrayList<ArrayList<String>>  getMultiCustomerConcernsInfo(String table, String column, String shopId) {
        ArrayList<String> level = new ArrayList<String>(Arrays.asList("品质关注","价格敏感","主流类型"));
        StringBuffer sb = new StringBuffer();
        ArrayList<ArrayList<String>> lists = new ArrayList<ArrayList<String>>();
        ArrayList<String> list;
        int i = 0;
        for(String customerConcerns: customerConcernsList) {
            list = new ArrayList<String>();
            list.add("关注划分");
            list.add(level.get(i));
            i++;
            for (String type : typeList) {
                for (String year : yearList) {
                    list.add(getSingleCustomerConcernsInfoByCumstomerConcerns(table, column, shopId, year, type, customerConcerns));
                }
            }
            lists.add(list);
        }
        return lists;
    }

    /**
     * 通过表名，列名，门店号，类型来得到品牌排行数据
     */

    public ArrayList<String> getSingleTopBrandInfo(String table, String column, String shopId, String year, String type, int num) {
        String query1 = "select cate from " +  table  + " where year = " + year + " and month = " + month1
                + " and shop = " + shopId + " and type = '" + type + "'" + " order by " + column + " DESC LIMIT 0 , " + num ; //+ " and sm = " + sm
        ArrayList<String> result = conn.getQueryResultList(query1);
        if(result.size() < 5) {
            for(int i = 0; i < 9 - result.size(); i++)
                result.add(" ");
        }
        return result;
    }

    /**
     * 通过表名，列名，门店号，类型来得到品类排行数据
     */

    public ArrayList<String> getSingleTopCategoryInfo(String table, String category, String column, String shopId, String year, String type, int num) {
        String query1 = "select " + category + " from " +  table  + " where year = " + year + " and month = " + month1
                + " and shop = " + shopId + " and type = '" + type + "'" + " order by " + column + " DESC LIMIT 0 , " + num ; //+ " and sm = " + sm
//        System.out.println("query is " + query1);
        ArrayList<String> result = conn.getQueryResultList(query1);
//        System.out.println("result is " + result);
        return result;
    }

    /**
     * 通过表名，列名，门店号来得到多行品牌偏好数据
     * 一共得到5行6列数据，用tab分割
     */

    public ArrayList<ArrayList<String>>  getMultiTopBrandInfo(String table, String column, String shopId) {
        ArrayList<String> level = new ArrayList<String>(Arrays.asList("TOP1","TOP2","TOP3","TOP4","TOP5"));
        StringBuffer sb = new StringBuffer();
        ArrayList<ArrayList<String>> lists = new ArrayList<ArrayList<String>>();
        ArrayList<String> list;
        ArrayList<String> brands;

            for (String type : typeList) {
                for (String year : yearList) {
                    brands = getSingleTopBrandInfo(table, column, shopId, year, type, 5);
                    if(brands.size() < 5)
                        return lists;
                    lists.add(brands);
                }
            }
            System.out.println("top lists is " + lists  );

        ArrayList<ArrayList<String>> tops = new ArrayList<ArrayList<String>>();
            for(int i = 0; i < level.size(); i++) {
                list = new ArrayList<String>();
                list.add("品牌偏好");
                list.add(level.get(i));
                for(int j = 0; j < typeList.size() * yearList.size(); j++) {
                    list.add(lists.get(j).get(i));
                }
                tops.add(list);
            }
        return tops;
    }

    /**
     * 通过表名，列名，门店号，类型，品牌来得到单行品牌数据
     */

    public String getSingleLineBrandInfoByBrand(String table, String column, String shopId, String year, String type, String brand) {
        String query1 = "select " + column + " from " +  table  + " where year = " + year + " and month = " + month1
                + " and sm = " + sm + " and shop = " + shopId + " and type = '" + type + "'" + " and cate = '" + brand + "'";
//        System.out.println("query is " + query1);
        String result = conn.getQueryResult(query1);
        if(result.contains(".")){
            double value = Double.parseDouble(result);
            result = String.valueOf((int)value);
        }
//        System.out.println("result is " + result);
        return result;
    }

    /**
     * 通过表名，列名，门店号，类型，品类来得到单行品类数据
     */

    public String getSingleLineCategoryInfoByCategory(String table, String category, String column, String shopId, String year, String type, String categoryName) {
        String query1 = "select " + column + " from " +  table  + " where year = " + year + " and month = " + month1
                + " and sm = " + sm + " and shop = " + shopId + " and type = '" + type + "'" + " and " + category + "= '" + categoryName + "'";
//        System.out.println("query is " + query1);
        String result = conn.getQueryResult(query1);
        if(result.contains(".")){
            double value = Double.parseDouble(result);
            result = String.valueOf((int)value);
        }
//        System.out.println("result is " + result);
        return result;
    }

    /**
     * 通过表名，列名，门店号来得到多行品牌购买人次,销售额，销售量数据
     * 一共得到10行6列数据，用tab分割
     */

    public ArrayList<ArrayList<String>>  getMultiTopBrandPurchaseInfo(String rowName, String table, String column, String shopId) {
        ArrayList<String> brands = getSingleTopBrandInfo(table, column, shopId, "2017","全部", 10);
        HashMap<String, String> map =  getCategoryMap(table, "cate", column, shopId);
        String key = new String();
        String value = " ";
        ArrayList<ArrayList<String>> lists = new ArrayList<ArrayList<String>>();
        ArrayList<String> list;
        for(String brand: brands) {
            list = new ArrayList<String>();
            list.add(rowName);
            list.add(brand);
            for (String type : typeList) {
                for (String year : yearList) {
                    //list.add(getSingleLineBrandInfoByBrand(table, column, shopId, year, type, brand));
                    key = year + brand + type;
                    if(map.containsKey(key))
                        list.add(map.get(key));
                    else
                        list.add(value);
                }
            }
            lists.add(list);
        }
        return lists;
    }

    /**
     * 通过表名，列名，门店号来得到多行品类购买人次,销售额，销售量数据
     * 一共得到16行6列数据，用tab分割
     * 备份
     */

  /*  public ArrayList<ArrayList<String>>  backupgetMultiTopCategoryPurchaseInfo(String rowName, String table, String category, String column, String shopId, int num) {
        System.out.println("begin get multiple category info" );
        long start = System.currentTimeMillis();
        ArrayList<String> categories = getSingleTopCategoryInfo(table, category, column, shopId, "2017","全部", num);
        long end = System.currentTimeMillis();
        System.out.println("it takes to get multiple category list " + (start - end ));


        ArrayList<ArrayList<String>> lists = new ArrayList<ArrayList<String>>();
        ArrayList<String> list;
        for(String categoryName: categories) {
            list = new ArrayList<String>();
            list.add(rowName);
            list.add(categoryName);
            for (String type : typeList) {
                for (String year : yearList) {
                    start = System.currentTimeMillis();
                    //System.out.println(start);
                    list.add(getSingleLineCategoryInfoByCategory(table, category, column, shopId, year, type, categoryName));
                    end = System.currentTimeMillis();
                    //System.out.println(end);
                    System.out.println("it takes to get single category list " + (start - end ));
                }
            }
            lists.add(list);
        }
        return lists;
    }*/

    /**
     * 改进版，解决时间太长的问题，通过Map来一次性读取品类数据
     * 通过表名，列名，门店号来得到多行品类购买人次,销售额，销售量数据
     * 一共得到16行6列数据，用tab分割
     */

    public ArrayList<ArrayList<String>>  getMultiTopCategoryPurchaseInfo(String rowName, String table, String category, String column, String shopId, int num) {

        ArrayList<String> categories = getSingleTopCategoryInfo(table, category, column, shopId, "2017","全部", num);
        if(categories.size() == 0)
            categories = getSingleTopCategoryInfo(table, category, column, "888888", "2017","全部", num);
        ArrayList<ArrayList<String>> lists = new ArrayList<ArrayList<String>>();
        ArrayList<String> list;
        HashMap<String, String> map =  getCategoryMap(table, category, column, shopId);
        String key = new String();
        String value = " ";
        for(String categoryName: categories) {
            list = new ArrayList<String>();
            list.add(rowName);
            list.add(categoryName);
            for (String type : typeList) {
                for (String year : yearList) {
                    //list.add(getSingleLineCategoryInfoByCategory(table, category, column, shopId, year, type, categoryName));
                    key = year + categoryName + type;
                    if(map.containsKey(key))
                        list.add(map.get(key));
                    else
                        list.add(value);
                }
            }
            lists.add(list);
        }
        return lists;
    }

    public HashMap<String, String> getCategoryMap(String table, String category, String column, String shopId ){
        HashMap<String, String> map = new HashMap<String, String>();
        ArrayList<ArrayList<String>> lists = conn.getQueryResultList2("select year, " +  category + ", type, "
                +  column + " from " + table + " where shop = " + shopId, 4);
        for(ArrayList<String> list: lists){
            int size = list.size();
            String key = new String();
            for(int i = 0; i < list.size() - 1; i++)
                key += list.get(i);
            if(map.containsKey(key)) {
                map.put(key, String.valueOf(Math.max(Integer.parseInt(map.get(key)) , Integer.parseInt(list.get(size-1)))));
            } else {
                map.put(key, list.get(size-1));
            }
        }
        return map;
    }

    /**
     * 得到门店list
     */

    public ArrayList<String> getDistinctShopInfo() {
        String query1 = "SELECT distinct shop FROM  `t_shop_day_count`   ORDER BY shop " ;
        ArrayList<String> result = conn.getQueryResultList(query1);
        return result;
    }

    /**
     * 得到门店详细信息
     */

    public String getDetailShopInfo(String shop, String column) {
        String query1 = "SELECT " + column + " FROM `shop`   where id = " + shop ;
        String result = conn.getQueryResult(query1);
        return result;
    }


    public static void main(String[] args ) {
        DataCollection collection = new DataCollection();
;
        HashMap<String, String> map =  collection.getCategoryMap("t_pinlei_third", "category3", "saleCount", "888888");

        System.out.println(map);
        String query = "2017牙膏全部";
        System.out.println(map.get(query));
         ArrayList<String> shops = collection.getDistinctShopInfo();
        System.out.println(shops.size());

     //   for(String shop : shops) {
           /* String type = collection.getDetailShopInfo("1801", "type");
            System.out.println(type);*/
           // if(shopInfo.size() == 0)
        // System.out.println(1801 + " : " + shopInfo.get(0) + shopInfo.get(1) + shopInfo.get(2));

            //    System.out.println(shop + " has no name");
     //   }

       /* ArrayList<ArrayList<String>> lists = collection.getMultiTopBrandInfo("t_pinpai_sale", "cate", "1313");
        System.out.println(lists);
        ArrayList<String> result = collection.getSingleTopBrandInfo("t_pinpai_sale", "cate", "888888", "2017","全部", 10);
        System.out.println(result);
        DataBaseConnection conn = new DataBaseConnection();
        ArrayList<String> list = conn.getQueryResultList("SELECT cate FROM  `t_pinpai_sale` WHERE shop =1313 AND YEAR =2018 AND TYPE='工作日' ORDER BY saleCount DESC LIMIT 0 , 5");
        System.out.println(list);*/
    }

}
