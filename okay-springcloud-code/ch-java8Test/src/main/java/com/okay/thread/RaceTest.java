package com.okay.thread;

/**
 * Created by OKali on 2019/3/23.
 */
public class RaceTest {

    static public volatile int a = 0;

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            a = 1;
        },"thread-one").start();

        if (0 == a) {
            Thread.sleep(2000);
            System.out.println(a);
        }
    }
}
