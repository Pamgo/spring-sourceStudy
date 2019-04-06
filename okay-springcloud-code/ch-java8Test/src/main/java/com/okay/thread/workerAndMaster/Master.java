package com.okay.thread.workerAndMaster;


import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * Created by OKali on 2019/4/5.
 */
public class Master {
    // 任务队列
    private Queue<Object> workQueue =  new ConcurrentLinkedDeque<>();
    // Worker进程队列
    private Map<String, Thread> threadMap = new HashMap<>();
    // 子任务处理结果集
    private Map<String, Object> resultMap = new ConcurrentHashMap<>();

    // 是否所有的子任务都结束了
    public boolean isComplete() {
        for (Map.Entry<String, Thread> entry : threadMap.entrySet()) {
            if (entry.getValue().getState() != Thread.State.TERMINATED) {
                return false;
            }
        }
        return true;
    }

    public Master(Worker worker, int countWorker) {
        worker.setWorkQueue(workQueue);
        worker.setResultMap(resultMap);
        for (int i = 0; i < countWorker; i++) {
            threadMap.put(Integer.toString(i), new Thread(worker, Integer.toString(i)));
        }
    }

    // 提交一个任务
    public void submit(Object job) {
        workQueue.add(job);
    }

    public Map<String, Object> getResultMap() {
        return resultMap;
    }

    public void execute() {
        for (Map.Entry<String, Thread> entry : threadMap.entrySet()) {
            entry.getValue().start();
        }
    }
}
