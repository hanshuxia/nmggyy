package com.anchorcms.cms.controller.admin.main;


import javax.servlet.http.HttpServletRequest;

import com.anchorcms.cms.model.main.CmsThirdAccount;
import com.anchorcms.cms.service.main.CmsThirdAccountMng;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.common.utils.CmsUtils;
import com.anchorcms.common.web.CookieUtils;
import com.anchorcms.core.model.CmsSite;
import com.anchorcms.core.web.WebErrors;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.anchorcms.common.page.SimplePage.cpn;

@Controller
public class CmsThirdAccountController {
	private static final Logger log = LoggerFactory.getLogger(CmsThirdAccountController.class);

	@RequiresPermissions("account:v_list")
	@RequestMapping("/account/v_list.do")
	public String list(String username,String source,Integer pageNo, HttpServletRequest request, ModelMap model) {
		Pagination pagination = manager.getPage(username,source,cpn(pageNo), CookieUtils
				.getPageSize(request));
		model.addAttribute("username",username);
		model.addAttribute("source",source);
		model.addAttribute("pagination",pagination);
		model.addAttribute("pageNo",pagination.getPageNo());
		return "account/list";
	}
	
	@RequiresPermissions("account:o_delete")
	@RequestMapping("/account/o_delete.do")
	public String delete(String username,String source,Long[] ids, Integer pageNo, HttpServletRequest request,
			ModelMap model) {
		WebErrors errors = validateDelete(ids, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		CmsThirdAccount[] beans = manager.deleteByIds(ids);
		for (CmsThirdAccount bean : beans) {
			log.info("delete CmsThirdAccount id={}", bean.getAccountId());
		}
		return list(username,source,pageNo, request, model);
	}

	
	private WebErrors validateDelete(Long[] ids, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		CmsSite site = CmsUtils.getSite(request);
		if (errors.ifEmpty(ids, "ids")) {
			return errors;
		}
		for (Long id : ids) {
			vldExist(id, site.getSiteId(), errors);
		}
		return errors;
	}

	private boolean vldExist(Long id, Integer siteId, WebErrors errors) {
		if (errors.ifNull(id, "id")) {
			return true;
		}
		CmsThirdAccount entity = manager.findById(id);
		if(errors.ifNotExist(entity, CmsThirdAccount.class, id)) {
			return true;
		}
		return false;
	}
	
	@Autowired
	private CmsThirdAccountMng manager;
}