package com.okay.thread.guardSuspension;

import java.util.LinkedList;

/**
 * 维护系统的Request列表
 * Created by OKali on 2019/4/7.
 */
public class RequestQueue {

    private LinkedList queue = new LinkedList();

    public synchronized Request getRequest() {
        while (queue.size() == 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return (Request) queue.remove();
    }

    public synchronized void addRequest(Request request) {
        queue.add(request);
        notifyAll();
    }
}
