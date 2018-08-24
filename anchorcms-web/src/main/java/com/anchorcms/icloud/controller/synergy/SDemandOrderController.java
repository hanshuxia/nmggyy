package com.anchorcms.icloud.controller.synergy;

import com.anchorcms.cms.service.main.ContentMng;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.common.utils.CmsUtils;
import com.anchorcms.common.utils.FrontUtils;
import com.anchorcms.core.model.CmsSite;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.core.model.MemberConfig;
import com.anchorcms.icloud.model.synergy.SCompanyAptitude;
import com.anchorcms.icloud.service.synergy.SPOrderService;
import com.anchorcms.icloud.service.synergy.SynergyCompanyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import static com.anchorcms.common.constants.Constants.TPLDIR_MEMBER;
import static com.anchorcms.common.page.SimplePage.cpn;

/**
 * 协同制造--需求方--订单管理列表
 * @Author hansx
 * @Date 2017/05/02 0023 15:57
 */
@Controller
public class SDemandOrderController {
    private static final Logger log = LoggerFactory.getLogger(SDemandOrderController.class);
    public static final String DEMAND_ORDER_LIST = "tpl.synergySdemandOrderList";
    public static final String BIGDATA_QUOTE_LIST = "tpl.bigdataSdemandQuoteList";
    public static final String COMPANY_APTITUDE_ADD = "/member/company_edit.jspx";
    public static final String BIGDATA_ABILITY_QUOTE_LIST = "tpl.bigdataAbilityQuoteList";
    public static final String BIGDATA_ABILITY_Q_LIST = "tpl.bigdataAbilityQList";
    public static final String BIGDATA_DEMAND_ORDER_LIST = "tpl.bigdataDemandOrderList";
    public static final String BIGDATA_ABILITY_ORDER_LIST = "tpl.bigdataAbilityOrderList";
    /**
     * @Author hansx
     * 需求方订单列表
     * @Date 2017/05/02 0023 15:57
     */

    @RequestMapping(value = "/member/synergy_demand_order_list.jspx")
    public String list(String statusType, Integer modelId,Integer pageNo,
                       HttpServletRequest request, ModelMap model) {
        String nextUrl = DEMAND_ORDER_LIST;
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
        //需要用户存在某公司下
        if(user.getCompany()==null){
            return FrontUtils.showMessage(request, model, "error.userWithoutCompany");
        }
        //判断企业资质
        SCompanyAptitude company_aptitude = synergyCompanyService.findAptitudeByCompanyId(user.getCompany().getCompanyId());
        if(company_aptitude==null||!company_aptitude.getStatus().equals("3")) {//未通过企业资质认证
            String message = "您的企业尚未通过资质认证，请前往会员中心-维护企业信息-企业资质进行维护！";
            return FrontUtils.showBaseMessage(request, model, message, COMPANY_APTITUDE_ADD);
        }
        Pagination p = spOrderService.getPageForMember(user.getUserId(),cpn(pageNo), 20,statusType);
        model.addAttribute("pagination", p);
        if (modelId != null) {
            model.addAttribute("modelId", modelId);
        }
        model.addAttribute("statusType", statusType);
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, nextUrl);
    }

/**
 * @author liuyang
 * @Date 2018/4/27 9:27
 * @return
 * 大数据需求对接列表
 */
@RequestMapping(value = "/member/bigdata_demand_quote_list.jspx")
public String list2(String status,String deFlag, Integer modelId,Integer pageNo,
                   HttpServletRequest request, ModelMap model) {
    String nextUrl = BIGDATA_QUOTE_LIST;
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
    //需要用户存在某公司下
    if(user.getCompany()==null){
        return FrontUtils.showMessage(request, model, "error.userWithoutCompany");
    }
//    //判断企业资质
//    SCompanyAptitude company_aptitude = synergyCompanyService.findAptitudeByCompanyId(user.getCompany().getCompanyId());
//    if(company_aptitude==null||!company_aptitude.getStatus().equals("3")) {//未通过企业资质认证
//        String message = "您的企业尚未通过资质认证，请前往会员中心-维护企业信息-企业资质进行维护！";
//        return FrontUtils.showBaseMessage(request, model, message, COMPANY_APTITUDE_ADD);
//    }
    Pagination p = spOrderService.getPageForMember2(user.getUserId(),cpn(pageNo), 20,status,deFlag);
    model.addAttribute("pagination", p);
    if (modelId != null) {
        model.addAttribute("modelId", modelId);
    }
    model.addAttribute("status", status);
    model.addAttribute("deFlag", deFlag);
    return FrontUtils.getTplPath(request, site.getSolutionPath(),
            TPLDIR_MEMBER, nextUrl);
}
    /**
     * @author liuyang
     * @Date 2018/4/27 14:45
     * @return大数据能力对接表
     */
    @RequestMapping(value = "/member/bigdata_ability_quote_list.jspx")
    public String list3(String status,String deFlag, Integer modelId,Integer pageNo,
                        HttpServletRequest request, ModelMap model) {
        String nextUrl = BIGDATA_ABILITY_QUOTE_LIST;
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
        //需要用户存在某公司下
        if(user.getCompany()==null){
            return FrontUtils.showMessage(request, model, "error.userWithoutCompany");
        }
        //判断企业资质
//        SCompanyAptitude company_aptitude = synergyCompanyService.findAptitudeByCompanyId(user.getCompany().getCompanyId());
//        if(company_aptitude==null||!company_aptitude.getStatus().equals("3")) {//未通过企业资质认证
//            String message = "您的企业尚未通过资质认证，请前往会员中心-维护企业信息-企业资质进行维护！";
//            return FrontUtils.showBaseMessage(request, model, message, COMPANY_APTITUDE_ADD);
//        }
        Pagination p = spOrderService.getPageForMember3(user.getUserId(),cpn(pageNo), 20,status,deFlag);
        model.addAttribute("pagination", p);
        if (modelId != null) {
            model.addAttribute("modelId", modelId);
        }
        model.addAttribute("status", status);
        model.addAttribute("deFlag", deFlag);
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, nextUrl);
    }
/**
 * @author liuyang
 * @Date 2018/5/7 9:01
 * @return
 * 大数据需求方能力对接表
 */
    @RequestMapping(value = "/member/bigdata_demand_order_list.jspx")
    public String list4(String status, Integer modelId,Integer pageNo,
                        HttpServletRequest request, ModelMap model,String deFlag) {
        String nextUrl = BIGDATA_DEMAND_ORDER_LIST;
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
        //需要用户存在某公司下
        if(user.getCompany()==null){
            return FrontUtils.showMessage(request, model, "error.userWithoutCompany");
        }
        //判断企业资质
//        SCompanyAptitude company_aptitude = synergyCompanyService.findAptitudeByCompanyId(user.getCompany().getCompanyId());
//        if(company_aptitude==null||!company_aptitude.getStatus().equals("3")) {//未通过企业资质认证
//            String message = "您的企业尚未通过资质认证，请前往会员中心-维护企业信息-企业资质进行维护！";
//            return FrontUtils.showBaseMessage(request, model, message, COMPANY_APTITUDE_ADD);
//        }
        Pagination p = spOrderService.getPageForMember4(user.getUserId(),cpn(pageNo), 20,status,deFlag);
        model.addAttribute("pagination", p);
        if (modelId != null) {
            model.addAttribute("modelId", modelId);
        }
        model.addAttribute("status", status);
        model.addAttribute("deFlag", deFlag);
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, nextUrl);
    }

    /**
     * @author liuyang
     * @Date 2018/5/7 9:05
     * @return
     * 大数据能力方需求对接表
     */
    @RequestMapping(value = "/member/bigdata_ability_order_list.jspx")
    public String list5(String status, Integer modelId,Integer pageNo,
                        HttpServletRequest request, ModelMap model,String deFlag) {
        String nextUrl = BIGDATA_ABILITY_ORDER_LIST;
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
        //需要用户存在某公司下
        if(user.getCompany()==null){
            return FrontUtils.showMessage(request, model, "error.userWithoutCompany");
        }
        //判断企业资质
        SCompanyAptitude company_aptitude = synergyCompanyService.findAptitudeByCompanyId(user.getCompany().getCompanyId());
        if(company_aptitude==null||!company_aptitude.getStatus().equals("3")) {//未通过企业资质认证
            String message = "您的企业尚未通过资质认证，请前往会员中心-维护企业信息-企业资质进行维护！";
            return FrontUtils.showBaseMessage(request, model, message, COMPANY_APTITUDE_ADD);
        }
        Pagination p = spOrderService.getPageForMember4(user.getUserId(),cpn(pageNo), 20,status,deFlag);
        model.addAttribute("pagination", p);
        if (modelId != null) {
            model.addAttribute("modelId", modelId);
        }
        model.addAttribute("status", status);
        model.addAttribute("deFlag", deFlag);
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, nextUrl);
    }


    /**
     * @author 苏和
     * @Date 2018/5/4 14:45
     * @return大数据能力对接管理
     */
    @RequestMapping(value = "/member/bigdata_ability_q_list.jspx")
    public String abilityQlist(String status,String deFlag, Integer modelId,Integer pageNo,
                        HttpServletRequest request, ModelMap model) {
        String nextUrl = BIGDATA_ABILITY_Q_LIST;
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
        //需要用户存在某公司下
        if(user.getCompany()==null){
            return FrontUtils.showMessage(request, model, "error.userWithoutCompany");
        }
        //判断企业资质
//        SCompanyAptitude company_aptitude = synergyCompanyService.findAptitudeByCompanyId(user.getCompany().getCompanyId());
//        if(company_aptitude==null||!company_aptitude.getStatus().equals("3")) {//未通过企业资质认证
//            String message = "您的企业尚未通过资质认证，请前往会员中心-维护企业信息-企业资质进行维护！";
//            return FrontUtils.showBaseMessage(request, model, message, COMPANY_APTITUDE_ADD);
//        }
        Pagination p = spOrderService.getPageForMember4(user.getUserId(),cpn(pageNo), 20,status,deFlag);
        model.addAttribute("pagination", p);
        if (modelId != null) {
            model.addAttribute("modelId", modelId);
        }
        model.addAttribute("status", status);
        model.addAttribute("deFlag", deFlag);
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, nextUrl);
    }



    /**
     * @author ldong
     * @Date 2017/5/4 21:21
     * @return  删除订单
     */

    @RequestMapping(value = "/member/synergy_demand_order_del.jspx")
    public String del(String statusType, Integer modelId,Integer pageNo,
                       HttpServletRequest request, ModelMap model,String id) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
        //需要用户存在某公司下
        if(user.getCompany()==null){
            return FrontUtils.showMessage(request, model, "error.userWithoutCompany");
        }
        //判断企业资质
        SCompanyAptitude company_aptitude = synergyCompanyService.findAptitudeByCompanyId(user.getCompany().getCompanyId());
        if(company_aptitude==null||!company_aptitude.getStatus().equals("3")) {//未通过企业资质认证
            String message = "您的企业尚未通过资质认证，请前往会员中心-维护企业信息-企业资质进行维护！";
            return FrontUtils.showBaseMessage(request, model, message, COMPANY_APTITUDE_ADD);
        }
        this.spOrderService.delete(id);
      return  FrontUtils.showSuccess(request,model,"/member/synergy_demand_order_list.jspx?statusType=5");
    }

    @Resource
    protected ContentMng contentMng;
    @Resource
    private SPOrderService spOrderService;
    @Resource
    private SynergyCompanyService synergyCompanyService;
}
