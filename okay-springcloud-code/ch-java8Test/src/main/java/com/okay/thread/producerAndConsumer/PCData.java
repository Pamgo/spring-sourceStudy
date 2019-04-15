package com.okay.thread.producerAndConsumer;

/**
 * Created by OKali on 2019/4/15.
 */
public final class PCData {

    private final int intData;

    public PCData(int d) {
        intData = d;
    }

    public int getIntData() {
        return intData;
    }

    @Override
    public String toString() {
        return "data:" + intData;
    }
}
