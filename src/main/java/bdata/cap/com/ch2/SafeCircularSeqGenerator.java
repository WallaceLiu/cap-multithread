package bdata.cap.com.ch2;

public class SafeCircularSeqGenerator implements CircularSeqGenerator {
    private short sequence = -1;

    /**
     * 注意 synchronized 关键字
     *
     * @return
     */
    @Override
    public synchronized short nextSequence() {
        if (sequence >= 999) {
            sequence = 0;
        } else {
            sequence++;
        }
        return sequence;
    }
}