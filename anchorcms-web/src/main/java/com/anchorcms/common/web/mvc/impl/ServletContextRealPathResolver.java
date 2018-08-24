package com.anchorcms.common.web.mvc.impl;

import javax.servlet.ServletContext;

import com.anchorcms.common.web.mvc.RealPathResolver;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

@Component("realPathResolver")
public class ServletContextRealPathResolver implements RealPathResolver,
		ServletContextAware {
	public String get(String path) {
		String realpath=context.getRealPath(path);
		//tomcat8.0获取不到真实路径，通过/获取路径
		if(realpath==null){
			realpath=context.getRealPath("/")+path;
		}
		return realpath;
	}

	public void setServletContext(ServletContext servletContext) {
		this.context = servletContext;
	}

	private ServletContext context;
}
