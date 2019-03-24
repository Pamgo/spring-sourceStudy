package com.okay.thread;

/**
 * Created by OKali on 2019/3/23.
 */
public class ThreadThreadMen {

    public static Long shareLong = new Long(2L);

    public static void sayHello() {
        int c = 3;
        Long d = shareLong;
        System.out.println(Thread.currentThread().getName()+ " " + c + " "+ d);
    }

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            int a = 2;
            sayHello();
        }, "thread-1").start();

        new Thread(() -> {
            int b = 3;
            sayHello();
        },"thread-2").start();

        Thread.currentThread().join();
    }
}
