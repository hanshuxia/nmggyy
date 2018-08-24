package com.anchorcms.cms.web;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.anchorcms.common.utils.CheckMobile;
import com.anchorcms.common.utils.CmsUtils;
import com.anchorcms.common.web.CookieUtils;
import com.anchorcms.common.web.session.SessionProvider;
import com.anchorcms.core.model.CmsConfig;
import com.anchorcms.core.model.CmsSite;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.core.service.CmsConfigMng;
import com.anchorcms.core.service.CmsSiteMng;
import com.anchorcms.core.service.CmsUserMng;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


/**
 * CMS上下文信息拦截器
 * 
 * 包括登录信息、权限信息、站点信息
 */
public class FrontContextInterceptor extends HandlerInterceptorAdapter {
	public static final String SITE_COOKIE = "_site_id_cookie";
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler)
			throws ServletException {
		CmsConfig config=configMng.get();
		CmsSite site = null;
		List<CmsSite> list = cmsSiteMng.getListFromCache();
		int size = list.size();
		if (size == 0) {
			throw new RuntimeException("no site record in database!");
		} else if (size == 1) {
			site = list.get(0);
		} else {
			String server = request.getServerName();
			String alias, redirect;
			for (CmsSite s : list) {
				// 检查域名
				if (s.getDomain().equals(server)) {
					site = s;
					break;
				}
				// 检查域名别名
				alias = s.getDomainAlias();
				if (!StringUtils.isBlank(alias)) {
					for (String a : StringUtils.split(alias, ',')) {
						if (a.equals(server)) {
							site = s;
							break;
						}
					}
				}
				// 检查重定向
				redirect = s.getDomainRedirect();
				if (!StringUtils.isBlank(redirect)) {
					for (String r : StringUtils.split(redirect, ',')) {
						if (r.equals(server)) {
							try {
								response.sendRedirect(s.getAdminUrl());
							} catch (IOException e) {
								throw new RuntimeException(e);
							}
							return false;
						}
					}
				}
			}
			if (site == null) {
				throw new SiteNotFoundException(server);
			}
		}
		if(site!=null){
			CookieUtils.addCookie(request, response, SITE_COOKIE, site.getSiteId().toString(), null, null);
		}
		CmsUtils.setSite(request, site);
		CmsThreadVariable.setSite(site);
		Subject subject = SecurityUtils.getSubject();
		CmsUser user =null;
		if (subject.isAuthenticated()|| subject.isRemembered()) {
			String username =  (String) subject.getPrincipal();
			user= cmsUserMng.findByUsername(username);
			CmsUtils.setUser(request, user);
			// Site加入线程变量
			CmsThreadVariable.setUser(user);
		}
		checkEquipment(request, response);
		return true;
	}
	
	/**
	 * 检查访问方式
	 */
	public void checkEquipment(HttpServletRequest request,HttpServletResponse response){
		String ua=(String) session.getAttribute(request,"ua");
		if(null==ua){
			try{
				String userAgent = request.getHeader( "USER-AGENT" ).toLowerCase();
				if(null == userAgent){  
				    userAgent = "";  
				}
				if(CheckMobile.check(userAgent)){
					ua="mobile";
				} else {
					ua="pc";
				}
				session.setAttribute(request, response, "ua",ua);
			}catch(Exception e){}
		}
		if(StringUtils.isNotBlank((ua) )){
			request.setAttribute("ua", ua);
		}
	}
	
	private CmsSite getByCookie(HttpServletRequest request) {
		Cookie cookie = CookieUtils.getCookie(request, SITE_COOKIE);
		if (cookie != null) {
			String v = cookie.getValue();
			if (!StringUtils.isBlank(v)) {
				try {
					Integer siteId = Integer.parseInt(v);
					return cmsSiteMng.findById(siteId);
				} catch (NumberFormatException e) {
				}
			}
		}
		return null;
	}

	@Resource
	private CmsSiteMng cmsSiteMng;
	@Resource
	private CmsUserMng cmsUserMng;
	@Resource
	private CmsConfigMng configMng;
	@Resource
	private SessionProvider session;
}