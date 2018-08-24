package com.anchorcms.icloud.controller.synergy;

import com.anchorcms.cms.model.main.Content;
import com.anchorcms.cms.service.main.ChannelMng;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.common.utils.CmsUtils;
import com.anchorcms.common.utils.FrontUtils;
import com.anchorcms.common.web.CookieUtils;
import com.anchorcms.core.model.CmsSite;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.core.model.MemberConfig;
import com.anchorcms.core.web.WebErrors;
import com.anchorcms.icloud.model.synergy.SAbility;
import com.anchorcms.icloud.model.synergy.SCompany;
import com.anchorcms.icloud.model.synergy.SCompanyAptitude;
import com.anchorcms.icloud.service.common.CfcaService;
import com.anchorcms.icloud.service.synergy.SCompanyManagementService;
import com.anchorcms.icloud.service.synergy.SynergyCreateService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static com.anchorcms.common.constants.Constants.TPLDIR_MEMBER;
import static com.anchorcms.common.constants.Constants.TPLDIR_NLCZS;
import static com.anchorcms.common.page.SimplePage.cpn;

/**
 * Copyright @ 2016 Shandong jxdinfo software co,ltd.
 * All right reserved.
 * Created by Gao Jiangning
 * @Date 2016/12/26 0026
 * @Desc 所有企业列表界面(管理员后台登陆后管理)的controller
 */
@Controller
public class SCompanyManagementController {
    public static final String COMPANY_LIST = "tpl.sCompanyList";
    public static final String COMPANY_CHECKED = "tpl.sCompanyChecked";
    public static final String COMPANY_ABILITY_LIST = "tpl.sCompanyAbilityList";
    public static final String SCOMPANYCREDENTIALS = "tpl.sCompanyCredentialsChecked";
    public static final String COMPANY_PREVIEW_CREDENTIALS ="tpl.synergyCompanyPreViewCredentials";
    /**
     * @author: gao jiangning
     * @desciption 企业制造能力管理界面，每页显示10条先写死
     */
    @RequestMapping(value = "/member/companies_list.jspx")
    public String companyList(String companyName, String companyCode, Date createDtStart, Date createDtEnd, String mobile, Integer pageNo,
                              HttpServletRequest request, HttpServletResponse response, ModelMap model,String status) {
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
        //非管理员不能查看
        if(!user.getIsAdmin()){
            errors.addErrorCode("error.noPermissionsView");
            return FrontUtils.showError(request,response,model,errors);
        }
        Pagination p =service.getCompanyListByPage(companyName, companyCode,mobile, createDtStart, createDtEnd, pageNo,status);
        model.addAttribute("coPagination", p);
        model.addAttribute("companyName",companyName);
        model.addAttribute("companyCode",companyCode);
        model.addAttribute("searchMobile",mobile);
        model.addAttribute("searchCreateDtStart",createDtStart);
        model.addAttribute("searchCreateDtEnd",createDtEnd);
        model.addAttribute("pageNo",pageNo);
        model.addAttribute("status",status);
        System.out.print(p.getTotalCount());
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, COMPANY_LIST);
    }

    /**
     * @author: gao jiangning
     * @desciption 企业制造能力管理界面，每页显示10条先写死
     */
    @RequestMapping(value = "/member/companies_checked.jspx")
    public String companyList_Checked(String companyName, String companyCode, Date createDtStart, Date createDtEnd, String mobile, Integer pageNo,
                              HttpServletRequest request, HttpServletResponse response, ModelMap model,String status) {
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
        //非管理员不能查看
        if(!user.getIsAdmin()){
            errors.addErrorCode("error.noPermissionsView");
            return FrontUtils.showError(request,response,model,errors);
        }
        Pagination p =service.getCompanyListByPage_check(companyName, companyCode,mobile, createDtStart, createDtEnd, pageNo,status);
        model.addAttribute("coPagination", p);
        model.addAttribute("companyName",companyName);
        model.addAttribute("companyCode",companyCode);
        model.addAttribute("searchMobile",mobile);
        model.addAttribute("searchCreateDtStart",createDtStart);
        model.addAttribute("searchCreateDtEnd",createDtEnd);
        model.addAttribute("pageNo",pageNo);
        model.addAttribute("status",status);
        System.out.print(p.getTotalCount());
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, COMPANY_CHECKED);
    }


    /**
     * @author: gao jiangning
     * @desciption 2016/12/29 企业能力列表管理界面
     */
    @RequestMapping(value = "/member/company_ability_list.jspx")
    public String companyAbilitiesList(
            String companyId,
            java.util.Date startDate,
            java.util.Date endDate,
            String releaseId,
            String abilityType,
            String abilityName,
            String abilityCode,
            Integer modelId,
            Integer pageNo,
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
        //非管理员or主帐号(1为主账号)不能查看
        if(!user.getIsAdmin() && !"1".equals(user.getIsMain())){
            return FrontUtils.showMessage(request, model, "error.noPermissionsView");
        }
        //获取分页对象
        Pagination p = synergyCreateService.getPageForCompany(companyId, cpn(pageNo), 20, startDate, endDate, releaseId,
                abilityType, abilityName, abilityCode);

        model.addAttribute("pagination", p);
        if (modelId != null) {
            model.addAttribute("modelId", modelId);
        }
        //将查询条件放入model中
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("releaseId", releaseId);
        model.addAttribute("abilityType", abilityType);
        model.addAttribute("abilityName", abilityName);
        model.addAttribute("abilityCode", abilityCode);
        model.addAttribute("companyId", companyId);
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, COMPANY_ABILITY_LIST);
    }
/**
 * @author liuyang
 * @Date 2017/5/10 18:51
 * @return
 */
@RequestMapping(value ="/member/sCompanyCredentialsChecked.jspx")
public String list(String companyId,String companyName, Integer pageNo, HttpServletRequest request, ModelMap model, String status) {

    CmsSite site = CmsUtils.getSite(request);
    FrontUtils.frontData(request, model, site); // 为前台模板设置公用数据
    // 获得分页信息
    Pagination pagination = service.getCredentialsList(companyId,companyName, status, cpn(pageNo), CookieUtils.getPageSize(request));
    List paginationList = pagination.getList(); // 必须返回List格式数据
    model.addAttribute("cfcaList", paginationList);
    model.addAttribute("pagination", pagination);
    model.addAttribute("companyId", companyId);
    model.addAttribute("status",status);
    model.addAttribute("companyName",companyName);
    return FrontUtils.getTplPath(request, site.getSolutionPath(),
            TPLDIR_MEMBER, SCOMPANYCREDENTIALS);
}
    /**
     * @author liuyang
     * @Date 2017/5/11 9:44
     * @return 资质审核批量通过驳回
     */
    @RequestMapping("/member/AptitudeModifyState.jspx")
    public String modifyAptitudeStateById(String aptitudeId, String status, java.util.Date updateDt, String nextUrl,
                                      HttpServletRequest request, HttpServletResponse response, ModelMap model, String backReason) throws UnsupportedEncodingException {
        CmsSite site = CmsUtils.getSite(request);
        FrontUtils.frontData(request, model, site);
        if(backReason != null){
            backReason = java.net.URLDecoder.decode(backReason, "utf-8");
        }
        if (aptitudeId != null) {
            aptitudeId = java.net.URLDecoder.decode(aptitudeId, "utf-8");
            service.modifyAptitudeStateById(aptitudeId, status,updateDt,backReason);
        }
        return FrontUtils.showSuccess(request, model, nextUrl);
    }
    /**
     * @author liuyang
     * @Date 2017/5/11 9:51
     * @return  企业资质详情
     */
    @RequestMapping(value = "/member/synergyCompanyPreViewCredentials.jspx")
    public String xtzz_Company(String id, HttpServletRequest request,
                               HttpServletResponse response, ModelMap model) {
        String nextUrl = COMPANY_PREVIEW_CREDENTIALS;
        CmsSite site = CmsUtils.getSite(request);
        FrontUtils.frontData(request, model, site);
        MemberConfig mcfg = site.getConfig().getMemberConfig();
        // 没有开启会员功能
        if (!mcfg.isMemberOn()) {
            return FrontUtils.showMessage(request, model, "member.memberClose");
        }
        SCompany company = service.byCompanyId(id);
        Set<SAbility> list = company.getAbilities();
        Content content = null;
        if((company.getAbilities()).size()>0){
            Iterator<SAbility> it  = list.iterator();
            while(it.hasNext()){
                content= it.next().getContent();
            }
        }
        // Content content = company.getAbilities();
        // 获得本站栏目列表
        model.addAttribute("company",company);
        model.addAttribute("channel",channelMng.findById(105));
        model.addAttribute("site", site);
        model.addAttribute("sessionId",request.getSession().getId());
        model.addAttribute("channel",channelMng.findById(103));
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_NLCZS, nextUrl);
    }
    @Resource
    protected CfcaService cfcaService;
    @Resource
    private SCompanyManagementService service;

    @Resource
    private SynergyCreateService synergyCreateService;

    @Resource
    private ChannelMng channelMng;
}
