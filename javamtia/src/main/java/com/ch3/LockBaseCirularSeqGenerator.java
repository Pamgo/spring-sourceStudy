package com.ch3;

import com.imp.CirularSeqGenerator;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by OKali on 2019/1/13.
 * 使用显式锁实现循环递增序号生成
 */
public class LockBaseCirularSeqGenerator implements CirularSeqGenerator {
    private short sequence = -1;
    private final Lock lock = new ReentrantLock();

    @Override
    public short nextSequence() {
        lock.lock();

        try {
            if (sequence >= 999) {
                sequence = 0;
            } else {
                sequence++;
            }
            return sequence;
        } finally {
            lock.unlock();
        }

    }
}
