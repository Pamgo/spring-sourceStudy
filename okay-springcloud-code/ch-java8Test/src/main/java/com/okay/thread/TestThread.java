package com.okay.thread;

/**
 * Created by OKali on 2019/3/23.
 */
public class TestThread {

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            for(;;) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("testThread hello");
            }
        }, "thread").start();

        Thread.currentThread().join();
    }
}
