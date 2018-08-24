package com.anchorcms.core.security;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.anchorcms.common.web.CookieUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.LogoutFilter;



/**
 * CmsUserFilter
 */
public class CmsLogoutFilter extends LogoutFilter {
	/**
	 * 返回URL
	 */
	public static final String RETURN_URL = "returnUrl";
	
	public static final String USER_LOG_OUT_FLAG = "logout";

	protected String getRedirectUrl(ServletRequest req, ServletResponse resp,Subject subject) {
		String cyrh = req.getParameter("cyrh");
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response=(HttpServletResponse) resp;
		String redirectUrl = request.getParameter(RETURN_URL);
		String domain = request.getServerName();
		if (domain.indexOf(".") > -1) {
			domain= domain.substring(domain.indexOf(".") + 1);
		}
		CookieUtils.addCookie(request, response,  "JSESSIONID",  null,0,domain,"/");
		CookieUtils.addCookie(request, response,  "sso_logout",  "true",null,domain,"/");
		if (StringUtils.isBlank(redirectUrl)) {
			if (request.getRequestURI().startsWith(request.getContextPath() + getAdminPrefix())) {
				redirectUrl = getAdminLogin();
			} else {
				if(cyrh==null || cyrh.length()==0) {
					redirectUrl = "/";
				} else {
					redirectUrl = "/bigdata_page.jspx";
				}
//				redirectUrl = getRedirectUrl();
			}
		}
		return redirectUrl;
	}
	private String adminPrefix;
	private String adminLogin;
	public String getAdminPrefix() {
		return adminPrefix;
	}

	public void setAdminPrefix(String adminPrefix) {
		this.adminPrefix = adminPrefix;
	}

	public String getAdminLogin() {
		return adminLogin;
	}

	public void setAdminLogin(String adminLogin) {
		this.adminLogin = adminLogin;
	}

	
}
