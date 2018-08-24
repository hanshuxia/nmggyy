package com.anchorcms.icloud.controller.synergy;

import com.anchorcms.cms.model.main.Channel;
import com.anchorcms.cms.model.main.Content;
import com.anchorcms.cms.service.main.ChannelMng;
import com.anchorcms.cms.service.main.ContentMng;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.common.utils.CmsUtils;
import com.anchorcms.common.utils.FrontUtils;
import com.anchorcms.core.model.CmsSite;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.core.model.MemberConfig;
import com.anchorcms.core.web.WebErrors;
import com.anchorcms.icloud.service.synergy.AskHelpService;
import com.anchorcms.icloud.service.synergy.BrainStormService;
import com.anchorcms.icloud.service.synergy.InteractAreaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static com.anchorcms.common.constants.Constants.TPLDIR_MEMBER;
import static com.anchorcms.common.page.SimplePage.cpn;

/**
 * @Author yhr
 * @Email netuser.orz@icloud.com
 * @Date 2017/2/8
 * @Desc 首页-互动专区-问题求助
 */
@Controller
public class AskandHelpController {
    public static final String ASKHELP_LIST ="tpl.askHelpList";
    public static final String ASKHELP_PREVIEW="tpl.askHelpPreview";
    public static final String ASKHELP_VIEW="tpl.askHelpView";
    /**
     * @Author 闫浩芮
     * 分页查询问题求助信息列表
     * @Date 2016/12/23 0023 15:57
     */
    @RequestMapping(value = "/member/ask_help_list.jspx")
    public String list(Date startDate, Date endDate, String title, String author, Byte status, Integer modelId, Integer pageNo,
                       Integer queryChannelId,HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        String nextUrl =ASKHELP_LIST;
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        MemberConfig mcfg = site.getConfig().getMemberConfig();
        WebErrors errors = WebErrors.create(request);
        // 没有开启会员功能
        if (!mcfg.isMemberOn()) {
            return FrontUtils.showMessage(request, model, "member.memberClose");
        }
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
        //不是管理员不能查看
        if(!user.getIsAdmin()){
            errors.addErrorCode("error.noPermissionsView");
            return FrontUtils.showError(request,response,model,errors);
        }
        //获取分页对象，
        Pagination p = synergyCreateService.getPageForMember(queryChannelId, site.getSiteId(), modelId,
                user.getUserId(), cpn(pageNo), 20,startDate,endDate,author,title,status);
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
                TPLDIR_MEMBER, nextUrl);

    }

    /**
     * @Author yhr
     * @Date 2017/2/9
     * @Desc 后台问题求助--通过
     */
    @RequestMapping(value = "/member/ask_help_pass.jspx")
    public String pass(Integer id, HttpServletRequest request, HttpServletResponse response,String nextUrl,
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
     * @Author yhr
     * @Date 2017/2/14
     * @Desc 后台-问题求助--明细
     */
    @RequestMapping(value = "/member/ask_help_view.jspx")
    public String view(Integer contentId, HttpServletRequest request,
                       HttpServletResponse response, ModelMap model) {
        String nextUrl = ASKHELP_VIEW;
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
        Content content = interactAreaService.byContentId(contentId);
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
     * @author yhr
     * @date 2017/2/14
     * @desc 删除问题求助
     * @param id contentId
     * @param request
     * @param response
     * @param model
     * @param modelId
     * @param channelId
     **/
    @RequestMapping(value = "/hdzq/ask_help_delete.jspx")
    public String delete(Integer id, HttpServletRequest request, HttpServletResponse response,
                         ModelMap model,String nextUrl, Integer modelId, Integer channelId) {
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
     * 管理员置为删除问题求助
     * @Author yhr
     * @param channelId 栏目ID
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/member/synergy_content_deleteMany.jspx")
    public String rejectMany(String ids,String nextUrl,Integer channelId,HttpServletRequest request,
                             HttpServletResponse response, ModelMap model) {
        String statusType = "3";
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
        model.addAttribute("statusType", statusType);
        return FrontUtils.showSuccess(request, model, nextUrl);
    }
    /**
     * @Author 闫浩芮
     * 置为通过
     * @Date 2017/2/17 0017 18:44
     */
    @RequestMapping(value = "/member/synergy_content_passMany.jspx")
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
    protected BrainStormService brainStormService;
    @Resource
    private ChannelMng channelMng;
    @Resource
    protected ContentMng contentMng;
    @Resource
    private AskHelpService synergyCreateService;
    @Resource
    protected InteractAreaService interactAreaService;

}
