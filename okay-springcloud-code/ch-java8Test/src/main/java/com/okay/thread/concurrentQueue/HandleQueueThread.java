package com.okay.thread.concurrentQueue;

import java.util.Random;

/**
 * Created by OKali on 2019/4/21.
 */
public class HandleQueueThread implements Runnable {
    protected String name;

    Random rand = new Random();

    public HandleQueueThread(){}

    public HandleQueueThread(String name) {
        this.name = name;
    }

    @Override
    public void run() {

        try {
            for (int i = 0; i < 500; i++) {
                Thread.sleep(rand.nextInt(100));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
