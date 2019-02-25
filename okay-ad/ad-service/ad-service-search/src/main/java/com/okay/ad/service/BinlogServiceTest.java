package com.okay.ad.service;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.*;

/**
 * Created by OKali on 2019/2/1.
 */
public class BinlogServiceTest {

    public static void main(String[] args) throws Exception {

        // 监听binlog客户端
        BinaryLogClient client = new BinaryLogClient(
                "127.0.0.1",
                3306,
                "root",
                "root"
        );
        // 从那个文件开始监听
//        client.setBinlogFilename("mysql-bin.000013");
        // 监听位置
//        client.setBinlogPosition();
        // 注册监听器,默认最新的开始

        client.registerEventListener(event -> {
            EventData data = event.getData();
            if (data instanceof UpdateRowsEventData) {
                System.out.println("update ........");
                System.out.println(data.toString());
            } else if (data instanceof WriteRowsEventData) {
                System.out.println("write ........");
                System.out.println(data.toString());
            } else if (data instanceof DeleteRowsEventData) {
                System.out.println("delete ........");
                System.out.println(data.toString());
            }
        });
        client.connect();
    }
}
