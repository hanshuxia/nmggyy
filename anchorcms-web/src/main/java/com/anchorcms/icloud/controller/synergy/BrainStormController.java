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
import com.anchorcms.icloud.service.synergy.AskHelpService;
import com.anchorcms.icloud.service.synergy.BrainStormService;
import com.anchorcms.icloud.service.synergy.InteractAreaService;
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
import static com.anchorcms.common.constants.Constants.TPLDIR_MEMBER;
import static com.anchorcms.common.page.SimplePage.cpn;

/**
 * @Author maocheng
 * @Email netuser.orz@icloud.com
 * @Date 2017/2/10
 * @Desc 首页-互动专区
 */
@Controller
public class BrainStormController {
    private static final Logger log = LoggerFactory.getLogger(BrainStormController.class);
    public static final String BRAIN_STORM_PREVIEW = "tpl.brainStormPreview";
    public static final String BRAIN_STORM_ADD = "tpl.brainStormAdd";
    public static final String BRAIN_STORM_LIST = "tpl.brainStormList";
    public static final String BRAIN_STORM_VIEW = "tpl.brainStormView";
    public static final String NEWS_BRAIN_STORM_ADD = "tpl.NewsBtainStormAdd";

    /**
     * @Author maocheng
     * @Date 2017/2/13
     * @Desc 后台公司头脑风暴--列表
     */
    @RequestMapping(value = "/member/brain_storm_list.jspx")
    public String list(Date startDate, Date endDate, String author, String title, Byte status, Integer modelId,
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
        Pagination p = brainStormService.getPageForMember(123, site.getSiteId(), modelId,
                user.getUserId(), cpn(pageNo), 20,author,title,status);
        model.addAttribute("pagination", p);
        if (modelId != null) {
            model.addAttribute("modelId", modelId);
        }
        //将查询条件放入model中
//        model.addAttribute("startDate", startDate);
//        model.addAttribute("endDate", endDate);
        model.addAttribute("status",status);
        model.addAttribute("author", author);
        model.addAttribute("title", title);
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, BRAIN_STORM_LIST);
    }

    /**
     * @Author maocheng
     * @Date 2017/2/10
     * @Desc 前台发布头脑风暴
     */
    @RequestMapping("/member/front_brain_storm_add.jspx")
    public String addNotice(HttpServletRequest request, HttpServletResponse response, ModelMap model,Integer modelId, Integer channelId) {
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
                TPLDIR_CONTENT, BRAIN_STORM_ADD);
    }

    /**
     * @Author maocheng
     * @Date 2017/2/10
     * @Desc 后台发布头脑风暴
     */
    @RequestMapping("/member/brain_storm_add.jspx")
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
                TPLDIR_CONTENT, NEWS_BRAIN_STORM_ADD);
    }

    /**
     * @Author maocheng
     * @Date 2017/2/10
     * @Desc 头脑风暴公告--保存
     */
    @RequestMapping("/member/brain_storm_save.jspx")
    public String saveNotice(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                             String title, String nextUrl, String detailDesc, String txt,String tagStr,
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
        ext.setDescription("头脑风暴");
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
        c = brainStormService.save(c, ext, t, channelId, typeId,charge, user, true);
        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    /**
     * @author maocheng
     * @date 2017/2/14
     * @desc 删除头脑风暴
     * @param id contentId
     * @param request
     * @param response
     * @param model
     * @param modelId
     * @param channelId
     **/
    @RequestMapping(value = "/member/brain_storm_delete.jspx")
    public String delete(Integer id,String nextUrl, HttpServletRequest request, HttpServletResponse response,
                         ModelMap model, Integer modelId, Integer channelId) {
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

    /**
     * @Author maocheng
     * @Date 2017/2/14
     * @Desc 后台头脑风暴--通过
     */
    @RequestMapping(value = "/member/brain_storm_pass.jspx")
    public String pass(Integer id, HttpServletRequest request,String nextUrl,
                       HttpServletResponse response,
                       ModelMap model, Integer modelId, Integer channelId, Short charge) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        //获取Content实体类
        Content content = interactAreaService.byContentId(id);
        //更新状态
        interactAreaService.pass(id, channelId, user, charge, true);
        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    /**
     * @Author maocheng
     * @Date 2017/2/14
     * @Desc 后台-头脑风暴--明细
     */
    @RequestMapping(value = "/member/brain_storm_view.jspx")
    public String view(Integer id, HttpServletRequest request,
                       HttpServletResponse response, ModelMap model) {
        String nextUrl = BRAIN_STORM_VIEW;
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
     * @Author maocheng
     * @Date 2017/2/14
     * @Desc 后台-头脑风暴--预览
     */
    @RequestMapping(value = "/hdzq/brain_storm_preview.jspx")
    public String Preview(Integer id, HttpServletRequest request,
                          HttpServletResponse response, ModelMap model) {
        String nextUrl = BRAIN_STORM_PREVIEW;
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
     * 管理员置为删除问题求助
     * @Author yhr
     * @param channelId 栏目ID
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/member/synergy_brain_deleteMany.jspx")
    public String rejectMany(String ids,String nextUrl,Integer channelId,HttpServletRequest request,
                             HttpServletResponse response, ModelMap model) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
        //非管理员不能修改
        if (!user.getIsAdmin()) {
            return FrontUtils.showMessage(request, model, "error.noPermission");
        }
        //业务数据更新处理
        synergyCreateService.deleteMany(ids);
        return FrontUtils.showSuccess(request, model, nextUrl);
    }
    /**
     * @Author 闫浩芮
     * 置为通过
     * @Date 2017/2/17 0017 18:44
     */
    @RequestMapping(value = "/member/synergy_brain_passMany.jspx")
    public String passMany(String ids,String nextUrl,HttpServletRequest request,
                           HttpServletResponse response, ModelMap model) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
        //非管理员不能修改
        if (!user.getIsAdmin()) {
            return FrontUtils.showMessage(request, model, "error.noPermission");
        }
        //业务数据更新处理
        synergyCreateService.passMany(ids);
        return FrontUtils.showSuccess(request, model, nextUrl);
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
    protected BrainStormService brainStormService;
    @Resource
    protected InteractAreaService interactAreaService;
    @Resource
    private AskHelpService synergyCreateService;
}
