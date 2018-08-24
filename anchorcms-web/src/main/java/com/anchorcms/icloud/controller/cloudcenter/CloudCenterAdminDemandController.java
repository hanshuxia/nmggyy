package com.anchorcms.icloud.controller.cloudcenter;

import com.anchorcms.cms.service.main.ContentMng;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.common.utils.CmsUtils;
import com.anchorcms.common.utils.FrontUtils;
import com.anchorcms.core.model.CmsSite;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.core.model.MemberConfig;
import com.anchorcms.core.web.WebErrors;
import com.anchorcms.icloud.service.cloudcenter.CloudCenterAdminDemandService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import static com.anchorcms.common.constants.Constants.TPLDIR_MEMBER;
import static com.anchorcms.common.page.SimplePage.cpn;

@Controller
public class CloudCenterAdminDemandController {
    private static final Logger log = LoggerFactory.getLogger(CloudCenterAdminDemandController.class);
    public static final String DEMAND_LIST = "tpl.cloudCenterDemandList";

    /**
     * 管理员--云需求管理界面
     * @param demandId 询价编号
     * @param demandName 询价主题
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @param serverType 所属分类
     * @param modelId
     * @param queryChannelId
     * @param pageNo
     * @param request
     * @param response
     * @param model
     * @param status
     * @Author maocheng
     * @return
     */
    @RequestMapping(value = "/member/cloudcenter_admin_demand_list.jspx")
    public String list(String demandId, String demandName, Date startDate, Date endDate, String serverType,
                       Integer modelId, Integer queryChannelId, Integer pageNo, HttpServletRequest request, HttpServletResponse response, ModelMap model, String status) {
        String nextUrl = DEMAND_LIST;
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
        //非管理员 无权查看
        if (!user.getIsAdmin()) {
            errors.addErrorCode("error.noPermissionsView");
            return FrontUtils.showError(request, response, model, errors);
        }
        Pagination p = CloudCenterAdminDemandService.getPageForMember(queryChannelId, site.getSiteId(), modelId, user.getUserId(), cpn(pageNo), 20, demandId, demandName, startDate, endDate, serverType, status);
        model.addAttribute("pagination", p);
        if (modelId != null) {
            model.addAttribute("modelId", modelId);
        }
        //将查询条件放入model中
        model.addAttribute("demandId", demandId);
        model.addAttribute("demandName", demandName);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("serverType", serverType);
        model.addAttribute("status", status);
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, nextUrl);
    }

    /**
     * @param id
     * @param request
     * @param response
     * @param model
     * @param modelId
     * @param channelId
     * @param charge
     * @return
     * @auther lilimin
     * 云需求状态改变
     */
    @RequestMapping(value = "/member/demand_controller.jspx")
    public String manageAdmin(Integer id, HttpServletRequest request, HttpServletResponse response,
                              ModelMap model, String states, String status,
                              Integer modelId, Integer channelId, Short charge,String backReason) throws Exception {
        if(!StringUtils.isBlank(backReason)){
        backReason=java.net.URLDecoder.decode(backReason, "utf-8");}
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        String nextUrl = "/member/cloudcenter_admin_demand_list.jspx?&status=" + status;
        //更新申请状态
        CloudCenterAdminDemandService.updateState(id, states, channelId, user, charge, true,backReason);

        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    /**
     * @param id
     * @param request
     * @param response
     * @param model
     * @param modelId
     * @param channelId
     * @param charge
     * @return
     * @auther lilimin
     * 云需求状态改变
     */
    @RequestMapping(value = "/member/demandUser_controller.jspx")
    public String manage(Integer id, HttpServletRequest request, HttpServletResponse response,
                         ModelMap model, String states,
                         Integer modelId, Integer channelId, Short charge) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        String nextUrl = "/member/cloudcenter_demand_list.jspx";
        //更新申请状态
        CloudCenterAdminDemandService.updateState(id, states, channelId, user, charge, true ,"");

        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    /**
     * 管理员置为驳回需求
     * @param  demandIdsStr 需要置为通过的Id字符串
     * @param  nextUrl  执行置为通过后的返回地址
     * @param channelId 栏目ID
     * @param request
     * @param response
     * @param model
     * @return
     * @Author yhr
     */
    @RequestMapping(value = "/member/synergy_demand_rejectManyIcloudDemand.jspx")
    public String rejectMany(String demandIdsStr, String nextUrl, Integer channelId, HttpServletRequest request,
                             HttpServletResponse response, ModelMap model, String backReason) throws UnsupportedEncodingException {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        if(!StringUtils.isBlank(backReason))
        backReason=java.net.URLDecoder.decode(backReason, "utf-8");
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
        //非管理员不能修改
        if (!user.getIsAdmin()) {
            return FrontUtils.showMessage(request, model, "error.noPermission");
        }
        //业务数据更新处理
        CloudCenterAdminDemandService.rejectMany(demandIdsStr,backReason);
        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    /**
     *管理员置为通过需求
     * @Author 闫浩芮
     * @param  demandIdsStr 需要置为通过的Id字符串
     * @param  nextUrl  执行置为通过后的返回地址
     * @param request
     * @param response
     * @param model
     * @Date 2017/2/17 0017 18:44
     */
    @RequestMapping(value = "/member/synergy_demand_passManyIcloudDemand.jspx")
    public String passMany(String demandIdsStr, String nextUrl, HttpServletRequest request,
                           HttpServletResponse response, ModelMap model) {
        String status = "3";
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
        CloudCenterAdminDemandService.passMany(demandIdsStr);
        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    @Resource
    protected ContentMng contentMng;
    @Resource
    private CloudCenterAdminDemandService CloudCenterAdminDemandService;
}
