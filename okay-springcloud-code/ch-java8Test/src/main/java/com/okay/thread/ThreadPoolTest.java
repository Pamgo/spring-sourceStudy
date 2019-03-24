package com.okay.thread;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by OKali on 2019/3/23.
 */
public class ThreadPoolTest {

    static ThreadPoolExecutor executorOne =
            new ThreadPoolExecutor(5,5,1,
                    TimeUnit.MINUTES, new LinkedBlockingQueue<>(), new NamedThreadFactory("ASYN-ACCEPT-POOL"));
    static ThreadPoolExecutor executorTwo=
            new ThreadPoolExecutor(5,5,1,
                    TimeUnit.MINUTES, new LinkedBlockingQueue<>(), new NamedThreadFactory("ASYN-PROCESS-POOL"));

    public static void main(String[] args) {
        executorOne.execute(() -> {
            System.out.println("接收用户请求");
            throw new NullPointerException();
        });

        executorTwo.execute(() -> {
            System.out.println("具体业务请求线程");
        });

        executorOne.shutdown();
        executorTwo.shutdown();
    }
}
