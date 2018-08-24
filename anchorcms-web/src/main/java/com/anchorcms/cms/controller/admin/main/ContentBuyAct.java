package com.anchorcms.cms.controller.admin.main;


import com.anchorcms.cms.model.main.ContentBuy;
import com.anchorcms.cms.service.main.ContentBuyMng;
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

import javax.servlet.http.HttpServletRequest;

import static com.anchorcms.common.page.SimplePage.cpn;


@Controller
public class ContentBuyAct {
	private static final Logger log = LoggerFactory.getLogger(ContentBuyAct.class);

	@RequiresPermissions("contentBuy:v_list")
	@RequestMapping("/contentBuy/v_list.do")
	public String list(String orderNum,Integer pageNo, 
			HttpServletRequest request, ModelMap model) {
		Pagination pagination = manager.getPage(orderNum,null,null,null,
				cpn(pageNo), CookieUtils.getPageSize(request));
		model.addAttribute("pagination",pagination);
		model.addAttribute("pageNo",pagination.getPageNo());
		return "contentBuy/list";
	}
	
	@RequiresPermissions("contentBuy:o_delete")
	@RequestMapping("/contentBuy/o_delete.do")
	public String delete(String orderNum,Long[] ids, 
			Integer pageNo, HttpServletRequest request,
			ModelMap model) {
		WebErrors errors = validateDelete(ids, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		ContentBuy[] beans = manager.deleteByIds(ids);
		for (ContentBuy bean : beans) {
			log.info("delete ContentBuy id={}", bean.getContentBuyId());
		}
		return list(orderNum,pageNo, request, model);
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
		ContentBuy entity = manager.findById(id);
		if(errors.ifNotExist(entity, ContentBuy.class, id)) {
			return true;
		}
		return false;
	}
	
	@Autowired
	private ContentBuyMng manager;
}