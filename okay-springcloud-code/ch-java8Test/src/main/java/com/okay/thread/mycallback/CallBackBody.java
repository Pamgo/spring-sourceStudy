package com.okay.thread.mycallback;

/**
 * Created by OKali on 2019/4/23.
 */
public abstract class CallBackBody {

    void onSuccess(Object context) {
        System.out.println("onSuccess执行");
    }

    void onFailure(Object context) {
        System.out.println("onFailure执行");
    }

    abstract void execute(Object context) throws Exception;
}
