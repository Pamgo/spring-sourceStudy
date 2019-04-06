package com.okay.thread.workerAndMaster;

/**
 * Created by OKali on 2019/4/5.
 */
public class PlusWorker extends Worker {

    @Override
    public Object handleObject(Object input) {
        Integer i = (Integer) input;
        return i*i*i;
    }
}
