package com.anchorcms.icloud.controller.supplychain;

import com.anchorcms.cms.service.main.ChannelMng;
import com.anchorcms.cms.service.main.CmsModelMng;
import com.anchorcms.cms.service.main.ContentTypeMng;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.common.utils.CmsUtils;
import com.anchorcms.common.utils.FrontUtils;
import com.anchorcms.core.model.CmsSite;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.core.model.MemberConfig;
import com.anchorcms.icloud.model.supplychain.SRepairInquiry;
import com.anchorcms.icloud.model.supplychain.SRepairRes;
import com.anchorcms.icloud.model.synergy.SCompanyAptitude;
import com.anchorcms.icloud.service.supplychain.SRepairAbilityService;
import com.anchorcms.icloud.service.supplychain.SRepairInquiryService;
import com.anchorcms.icloud.service.supplychain.SRepairQuoteService;
import com.anchorcms.icloud.service.supplychain.SRepairResService;
import com.anchorcms.icloud.service.synergy.SynergyCompanyService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.sql.Date;

import static com.anchorcms.common.constants.Constants.TPLDIR_SUPPLYCHAIN;
import static com.anchorcms.common.page.SimplePage.cpn;

/**
 * Created by hansx on 2017/1/2.
 * <p>
 * 待报价方案管理
 */
@Controller
public class SRepairResWaitQuoteController {
    private static final Logger log = LoggerFactory.getLogger(SRepairResWaitQuoteController.class);

    public static final String TPL_REPAIRRES_WAITQUOTE_LIST = "tpl.repairres_waitquote_list";
    public static final String TPL_REPAIRRES_INQUIRYPRICE_DETAIL = "tpl.repairres_inquiryprice_detail";
    public static final String TPL_REPAIRRES_QUOTE = "tpl.repairres_quote";
    public static final String COMPANY_APTITUDE_ADD = "/member/company_edit.jspx";
    String  strinquiryId;

    /**
     * @return 转至待报价管理界面
     * @author hansx
     * @Date 2017/1/4 0004 下午 1:54
     */
    @RequestMapping(value = "/member/getquotelist.jspx")
    public String condition_query(String statusType, String inquiryTheme, Date startDate, Date endDate, String companyName, Integer modelId,
                                  Integer queryChannelId, Integer pageNo, HttpServletRequest request,
                                  ModelMap model) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        MemberConfig mcfg = site.getConfig().getMemberConfig();
        //没有开启会员功能
        if (!mcfg.isMemberOn()) {
            return FrontUtils.showMessage(request, model, "member.memberClose");
        }
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
        if (user.getCompany() == null) {
            return FrontUtils.showMessage(request, model, "error.userWithoutCompany");
        }
        //判断企业资质
        SCompanyAptitude company_aptitude = synergyCompanyService.findAptitudeByCompanyId(user.getCompany().getCompanyId());
        if(company_aptitude==null||!company_aptitude.getStatus().equals("3")) {//未通过企业资质认证
            String message = "您的企业尚未通过资质认证，请前往会员中心-维护企业信息-企业资质进行维护！";
            return FrontUtils.showBaseMessage(request, model, message, COMPANY_APTITUDE_ADD);
        }
        String companyId = user.getCompany().getCompanyId();
        //条件查询列表
        Pagination p = sRepairInquiryService.getInquiryListForMember(statusType, inquiryTheme, startDate, endDate,
                companyName, companyId, queryChannelId, site.getSiteId(), modelId, user.getUserId(), cpn(pageNo), 20,true);
        model.addAttribute("pagination", p);
        if (modelId != null) {
            model.addAttribute("modelId", modelId);
        }
        //条件查询参数
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("inquiryTheme", inquiryTheme);
        model.addAttribute("companyName", companyName);
        model.addAttribute("statusType", statusType);

        FrontUtils.frontData(request, model, site);
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_SUPPLYCHAIN, TPL_REPAIRRES_WAITQUOTE_LIST);
    }


    /**
     * @return 转至询价明细页面
     * @author hansx
     * @Date 2017/1/5 0005 下午 1:50
     */
    @RequestMapping(value = "/member/inquirypricedetail.jspx")
    public String inquirypricedetail(String inquiryId,  HttpServletRequest request, ModelMap model) {

        //获取询价信息
        SRepairInquiry sRepairInquiryEntity = sRepairInquiryService.findById(inquiryId);
        model.addAttribute("repairinquiry", sRepairInquiryEntity);//询价信息
        model.addAttribute("repairres", sRepairInquiryEntity.getsRepairRes());//能力信息
        CmsSite site = CmsUtils.getSite(request);
        FrontUtils.frontData(request, model, site);
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_SUPPLYCHAIN, TPL_REPAIRRES_INQUIRYPRICE_DETAIL);
    }

    /**
     * @return 转至报价页面
     * @author hansx
     * @Date 2017/1/5 0005 下午 1:51
     */
    @RequestMapping(value = "/member/repairesquote.jspx")
    public String repairesquote(String id, HttpServletRequest request, ModelMap model) {

        //获取询价信息
        SRepairInquiry sRepairInquiryEntity = sRepairInquiryService.findById(id);
        model.addAttribute("repairinquiry", sRepairInquiryEntity);//询价信息
        model.addAttribute("repairres", sRepairInquiryEntity.getsRepairRes());//能力信息
        strinquiryId = id;
        CmsSite site = CmsUtils.getSite(request);
        FrontUtils.frontData(request, model, site);
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_SUPPLYCHAIN, TPL_REPAIRRES_QUOTE);
    }


    /**
     * 报价信息保存
     *
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/member/quotesave.jspx")
    public String quotesave(String offer, String statusType, HttpServletRequest request,
                            ModelMap model, String nextUrl) {
        CmsSite site = CmsUtils.getSite(request);
        FrontUtils.frontData(request, model, site);

        sRepairInquiryService.updateById(strinquiryId, Double.valueOf(offer), statusType);
        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    @Resource
    private SRepairAbilityService sRepairAbilityService;
    @Resource
    private SRepairQuoteService sRepairQuoteService;
    @Resource
    private SRepairInquiryService sRepairInquiryService;
    @Resource
    private SRepairResService sRepairResService;
    @Resource
    private CmsModelMng cmsModelMng;
    @Resource
    private ChannelMng channelMng;
    @Resource
    private ContentTypeMng contentTypeMng;
    @Resource
    private SynergyCompanyService synergyCompanyService;
}
