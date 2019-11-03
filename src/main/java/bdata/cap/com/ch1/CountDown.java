package bdata.cap.com.ch1;

/**
 * 倒计时
 */
public class CountDown {
    private static int count;

    public static void main(String[] args) {
        count = 60;//args.length >= 1 ? Integer.valueOf(args[0]) : 60;

        int remaining;

        while (true) {
            remaining = countDown();
            if (0 == remaining) {
                break;
            } else {
                System.out.println("Remaining " + count + " second(s)");
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // 什么也不做;
            }
        }
        System.out.println("Done.");
    }

    private static int countDown() {
        return count--;
    }
}
