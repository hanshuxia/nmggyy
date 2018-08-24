package com.anchorcms.cms.controller.member;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.anchorcms.cms.model.assist.CmsWebservice;
import com.anchorcms.cms.model.main.Channel;
import com.anchorcms.cms.service.main.ChannelMng;
import com.anchorcms.cms.service.main.CmsWebserviceMng;
import com.anchorcms.common.email.EmailSender;
import com.anchorcms.common.email.MessageTemplate;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.common.utils.CmsUtils;
import com.anchorcms.common.utils.FrontUtils;
import com.anchorcms.common.web.RequestUtils;
import com.anchorcms.common.web.ResponseUtils;
import com.anchorcms.common.web.mvc.MessageResolver;
import com.anchorcms.common.web.session.SessionProvider;
import com.anchorcms.core.model.*;
import com.anchorcms.core.service.CmsConfigItemMng;
import com.anchorcms.core.service.CmsUserMng;
import com.anchorcms.core.service.ConfigMng;
import com.anchorcms.core.service.UnifiedUserMng;
import com.anchorcms.core.web.WebErrors;
import com.anchorcms.icloud.service.synergy.RegisterListService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.octo.captcha.service.CaptchaServiceException;
import com.octo.captcha.service.image.ImageCaptchaService;

import static com.anchorcms.common.constants.Constants.TPLDIR_MEMBER;
import static com.anchorcms.common.page.SimplePage.cpn;

/**
 * 前台会员注册Action
 */
@Controller
public class RegisterController {
	private static final Logger log = LoggerFactory
			.getLogger(RegisterController.class);

	public static final String REGISTER_ADD = "tpl.registeradd";
	public static final String REGISTER = "tpl.register";
	public static final String REGISTER_RESULT = "tpl.registerResult";
	public static final String REGISTER_ACTIVE_SUCCESS = "tpl.registerActiveSuccess";
	public static final String LOGIN_INPUT = "tpl.loginInput";
	public static final String REGISTER_LIST = "tpl.registerList";
	public static final String REGISTER_SUCCESS = "tpl.registerSuccess";

	@RequestMapping(value = "/register.jspx", method = RequestMethod.GET)
	public String input(HttpServletRequest request,
			HttpServletResponse response, ModelMap model,String cyrh) {
		CmsSite site = CmsUtils.getSite(request);
		MemberConfig mcfg = site.getConfig().getMemberConfig();
		// 没有开启会员功能
		if (!mcfg.isMemberOn()) {
			return FrontUtils.showMessage(request, model, "member.memberClose");
		}
		// 没有开启会员注册
		if (!mcfg.isRegisterOn()) {
			return FrontUtils.showMessage(request, model,
					"member.registerClose");
		}
		List<CmsConfigItem>items=cmsConfigItemMng.getList(site.getConfig().getConfigId(), CmsConfigItem.CATEGORY_REGISTER);
		FrontUtils.frontData(request, model, site);
		model.addAttribute("mcfg", mcfg);
		model.addAttribute("items", items);
		model.addAttribute("cyrh", cyrh);
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TPLDIR_MEMBER, REGISTER);
	}

	/**
	 * @auther lilimin
	 * @descript 子账号创建
	 * @data 2017/1/20
	 * @param request
	 * @param response
	 * @param model
     * @return
     */
	@RequestMapping(value = "/register_add.jspx", method = RequestMethod.GET)
	public String add_input(HttpServletRequest request,
						HttpServletResponse response, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		MemberConfig mcfg = site.getConfig().getMemberConfig();
		// 没有开启会员功能
		if (!mcfg.isMemberOn()) {
			return FrontUtils.showMessage(request, model, "member.memberClose");
		}
		// 没有开启会员注册
		if (!mcfg.isRegisterOn()) {
			return FrontUtils.showMessage(request, model,
					"member.registerClose");
		}
		List<CmsConfigItem>items=cmsConfigItemMng.getList(site.getConfig().getConfigId(), CmsConfigItem.CATEGORY_REGISTER);
		FrontUtils.frontData(request, model, site);
		model.addAttribute("mcfg", mcfg);
		model.addAttribute("items", items);
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TPLDIR_MEMBER, REGISTER_ADD);
	}


	@RequestMapping(value = "/register.jspx", method = RequestMethod.POST)
	public String submit(String username, String email, String loginPassword,
						 CmsUserExt userExt, String captcha, String nextUrl,
						 HttpServletRequest request, HttpServletResponse response,
						 ModelMap model,String cyrh) throws IOException {
		CmsSite site = CmsUtils.getSite(request);
		CmsConfig config = site.getConfig();
		cyrh =request.getParameter("cyrh");
		model.addAttribute("cyrh",cyrh);
		WebErrors errors = validateSubmit(username, email, loginPassword, captcha,
				site, request, response);
		boolean disabled=false;
		if(config.getMemberConfig().isCheckOn()){
			disabled=true;
		}
		if (errors.hasErrors()) {
			return FrontUtils.showError(request, response, model, errors);
		}
		String ip = RequestUtils.getIpAddr(request);
		Map<String,String>attrs=RequestUtils.getRequestMap(request, "attr_");
//		if (config.getEmailValidate()) {
//			EmailSender sender = configMng.getEmailSender();
//			MessageTemplate msgTpl = configMng.getRegisterMessageTemplate();
//			if (sender == null) {
//				// 邮件服务器没有设置好
//				model.addAttribute("status", 4);
//			} else if (msgTpl == null) {
//				// 邮件模板没有设置好
//				model.addAttribute("status", 5);
//			} else {
//				try {
//					cmsUserMng.registerMember(username, email, loginPassword, ip,
//							null,disabled,userExt,attrs, false, sender, msgTpl);
//					cmsWebserviceMng.callWebService("false",username, loginPassword, email, userExt, CmsWebservice.SERVICE_TYPE_ADD_USER);
//					model.addAttribute("status", 0);
//				} catch (UnsupportedEncodingException e) {
//					// 发送邮件异常
//					model.addAttribute("status", 100);
//					model.addAttribute("message", e.getMessage());
//					log.error("send email exception.", e);
//				} catch (MessagingException e) {
//					// 发送邮件异常
//					model.addAttribute("status", 101);
//					model.addAttribute("message", e.getMessage());
//					log.error("send email exception.", e);
//				}
//			}
//			log.info("member register success. username={}", username);
//			FrontUtils.frontData(request, model, site);
//			if (!StringUtils.isBlank(nextUrl)) {
//				response.sendRedirect(nextUrl);
//				return null;
//			} else {
//				return FrontUtils.getTplPath(request, site.getSolutionPath(),
//						TPLDIR_MEMBER, REGISTER_RESULT);
//			}
//		} else {
		cmsUserMng.registerMember(username, email, loginPassword, ip, null, null, disabled, userExt, attrs);
		cmsWebserviceMng.callWebService("false", username, loginPassword, email, userExt, CmsWebservice.SERVICE_TYPE_ADD_USER);
		log.info("member register success. username={}", username);
		model.addAttribute("cyrh", cyrh);
		FrontUtils.frontData(request, model, site);
		FrontUtils.frontPageData(request, model);
		model.addAttribute("success", true);
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TPLDIR_MEMBER, REGISTER_SUCCESS);
//		}
	}

	/**
	 * @auther lilimin
	 * @deprecated 子账户创建
	 * @data 2017/1/20
	 * @param username
	 * @param email
	 * @param loginPassword
	 * @param userExt
	 * @param captcha
	 * @param nextUrl
	 * @param request
	 * @param response
	 * @param model
     * @return
     * @throws IOException
     */
	@RequestMapping(value = "/admin_register.jspx", method = RequestMethod.POST)
	public String admin_submit(String username, String email, String loginPassword,
						 CmsUserExt userExt, String nextUrl,
						 HttpServletRequest request, HttpServletResponse response,
						 ModelMap model) throws IOException {
		CmsUser user = CmsUtils.getUser(request);
		CmsSite site = CmsUtils.getSite(request);
		CmsConfig config = site.getConfig();
		WebErrors errors = validateSubmit(username, email, loginPassword, null,
				site, request, response);
		boolean disabled=true;
		if(config.getMemberConfig().isCheckOn()){
			disabled=false;
		}
		if (errors.hasErrors()) {
			return FrontUtils.showError(request, response, model, errors);
		}
		String ip = RequestUtils.getIpAddr(request);
		Map<String,String>attrs=RequestUtils.getRequestMap(request, "attr_");
//		if (config.getEmailValidate()) {
//			EmailSender sender = configMng.getEmailSender();
//			MessageTemplate msgTpl = configMng.getRegisterMessageTemplate();
//			if (sender == null) {
//				// 邮件服务器没有设置好
//				model.addAttribute("status", 4);
//			} else if (msgTpl == null) {
//				// 邮件模板没有设置好
//				model.addAttribute("status", 5);
//			} else {
//				try {
//					cmsUserMng.registerAdminMember(user,username, email, loginPassword, ip,
//							null,disabled,userExt,attrs, false, sender, msgTpl);
//					cmsWebserviceMng.callWebService("false",username, loginPassword, email, userExt, CmsWebservice.SERVICE_TYPE_ADD_USER);
//					model.addAttribute("status", 0);
//				} catch (Exception e) {
//					// 发送邮件异常
//					model.addAttribute("status", 101);
//					model.addAttribute("message", e.getMessage());
//
//					log.error("send email exception.", e);
//				}
//			}
//			log.info("member register success. username={}", username);
//			FrontUtils.frontData(request, model, site);
//			if (!StringUtils.isBlank(nextUrl)) {
//				response.sendRedirect(nextUrl);
//				return null;
//			} else {
//				return FrontUtils.getTplPath(request, site.getSolutionPath(),
//						TPLDIR_MEMBER, REGISTER_RESULT);
//			}
//		} else {
			//把当前登录人传到后台处理获取当前登录人的companyId
			cmsUserMng.registerAdminMember(user,username, email, loginPassword, ip, null,null,disabled,userExt,attrs);
			cmsWebserviceMng.callWebService("false",username, loginPassword, email, userExt,CmsWebservice.SERVICE_TYPE_ADD_USER);
			log.info("member register success. username={}", username);
			FrontUtils.frontData(request, model, site);
			FrontUtils.frontPageData(request, model);
			model.addAttribute("success", true);
			model.addAttribute("result","创建成功！");
			nextUrl="/member/register_list.jspx";
			return FrontUtils.showSuccess(request, model, nextUrl);
//		}
	}

	@RequestMapping(value = "/active.jspx", method = RequestMethod.GET)
	public String active(String username, String key,
			HttpServletRequest request, HttpServletResponse response,
			ModelMap model) throws IOException {
		CmsSite site = CmsUtils.getSite(request);
	    username = new String(request.getParameter("username").getBytes("iso8859-1"),"GBK");
		WebErrors errors = validateActive(username, key, request, response);
		if (errors.hasErrors()) {
			return FrontUtils.showError(request, response, model, errors);
		}
		unifiedUserMng.active(username, key);
		FrontUtils.frontData(request, model, site);
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TPLDIR_MEMBER, REGISTER_ACTIVE_SUCCESS);
	}

	@RequestMapping(value = "/username_unique.jspx")
	public void usernameUnique(HttpServletRequest request,
			HttpServletResponse response) {
		String username = RequestUtils.getQueryParam(request, "username");
		// 用户名为空，返回false。
		if (StringUtils.isBlank(username)) {
			ResponseUtils.renderJson(response, "false");
			return;
		}
		CmsSite site = CmsUtils.getSite(request);
		CmsConfig config = site.getConfig();
		// 保留字检查不通过，返回false。
		if (!config.getMemberConfig().checkUsernameReserved(username)) {
			ResponseUtils.renderJson(response, "false");
			return;
		}
		// 用户名存在，返回false。
		if (unifiedUserMng.usernameExist(username)) {
			ResponseUtils.renderJson(response, "false");
			return;
		}
		ResponseUtils.renderJson(response, "true");
	}

	@RequestMapping(value = "/email_unique.jspx")
	public void emailUnique(HttpServletRequest request,
			HttpServletResponse response) {
		String email = RequestUtils.getQueryParam(request, "email");
		// email为空，返回false。
		if (StringUtils.isBlank(email)) {
			ResponseUtils.renderJson(response, "false");
			return;
		}
		// email存在，返回false。
		if (unifiedUserMng.emailExist(email)) {
			ResponseUtils.renderJson(response, "false");
			return;
		}
		ResponseUtils.renderJson(response, "true");
	}

	private WebErrors validateSubmit(String username, String email,
			String password, String captcha, CmsSite site,
			HttpServletRequest request, HttpServletResponse response) {
		MemberConfig mcfg = site.getConfig().getMemberConfig();
		WebErrors errors = WebErrors.create(request);
		if(captcha!= null){
			try {
				if (!imageCaptchaService.validateResponseForID(session
						.getSessionId(request, response), captcha)) {
					errors.addErrorCode("error.invalidCaptcha");
					return errors;
				}
			} catch (CaptchaServiceException e) {
				errors.addErrorCode("error.exceptionCaptcha");
				log.warn("", e);
				return errors;
			}
		}
		if (errors.ifOutOfLength(username, MessageResolver.getMessage(request, "field.username"),
				2, 100)) {
			return errors;
		}
		if (errors.ifNotUsername(username,MessageResolver.getMessage(request, "field.username"),
				2, 100)) {
			return errors;
		}
		if (errors.ifOutOfLength(password, MessageResolver.getMessage(request, "field.password"),
				2, 100)) {
			return errors;
		}
		if (errors.ifNotEmail(email, MessageResolver.getMessage(request, "field.email"), 100)) {
			return errors;
		}
		// 保留字检查不通过，返回false。
		if (!mcfg.checkUsernameReserved(username)) {
			errors.addErrorCode("error.usernameReserved");
			return errors;
		}
		// 用户名存在，返回false。
		if (unifiedUserMng.usernameExist(username)) {
			errors.addErrorCode("error.usernameExist");
			return errors;
		}
		return errors;
	}

	private WebErrors validateActive(String username, String activationCode,
			HttpServletRequest request, HttpServletResponse response) {
		WebErrors errors = WebErrors.create(request);
		if (StringUtils.isBlank(username)
				|| StringUtils.isBlank(activationCode)) {
			errors.addErrorCode("error.exceptionParams");
			return errors;
		}
		UnifiedUser user = unifiedUserMng.getByUsername(username);
		if (user == null) {
			errors.addErrorCode("error.usernameNotExist");
			return errors;
		}
		/*
		 * firefox访问链接二次访问，现简单不验证
		if (user.getActivation()
				|| StringUtils.isBlank(user.getActivationCode())) {
			errors.addErrorCode("error.usernameActivated");
			return errors;
		}
		if (!user.getActivationCode().equals(activationCode)) {
			errors.addErrorCode("error.exceptionActivationCode");
			return errors;
		}
		*/
		if (StringUtils.isNotBlank(user.getActivationCode())&&!user.getActivationCode().equals(activationCode)) {
			errors.addErrorCode("error.exceptionActivationCode");
			return errors;
		}
		return errors;
	}
	/**
	 * @Author ly
	 * @Date 2016/12/30 16:57
	 * @Desc 获取企业设备列表
	 **/
	@RequestMapping("/member/register_list.jspx")
	public String registerList(HttpServletRequest request, ModelMap model, Integer pageNo) {
		CmsSite site = CmsUtils.getSite(request);
		CmsUser user = CmsUtils.getUser(request);
		FrontUtils.frontData(request, model, site);
		MemberConfig mcfg = site.getConfig().getMemberConfig();
		// 没有开启会员功能
		if (!mcfg.isMemberOn()) {
			return FrontUtils.showMessage(request, model, "member.memberClose");
		}
		if (user == null) {
			return FrontUtils.showLogin(request, model, site);
		}
		// 获得本站栏目列表
		Set<Channel> rights = user.getGroup().getContriChannels();
		List<Channel> topList = channelMng.getTopList(site.getSiteId(), true);
		List<Channel> channelList = Channel.getListForSelect(topList, rights, true);
		//获取设备分页对象
		Pagination p = registerListService.getRegisterPage(site.getSiteId(), user, cpn(pageNo), 20);
		model.addAttribute("pagination", p);
		model.addAttribute("site", site);
		model.addAttribute("channelList", channelList);
		model.addAttribute("sessionId", request.getSession().getId());
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TPLDIR_MEMBER, REGISTER_LIST);
	}

	@Autowired
	private CmsWebserviceMng cmsWebserviceMng;
	@Autowired
	private UnifiedUserMng unifiedUserMng;
	@Autowired
	private CmsUserMng cmsUserMng;
	@Autowired
	private SessionProvider session;
	@Autowired
	private ImageCaptchaService imageCaptchaService;
	@Autowired
	private ConfigMng configMng;
	@Autowired
	private CmsConfigItemMng cmsConfigItemMng;
	@Resource
	private ChannelMng channelMng;
	@Resource
	private RegisterListService registerListService;
}
