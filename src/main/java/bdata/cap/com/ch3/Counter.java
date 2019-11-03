package bdata.cap.com.ch3;

import bdata.cap.com.util.annotations.SuppressFBWarnings;

public class Counter {
    private volatile long count;

    public long vaule() {
        return count;
    }

    @SuppressFBWarnings(
            value = "VO_VOLATILE_INCREMENT",
            justification = "It is done inside critical section")
    public void increment() {
        synchronized (this) {
            count++;
        }
    }
}
