package com.anchorcms.common.web.xss;

import com.anchorcms.common.utils.StrUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * @Author 阁楼麻雀
 * @Email netuser.orz@icloud.com
 * @Date 2016-10-31
 * @Desc 解决跨站问题
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

    public XssHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
    }
    public String getQueryString() {
        String value = super.getQueryString();
        if (value != null) {
            value = StrUtil.xssEncode(value);
        }
        return value;
    }

    /**
     * 覆盖getParameter方法，将参数名和参数值都做xss过滤。<br/>
     * 如果需要获得原始的值，则通过super.getParameterValues(name)来获取<br/>
     * getParameterNames,getParameterValues和getParameterMap也可能需要覆盖
     */
    public String getParameter(String name) {
        String value = super.getParameter(StrUtil.xssEncode(name));
        if (value != null) {
            value = StrUtil.xssEncode(value);
        }
        return value;
    }

    public String[] getParameterValues(String name) {
        String[]parameters=super.getParameterValues(name);
        if (parameters==null||parameters.length == 0) {
            return null;
        }
        for (int i = 0; i < parameters.length; i++) {
            parameters[i] = StrUtil.xssEncode(parameters[i]);
        }
        return parameters;
    }

    /**
     * 覆盖getHeader方法，将参数名和参数值都做xss过滤。<br/>
     * 如果需要获得原始的值，则通过super.getHeaders(name)来获取<br/> getHeaderNames 也可能需要覆盖
     */
    public String getHeader(String name) {

        String value = super.getHeader(StrUtil.xssEncode(name));
        if (value != null) {
            value = StrUtil.xssEncode(value);
        }
        return value;
    }
}
