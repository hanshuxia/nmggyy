package com.anchorcms.icloud.controller.synergy;

import com.anchorcms.cms.model.main.*;
import com.anchorcms.cms.service.main.ChannelMng;
import com.anchorcms.cms.service.main.CmsModelMng;
import com.anchorcms.cms.service.main.ContentTypeMng;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.common.utils.CmsUtils;
import com.anchorcms.common.utils.FrontUtils;
import com.anchorcms.core.model.CmsSite;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.core.model.MemberConfig;
import com.anchorcms.icloud.service.synergy.BrainStormService;
import com.anchorcms.icloud.service.synergy.InteractAreaService;
import com.anchorcms.icloud.service.synergy.SuggestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static com.anchorcms.common.constants.Constants.TPLDIR_CONTENT;
import static com.anchorcms.common.constants.Constants.TPLDIR_MEMBER;
import static com.anchorcms.common.page.SimplePage.cpn;

/**
 * @Author 姜舒中
 * @Email netuser.orz@icloud.com
 * @Date 2017/2/10 0010
 * @Desc
 */
@Controller
public class SuggestionsController {
    private static final Logger log = LoggerFactory.getLogger(InteractAreaController.class);
    public static final String ADD_SUGGESTIONS = "tpl.suggestionAdd";
    public static final String ADD_SUGGESTIONS_BACK = "tpl.suggestionAddBack";
    public static final String SUGGESTIONS_LIST = "tpl.suggestionlistbefore";
    public static final String LIST_SUGGESTIONS = "tpl.suggestionlist";
    public static  final String SUGGESTIONS_VIEW="tpl.suggestionView";
    public static final String SUGGESTIONS_PREVIEW = "tpl.suggestionPreview";
    /**
     * @Author jiangshuzhong
     * @Date 2017/2/13
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @param companyName 公司名称
     * @param title 标题
     * @param status 状态
     * @param modelId
     * @param queryChannelId 查询栏目id
     * @param pageNo 页数
     * @param request
     * @param model
     * @Desc 后台公司公告--列表
     */
    @RequestMapping(value = "/member/suggestion.jspx")
    public String list(Date startDate, Date endDate, String companyName, String title, Byte status, Integer modelId,
                       Integer queryChannelId, Integer pageNo,
                       HttpServletRequest request, ModelMap model) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        MemberConfig mcfg = site.getConfig().getMemberConfig();
        // 没有开启会员功能
        if (!mcfg.isMemberOn()) {
            return FrontUtils.showMessage(request, model, "member.memberClose");
        }
        //没有登录
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
        //获取分页对象，
        Pagination p = interactAreaService.getPageForMember(124, site.getSiteId(), modelId,
                user.getUserId(), cpn(pageNo), 20,startDate,endDate,companyName,title,status);
        model.addAttribute("pagination", p);
        if (modelId != null) {
            model.addAttribute("modelId", modelId);
        }
        //将查询条件放入model中
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("companyName", companyName);
        model.addAttribute("status", status);
        model.addAttribute("title", title);
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, LIST_SUGGESTIONS);
    }
    /**
     * @Author jiangshuzhong
     * @Date 2017/2/13
     * @param request
     * @param response
     * @param model
     * @param modelId modelId
     * @param channelId 栏目id
     * @Desc 后台点击发布意见按钮跳转发布意见页面
     */
    @RequestMapping("/member/suggestion_add.jspx")
    public String backAddSuggestion(HttpServletRequest request, HttpServletResponse response, ModelMap model, Integer modelId, Integer channelId) {
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
        List<Channel> channelList = Channel.getListForSelect(topList, rights, true);
        model.addAttribute("site", site);
        model.addAttribute("channelList", channelList);
        model.addAttribute("sessionId",request.getSession().getId());
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, ADD_SUGGESTIONS_BACK);
    }
    /**
     * @Author jiangshuzhong
     * @Date 2017/2/10
     * @param request
     * @param response
     * @param model
     * @param modelId modelId
     * @param channelId 栏目id
     * @Desc 点击发布意见按钮跳转发布意见页面
     */
    @RequestMapping("/member/front_suggestion_add.jspx")
    public String addSuggestion(HttpServletRequest request, HttpServletResponse response, ModelMap model, Integer modelId, Integer channelId) {
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
        List<Channel> channelList = Channel.getListForSelect(topList, rights, true);
        model.addAttribute("site", site);
        model.addAttribute("channelList", channelList);
        model.addAttribute("sessionId",request.getSession().getId());
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_CONTENT, ADD_SUGGESTIONS);
    }
    /**
     * @Author jsz
     * @Date 2017/2/10
     * @Desc 发布意见--保存
     */
    @RequestMapping("/member/hdzq_suggestion_save.jspx")
    public String saveSuggestion(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                             String title, String author, String detailDesc, String txt,String tagStr,
                             String captcha,Integer channelId, Integer modelId, Short charge) {
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
        Content c = new Content();
        c.setSite(site);
        CmsModel defaultModel=cmsModelMng.getDefModel();
        if(modelId!=null){
            CmsModel m=cmsModelMng.findById(modelId);
            if(m!=null){
                c.setModel(m);
            }else{
                c.setModel(defaultModel);
            }
        }else{
            c.setModel(defaultModel);
        }
        ContentExt ext = new ContentExt();
        ext.setTitle(title);
        ext.setAuthor(user.getUsername());
        ext.setDescription("公司公告");
        ContentTxt t = new ContentTxt();
        t.setTxt(detailDesc);
        ContentType type = contentTypeMng.getDef();
        if (type == null) {
            throw new RuntimeException("Default ContentType not found.");
        }
        Integer typeId = type.getTypeId();
        if(c.getRecommendLevel()==null){
            c.setRecommendLevel((byte) 0);
        }
        c = suggestionService.save(c, ext, t, channelId, typeId,charge, user, true);
        String nextUrl="/yjjy/index.jhtml";
        return FrontUtils.showSuccess(request, model, nextUrl);
    }
    /**
     * @Author jsz
     * @Date 2017/2/13
     * @Desc 后台发布意见--保存
     */
    @RequestMapping("/member/suggestion_save.jspx")
    public String backSaveSuggestion(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                                 String title, String author, String detailDesc, String txt,String tagStr,
                                 String captcha,Integer channelId, Integer modelId, Short charge) {
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
        Content c = new Content();
        c.setSite(site);
        CmsModel defaultModel=cmsModelMng.getDefModel();
        if(modelId!=null){
            CmsModel m=cmsModelMng.findById(modelId);
            if(m!=null){
                c.setModel(m);
            }else{
                c.setModel(defaultModel);
            }
        }else{
            c.setModel(defaultModel);
        }
        ContentExt ext = new ContentExt();
        ext.setTitle(title);
        ext.setAuthor(user.getUsername());
        ext.setDescription("意见建议");
        ContentTxt t = new ContentTxt();
        t.setTxt(detailDesc);
        ContentType type = contentTypeMng.getDef();
        if (type == null) {
            throw new RuntimeException("Default ContentType not found.");
        }
        Integer typeId = type.getTypeId();
        if(c.getRecommendLevel()==null){
            c.setRecommendLevel((byte) 0);
        }
        c = suggestionService.save(c, ext, t, channelId, typeId,charge, user, true);
        String nextUrl="/yjjy/index.jhtml";
        return FrontUtils.showSuccess(request, model, nextUrl);
    }
    /**
     * @Author jiangshuzhong
     * @Date 2017/2/13
     * @param id
     * @param request
     * @param response
     * @param model modelId
     * @param nextUrl
     * @param modelId
     * @param channelId 栏目id
     * @param charge
     * @Desc 后台意见建议--通过
     */
    @RequestMapping(value = "/member/suggestion_pass.jspx")
    public String pass(Integer id, HttpServletRequest request, HttpServletResponse response,
                       ModelMap model, String nextUrl, Integer modelId, Integer channelId, Short charge) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        //获取Content实体类
        Content content = interactAreaService.byContentId(id);
        //更新状态
        interactAreaService.pass(id, channelId, user, charge, true);
        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    @RequestMapping(value = "/member/suggestions_pass.jspx")
    public String pass(String ids, HttpServletRequest request, ModelMap model, String nextUrl, Integer channelId, Short charge) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        //更新状态
        interactAreaService.passTotal(ids, channelId, user, charge, true);
        return FrontUtils.showSuccess(request, model, nextUrl);
    }
    /**
     * @Author jiangshuzhong
     * @Date 2017/2/14
     * @param id 意见id
     * @param request
     * @param response
     * @param model
     * @Desc 后台-意见建议--明细
     */
    @RequestMapping(value = "/member/suggestion_view.jspx")
    public String view(Integer id, HttpServletRequest request,
                       HttpServletResponse response, ModelMap model) {
        String nextUrl = SUGGESTIONS_VIEW;
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
        Content content = interactAreaService.byContentId(id);
        // 获得本站栏目列表
        Set<Channel> rights = user.getGroup().getContriChannels();
        List<Channel> topList = channelMng.getTopList(site.getSiteId(), true);
        List<Channel> channelList = Channel.getListForSelect(topList, rights, true);
        model.addAttribute("content", content);
        model.addAttribute("site", site);
        model.addAttribute("channelList", channelList);
        model.addAttribute("sessionId",request.getSession().getId());
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, nextUrl);
    }
    /**
     * @Author wanjinyou
     * @Date 2017/2/14
     * @Desc 后台-公司公告--预览
     */
    @RequestMapping(value = "/hdzq/suggestion_preview.jspx")
    public String Preview(Integer id, HttpServletRequest request,
                          HttpServletResponse response, ModelMap model) {
        String nextUrl = SUGGESTIONS_PREVIEW;
        CmsSite site = CmsUtils.getSite(request);
        FrontUtils.frontData(request, model, site);
        MemberConfig mcfg = site.getConfig().getMemberConfig();
        // 没有开启会员功能
        if (!mcfg.isMemberOn()) {
            return FrontUtils.showMessage(request, model, "member.memberClose");
        }
        Content content = interactAreaService.byContentId(id);
        model.addAttribute("content", content);
        model.addAttribute("site", site);
        model.addAttribute("sessionId", request.getSession().getId());
        model.addAttribute("channel", channelMng.findById(125));
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, nextUrl);
    }
    /**
     * @author jsz
     * @date 2017/2/14
     * @desc 意见建议删除
     * @param id contentId
     * @param request
     * @param response
     * @param model
     * @param modelId
     * @param channelId
     **/
    @RequestMapping(value = "/member/suggestion_delete.jspx")
    public String delete(Integer id, HttpServletRequest request, HttpServletResponse response,
                         ModelMap model, String nextUrl, Integer modelId, Integer channelId) {
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
        //删除
        brainStormService.delete(id);
        // 获得本站栏目列表
        Set<Channel> rights = user.getGroup().getContriChannels();
        List<Channel> topList = channelMng.getTopList(site.getSiteId(), true);
        List<Channel> channelList = Channel.getListForSelect(topList, rights, true);
        model.addAttribute("site", site);
        model.addAttribute("channelList", channelList);
        model.addAttribute("sessionId", request.getSession().getId());
        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    @RequestMapping(value = "/member/suggestions_delete.jspx")
    public String delete(String ids, String nextUrl, HttpServletRequest request, ModelMap model) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        //删除
        brainStormService.deletes(ids);
        // 获得本站栏目列表
        Set<Channel> rights = user.getGroup().getContriChannels();
        List<Channel> topList = channelMng.getTopList(site.getSiteId(), true);
        List<Channel> channelList = Channel.getListForSelect(topList, rights, true);
        model.addAttribute("site", site);
        model.addAttribute("channelList", channelList);
        model.addAttribute("sessionId", request.getSession().getId());
        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    @Resource
    private ChannelMng channelMng;
    @Resource
    private CmsModelMng cmsModelMng;
    @Resource
    private ContentTypeMng contentTypeMng;
    @Resource
    protected SuggestionService suggestionService;
    @Resource
    protected InteractAreaService interactAreaService;
    @Resource
    protected BrainStormService brainStormService;
}
