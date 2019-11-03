package bdata.cap.com.ch3;

import bdata.cap.com.util.Debug;
import bdata.cap.com.util.Tools;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;

public class CASBasedCounter {
    private volatile long count;
    /**
     * 这里使用AtomicLongFieldUpdater只是为了便于讲解和运行该实例，
     * 实际上更多的情况是我们不使用AtomicLongFieldUpdater，而是使用
     * java.util.concurrent.atomic包下的其他更为直接的类。
     */
    private final AtomicLongFieldUpdater<CASBasedCounter> fieldUpdater;

    public CASBasedCounter() throws SecurityException, NoSuchFieldException {
        fieldUpdater = AtomicLongFieldUpdater.newUpdater(CASBasedCounter.class,
                "count");
    }

    public long vaule() {
        return count;
    }

    public void increment() {
        long oldValue;
        long newValue;
        do {
            oldValue = count;// 读取共享变量当前值
            newValue = oldValue + 1;// 计算共享变量的新值
        } while (/* 调用CAS来更新共享变量的值 */!compareAndSwap(oldValue, newValue));
    }

    /*
     * 该方法是个实例方法，且共享变量count是当前类的实例变量，因此这里我们没有必要在方法参数中声明一个表示共享变量的参数
     */
    private boolean compareAndSwap(long oldValue, long newValue) {
        boolean isOK = fieldUpdater.compareAndSet(this, oldValue, newValue);
        return isOK;
    }

    public static void main(String[] args) throws Exception {
        final CASBasedCounter counter = new CASBasedCounter();
        Thread t;
        Set<Thread> threads = new HashSet<Thread>();
        for (int i = 0; i < 20; i++) {
            t = new Thread(new Runnable() {
                @Override
                public void run() {
                    Tools.randomPause(50);
                    counter.increment();
                }
            });
            threads.add(t);
        }

        for (int i = 0; i < 8; i++) {
            t = new Thread(new Runnable() {
                @Override
                public void run() {
                    Tools.randomPause(50);
                    Debug.info(String.valueOf(counter.vaule()));
                }
            });
            threads.add(t);
        }

        // 启动并等待指定的线程结束
        Tools.startAndWaitTerminated(threads);
        Debug.info("final count:" + String.valueOf(counter.vaule()));
    }
}