package com.anchorcms.icloud.service.payment.impl;

import com.anchorcms.icloud.model.payment.SendMessageVO;
import com.anchorcms.icloud.service.payment.SettlementAndRefundService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import payment.api.tx.marketorder.*;
import payment.api.tx.statement.Tx1810Request;
import payment.api.vo.BankAccount;
import payment.tools.util.XmlUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ldong on 2017/2/22
 */
@Service
public class SettlementAndRefundServiceImpl implements SettlementAndRefundService {

    /**
     * @return 市场订单交易支付查询
     * 机构号 支付流水号
     * @author ldong
     * @Date 2017/2/22 11:14
     */
    public SendMessageVO tx1320(String institutionID, String paymentNo) throws Exception {

        // 2.创建交易请求对象
        Tx1320Request tx1320Request = new Tx1320Request();
        tx1320Request.setInstitutionID(institutionID);
        tx1320Request.setPaymentNo(paymentNo);
        // 3.执行报文处理
        tx1320Request.process();
        // 4.将参数放置
        // //3个交易参数
        String plainText = XmlUtil.createPrettyFormat(XmlUtil.createDocument(tx1320Request.getRequestPlainText()));
        String message = tx1320Request.getRequestMessage();
        String signature =
                tx1320Request.getRequestSignature();
        // //2个信息参数
        String txCode = "1320";
        String txName = "市场订单支付交易查询";
        SendMessageVO so = new SendMessageVO(plainText, message, signature, txCode, txName);
            /*map.put("plainText", plainText);
            map.put("message", message);
            map.put("signature", signature);
            map.put("txCode", txCode);
            map.put("txName", txName);*/
        return so;


    }

    /**
     * @return 市场订单结算交易
     * 需要机构号  结算流水号
     * @author ldong
     * @Date 2017/2/22 11:14
     */
    public SendMessageVO tx1350(String institutionID, String serialNumber) throws Exception {
        // 2.创建交易请求对象
        Tx1350Request tx1350Request = new Tx1350Request();
        tx1350Request.setInstitutionID(institutionID);
        tx1350Request.setSerialNumber(serialNumber);

        // 3.执行报文处理
        tx1350Request.process();

        SendMessageVO so = new SendMessageVO(tx1350Request.getRequestPlainText(),
                tx1350Request.getRequestMessage(), tx1350Request.getRequestSignature(),
                "1350", "市场订单结算交易查询");
       /* request.setAttribute("plainText", tx1350Request.getRequestPlainText());
        request.setAttribute("message", tx1350Request.getRequestMessage());
        request.setAttribute("signature", tx1350Request.getRequestSignature());
        request.setAttribute("txCode", "1350");
        request.setAttribute("txName", "市场订单结算交易查询");*/
        return so;


    }

    /**
     * @return 市场订单结算交易
     * 机构号  对账日期
     * @author ldong
     * @Date 2017/2/22 11:14
     */
    public SendMessageVO tx1810(String institutionID, String date) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date vDate = sdf.parse(date);

        // 2.创建交易请求对象
        Tx1810Request txRequest = new Tx1810Request();
        txRequest.setInstitutionID(institutionID);
        txRequest.setDate(vDate);

        // 3.执行报文处理
        txRequest.process();

        // 4.将参数放置到request对象
        // //3个交易参数
        SendMessageVO so = new SendMessageVO(txRequest.getRequestPlainText(), txRequest.getRequestMessage(),
                txRequest.getRequestSignature(), "1810", "下载交易对账单");
       /* request.setAttribute("plainText", txRequest.getRequestPlainText());
        request.setAttribute("message", txRequest.getRequestMessage());
        request.setAttribute("signature", txRequest.getRequestSignature());
        // //2个信息参数
        request.setAttribute("txCode", "1810");
        request.setAttribute("txName", "下载交易对账单");*/
        // 1个action(支付平台地址)参数
        return so;

    }

    /**
     * @return 退款1341接口 只需要得到退款钱数 和订单号  退款流水号。
     * @author ldong
     * @Date 2017/2/22 12:54
     */

    public SendMessageVO tx1341(String serialNumber,
                                String orderNo,
                                String remark,
                                long amount,
                                int accountType,
                                String bankID,
                                String accountName,
                                String accountNumber,
                                String branchName,
                                String province,
                                String city) throws Exception {

        // 1.取得参数
        String institutionID = "002818";
        //String serialNumber = request.getParameter("SerialNumber");
        //String orderNo = request.getParameter("OrderNo");
        //long amount = Long.parseLong(request.getParameter("Amount"));
        /**
         * 账户类型 11   12   20
         */


        //String paymentAccountName =  "shujuku取值或者默认值";
        //String paymentAccountNumber = "数据库取值或者默认值";
          /*  paymentAccountName      =    !request.getParameter("PaymentAccountName").equals("") ? request.getParameter("PaymentAccountName").trim() : null;
            paymentAccountNumber    =    !request.getParameter("PaymentAccountNumber").equals("") ? request.getParameter("PaymentAccountNumber").trim() : null;
            bankID                  =   !request.getParameter("BankID").equals("") ? request.getParameter("BankID").trim() : null;
            accountName             =    !request.getParameter("AccountName").equals("") ? request.getParameter("AccountName").trim() : null;
            accountNumber            =   !request.getParameter("AccountNumber").equals("") ? request.getParameter("AccountNumber").trim() : null;
            branchName             =      !request.getParameter("BranchName").equals("") ? request.getParameter("BranchName").trim() : null;
            province                =     !request.getParameter("Province").equals("") ? request.getParameter("Province").trim() : null;
            city                     =    !request.getParameter("City").equals("") ? request.getParameter("City").trim() : null;
*/
        //paymentAccountName = paymentAccountName.equals("") ? paymentAccountName.trim() : null;
        //paymentAccountNumber = paymentAccountNumber.equals("") ? paymentAccountNumber.trim() : null;
        remark = !StringUtils.isBlank(remark) ? remark.trim() : null;
        bankID = !StringUtils.isBlank(bankID) ? bankID.trim() : null;
        accountName = !StringUtils.isBlank(accountName) ? accountName.trim() : null;
        accountNumber = !StringUtils.isBlank(accountNumber) ? accountNumber.trim() : null;
        branchName = !StringUtils.isBlank(branchName) ? branchName.trim() : null;
        province = !StringUtils.isBlank(province) ? province.trim() : null;
        city = !StringUtils.isBlank(city) ? city.trim() : null;
        // 2.创建交易请求对象
        Tx1341Request tx1341Request = new Tx1341Request();
        tx1341Request.setInstitutionID(institutionID);
        tx1341Request.setSerialNumber(serialNumber);
        tx1341Request.setOrderNo(orderNo);
        tx1341Request.setAmount(amount);
        tx1341Request.setRemark(remark);
        tx1341Request.setAccountType(accountType);
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
        // 4.将参数放置到map对象
        // //3个交易参数
        SendMessageVO so = new SendMessageVO(tx1341Request.getRequestPlainText(),
                tx1341Request.getRequestMessage(),
                tx1341Request.getRequestSignature(),
                "1341", "市场订单结算一（结算）");
        return so;
    }

    /**
     * @return 退款1343接口
     * @author ldong
     * @Date 2017/3/28 18:19
     */

    public SendMessageVO tx1343(String serialNumber,
                                String orderNo,
                                String remark,
                                long amount,
                                int accountType,
                                String bankID,
                                String accountName,
                                String accountNumber,
                                String branchName,
                                String province,
                                String city) throws Exception {

            // 1.取得参数
            String institutionID = "002818";
            remark = !StringUtils.isBlank(remark) ? remark.trim() : null;
            bankID = !StringUtils.isBlank(bankID) ? bankID.trim() : null;
            accountName = !StringUtils.isBlank(accountName) ? accountName.trim() : null;
            accountNumber = !StringUtils.isBlank(accountNumber) ? accountNumber.trim() : null;
            branchName = !StringUtils.isBlank(branchName) ? branchName.trim() : null;
            province = !StringUtils.isBlank(province) ? province.trim() : null;
            city = !StringUtils.isBlank(city) ? city.trim() : null;
            // 2.创建交易请求对象
            Tx1343Request tx1343Request = new Tx1343Request();
            tx1343Request.setInstitutionID(institutionID);
            tx1343Request.setSerialNumber(serialNumber);
            tx1343Request.setOrderNo(orderNo);
            tx1343Request.setAmount(amount);
            tx1343Request.setRemark(remark);
            tx1343Request.setAccountType(accountType);
            tx1343Request.setPaymentAccountName(null);
            tx1343Request.setPaymentAccountNumber(null);
            BankAccount bankAccount = new BankAccount();
            bankAccount.setBankID(bankID);
            bankAccount.setAccountName(accountName);
            bankAccount.setAccountNumber(accountNumber);
            bankAccount.setBranchName(branchName);
            bankAccount.setProvince(province);
            bankAccount.setCity(city);
            tx1343Request.setBankAccount(bankAccount);

            // 3.执行报文处理
            tx1343Request.process();

            // 4.将参数放置到request对象
            // //3个交易参数
            SendMessageVO so = new SendMessageVO(tx1343Request.getRequestPlainText(),
                    tx1343Request.getRequestMessage(),
                    tx1343Request.getRequestSignature(),
                    "1343", "市场订单结算三（退款）");
            return so;

    }

    /**
     * @return 原路退款接口1333
     * 需要 机构号  退款流水号 订单流水号  支付流水号  退款金额  备注。
     * @author ldong
     * @Date 2017/2/22 15:10
     */
    public SendMessageVO tx1333(String institutionID,
                                String serialNumber,
                                String orderNo,
                                String paymentNo,
                                Long amount,
                                String remark) throws Exception {

        // 1.取得参数
        // institutionID           = request.getParameter("InstitutionID");
        // serialNumber             = request.getParameter("SerialNumber");
        // orderNo                = request.getParameter("OrderNo");
        // paymentNo              = request.getParameter("PaymentNo");
        // amount                  = Long.parseLong(amount);
        remark = remark.equals("") ? remark.trim() : null;

        // 2.创建交易请求对象
        Tx1333Request tx1333Request = new Tx1333Request();
        tx1333Request.setInstitutionID(institutionID);
        tx1333Request.setSerialNumber(serialNumber);
        tx1333Request.setOrderNo(orderNo);
        tx1333Request.setPaymentNo(paymentNo);
        tx1333Request.setAmount(amount);
        tx1333Request.setRemark(remark);

        // 3.执行报文处理
        tx1333Request.process();

        // 4.将参数放置到request对象
        // //3个交易参数
        SendMessageVO so = new SendMessageVO(tx1333Request.getRequestPlainText(), tx1333Request.getRequestMessage(),
                tx1333Request.getRequestSignature(), "1333", "原路退款");
       /* map.put("plainText", tx1333Request.getRequestPlainText());
        map.put("message", tx1333Request.getRequestMessage());
        map.put("signature", tx1333Request.getRequestSignature());
        //2 个信息参数
        map.put("txCode", "1333");
        map.put("txName", "原路退款");*/
        // 1个action(支付平台地址)参数
        return so;
    }


}
