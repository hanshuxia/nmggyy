package com.anchorcms.common.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static com.anchorcms.common.web.ProcessTimeFilter.START_TIME;

/**
 * @Author 阁楼麻雀
 * @Email netuser.orz@icloud.com
 * @Date 2016-10-26
 * @Desc 统计执行时间
 */
public class ExecuteTimeFilter implements Filter {
    protected final Logger log = LoggerFactory.getLogger(ExecuteTimeFilter.class);

    public void init(FilterConfig filterConfig) throws ServletException {

    }
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        long time = System.currentTimeMillis();
        request.setAttribute(START_TIME, time);
        filterChain.doFilter(request, servletResponse);
        time = System.currentTimeMillis() - time;
        log.debug("process in {} ms: {}", time, request.getRequestURI());
    }

    public void destroy() {

    }
}
