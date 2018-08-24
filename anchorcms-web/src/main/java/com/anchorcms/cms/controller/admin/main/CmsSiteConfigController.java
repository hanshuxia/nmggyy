package com.anchorcms.cms.controller.admin.main;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import static com.anchorcms.common.constants.Constants.TPLDIR_INDEX;
import static com.anchorcms.common.constants.Constants.TPL_BASE;

import com.anchorcms.common.utils.CmsUtils;
import com.anchorcms.common.utils.CoreUtils;
import com.anchorcms.core.model.CmsDictionary;
import com.anchorcms.core.model.CmsSite;
import com.anchorcms.core.model.Ftp;
import com.anchorcms.core.service.*;
import com.anchorcms.core.tpl.TplService;
import com.anchorcms.core.web.WebErrors;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class CmsSiteConfigController {
	private static final Logger log = LoggerFactory
			.getLogger(CmsSiteConfigController.class);
	@RequiresPermissions("site_config:v_base_edit")
	@RequestMapping("/site_config/v_base_edit.do")
	public String baseEdit(HttpServletRequest request, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		List<Ftp> ftpList = ftpMng.getList();
		List<String>indexTplList=getTplIndex(site,null);
		// 当前模板，去除基本路径
		int tplPathLength = site.getTplPath().length();
		String tplIndex = site.getTplIndex();
		if (!StringUtils.isBlank(tplIndex)) {
			tplIndex = tplIndex.substring(tplPathLength);
		}
		model.addAttribute("ftpList", ftpList);
		model.addAttribute("indexTplList",indexTplList);
		model.addAttribute("config", configMng.get());
		model.addAttribute("cmsSite", site);
		model.addAttribute("tplIndex", tplIndex);
		return "site_config/base_edit";
	}

	@RequiresPermissions("site_config:o_base_update")
	@RequestMapping("/site_config/o_base_update.do")
	public String baseUpdate(CmsSite bean, Integer uploadFtpId,Integer syncPageFtpId,
			HttpServletRequest request, ModelMap model) {
		WebErrors errors = validateBaseUpdate(bean, request);
		String tplPath = bean.getTplPath();
		if (!StringUtils.isBlank(bean.getTplIndex())) {
			bean.setTplIndex(tplPath + bean.getTplIndex());
		}
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		CmsSite site = CmsUtils.getSite(request);
		bean.setSiteId(site.getSiteId());
		bean = manager.update(bean, uploadFtpId,syncPageFtpId);
		model.addAttribute("message", "global.success");
		log.info("update CmsSite success. id={}", site.getSiteId());
		cmsLogMng.operating(request, "cmsSiteConfig.log.updateBase", null);
		return baseEdit(request, model);
	}

	private List<String> getTplIndex(CmsSite site,String tpl) {
		String path=site.getSitePath();
		List<String> tplList = tplManager.getNameListByPrefix(site.getTplIndexPrefix(TPLDIR_INDEX));
		return CoreUtils.tplTrim(tplList,getTplPath(path), tpl);
	}

	private WebErrors validateBaseUpdate(CmsSite bean,
			HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		return errors;
	}
	
	private String getTplPath(String path){
		return TPL_BASE + "/" + path;
	}

	@Resource
	private FtpMng ftpMng;
	@Resource
	private CmsLogMng cmsLogMng;
	@Resource
	private CmsSiteMng manager;
	@Resource
	private CmsDictionaryMng dictionaryMng;
    @Resource
	private TplService tplManager;
	@Resource
	private CmsConfigMng configMng;

}