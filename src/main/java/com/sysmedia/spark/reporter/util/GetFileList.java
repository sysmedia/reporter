package com.sysmedia.spark.reporter.util;

import java.io.File;
import java.util.ArrayList;

/**
 * 得到数据完整的门店列表，导出到list里面
 */
public class GetFileList {

    public static void main(String[] args) {
        ArrayList<String> shops = new ArrayList<String>();
        StringBuffer sb = new StringBuffer();
        File root = new File("C:\\project\\bakcup\\report0131\\北京标中");
        for(File file: root.listFiles()) {
            String id = file.getName().split("-")[0];
            //System.out.println(id);
            shops.add(id);
            sb.append("\"" + id + "\",");

        }
        System.out.println(sb);



    }
}
