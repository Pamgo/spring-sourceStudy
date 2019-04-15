package com.okay.thread.guardSuspension;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by OKali on 2019/4/7.
 */
public class ClientThread extends Thread {

    private RequestQueue requestQueue;  // 请求队列

    public ClientThread(RequestQueue requestQueue, String name) {
        super(name);
        this.requestQueue = requestQueue;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            Request request =
                    new Request("RequestID: " + i + ",ThreadName :" + Thread.currentThread().getName());
            System.out.println(Thread.currentThread().getName() + " request " + request.toString());
            requestQueue.addRequest(request);
            try {
                Thread.sleep(10); // 比服务器处理速度要快
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        System.out.println(Thread.currentThread().getName() + " request end");
    }
}

