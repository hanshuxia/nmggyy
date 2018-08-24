package com.anchorcms.icloud.controller.payment;

import com.anchorcms.common.utils.CmsUtils;
import com.anchorcms.common.utils.FrontUtils;
import com.anchorcms.core.model.CmsSite;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.model.common.SOrderPayment;
import com.anchorcms.icloud.model.payment.PaySettlementRefund;
import com.anchorcms.icloud.service.cloudcenter.CloudCenterPaymentService;
import com.anchorcms.icloud.service.payment.Tx1311Service;
import com.anchorcms.icloud.service.payment.impl.SendMessageServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import payment.api.tx.marketorder.Tx1341Request;
import payment.api.util.GUIDGenerator;
import payment.api.vo.BankAccount;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

import static com.anchorcms.common.constants.Constants.TPLDIR_PAYMENT;

/**
 * Created by ldong on 2017-2-20
 */
@Controller
public class Tx1341Controller {
    public static final String ORDERSUCCESS = "tpl.orderSuccess";
    public static final String SETTLEMENT = "tpl.settlement";

    /**
     * @Author ldong
     * @Date 2017/2/15 14:06
     * @Desc 中金支付   结算接口
     */
    @RequestMapping(value = "/settlement_1.jspx", method = RequestMethod.GET)
    public String add(HttpServletRequest request, HttpServletResponse response, ModelMap model
    ) throws Exception {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
        // 在当前页面获取 订单号    结算金额（数据库）   账户类型 （填写）  机构号码（默认）
        //账户名称   账户类型等需要填写

        String OrderNo = "201702201518270085545118149"; //订单号（数据库取值）
        long amount = 1;//分（数据库取值）
        String SerialNumber = GUIDGenerator.genGUID(); //交易流水号
        int AccountType = 20;  //交易类型默认支付平台账户20
        model.addAttribute("OrderNo", OrderNo);
        model.addAttribute("Amount", amount);
        model.addAttribute("SerialNumber", SerialNumber);
        model.addAttribute("AccountType", AccountType);
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_PAYMENT, SETTLEMENT);
    }

    /**
     * @Author ldong
     * @Date 2017/2/15 15:14
     * @Desc 中金支付--结算
     */
    @RequestMapping(value = "/doJS.jspx", method = RequestMethod.POST)
    public String save(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        try {
            // 1.取得参数
            String institutionID = request.getParameter("InstitutionID");
            String serialNumber = request.getParameter("SerialNumber");
            String orderNo = request.getParameter("OrderNo");
            long amount = Long.parseLong(request.getParameter("Amount"));
            //String remark = !request.getParameter("Remark").equals("") ? request.getParameter("Remark").trim() : null;
            int accountType = Integer.parseInt(request.getParameter("AccountType"));
            String paymentAccountName = !request.getParameter("PaymentAccountName").equals("") ? request.getParameter("PaymentAccountName").trim() : null;
            String paymentAccountNumber = !request.getParameter("PaymentAccountNumber").equals("") ? request.getParameter("PaymentAccountNumber").trim() : null;
            String bankID = !request.getParameter("BankID").equals("") ? request.getParameter("BankID").trim() : null;
            String accountName = !request.getParameter("AccountName").equals("") ? request.getParameter("AccountName").trim() : null;
            String accountNumber = !request.getParameter("AccountNumber").equals("") ? request.getParameter("AccountNumber").trim() : null;
            String branchName = !request.getParameter("BranchName").equals("") ? request.getParameter("BranchName").trim() : null;
            String province = !request.getParameter("Province").equals("") ? request.getParameter("Province").trim() : null;
            String city = !request.getParameter("City").equals("") ? request.getParameter("City").trim() : null;

            //String[] paymentNos = request.getParameterValues("PaymentNo");
            // 2.创建交易请求对象
            Tx1341Request tx1341Request = new Tx1341Request();
            tx1341Request.setInstitutionID(institutionID);
            tx1341Request.setSerialNumber(serialNumber);
            tx1341Request.setOrderNo(orderNo);
            tx1341Request.setAmount(amount);
            //tx1341Request.setRemark(remark);
            tx1341Request.setAccountType(accountType);
            tx1341Request.setPaymentAccountName(paymentAccountName);
            tx1341Request.setPaymentAccountNumber(paymentAccountNumber);
            //if (paymentNos != null && paymentNos.length > 0) {
            //    List<String> paymentNoList = new ArrayList<String>();
            //    for (int i = 0; i < paymentNos.length; i++) {
            //        if (StringUtil.isNotEmpty(paymentNos[i])) {
            //            paymentNoList.add(paymentNos[i]);
            //        }
            //    }
            tx1341Request.setPaymentNoList(null);
            //}
            BankAccount bankAccount = new BankAccount();
            bankAccount.setBankID(bankID);
            bankAccount.setAccountName(accountName);
            bankAccount.setAccountNumber(accountNumber);
            bankAccount.setBranchName(branchName);
            bankAccount.setProvince(province);
            bankAccount.setCity(city);
            tx1341Request.setBankAccount(bankAccount);

            // 3.执行报文处理
            tx1341Request.process();
            SendMessageServiceImpl.sendMessage(tx1341Request.getRequestMessage(), tx1341Request.getRequestSignature(), "1341", "市场订单结算一（结算）", "");
        } catch (Exception e) {
            e.printStackTrace();
            processException(request, response, e.getMessage());
        }
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_PAYMENT, ORDERSUCCESS);
    }

    public void processException(HttpServletRequest request, HttpServletResponse response, String errorMessage) {
        try {
            request.setAttribute("errorMessage", errorMessage);
            request.getRequestDispatcher("/ErrorPage.jsp").forward(request, response);
        } catch (Exception e) {
        }
    }

    /**
     * @return 买家申请退款
     * @author ldong
     * @Date 2017/3/25 11:29
     */

    @RequestMapping(value = "/member/cloud_settlement.jspx")
    public String add(HttpServletRequest request, HttpServletResponse response, ModelMap model, String orderNo,
                      String flag) throws Exception {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
        SOrderPayment spo = service.getByOrderNo(orderNo);
        int id = spo.getOrderId();
        double amount = spo.getAmount();//分（数据库取值）
        String SerialNumber = GUIDGenerator.genGUID(); //交易流水号
        //int AccountType = 20;  //交易类型默认支付平台账户20
        String action = null;
        if (flag.equals("1")) {
            action = "cloud_settle.jspx";
        } else if (flag.equals("0")) {
            action = "cloud_save_apply_refund.jspx";
        }
        model.addAttribute("OrderNo", orderNo);
        model.addAttribute("Amount", amount);
        model.addAttribute("SerialNumber", SerialNumber);
        model.addAttribute("id", id);
        model.addAttribute("actiona", action);
        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_PAYMENT, SETTLEMENT);
    }

    @RequestMapping(value = "/member/cloud_save_apply_refund.jspx")
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
        SOrderPayment spo = service.getByOrderNo(OrderNo);
        PaySettlementRefund po = spo.getPaySettlementRefund();
        if (po == null) {
            po = new PaySettlementRefund();
            String u = UUID.randomUUID().toString().replaceAll("-", "");
            po.setId(u);
        }
        po.setAmount(spo.getAmount());

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
        spo.setPaySettlementRefund(po);
        spo.setState(30);
        service.save(spo);
        return FrontUtils.showSuccess(request,
                model, "/member/cloud_resource_order_list.jspx?statusType=" + 3);
    }

    /**
     * @return 云资源退款 中金交互
     * @author ldong
     * @Date 2017/3/25 11:54
     */
    @RequestMapping(value = "/member/cloud_settle.jspx")
    public String settle(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                         String SerialNumber,
                         String OrderNo,
                         String remark,
                         String Amount,
                         int AccountType,
                         String BankID,
                         String bankName,
                         String AccountName,
                         String AccountNumber,
                         String BranchName,
                         String Province,
                         String City, String id, String flag/*(设置标志位确定是结算还是退款 0 退款  1结算)*/) throws Exception {
        SOrderPayment spo = service.getByOrderNo(OrderNo);
        String statusType = "3";
        if (("0").equals(flag)) {
            statusType = "5";
            PaySettlementRefund paySettlementRefund = spo.getPaySettlementRefund();
            if (paySettlementRefund != null) {
                SerialNumber = paySettlementRefund.getSerialNumber(); // 流水号
                OrderNo = paySettlementRefund.getOrderNo(); // 订单号
                remark = spo.getRemark(); // 备注
                AccountType = paySettlementRefund.getAccountType(); // 账号类型
                BankID = paySettlementRefund.getBankId(); // 银行账号
                bankName = paySettlementRefund.getBankName(); // 银行账号
                AccountName = paySettlementRefund.getAcountName(); // 账户名称
                AccountNumber = paySettlementRefund.getAcountNumber(); // 账户号码
                BranchName = paySettlementRefund.getBranchName(); // 网点名称
                Province = paySettlementRefund.getProvince(); // 省市
                City = paySettlementRefund.getCity(); // 城市
            }
        }
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
      String result =   service.settle(SerialNumber, OrderNo, remark,
                123L,
                AccountType,
                BankID,bankName,
                AccountName,
                AccountNumber,
                BranchName,
                Province,
                City, spo, flag);

       /* return FrontUtils.showSuccess(request,
                model, "/member/resource_order_manage_list.jspx?status=" + statusType);*/
        if (result.equals("success")) {
            return FrontUtils.showSuccess(request,
                    model, "/member/resource_order_manage_list.jspx?status=" + statusType);
        } else {
            return FrontUtils.showBaseMessage(request,
                    model,result,"/member/resource_order_manage_list.jspx?status=" + statusType);
        }
    }

    @Resource
    private Tx1311Service Tx1311Service;
    @Resource
    SendMessageServiceImpl SendMessageServiceImpl;
    @Resource
    private CloudCenterPaymentService paymentService;
    @Resource
    private CloudCenterPaymentService service;
}
