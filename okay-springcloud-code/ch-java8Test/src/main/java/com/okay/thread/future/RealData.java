package com.okay.thread.future;

import java.util.concurrent.Callable;

/**
 * Created by OKali on 2019/4/5.
 */
public class RealData implements Callable<String> {

    private String param;

    public RealData(String param) {
        this.param = param;
    }

    @Override
    public String call() throws Exception {
        // 这里处理业务逻辑
        StringBuffer sb = new StringBuffer();
        for (int i =0; i< 10; i++) {
            sb.append(param);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {}
        }
        return sb.toString();
    }
}
