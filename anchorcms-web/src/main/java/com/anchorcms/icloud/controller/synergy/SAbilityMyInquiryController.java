package com.anchorcms.icloud.controller.synergy;

import com.anchorcms.cms.service.main.ChannelMng;
import com.anchorcms.cms.service.main.ContentMng;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.common.utils.CmsUtils;
import com.anchorcms.common.utils.FrontUtils;
import com.anchorcms.core.model.CmsSite;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.core.model.MemberConfig;
import com.anchorcms.icloud.model.synergy.SCompanyAptitude;
import com.anchorcms.icloud.model.synergy.SDemandQuote;
import com.anchorcms.icloud.service.synergy.SAbilityMyInquiryService;
import com.anchorcms.icloud.service.synergy.SDemandQuoteService;
import com.anchorcms.icloud.service.synergy.SynergyCompanyService;
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
public class SAbilityMyInquiryController {
    private static final Logger log = LoggerFactory.getLogger(SAbilityMyInquiryController.class);
    public static final String MYINQUIRY_LIST = "tpl.SAbilityMyInquiryList";
    public static final String MYINQUIRY_VIEW = "tpl.SAbilityMyInquiryView";
    public static final String MYINQUIRY_DETAIL_VIEW = "tpl.SAbilityMyInquiryDetailView";
    public static final String COMPANY_APTITUDE_ADD = "/member/company_edit.jspx";

    /**
     * 会员投稿列表
     *
     * @param pageNo
     *            页码
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/member/ability_myInquiry_list.jspx")
    public String list(Integer UserId, Date releaseDt, Date deadlineDt, Integer demandId, String inquiryTheme,
                       String selectedStatus, String payType,String statusType, Integer modelId,
                       Integer queryChannelId, Integer pageNo,
                       HttpServletRequest request, ModelMap model) {
        String nextUrl = MYINQUIRY_LIST;
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
        //判断企业资质
        SCompanyAptitude company_aptitude = synergyCompanyService.findAptitudeByCompanyId(user.getCompany().getCompanyId());
        if(company_aptitude==null||!company_aptitude.getStatus().equals("3")) {//未通过企业资质认证
            String message = "您的企业尚未通过资质认证，请前往会员中心-维护企业信息-企业资质进行维护！";
            return FrontUtils.showBaseMessage(request, model, message, COMPANY_APTITUDE_ADD);
        }
        Pagination p = SAbilityMyInquiryService.getPageForMember(queryChannelId, site.getSiteId(), modelId, user.getUserId(),
                user.getUserId(),cpn(pageNo), 20, releaseDt,deadlineDt, demandId, inquiryTheme,selectedStatus,payType,statusType);
        model.addAttribute("pagination", p);
        if (modelId != null) {
            model.addAttribute("modelId", modelId);
        }
        //将查询条件放入model中
        model.addAttribute("inquiryTheme", inquiryTheme);
        model.addAttribute("statusType", statusType);
/*        model.addAttribute("myStartDate", myStartDate);
        model.addAttribute("myEndDate", myEndDate);
        model.addAttribute("companyId", companyId);*/
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, nextUrl);
    }

    /**
     * @Author maocheng
     * @Date 2016/12/26
     * @param quoteId 主键id
     * @param request request对象
     * @param response response对象
     * @param model model对象
     * @Desc 我的方案报价：中标详情
     */

    @RequestMapping(value = "/member/ability_myInquiry_view.jspx")
    public String view(Integer quoteId, HttpServletRequest request,
                       HttpServletResponse response, ModelMap model) {
        String nextUrl = MYINQUIRY_VIEW;
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
        //用户没有关联公司的话不能操作
        if(user.getCompany() == null){
            return FrontUtils.showMessage(request, model, "error.userWithoutCompany");
        }
        //判断企业资质
        SCompanyAptitude company_aptitude = synergyCompanyService.findAptitudeByCompanyId(user.getCompany().getCompanyId());
        if(company_aptitude==null||!company_aptitude.getStatus().equals("3")) {//未通过企业资质认证
            String message = "您的企业尚未通过资质认证，请前往会员中心-维护企业信息-企业资质进行维护！";
            return FrontUtils.showBaseMessage(request, model, message, COMPANY_APTITUDE_ADD);
        }
        if(quoteId==null){
            return FrontUtils.showMessage(request, model, "error.needParams");
        }
        SDemandQuote quote= sDemandQuoteService.byDemandQuoteId(quoteId);
        //报价状态为已中标才能查看
        if(!"2".equals(quote.getSelectedStatus())){
            return FrontUtils.showMessage(request, model, "error.exceptionParams");
        }
        model.addAttribute("quote",quote);
        model.addAttribute("sessionId",request.getSession().getId());
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, nextUrl);
    }

    /**
     * @Author zhouyq
     * @Date 2017/04/26
     * @param quoteId 主键id
     * @param request request对象
     * @param response response对象
     * @param model model对象
     * @Desc 优选查看企业报价详情
     */
    @RequestMapping(value = "/member/ability_myInquiry_detail_view.jspx")
    public String yxDetailView(Integer quoteId, HttpServletRequest request,
                       HttpServletResponse response, ModelMap model) {
        String nextUrl = MYINQUIRY_DETAIL_VIEW;
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
        // 用户没有关联公司的话不能操作
        if (user.getCompany() == null){
            return FrontUtils.showMessage(request, model, "error.userWithoutCompany");
        }
        //判断企业资质
        SCompanyAptitude company_aptitude = synergyCompanyService.findAptitudeByCompanyId(user.getCompany().getCompanyId());
        if(company_aptitude==null||!company_aptitude.getStatus().equals("3")) {//未通过企业资质认证
            String message = "您的企业尚未通过资质认证，请前往会员中心-维护企业信息-企业资质进行维护！";
            return FrontUtils.showBaseMessage(request, model, message, COMPANY_APTITUDE_ADD);
        }
        if (quoteId==null){
            return FrontUtils.showMessage(request, model, "error.needParams");
        }
        SDemandQuote quote= sDemandQuoteService.byDemandQuoteId(quoteId);
        model.addAttribute("quote",quote);
        model.addAttribute("sessionId",request.getSession().getId());
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, nextUrl);
    }

    @Resource
    protected ContentMng contentMng;
    @Resource
    private ChannelMng channelMng;
    @Resource
    private SAbilityMyInquiryService SAbilityMyInquiryService;
    @Resource
    private SDemandQuoteService sDemandQuoteService;
    @Resource
    private SynergyCompanyService synergyCompanyService;
}
