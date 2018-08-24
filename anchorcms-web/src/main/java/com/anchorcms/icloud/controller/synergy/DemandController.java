package com.anchorcms.icloud.controller.synergy;

import com.anchorcms.cms.service.main.ContentMng;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.common.utils.CmsUtils;
import com.anchorcms.common.utils.FrontUtils;
import com.anchorcms.core.model.CmsSite;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.core.model.MemberConfig;
import com.anchorcms.core.web.WebErrors;
import com.anchorcms.icloud.service.synergy.DemandService;
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
public class DemandController {
    private static final Logger log = LoggerFactory.getLogger(DemandController.class);
    public static final String DEMAND_LIST = "tpl.adminsdemandList";

    /**
     * 获得文章分页。协同制造-管理员-需求管理列表
     * @param demandId  询价编号
     * @param inquiryTheme  询价主题
     * @param contact  联系人
     * @param startDate  开始时间
     * @param endDate  结束时间
     * @param classifyType 所属分类
     * @param companyId  公司编号
     * @param statusType  状态
     * @param mobile  电话号码
     * @param createDt  创建日期
     * @param deadlineDt  报价截止日期
     * @Author maocheng
     * @return
     */
    @RequestMapping(value = "/member/admin_demand_list.jspx")
    public String list(String demandId, String inquiryTheme, String contact, Date startDate, Date endDate, String classifyType,
                       String companyId, String statusType, String mobile, Date createDt, Date deadlineDt, Integer modelId, Integer queryChannelId,
                       Integer pageNo, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
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
        //不是管理员不能查看
        if(!user.getIsAdmin()){
            errors.addErrorCode("error.noPermissionsView");
            return FrontUtils.showError(request,response,model,errors);
        }
        Pagination p = DemandService.getPageForAll(queryChannelId, site.getSiteId(), modelId, user.getUserId(), cpn(pageNo), 20,
                demandId,inquiryTheme,contact,startDate,endDate,classifyType,companyId,statusType,mobile,createDt,deadlineDt);
        model.addAttribute("pagination", p);
        if (modelId != null) {
            model.addAttribute("modelId", modelId);
        }
        //将查询条件放入model中
        model.addAttribute("demandId", demandId);
        model.addAttribute("inquiryTheme", inquiryTheme);
        model.addAttribute("contact", contact);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("classifyType", classifyType);
        model.addAttribute("companyId", companyId);
        model.addAttribute("statusType", statusType);
        model.addAttribute("mobile", mobile);
        model.addAttribute("createDt", createDt);
        model.addAttribute("deadlineDt", deadlineDt);
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, nextUrl);
    }
    /**
     * 管理员驳回需求
     * @Author jiangshuzhong
     * @param id 文章ID
     * @param channelId 栏目ID
     * @param canshu  判断跳转路径2：跳转待审批 3：跳转询价中
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/member/synergy_demand_reject.jspx")
    public String reject(Integer id, Integer channelId, String canshu, HttpServletRequest request,
                         HttpServletResponse response, ModelMap model,String backReason) {
        String nextUrl = "/member/admin_demand_list.jspx?statusType="+canshu;
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
//        MemberConfig mcfg = site.getConfig().getMemberConfig();
//         没有开启会员功能
//        if (!mcfg.isMemberOn()) {
//            return FrontUtils.showMessage(request, model, "member.memberClose");
//        }
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
        //非管理员不能修改
        if (!user.getIsAdmin()) {
            return FrontUtils.showMessage(request, model, "error.noPermission");
        }
        //业务数据更新处理
        try {
            backReason=java.net.URLDecoder.decode(backReason, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        DemandService.rejectDemand(id,backReason);
        return FrontUtils.showSuccess(request, model, nextUrl);
    }
    /**
     * 管理员通过需求
     * @Author jiangshuzhong
     * @param id 文章ID
     * @param channelId 栏目ID
     * @param canshu  判断跳转路径2：跳转待审批3：跳转询价中
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/member/synergy_demand_pass.jspx")
    public String pass(Integer id,Integer channelId, String canshu, HttpServletRequest request,
                       HttpServletResponse response, ModelMap model) {
        String nextUrl = "/member/admin_demand_list.jspx?statusType=2";
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
//        MemberConfig mcfg = site.getConfig().getMemberConfig();
//         没有开启会员功能
//        if (!mcfg.isMemberOn()) {
//            return FrontUtils.showMessage(request, model, "member.memberClose");
//        }
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
        //非管理员不能修改
        if (!user.getIsAdmin()) {
            return FrontUtils.showMessage(request, model, "error.noPermission");
        }
        //业务数据更新处理
        DemandService.passDemand(id);
        return FrontUtils.showSuccess(request, model, nextUrl);
    }
    /**
     * 管理员置为驳回需求
     * @Author 闫浩芮
     * @param  demandIdsStr 批量操作对象的Id字符串
     * @param  nextUrl 操作完成后的返回地址
     * @return
     */
    @RequestMapping(value = "/member/synergy_demand_rejectMany.jspx")
    public String rejectMany(String demandIdsStr,String nextUrl,HttpServletRequest request,
                       HttpServletResponse response, ModelMap model,String backReason) {
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
        try {
            backReason=java.net.URLDecoder.decode(backReason, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        DemandService.rejectMany(demandIdsStr,backReason);
        model.addAttribute("statusType", statusType);
        return FrontUtils.showSuccess(request, model, nextUrl);
    }
    /**
     * 置为通过
     * @Author 闫浩芮
     * @param  demandIdsStr 批量操作对象的Id字符串
     * @param  nextUrl 操作完成后的返回地址
     * @Date 2017/2/17 0017 18:44
     */
    @RequestMapping(value = "/member/synergy_demand_passMany.jspx")
    public String passMany(String demandIdsStr,String nextUrl,HttpServletRequest request,
                           HttpServletResponse response, ModelMap model) {
        String statusType = "2";
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
        DemandService.passMany(demandIdsStr);
        model.addAttribute("statusType", statusType);
        return FrontUtils.showSuccess(request, model, nextUrl);
    }
    @Resource
    protected ContentMng contentMng;
    @Resource
    private DemandService DemandService;
}
