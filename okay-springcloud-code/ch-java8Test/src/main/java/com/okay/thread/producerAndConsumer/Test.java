package com.okay.thread.producerAndConsumer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by OKali on 2019/4/15.
 */
public class Test {

    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<PCData> queue = new LinkedBlockingQueue<>(10); // 建立缓冲区

        Producer producer = new Producer(queue); // 建立生产者
        Producer producer1 = new Producer(queue); // 建立生产者
        Producer producer2 = new Producer(queue); // 建立生产者
        Producer producer3 = new Producer(queue); // 建立生产者
        Producer producer4 = new Producer(queue); // 建立生产者
        Producer producer5 = new Producer(queue); // 建立生产者
        Producer producer6 = new Producer(queue); // 建立生产者
        Producer producer7 = new Producer(queue); // 建立生产者

        Consumer consumer = new Consumer(queue); // 建立消费者
        Consumer consumer1 = new Consumer(queue); // 建立消费者
        Consumer consumer2 = new Consumer(queue); // 建立消费者

        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(producer);
        executorService.execute(producer1);
        executorService.execute(producer2);
        executorService.execute(producer3);
        executorService.execute(producer4);
        executorService.execute(producer5);
        executorService.execute(producer6);
        executorService.execute(producer7);

        executorService.execute(consumer);
        executorService.execute(consumer1);
        executorService.execute(consumer2);

        Thread.sleep(10*1000);

        producer.stop();
        producer1.stop();
        producer2.stop();
        producer3.stop();
        producer4.stop();
        producer5.stop();
        producer6.stop();
        producer7.stop();

        Thread.sleep(3000);

        executorService.shutdown();

    }
}
