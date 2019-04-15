package com.okay.thread.producerAndConsumer;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by OKali on 2019/4/15.
 */
public class Producer implements Runnable {

    private volatile boolean isRunning = true;

    private BlockingQueue<PCData> queue;             // 内存缓存区

    private static AtomicInteger count = new AtomicInteger();  // 总数，原子性操作

    private static final int SLEEPTIME = 1000;

    public Producer(BlockingQueue<PCData> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {

        PCData data = null;

        Random r = new Random();
        System.out.println("start  producer id=" + Thread.currentThread().getId());
        try {
            while (isRunning) {
                Thread.sleep(r.nextInt(SLEEPTIME));

                data = new PCData(count.incrementAndGet()); // 构造原子性
                System.out.println(data + " is put into queuq.");
                if (!queue.offer(data,2, TimeUnit.SECONDS)) {  // 提交到缓冲区中
                    System.err.println("failed to put data:" + data);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }

    public void stop() {
        isRunning = false;
    }
}
