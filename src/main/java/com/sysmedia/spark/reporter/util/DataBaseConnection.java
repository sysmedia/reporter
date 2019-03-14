package com.sysmedia.spark.reporter.util;

import com.mysql.jdbc.StringUtils;
import com.sysmedia.spark.reporter.Test;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public class DataBaseConnection {
    // 创建一个数据库连接
    Connection connection = null;
    // 创建编译语句对象
    Statement stat =null;
    // 创建预编译语句对象
    PreparedStatement pstat = null;
    // 创建结果集对象
    ResultSet rs1 = null;
    int rs2 ;

/*    //驱动名称
    private static final String driverName="com.mysql.jdbc.Driver";
    //数据库的地址(通过端口和SID找到对应的数据库)
  //  private static final String URL="jdbc:mysql://192.168.1.90:3306/reporter?characterEncoding=utf8";
   private static final String URL="jdbc:mysql://localhost:3306/reporter?characterEncoding=utf8";

    //数据库登录用户名
    private static final String userName="root";
    //数据库登录密码
   // private static final String pwd="dagong2018";
    private static final String pwd="168168";*/

    public Connection getConnection(){
        try {

            InputStream inputStream = DataBaseConnection.class.getClassLoader()
                    .getResourceAsStream("db.properties");
            Properties properties = new Properties();
//            从输入字节流读取属性列表（键和元素对）
            properties.load(inputStream);
//            用此属性列表中指定的键搜索属性，获取驱动，url，username，password
            String dirverName = properties.getProperty("driverName");
            String url = properties.getProperty("url");
            String username = properties.getProperty("username");
            String password = properties.getProperty("password");
            //加载oracle驱动
            Class.forName(dirverName);
            //通过驱动获取数据库的连接
            if(connection == null) {
                connection = DriverManager.getConnection(url, username, password);
                System.out.println("连接成功");
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return connection;
    }

    public ResultSet query(String sql){
        try {
            //通过刚才的getConnection方法获得一个连接的对象。
            connection = getConnection();
            //向数据库中发送你的sql语句
            stat = connection.createStatement();
            //获得所查询的结果，返回的是Resultset对象
            System.out.println("sql is " + sql);
            rs1 = stat.executeQuery(sql);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return rs1;
    }

    public String getQueryResult(String sql){
        String result = " ";
        try {
            //通过刚才的getConnection方法获得一个连接的对象。
            connection = getConnection();
            //向数据库中发送你的sql语句
            stat = connection.createStatement();
            //获得所查询的结果，返回的是Resultset对象
            rs1 = stat.executeQuery(sql);
            while (rs1.next()) {
                result = rs1.getString(1);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return result;
    }

    public ArrayList<String> getQueryResultList(String sql){
        String result = " ";
        ArrayList<String> list = new ArrayList<String>();
        try {
            //通过刚才的getConnection方法获得一个连接的对象。
            connection = getConnection();
            //向数据库中发送你的sql语句
            stat = connection.createStatement();
            //获得所查询的结果，返回的是Resultset对象
            rs1 = stat.executeQuery(sql);
            while (rs1.next()) {
                result = rs1.getString(1);
                if(StringUtils.isEmptyOrWhitespaceOnly(result))
                    result = " ";
                list.add(result);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return list;
    }
    /**
     * 得到嵌套的ArrayList结果集
     */

    public ArrayList<ArrayList<String>> getQueryResultList2(String sql , int num){
        String result = " ";
        ArrayList<ArrayList<String>> lists = new ArrayList<ArrayList<String>>();
        ArrayList<String> list = new ArrayList<String>();
        try {
            //通过刚才的getConnection方法获得一个连接的对象。
            connection = getConnection();
            //向数据库中发送你的sql语句
            stat = connection.createStatement();
            //获得所查询的结果，返回的是Resultset对象
            rs1 = stat.executeQuery(sql);
            while (rs1.next()) {
                list = new ArrayList<String>();
                for(int i = 0; i < num; i++) {
                    result = rs1.getString(i + 1);
                    if (StringUtils.isEmptyOrWhitespaceOnly(result))
                        result = " ";
                    list.add(result);
                }
                lists.add(list);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return lists;
    }

    public int update(String sql){
        try {
            connection = getConnection();
            stat = connection.createStatement();
            rs2 = stat.executeUpdate(sql);
        }
        catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return rs2;
    }

    public void update(String sql, String[] args){
        try {
            connection = getConnection();
            pstat = connection.prepareStatement(sql);
            System.out.println(sql);
            System.out.println(args.length);
            for (int i = 0; i < args.length; i++) {
                pstat.setString(i+1, args[i]);
                System.out.println(pstat);
            }

            pstat.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public int delete(String sql){
        try {
            connection = getConnection();
            stat = connection.createStatement();
            rs2 = stat.executeUpdate(sql);
            return rs2;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 0;
    }

    public void close(){

        try {
            if(rs1!=null) {
                rs1.close();
            }
            if(stat!=null) {
                rs1.close();
            }
            if(pstat!=null) {
                rs1.close();
            }
            if(connection!=null) {
                rs1.close();
            }

        } catch (SQLException e) {
            // TODO: handle exception
        }
    }




}
