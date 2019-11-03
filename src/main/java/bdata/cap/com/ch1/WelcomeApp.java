package bdata.cap.com.ch1;

public class WelcomeApp {

    public static void main(String[] args) throws InterruptedException {
        // 创建线程三种方式
        Thread welcomeThread = new WelcomeThread();
        Thread welcomeThread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.printf("3.Welcome! I'm %s.%n", Thread.currentThread()
                        .getName());
            }
        });
        Thread welcomeThread3 = new Thread() {
            @Override
            public void run() {
                System.out.printf("5.Welcome! I'm %s.%n", Thread.currentThread()
                        .getName());
            }
        };


        Thread welcomeTask = new Thread(new WelcomeTask());

        // 启动线程
        welcomeThread.start();
        welcomeThread2.start();
        welcomeThread3.start();
        welcomeTask.start();

        // 等待线程结束
//        welcomeThread.join();
//        welcomeThread2.join();
//        welcomeThread3.join();
//        welcomeTask.join();

        // 输出“当前线程”的线程名称
        System.out.printf("1.Welcome! I'm %s.%n", Thread.currentThread().getName());
    }
}

class WelcomeThread extends Thread {
    @Override
    public void run() {
        // 输出“当前线程”的线程名称
        System.out.printf("2.Welcome! I'm %s.%n", Thread.currentThread().getName());
    }
}

class WelcomeTask implements Runnable {
    @Override
    public void run() {
        // 输出“当前线程”的线程名称
        System.out.printf("4.Welcome! I'm %s.%n", Thread.currentThread().getName());
    }
}