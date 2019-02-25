package com.ch3;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by OKali on 2019/1/2.
 */
public enum AlarmMgr implements Runnable {
    // 保存该类的唯一实例
    INITANCE;

    private final AtomicBoolean initializating = new AtomicBoolean(false);

    AlarmMgr() {

    }

    public void init() {
        // 使用AtomicBoolean 的CAS操作确保工作者线程只会被创建（并启动一次）
        if (initializating.compareAndSet(false, true)) {
            System.out.println("initializating ...");
            new Thread(this).start();
        }
    }

    public int sendAlarm(String message) {
        int result = 0;
        return result;
    }

    @Override
    public void run() {

    }
}
