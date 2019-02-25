package com.ch3;

import com.util.Tools;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;

/**
 * Created by OKali on 2019/1/1.
 */
public class CASBaseCounter {

    private volatile long count;

    /**
     * 这里使用AtomicLongFieldUpdater只是为了方便j简介和运行该实例，
     * 实际上更多的情况是我们不使用AtomicLongFieldUpdater, 而使用java.util.concurrent.atomic包下的q其他
     * 更为直接的类
     */
    private AtomicLongFieldUpdater<CASBaseCounter> fieldUpdater ;

    public CASBaseCounter() {
        fieldUpdater = AtomicLongFieldUpdater.newUpdater(CASBaseCounter.class, "count");
    }

    public  long value() {
        return count;
    }

    public void increment() {
        long oldValue;
        long newValue;
        do {
            oldValue = count;
            newValue = oldValue + 1;
        } while (!compareAndSwap(oldValue, newValue));
    }

    /**
     * 该方法s是个实例方法，且共享变量count是当前类的实例变量，因此我们这里没有必要在方法参数声明一个表示
     * 共享变量参数
     * @param oldValue 共享变量当前值
     * @param newValue 共享变量新值
     * @return
     */
    private boolean compareAndSwap(long oldValue, long newValue) {
        boolean isOk = fieldUpdater.compareAndSet(this, oldValue, newValue);
        return isOk;
    }

    public static void main(String[] args) throws InterruptedException {
        final CASBaseCounter counter = new CASBaseCounter();
        Thread t;

        Set<Thread> threads = new HashSet<>();
        for (int i = 0; i < 20; i++) {
            t = new Thread(new Runnable() {
                @Override
                public void run() {
                    Tools.randomPause(50);
                    counter.increment();
                }
            });
            threads.add(t);
        }  // for end

        for (int i = 0; i < 8; i++) {
            t = new Thread(new Runnable() {
                @Override
                public void run() {
                    Tools.randomPause(50);
                    System.out.println(Thread.currentThread().getName() + "值：" + counter.value());
                }
            });  // thread end
            threads.add(t);
        } // for end

        Tools.startAndWaitTerminated(threads);
        System.out.println(Thread.currentThread().getName()+"值："+counter.value());
    } // main end
}
