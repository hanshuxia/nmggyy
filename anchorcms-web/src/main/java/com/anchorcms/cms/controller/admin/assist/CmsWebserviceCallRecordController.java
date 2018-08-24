package com.anchorcms.cms.controller.admin.assist;


import com.anchorcms.cms.service.main.CmsWebserviceCallRecordMng;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.common.web.CookieUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

import static com.anchorcms.common.page.SimplePage.cpn;


@Controller
public class CmsWebserviceCallRecordController {

	@RequiresPermissions("webserviceCallRecord:v_list")
	@RequestMapping("/webserviceCallRecord/v_list.do")
	public String list(Integer pageNo, HttpServletRequest request, ModelMap model) {
		Pagination pagination = manager.getPage(cpn(pageNo), CookieUtils
				.getPageSize(request));
		model.addAttribute("pagination",pagination);
		return "webserviceCallRecord/list";
	}
	
	@RequiresPermissions("webserviceCallRecord:o_delete")
	@RequestMapping("/webserviceCallRecord/o_delete.do")
	public String delete(Integer[] ids, Integer pageNo, HttpServletRequest request,
			ModelMap model) {
		manager.deleteByIds(ids);
		return list(pageNo, request, model);
	}
	@Autowired
	private CmsWebserviceCallRecordMng manager;
}