package com.anchorcms.cms.controller.admin.main;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.anchorcms.cms.model.main.Channel;
import com.anchorcms.cms.service.main.ChannelMng;
import com.anchorcms.cms.service.main.CmsWebserviceMng;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.core.security.CmsAuthorizingRealm;
import com.anchorcms.core.service.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;

import com.anchorcms.common.web.RequestUtils;
import com.anchorcms.common.web.ResponseUtils;

public class CmsAdminAbstract {
	protected String channelsAddJson(Integer siteId,
			HttpServletRequest request, HttpServletResponse response,
			ModelMap model) {
		List<Channel> channelList = channelMng.getTopList(siteId, false);
		model.addAttribute("channelList", channelList);
		response.setHeader("Cache-Control", "no-cache");
		response.setContentType("text/json;charset=UTF-8");
		return "admin/channels_add";
	}

	protected String channelsEditJson(Integer userId, Integer siteId,
			HttpServletRequest request, HttpServletResponse response,
			ModelMap model) {
		List<Channel> channelList = channelMng.getTopList(siteId, false);
		CmsUser user = manager.findById(userId);
		model.addAttribute("channelList", channelList);
		model.addAttribute("channelIds", user.getChannelIds(siteId));
		response.setHeader("Cache-Control", "no-cache");
		response.setContentType("text/json;charset=UTF-8");
		return "admin/channels_edit";
	}
	

	protected void checkUserJson(HttpServletRequest request,HttpServletResponse response) {
		String username=RequestUtils.getQueryParam(request,"username");
		String pass;
		if (StringUtils.isBlank(username)) {
			pass = "false";
		} else {
			pass = manager.usernameNotExist(username) ? "true" : "false";
		}
		ResponseUtils.renderJson(response, pass);
	}

	protected void checkEmailJson(String email, HttpServletResponse response) {
		String pass;
		if (StringUtils.isBlank(email)) {
			pass = "false";
		} else {
			pass = manager.emailNotExist(email) ? "true" : "false";
		}
		ResponseUtils.renderJson(response, pass);
	}

	@Resource
	protected CmsSiteMng cmsSiteMng;
	@Resource
	protected ChannelMng channelMng;
	@Resource
	protected CmsRoleMng cmsRoleMng;
	@Resource
	protected CmsGroupMng cmsGroupMng;
	@Resource
	protected CmsLogMng cmsLogMng;
	@Resource
	protected CmsUserMng manager;
	@Resource
	protected CmsWebserviceMng cmsWebserviceMng;
	@Resource
	protected CmsAuthorizingRealm authorizingRealm;
}
