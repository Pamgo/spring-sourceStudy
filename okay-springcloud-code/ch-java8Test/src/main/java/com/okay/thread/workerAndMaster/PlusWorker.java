package com.okay.thread.workerAndMaster;

/**
 * Created by OKali on 2019/4/5.
 */
public class PlusWorker extends Worker {

    @Override
    public Object handleObject(Object input) {
        Integer i = (Integer) input;
        System.out.println(""+i+"*"+i+"*"+i);
        return i*i*i;
    }
}
