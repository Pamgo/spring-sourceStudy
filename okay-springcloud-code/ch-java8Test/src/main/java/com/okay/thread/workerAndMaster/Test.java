package com.okay.thread.workerAndMaster;

import java.util.Map;
import java.util.Set;

/**
 * Created by OKali on 2019/4/5.
 */
public class Test {

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        Master master = new Master(new PlusWorker(), 5); //  固定使用5个Worker

        for (int i = 0; i < 100; i++) {
            master.submit(i);  // 提交100个子任务
        }
        master.execute();
        int re = 0;
        Map<String, Object> resultMap = master.getResultMap();
        while (resultMap.size() > 0 || !master.isComplete()) {
            // 不需要等到所有Worker都执行完，即可
            Set<String> keys = resultMap.keySet();
            String key = null;
            for (String k : keys) {
                key = k;
                break;
            }
            Integer i = null;
            if (key != null)
                i = (Integer) resultMap.get(key);
            if (i != null) {
                re += i;
            }
            if (key != null)
                resultMap.remove(key);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("结果："+re+"，耗时：" + (endTime - startTime) +"ms");
    }
}
