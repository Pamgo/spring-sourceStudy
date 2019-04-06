package com.okay.thread.future;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * Created by OKali on 2019/4/5.
 */
public class Test {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<String> futureTask = new FutureTask<String>(new RealData("okay"));

        ExecutorService executorService = Executors.newFixedThreadPool(1);
        // 执行futureTask
        executorService.submit(futureTask);
        System.out.println("请求执行完毕！");

        try {
            // TODO 做其他数据操作
            Thread.sleep(2000);
        } catch (InterruptedException e) {
        }

        System.out.println("数据=" + futureTask.get());
    }
}
