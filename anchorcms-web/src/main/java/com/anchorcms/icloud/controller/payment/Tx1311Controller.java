package com.anchorcms.icloud.controller.payment;

import com.anchorcms.common.utils.CmsUtils;
import com.anchorcms.common.utils.FrontUtils;
import com.anchorcms.core.model.CmsSite;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.dao.cloudcenter.CloudMangerDao;
import com.anchorcms.icloud.model.cloudcenter.SIcloudManage;
import com.anchorcms.icloud.model.common.SOrderPayment;
import com.anchorcms.icloud.service.cloudcenter.CloudCenterPaymentService;
import com.anchorcms.icloud.service.payment.Tx1311Service;
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
import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

import static com.anchorcms.common.constants.Constants.TPLDIR_PAYMENT;

/**
 * Created by ly on 2017/2/15.
 */
@Controller
public class Tx1311Controller {
    private static final Logger log = LoggerFactory.getLogger(Tx1311Controller.class);
    public static final String DOWNLOADPAY = "tpl.paymentDownloadPay";
    public static final String ORDERSUCCESS = "tpl.orderSuccess";


    /**
     * @Author ly
     * @Date 2017/2/15 14:06
     * @Desc 中金支付
     */
    @RequestMapping(value = "/orderPay.jspx", method = RequestMethod.POST)
    public String add(HttpServletRequest request, HttpServletResponse response,
                      ModelMap model, Integer managerId, String count,//数量
                      Date rentTimeStart, // 租赁开始时间
                      String rentTime, // 租赁时长
                      Date rentTimeEnd, // 租赁结束时间
                      Double allPrice, // 总价
                      String  contact, // 联系人
                      String mobile, // 联系电话
                      String msg, // 申请说明
                      String capacityUnit, // 使用容量
                      String specVersion, // 租赁单位
                      String rentHourStart, // 租赁开始时间(小时)
                      String rentHourEnd // 租赁结束时间(小时)
                      ) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
        String amount = request.getParameter("allPrice");
        // 订单号
        String guId = null;
        // 支付流水号
        try {
            guId = GUIDGenerator.genGUID();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String nextUrl = "/yzyjyzxYzyck/index.jhtml";
        SOrderPayment payment = new SOrderPayment();
        payment.setOrderNo(guId);
        payment.setCount(count);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 处理租赁开始与结束日期
        if (rentTimeStart == null) {
            try {
                java.util.Date date = sdf.parse(rentHourStart);
                payment.setRentTimeStart(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            payment.setRentTimeStart(rentTimeStart);
        }
        if (rentTimeEnd == null) {
            try {
                java.util.Date date = sdf.parse(rentHourEnd);
                payment.setRentAllTime(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            payment.setRentAllTime(rentTimeEnd);
        }
        payment.setRentTime(rentTime);
        payment.setAmount(allPrice);
        payment.setContact(contact);
        payment.setMobile(mobile);
        payment.setMsg(msg);
        payment.setCapacityUnit(capacityUnit);
        payment.setSpecVersion(specVersion);

        SIcloudManage manage = cloudMangerDao.getMangerById(managerId);
        payment.setManage(manage);
        payment.setState(0);
        payment.setPayUser(user);
        payment.setCreateDT(new Timestamp(System.currentTimeMillis()));
        SOrderPayment orderPayment = paymentService.save(payment,managerId);
        model.addAttribute("count",count);
        model.addAttribute("managerId",managerId);
        model.addAttribute("user",user);
        model.addAttribute("guId",guId);
        model.addAttribute("site", site);
        model.addAttribute("amount",amount);
        model.addAttribute("sessionId",request.getSession().getId());
        return FrontUtils.showSuccess(request, model, nextUrl);
    }

    /**
     * @Author ly
     * @Date 2017/2/15 15:14
     * @Desc 中金支付
     */
    @RequestMapping(value = "/order.jspx", method = RequestMethod.POST)
    public String save(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                             String institutionID, String paymentNo, String orderNo, double amount, double fee, String payerID,
                             String payerName, String usage, String remark, String notificationURL, String payees,
                       String bankID, String bankName, Integer accountType, String cardType,Integer managerId,String label,String count){
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        //金钱*100
        Long Allamount = new Double(amount*100).longValue();
        long fees = new Double(fee*100).longValue();
        institutionID="002818";
        Map attributes = service.checkCode(institutionID, paymentNo, orderNo, Allamount, fees, payerID, payerName,
                usage, remark, notificationURL, payees, bankID, accountType, cardType);
        model.addAttribute("RequestPlainText", attributes.get("RequestPlainText"));
        model.addAttribute("message", attributes.get("message"));
        model.addAttribute("signature", attributes.get("signature"));
        model.addAttribute("txCode", attributes.get("txCode"));
        model.addAttribute("txName", attributes.get("txName"));
        model.addAttribute("Flag", attributes.get("flag"));
        model.addAttribute("action", PaymentEnvironment.paymentURL);
        SIcloudManage manage = cloudMangerDao.getMangerById(managerId);
        model.addAttribute("manage",manage);
        model.addAttribute("institutionID",institutionID);//机构号码
        model.addAttribute("managerId",managerId);
        model.addAttribute("orderNo",orderNo);//订单号
        model.addAttribute("paymentNo",paymentNo);//支付流水号
        model.addAttribute("amount",amount);//付款金额
        model.addAttribute("fee",fee);//支付服务手续费
        model.addAttribute("payerID",payerID);//付款者ID
        model.addAttribute("label",label);//资金用途
        model.addAttribute("remark",remark);//订单描述
        model.addAttribute("payees",payees);//收款人
        model.addAttribute("bankID",bankID);//银行ID
        model.addAttribute("bankName",bankName);//银行ID
        model.addAttribute("accountType",accountType);//账号类型
        model.addAttribute("cardType",cardType);//卡类型
        model.addAttribute("price",(fee+amount));
        model.addAttribute("payerName",payerName);
        SOrderPayment payment = paymentService.getByOrderNo(orderNo);
        payment.setInstitutionId(institutionID);
        payment.setPaymentNo(paymentNo);
        payment.setAmount(amount);
        payment.setFee(fee);
        payment.setUeage(label);
        payment.setRemark(remark);
        payment.setPayees(payees);
        payment.setBankId(bankID);
        payment.setBankName(bankName);
        payment.setAccountType(accountType);
        payment.setCardType(cardType);
            paymentService.update(payment);
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_PAYMENT, ORDERSUCCESS);
    }

    public static final String CLOUDREFUND = "tpl.cloudRefund";
    /**
     * @Author zhouyq
     * @Date 2017/3/25
     * @Desc 中金支付   退款结算接口
     * flag 结算或者退款标置位 1 为结算  0为退款
     */
    @RequestMapping(value = "/cloudRefund.jspx")
    public String refund(HttpServletRequest request, HttpServletResponse response, ModelMap model, String orderNo,
                         String flag) throws Exception {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
        // 非管理员不能修改
        if (!user.getIsAdmin()) {
            return FrontUtils.showMessage(request, model, "error.noPermission");
        }
        // 在当前页面获取 订单号    结算金额（数据库）   账户类型 （填写）  机构号码（默认）
        //账户名称   账户类型等需要填写
        SOrderPayment sOrderPayment = cloudCenterPaymentService.getByOrderNo(orderNo);
        sOrderPayment.setState(31);
        String OrderNo = sOrderPayment.getOrderNo(); // 订单号（数据库取值）
        double amount = sOrderPayment.getAmount(); // 分（数据库取值）
        String SerialNumber = GUIDGenerator.genGUID(); // 交易流水号
        //int AccountType = 20; // 交易类型默认支付平台账户20
        model.addAttribute("OrderNo", OrderNo);
        model.addAttribute("Amount", amount);
        model.addAttribute("SerialNumber", SerialNumber);
        model.addAttribute("OrderNo", OrderNo);
        model.addAttribute("spo", sOrderPayment);
        //model.addAttribute("AccountType",AccountType);
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_PAYMENT, CLOUDREFUND);
    }

    /**
     * @author zhouyq
     * @Date 2017/03/25
     * @param orderNo, request, response, model
     * @return
     * @Desc 根据id修改云资源订单状态（拒绝）
     */
    @RequestMapping("/member/cloudOrderMdyState.jspx")
    public String modifyDemandStatById(String orderNo, String status, String nextUrl,
                                       HttpServletRequest request, HttpServletResponse response, ModelMap model, String backReason) throws UnsupportedEncodingException {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
        // 非管理员不能修改
        if (!user.getIsAdmin()) {
            return FrontUtils.showMessage(request, model, "error.noPermission");
        }
        if (backReason != null) {
            backReason = java.net.URLDecoder.decode(backReason, "utf-8");
        }
        tx1311Service.mdyOrderStateById(orderNo, status, backReason);
        return FrontUtils.showSuccess(request, model, nextUrl);
    }


    @Resource
    private Tx1311Service service;
    @Resource
    private CloudCenterPaymentService paymentService;
    @Resource
    private CloudMangerDao cloudMangerDao;
    @Resource
    private CloudCenterPaymentService cloudCenterPaymentService;
    @Resource
    private Tx1311Service tx1311Service;

}
