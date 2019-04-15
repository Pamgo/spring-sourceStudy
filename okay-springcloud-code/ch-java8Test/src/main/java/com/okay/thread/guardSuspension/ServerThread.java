package com.okay.thread.guardSuspension;

/**
 * Created by OKali on 2019/4/7.
 */
public class ServerThread extends Thread {

    private RequestQueue requestQueue;

    public ServerThread(RequestQueue requestQueue, String name) {
        super(name);
        this.requestQueue = requestQueue;
    }

    @Override
    public void run() {
        while (true) {
            final Request request = requestQueue.getRequest();

            try {
                // 模拟处理
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " handles " + request.toString());
        }
    }
}
