package bdata.cap.com.ch6;


import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

@WebFilter("/memoryLeak")
public class ThreadLocalCleanupFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        chain.doFilter(request, response);
        ThreadLocalMemoryLeak.counterHolder.remove();
    }

    @Override
    public void init(FilterConfig fConfig) throws ServletException {
        // 什么也不做
    }

    @Override
    public void destroy() {
        // 什么也不做
    }
}
