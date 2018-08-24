package com.anchorcms.cms.controller.member;

import com.anchorcms.cms.service.main.ContentMng;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.common.utils.CmsUtils;
import com.anchorcms.common.utils.FrontUtils;
import com.anchorcms.common.web.CookieUtils;
import com.anchorcms.common.web.ResponseUtils;
import com.anchorcms.core.model.CmsSite;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.core.model.MemberConfig;
import com.anchorcms.core.service.CmsUserMng;
import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.anchorcms.common.constants.Constants.TPLDIR_MEMBER;
import static com.anchorcms.common.page.SimplePage.cpn;

/**
 * 收藏信息Action
 */
@Controller
public class CollectionMemberController {

	public static final String COLLECTION_LIST = "tpl.collectionList";

	/**
	 * 我的收藏信息
	 * 
	 * 如果没有登录则跳转到登陆页
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/member/collection_list.jspx")
	public String collection_list(String queryTitle, Integer queryChannelId,
								  Integer pageNo, HttpServletRequest request,
								  HttpServletResponse response, ModelMap model) {
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
		Pagination p = contentMng.getPageForCollection(site.getSiteId(), user
				.getUserId(), cpn(pageNo), CookieUtils.getPageSize(request));
		model.addAttribute("pagination", p);
		if (!StringUtils.isBlank(queryTitle)) {
			model.addAttribute("queryTitle", queryTitle);
		}
		if (queryChannelId != null) {
			model.addAttribute("queryChannelId", queryChannelId);
		}
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TPLDIR_MEMBER, COLLECTION_LIST);
	}

	@RequestMapping(value = "/member/collect.jspx")
	public void collect(Integer cId, Integer operate,
						HttpServletRequest request, HttpServletResponse response,
						ModelMap model) throws JSONException {
		CmsUser user = CmsUtils.getUser(request);
		JSONObject object = new JSONObject();
		if (user == null) {
			object.put("result", false);
		} else {
			object.put("result", true);
			userMng.updateUserConllection(user,cId,operate);
		}
		ResponseUtils.renderJson(response, object.toString());
	}
	
	@RequestMapping(value = "/member/collect_cancel.jspx")
	public String  collect_cancel(Integer[] cIds, Integer pageNo, HttpServletRequest request, HttpServletResponse response,
								  ModelMap model) throws JSONException {
		CmsUser user = CmsUtils.getUser(request);
		CmsSite site = CmsUtils.getSite(request);
		FrontUtils.frontData(request, model, site);
		MemberConfig mcfg = site.getConfig().getMemberConfig();
		// 没有开启会员功能
		if (!mcfg.isMemberOn()) {
			return FrontUtils.showMessage(request, model, "member.memberClose");
		}
		if (user == null) {
			return FrontUtils.showLogin(request, model, site);
		}
		for(Integer  id:cIds){
			userMng.updateUserConllection(user,id,0);
		}
		return collection_list(null, null, pageNo, request, response, model);
	}

	@RequestMapping(value = "/member/collect_exist.jspx")
	public void collect_exist(Integer cId, HttpServletRequest request,
							  HttpServletResponse response, ModelMap model) throws JSONException {
		CmsSite site = CmsUtils.getSite(request);
		FrontUtils.frontData(request, model, site);
		CmsUser user = CmsUtils.getUser(request);
		JSONObject object = new JSONObject();
		FrontUtils.frontData(request, model, site);
		if (user == null) {
			object.put("result", false);
		} else {
			if (cId!=null&&user.getCollectContents().contains(contentMng.findById(cId))) {
				object.put("result", true);
			} else {
				object.put("result", false);
			}
		}
		ResponseUtils.renderJson(response, object.toString());
	}

	@Resource
	private ContentMng contentMng;
	@Resource
	private CmsUserMng userMng;

}
