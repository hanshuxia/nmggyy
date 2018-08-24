package com.anchorcms.icloud.controller.supplychain;

import com.anchorcms.cms.service.main.ContentMng;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.common.utils.CmsUtils;
import com.anchorcms.common.utils.FrontUtils;
import com.anchorcms.core.model.CmsSite;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.core.model.MemberConfig;
import com.anchorcms.icloud.model.payment.*;
import com.anchorcms.icloud.model.synergy.SCompanyAptitude;
import com.anchorcms.icloud.service.supplychain.SLDOrderService;
import com.anchorcms.icloud.service.synergy.SynergyCompanyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import payment.api.system.PaymentEnvironment;
import payment.api.util.GUIDGenerator;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.anchorcms.common.constants.Constants.TPLDIR_PAYMENT;
import static com.anchorcms.common.constants.Constants.TPLDIR_SUPPLYCHAIN;
import static com.anchorcms.common.page.SimplePage.cpn;

/**
 * 供应链众修需求-需求方--订单管理列表
 *
 * @author ldong
 * @Date 2017/5/3 15:27
 * @return
 */

@Controller
public class SLdOrderController {
    private static final Logger log = LoggerFactory.getLogger(SLdOrderController.class);
    public static final String SPCHAINDEMAND_ORDER_LIST = "tpl.supplychainSdemandOrderList";
    public static final String COMPANY_APTITUDE_ADD = "/member/company_edit.jspx";
    /**
     * @Author ld
     * 需求方订单列表
     * @Date 2017年5月3日15:28:04
     */

    @RequestMapping(value = "/member/supplychain_demand_order_list.jspx")
    public String list(String statusType, Integer modelId, Integer pageNo,
                       HttpServletRequest request, ModelMap model) {
        String nextUrl = SPCHAINDEMAND_ORDER_LIST;
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
        if (user.getCompany() == null) {
            return FrontUtils.showMessage(request, model, "error.userWithoutCompany");
        }
        //判断企业资质
        SCompanyAptitude company_aptitude = synergyCompanyService.findAptitudeByCompanyId(user.getCompany().getCompanyId());
        if(company_aptitude==null||!company_aptitude.getStatus().equals("3")) {//未通过企业资质认证
            String message = "您的企业尚未通过资质认证，请前往会员中心-维护企业信息-企业资质进行维护！";
            return FrontUtils.showBaseMessage(request, model, message, COMPANY_APTITUDE_ADD);
        }
        Pagination p = spOrderService.getPageForMember(user.getUserId(), cpn(pageNo), 20, statusType);
        model.addAttribute("pagination", p);
        if (modelId != null) {
            model.addAttribute("modelId", modelId);
        }
        model.addAttribute("statusType", statusType);
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_SUPPLYCHAIN, nextUrl);
    }

    @RequestMapping(value = "/member/supplychain_demand_order_del.jspx")
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
    String SUPPLYCHAIN_CLICK_PAY = "tpl.supplychain_clickpay";

    @RequestMapping("/member/supplychain_clickPay.jspx")
    public String payclick(HttpServletRequest request, HttpServletResponse response,
                           ModelMap model, String orderId) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }

        String orderNo = orderId;
        SSupplychainOrder sp = this.spOrderService.findOrderById(orderNo);
        String s = sp.getState();
        if (!s.equals("1")) {
           /* return FrontUtils.showBaseMessage(request,
                    model, "请确认订单是否已经付款", "/member/supplychain_demand_order_list.jspx?statusType=1");
      */
            return FrontUtils.showClose(request,model);
        }
        //支付流水号
        String payGuid = null;
        try {
            payGuid = GUIDGenerator.genGUID();

        } catch (Exception e) {
            e.printStackTrace();
        }
        String contextpath;
        contextpath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
/*
        model.addAttribute("count",count);
        model.addAttribute("managerId",managerId);*/
        model.addAttribute("user", user);
        model.addAttribute("orderId", orderId);
        model.addAttribute("guId", orderNo);
        model.addAttribute("payGuid", payGuid);
        model.addAttribute("site", site);
        model.addAttribute("url", contextpath);
        model.addAttribute("order", sp);
        //model.addAttribute("buy", buy);
      /*  model.addAttribute("softwareid",softwareid);*/
        model.addAttribute("sessionId", request.getSession().getId());
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_PAYMENT, SUPPLYCHAIN_CLICK_PAY);
    }

    /**
     * @author ldong
     * @Date 2017/5/3 9:17
     * @return 显示订单详情，进行确定付款
     */
    String SUPPLYCHAIN_BUY = "tpl.supplychain_paybuy";

    @RequestMapping(value = "/member/supplychain_buy.jspx")
    public String save(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                       String institutionID, String paymentNo, String orderNo, double amount, double fee, String payerID,
                       String payerName, String usage, String remark, String notificationURL, String payees,
                       String bankID, String bankName, Integer accountType, String cardType, Integer managerId, String label, String count, String orderId) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        institutionID = "002818";
        SSupplychainOrder order = spOrderService.findOrderById(orderId);
        amount = order.getPrice();
        Map attributes = spOrderService.pay(orderId, user, institutionID, paymentNo, orderNo, amount, fee, payerID, payerName, usage, remark, notificationURL, payees, bankID, bankName, accountType, cardType);
        model.addAttribute("message", attributes.get("message"));
        model.addAttribute("signature", attributes.get("signature"));
        model.addAttribute("txCode", attributes.get("txCode"));
        model.addAttribute("txName", attributes.get("txName"));
        model.addAttribute("Flag", attributes.get("flag"));
        model.addAttribute("action", PaymentEnvironment.paymentURL);
        model.addAttribute("institutionID", institutionID);//机构号码
        model.addAttribute("managerId", managerId);
        model.addAttribute("orderNo", orderNo);//订单号
        model.addAttribute("paymentNo", paymentNo);//支付流水号
        model.addAttribute("amount", amount);//付款金额
        model.addAttribute("fee", fee);//支付服务手续费
        model.addAttribute("payerID", payerID);//付款者ID
        model.addAttribute("payerName", payerName);
        model.addAttribute("label", label);//资金用途
        model.addAttribute("remark", remark);//订单描述
        model.addAttribute("payees", payees);//收款人
        model.addAttribute("bankID", bankID);//银行ID
        model.addAttribute("bankName", bankName);//银行
        model.addAttribute("accountType", accountType);//账号类型
        model.addAttribute("cardType", cardType);//卡类型
        model.addAttribute("order", order);
        List<SSupplychainOrderObj> list = order.getSOrderObjList();
        model.addAttribute("orderList", list);
        model.addAttribute("price", order.getPrice());

        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_PAYMENT, SUPPLYCHAIN_BUY);


    }

    /**
     * @author ldong
     * @Date 2017/5/3 9:47
     * @return 前台接受支付结果
     */
    String SUPPLYCHAIN_PAYRESULT = "tpl.supplychain_payResult";

    @RequestMapping(value = "/supplychain_receive_notice.jspx", method = RequestMethod.POST)
    public String save(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                       String message, String signature) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        System.out.println(message);
        message = message.replace(" ", "+");
        spOrderService.receiveNotice(message, signature);
        //return  FrontUtils.showBaseMessage(request,model,"nihao","rjyyRjjsym/index.jhtml");
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_PAYMENT, SUPPLYCHAIN_PAYRESULT);
    }

    /**
     * @author ldong
     * @Date 2017/5/3 11:06
     * @return 买家申请退款
     */
    String SUPPLYCHAINREFUND = "tpl.supplychainRefundadmin";

    @RequestMapping(value = "/member/supplychain_demand_clickRefund.jspx")
    public String applyRefund(HttpServletRequest request, HttpServletResponse response, ModelMap model, String orderId,
                              String flag) throws Exception {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
        // 在当前页面获取 订单号    结算金额（数据库）   账户类型 （填写）  机构号码（默认）
        //账户名称   账户类型等需要填写
        SSupplychainOrder order = spOrderService.findOrderById(orderId);
        SPPay sp = order.getSpPay();
        if (sp == null) {
            return FrontUtils.showBaseMessage(request,model,"订单信息错误","/member/supplychain_demand_order_list.jspx");
        }
        double amount = sp.getAmount();//分（数据库取值）
        String SerialNumber = GUIDGenerator.genGUID(); //交易流水号
        String action = null;
        if (flag.equals("1")) {
            action = "software_settle.jspx";
        } else if (flag.equals("0")) {
            action = "supplychain_demand_saveRefund.jspx";
        }
        model.addAttribute("orderNo", orderId);
        model.addAttribute("amount", amount);
        model.addAttribute("serialNumber", SerialNumber);
        model.addAttribute("actiona", action);
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_PAYMENT, SUPPLYCHAINREFUND);
    }

    /**
     * @return 保存退款信息
     * @author ldong
     * @Date 2017/5/3 11:14
     */

    @RequestMapping(value = "/member/supplychain_demand_saveRefund.jspx")
    public String applyRefund(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                              String SerialNumber,
                              String OrderNo,
                              String remark,/*暂时无用 可以用作退款原因*/
                              String Amount,
                              int AccountType,
                              String BankID,
                              String bankName,
                              String AccountName,
                              String AccountNumber,
                              String BranchName,
                              String Province,
                              String City, String orderId, String flag/*(设置标志位确定是结算还是退款)*/) throws Exception {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
        SSupplychainOrder sorder = spOrderService.findOrderById(orderId);
        SPPay sp = sorder.getSpPay();
        SPSettle po = sp.getSpSettle();
        if (po == null) {
            po = new SPSettle();
            String u = UUID.randomUUID().toString().replaceAll("-", "");
            po.setId(u);
        }
        po.setAmount(sp.getAmount());
        po.setSerialNumber(SerialNumber);
        po.setOrderNo(OrderNo);
        po.setAccountType(AccountType);
        po.setAcountName(AccountName);
        po.setAcountNumber(AccountNumber);
        po.setBranchName(BranchName);
        po.setBankId(BankID);
        po.setBankName(bankName);
        po.setProvince(Province);
        po.setCity(City);
        sp.setSpSettle(po);
        sorder.setState("30");
        spOrderService.updateOrder(sorder);
        return FrontUtils.showSuccess(request,
                model, "/member/supplychain_demand_order_list.jspx?statusType=2");
    }

    /**
     * @return 改变订单状态
     * @author ldong
     * @Date 2017/5/3 14:52
     */


    @RequestMapping(value = "/member/supplychain_editOrderStatus.jspx")
    public String editOrderStatus(HttpServletRequest request, HttpServletResponse response, ModelMap model, String orderId, String setstatus, String tabState,String isOnline
    ) throws Exception {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
      /*  return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_PAYMENT, NEXTURL);*/
        this.spOrderService.editOrderStatus(orderId, setstatus,isOnline);
        String nextUrl = "/member/supplychain_demand_order_list.jspx?statusType=" + tabState;
        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    @Resource
    protected ContentMng contentMng;
    @Resource
    private SLDOrderService spOrderService;
    @Resource
    private SynergyCompanyService synergyCompanyService;

}
