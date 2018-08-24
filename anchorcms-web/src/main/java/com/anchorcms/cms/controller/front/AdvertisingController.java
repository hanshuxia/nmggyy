package com.anchorcms.cms.controller.front;

import com.anchorcms.cms.model.assist.CmsAdvertising;
import com.anchorcms.cms.model.assist.CmsAdvertisingSpace;
import com.anchorcms.cms.service.assist.CmsAdvertisingMng;
import com.anchorcms.cms.service.assist.CmsAdvertisingSpaceMng;
import com.anchorcms.common.utils.CmsUtils;
import com.anchorcms.common.utils.FrontUtils;
import com.anchorcms.core.model.CmsSite;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static com.anchorcms.common.constants.Constants.*;

/**
 * 广告Action
 */
@Controller
public class AdvertisingController {
	// private static final Logger log = LoggerFactory
	// .getLogger(AdvertisingAct.class);

	public static final String TPL_AD = "tpl.advertising";
	public static final String TPL_ADSPACE = "tpl.adspace";

	@RequestMapping(value = "/ad.jspx")
	public String ad(Integer id, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		if (id != null) {
			CmsAdvertising ad = cmsAdvertisingMng.findById(id);
			model.addAttribute("ad", ad);
		}
		FrontUtils.frontData(request, model, site);
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TPLDIR_CSI, TPL_AD);
	}

	@RequestMapping(value = "/adspace.jspx")
	public String adspace(Integer id, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		if (id != null) {
			CmsAdvertisingSpace adspace = cmsAdvertisingSpaceMng.findById(id);
			List<CmsAdvertising> adList = cmsAdvertisingMng.getList(id, true);
			model.addAttribute("adspace", adspace);
			model.addAttribute("adList", adList);
		}
		FrontUtils.frontData(request, model, site);
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TPLDIR_CSI, TPL_ADSPACE);
	}

	@RequestMapping(value = "/ad_display.jspx")
	public void display(Integer id, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		if (id != null) {
			cmsAdvertisingMng.display(id);
		}
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
	}

	@RequestMapping(value = "/ad_click.jspx")
	public void click(Integer id, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		if (id != null) {
			cmsAdvertisingMng.click(id);
		}
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
	}

	@Resource
	private CmsAdvertisingMng cmsAdvertisingMng;
	@Resource
	private CmsAdvertisingSpaceMng cmsAdvertisingSpaceMng;
}
