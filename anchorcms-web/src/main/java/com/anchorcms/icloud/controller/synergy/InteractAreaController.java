package com.anchorcms.icloud.controller.synergy;

import com.anchorcms.cms.model.main.*;
import com.anchorcms.cms.service.main.ChannelMng;
import com.anchorcms.cms.service.main.CmsModelMng;
import com.anchorcms.cms.service.main.ContentMng;
import com.anchorcms.cms.service.main.ContentTypeMng;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.common.utils.CmsUtils;
import com.anchorcms.common.utils.FrontUtils;
import com.anchorcms.common.web.session.SessionProvider;
import com.anchorcms.core.model.CmsSite;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.core.model.MemberConfig;
import com.anchorcms.core.web.WebErrors;
import com.anchorcms.icloud.service.synergy.InteractAreaService;
import com.octo.captcha.service.CaptchaServiceException;
import com.octo.captcha.service.image.ImageCaptchaService;
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
import static com.anchorcms.common.constants.Constants.TPLDIR_CHANNEL;
import static com.anchorcms.common.constants.Constants.TPLDIR_MEMBER;
import static com.anchorcms.common.page.SimplePage.cpn;

/**
 * @Author wanjinyou
 * @Email netuser.orz@icloud.com
 * @Date 2017/2/8
 * @Desc 首页-互动专区
 */
@Controller
public class InteractAreaController {
    private static final Logger log = LoggerFactory.getLogger(InteractAreaController.class);

    public static final String COMPANY_NOTICE = "tpl.companyNotice";
    public static final String ASK_HELP = "tpl.askForHelp";
    public static final String BRAIN_STORM = "tpl.brainStorm";
    public static final String COMMENTS_SUGGESTIONS = "tpl.commentsSuggestions";
    public static final String COMPANY_NOTICE_PREVIEW = "tpl.companyNoticePreview";
    public static final String COMPANY_NOTICE_ADD = "tpl.companyNoticeAdd";
    public static final String ASK_HELP_PREVIEW = "tpl.askHelpPreview";
    public static final String ASK_HELP_ADD = "tpl.askHelpAdd";
    public static final String ASK_HELP_ADD1="tpl.askHelpAdd1";
    public static  final String COMPANY_NOTICE_LIST="tpl.companyNoticeList";
    public static  final String COMPANY_NOTICE_VIEW="tpl.companyNoticeView";
    public static final String NEWS_COMPANY_NOTICE_ADD = "tpl.NewsCompanyNoticeAdd";
    public static final String ABOUT_US = "tpl.AboutUs";

    /***
     * @Author wanjinyou
     * @param request  request对象
     * @param response response对象
     * @param model model对象
     * @param modelId modelId
     * @param channelId 栏目id
     * @Date 2017/2/14
     * @Desc 后台--发布公告
     * @return
     */
    @RequestMapping("/member/company_notice_add.jspx")
    public String add(HttpServletRequest request, HttpServletResponse response, ModelMap model,Integer modelId, Integer channelId) {
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
                TPLDIR_MEMBER, NEWS_COMPANY_NOTICE_ADD);
    }

    /**
     * @Author 张文文
     * @Date 2017/2/8
     * @param request  request对象
     * @param response response对象
     * @param model model对象
     * @Desc 点击问题名称跳转问题详情页面
     */
    @RequestMapping("/hdzq/ask_help_preview.jspx")
    public String AskHelpPreview(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        CmsSite site = CmsUtils.getSite(request);
        FrontUtils.frontData(request, model, site);
        MemberConfig mcfg = site.getConfig().getMemberConfig();
        // 没有开启会员功能
        if (!mcfg.isMemberOn()) {
            return FrontUtils.showMessage(request, model, "member.memberClose");
        }
        model.addAttribute("site", site);
        model.addAttribute("sessionId",request.getSession().getId());
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_CONTENT, ASK_HELP_PREVIEW);
    }

    /**
     * @Author 张文文
     * @Date 2017/2/8
     * @param request  request对象
     * @param response response对象
     * @param model model对象
     * @Desc 点击提出问题按钮跳转发布问题页面
     */
    @RequestMapping("/member/ask_help_add.jspx")
    public String AskHelpAdd(String canshu,HttpServletRequest request, HttpServletResponse response, ModelMap model) {
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
        model.addAttribute("site", site);
        model.addAttribute("sessionId",request.getSession().getId());
        if("1".equals(canshu)){
            return FrontUtils.getTplPath(request, site.getSolutionPath(),
                    TPLDIR_CONTENT, ASK_HELP_ADD1);
        }else{
            return FrontUtils.getTplPath(request, site.getSolutionPath(),
                    TPLDIR_CONTENT, ASK_HELP_ADD);
        }

    }

    /***
     * @Author wanjinyou
     * @Date 2017/2/9
     * @param request request对象
     * @param response response对象
     * @param model model对象
     * @param title title
     * @param author 作者
     * @param nextUrl 跳转列表
     * @param detailDesc 详细描述
     * @param txt txt
     * @param tagStr
     * @param captcha
     * @param channelId 栏目id
     * @param modelId modelId
     * @param charge charge
     * @Desc 互动专区--保存
     * @return
     */
    @RequestMapping("/member/company_notice_save.jspx")
    public String backSaveSuggestion(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                                     String title, String author,String nextUrl, String detailDesc, String txt,String tagStr,
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
        c = interactAreaService.save(c, ext, t, channelId, typeId,charge, user, true);
        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    /**
     * @Author wangnan
     * @Date 2017/2/20
     * @param request request对象
     * @param response response对象
     * @param model model对象
     * @param title title
     * @param author 作者
     * @param nextUrl 跳转列表
     * @param detailDesc 详细描述
     * @param txt txt
     * @param tagStr
     * @param captcha
     * @param channelId 栏目id
     * @param modelId modelId
     * @param charge charge
     * @Desc 互动专区--系统公告保存
     */
    @RequestMapping("/member/company_system_notice_save.jspx")
    public String backNoticeSaveSuggestion(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                                           String title, String author,String nextUrl, String detailDesc, String txt,String tagStr,
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
        c = interactAreaService.save(c, ext, t, channelId, typeId,charge, user, true);
        //获取Content实体类
        Content content = interactAreaService.byContentId(c.getContentId());
        //更新状态
        interactAreaService.pass(content.getContentId(), channelId, user, charge, true);
        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    /***
     * @Author wanjinyou
     * @Date 2017/2/9
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @param companyName 公司名称
     * @param title 标题title
     * @param status 状态
     * @param modelId modelId
     * @param queryChannelId 栏目id
     * @param pageNo 页码
     * @param request request对象
     * @param response response对象
     * @param model model对象
     * @Desc 后台--公司公告--列表
     * @return
     */
    @RequestMapping(value = "/member/company_notice_list.jspx")
    public String list(Date startDate, Date endDate, String companyName,String title, Byte status, Integer modelId,
                       Integer queryChannelId, Integer pageNo,
                       HttpServletRequest request,HttpServletResponse response, ModelMap model) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        MemberConfig mcfg = site.getConfig().getMemberConfig();
        WebErrors errors = WebErrors.create(request);
        // 没有开启会员功能
        if (!mcfg.isMemberOn()) {
            return FrontUtils.showMessage(request, model, "member.memberClose");
        }
        //没有登录
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
        //不是管理员不能查看
        if(!user.getIsAdmin()){
            errors.addErrorCode("error.noPermissionsView");
            return FrontUtils.showError(request,response,model,errors);
        }
        //获取分页对象，
        Pagination p = interactAreaService.getPageForMember(125, site.getSiteId(), modelId,
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
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, COMPANY_NOTICE_LIST);
    }

    /***
     * @Author wanjinyou
     * @Date 2017/2/9
     * @param id 主键id
     * @param request request对象
     * @param response response对象
     * @param model model
     * @param modelId modelId
     * @param channelId 栏目id
     * @param charge charge
     * @Desc 后台公司公告--通过
     * @return
     */
    @RequestMapping(value = "/member/company_notice_pass.jspx")
    public String pass(Integer id, HttpServletRequest request, HttpServletResponse response,
                         ModelMap model, Integer modelId, Integer channelId, Short charge) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        //获取Content实体类
        Content content = interactAreaService.byContentId(id);
        //更新状态
        interactAreaService.pass(id, channelId, user, charge, true);
        String nextUrl="/member/company_notice_list.jspx";
        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    /**
     * @Author wanjinyou
     * @Date 2017/2/14
     * @param id 主键id
     * @param request request对象
     * @param response response对象
     * @param model model
     * @Desc 后台-公司公告--明细
     */
    @RequestMapping(value = "/member/company_notice_view.jspx")
    public String view(Integer id, HttpServletRequest request,
                       HttpServletResponse response, ModelMap model) {
        String nextUrl = COMPANY_NOTICE_VIEW;
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
     * @param id 主键id
     * @param request request对象
     * @param response response对象
     * @param model model
     * @Desc 后台-公司公告--预览
     */
    @RequestMapping(value = "/hdzq/company_notice_preview.jspx")
    public String Preview(Integer id, HttpServletRequest request,
                             HttpServletResponse response, ModelMap model) {
        String nextUrl = COMPANY_NOTICE_PREVIEW;
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
     * @author wanjinyou
     * @date 2017/2/14
     * @param id 主键id
     * @param request request对象
     * @param response response对象
     * @param model model
     * @param channelId 栏目id
     * @desc 后台-公司公告--删除
     **/
    @RequestMapping(value = "/member/company_notice_delete.jspx")
    public String delete(Integer id, HttpServletRequest request, HttpServletResponse response,
                         ModelMap model, Integer modelId, Integer channelId) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        String nextUrl="/member/company_notice_list.jspx";
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
        interactAreaService.delete(id);
        // 获得本站栏目列表
        Set<Channel> rights = user.getGroup().getContriChannels();
        List<Channel> topList = channelMng.getTopList(site.getSiteId(), true);
        List<Channel> channelList = Channel.getListForSelect(topList, rights, true);
        model.addAttribute("site", site);
        model.addAttribute("channelList", channelList);
        model.addAttribute("sessionId", request.getSession().getId());
        return FrontUtils.showSuccess(request, model, nextUrl);
    }


    private WebErrors validateSave(String title, String author,
                                   String description, String txt, String tagStr, Integer channelId,
                                   CmsSite site, CmsUser user, String captcha,
                                   HttpServletRequest request, HttpServletResponse response) {
        WebErrors errors = WebErrors.create(request);
        try {
            if (!imageCaptchaService.validateResponseForID(session
                    .getSessionId(request, response), captcha)) {
                errors.addErrorCode("error.invalidCaptcha");
                return errors;
            }
        } catch (CaptchaServiceException e) {
            errors.addErrorCode("error.exceptionCaptcha");
            return errors;
        }
        if (errors.ifBlank(title, "title", 150)) {
            return errors;
        }
        if (errors.ifMaxLength(author, "author", 100)) {
            return errors;
        }
        if (errors.ifMaxLength(description, "description", 255)) {
            return errors;
        }
        // 内容不能大于1M
        if (errors.ifBlank(txt, "txt", 1048575)) {
            return errors;
        }
        if (errors.ifMaxLength(tagStr, "tagStr", 255)) {
            return errors;
        }
        if (errors.ifNull(channelId, "channelId")) {
            return errors;
        }
        if (vldChannel(errors, site, user, channelId)) {
            return errors;
        }
        return errors;
    }

    private boolean vldChannel(WebErrors errors, CmsSite site, CmsUser user,
                               Integer channelId) {
        Channel channel = channelMng.findById(channelId);
        if (errors.ifNotExist(channel, Channel.class, channelId)) {
            return true;
        }
        if (!channel.getSite().getSiteId().equals(site.getSiteId())) {
            errors.notInSite(Channel.class, channelId);
            return true;
        }
        if (!channel.getContriGroups().contains(user.getGroup())) {
            errors.noPermission(Channel.class, channelId);
            return true;
        }
        return false;
    }

    /**
     * @Author 张文文
     * @Date 2017/2/23
     * @param request request对象
     * @param response response对象
     * @param model model
     * @Desc 打开关于工业云页面
     */
    @RequestMapping("/channel/aboutUs.jspx")
    public String AboutUs(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        CmsSite site = CmsUtils.getSite(request);
        FrontUtils.frontData(request, model, site);
        MemberConfig mcfg = site.getConfig().getMemberConfig();
        // 没有开启会员功能
        if (!mcfg.isMemberOn()) {
            return FrontUtils.showMessage(request, model, "member.memberClose");
        }
        model.addAttribute("site", site);
        model.addAttribute("sessionId",request.getSession().getId());
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_CHANNEL, ABOUT_US);
    }



    @Resource
    protected ContentMng contentMng;
    @Resource
    private CmsModelMng cmsModelMng;
    @Resource
    private ChannelMng channelMng;
    @Resource
    private ContentTypeMng contentTypeMng;
    @Resource
    protected SessionProvider session;
    @Resource
    protected ImageCaptchaService imageCaptchaService;
    @Resource
    protected InteractAreaService interactAreaService;
}
