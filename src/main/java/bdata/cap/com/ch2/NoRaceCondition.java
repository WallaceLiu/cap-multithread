package bdata.cap.com.ch2;

/**
 * 非竞态
 *
 * @author: liuning11
 * @date: 2019-11-06
 */
public class NoRaceCondition {

    /**
     * @param sequence
     * @return
     */
    public int nextSequence(int sequence) {

        // 以下语句使用的是局部变量，而非状态变量，并不会产生竞态
        if (sequence >= 999) {
            sequence = 0;
        } else {
            sequence++;
        }
        return sequence;
    }
}
