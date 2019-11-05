package bdata.cap.com.ch6;


import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/threadLocalExample")
public class ServletWithThreadLocal extends HttpServlet {
    private static final long serialVersionUID = -9179908895742969397L;
    final static ThreadLocal<SimpleDateFormat> SDF = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        final SimpleDateFormat sdf = SDF.get();
        String strExpiryDate = req.getParameter("expirtyDate");
        try (PrintWriter pwr = resp.getWriter()) {
            sdf.parse(strExpiryDate);
            // 省略其他代码
            pwr.printf("[%s]expirtyDate:%s", Thread.currentThread().getName(), strExpiryDate);
        } catch (ParseException e) {
            throw new ServletException(e);
        } // try结束
    }
}
