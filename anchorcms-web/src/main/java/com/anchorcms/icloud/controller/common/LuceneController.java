package com.anchorcms.icloud.controller.common;

import com.anchorcms.cms.lucene.LuceneContentSvc;
import com.anchorcms.cms.model.main.Channel;
import com.anchorcms.cms.service.main.ChannelMng;
import com.anchorcms.common.utils.CmsUtils;
import com.anchorcms.common.utils.FrontUtils;
import com.anchorcms.common.web.ResponseUtils;
import com.anchorcms.core.model.CmsSite;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.core.model.MemberConfig;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static com.anchorcms.common.constants.Constants.TPLDIR_MEMBER;


@Controller
public class LuceneController {
	private static final Logger log = LoggerFactory.getLogger(com.anchorcms.cms.controller.admin.main.LuceneContentController.class);
	public static final String lucene= "tpl.lucene";

	/**
	 * @Desc 全文检索
	 */
	@RequiresPermissions("lucene:v_index")
	@RequestMapping(value = "/lucene/v_index.jspx")
	public String index(HttpServletRequest request, ModelMap model) {
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
		// 获得本站栏目列表
		Set<Channel> rights = user.getGroup().getContriChannels();
		List<Channel> topList = channelMng.getTopList(site.getSiteId(), true);
		List<Channel> channelList = Channel.getListForSelect(topList, null,
				true);
		model.addAttribute("site", site);
		model.addAttribute("channelList", channelList);
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TPLDIR_MEMBER, lucene);
	}

	/**
	 * @Desc 全文检索
	 */
	@RequiresPermissions("lucene:o_create")
	@RequestMapping(value = "/lucene/o_create.jspx")
	public void create(Integer siteId, Integer channelId, Date startDate,
					   Date endDate, Integer startId, Integer max,
					   HttpServletRequest request, HttpServletResponse response,
					   ModelMap model) throws JSONException {
		try {
			Integer lastId = luceneContentSvc.createIndex(siteId, channelId,
					startDate, endDate, startId, max);
			JSONObject json = new JSONObject();
			json.put("success", true);
			if (lastId != null) {
				json.put("lastId", lastId);
			}
			ResponseUtils.renderJson(response, json.toString());
		} catch (CorruptIndexException e) {
			JSONObject json = new JSONObject();
			json.put("success", false).put("msg", e.getMessage());
			ResponseUtils.renderJson(response, json.toString());
			log.error("", e);
		} catch (LockObtainFailedException e) {
			JSONObject json = new JSONObject();
			json.put("success", false).put("msg", e.getMessage());
			ResponseUtils.renderJson(response, json.toString());
			log.error("", e);
		} catch (IOException e) {
			JSONObject json = new JSONObject();
			json.put("success", false).put("msg", e.getMessage());
			ResponseUtils.renderJson(response, json.toString());
			log.error("", e);
		} catch (ParseException e) {
			JSONObject json = new JSONObject();
			json.put("success", false).put("msg", e.getMessage());
			ResponseUtils.renderJson(response, json.toString());
			log.error("", e);
		}
	}

	@Autowired
	private LuceneContentSvc luceneContentSvc;

	@Autowired
	private ChannelMng channelMng;
}
