package com.ch3;

import java.util.Map;

/**
 * 安全发布对象
 * 1、使用static，final,volatile,AtomicReference,加锁关键字修饰引用对象的变量
 * Created by OKali on 2019/1/2.
 */
public class SafeObjectPublishWhenStartingThread {

    private final Map<String, String> objectstate;

    private SafeObjectPublishWhenStartingThread(Map<String, String> objectstate) {
        this.objectstate = objectstate;
    }

    private void init() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String someKey = objectstate.get("someKey");
                System.out.println(someKey);
            }
        }).start();
    }

    public static SafeObjectPublishWhenStartingThread newInstane(Map<String, String> objstate) {
        SafeObjectPublishWhenStartingThread instance
                = new SafeObjectPublishWhenStartingThread(objstate);
        instance.init();
        return instance;
    }
}

