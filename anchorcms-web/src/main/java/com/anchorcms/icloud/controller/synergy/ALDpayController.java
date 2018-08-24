package com.anchorcms.icloud.controller.synergy;

import com.anchorcms.common.utils.CmsUtils;
import com.anchorcms.common.utils.FrontUtils;
import com.anchorcms.core.model.CmsSite;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.model.payment.*;
import com.anchorcms.icloud.service.synergy.ALDPayService;
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

@Controller
public class ALDpayController {
    @Resource
    ALDPayService ALDPayService;

    String SYNERGY_CLICK_PAY = "tpl.synergy_clickpay";

    @RequestMapping("/member/synergy/clickPay")
    public String payclick(HttpServletRequest request, HttpServletResponse response,
                           ModelMap model, String orderId) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }

        String orderNo = orderId;
        SPOrder sp = this.ALDPayService.findOrderById(orderNo);
        String s = sp.getState();
        if (!s.equals("1")) {
         /*   return FrontUtils.showBaseMessage(request,
                    model, "请确认订单是否已经付款", "/member/synergy_demand_order_list.jspx?statusType=1");*/
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
                TPLDIR_PAYMENT, SYNERGY_CLICK_PAY);
    }

    /**
     * @author ldong
     * @Date 2017/5/3 9:17
     * @return 显示订单详情，进行确定付款
     */
    String SYNERGY_BUY = "tpl.synergy_paybuy";

    @RequestMapping(value = "/member/synergy_buy.jspx")
    public String save(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                       String institutionID, String paymentNo, String orderNo, double amount, double fee, String payerID,
                       String payerName, String usage, String remark, String notificationURL, String payees,
                       String bankID, String bankName, Integer accountType, String cardType, Integer managerId, String label, String count, String orderId) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        institutionID = "002818";
        SPOrder order = ALDPayService.findOrderById(orderId);
        amount = order.getPrice();
        Map attributes = ALDPayService.pay(orderId, user, institutionID, paymentNo, orderNo, amount, fee, payerID, payerName, usage, remark, notificationURL, payees, bankID, bankName, accountType, cardType);
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
        List<SPOrderObj> list = order.getSOrderObjList();
        model.addAttribute("orderList", list);
        model.addAttribute("price", order.getPrice());

        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_PAYMENT, SYNERGY_BUY);


    }

    /**
     * @author ldong
     * @Date 2017/5/3 9:47
     * @return 前台接受支付结果
     */
    String SYNERGY_PAYRESULT = "tpl.synergy_payResult";

    @RequestMapping(value = "/synergy_receive_notice.jspx", method = RequestMethod.POST)
    public String save(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                       String message, String signature) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        System.out.println(message);
        message = message.replace(" ", "+");
        ALDPayService.receiveNotice(message, signature);
        //return  FrontUtils.showBaseMessage(request,model,"nihao","rjyyRjjsym/index.jhtml");
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_PAYMENT, SYNERGY_PAYRESULT);
    }

    /**
     * @author ldong
     * @Date 2017/5/3 11:06
     * @return 买家申请退款
     */
    String SYNERGYREFUND = "tpl.SynergyRefundadmin";

    @RequestMapping(value = "/member/synergy_demand_clickRefund.jspx")
    public String applyRefund(HttpServletRequest request, HttpServletResponse response, ModelMap model, String orderId,
                              String flag) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
        // 在当前页面获取 订单号    结算金额（数据库）   账户类型 （填写）  机构号码（默认）
        //账户名称   账户类型等需要填写
        SPOrder order = ALDPayService.findOrderById(orderId);
        SPPay sp = order.getSpPay();
        if (sp == null) {
            return FrontUtils.showBaseMessage(request, model, "订单信息错误", "/member/synergy_demand_order_list.jspx");
        }
        double amount = sp.getAmount();//分（数据库取值）
        String SerialNumber = null;
        try {
            SerialNumber = GUIDGenerator.genGUID();
        } //交易流水号
        catch (Exception e) {
            e.printStackTrace();
        }
        String action = null;
        if (flag.equals("1")) {
            action = "software_settle.jspx";
        } else if (flag.equals("0")) {
            action = "synergy_demand_saveRefund.jspx";
        }
        model.addAttribute("orderNo", orderId);
        model.addAttribute("amount", amount);
        model.addAttribute("serialNumber", SerialNumber);
        model.addAttribute("actiona", action);
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_PAYMENT, SYNERGYREFUND);
    }

    /**
     * @return 保存退款信息
     * @author ldong
     * @Date 2017/5/3 11:14
     */

    @RequestMapping(value = "/member/synergy_demand_saveRefund.jspx")
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
        SPOrder sorder = ALDPayService.findOrderById(orderId);
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
        ALDPayService.updateOrder(sorder);
        return FrontUtils.showSuccess(request,
                model, "/member/synergy_demand_order_list.jspx?statusType=2");
    }

    /**
     * @return 改变订单状态
     * @author ldong
     * @Date 2017/5/3 14:52
     */


    @RequestMapping(value = "/member/synergy_editOrderStatus.jspx")
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
        this.ALDPayService.editOrderStatus(orderId, setstatus,isOnline);
        String nextUrl = "/member/synergy_demand_order_list.jspx?statusType=" + tabState;
        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    @RequestMapping(value = "/member/synergy_editOrderStatus2.jspx")
    public String editOrderStatus2(HttpServletRequest request, HttpServletResponse response, ModelMap model, String orderId, String setstatus, String tabState,String isOnline
    ) throws Exception {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
      /*  return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_PAYMENT, NEXTURL);*/
        this.ALDPayService.editOrderStatus(orderId, setstatus,isOnline);
        String nextUrl = "/member/abilitySellerOrder_list.jspx?statusType=" + tabState;
        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    @RequestMapping(value = "/member/synergy_editOrderStatus3.jspx")
    public String editOrderStatus3(HttpServletRequest request, HttpServletResponse response, ModelMap model, String orderId, String setstatus, String tabState,String isOnline
    ) throws Exception {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
      /*  return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_PAYMENT, NEXTURL);*/
        this.ALDPayService.editOrderStatus(orderId, setstatus,isOnline);
        String nextUrl = "/member/deviceSellerOrder_list.jspx?statusType=" + tabState;
        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    /**
     * @author ldong
     * @Date 2017/5/4 15:52
     * @return 管理员结算手续费
     */
    String ADMINSETTLEDIV = "tpl.admin_settle_fee_div";

    @RequestMapping(value = "/member/admin_apply_settle_fee.jspx")
    public String settleFee(HttpServletRequest request, HttpServletResponse response, ModelMap model, Integer orderId, String setstatus, String tabState
    ) throws Exception {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
        if (!user.getIsAdmin()) {
            return FrontUtils.showMessage(request, model, "error.noPermission");
        }
        // 在当前页面获取 订单号    结算金额（数据库）   账户类型 （填写）  机构号码（默认）
        //账户名称   账户类型等需要填写
        SPAdminSettle spo = this.ALDPayService.findSadminSettle(orderId);
        String OrderNo = spo.getOrdeId(); //订单号（数据库取值）
        double fee = spo.getFee();//分（数据库取值）
        String SerialNumber = GUIDGenerator.genGUID(); //交易流水号

        model.addAttribute("orderNo", OrderNo);
        model.addAttribute("amount", fee);
        model.addAttribute("serialNumber", SerialNumber);
        model.addAttribute("id", orderId);
        //model.addAttribute("AccountType",AccountType);
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_PAYMENT, ADMINSETTLEDIV);
    }

    /**
     * @return 订单删除
     * @author ldong
     * @Date 2017/5/4 19:08
     */

    @RequestMapping(value = "/member/admin_settle_del.jspx")
    public String settle(HttpServletRequest request, HttpServletResponse response, ModelMap model, Integer id, String flag
    ) {
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
        this.ALDPayService.delAdminSettle(id);
        if (flag == "1") {
            return FrontUtils.showSuccess(request,
                    model, "/member/supplychain_order_list.jspx?state=2");
        } else {
            return FrontUtils.showSuccess(request,
                    model, "/member/synergy_seller_list.jspx?state=2");
        }
    }

    @RequestMapping(value = "/member/admin_settle_apply.jspx")
    public String settle(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                         String SerialNumber,
                         Integer AccountType,
                         String BankID,
                         String bankName,
                         String AccountName,
                         String AccountNumber,
                         String BranchName,
                         String Province,
                         String City, int id, String flag/*(设置标志位确定是结算还是退款 0 退款  1结算)*/) throws Exception {
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
        String result = this.ALDPayService.settle(SerialNumber, "", "",
                123L,
                AccountType,
                BankID,
                bankName,
                AccountName,
                AccountNumber,
                BranchName,
                Province,
                City, id);
      /*  return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_PAYMENT, NEXTURL);*/
//        return FrontUtils.showSuccess(request,
//                model, "/member/index.jspx");
        if (result.equals("success")) {
            return FrontUtils.showSuccess(request,
                    model, "/member/synergy_seller_list.jspx?state=2");
        } else {
            return FrontUtils.showBaseMessage(request,
                    model, result, "/member/synergy_seller_list.jspx?state=2");
        }
    }

}
