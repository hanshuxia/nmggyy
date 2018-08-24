package com.anchorcms.icloud.controller.staticpage;

import com.anchorcms.cms.model.main.Channel;
import com.anchorcms.cms.service.main.ChannelMng;
import com.anchorcms.cms.staticpage.StaticPageSvc;
import com.anchorcms.common.utils.CmsUtils;
import com.anchorcms.common.utils.FrontUtils;
import com.anchorcms.common.web.ResponseUtils;
import com.anchorcms.core.model.CmsSite;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.core.model.MemberConfig;
import com.anchorcms.icloud.service.staticpage.StaticpageService;
import freemarker.template.TemplateException;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import static com.anchorcms.common.constants.Constants.TPLDIR_MEMBER;

/**
 * Created by jxd on 2017/2/21.
 */
@Controller
public class StaticpageController {
    private static final Logger log = LoggerFactory.getLogger(StaticpageController.class);
    public static final String content = "tpl.staticpage_content";
    public static final String index = "tpl.staticpage_index";


    @RequiresPermissions("staticpage:v_index")
    @RequestMapping(value = "/staticpage/v_index.jspx")
    public String indexInput(HttpServletRequest request, ModelMap model) {
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
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, index);
    }

    @RequiresPermissions("staticpage:o_index")
    @RequestMapping(value = "/staticpage/o_index.jspx")
    public void indexSubmit(HttpServletRequest request,
                            HttpServletResponse response, ModelMap model) throws JSONException {
        JSONObject json = new JSONObject();
        try {
            CmsSite site = CmsUtils.getSite(request);
            boolean staticRequired = true;
            if (!site.getIsStaticIndex()) {
                staticRequired = false;
                ResponseUtils.renderJson(response, "{\"success\":false,\"no\":true}");
            }
            if (staticRequired) {
                staticPageSvc.index(site);
                ResponseUtils.renderJson(response, "{\"success\":true}");
            }
        } catch (IOException e) {
            log.error("static index error!", e);
            json.put("success", false);
            json.put("msg", e.getMessage());
            ResponseUtils.renderJson(response, json.toString());
        } catch (TemplateException e) {
            log.error("static index error!", e);
            json.put("success", false);
            json.put("msg", e.getMessage());
            ResponseUtils.renderJson(response, json.toString());
        }
    }

    @RequiresPermissions("staticpage:o_index_remove")
    @RequestMapping(value = "/staticpage/o_index_remove.jspx")
    public void indexRemove(HttpServletRequest request,
                            HttpServletResponse response) throws JSONException {
        CmsSite site = CmsUtils.getSite(request);
        JSONObject json = new JSONObject();
        if (staticPageSvc.deleteIndex(site)) {
            json.put("success", true);
        } else {
            json.put("success", false);
        }
        ResponseUtils.renderJson(response, json.toString());
    }


    @RequiresPermissions("staticpage:v_content")
    @RequestMapping(value = "/staticpage/v_content.jspx")
    public String contentInput(HttpServletRequest request, ModelMap model) {
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
        // 栏目列表
        // List<Channel> topList = channelMng.getTopList(site.getSiteId(), true);
        List<Channel> channelList = Channel.getListForSelect(channelMng.getTopList(site.getSiteId(), true), null,
                null, true);
        model.addAttribute("channelList", channelList);
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, content);
    }

    @RequiresPermissions("staticpage:o_content")
    @RequestMapping(value = "/staticpage/o_content.jspx")
    public void contentSubmit(Integer channelId, Date startDate,
                              HttpServletRequest request, HttpServletResponse response) {
        String msg = "";
        try {
            Integer siteId = null;
            boolean staticRequired = true;
            if (channelId == null) {
                // 没有指定栏目，则需指定站点
                siteId = CmsUtils.getSiteId(request);
            } else {
                Channel c = channelMng.findById(channelId);
                if (c != null && (!StringUtils.isBlank(c.getLink()) || !c.getStaticContent())) {
                    msg = "{\"success\":false,\"no\":true}";
                    staticRequired = false;
                }
            }
            if (staticRequired) {
                int count = staticPageSvc.content(siteId, channelId, startDate,null);
                msg = "{\"success\":true,\"count\":" + count + "}";
            }
        } catch (IOException e) {
            e.printStackTrace();
            log.error("static channel error!", e);
            msg = "{\"success\":false,\"msg\":'" + e.getMessage() + "'}";
        } catch (TemplateException e) {
            log.error("static channel error!", e);
            e.printStackTrace();
            msg = "{\"success\":false,\"msg\":'" + e.getMessage() + "'}";
        }
        ResponseUtils.renderJson(response, msg);
    }



    @RequiresPermissions("staticpage:o_contentAdd")
    @RequestMapping(value = "/staticpage/o_contentAdd.jspx")
    public void contentSubmitAdd(Integer channelId, Date startDate,
                              HttpServletRequest request, HttpServletResponse response) {
        String msg="";
        String isAdd = "1";
        try {
            Integer siteId = null;
            boolean staticRequired=true;
            if (channelId != null) {
                Channel c=channelMng.findById(channelId);
                if(c!=null&&(!StringUtils.isBlank(c.getLink()) || !c.getStaticContent())){
                    msg = "{\"success\":false,\"no\":true}";
                    staticRequired=false;
                }
            }else{
                // 没有指定栏目，则需指定站点
                siteId = CmsUtils.getSiteId(request);
            }
            if(staticRequired){
                int count = staticPageSvc.content(siteId, channelId, startDate,isAdd);
                msg = "{\"success\":true,\"count\":" + count + "}";
            }
        } catch (IOException e) {
            log.error("static channel error!", e);
            e.printStackTrace();
            msg = "{\"success\":false,\"msg\":'" + e.getMessage() + "'}";
        } catch (TemplateException e) {
            log.error("static channel error!", e);
            e.printStackTrace();
            msg = "{\"success\":false,\"msg\":'" + e.getMessage() + "'}";
        }
        ResponseUtils.renderJson(response, msg);
    }


    @Resource
    private ChannelMng channelMng;
    @Resource
    private StaticPageSvc staticPageSvc;

}
