package com.anchorcms.cms.controller.admin.main;


import com.anchorcms.cms.model.main.ContentRecord;
import com.anchorcms.cms.service.main.ContentRecordMng;
import com.anchorcms.common.utils.CmsUtils;
import com.anchorcms.core.model.CmsSite;
import com.anchorcms.core.web.WebErrors;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class ContentRecordController {

	@RequiresPermissions("content:v_records")
	@RequestMapping(value = "/content/record/list.do")
	public String list(Integer id, HttpServletRequest request,
					   HttpServletResponse response, ModelMap model) {
		model.addAttribute("records", manager.getListByContentId(id));
		model.addAttribute("contentId",id);
		return "content/record";
	}
	
	@RequiresPermissions("ContentRecord:o_delete")
	@RequestMapping("/content/record/o_delete.do")
	public String delete(Integer contentId, Long[] ids, Integer pageNo,
						 HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		WebErrors errors = validateDelete(ids, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		manager.deleteByIds(ids);
		return list(contentId, request, response, model);
	}
	

	private WebErrors validateDelete(Long[] ids, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		CmsSite site = CmsUtils.getSite(request);
		errors.ifEmpty(ids, "ids");
		for (Long id : ids) {
			vldExist(id, site.getSiteId(), errors);
		}
		return errors;
	}

	private boolean vldExist(Long id, Integer siteId, WebErrors errors) {
		if (errors.ifNull(id, "id")) {
			return true;
		}
		ContentRecord entity = manager.findById(id);
		if (errors.ifNotExist(entity, ContentRecord.class, id)) {
			return true;
		}
		return false;
	}
	
	@Autowired
	private ContentRecordMng manager;
}