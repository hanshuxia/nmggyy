package com.anchorcms.cms.controller.member;


import com.anchorcms.common.utils.CmsUtils;
import com.anchorcms.common.utils.FrontUtils;
import com.anchorcms.common.web.RequestUtils;
import com.anchorcms.core.model.CmsSite;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

import static com.anchorcms.common.constants.Constants.TPLDIR_CSI;


@Controller
public class LoginController {
	public static final String LOGIN_CSI = "tpl.loginCsi";
	public static final String LOGIN_CSI2 = "tpl.loginCsi2";
	public static final String TPL_SPACE = "tpl.space";

	/**
	 * 客户端包含
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/login_csi.jspx")
	public String csi(HttpServletRequest request, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		// 将request中所有参数
		model.putAll(RequestUtils.getQueryParams(request));
		FrontUtils.frontData(request, model, site);
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TPLDIR_CSI, LOGIN_CSI);
	}

	@RequestMapping(value = "/login_csi2.jspx")
	public String csi2(HttpServletRequest request, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		// 将request中所有参数
		model.putAll(RequestUtils.getQueryParams(request));
		FrontUtils.frontData(request, model, site);
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TPLDIR_CSI, LOGIN_CSI2);
	}

}
