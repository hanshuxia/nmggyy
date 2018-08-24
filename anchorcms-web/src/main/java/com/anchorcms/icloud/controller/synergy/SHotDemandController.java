package com.anchorcms.icloud.controller.synergy;

import com.anchorcms.cms.service.main.ChannelMng;
import com.anchorcms.cms.service.main.CmsModelMng;
import com.anchorcms.cms.service.main.ContentMng;
import com.anchorcms.cms.service.main.ContentTypeMng;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.common.utils.CmsUtils;
import com.anchorcms.common.utils.FrontUtils;
import com.anchorcms.core.model.CmsSite;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.core.model.MemberConfig;
import com.anchorcms.core.web.WebErrors;
import com.anchorcms.icloud.service.synergy.SHotDemandService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

import static com.anchorcms.common.constants.Constants.TPLDIR_MEMBER;
import static com.anchorcms.common.page.SimplePage.cpn;

@Controller
public class SHotDemandController {
    private static final Logger log = LoggerFactory.getLogger(SDemandController.class);
    public static final String HOTDEMAND_LIST ="tpl.synergyShotdemandList";
    public static final String HOTDEMAND_ADD ="tpl.synergyShotdemandAdd";
    /**
     * @Author 闫浩芮
     * 分页查询热门需求列表
     * @Date 2016/12/23 0023 15:57
     */

    @RequestMapping(value = "/member/admin_hotdemand_list.jspx")
    public String list(Integer UserId, Date releaseDt, Date deadlineDt, Integer demandId, String inquiryTheme,
                       String selectedStatus, String recommendedType,String statusType, Integer modelId,
                       Integer queryChannelId, Integer pageNo,HttpServletResponse response, HttpServletRequest request,
                       ModelMap model) {
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
        Pagination p = synergyCreateService.getPageForMember(queryChannelId, site.getSiteId(), modelId,cpn(pageNo), 20,
                releaseDt,deadlineDt, demandId, inquiryTheme,selectedStatus,recommendedType,statusType,statusType,statusType,statusType);
        model.addAttribute("pagination", p);
        if (modelId != null) {
            model.addAttribute("modelId", modelId);
        }
        if ("1".equals(recommendedType)||recommendedType==null||"".equals(recommendedType)){
            String nextUrl = HOTDEMAND_LIST;
            return FrontUtils.getTplPath(request, site.getSolutionPath(),
                    TPLDIR_MEMBER, nextUrl);
        }else{
            String nextUrl = HOTDEMAND_ADD;
            return FrontUtils.getTplPath(request, site.getSolutionPath(),
                    TPLDIR_MEMBER, nextUrl);
        }

    }
    /**
     * @Author 闫浩芮
     * 更新热门需求新的status
     * @Date 2016/12/27 0027 9:55
     */
    @RequestMapping(value = "/member/admin_hotdemand_update.jspx")
    public String update(Integer UserId, Date releaseDt, Date deadlineDt, Integer demandId, String inquiryTheme,
                         String selectedStatus, String recommendedType,String statusType, Integer modelId,
                         Integer queryChannelId, Integer pageNo,HttpServletResponse response, HttpServletRequest request,
                         ModelMap model) {
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
        //不是管理员不能查看
        if(!user.getIsAdmin()){
            return FrontUtils.showMessage(request, model, "error.noPermissionsView");
        }
        synergyCreateService.update(demandId);
        Pagination p = synergyCreateService.getPageForMember(queryChannelId, site.getSiteId(), modelId,cpn(pageNo), 20,
                releaseDt,deadlineDt, demandId, inquiryTheme,selectedStatus,recommendedType,statusType,statusType,statusType,statusType);
        model.addAttribute("pagination", p);
        if (modelId != null) {
            model.addAttribute("modelId", modelId);
        }
        model.addAttribute("releaseDt", releaseDt);
        model.addAttribute("deadlineDt", deadlineDt);
        model.addAttribute("demandId", demandId);
        model.addAttribute("inquiryTheme", inquiryTheme);
        model.addAttribute("recommendedType",recommendedType);
        if ("1".equals(recommendedType)||recommendedType==null||"".equals(recommendedType)){
            String nextUrl = HOTDEMAND_LIST;
            return FrontUtils.getTplPath(request, site.getSolutionPath(),
                    TPLDIR_MEMBER, nextUrl);
        }else{
            String nextUrl = HOTDEMAND_ADD;
            return FrontUtils.getTplPath(request, site.getSolutionPath(),
                    TPLDIR_MEMBER, nextUrl);
        }
    }
    /**
     * @Author 闫浩芮
     * 批量撤销
     * @Date 2017/2/22 0022 13:27
     */
    @RequestMapping(value = "/member/synergy_hotdemand_rejectMany.jspx")
    public String rejectMany(String demandIdsStr, String nextUrl, Integer channelId, HttpServletRequest request,
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
        synergyCreateService.rejectMany(demandIdsStr);
        return FrontUtils.showSuccess(request, model, nextUrl);
    }
    /**
     * @Author 闫浩芮
     * 批量推荐
     * @Date 2017/2/17 0017 18:44
     */
    @RequestMapping(value = "/member/synergy_hotdemand_passMany.jspx")
    public String passMany(String demandIdsStr, String nextUrl, HttpServletRequest request,
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
        synergyCreateService.passMany(demandIdsStr);
        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    @Resource
    protected ContentMng contentMng;
    @Resource
    private SHotDemandService synergyCreateService;
    @Resource
    private CmsModelMng cmsModelMng;
    @Resource
    private ChannelMng channelMng;
    @Resource
    private ContentTypeMng contentTypeMng;
}
