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
import com.anchorcms.icloud.model.payment.SPOrder;
import com.anchorcms.icloud.model.payment.SPOrderObj;
import com.anchorcms.icloud.model.synergy.*;
import com.anchorcms.icloud.service.synergy.SAbilityInquiryService;
import com.anchorcms.icloud.service.synergy.SDemandQuoteService;
import com.anchorcms.icloud.service.synergy.SynergyCompanyService;
import com.anchorcms.icloud.service.synergy.SynergyManageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import payment.api.util.GUIDGenerator;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.*;
import java.util.Date;

import static com.anchorcms.common.constants.Constants.TPLDIR_MEMBER;
import static com.anchorcms.common.page.SimplePage.cpn;

@Controller
public class SAbilityInquiryController {
    private static final Logger log = LoggerFactory.getLogger(SAbilityInquiryController.class);
    public static final String INQUIRY_LIST = "tpl.SAbilityInquiryList";
    public static final String INQUIRY_VIEW = "tpl.SAbilityInquiryView";
    public static final String DEVICE_INQUIRY_VIEW = "tpl.SDeviceInquiryView";
    public static final String DEVICE_LIST = "tpl.SDeviceInquiryList";
    public static final String COMPANY_APTITUDE_ADD = "/member/company_edit.jspx";
    /**
     * 协同制造-能力方-待报价方案列表
     * @param statusType
     * @param inquiryTheme 询价主题
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @param companyId 询价企业
     * @param pageNo
     * @Author maocheng
     * @return
     */
    @RequestMapping(value = "/member/ability_inquiry_list.jspx")
    public String list(String statusType,String inquiryTheme, Date startDate, Date endDate, String companyId,
                       Integer modelId, Integer pageNo, HttpServletRequest request, ModelMap model) {
        String nextUrl = INQUIRY_LIST;
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
        if(user.getCompany()==null){
            return FrontUtils.showMessage(request, model, "error.userWithoutCompany");
        }
        //判断企业资质
        SCompanyAptitude company_aptitude = synergyCompanyService.findAptitudeByCompanyId(user.getCompany().getCompanyId());
        if(company_aptitude==null||!company_aptitude.getStatus().equals("3")) {//未通过企业资质认证
            String message = "您的企业尚未通过资质认证，请前往会员中心-维护企业信息-企业资质进行维护！";
            return FrontUtils.showBaseMessage(request, model, message, COMPANY_APTITUDE_ADD);
        }
        //写作forMember，实为forCompany
        //写作companyId, 实为companyName
        Pagination p = SAbilityInquiryService.getPageForMember(statusType,user, inquiryTheme, startDate, endDate, companyId,cpn(pageNo), 20);
        model.addAttribute("pagination", p);
        if (modelId != null) {
            model.addAttribute("modelId", modelId);
        }
        //将查询条件放入model中
        model.addAttribute("statusType", statusType);
        model.addAttribute("inquiryTheme", inquiryTheme);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("companyId", companyId);
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, nextUrl);
    }

    /**
     * 协同制造-企业设备-待报价方案列表
     * @param statusType
     * @param inquiryTheme 询价主题
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @param companyId 询价企业
     * @param pageNo
     * @Author zhouyq
     * @return
     */
    @RequestMapping(value = "/member/device_inquiry_list.jspx")
    public String DeviceQuotelist(String statusType, String inquiryTheme, Date startDate, Date endDate, String companyId,
                       Integer modelId, Integer pageNo, HttpServletRequest request, ModelMap model) {
        String nextUrl = DEVICE_LIST;
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
        if(user.getCompany()==null){
            return FrontUtils.showMessage(request, model, "error.userWithoutCompany");
        }
        //判断企业资质
        SCompanyAptitude company_aptitude = synergyCompanyService.findAptitudeByCompanyId(user.getCompany().getCompanyId());
        if(company_aptitude==null||!company_aptitude.getStatus().equals("3")) {//未通过企业资质认证
            String message = "您的企业尚未通过资质认证，请前往会员中心-维护企业信息-企业资质进行维护！";
            return FrontUtils.showBaseMessage(request, model, message, COMPANY_APTITUDE_ADD);
        }
        //写作forMember，实为forCompany
        //写作companyId, 实为companyName
        Pagination p = SAbilityInquiryService.getPageForDevice(statusType, user, inquiryTheme, startDate, endDate, companyId,cpn(pageNo), 20);
        model.addAttribute("pagination", p);
        if (modelId != null) {
            model.addAttribute("modelId", modelId);
        }
        //将查询条件放入model中
        model.addAttribute("statusType", statusType);
        model.addAttribute("inquiryTheme", inquiryTheme);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("companyId", companyId);
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, nextUrl);
    }

    /**
     * @Author maocheng
     * @Date 2016/12/26
     * @param inquiryId 主键id
     * @param request request对象
     * @param response response对象
     * @param model model对象
     * @Desc 询价明细查看
     */
    @RequestMapping(value = "/member/ability_inquiry_view.jspx")
    public String view(Integer inquiryId, HttpServletRequest request,
                       HttpServletResponse response, ModelMap model) {
        String nextUrl = INQUIRY_VIEW;
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
        SAbilityInquiry inquiry= SAbilityInquiryService.byInquiryId(inquiryId);
        Content content = inquiry.getContent();
        // 获得本站栏目列表
        Set<Channel> rights = user.getGroup().getContriChannels();
        List<Channel> topList = channelMng.getTopList(site.getSiteId(), true);
        List<Channel> channelList = Channel.getListForSelect(topList, rights,
                true);
        model.addAttribute("inquiry",inquiry);
        model.addAttribute("content", content);
        model.addAttribute("site", site);
        model.addAttribute("channelList", channelList);
        model.addAttribute("sessionId",request.getSession().getId());
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, nextUrl);
    }

    /**
     * @Author zhouyq
     * @Date 2017/6/5
     * @param inquiryId 主键id
     * @param request request对象
     * @param response response对象
     * @param model model对象
     * @Desc 设备询价明细查看
     */
    @RequestMapping(value = "/member/device_inquiry_view.jspx")
    public String deviceInquiryView(Integer inquiryId, HttpServletRequest request,
                       HttpServletResponse response, ModelMap model) {
        String nextUrl = DEVICE_INQUIRY_VIEW;
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
        SDeviceInquiry inquiry= SAbilityInquiryService.byDeviceInquiryId(inquiryId);
        Content content = inquiry.getContent();
        // 获得本站栏目列表
        Set<Channel> rights = user.getGroup().getContriChannels();
        List<Channel> topList = channelMng.getTopList(site.getSiteId(), true);
        List<Channel> channelList = Channel.getListForSelect(topList, rights,
                true);
        model.addAttribute("inquiry",inquiry);
        model.addAttribute("content", content);
        model.addAttribute("site", site);
        model.addAttribute("channelList", channelList);
        model.addAttribute("sessionId",request.getSession().getId());
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, nextUrl);
    }

    /**
     * @author: gao jiangning
     * @desciption 2017/1/10 显示对 能力询价 进行 报价的ajax界面
     */
    @RequestMapping(value = "/member/ability_inquiry_quoteList.jspx")
    public void inquiryQuoteListAjax(Integer inquiryId, HttpServletRequest request, HttpServletResponse response){
        response.setCharacterEncoding("utf-8");
            response.setHeader("contentType", "text/json; charset=utf-8");
            CmsSite site = CmsUtils.getSite(request);
            CmsUser user = CmsUtils.getUser(request);
            MemberConfig mcfg = site.getConfig().getMemberConfig();
            // 没有开启会员功能
            if (!mcfg.isMemberOn()) {
                return;
            }
            if (user == null) {
                return;
            }
            //用户没有关联公司的话不能继续
            if(user.getCompany() == null){
                return;
            }
            //判断一下Id
            if (inquiryId == null) {
                return;
            }
            SAbilityInquiry aInquiry = SAbilityInquiryService.byInquiryId(inquiryId);
            //不能对非本公司的 能力询价 进行 报价
            if(!user.getCompany().getCompanyId().equals(aInquiry.getAbility().getCompanyId())){
                return;
            }
            try {
                PrintWriter writer = response.getWriter();
                writer.print(SAbilityInquiryService.getInquiryObjJson(inquiryId));
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
        }
    }

    /**
     * @author: zhouyq
     * @desciption 2017/6/5 显示对 设备询价 进行 报价的ajax界面
     */
    @RequestMapping(value = "/member/device_inquiry_quoteList.jspx")
    public void deviceQuoteListAjax(Integer inquiryId, HttpServletRequest request, HttpServletResponse response){
        response.setCharacterEncoding("utf-8");
        response.setHeader("contentType", "text/json; charset=utf-8");
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        MemberConfig mcfg = site.getConfig().getMemberConfig();
        // 没有开启会员功能
        if (!mcfg.isMemberOn()) {
            return;
        }
        if (user == null) {
            return;
        }
        //用户没有关联公司的话不能继续
        if(user.getCompany() == null){
            return;
        }
        //判断一下Id
        if (inquiryId == null) {
            return;
        }
        SDeviceInquiry deviceInquiry = SAbilityInquiryService.byDeviceInquiryId(inquiryId);
        try {
            PrintWriter writer = response.getWriter();
            writer.print(SAbilityInquiryService.getDeviceObjJson(inquiryId));
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @author: gao jiangning
     * @desciption 2017/1/10 保存报价信息的ajax
     * bj:对inquiryId所属的询价的报价
     */
    @RequestMapping(value = "/member/ability_inquiry_quoteSubmit.jspx")
    public void inquiryQuoteSaveAjax(Integer inquiryId, Double bj, HttpServletRequest request, HttpServletResponse response){
        response.setCharacterEncoding("utf-8");
        response.setHeader("contentType", "text/json; charset=utf-8");
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        MemberConfig mcfg = site.getConfig().getMemberConfig();
        // 没有开启会员功能
        if (!mcfg.isMemberOn()) {
            return;
        }
        if (user == null) {
            return;
        }
        //用户没有关联公司的话不能继续
        if(user.getCompany() == null){
            return;
        }
        //判断一下Id
        if (inquiryId == null) {
            return;
        }
        SAbilityInquiry aInquiry = SAbilityInquiryService.byInquiryId(inquiryId);
        //不能对非本公司的 能力询价 进行 报价
        if(!user.getCompany().getCompanyId().equals(aInquiry.getAbility().getCompanyId())){
            return;
        }
        //已报价的不能再报
        if(aInquiry.getQuotePrice()!=null){
            return;
        }
        try {
            PrintWriter writer = response.getWriter();
            Integer rows = SAbilityInquiryService.saveAbilityInquiryBj(inquiryId,bj);
            if(rows>0){
                writer.print("{\"rows\":\""+rows+"\"}");
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @author: zhouyq
     * @desciption 2017/6/5 保存报价信息的ajax
     * bj:对inquiryId所属的询价的报价
     */
    @RequestMapping(value = "/member/device_inquiry_quoteSubmit.jspx")
    public void deviceQuoteSaveAjax(Integer inquiryId, Double bj, HttpServletRequest request, HttpServletResponse response){
        response.setCharacterEncoding("utf-8");
        response.setHeader("contentType", "text/json; charset=utf-8");
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        MemberConfig mcfg = site.getConfig().getMemberConfig();
        // 没有开启会员功能
        if (!mcfg.isMemberOn()) {
            return;
        }
        if (user == null) {
            return;
        }
        // 用户没有关联公司的话不能继续
        if(user.getCompany() == null){
            return;
        }
        // 判断一下Id
        if (inquiryId == null) {
            return;
        }
        SDeviceInquiry aInquiry = SAbilityInquiryService.byDeviceInquiryId(inquiryId);
        // 已报价的不能再报
        if(aInquiry.getQuotePrice()!=null){
            return;
        }
        try {
            PrintWriter writer = response.getWriter();
            Integer rows = SAbilityInquiryService.saveDeviceInquiryBj(inquiryId, bj);
            if(rows > 0) {
                writer.print("{\"rows\":\""+rows+"\"}");
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 对能力询价(的报价) 下单/关闭
     * @param isOrder 1下单 0关闭
     * @return
     */
    @RequestMapping(value = "/member/ability_inquiry_orderORclose.jspx")
    public String orderORclose(Integer inquiryId, Integer isOrder, HttpServletRequest request, ModelMap model){
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        boolean isOrderBoolean;
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
        //判断一下Id
        if (inquiryId == null || isOrder == null) {
            return FrontUtils.showMessage(request, model, "error.needParams");
        }
        SAbilityInquiry aInq = SAbilityInquiryService.byInquiryId(inquiryId);
        //不能操作非本公司的
        if(!user.getCompany().getCompanyId().equals(aInq.getCompanyId())){
            return FrontUtils.showMessage(request, model, "error.noPermissionsView");
        }
        if(isOrder==1){
            isOrderBoolean=true;
        }else if(isOrder==0){
            isOrderBoolean=false;
        }else {
            return FrontUtils.showMessage(request, model, "error.exceptionParams");
        }
        SAbilityInquiryService.orderOrClose(inquiryId,isOrderBoolean);
        if(isOrder==1){
            SAbilityInquiry inquiry=SAbilityInquiryService.byInquiryId(inquiryId);
            SAbility ablity = inquiry.getAbility();
//        List<SDemandQuoteObj> quoteObj = sDemandQuoteService.byDemandQuoteObjId(demandQuoteId);
            SPOrder buy = new SPOrder();
            SPOrderObj o = new SPOrderObj();
//        buy.setOrderId(quote.getDemandQuoteId());
            o.setObjName(ablity.getAbilityName());
            o.setObjNum(inquiry.getDemandCount());
            o.setObjPrice(inquiry.getQuotePrice());
            buy.setSupplyContact(ablity.getContact());
            buy.setSupplyName(ablity.getAbilityName());
            buy.setSupplycompany(ablity.getCompany());
            buy.setSupplyMobile(ablity.getMobile());
            buy.setBuycompany(inquiry.getCompany());
            buy.setBuyMobile(inquiry.getMobile());
            buy.setBuyContact(inquiry.getContact());
            buy.setOperator(inquiry.getOperatorId());
            buy.setOrderDt(new java.sql.Date(System.currentTimeMillis()));

            buy.setNum(inquiry.getDemandCount());
            List<SPOrderObj> li = new ArrayList<SPOrderObj>();li.add(o);
            buy.setSOrderObjList(li);
            //buy.setPayerID(payerID);
            //buy.setpayerName;
            //订单号
            String guId = null;
            try {
                guId = GUIDGenerator.genGUID();
            } catch (Exception e) {
                e.printStackTrace();
            }
            String guidStart = "HTYWHL";
            String guidMIddle = guId.substring(0, 14);
            String guidEnd = guId.substring(guId.length()-6, guId.length());
            guId =  guidStart + guidMIddle + guidEnd;
            buy.setOrderId(guId);

            String uid = UUID.randomUUID().toString().replace("-","");//用来生成数据库的主键id
            o.setOrderObjId(uid);
            buy.setPrice(buy.getSPOrderObjPrice());
            buy.setState("0");
            buy.setSenderPostCode(ablity.getPostCode());
            buy.setSenderProv(ablity.getAddrProvince());
            buy.setSenderCity(ablity.getAddrCity());
            buy.setSenderArea(ablity.getAddrCounty());
            buy.setSenderAddress(ablity.getAddrStreet());
            buy.setReceiverPostCode(inquiry.getPostCode());
            buy.setReceiverProv(inquiry.getAddrProvince());
            buy.setReceiverCity(inquiry.getAddrCity());
            buy.setReceiverArea(inquiry.getAddrCounty());
            buy.setReceiverAddress(inquiry.getAddrStreet());
            buy.setFreighter(inquiry.getFreightBorne());
            sDemandQuoteService.saveOrder(buy);
        }

        String nextUrl = "/member/synergy_demand_abilityInquiryList.jspx?canshu=1";
        return FrontUtils.showSuccess(request, model, nextUrl);
    }
    /**
     * @Author 闫浩芮
     * @Date 2017/1/18 0018 11:52
     * 删除
     */
    @RequestMapping(value = "/member/ability_inquiry_delete.jspx")
    public String deleteAbilityInquiry(Integer inquiryId,String statusType,String inquiryTheme, Date startDate, Date endDate, String companyId,
                                       Integer modelId, Integer pageNo, HttpServletRequest request, ModelMap model){
        String nextUrl = "/member/synergy_demand_abilityInquiryList.jspx?canshu=1";
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        MemberConfig mcfg = site.getConfig().getMemberConfig();
        // 没有开启会员功能
        if (!mcfg.isMemberOn()) {
            return FrontUtils.showMessage(request, model, "member.memberClose");
        }
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
        if(user.getCompany()==null){
            return FrontUtils.showMessage(request, model, "error.userWithoutCompany");
        }
        //判断企业资质
        SCompanyAptitude company_aptitude = synergyCompanyService.findAptitudeByCompanyId(user.getCompany().getCompanyId());
        if(company_aptitude==null||!company_aptitude.getStatus().equals("3")) {//未通过企业资质认证
            String message = "您的企业尚未通过资质认证，请前往会员中心-维护企业信息-企业资质进行维护！";
            return FrontUtils.showBaseMessage(request, model, message, COMPANY_APTITUDE_ADD);
        }
        SAbilityInquiryService.delete(inquiryId);
        Pagination p = SAbilityInquiryService.getPageForMember(statusType,user, inquiryTheme, startDate, endDate, companyId,cpn(pageNo), 20);
        model.addAttribute("pagination", p);
        return FrontUtils.showSuccess(request, model, nextUrl);

    }

    /**
     * @author liuyang
     * @Date 2017/7/4 13:43
     * @return设备删除
     */

    @RequestMapping(value = "/member/device_inquiry_delete.jspx")
    public String deleteDeviceInquiry(Integer inquiryId,String statusType,String inquiryTheme, Date startDate, Date endDate, String companyId,
                                       Integer modelId, Integer pageNo, HttpServletRequest request, ModelMap model){
        String nextUrl = "/member/synergy_demand_deviceInquiryList.jspx?canshu=1";
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        MemberConfig mcfg = site.getConfig().getMemberConfig();
        // 没有开启会员功能
        if (!mcfg.isMemberOn()) {
            return FrontUtils.showMessage(request, model, "member.memberClose");
        }
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
        if(user.getCompany()==null){
            return FrontUtils.showMessage(request, model, "error.userWithoutCompany");
        }
        //判断企业资质
        SCompanyAptitude company_aptitude = synergyCompanyService.findAptitudeByCompanyId(user.getCompany().getCompanyId());
        if(company_aptitude==null||!company_aptitude.getStatus().equals("3")) {//未通过企业资质认证
            String message = "您的企业尚未通过资质认证，请前往会员中心-维护企业信息-企业资质进行维护！";
            return FrontUtils.showBaseMessage(request, model, message, COMPANY_APTITUDE_ADD);
        }
        SAbilityInquiryService.deleteDevice(inquiryId);
        Pagination p = SAbilityInquiryService.getPageForMember(statusType,user, inquiryTheme, startDate, endDate, companyId,cpn(pageNo), 20);
        model.addAttribute("pagination", p);
        return FrontUtils.showSuccess(request, model, nextUrl);

    }
    @Resource
    protected ContentMng contentMng;
    @Resource
    private ChannelMng channelMng;
    @Resource
    private SAbilityInquiryService SAbilityInquiryService;
    /**
     * @author liuyang
     * @Date 2017/5/2 14:12
     * @return  能力订单生成
     */
    @RequestMapping(value = "/member/xtzz_nlcdo.jspx")
    public String orderdo(HttpServletRequest request, HttpServletResponse response,
                          ModelMap model, Integer inquiryId,Integer abilityId) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
//        SAbility ablity = synergyManageService.byAbilityId(abilityId);
        SAbilityInquiry inquiry=SAbilityInquiryService.byInquiryId(inquiryId);
        SAbility ablity = inquiry.getAbility();
//        List<SDemandQuoteObj> quoteObj = sDemandQuoteService.byDemandQuoteObjId(demandQuoteId);
        SPOrder buy = new SPOrder();
        SPOrderObj o = new SPOrderObj();
//        buy.setOrderId(quote.getDemandQuoteId());
        o.setObjName(ablity.getAbilityName());
        o.setObjNum(inquiry.getDemandCount());
        o.setObjPrice(inquiry.getQuotePrice());
        buy.setSupplyContact(ablity.getContact());
        buy.setSupplyName(ablity.getAbilityName());
        buy.setSupplycompany(ablity.getCompany());
        buy.setSupplyMobile(ablity.getMobile());
        buy.setBuycompany(inquiry.getCompany());
        buy.setBuyMobile(inquiry.getMobile());
        buy.setBuyContact(inquiry.getContact());
        buy.setOperator(inquiry.getOperatorId());
        buy.setOrderDt(new java.sql.Date(System.currentTimeMillis()));

        buy.setNum(inquiry.getDemandCount());
        List<SPOrderObj> li = new ArrayList<SPOrderObj>();li.add(o);
        buy.setSOrderObjList(li);
        //buy.setPayerID(payerID);
        //buy.setpayerName;
        //订单号
        String guId = null;
        try {
            guId = GUIDGenerator.genGUID();
        } catch (Exception e) {
            e.printStackTrace();
        }
        guId = "HTYWHLGS" + "123" + guId.substring(guId.length()-6, guId.length());
        buy.setOrderId(guId);

        String uid = UUID.randomUUID().toString().replace("-","");//用来生成数据库的主键id
        o.setOrderObjId(uid);
        buy.setPrice(buy.getSPOrderObjPrice());
        buy.setState("0");
        sDemandQuoteService.saveOrder(buy);
      /*  return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_PAYMENT, SOFTWARE_PAY);*/
        //String nextUrl = "/yzyjyzxYzyck/index.jhtml";
        String nextUrl = "/member/synergy_demand_abilityInquiryList.jspx?canshu=1";
        return FrontUtils.showSuccess(request, model, nextUrl);
    }
    @Resource
    private SDemandQuoteService sDemandQuoteService;
    @Resource
    protected SynergyManageService synergyManageService;
    @Resource
    private SynergyCompanyService synergyCompanyService;
}
