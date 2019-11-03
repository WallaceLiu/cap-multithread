package bdata.cap.com.ch3.case02;///*
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

@WebFilter("/echo")
public class CountingFilter implements Filter {
  final Indicator indicator = Indicator.getInstance();

  public CountingFilter() {
    // 什么也不做
  }

  @Override
  public void destroy() {
    // 什么也不做
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response,
      FilterChain chain) throws IOException, ServletException {
    indicator.newRequestReceived();
    StatusExposingResponse httpResponse = new StatusExposingResponse(
        (HttpServletResponse) response);

    chain.doFilter(request, httpResponse);

    int statusCode = httpResponse.getStatus();
    if (0 == statusCode || 2 == statusCode / 100) {
      indicator.newRequestProcessed();
    } else {
      indicator.requestProcessedFailed();
    }
  }

  public class StatusExposingResponse extends HttpServletResponseWrapper {
    private int httpStatus;

    public StatusExposingResponse(HttpServletResponse response) {
      super(response);
    }

    @Override
    public void sendError(int sc) throws IOException {
      httpStatus = sc;
      super.sendError(sc);
    }

    @Override
    public void sendError(int sc, String msg) throws IOException {
      httpStatus = sc;
      super.sendError(sc, msg);
    }

    @Override
    public void setStatus(int sc) {
      httpStatus = sc;
      super.setStatus(sc);
    }

    @Override
    public int getStatus() {
      return httpStatus;
    }
  }

  @Override
  public void init(FilterConfig fConfig) throws ServletException {
    // 什么也不做
  }

}
