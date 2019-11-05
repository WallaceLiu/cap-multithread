package bdata.cap.com.ch6;


import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 该类可能导致内存泄漏！
 *
 * @author Viscent Huang
 */
@WebServlet("/memoryLeak")
public class ThreadLocalMemoryLeak extends HttpServlet {
    private static final long serialVersionUID = 4364376277297114653L;
    final static ThreadLocal<Counter> counterHolder = new ThreadLocal<Counter>() {
        @Override
        protected Counter initialValue() {
            Counter tsoCounter = new Counter();
            return tsoCounter;
        }
    };

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doProcess(req, resp);
        try (PrintWriter pwr = resp.getWriter()) {
            pwr.printf("Thread %s,counter:%d",
                    Thread.currentThread().getName(),
                    counterHolder.get().getAndIncrement());
        }
    }

    private void doProcess(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
            IOException {
        counterHolder.get().getAndIncrement();
        // 省略其他代码
    }
}

// 非线程安全
class Counter {
    private int i = 0;

    public int getAndIncrement() {
        return i++;
    }
}