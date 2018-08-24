package com.anchorcms.common.web.xss;

import com.anchorcms.common.utils.URLHelper;
import org.apache.commons.lang.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @Author 阁楼麻雀
 * @Email netuser.orz@icloud.com
 * @Date 2016-10-31
 * @Desc 解决跨站问题
 */
public class XssFilter implements Filter {
    private String excludeUrls;
    FilterConfig filterConfig = null;
    public void init(FilterConfig filterConfig) throws ServletException {
        this.excludeUrls=filterConfig.getInitParameter("excludeUrls");
        this.filterConfig = filterConfig;
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if(isExcludeUrl(servletRequest)){
            filterChain.doFilter(servletRequest, servletResponse);
        }else{
            filterChain.doFilter(new XssHttpServletRequestWrapper((HttpServletRequest) servletRequest), servletResponse);
        }

    }
    private boolean isExcludeUrl(ServletRequest request){
        boolean exclude=false;
        if(StringUtils.isNotBlank(excludeUrls)){
            String[]excludeUrl=excludeUrls.split("@");
            if(excludeUrl!=null&&excludeUrl.length>0){
                for(String url:excludeUrl){
                    if(URLHelper.getURI((HttpServletRequest)request).startsWith(url)){
                        exclude=true;
                    }
                }
            }
        }
        return exclude;
    }

    public void destroy() {
        this.filterConfig = null;
    }
}
