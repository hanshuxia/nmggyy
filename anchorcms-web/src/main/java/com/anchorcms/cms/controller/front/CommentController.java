package com.anchorcms.cms.controller.front;


import com.anchorcms.cms.model.assist.CmsComment;
import com.anchorcms.cms.model.main.ChannelExt;
import com.anchorcms.cms.model.main.Content;
import com.anchorcms.cms.service.assist.CmsCommentMng;
import com.anchorcms.cms.service.main.ChannelMng;
import com.anchorcms.cms.service.main.ContentMng;
import com.anchorcms.common.utils.CmsUtils;
import com.anchorcms.common.utils.FrontUtils;
import com.anchorcms.common.web.RequestUtils;
import com.anchorcms.common.web.ResponseUtils;
import com.anchorcms.common.web.session.SessionProvider;
import com.anchorcms.core.model.CmsSite;
import com.anchorcms.core.model.CmsUser;
import com.octo.captcha.service.CaptchaServiceException;
import com.octo.captcha.service.image.ImageCaptchaService;
import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.anchorcms.common.constants.Constants.TPLDIR_CSI;
import static com.anchorcms.common.constants.Constants.TPLDIR_SPECIAL;

/**
 * @Author 阁楼麻雀
 * @Date 2016/12/17 10:27
 * @Desc 评论功能
 */

@Controller
public class CommentController {
	private static final Logger log = LoggerFactory.getLogger(CommentController.class);

	public static final String COMMENT_PAGE = "tpl.commentPage";
	public static final String COMMENT_PAGE_ASKHELP="tpl.commentPageAsk";
	public static final String COMMENT_LIST = "tpl.commentList";
	public static final String CONSULT_LIST = "tpl.consultList";
	public static final String COMMENT_INPUT = "tpl.commentInput";

	@RequestMapping(value = "/comment*.jspx", method = RequestMethod.GET)
	public String page(Integer contentId, Integer pageNo,Integer channelId,
			HttpServletRequest request, HttpServletResponse response,
			ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		if(contentId==null){
			return FrontUtils.showMessage(request, model,
			"comment.contentNotFound");
		}
		Content content = contentMng.findById(contentId);
		if (content == null) {
			return FrontUtils.showMessage(request, model,
					"comment.contentNotFound");
		}
		if (content.getChannel().getCommentControl() == ChannelExt.COMMENT_OFF) {
			return FrontUtils.showMessage(request, model, "comment.closed");
		}
		// 将request中所有参数保存至model中。
		model.putAll(RequestUtils.getQueryParams(request));
		FrontUtils.frontData(request, model, site);
		FrontUtils.frontPageData(request, model);
		model.addAttribute("content", content);
		model.addAttribute("channel",channelMng.findById(channelId));
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TPLDIR_SPECIAL, COMMENT_PAGE);
	}
	
	@RequestMapping(value = "/comment_input_csi.jspx")
	public String custom(String tpl,Integer contentId, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		log.debug("visit csi custom template: {}", tpl);
		CmsSite site = CmsUtils.getSite(request);
		if(contentId==null){
			return FrontUtils.showMessage(request, model,
			"comment.contentNotFound");
		}
		Content content = contentMng.findById(contentId);
		if (content == null) {
			return FrontUtils.showMessage(request, model,
					"comment.contentNotFound");
		}
		if (content.getChannel().getCommentControl() == ChannelExt.COMMENT_OFF) {
			return FrontUtils.showMessage(request, model, "comment.closed");
		}
		// 将request中所有参数保存至model中。
		model.putAll(RequestUtils.getQueryParams(request));
		model.addAttribute("content", content);
		FrontUtils.frontData(request, model, site);
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TPLDIR_SPECIAL, COMMENT_INPUT);
	}

	@RequestMapping(value = "/comment_list.jspx")
	public String list(Integer siteId, Integer contentId, Integer parentId,
			Integer greatTo,Integer recommend, Integer checked, 
			Integer orderBy, Integer count,
			HttpServletRequest request, HttpServletResponse response,
			ModelMap model) {
		if (count == null || count <= 0 || count > 200) {
			count = 200;
		}
		boolean desc;
		if (orderBy == null || orderBy == 0) {
			desc = true;
		} else {
			desc = false;
		}
		Boolean rec;
		if (recommend != null) {
			rec = recommend != 0;
		} else {
			rec = null;
		}
		Boolean chk;
		if (checked != null) {
			chk = checked != 0;
		} else {
			chk = null;
		}
		List<CmsComment> list = cmsCommentMng.getListForTag(siteId, contentId,
				parentId,greatTo, chk, rec, desc, count);
		// 将request中所有参数
		model.putAll(RequestUtils.getQueryParams(request));
		model.addAttribute("list", list);
		model.addAttribute("contentId", contentId);
		model.addAttribute("content", contentMng.findById(contentId));
		CmsSite site = CmsUtils.getSite(request);
		FrontUtils.frontData(request, model, site);
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TPLDIR_CSI, COMMENT_LIST);
	}

	@RequestMapping(value = "/comment.jspx", method = RequestMethod.POST)
	public void submit(Integer contentId, Integer parentId,Integer score,
			String text, String captcha,String sessionId,
			HttpServletRequest request, HttpServletResponse response,
			ModelMap model) throws JSONException, IOException {
		CmsSite site = CmsUtils.getSite(request);
		CmsUser user = CmsUtils.getUser(request);
		JSONObject json = new JSONObject();
		if (contentId == null) {
			json.put("success", false);
			json.put("status", 100);
			ResponseUtils.renderJson(response, json.toString());
			return;
		}
		if (StringUtils.isBlank(text)) {
			json.put("success", false);
			json.put("status", 101);
			ResponseUtils.renderJson(response, json.toString());
			return;
		}
		if (user == null || user.getGroup().getNeedCaptcha()) {
			// 验证码错误
			try {
				if (!imageCaptchaService.validateResponseForID(session
						.getSessionId(request, response), captcha)) {
					json.put("success", false);
					json.put("status", 1);
					ResponseUtils.renderJson(response, json.toString());
					return;
				}
			} catch (CaptchaServiceException e) {
				json.put("success", false);
				json.put("status", 1);
				log.warn("", e);
				ResponseUtils.renderJson(response, json.toString());
				return;
			}
		}
		Content content = contentMng.findById(contentId);
		if (content == null) {
			// 内容不存在
			json.put("success", false);
			json.put("status", 2);
		} else if (content.getChannel().getCommentControl() == ChannelExt.COMMENT_OFF) {
			// 评论关闭
			json.put("success", false);
			json.put("status", 3);
		} else if ((content.getChannel().getCommentControl() == ChannelExt.COMMENT_LOGIN|content.getChannel().getCommentControl() == ChannelExt.COMMENT_LOGIN_MANY)
				&& user == null) {
			// 需要登录才能评论
			json.put("success", false);
			json.put("status", 4);
		}else if(content.getChannel().getCommentControl() == ChannelExt.COMMENT_LOGIN&&user!=null){
			if (hasCommented(user, content)) {
				// 已经评论过，不能重复评论
				json.put("success", false);
				json.put("status", 5);
			}
		}else {
			boolean checked = false;
			Integer userId = null;
			if (user != null) {
				checked = !user.getGroup().getNeedCheck();
				userId = user.getUserId();
			}
			cmsCommentMng.comment(score,text, RequestUtils.getIpAddr(request),
					contentId, site.getSiteId(), userId, checked, false,parentId);
			json.put("success", true);
			json.put("status", 0);
		}
		ResponseUtils.renderJson(response, json.toString());
	}

	@RequestMapping(value = "/comment_up.jspx")
	public void up(Integer commentId, HttpServletRequest request,
			HttpServletResponse response) {
		if (exist(commentId)) {
			cmsCommentMng.ups(commentId);
			ResponseUtils.renderJson(response, "true");
		} else {
			ResponseUtils.renderJson(response, "false");
		}
	}

	@RequestMapping(value = "/comment_down.jspx")
	public void down(Integer commentId, HttpServletRequest request,
			HttpServletResponse response) {
		if (exist(commentId)) {
			cmsCommentMng.downs(commentId);
			ResponseUtils.renderJson(response, "true");
		} else {
			ResponseUtils.renderJson(response, "false");
		}
	}

	private boolean hasCommented(CmsUser user, Content content) {
		if (content.hasCommentUser(user)) {
			return true;
		} else {
			return false;
		}
	}

	private boolean exist(Integer id) {
		if (id == null) {
			return false;
		}
		CmsComment comment = cmsCommentMng.findById(id);
		return comment != null;
	}
	@RequestMapping(value = "/front/askhelp_comment.jspx", method = RequestMethod.GET)
	public String page1(Integer contentId, Integer pageNo,
					   HttpServletRequest request, HttpServletResponse response,
					   ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		if(contentId==null){
			return FrontUtils.showMessage(request, model,
					"comment.contentNotFound");
		}
		Content content = contentMng.findById(contentId);
		if (content == null) {
			return FrontUtils.showMessage(request, model,
					"comment.contentNotFound");
		}
		if (content.getChannel().getCommentControl() == ChannelExt.COMMENT_OFF) {
			return FrontUtils.showMessage(request, model, "comment.closed");
		}
		// 将request中所有参数保存至model中。
		model.putAll(RequestUtils.getQueryParams(request));
		FrontUtils.frontData(request, model, site);
		FrontUtils.frontPageData(request, model);
		model.addAttribute("content", content);
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TPLDIR_SPECIAL, COMMENT_PAGE_ASKHELP);
	}


	/**
	 * 2-27 动态加载品论页
	 *
	 * @return
	 */
	@RequestMapping(value = "/icloudCommentList.jspx")
	public String list(Integer contentId, Integer channelId, HttpServletRequest request, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		// 将request中所有参数
		model.putAll(RequestUtils.getQueryParams(request));
		model.addAttribute("contentId", contentId);
		model.addAttribute("channelId", channelId);
		FrontUtils.frontData(request, model, site);
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TPLDIR_CSI, COMMENT_LIST);
	}
	/**
	 * 2-27 动态加载留言页
	 *
	 * @return
	 */
	@RequestMapping(value = "/icloudConsultList.jspx")
	public String listp(Integer contentId, Integer channelId, HttpServletRequest request, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		// 将request中所有参数
		model.putAll(RequestUtils.getQueryParams(request));
		model.addAttribute("contentId", contentId);
		model.addAttribute("channelId", channelId);
		FrontUtils.frontData(request, model, site);
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TPLDIR_CSI, CONSULT_LIST);
	}

	@Resource
	private CmsCommentMng cmsCommentMng;
	@Resource
	private ContentMng contentMng;
	@Resource
	private SessionProvider session;
	@Autowired
	private ImageCaptchaService imageCaptchaService;
	@Resource
	private ChannelMng channelMng;
}
