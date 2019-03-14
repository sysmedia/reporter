package com.sysmedia.spark.reporter;

import java.io.InputStream;
import java.util.Properties;

public class Test {
    public static void main(String[] args) throws Exception {
        InputStream inputStream = Test.class.getClassLoader()
                .getResourceAsStream("db.properties");
        Properties properties = new Properties();
//            从输入字节流读取属性列表（键和元素对）
        properties.load(inputStream);
//            用此属性列表中指定的键搜索属性，获取驱动，url，username，password
        String dirverName = properties.getProperty("driverName");
        String url = properties.getProperty("url");
        String username = properties.getProperty("username");
        String password = properties.getProperty("password");
        System.out.println(dirverName);
        System.out.println(url);
        System.out.println(username);
        System.out.println(password);

    }

}
