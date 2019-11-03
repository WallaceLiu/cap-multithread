package bdata.cap.com.ch2;

import bdata.cap.com.util.Tools;

public class RaceConditionDemo {

    public static void main(String[] args) {
        // 客户端线程数
        int numberOfThreads = Runtime.getRuntime().availableProcessors();
        Thread[] workerThreads = new Thread[numberOfThreads];

        for (int i = 0; i < numberOfThreads; i++) {
            workerThreads[i] = new WorkerThread(i, 10);
        }

        for (Thread ct : workerThreads) {
            ct.start();
        }
    }

    /**
     * 模拟业务线程
     */
    static class WorkerThread extends Thread {
        private final int requestCount;

        /**
         * @param id
         * @param requestCount
         */
        public WorkerThread(int id, int requestCount) {
            super("worker-" + id);
            this.requestCount = requestCount;
        }

        @Override
        public void run() {
            int i = requestCount;
            String requestID;
            RequestIDGenerator requestIDGen = RequestIDGenerator.getInstance();

            while (i-- > 0) {
                // 生成Request ID
                requestID = requestIDGen.nextID();
                processRequest(requestID);
            }
        }

        // 模拟请求处理
        private void processRequest(String requestID) {
            // 模拟请求处理耗时
            Tools.randomPause(50);
            System.out.printf("%s got requestID: %s %n", Thread.currentThread().getName(), requestID);
        }
    }
}