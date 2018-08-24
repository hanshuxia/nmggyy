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
import com.anchorcms.icloud.controller.logistics.DeliverGoods;
import com.anchorcms.icloud.dao.synergy.OrderPayDao;
import com.anchorcms.icloud.model.common.SMemberAddress;
import com.anchorcms.icloud.model.commservice.SBigDataDemandSign;
import com.anchorcms.icloud.model.commservice.SBigDataNews;
import com.anchorcms.icloud.model.commservice.SBigDataPolicy;
import com.anchorcms.icloud.model.commservice.SBigDataSign;
import com.anchorcms.icloud.model.logistics.SLogistics;
import com.anchorcms.icloud.model.payment.SPOrder;
import com.anchorcms.icloud.model.payment.SPPay;
import com.anchorcms.icloud.model.payment.SPSettle;
import com.anchorcms.icloud.model.synergy.SBigdataDemandQuote;
import com.anchorcms.icloud.model.synergy.SCompanyAptitude;
import com.anchorcms.icloud.service.logistics.LogisticsService;
import com.anchorcms.icloud.service.synergy.SDemandCreateService;
import com.anchorcms.icloud.service.synergy.SynergyCompanyService;
import com.anchorcms.icloud.service.synergy.SynergyManageService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import payment.api.util.GUIDGenerator;
import payment.tools.util.StringUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static com.anchorcms.common.constants.Constants.TPLDIR_MEMBER;
import static com.anchorcms.common.constants.Constants.TPLDIR_PAYMENT;
import static com.anchorcms.common.page.SimplePage.cpn;

/**
 * @Author 姜舒中
 * @Date 2016/12/23 0023
 * @Desc 协同制造
 */
@Controller
public class SynergyManageController {
    private static final Logger log = LoggerFactory.getLogger(SynergyCreateController.class);
    public static final String ABILITY_LIST = "tpl.synergyAbilityManageList";
    public static final String SYNERGY_LIST = "tpl.synergy_seller_list"; // 协同制造订单管理员列表
    public static final String Synergy_Refund = "tpl.SynergyRefund"; // 管理员退款
    public static final String SynergyDevice_Refund = "tpl.SynergyDevice_Refund"; // 管理员企业设备退款
    public static final String Synergy_Refundd = "tpl.SynergyRefundd"; // 用户退款
    public static final String Synergy_Settlement = "tpl.SynergySettlement"; // 管理员结算
    public static final String SynergyDevice_Settlement = "tpl.SynergyDevice_Settlement"; // 管理员企业设备结算
    public static final String WULIU_LIST = "tpl.zt_logistics_manage_list";
    public static final String wuliuOrderDetail = "tpl.wuliuOrderDetail";
    public static final String SUNIONCITY_LIST = "tpl.sunion_city_manage";
    public static final String BIGDATA_LIST = "tpl.bigdata_registed_manage";
    public static final String BIGDATA_ONLINE_LIST = "tpl.bigdata_online_manage";
    public static final String BIGDATA_REGISTRED_DETAIL = "tpl.bigdataRegistredDetail";
    public static final String BIGDATA_DEMAND_QUOTE_DETAIL = "tpl.bigdataDemandQuoteDetail";
    public static final String BIGDATA_ONLINE_DETAIL = "tpl.bigdataOnlineDetail";
    public static final String BIGDATA_NEWS_LIST = "tpl.bigdata_news_manage";
    public static final String BIGDATA_POLICY_LIST = "tpl.bigdata_policy_manage";
    public static final String BIGDATA_NEWS_PREVIEW = "tpl.bigdata_news_preview";
    public static final String BIGDATA_POLICY_PREVIEW = "tpl.bigdata_policy_preview";
    public static final String CONTACTADDRESS_LIST = "tpl.contact_address_manage";
    public static final String COMPANY_APTITUDE_ADD = "/member/company_edit.jspx";

    /**
     * 管理能力列表
     *
     * @param pageNo  页码
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/member/synergy_ability_manage.jspx")
    public String list(String abilityName, String abilityCode, Date createDate, String statusType, String queryTitle, Integer modelId, Integer queryChannelId,
                       Integer pageNo, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        String q = queryTitle;
        String nextUrl = ABILITY_LIST;
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
        Pagination p = synergyManageService.getPageForMember(q, queryChannelId, site.getSiteId(), modelId, user.getUserId(), cpn(pageNo), 20, abilityName, abilityCode, createDate, statusType);
        model.addAttribute("pagination", p);
        if (!StringUtils.isBlank(q)) {
            model.addAttribute("q", q);
        }
        if (modelId != null) {
            model.addAttribute("modelId", modelId);
        }
        model.addAttribute("createDate", createDate);
        model.addAttribute("abilityName", abilityName);
        model.addAttribute("abilityCode", abilityCode);
        model.addAttribute("statusType", statusType);
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, nextUrl);
    }

    /***
     * @Author jiangshuzhong
     * @param id 文章ID
     * @param channelId 栏目ID
     * @param canshu  跳转路径依据:2跳转待审批3跳转已审批
     * @param request
     * @param response
     * @param model
     * @return 驳回能力
     */
    @RequestMapping(value = "/member/synergy_ability_reject.jspx")
    public String reject(Integer id,Integer channelId, String canshu, HttpServletRequest request,
                         HttpServletResponse response, ModelMap model,String backReason) {
        String nextUrl ="/member/synergy_ability_manage.jspx?statusType="+canshu;
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
        WebErrors errors = validateUpdate(id, channelId, site, user, request);
        if (!user.getIsAdmin()&&errors.hasErrors()) {
            return FrontUtils.showError(request, response, model, errors);
        }
        //业务数据更新处理
        try {
            backReason=java.net.URLDecoder.decode(backReason, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        synergyManageService.rejectAdility(id,backReason);

        return FrontUtils.showSuccess(request, model, nextUrl);
    }
    /***
     * @Author jiangshuzhong
     * @param id 文章ID
     * @param channelId 栏目ID
     * @param canshu  跳转路径的依据:2跳转待审批3跳转已审批
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/member/synergy_ability_pass.jspx")
    public String pass(Integer id,Integer channelId, String canshu, HttpServletRequest request,
                         HttpServletResponse response, ModelMap model) {
        String nextUrl ="/member/synergy_ability_manage.jspx?statusType="+canshu;
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
        WebErrors errors = validateUpdate(id, channelId, site, user, request);
        if (!user.getIsAdmin()&&errors.hasErrors()) {
            return FrontUtils.showError(request, response, model, errors);
        }
        //业务数据更新处理
        synergyManageService.passAdility(id);

        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    /***
     * @Author yuantao
     * @param ids 文章ID
     * @param nextUrl 跳转路径
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/member/synergy_abilitys_pass.jspx")
    public String pass(String ids, String nextUrl, HttpServletRequest request, ModelMap model) {
        CmsSite site = CmsUtils.getSite(request);
        FrontUtils.frontData(request, model, site);
        //业务数据更新处理
        synergyManageService.passAdilitys(ids);
        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    /***
     * @Author yuantao
     * @param ids 文章ID
     * @param nextUrl 跳转路径
     * @param request
     * @param model
     * @return 置为驳回需求
     */
    @RequestMapping(value = "/member/synergy_abilitys_reject.jspx")
    public String reject(String ids, String nextUrl, HttpServletRequest request, ModelMap model,String backReason) {
        CmsSite site = CmsUtils.getSite(request);
        FrontUtils.frontData(request, model, site);
        //业务数据更新处理
        try {
            backReason=java.net.URLDecoder.decode(backReason, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        synergyManageService.rejectAdilitys(ids,backReason);
        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    private WebErrors validateUpdate(Integer id, Integer channelId,
                                     CmsSite site, CmsUser user, HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);
        if (vldOpt(errors, site, user, new Integer[] { id })) {
            return errors;
        }
        if (vldChannel(errors, site, user, channelId)) {
            return errors;
        }
        return errors;
    }

    private boolean vldOpt(WebErrors errors, CmsSite site, CmsUser user,
                           Integer[] ids) {
        for (Integer id : ids) {
            if (errors.ifNull(id, "id")) {
                return true;
            }
            Content c = contentMng.findById(id);
            // 数据不存在
            if (errors.ifNotExist(c, Content.class, id)) {
                return true;
            }
            // 非本用户数据
            if (!c.getUser().getUserId().equals(user.getUserId())) {
                errors.noPermission(Content.class, id);
                return true;
            }
            // 非本站点数据
            if (!c.getSite().getSiteId().equals(site.getSiteId())) {
                errors.notInSite(Content.class, id);
                return true;
            }
            // 文章级别大于0，不允许修改
            if (c.getCheckStep() > 0) {
                errors.addErrorCode("member.contentChecked");
                return true;
            }
        }
        return false;
    }
    private boolean vldChannel(WebErrors errors, CmsSite site, CmsUser user,
                               Integer channelId) {
        Channel channel = channelMng.findById(channelId);
        if (errors.ifNotExist(channel, Channel.class, channelId)) {
            return true;
        }
        if (!channel.getSite().getSiteId().equals(site.getSiteId())) {
            errors.notInSite(Channel.class, channelId);
            return true;
        }
        if (!channel.getContriGroups().contains(user.getGroup())) {
            errors.noPermission(Channel.class, channelId);
            return true;
        }
        return false;
    }

    /**
    *@Author 苏和 【562763562@qq.com】
    *@Date 2017/5/2 11:17
    *@Return
     * 协同制造管理员订单列表
    */
    @RequestMapping("/member/synergy_seller_list.jspx")
    public String getSyneryList(HttpServletRequest request, ModelMap model, Integer pageNo,
                                Date startDate, Date endDate, String state, String paramu) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        if (StringUtil.isEmpty(state) || state.equals("1")) {
            state = "1";
        }
        MemberConfig mcfg = site.getConfig().getMemberConfig();
        // 没有开启会员功能
        if (!mcfg.isMemberOn()) {
            return FrontUtils.showMessage(request, model, "member.memberClose");
        }
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
//        if (StringUtils.isBlank(state)) {
//            state = "1";
//        }
        // 获得本站栏目列表
        Set<Channel> rights = user.getGroup().getContriChannels();
        List<Channel> topList = channelMng.getTopList(site.getSiteId(), true);
        List<Channel> channelList = Channel.getListForSelect(topList, rights, true);
        //获取分页对象
        Pagination p = synergyManageService.getSyneryListPage(site.getSiteId(), user, cpn(pageNo),
                20, startDate, state, paramu);
        model.addAttribute("pagination", p);
        model.addAttribute("startDate", startDate);
        model.addAttribute("site", site);
        model.addAttribute("channelList", channelList);
        model.addAttribute("state", state);
        model.addAttribute("sessionId", request.getSession().getId());
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, SYNERGY_LIST);
    }

    /**
     * @Author 苏和 【562763562@qq.com】
     * @Date 2017/5/2 17:24
     * @Return 协同制造卖家结算
     */
    @RequestMapping(value = "/member/SynergySettlement.jspx")
    public String add(HttpServletRequest request, HttpServletResponse response, ModelMap model, String id,
                      String flag) throws Exception {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
        // 在当前页面获取 订单号    结算金额（数据库）   账户类型 （填写）  机构号码（默认）
        //账户名称   账户类型等需要填写
        SPPay spPay = orderPayDao.getOrderById(id).getSpPay();
        String OrderNo = spPay.getOrderNo(); //订单号（数据库取值）
        double amount = spPay.getAmount();//分（数据库取值）
        String SerialNumber = GUIDGenerator.genGUID(); //交易流水号
        //int AccountType = 20;  //交易类型默认支付平台账户20
        model.addAttribute("orderNo", OrderNo);
        model.addAttribute("amount", amount);
        model.addAttribute("serialNumber", SerialNumber);
        model.addAttribute("id", id);
//        model.addAttribute("AccountType",AccountType);
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_PAYMENT, Synergy_Settlement);
    }

    /**
     * @Author zhouyq
     * @Date 2017/6/12
     * @Return 协同制造企业设备结算
     */
    @RequestMapping(value = "/member/SynergyDevice_Settlement.jspx")
    public String deviceSettlement(HttpServletRequest request, HttpServletResponse response, ModelMap model, String id,
                      String flag) throws Exception {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
        // 在当前页面获取 订单号    结算金额（数据库）   账户类型 （填写）  机构号码（默认）
        //账户名称   账户类型等需要填写
        SPPay spPay = orderPayDao.getOrderById(id).getSpPay();
        String OrderNo = spPay.getOrderNo(); //订单号（数据库取值）
        double amount = spPay.getAmount();//分（数据库取值）
        String SerialNumber = GUIDGenerator.genGUID(); //交易流水号
        //int AccountType = 20;  //交易类型默认支付平台账户20
        model.addAttribute("orderNo", OrderNo);
        model.addAttribute("amount", amount);
        model.addAttribute("serialNumber", SerialNumber);
        model.addAttribute("id", id);
//        model.addAttribute("AccountType",AccountType);
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_PAYMENT, SynergyDevice_Settlement);
    }

    /**
     * @Author 苏和 【562763562@qq.com】
     * @Date 2017/5/3 9:22
     * @Return 管理员退款给买家
     */
    @RequestMapping(value = "/member/SynergyRefund.jspx")
    public String addd(HttpServletRequest request, HttpServletResponse response, ModelMap model, String id,
                      String flag) throws Exception {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
        // 在当前页面获取 订单号    结算金额（数据库）   账户类型 （填写）  机构号码（默认）
        //账户名称   账户类型等需要填写
//        SPSettle spo = synergyManageService.findOrderById(id);
        SPPay spo = orderPayDao.getOrderById(id).getSpPay();
        SPSettle se = spo.getSpSettle();
        String OrderNo = spo.getOrderNo(); //订单号（数据库取值）
        double amount = spo.getAmount();//分（数据库取值）
        String SerialNumber = GUIDGenerator.genGUID(); //交易流水号
        //int AccountType = 20;  //交易类型默认支付平台账户20
        model.addAttribute("orderNo", OrderNo);
        model.addAttribute("amount", amount);
        model.addAttribute("serialNumber", SerialNumber);
        model.addAttribute("id", id);
        model.addAttribute("se", se);
//        model.addAttribute("accountType",accountType);
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_PAYMENT, Synergy_Refund);
    }

    /**
     * @Author zhouyq
     * @Date 2017/6/12
     * @Return 管理员退款给买家(企业设备)
     */
    @RequestMapping(value = "/member/SynergyDevice_Refund.jspx")
    public String deviceRefund(HttpServletRequest request, HttpServletResponse response, ModelMap model, String id,
                       String flag) throws Exception {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
        // 在当前页面获取 订单号    结算金额（数据库）   账户类型 （填写）  机构号码（默认）
        //账户名称   账户类型等需要填写
//        SPSettle spo = synergyManageService.findOrderById(id);
        SPPay spo = orderPayDao.getOrderById(id).getSpPay();
        SPSettle se = spo.getSpSettle();
        String OrderNo = spo.getOrderNo(); //订单号（数据库取值）
        double amount = spo.getAmount();//分（数据库取值）
        String SerialNumber = GUIDGenerator.genGUID(); //交易流水号
        //int AccountType = 20;  //交易类型默认支付平台账户20
        model.addAttribute("orderNo", OrderNo);
        model.addAttribute("amount", amount);
        model.addAttribute("serialNumber", SerialNumber);
        model.addAttribute("id", id);
        model.addAttribute("se", se);
//        model.addAttribute("accountType",accountType);
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_PAYMENT, SynergyDevice_Refund);
    }

    /**
    *@Author 苏和 【562763562@qq.com】
    *@Date 2017/5/3 9:22
    *@Return
     * 卖家确认退款页面
    */
    @RequestMapping(value = "/member/SynergyRefundd.jspx")
    public String adddd(HttpServletRequest request, HttpServletResponse response, ModelMap model, String id,
                        String flag) throws Exception {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
        // 在当前页面获取 订单号    结算金额（数据库）   账户类型 （填写）  机构号码（默认）
        //账户名称   账户类型等需要填写
        SPPay spPay = orderPayDao.getOrderById(id).getSpPay();
        SPSettle se = spPay.getSpSettle();
        String OrderNo = spPay.getOrderNo(); //订单号（数据库取值）
        double amount = spPay.getAmount();//分（数据库取值）
        String SerialNumber = GUIDGenerator.genGUID(); //交易流水号
        //int AccountType = 20;  //交易类型默认支付平台账户20
        model.addAttribute("orderNo", OrderNo);
        model.addAttribute("amount", amount);
        model.addAttribute("serialNumber", SerialNumber);
        model.addAttribute("id", id);
        model.addAttribute("se", se);
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_PAYMENT, Synergy_Refundd);
    }
    /**
     * @author liuyang
     * @Date 2017/7/10 14:17
     * @return   物流订单管理列表
     */
    @RequestMapping(value = "/member/zt_logistics_manage_list.jspx")
    public String list2(String txlogisticId, String logisticsOrderState, Date createOrderTime, String queryTitle, Integer modelId, Integer queryChannelId,
                       Integer pageNo, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        String q = queryTitle;
        String nextUrl = WULIU_LIST;
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
        Pagination p = logisticsService.getPageForMember(q, queryChannelId, site.getSiteId(), modelId, user.getUserId(), cpn(pageNo), 20, txlogisticId, createOrderTime, logisticsOrderState);
        model.addAttribute("pagination", p);
        if (!StringUtils.isBlank(q)) {
            model.addAttribute("q", q);
        }
        if (modelId != null) {
            model.addAttribute("modelId", modelId);
        }
        model.addAttribute("txlogisticId", txlogisticId);
        model.addAttribute("createOrderTime", createOrderTime);
        model.addAttribute("logisticsOrderState", logisticsOrderState);
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, nextUrl);
    }


    /**
     * @author liuyang
     * @Date 2017/7/12 8:33
     * @return   物流管理结算
     */
    @RequestMapping("/member/zt_logistics_ModifyState.jspx")
    public String modifyWuliuStatById(String txlogisticId, String logisticsOrderState,
                                       HttpServletRequest request, HttpServletResponse response, ModelMap model) throws UnsupportedEncodingException {
        CmsSite site = CmsUtils.getSite(request);
        FrontUtils.frontData(request, model, site);

        if (txlogisticId != null) {
            txlogisticId = java.net.URLDecoder.decode(txlogisticId, "utf-8");

            //    service.modifyRepairDemandStateById(demandId, state);
            // 查询物流详情
            DeliverGoods deliverGoods = new DeliverGoods();
//        String orderNO = "K29010323933";
//        String orderNO = "XOUT056558";
//        String deliverInfo = deliverGoods.selLogisticsOrder(orderNO);
            String deliverInfo = null;
            try {
                deliverInfo = deliverGoods.selLogisticsOrder(txlogisticId);
            } catch (Exception e) {
                e.printStackTrace();
            }
            model.addAttribute("deliverInfo", deliverInfo);
            SLogistics sLogistics = logisticsService.byAbilityId(txlogisticId);
            double logisticsFeeDouble = 0.00;
            if (deliverInfo != null) {
                // Integer转Double
                if (JSONObject.fromObject(JSONArray.fromObject(deliverInfo).get(0)).get("fee") != null) {
                    String logisticsFeeStr = String.valueOf(JSONObject.fromObject(JSONArray.fromObject(deliverInfo).get(0)).get("fee"));
                    logisticsFeeDouble = Double.parseDouble(logisticsFeeStr);
                }
                sLogistics.setGoodsValue(logisticsFeeDouble);
                sLogistics.setItemsValue(logisticsFeeDouble * (0.05));
                logisticsService.creLogisticsEntity(sLogistics);
            }
            synergyManageService.modifyWuliuStateById(txlogisticId, logisticsOrderState);
        }
        String nextUrl = "/member/zt_logistics_manage_list.jspx";
//        if (double logisticsFeeDouble!=0)
        return FrontUtils.showSuccess(request, model, nextUrl);
    }
    /**
     * @author liuyang
     * @Date 2017/7/17 14:57
     * @return   物流管理明细
     */
    @RequestMapping(value = "/member/Wuliu_Manage_detail.jspx")
    public String Wuliu_Manage_detail(HttpServletRequest request, HttpServletResponse response, ModelMap model,String txlogisticId
    ) throws Exception {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);

        SLogistics order = logisticsService.findOrderById(txlogisticId);
        SPOrder sp = orderPayDao.getOrderById(txlogisticId);
//        SPOrderObj obj=orderPayDao.getOrderObjById(id);

//        return FrontUtils.showSuccess(request, model, softwareDetail);
        model.addAttribute("order", order);
        model.addAttribute("sp", sp);

//        model.addAttribute("obj", obj);
//        List<SPOrderObj> list = order.getSOrderObjList();
//        model.addAttribute("orderList", list);
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_PAYMENT, wuliuOrderDetail);
    }

    /**
     * @author liuyang
     * @Date 2017/8/7 13:55
     * @return  盟市行统计查看
     */
    @RequestMapping("/member/sunion_city_manage.jspx")
    public String getUnionCityList(HttpServletRequest request, ModelMap model, Integer pageNo,
                                   java.sql.Date startDate, java.sql.Date endDate, String state, String paramu) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        if (StringUtil.isEmpty(state) || state.equals("1")) {
            state = "1";
        }
        MemberConfig mcfg = site.getConfig().getMemberConfig();
        // 没有开启会员功能
        if (!mcfg.isMemberOn()) {
            return FrontUtils.showMessage(request, model, "member.memberClose");
        }
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
//        if (StringUtils.isBlank(state)) {
//            state = "1";
//        }
        // 获得本站栏目列表
        Set<Channel> rights = user.getGroup().getContriChannels();
        List<Channel> topList = channelMng.getTopList(site.getSiteId(), true);
        List<Channel> channelList = Channel.getListForSelect(topList, rights, true);
        //获取分页对象
        Pagination p = unionCityService.getSyneryListPage(site.getSiteId(), user, cpn(pageNo),
                20, startDate,endDate, state, paramu);
        model.addAttribute("pagination", p);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("site", site);
        model.addAttribute("channelList", channelList);
        model.addAttribute("state", state);
        model.addAttribute("sessionId", request.getSession().getId());
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, SUNIONCITY_LIST);
    }

/**
 * @author liuyang
 * @Date 2018/4/13 15:23
 * @return
 * 大数据能力统计管理
 */
    @RequestMapping("/member/bigdata_registred_manage.jspx")
    public String getUnionCityList2(HttpServletRequest request, ModelMap model, Integer pageNo,
                                    String paramu) {
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
//        if (StringUtils.isBlank(state)) {
//            state = "1";
//        }
        // 获得本站栏目列表
        Set<Channel> rights = user.getGroup().getContriChannels();
        List<Channel> topList = channelMng.getTopList(site.getSiteId(), true);
        List<Channel> channelList = Channel.getListForSelect(topList, rights, true);
        //获取分页对象
        Pagination p = unionCityService.getSyneryListPage2(site.getSiteId(), user, cpn(pageNo),
                20, paramu);
        model.addAttribute("pagination", p);
        model.addAttribute("site", site);
        model.addAttribute("channelList", channelList);
        model.addAttribute("sessionId", request.getSession().getId());
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, BIGDATA_LIST);
    }

    /**
     * @author suhe
     * @Date 2018/4/18 14:32
     * @return
     * 大数据能力统计管理明细
     */
    @RequestMapping("/member/bigdata_registred_detail.jspx")
    public String registredDetail(Integer id, HttpServletRequest request,
                       HttpServletResponse response, ModelMap model) {
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
        SBigDataSign sBigDataSign = unionCityService.findRegistredById(id);
        Content content = sBigDataSign.getContent();
        // 获得本站栏目列表
        Set<Channel> rights = user.getGroup().getContriChannels();
        List<Channel> topList = channelMng.getTopList(site.getSiteId(), true);
        List<Channel> channelList = Channel.getListForSelect(topList, rights,
                true);
        model.addAttribute("sBigDataSign",sBigDataSign);
        model.addAttribute("content", content);
        model.addAttribute("site", site);
        model.addAttribute("channelList", channelList);
        model.addAttribute("sessionId",request.getSession().getId());
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, BIGDATA_REGISTRED_DETAIL);
    }
    /**
     * @author liuyang
     * @Date 2018/4/27 11:28
     * @return
     * 大数据对接明细
     */
    @RequestMapping("/member/bigdata_demand_quote_detail.jspx")
    public String registredDetail2(Integer id, HttpServletRequest request,
                                  HttpServletResponse response, ModelMap model) {
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
        SCompanyAptitude company_aptitude = synergyCompanyService.findAptitudeByCompanyId(user.getCompany().getCompanyId());
        if(company_aptitude==null||!company_aptitude.getStatus().equals("3")) {//未通过企业资质认证
            String message = "您的企业尚未通过资质认证，请前往会员中心-维护企业信息-企业资质进行维护！";
            return FrontUtils.showBaseMessage(request, model, message, COMPANY_APTITUDE_ADD);
        }
        SBigdataDemandQuote sBigdataDemandQuote = unionCityService.findRegistredById2(id);

        // 获得本站栏目列表
        Set<Channel> rights = user.getGroup().getContriChannels();
        List<Channel> topList = channelMng.getTopList(site.getSiteId(), true);
        List<Channel> channelList = Channel.getListForSelect(topList, rights,
                true);
        model.addAttribute("sBigDataSign",sBigdataDemandQuote);
        model.addAttribute("company_aptitude",company_aptitude);
        model.addAttribute("site", site);
        model.addAttribute("channelList", channelList);
        model.addAttribute("sessionId",request.getSession().getId());
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, BIGDATA_DEMAND_QUOTE_DETAIL);
    }




    /**
     * @author suhe
     * @Date 2018/4/17 9:32
     * @return
     * 大数据需求统计管理
     */

    @RequestMapping("/member/bigdata_online_manage.jspx")
    public String getBigdataOnline(HttpServletRequest request, ModelMap model, Integer pageNo,
                                    String paramu) {
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
//        if (StringUtils.isBlank(state)) {
//            state = "1";
//        }
        // 获得本站栏目列表
        Set<Channel> rights = user.getGroup().getContriChannels();
        List<Channel> topList = channelMng.getTopList(site.getSiteId(), true);
        List<Channel> channelList = Channel.getListForSelect(topList, rights, true);
        //获取分页对象
        Pagination p = unionCityService.getBigdataOnline(site.getSiteId(), user, cpn(pageNo),
                20, paramu);
        model.addAttribute("pagination", p);
        model.addAttribute("site", site);
        model.addAttribute("channelList", channelList);
        model.addAttribute("sessionId", request.getSession().getId());
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, BIGDATA_ONLINE_LIST);
    }

    /**
     * @author suhe
     * @Date 2018/4/18 14:32
     * @return
     * 大数据需求统计管理明细
     */
    @RequestMapping("/member/bigdata_online_detail.jspx")
    public String onlineDetail(Integer id, HttpServletRequest request,
                                  HttpServletResponse response, ModelMap model) {
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
        SBigDataDemandSign sBigDataDemandSign = unionCityService.findOnlineById(id);
        Content content = sBigDataDemandSign.getContent();
        // 获得本站栏目列表
        Set<Channel> rights = user.getGroup().getContriChannels();
        List<Channel> topList = channelMng.getTopList(site.getSiteId(), true);
        List<Channel> channelList = Channel.getListForSelect(topList, rights,
                true);
        model.addAttribute("sBigDataDemandSign",sBigDataDemandSign);
        model.addAttribute("content", content);
        model.addAttribute("site", site);
        model.addAttribute("channelList", channelList);
        model.addAttribute("sessionId",request.getSession().getId());
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, BIGDATA_ONLINE_DETAIL);
    }


    /**
     * @author suhe
     * @Date 2018/4/13 9:30
     * @return
     * 大数据新闻管理
     */

    @RequestMapping("/member/bigdata_news_manage.jspx")
    public String getBigdataNews(HttpServletRequest request, ModelMap model, Integer pageNo,
                                    String paramu) {
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
//        if (StringUtils.isBlank(state)) {
//            state = "1";
//        }
        // 获得本站栏目列表
        Set<Channel> rights = user.getGroup().getContriChannels();
        List<Channel> topList = channelMng.getTopList(site.getSiteId(), true);
        List<Channel> channelList = Channel.getListForSelect(topList, rights, true);
        //获取分页对象
        Pagination p = unionCityService.getBigdataNews(site.getSiteId(), user, cpn(pageNo),
                20, paramu);
        model.addAttribute("pagination", p);
        model.addAttribute("site", site);
        model.addAttribute("channelList", channelList);
        model.addAttribute("sessionId", request.getSession().getId());
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, BIGDATA_NEWS_LIST);
    }



    /**
     * @author suhe
     * @Date 2018/4/13 11:30
     * @return
     * 大数据新闻删除
     */

    @RequestMapping(value = "/member/bigdata_news_delete.jspx")
    public String deleteNewsById(Integer id,
                                       HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        CmsSite site = CmsUtils.getSite(request);
        String nextUrl = "/member/bigdata_news_manage.jspx";
        FrontUtils.frontData(request, model, site);
        if (id != null) {
            unionCityService.deleteNewsById(id);
        }
        return FrontUtils.showSuccess(request, model, nextUrl);

    }



    /**
     * @author suhe
     * @Date 2018/4/13 11:30
     * @return
     * 大数据新闻预览
     */

    @RequestMapping(value = "/bigdata_news_preview.jspx")
    public String view(Integer id, HttpServletRequest request,
                       HttpServletResponse response, ModelMap model) {
        CmsSite site = CmsUtils.getSite(request);
        FrontUtils.frontData(request, model, site);
        SBigDataNews sBigDataNews = unionCityService.findNewsById(id);
        Content content = sBigDataNews.getContent();
        // 获得本站栏目列表
        model.addAttribute("sBigDataNews",sBigDataNews);
        model.addAttribute("content", content);
        model.addAttribute("site", site);
        model.addAttribute("sessionId",request.getSession().getId());
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, BIGDATA_NEWS_PREVIEW);
    }

    /**
     * @author suhe
     * @Date 2018/4/26 9:30
     * @return
     * 大数据政策管理
     */

    @RequestMapping("/member/bigdata_policy_manage.jspx")
    public String getBigdataPolicy(HttpServletRequest request, ModelMap model, Integer pageNo,
                                 String paramu) {
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
//        if (StringUtils.isBlank(state)) {
//            state = "1";
//        }
        // 获得本站栏目列表
        Set<Channel> rights = user.getGroup().getContriChannels();
        List<Channel> topList = channelMng.getTopList(site.getSiteId(), true);
        List<Channel> channelList = Channel.getListForSelect(topList, rights, true);
        //获取分页对象
        Pagination p = unionCityService.getBigdataPolicy(site.getSiteId(), user, cpn(pageNo),
                20, paramu);
        model.addAttribute("pagination", p);
        model.addAttribute("site", site);
        model.addAttribute("channelList", channelList);
        model.addAttribute("sessionId", request.getSession().getId());
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, BIGDATA_POLICY_LIST);
    }

    /**
     * @author suhe
     * @Date 2018/4/26 9:30
     * @return
     * 大数据政策预览
     */

    @RequestMapping(value = "/bigdata_policy_preview.jspx")
    public String policyView(Integer id, HttpServletRequest request,
                       HttpServletResponse response, ModelMap model) {
        CmsSite site = CmsUtils.getSite(request);
        FrontUtils.frontData(request, model, site);
        SBigDataPolicy sBigDataPolicy = unionCityService.findPolicyById(id);
        Content content = sBigDataPolicy.getContent();
        // 获得本站栏目列表
        model.addAttribute("sBigDataPolicy",sBigDataPolicy);
        model.addAttribute("content", content);
        model.addAttribute("site", site);
        model.addAttribute("sessionId",request.getSession().getId());
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, BIGDATA_POLICY_PREVIEW);
    }


    /**
     * @author suhe
     * @Date 2018/4/26 9:30
     * @return
     * 大数据政策删除
     */
    @RequestMapping(value = "/member/bigdata_policy_delete.jspx")
    public String deletePolicyById(Integer id,
                                 HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        CmsSite site = CmsUtils.getSite(request);
        String nextUrl = "/member/bigdata_policy_manage.jspx";
        FrontUtils.frontData(request, model, site);
        if (id != null) {
            unionCityService.deletePolicyById(id);
        }
        return FrontUtils.showSuccess(request, model, nextUrl);

    }





    @RequestMapping("/member/sunion_city_manage_delete.jspx")
    public String deleteSunionCity(Integer unionId, String nextUrl,
                                       HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        CmsSite site = CmsUtils.getSite(request);
        FrontUtils.frontData(request, model, site);
        if (unionId != null) {
            unionCityService.deleteSunionCity(unionId);
        }
//    String nextUrl = "/member/contact_address_manage.jspx?userId=${user.userId}";
        return FrontUtils.showSuccess(request, model, nextUrl);

    }
    @RequestMapping("/member/contact_address_manage.jspx")
    public String getContactAddressList(HttpServletRequest request, ModelMap model,Integer userId) {
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
//        if (StringUtils.isBlank(state)) {
//            state = "1";
//        }
        // 获得本站栏目列表
        Set<Channel> rights = user.getGroup().getContriChannels();
        List<Channel> topList = channelMng.getTopList(site.getSiteId(), true);
        List<Channel> channelList = Channel.getListForSelect(topList, rights, true);
        //获取分页对象
        List<SMemberAddress> sMemberAddressesList = sDemandCreateService.getContactJsonList(userId);
        model.addAttribute("sMemberAddressesList", sMemberAddressesList);
        model.addAttribute("user", user);
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_MEMBER, CONTACTADDRESS_LIST);
    }

//    @RequestMapping(value = "/member/contact_address.jspx")
//    public String deleteContactAddress(Integer addrId, HttpServletRequest request, HttpServletResponse response,
//                              ModelMap model, Integer modelId, Integer channelId) {
//        CmsSite site = CmsUtils.getSite(request);
//        CmsUser user = CmsUtils.getUser(request);
//        String nextUrl = "/member/contact_address_manage.jspx";
//        FrontUtils.frontData(request, model, site);
//        //更新申请状态
//        unionCityService.delete(addrId);
//        return FrontUtils.showSuccess(request, model, nextUrl);
//    }
@RequestMapping("/member/contact_address_delete.jspx")
public String deleteContactAddress(Integer addrId, String nextUrl,
                                HttpServletRequest request, HttpServletResponse response, ModelMap model) {
    CmsSite site = CmsUtils.getSite(request);
    FrontUtils.frontData(request, model, site);
    if (addrId != null) {
        unionCityService.deleteContactAddress(addrId);
    }
//    String nextUrl = "/member/contact_address_manage.jspx?userId=${user.userId}";
    return FrontUtils.showSuccess(request, model, nextUrl);

}
    @Resource
    LogisticsService logisticsService;
    @Resource
    protected ContentMng contentMng;
    @Resource
    protected SynergyManageService synergyManageService;
    @Resource
    private ChannelMng channelMng;
    @Resource
    private OrderPayDao orderPayDao;
    @Resource
    private SDemandCreateService sDemandCreateService;
    @Resource
    com.anchorcms.icloud.service.unionCity.unionCityService unionCityService;
    @Resource
    private SynergyCompanyService synergyCompanyService;
}
