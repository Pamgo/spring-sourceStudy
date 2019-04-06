package com.okay.thread.workerAndMaster;

import java.util.Map;
import java.util.Queue;

/**
 * Created by OKali on 2019/4/5.
 */
public abstract class Worker implements Runnable {

    // 任务队列，用于取得子任务
    private Queue<Object> workQueue;
    // 子任务处理结果集
    private Map<String, Object> resultMap;


    public void setWorkQueue(Queue<Object> queue) {
        this.workQueue  = queue;
    }

    public void setResultMap(Map<String, Object> resultMap) {
        this.resultMap = resultMap;
    }

    // 子任务处理逻辑，在子类中实现具体逻辑
    public abstract Object handleObject(Object input);

    @Override
    public void run() {
        while (true) {
            // 获取子任务
            Object input = workQueue.poll();

            if (input == null) break;
            // 处理子任务
            Object result = handleObject(input);
            // 将处理结果写入结果集
            resultMap.put(String.valueOf(input.hashCode()), result);

        }
    }
}
