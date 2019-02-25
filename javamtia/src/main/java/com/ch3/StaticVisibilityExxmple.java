package com.ch3;

import com.util.Tools;

import java.util.HashMap;
import java.util.Map;
/**
 * static 关键字仅仅保障读线程读取到相应字段的初始值，而不是相对最新值
 * Created by OKali on 2019/1/2.
 */
public class StaticVisibilityExxmple {

    private static Map<String, String> taskConfig;

    static {
        System.out.println("The class initialized...");
        taskConfig = new HashMap<>();
        taskConfig.put("url", "https://www.pamgo.com");
        taskConfig.put("timeout", "1000");
    }

    public static void changeConfig(String url, String timeout) {
        taskConfig = new HashMap<>();
        taskConfig.put("url", url);
        taskConfig.put("timeout", timeout);
    }

    public static void init() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                String url = taskConfig.get("url");
                String timeout = taskConfig.get("timeout");
               doTask(url, timeout);
            }
        });
        t.start();
    }

    private static void doTask(String url, String timeout) {
        // TODO

        Tools.randomPause(500);
    }
}
