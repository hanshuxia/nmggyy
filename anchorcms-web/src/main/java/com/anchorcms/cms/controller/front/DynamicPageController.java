package com.anchorcms.cms.controller.front;


import java.io.File;
import java.io.IOException;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.anchorcms.cms.model.main.Channel;
import com.anchorcms.cms.model.main.Content;
import com.anchorcms.cms.model.main.ContentCheck;
import com.anchorcms.cms.service.main.*;
import com.anchorcms.common.model.PageInfo;
import com.anchorcms.common.page.Paginable;
import com.anchorcms.common.page.SimplePage;
import com.anchorcms.common.utils.CmsUtils;
import com.anchorcms.common.utils.FrontUtils;
import com.anchorcms.common.utils.URLHelper;
import com.anchorcms.common.web.mvc.RealPathResolver;
import com.anchorcms.common.web.session.SessionProvider;
import com.anchorcms.core.model.CmsConfig;
import com.anchorcms.core.model.CmsGroup;
import com.anchorcms.core.model.CmsSite;
import com.anchorcms.core.model.CmsUser;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import static com.anchorcms.common.constants.Constants.*;


@Controller
public class DynamicPageController {
	private static final Logger log = LoggerFactory
			.getLogger(DynamicPageController.class);
	/**
	 * 首页模板名称
	 */
	public static final String TPL_INDEX = "tpl.index";
	public static final String GROUP_FORBIDDEN = "login.groupAccessForbidden";
	public static final String CONTENT_STATUS_FORBIDDEN ="content.notChecked";


	/**
	 * TOMCAT的默认路径
	 *
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(HttpServletRequest request,HttpServletResponse response, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		FrontUtils.frontData(request, model, site);
		//使用静态首页而且静态首页存在
		if(existIndexPage(site)){
			return goToIndexPage(request, response, site);
		}else{
			String tpl = site.getTplIndex();
			if (!StringUtils.isBlank(tpl)) {
				return tpl;
			} else {
				return FrontUtils.getTplPath(request, site.getSolutionPath(),TPLDIR_INDEX, TPL_INDEX);
			}
		}
	}


	/**
	 * WEBLOGIC的默认路径
	 *
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/index.jhtml", method = RequestMethod.GET)
	public String indexForWeblogic(HttpServletRequest request,HttpServletResponse response, ModelMap model) {
		return index(request, response,model);
	}

	/**
	 * 动态页入口
	 */
	@RequestMapping(value = "/**/*.*", method = RequestMethod.GET)
	public String dynamic(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		// 尽量不要携带太多参数，多使用标签获取数据。
		// 目前已知的需要携带翻页信息。
		// 获得页号和翻页信息吧。
		int pageNo = URLHelper.getPageNo(request);
		/**
		 * @auther 李利民
		 * 获取ＵＲＬ中的阐述，并拆分放到平param 中
		 * 2016/12/23
		 */
		String[] params = URLHelper.getParams(request);
		for (int i=0;i<params.length;i++){
			//判断传入的参数是否为空
			String pTmp = params[i];
			if(pTmp.equals("")){
				params[i] = null;
			}
		}
		//end
		PageInfo info = URLHelper.getPageInfo(request);
		String[] paths = URLHelper.getPaths(request);
		int len = paths.length;
		if (len == 1) {
			// 单页
			return channel(paths[0],true, pageNo, params, info, request, response,
					model);
		} else if (len == 2) {
			if (paths[1].equals(INDEX)) {
				// 栏目页
				return channel(paths[0],false, pageNo, params, info, request,
						response, model);
			} else {
				// 内容页
				try {
					Integer id = Integer.parseInt(paths[1]);
					return content(id, pageNo, params, info, request, response,
							model);
				} catch (NumberFormatException e) {
					log.debug("Content id must String: {}", paths[1]);
					return FrontUtils.pageNotFound(request, response, model);
				}
			}
		} else {
			log.debug("Illegal path length: {}, paths: {}", len, paths);
			return FrontUtils.pageNotFound(request, response, model);
		}
	}


	public String channel(String path,boolean checkAlone, int pageNo, String[] params,PageInfo info, HttpServletRequest request,HttpServletResponse response, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		Channel channel = channelMng.findByPathForTag(path, site.getSiteId());
		if (channel == null) {
			log.debug("Channel path not found: {}", path);
			return FrontUtils.pageNotFound(request, response, model);
		}
		//检查是否单页
		if(checkAlone){
			if(channel.getHasContent()){
				return FrontUtils.pageNotFound(request, response, model);
			}
		}
		model.addAttribute("channel", channel);
		model.addAttribute("q",params);
		FrontUtils.frontData(request, model, site);
		FrontUtils.frontPageData(request, model);
		String equipment=(String) request.getAttribute("ua");
		if(StringUtils.isNotBlank(equipment)&&equipment.equals("mobile")){
			return channel.getMobileTplChannelOrDef();
		}
		return channel.getTplChannelOrDef();
	}

	public String content(Integer id, int pageNo, String[] params,
			PageInfo info, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		Content content = contentMng.findById(id);
		if (content == null) {
			log.debug("Content id not found: {}", id);
			return FrontUtils.pageNotFound(request, response, model);
		}
		Integer pageCount=content.getPageCount();
		if(pageNo>pageCount||pageNo<0){
			return FrontUtils.pageNotFound(request, response, model);
		}
		//非终审文章
		CmsConfig config=CmsUtils.getSite(request).getConfig();
		Boolean preview=config.getConfigAttr().getPreview();
		if(config.getViewOnlyChecked()&&!content.getStatus().equals(ContentCheck.CHECKED)){
			return FrontUtils.showMessage(request, model, CONTENT_STATUS_FORBIDDEN);
		}
		CmsUser user = CmsUtils.getUser(request);
		CmsSite site = content.getSite();
		Set<CmsGroup> groups = content.getViewGroupsExt();
		int len = groups.size();
		// 需要浏览权限
		if (len != 0) {
			// 没有登录
			if (user == null) {
				session.setAttribute(request, response, "loginSource", "needPerm");
				return FrontUtils.showLogin(request, model, site);
			}
			// 已经登录但没有权限
			Integer gid = user.getGroup().getGroupId();
			boolean right = false;
			for (CmsGroup group : groups) {
				if (group.getGroupId().equals(gid)) {
					right = true;
					break;
				}
			}
			//无权限且不支持预览
			if (!right&&!preview) {
				String gname = user.getGroup().getGroupName();
				return FrontUtils.showMessage(request, model, GROUP_FORBIDDEN,
						gname);
			}
			//无权限支持预览
			if(!right&&preview){
				model.addAttribute("preview", preview);
				model.addAttribute("groups", groups);
			}
		}
		//收费模式
		if(content.getCharge()){
			if(user==null){
				session.setAttribute(request, response, "loginSource", "charge");
				return FrontUtils.showLogin(request, model, site);
			}else{
				//非作者且未购买
				if(!content.getUser().equals(user)){
					//用户已登录判断是否已经购买
					boolean hasBuy=contentBuyMng.hasBuyContent(user.getUserId(), content.getContentId());
					if(!hasBuy){
						try {
							String rediretUrl="/content/buy.jspx?contentId="+content.getContentId();
							if(StringUtils.isNotBlank(site.getContextPath())){
								rediretUrl=site.getContextPath()+rediretUrl;
							}
							response.sendRedirect(rediretUrl);
						} catch (IOException e) {
							//e.printStackTrace();
						}
					}
				}
			}
		}
		String txt = content.getTxtByNo(pageNo);
		// 内容加上关键字
		txt = cmsKeywordMng.attachKeyword(site.getSiteId(), txt);

		Paginable pagination = new SimplePage(pageNo, 1, content.getPageCount());
		model.addAttribute("pagination", pagination);
		FrontUtils.frontPageData(request, model);
		model.addAttribute("content", content);
		model.addAttribute("channel", content.getChannel());
		model.addAttribute("title", content.getTitleByNo(pageNo));
		model.addAttribute("txt", txt);
		model.addAttribute("pic", content.getPictureByNo(pageNo));

		model.addAttribute("q",params);
		Object ext = extContentService.findById(id,content.getModel().getModelPath());
		model.addAttribute("ext",ext);

		FrontUtils.frontData(request, model, site);
		String equipment=(String) request.getAttribute("ua");
		if(StringUtils.isNotBlank(equipment)&&equipment.equals("mobile")){
			return content.getMobileTplContentOrDef(content.getModel());
		}
		return content.getTplContentOrDef(content.getModel());
	}


	private boolean existIndexPage(CmsSite site){
		boolean exist=false;
		if(site.getIsStaticIndex()){
			File indexPage;
			if(site.getIsIndexToRoot()){
				indexPage=new File(realPathResolver.get(INDEX_HTML));
			}else{
				indexPage=new File(realPathResolver.get(site.getStaticDir()+INDEX_HTML));
			}
			if(indexPage.exists()){
				exist=true;
			}
		}
		return exist;
	}

	private String goToIndexPage(HttpServletRequest request,HttpServletResponse response,CmsSite site){
		String equipment=(String) request.getAttribute("ua");
		try {
            // 2017/02/21 将site.getContextPath()为null时，改为""
            String contextPath = site.getContextPath() == null ? "" : site.getContextPath();
			if(site.getIsIndexToRoot()){
				if(StringUtils.isNotBlank(equipment)&&equipment.equals("mobile")){
					response.sendRedirect( contextPath +INDEX_HTML_MOBILE);
				}else{
					response.sendRedirect( contextPath +INDEX_HTML);
				}
			}else{
				response.sendRedirect( contextPath +site.getStaticDir()+INDEX_HTML);
			}
		} catch (IOException e) {
			//e.printStackTrace();
		}
		return FrontUtils.getTplPath(request, site.getSolutionPath(),TPLDIR_INDEX, TPL_INDEX);
	}


	@Resource
	private ChannelMng channelMng;
	@Resource
	private ContentMng contentMng;
	@Resource
	private CmsKeywordMng cmsKeywordMng;
	@Resource
	private RealPathResolver realPathResolver;
	@Resource
	private ContentBuyMng contentBuyMng;
	@Resource(name = "httpSession")
	private SessionProvider session;
	@Resource
	private ExtContentService extContentService;
}
