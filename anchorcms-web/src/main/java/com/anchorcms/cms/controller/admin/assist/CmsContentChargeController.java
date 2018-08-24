package com.anchorcms.cms.controller.admin.assist;

/*
import static com.jeecms.common.page.SimplePage.cpn;
*/

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.anchorcms.cms.model.assist.CmsConfigContentCharge;
import com.anchorcms.cms.service.assist.CmsConfigContentChargeMng;
import com.anchorcms.cms.service.main.ContentBuyMng;
import com.anchorcms.cms.service.main.ContentChargeMng;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.common.web.CookieUtils;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.core.service.CmsUserAccountMng;
import com.anchorcms.core.service.CmsUserMng;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.anchorcms.common.page.SimplePage.cpn;


/**
 * 内容收费统计
 * @author tom
 *
 */
@Controller
public class CmsContentChargeController {
	
	/**
	 * 内容收费统计列表
	 * @param orderBy
	 */
	@RequiresPermissions("contentBuy:charge_list")
	@RequestMapping("/contentBuy/charge_list.do")
	public String chargeList(String contentTitle,String author,
			Date buyTimeBegin,Date buyTimeEnd,
			Integer orderBy,Integer pageNo,
			HttpServletRequest request, ModelMap model) {
		if(orderBy==null){
			orderBy=1;
		}
		Integer authorUserId=null;
		if(StringUtils.isNotBlank(author)){
			CmsUser authorUser=userMng.findByUsername(author);
			if(authorUser!=null){
				authorUserId=authorUser.getUserId();
			}else{
				authorUserId=0;
			}
		}
		Pagination pagination = contentChargeMng.getPage(contentTitle,
				authorUserId,buyTimeBegin, buyTimeEnd,orderBy,
				cpn(pageNo), CookieUtils.getPageSize(request));
		model.addAttribute("pagination",pagination);
		model.addAttribute("pageNo",pagination.getPageNo());
		model.addAttribute("orderBy",orderBy);
		model.addAttribute("contentTitle",contentTitle);
		model.addAttribute("author",author);
		model.addAttribute("buyTimeBegin",buyTimeBegin);
		model.addAttribute("buyTimeEnd",buyTimeEnd);
		return "contentBuy/charge_list";
	}
	
	/**
	 * 作者所得统计
	 * @param orderBy
	 * @return
	 */
	@RequiresPermissions("contentBuy:user_account_list")
	@RequestMapping("/contentBuy/user_account_list.do")
	public String userAccountList(String username,
			Date drawTimeBegin,Date drawTimeEnd,
			Integer orderBy,Integer pageNo,
			HttpServletRequest request, ModelMap model) {
		if(orderBy==null){
			orderBy=1;
		}
		Pagination pagination = userAccountMng.getPage(username,
				drawTimeBegin,drawTimeEnd,orderBy, 
				cpn(pageNo), CookieUtils.getPageSize(request));
		model.addAttribute("pagination",pagination);
		model.addAttribute("pageNo",pagination.getPageNo());
		model.addAttribute("orderBy",orderBy);
		model.addAttribute("username",username);
		model.addAttribute("drawTimeBegin",drawTimeBegin);
		model.addAttribute("drawTimeEnd",drawTimeEnd);
		return "contentBuy/user_account_list";
	}
	
	/**
	 * 用户购买列表
	 * @param
	 * @param pageNo
	 * @param request
	 * @param model
	 * @return
	 */
	@RequiresPermissions("contentBuy:user_order_list")
	@RequestMapping("/contentBuy/user_order_list.do")
	public String userBuyList(String orderNum,String buyusername,
			String authorusername,Short payMode,
			Integer pageNo,HttpServletRequest request, ModelMap model) {
		Integer buyUserId=null,authorUserId=null;
		if(StringUtils.isNotBlank(buyusername)){
			CmsUser u=userMng.findByUsername(buyusername);
			if(u!=null){
				buyUserId=u.getUserId();
			}
		}
		if(StringUtils.isNotBlank(authorusername)){
			CmsUser u=userMng.findByUsername(authorusername);
			if(u!=null){
				authorUserId=u.getUserId();
			}
		}
		if(payMode==null){
			payMode=0;
		}
		Pagination pagination = contentBuyMng.getPage(orderNum, buyUserId, authorUserId, 
				payMode,cpn(pageNo), CookieUtils.getPageSize(request));
		model.addAttribute("pagination",pagination);
		model.addAttribute("pageNo",pagination.getPageNo());
		model.addAttribute("orderNum",orderNum);
		model.addAttribute("buyusername",buyusername);
		model.addAttribute("authorusername",authorusername);
		model.addAttribute("payMode",payMode);
		return "contentBuy/order_list";
	}
	
	/**
	 * 平台佣金汇总统计
	 * @return
	 */
	@RequiresPermissions("contentBuy:commissionStatic")
	@RequestMapping("/contentBuy/commissionStatic.do")
	public String commissionStatic(HttpServletRequest request, ModelMap model) {
		CmsConfigContentCharge config= configContentChargeMng.getDefault();
		model.addAttribute("config",config);
		return "contentBuy/commission";
	}
	
	@Autowired
	private ContentChargeMng contentChargeMng;
	@Autowired
	private CmsUserAccountMng userAccountMng;
	@Autowired
	private ContentBuyMng contentBuyMng;
	@Autowired
	private CmsUserMng userMng;
	@Autowired
	private CmsConfigContentChargeMng configContentChargeMng;
	
}