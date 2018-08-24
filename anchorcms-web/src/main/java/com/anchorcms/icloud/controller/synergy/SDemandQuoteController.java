package com.anchorcms.icloud.controller.synergy;

import com.anchorcms.cms.service.main.ChannelMng;
import com.anchorcms.common.utils.CmsUtils;
import com.anchorcms.common.utils.FrontUtils;
import com.anchorcms.core.model.CmsSite;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.core.model.MemberConfig;
import com.anchorcms.icloud.controller.logistics.DeliverGoods;
import com.anchorcms.icloud.dao.synergy.OrderPayDao;
import com.anchorcms.icloud.model.commservice.SUnionCity;
import com.anchorcms.icloud.model.logistics.SLogistics;
import com.anchorcms.icloud.model.payment.SPOrder;
import com.anchorcms.icloud.model.payment.SPOrderObj;
import com.anchorcms.icloud.model.synergy.*;
import com.anchorcms.icloud.service.logistics.LogisticsService;
import com.anchorcms.icloud.service.synergy.*;
import com.anchorcms.icloud.service.unionCity.unionCityService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import payment.api.util.GUIDGenerator;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.anchorcms.common.constants.Constants.TPLDIR_MEMBER;
import static com.anchorcms.common.constants.Constants.TPLDIR_PAYMENT;

/**
 * Copyright @ 2016 Shandong jxdinfo software co,ltd.
 * All right reserved.
 * Created by Gao Jiangning
 *
 * @Date 2016/12/30 0030
 * @Desc 对需求进行报价和报价管理的controller
 */
@Controller
public class SDemandQuoteController {
    public static final String DEMAND_QUOTE = "tpl.demandQuote";
    public static final String xtzzOrderDetail = "tpl.xtzzOrderDetail";
    public static final String xtzzWuliuOrderDetail = "tpl.xtzzWuliuOrderDetail";
    public static final String COMPANY_APTITUDE_ADD = "/member/company_edit.jspx";
    /**
     * @author: gao jiangning
     * @desciption 2017/1/5 根据demandId 对相应的需求进行报价的页面
     */
    @RequestMapping(value = "/member/synergy_demand_quote.jspx")
    public String toQuotePage(Integer demandId, HttpServletRequest request, ModelMap model){
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        MemberConfig mcfg = site.getConfig().getMemberConfig();

        SDemand targetDemand = sDemandService.byDemandId(demandId);
        String nextUrl="/xtzzNlczs/xtzz_synergy_demand_preview.jspx?id="+demandId;
        // 没有开启会员功能
        if (!mcfg.isMemberOn()) {
            return FrontUtils.showMessage(request, model, "member.memberClose");
        }
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
        //用户没有关联公司的话不能报价？
        if(user.getCompany() == null){
            return FrontUtils.showMessage(request, model, "error.userWithoutCompany");
        }
        //判断企业资质
        SCompanyAptitude company_aptitude = synergyCompanyService.findAptitudeByCompanyId(user.getCompany().getCompanyId());
        if(company_aptitude==null||!company_aptitude.getStatus().equals("3")) {//未通过企业资质认证
            String message = "您的企业尚未通过资质认证，请前往会员中心-维护企业信息-企业资质进行维护！";
            return FrontUtils.showBaseMessage(request, model, message, COMPANY_APTITUDE_ADD);
        }
        //判断一下demandId能不能用，[在数据库中是否存在
        if (demandId == null) {
            return FrontUtils.showMessage(request, model, "error.needParams");
        }
        //用户 不能报自己发的需求的价
        if(user.getCompany().getCompanyId().equals(targetDemand.getCompanyId())){
            return FrontUtils.showBaseMessage(request,model,"error.noPermissionsView", nextUrl);
        }
        //公司不能对同一需求进行二次报价
        if(sDemandQuoteService.hasQuoted(demandId,user.getCompany().getCompanyId())){
            return FrontUtils.showBaseMessage(request,model,"error.hasquoted", nextUrl);
        }
        model.addAttribute("site", site);
        model.addAttribute("channel",channelMng.findById(104));
        model.addAttribute("sessionId",request.getSession().getId());
        model.addAttribute("demand",targetDemand);
        model.addAttribute("user", user);
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, DEMAND_QUOTE);
    }

    /**
     * @author: gao jiangning
     * @desciption 2017/1/5 保存前台传来的报价参数和报价对象
     */
    @RequestMapping(value = "/member/synergy_demandquote_save.jspx")
    public String save(HttpServletRequest request, ModelMap model,
                       Integer demandId,
                       String offerExplan,
                       String contact,
                       String telephone,
                       String mobile,
                       String email,
                       String fax,
                       String postCode,
                       String addrProvince,
                       String addrCity,
                       String addrCounty,
                       String addrStreet,
                       Date deadlineDt,
                       String demandQuoteObjsJsonStr){
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        MemberConfig mcfg = site.getConfig().getMemberConfig();
        SDemand targetDemand = sDemandService.byDemandId(demandId);
        // 没有开启会员功能
        if (!mcfg.isMemberOn()) {
            return FrontUtils.showMessage(request, model, "member.memberClose");
        }
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
        //用户没有关联公司的话不能报价？
        if(user.getCompany() == null){
            return FrontUtils.showMessage(request, model, "error.userWithoutCompany");
        }
        //判断企业资质
        SCompanyAptitude company_aptitude = synergyCompanyService.findAptitudeByCompanyId(user.getCompany().getCompanyId());
        if(company_aptitude==null||!company_aptitude.getStatus().equals("3")) {//未通过企业资质认证
            String message = "您的企业尚未通过资质认证，请前往会员中心-维护企业信息-企业资质进行维护！";
            return FrontUtils.showBaseMessage(request, model, message, COMPANY_APTITUDE_ADD);
        }
        //判断一下demandId
        if (demandId == null) {
            return FrontUtils.showMessage(request, model, "error.needParams");
        }
        //用户 不能报自己发的需求的价
        if(user.getCompany().getCompanyId().equals(targetDemand.getCompanyId())){
            return FrontUtils.showMessage(request, model, "error.noPermissionsView");
        }
        SDemandQuote sDemandQuote = new SDemandQuote();
        //接收页面传来的值
        sDemandQuote.setDemand(targetDemand);
        sDemandQuote.setOfferExplan(offerExplan);
        sDemandQuote.setContact(contact);
        sDemandQuote.setTelephone(telephone);
        sDemandQuote.setMobile(mobile);
        sDemandQuote.setEmail(email);
        sDemandQuote.setFax(fax);
        sDemandQuote.setPostCode(postCode);
        sDemandQuote.setAddrProvince(addrProvince);
        sDemandQuote.setAddrCity(addrCity);
        sDemandQuote.setAddrCounty(addrCounty);
        sDemandQuote.setAddrStreet(addrStreet);
        sDemandQuote.setDeadlineDt(deadlineDt);
        //默认值set
        sDemandQuote.setCompany(user.getCompany());
        sDemandQuote.setOperatorId(user.getUserId().toString());
        sDemandQuote.setUpdateDt(new Date(System.currentTimeMillis()));
        sDemandQuote.setCreaterId(user.getUserId().toString());
        sDemandQuote.setReleaseDt(sDemandQuote.getUpdateDt());
        sDemandQuote.setCreateDt(sDemandQuote.getUpdateDt());
        sDemandQuote.setSelectedStatus("0");
        sDemandQuote.setDeFlag("1");
        //前往service层保存业务
        sDemandQuoteService.save(sDemandQuote,demandQuoteObjsJsonStr);
        //保存完了去需求池展示页
        String nextUrl = "/xtzzXqczs/index.jhtml";
        return FrontUtils.showSuccess(request, model, nextUrl);
    }
/**
 * @author liuyang
 * @Date 2018/4/27 9:02
 * @return
 * 需求池能力池 对接保存controller
 */
    @RequestMapping(value = "/bigdata_demandquote_save.jspx")
    public String save2(HttpServletRequest request, ModelMap model,
                       Integer demandId
                       ){
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        String nextUrl = "/cyrhxqc/index.jhtml";
        MemberConfig mcfg = site.getConfig().getMemberConfig();
        String cyrh =request.getParameter("cyrh");
        model.addAttribute("cyrh", cyrh);
        SDemand targetDemand = sDemandService.byDemandId(demandId);
        // 没有开启会员功能
        if (!mcfg.isMemberOn()) {
            return FrontUtils.showMessage(request, model, "member.memberClose");
        }
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
        //用户没有关联公司的话不能报价？
        if(user.getCompany() == null){
            return FrontUtils.showBaseMessage(request, model, "error.userWithoutCompany", nextUrl);
        }
        //判断企业资质
//        SCompanyAptitude company_aptitude = synergyCompanyService.findAptitudeByCompanyId(user.getCompany().getCompanyId());
//        if(company_aptitude==null||!company_aptitude.getStatus().equals("3")) {//未通过企业资质认证
//            String message = "您的企业尚未通过资质认证，请前往会员中心-维护企业信息-企业资质进行维护！";
//            return FrontUtils.showBaseMessage(request, model, message, COMPANY_APTITUDE_ADD);
//        }
        //不能重复报价
        if(sDemandQuoteService.hasQuoted2(demandId,user.getCompany().getCompanyId())){
            return FrontUtils.showBaseMessage(request,model,"error.userCyrhdj", nextUrl);
        }
        //判断一下demandId
        if (demandId == null) {
            return FrontUtils.showBaseMessage(request, model, "error.needParams",nextUrl);
        }
        //用户 不能报自己发的需求的价
        if(user.getCompany().getCompanyId().equals(targetDemand.getCompanyId())){
            return FrontUtils.showBaseMessage(request, model, "error.userCyrhxq",nextUrl);
        }
        SBigdataDemandQuote sBigdataDemandQuote = new SBigdataDemandQuote();
        //接收页面传来的值

        sBigdataDemandQuote.setCompanyName(user.getCompany().getCompanyName());
        sBigdataDemandQuote.setDemand(targetDemand);
        sBigdataDemandQuote.setCompany(user.getCompany());
        sBigdataDemandQuote.setAddrCity(user.getCompany().getAddrCity());
        sBigdataDemandQuote.setAddrCounty(user.getCompany().getAddrCounty());
        sBigdataDemandQuote.setAddrProvince(user.getCompany().getAddrProvince());
        sBigdataDemandQuote.setAddrStreet(user.getCompany().getAddrStreet());
        sBigdataDemandQuote.setCompanyType(user.getCompany().getCompanyType());
        sBigdataDemandQuote.setReleaseDt(new Date(System.currentTimeMillis()));
        sBigdataDemandQuote.setMobile(user.getCompany().getMobile());
        sBigdataDemandQuote.setCompanyType(user.getCompany().getCompanyType());
        sBigdataDemandQuote.setCompanyScale(user.getCompany().getCompanyScale());
        sBigdataDemandQuote.setStatus("0");//需求池对接状态为0
        sBigdataDemandQuote.setCompanyCode(targetDemand.getCompanyId());
        sBigdataDemandQuote.setDeFlag("0");
        //前往service层保存业务
        sDemandQuoteService.save2(sBigdataDemandQuote);
        //保存完了去需求池展示页
        return FrontUtils.showSuccess(request, model, nextUrl);
    }
    @RequestMapping(value = "/bigdata_abilityquote_save.jspx")
    public String save3(HttpServletRequest request, ModelMap model,
                        Integer abilityId){
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        String nextUrl = "/cyrhnlc/index.jhtml";
        String cyrh =request.getParameter("cyrh");
        model.addAttribute("cyrh", cyrh);
        MemberConfig mcfg = site.getConfig().getMemberConfig();
//        SDemand targetDemand = sDemandService.byDemandId(demandId);
        SAbility ability = synergyCreateService.byContentId(abilityId);
        // 没有开启会员功能
        if (!mcfg.isMemberOn()) {
            return FrontUtils.showMessage(request, model, "member.memberClose");
        }
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
        //用户没有关联公司的话不能报价？
        if(user.getCompany() == null){
            return FrontUtils.showMessage(request, model, "error.userWithoutCompany");
        }
        //用户 不能报自己发的能力的价
        if(user.getCompany().getCompanyId().equals(ability.getCompanyId())){
            return FrontUtils.showBaseMessage(request, model, "error.userCyrhnl",nextUrl);
        }
        //不能重复报价
        if(sDemandQuoteService.hasQuoted3(abilityId,user.getCompany().getCompanyId())){
            return FrontUtils.showBaseMessage(request,model,"error.userCyrhdj", nextUrl);
        }
        //判断企业资质
//        SCompanyAptitude company_aptitude = synergyCompanyService.findAptitudeByCompanyId(user.getCompany().getCompanyId());
//        if(company_aptitude==null||!company_aptitude.getStatus().equals("3")) {//未通过企业资质认证
//            String message = "您的企业尚未通过资质认证，请前往会员中心-维护企业信息-企业资质进行维护！";
//            return FrontUtils.showBaseMessage(request, model, message, COMPANY_APTITUDE_ADD);
//        }

        SBigdataDemandQuote sBigdataDemandQuote = new SBigdataDemandQuote();
        //接收页面传来的值

        sBigdataDemandQuote.setCompanyName(user.getCompany().getCompanyName());
        sBigdataDemandQuote.setAbility(ability);
        sBigdataDemandQuote.setCompany(user.getCompany());
        sBigdataDemandQuote.setAddrCity(user.getCompany().getAddrCity());
        sBigdataDemandQuote.setAddrCounty(user.getCompany().getAddrCounty());
        sBigdataDemandQuote.setAddrProvince(user.getCompany().getAddrProvince());
        sBigdataDemandQuote.setAddrStreet(user.getCompany().getAddrStreet());
        sBigdataDemandQuote.setCompanyType(user.getCompany().getCompanyType());
        sBigdataDemandQuote.setReleaseDt(new Date(System.currentTimeMillis()));
        sBigdataDemandQuote.setMobile(user.getCompany().getMobile());
        sBigdataDemandQuote.setCompanyType(user.getCompany().getCompanyType());
        sBigdataDemandQuote.setCompanyScale(user.getCompany().getCompanyScale());
        sBigdataDemandQuote.setStatus("1");//能力池对接状态为0
        sBigdataDemandQuote.setCompanyCode(ability.getCompany().getCompanyId());
        sBigdataDemandQuote.setDeFlag("0");
        //前往service层保存业务
        sDemandQuoteService.save2(sBigdataDemandQuote);
        //保存完了去需求池展示页

        return FrontUtils.showSuccess(request, model, nextUrl);
    }
/**
 * @author liuyang
 * @Date 2018/4/28 8:35
 * @return
 * 产融需求能力完成对接
 */

    @RequestMapping(value = "/member/Bigdata_editOrderStatus.jspx")
    public String editOrderStatus(HttpServletRequest request, HttpServletResponse response, ModelMap model, Integer demandQuoteId, String setstatus, String tabState,String isOnline,
                                  String nextUrl) throws Exception {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
      /*  return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_PAYMENT, NEXTURL);*/
        this.ALDPayService.editOrderStatus2(demandQuoteId, setstatus,isOnline);
//        if (tabState == "1") {
//            String nextUrl = "/member/bigdata_ability_quote_list.jspx?status=1";
//            return FrontUtils.showSuccess(request, model, nextUrl);
//        }
//        if (tabState == "0") {
//            String nextUrl = "/member/bigdata_demand_quote_list.jspx?status=0";
//            return FrontUtils.showSuccess(request, model, nextUrl);
//        }

        return FrontUtils.showSuccess(request, model, nextUrl);
    }




    /**
     * @author: gao jiangning
     * @desciption 2017/1/5 后台页面 对报价进行优选的 报价列表 ajax
     */
    @RequestMapping(value = "/member/synergy_demandQuoteList_getListAjax.jspx", method = RequestMethod.POST)
    public void getListAjax(Integer demandId, Integer pageNo, HttpServletRequest request, HttpServletResponse response){
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
        //判断一下demandId
        if (demandId == null) {
            return;
        }
        if(pageNo == null){
            pageNo = 1;
        }
        try {
            PrintWriter writer = response.getWriter();
//            writer.print(sDemandQuoteService.getQuoteListJson(demandId,pageNo,8));
            writer.print(sDemandQuoteService.getListJson(demandId));
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @author: gao jiangning
     * @desciption 2017/1/6 后台页面 对多条报价进行 优选/取消优选 的 ajax
     * toggleType: 1为进行优选 0为取消优选
     */
    @RequestMapping(value = "/member/synergy_demandQuoteList_toggleSelectAjax.jspx", method = RequestMethod.POST)
    public void toggleSelect(Integer demandId, String quoteIds, String toggleType, HttpServletRequest request,
                             HttpServletResponse response){
        boolean isSelect;
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
        //判断一下demandId是否为空
        if (demandId == null) {
            return;
        }
        //判断一下demand是否为用户公司的 不能优选非本公司的需求的 报价
        if (!user.getCompany().getCompanyId().equals(sDemandService.byDemandId(demandId).getCompanyId())) {
            return;
        }
        //判断优选 or 取消优选， 参数不存在则直接返回
        if("1".equals(toggleType)){
            isSelect= true;
        }else if("0".equals(toggleType)){
            isSelect = false;
        }else {
            return;
        }
        try {
            PrintWriter writer = response.getWriter();
            if(isSelect){
                writer.print("{\"rows\":\""+sDemandQuoteService.selectQuotes(quoteIds)+"\"}");
            }else{
                writer.print("{\"rows\":\""+sDemandQuoteService.cancelSelectedQuotes(quoteIds)+"\"}");
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @author: gao jiangning
     * @desciption 2017/1/7 后台页面 对已优选报价进行下单的 下单列表 ajax
     */
    @RequestMapping(value = "/member/synergy_demandQuoteOrder_getListAjax.jspx", method = RequestMethod.POST)
    public void getOrderListAjax(Integer demandQuoteFlag, Integer quoteId, HttpServletRequest request, HttpServletResponse response){
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
        //判断一下quoteId
        if (quoteId == null) {
            return;
        }
        //判断一下demand是否为用户公司的 不能对 非本公司的需求的报价 进行下单
        if (!user.getCompany().getCompanyId().equals(sDemandQuoteService.byDemandQuoteId(quoteId).getDemand().getCompanyId())) {
            return;
        }
        try {
            PrintWriter writer = response.getWriter();
            if ((demandQuoteFlag != null) && (!"".equals(demandQuoteFlag))) {
                writer.print(sDemandQuoteService.getDemandQuoteListJson(quoteId));
            } else {
                writer.print(sDemandQuoteService.getOrderListJson(quoteId));
            }

            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @author: gao jiangning
     * @desciption 2017/1/7 进行下单的业务逻辑处理【1.update报价表(状态位+分配量) 2.update需求表状态为已下单】
     */
    @RequestMapping(value = "/member/synergy_demandQuoteOrder_submitOrderAjax.jspx")
    public void excuteOrder(String orderJson, Integer quoteId, HttpServletRequest request,
                            HttpServletResponse response, Integer demandId){
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
        //判断一下quoteId
        if (quoteId == null) {
            return;
        }
        //判断一下demand是否为用户公司的 不能对 非本公司的需求的报价 进行下单
        if (!user.getCompany().getCompanyId().equals(sDemandQuoteService.byDemandQuoteId(quoteId).getDemand().getCompanyId())) {
            return;
        }
        try {
            PrintWriter writer = response.getWriter();
            sDemandQuoteService.excuteOrder(orderJson,quoteId);
            SDemandQuote afterUpd = sDemandQuoteService.byDemandQuoteId(quoteId);
            if("2".equals(afterUpd.getSelectedStatus())){
                writer.print("{\"rows\":\"1\"}");
            }
            writer.flush();
            writer.close();

            //生成订单处理
            SDemand demand=sDemandService.byDemandId(demandId);
            SDemandQuote quote = sDemandQuoteService.byDemandQuoteId(quoteId);
            SDemandObj demandObj;
            List<SDemandQuoteObj> quoteObj = quote.getDemandQuoteObjList();
            List<SPOrderObj> li = new ArrayList<SPOrderObj>();
            for(SDemandQuoteObj bean : quoteObj){
                SPOrderObj o = new SPOrderObj();
                o.setObjNum(bean.getDistributionAmount());
                o.setObjPrice(bean.getOffer());
                int id = bean.getDemandObjid();
                demandObj= sDemandService.byDemandObjId(id);
                o.setObjName(demandObj.getObjectName());
                String uid = UUID.randomUUID().toString().replace("-","");//用来生成数据库的主键id
                o.setOrderObjId(uid);
//            o.setOrderObjId(String.valueOf(bean.getDemandQuoteObjid()));
                this.sDemandQuoteService.saveSPOrderObj(o);
                li.add(o);
            }
            SPOrder buy = new SPOrder();
            buy.setSOrderObjList(li);
            buy.setBuyContact(demand.getContact());
            buy.setSupplyName(demand.getInquiryTheme());
            buy.setBuycompany(demand.getCompany());
            buy.setSupplycompany(quote.getCompany());
            buy.setBuyMobile(demand.getMobile());
            buy.setSupplyContact(quote.getContact());
            buy.setSupplyMobile(quote.getMobile());
            buy.setReceiverPostCode(demand.getPostCode());
            buy.setReceiverProv(demand.getAddrProvince());
            buy.setReceiverCity(demand.getAddrCity());
            buy.setReceiverArea(demand.getAddrCounty());
            buy.setReceiverAddress(demand.getAddrStreet());
            buy.setSenderPostCode(quote.getPostCode());
            buy.setSenderProv(quote.getAddrProvince());
            buy.setSenderCity(quote.getAddrCity());
            buy.setSenderArea(quote.getAddrCounty());
            buy.setSenderAddress(quote.getAddrStreet());

            buy.setOperator(quote.getOperatorId());
            buy.setPrice(buy.getSPOrderObjPrice());
            buy.setNum(buy.getSPOrderObjNum());
            buy.setFreighter(demand.getFreightBorne());
            buy.setOrderDt(new Date(System.currentTimeMillis()));
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
            buy.setState("0");
            sDemandQuoteService.saveOrder(buy);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @author: gao jiangning
     * @desciption 2017/1/7 后台页面 报价详情列表 ajax
     */
    @RequestMapping(value = "/member/synergy_demandQuoteDetail_getQuoteAjax.jspx", method = RequestMethod.POST)
    public void getQuoteAjax(Integer quoteId, HttpServletRequest request, HttpServletResponse response){
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
        //判断一下quoteId
        if (quoteId == null) {
            return;
        }
        //判断一下quote是否为用户公司的 不能查看非本公司的报价
        if (!user.getCompany().getCompanyId().equals(sDemandQuoteService.byDemandQuoteId(quoteId).getCompanyId())) {
            return;
        }
        try {
            PrintWriter writer = response.getWriter();
            writer.print(sDemandQuoteService.getQuoteDetailJson(quoteId));
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Resource
    private SDemandService sDemandService;
    @Resource
    private OrderPayDao orderPayDao;
    @Resource
    private SDemandQuoteService sDemandQuoteService;
    @Resource
    private ChannelMng channelMng;
    /**
     * @author liuyang
     * @Date 2017/5/2 14:12
     * @return   需求订单生成
     */
    @RequestMapping(value = "/member/xtzz_orderdo.jspx")
    public String orderdo(HttpServletRequest request, HttpServletResponse response,
                          ModelMap model, Integer demandQuoteId, Integer demandId) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
        SDemand demand=sDemandService.byDemandId(demandId);
        SDemandQuote quote = sDemandQuoteService.byDemandQuoteId(demandQuoteId);
        SDemandObj demandObj;
//        SDemandQuoteObj quoteObj= sDemandQuoteService.getDemandQuoteListJson(demandQuoteId);
//       List<SDemandQuoteObj> quoteObj = sDemandQuoteService.byDemandQuoteObjId(demandQuoteId);
       List<SDemandQuoteObj> quoteObj = quote.getDemandQuoteObjList();
        List<SPOrderObj> li = new ArrayList<SPOrderObj>();
        for(SDemandQuoteObj bean : quoteObj){
            SPOrderObj o = new SPOrderObj();
            o.setObjNum(bean.getDistributionAmount());
            o.setObjPrice(bean.getOffer());
           int id = bean.getDemandObjid();
            demandObj= sDemandService.byDemandObjId(id);
            o.setObjName(demandObj.getObjectName());
            String uid = UUID.randomUUID().toString().replace("-","");//用来生成数据库的主键id
            o.setOrderObjId(uid);
//            o.setOrderObjId(String.valueOf(bean.getDemandQuoteObjid()));
            this.sDemandQuoteService.saveSPOrderObj(o);
            li.add(o);
        }
        SPOrder buy = new SPOrder();
        buy.setSOrderObjList(li);
        buy.setBuyContact(demand.getContact());
        buy.setSupplyName(demand.getInquiryTheme());
        buy.setBuycompany(demand.getCompany());
        buy.setSupplycompany(quote.getCompany());
        buy.setBuyMobile(demand.getMobile());
        buy.setSupplyContact(quote.getContact());
        buy.setSupplyMobile(quote.getMobile());

        buy.setOperator(quote.getOperatorId());
        buy.setPrice(buy.getSPOrderObjPrice());
        buy.setNum(buy.getSPOrderObjNum());
        buy.setFreighter(demand.getFreightBorne());
        buy.setOrderDt(new Date(System.currentTimeMillis()));
        //buy.setpayerName;
        //订单号
        String guId = null;
        try {
            guId = GUIDGenerator.genGUID();
        } catch (Exception e) {
            e.printStackTrace();
        }
        buy.setOrderId(guId);
        buy.setState("0");
//        this.getSession().save(buy);
        sDemandQuoteService.saveOrder(buy);
      /*  return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_PAYMENT, SOFTWARE_PAY);*/
        //String nextUrl = "/yzyjyzxYzyck/index.jhtml";
        String nextUrl = "/member/synergy_demand_list.jspx";
        return FrontUtils.showSuccess(request, model, nextUrl);
    }
    /**
     * @author liuyang
     * @Date 2017/5/3 14:41
     * @return  协同制造明细
     */
    @RequestMapping(value = "/member/xtzz_order_detail.jspx")
    public String xtzz_order_detail(HttpServletRequest request, HttpServletResponse response, ModelMap model,String orderId, String id,Integer demandQuoteId
    ) throws Exception {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);

        SPOrder order = ALDPayService.findOrderById(orderId);
//        SPOrderObj obj=orderPayDao.getOrderObjById(id);

//        return FrontUtils.showSuccess(request, model, softwareDetail);
        model.addAttribute("order", order);

//        model.addAttribute("obj", obj);
        List<SPOrderObj> list = order.getSOrderObjList();
        model.addAttribute("orderList", list);
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_PAYMENT, xtzzOrderDetail);
    }
//    @RequiresPermissions("member:spareAdminDemandDetailList")
//    @RequestMapping("/member/spareAdminDemandDetailList.jspx")
//    public String findDetailById(String orderId, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
//        CmsSite site = CmsUtils.getSite(request);
//        FrontUtils.frontData(request, model, site);
//        if (orderId != null) {
//            SPOrder buy = sDemandQuoteService.findOrderById(orderId);
//            model.addAttribute("buy", buy);
//        }
//        return FrontUtils.getTplPath(request, site.getSolutionPath(),
//                TPLDIR_PAYMENT, xtzzOrderDetail);
//    }

    /**
     * @author: zhouyq
     * @desciption 2017/6/30 获取物流信息
     */
    @RequestMapping(value = "/member/xtzz_wuliuorder_detail_ajax.jspx", method = RequestMethod.POST)
    public void xtzz_wuliuorder_detail(HttpServletRequest request, HttpServletResponse response, ModelMap model, String orderId) throws Exception{
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
        // 判断一下orderId
        if (orderId == null) {
            return;
        }
        try {
            PrintWriter writer = response.getWriter();
            DeliverGoods deliverGoods = new DeliverGoods();
//            String orderNO = "K29010323933";
            String deliverInfo = deliverGoods.selLogisticsOrder(orderId);
//            model.addAttribute("deliverInfo", deliverInfo);
            writer.print(deliverInfo);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @author: zhouyq
     * @desciption 2017/6/30 获取物流信息
     */
    @RequestMapping(value = "/member/xtzz_wuliuorder_detail.jspx")
    public String xtzz_wuliuorder_detail(HttpServletRequest request, HttpServletResponse response, ModelMap model,String orderId,
                                         String id,Integer demandQuoteId) throws Exception {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);

        SPOrder order = ALDPayService.findOrderById(orderId);
        model.addAttribute("order", order);
        if (order != null) { // 直接走前台物流
            List<SPOrderObj> list = order.getSOrderObjList();
            model.addAttribute("orderList", list);
        }
        // 查询物流详情
        DeliverGoods deliverGoods = new DeliverGoods();
        String deliverInfo = deliverGoods.selLogisticsOrder(orderId);
        model.addAttribute("deliverInfo", deliverInfo);
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_PAYMENT, xtzzWuliuOrderDetail);
    }

    /**
     * @author: zhouyq
     * @desciption 2017/7/14 获取物流信息ajax(前台)
     */
    @RequestMapping(value = "/getWuliuDetail.jspx", method = RequestMethod.POST)
    public void getWuliuDetail(String orderId, HttpServletResponse response){
        response.addHeader("Cache-Control","no-cache");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        try {
            DeliverGoods deliverGoods = new DeliverGoods();
            String deliverInfo = null;
            try {
                deliverInfo = deliverGoods.selLogisticsOrder(orderId);
            } catch (Exception e) {
                e.printStackTrace();
            }
            PrintWriter writer = response.getWriter();
            writer.print(deliverInfo);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @author: zhouyq
     * @desciption 2017/7/14 寄件服务(前台)
     */
    @RequestMapping(value = "/member/generateDeliverOrder.jspx", method = RequestMethod.POST)
    public void generateDeliverOrder(String supplyCompany, String supplyContact, String senderPostCode, String supplyMobile, String senderProv, String senderCity,
                                     String senderArea, String senderAddress, String buyCompany, String buyContact, String receiverPostCode, String buyMobile,
                                     String receiverProv, String receiverCity, String receiverArea, String receiverAddress,
                                     HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        response.addHeader("Cache-Control","no-cache");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
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
        // 发货
        DeliverGoods deliverGoods = new DeliverGoods();
        String deliverResult = null;
        // 订单信息
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
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 设置日期格式
        String createorderTime = df.format(new java.util.Date()); // new Date()为获取当前系统时间，也可使用当前时间戳
        supplyCompany = java.net.URLDecoder.decode(supplyCompany, "utf-8");
        supplyContact = java.net.URLDecoder.decode(supplyContact, "utf-8");
        senderProv = java.net.URLDecoder.decode(senderProv, "utf-8");
        senderCity = java.net.URLDecoder.decode(senderCity, "utf-8");
        senderArea = java.net.URLDecoder.decode(senderArea, "utf-8");
        senderAddress = java.net.URLDecoder.decode(senderAddress, "utf-8");
        buyCompany = java.net.URLDecoder.decode(buyCompany, "utf-8");
        buyContact = java.net.URLDecoder.decode(buyContact, "utf-8");
        receiverProv = java.net.URLDecoder.decode(receiverProv, "utf-8");
        receiverCity = java.net.URLDecoder.decode(receiverCity, "utf-8");
        receiverArea = java.net.URLDecoder.decode(receiverArea, "utf-8");
        receiverAddress = java.net.URLDecoder.decode(receiverAddress, "utf-8");
        String creLogisticsParam = guId + "," + supplyContact + "," + senderPostCode + "," + supplyMobile
                + "," + senderProv + "," + senderCity + "," + senderArea + "," + senderAddress
                + "," + buyContact + "," + receiverPostCode + "," + buyMobile + "," + receiverProv
                + "," + receiverCity + "," + receiverArea + "," + receiverAddress + "," + createorderTime
                + "," + supplyCompany + "," + buyCompany;
        try {
            // 订单创建结果
            deliverResult = deliverGoods.createLogisticsOrder(creLogisticsParam);
            if (deliverResult.equals("true")) { // 创建订单成功
                // 生成物流订单
                creLogisticsEntity(creLogisticsParam);
            }
            PrintWriter writer = response.getWriter();
            writer.print(deliverResult);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @author zhouyq
     * @Date 2017/7/14
     * @return 创建物流订单(前台)
     */
    private void creLogisticsEntity(String creLogisticsParam) throws ParseException {
        // 参数数组
        String[] creLogisticsParaArray = creLogisticsParam.split(",");
        SLogistics sLogistics = new SLogistics();
        sLogistics.setTxlogisticId(creLogisticsParaArray[0]);
        sLogistics.setEccompanyId("HTYW");
        sLogistics.setLogisticproviderId("ZT");
        sLogistics.setOrderType(1);
        sLogistics.setServiceType("11");
        sLogistics.setCreateOrderTime(creLogisticsParaArray[15]);
        sLogistics.setPayType(2);
        sLogistics.setLogisticsOrderState("00");
        sLogistics.setIsBackground("0");
        sLogistics.setSupplyCompany(creLogisticsParaArray[16]);
        sLogistics.setSenderContact(creLogisticsParaArray[1]);
        sLogistics.setSenderPostcode(creLogisticsParaArray[2]);
        sLogistics.setSenderMobile(creLogisticsParaArray[3]);
        sLogistics.setSenderProv(creLogisticsParaArray[4]);
        sLogistics.setSenderCity(creLogisticsParaArray[5]);
        sLogistics.setSenderArea(creLogisticsParaArray[6]);
        sLogistics.setSenderAddress(creLogisticsParaArray[7]);

        sLogistics.setBuyCompany(creLogisticsParaArray[17]);
        sLogistics.setReceiverContact(creLogisticsParaArray[8]);
        sLogistics.setReceiverPostcode(creLogisticsParaArray[9]);
        sLogistics.setReceiverMobile(creLogisticsParaArray[10]);
        sLogistics.setReceiverProv(creLogisticsParaArray[11]);
        sLogistics.setReceiverCity(creLogisticsParaArray[12]);
        sLogistics.setReceiverArea(creLogisticsParaArray[13]);
        sLogistics.setReceiverAddress(creLogisticsParaArray[14]);
        logisticsService.creLogisticsEntity(sLogistics);
    }

    /**
     * @author: zhouyq
     * @desciption 2017/8/7 盟市行报名(前台)
     */
    @RequestMapping(value = "unionCitySignUp.jspx")
    public String generateDeliverOrder(String contact, String mobile, String companyName, String unionDuties, String email,
                                     String companyType, String addrCity, String addrCounty, String addrStreet,
                                     HttpServletRequest request, HttpServletResponse response, ModelMap model ,Integer unionId) throws Exception {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        MemberConfig mcfg = site.getConfig().getMemberConfig();
        // 没有开启会员功能
//        if (!mcfg.isMemberOn()) {
//            return FrontUtils.showMessage(request, model, "member.memberClose");
//        }
//        if (user == null) {
//            return FrontUtils.showLogin(request, model, site);
//        }
        java.sql.Date updateDate = new java.sql.Date(System.currentTimeMillis());//获取当前时间

//        FrontUtils.frontData(request, model, site);
        // 生成盟市实体类
        SUnionCity sUnionCity = new SUnionCity();
        sUnionCity.setUpdateDt(updateDate);
        sUnionCity.setContact(contact);
        sUnionCity.setMobile(mobile);
        sUnionCity.setCompanyName(companyName);
        sUnionCity.setUnionDuties(unionDuties);
        sUnionCity.setEmail(email);
        sUnionCity.setCompanyType(companyType);
        sUnionCity.setUnionName("201708盟市行");
        sUnionCity.setAddrProvince("内蒙古自治区");
        sUnionCity.setAddrCity(addrCity);
        sUnionCity.setAddrCounty(addrCounty);
        sUnionCity.setAddrStreet(addrStreet);
        unionCityService.creUnionCityEntity(sUnionCity);
        // 跳转至盟市行报名页面
        String nextUrl = "/unionCity_Online.jspx";
        return FrontUtils.showSuccess(request, model, nextUrl);
    }


    @Resource
    ALDPayService ALDPayService;
    @Resource
    private SynergyCompanyService synergyCompanyService;
    @Resource
    LogisticsService logisticsService;
    @Resource
    unionCityService unionCityService;
    @Resource
    private SynergyCreateService synergyCreateService;
}
