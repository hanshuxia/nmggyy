package com.anchorcms.icloud.service.payment.impl;

import com.anchorcms.icloud.dao.software.SoftwareBuyDao;
import com.anchorcms.icloud.dao.supplychain.SLDOrderDao;
import com.anchorcms.icloud.dao.synergy.OrderPayDao;
import com.anchorcms.icloud.model.common.SOrderPayment;
import com.anchorcms.icloud.model.payment.PaySettlementRefund;
import com.anchorcms.icloud.model.payment.SPOrder;
import com.anchorcms.icloud.model.payment.SPPay;
import com.anchorcms.icloud.model.payment.SSupplychainOrder;
import com.anchorcms.icloud.model.software.SSoftwareBuy;
import com.anchorcms.icloud.service.cloudcenter.CloudCenterPaymentService;
import com.anchorcms.icloud.service.payment.ReceiveNoticeBackService;
import com.anchorcms.icloud.service.software.SoftwareService;
import com.anchorcms.icloud.service.supplychain.SLDOrderService;
import com.anchorcms.icloud.service.synergy.ALDPayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import payment.api.notice.*;
import payment.api.vo.BankAccount;
import payment.tools.util.Base64;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * Created by ly on 2017/2/17.
 */
@Service
@Transactional
public class ReceiveNoticeServiceBackImpl implements ReceiveNoticeBackService {

    private static final Logger logger = LoggerFactory.getLogger(ReceiveNoticeServiceBackImpl.class);

    public void receive(HttpServletRequest request, HttpServletResponse response) throws Exception {

        try {
            logger.info("---------- Begin [ReceiveNotice] process......");

            // 1 获得参数message和signature
            String message = request.getParameter("message");
            String signature = request.getParameter("signature");
            message = message.replace(" ", "+");
            logger.info("[message]=[" + message + "]");
            logger.info("[signature]=[" + signature + "]");

            // 2 生成交易结果对象
            NoticeRequest noticeRequest = new NoticeRequest(message, signature);

            // 3 业务处理
            if ("7618".equals(noticeRequest.getTxCode())) {
                Notice7618Request nr = new Notice7618Request(noticeRequest.getDocument());
                // ！！！ 在这里添加商户处理逻辑！！！
                // 以下为演示代码
                logger.info("[TxCode]       = [7618]");
                logger.info("[TxName]       = [订单支付状态变更通知(银联)]");
                logger.info("[InstitutionID]= [" + nr.getInstitutionID() + "]");
                logger.info("[PaymentNo]    = [" + nr.getPaymentNo() + "]");
                logger.info("[Amount]       = [" + nr.getAmount() + "]");
                logger.info("[Status]       = [" + nr.getStatus() + "]");
                logger.info("[BankNotificationTime] = [" + nr.getBankNotificationTime() + "]");
                logger.info("[TraceNo]      = [" + nr.getTraceNo() + "]");
                logger.info("[TransTime]    = [" + nr.getTransTime() + "]");
                logger.info("[MerchantID]   = [" + nr.getMerchantID() + "]");
                logger.info("[MerchantName] = [" + nr.getMerchantName() + "]");

                if (20 == nr.getStatus()) {
                    logger.info("receive 7618 notification success");
                }

            } else if ("1118".equals(noticeRequest.getTxCode())) {
                Notice1118Request nr = new Notice1118Request(noticeRequest.getDocument());
                // ！！！ 在这里添加商户处理逻辑！！！
                // 以下为演示代码
                logger.info("[TxCode]       = [1118]");
                logger.info("[TxName]       = [商户订单支付状态变更通知]");
                logger.info("[InstitutionID]= [" + nr.getInstitutionID() + "]");
                logger.info("[PaymentNo]    = [" + nr.getPaymentNo() + "]");
                logger.info("[Amount]       = [" + nr.getAmount() + "]");
                logger.info("[Status]       = [" + nr.getStatus() + "]");
                logger.info("[BankNotificationTime]       = [" + nr.getBankNotificationTime() + "]");
                if (20 == nr.getStatus()) {
                    logger.info("receive 1118 notification success");
                }

            } else if ("1119".equals(noticeRequest.getTxCode())) {
                Notice1119Request nr = new Notice1119Request(noticeRequest.getDocument());
                // ！！！ 在这里添加商户处理逻辑！！！
                // 以下为演示代码
                logger.info("[TxCode]       = [1119]");
                logger.info("[TxName]       = [商户订单支付状态变更通知(监管银行)]");
                logger.info("[InstitutionID]= [" + nr.getInstitutionID() + "]");
                logger.info("[PaymentNo]    = [" + nr.getPaymentNo() + "]");
                logger.info("[Amount]       = [" + nr.getAmount() + "]");
                logger.info("[Status]       = [" + nr.getStatus() + "]");
                logger.info("[PaidTime]       = [" + nr.getPaidTime() + "]");
                if (20 == nr.getStatus()) {
                    logger.info("receive 1119 notification success");
                }

            } else if ("1138".equals(noticeRequest.getTxCode())) {
                Notice1138Request nr = new Notice1138Request(noticeRequest.getDocument());
                // ！！！ 在这里添加商户处理逻辑！！！
                // 以下为演示代码
                logger.info("[TxCode]       = [1138]");
                logger.info("[TxName]       = [商户订单退款结算状态变更通知]");
                logger.info("[InstitutionID]= [" + nr.getInstitutionID() + "]");
                logger.info("[SerialNumber] = [" + nr.getSerialNumber() + "]");
                logger.info("[PaymentNo] = [" + nr.getPaymentNo() + "]");
                logger.info("[Amount]       = [" + nr.getAmount() + "]");
                logger.info("[Status]       = [" + nr.getStatus() + "]");
                logger.info("[RefundTime]       = [" + nr.getRefundTime() + "]");
                logger.info("receive 1138 notification success");
            } else if ("1306".equals(noticeRequest.getTxCode())) {
                Notice1306Request nr = new Notice1306Request(noticeRequest.getDocument());
                // ！！！ 在这里添加商户处理逻辑！！！
                // 以下为演示代码
                logger.info("[TxCode]       = [1306]");
                logger.info("[TxName]       = [市场订单绑定并支付前台模式支付状态变更通知]");
                logger.info("[InstitutionID]=[" + nr.getInstitutionID() + "]");
                logger.info("[OrderNo]      =[" + nr.getOrderNo() + "]");
                logger.info("[TxSN]         =[" + nr.getTxSNBinding() + "]");
                logger.info("[PaymentNo]    =[" + nr.getPaymentNo() + "]");
                logger.info("[Status]       =[" + nr.getStatus() + "]");
                logger.info("[BankTxTime]   =[" + nr.getBankTxTime() + "]");
                logger.info("[ResponseCode] =[" + nr.getResponseCode() + "]");
                logger.info("[ResponseMessage]=[" + nr.getResponseMessage() + "]");
                logger.info("[BankID]        =[" + nr.getBankID() + "]");
                logger.info("[AccountName]   =[" + nr.getAccountName() + "]");
                logger.info("[AccountNumber] =[" + nr.getAccountNumberTail() + "]");
                if (20 == nr.getStatus()) {
                    logger.info("receive 1306 notification success");
                }
            } else if ("1318".equals(noticeRequest.getTxCode())) {
                Notice1318Request nr = new Notice1318Request(noticeRequest.getDocument());
                // ！！！ 在这里添加商户处理逻辑！！！

                // 以下为演示代码
                logger.info("[TxCode]       = [1318]");
                logger.info("[TxName]       = [市场订单支付状态变更通知]");
                logger.info("[InstitutionID]= [" + nr.getInstitutionID() + "]");
                logger.info("[PaymentNo]    = [" + nr.getPaymentNo() + "]");
                logger.info("[Amount]       = [" + nr.getAmount() + "]");
                logger.info("[Status]       = [" + nr.getStatus() + "]");
                logger.info("[BankNotificationTime]       = [" + nr.getBankNotificationTime() + "]");
                if (20 == nr.getStatus()) {
                    //logger.info("receive 1318 notification success");
                    String paymentNo = nr.getPaymentNo();
                    SOrderPayment payment = paymentService.getByPaymentNo(paymentNo);
                    SSoftwareBuy so = softwareService.findSOrderByPNo(paymentNo);
                    SPPay sp = OrderPayDao.getBypaymentNo(paymentNo);
                    if (sp != null) {
                        String id = sp.getOrderNo();
                        SPOrder spo = OrderPayDao.getOrderById(id);
                        SSupplychainOrder suo = SLDOrderDao.getOrderById(id);
                        if (spo != null) {
                            ALDPayService.receiveNotice(message, signature);
                        } else if (suo != null) {
                            SLDOrderService.receiveNotice(message, signature);
                        }

                    }
                    if (null != payment) {
                        //做云资源状态改变的功能
                        paymentService.getUpdateByPaymentNo(paymentNo);
                    } else if (so != null) {
                        softwareService.receiveNotice(message, signature);
                    }

                }
            } else if ("1348".equals(noticeRequest.getTxCode())) {
                Notice1348Request nr = new Notice1348Request(noticeRequest.getDocument());
                // ！！！ 在这里添加商户处理逻辑！！！
                String serNo = nr.getSerialNumber();
                PaySettlementRefund pay = SoftwareBuyDao.findbySerialNumber(serNo);
                // 以下为演示代码
                logger.info("[TxCode]       = [1348]");
                logger.info("[TxName]       = [市场订单结算状态变更通知]");
                logger.info("[InstitutionID]= [" + nr.getInstitutionID() + "]");
                logger.info("[OrderNo]      = [" + nr.getOrderNo() + "]");
                logger.info("[SerialNumber] = [" + nr.getSerialNumber() + "]");
                logger.info("[Amount]       = [" + nr.getAmount() + "]");
                logger.info("[Status]       = [" + nr.getStatus() + "]");
                logger.info("[TransferTime]       = [" + nr.getTransferTime() + "]");
                if (40 == nr.getStatus() || 50 == nr.getStatus()) {
                    pay.setFlag("40");
                    SoftwareBuyDao.updatePaySettle(pay);
                }
            } else if ("1353".equals(noticeRequest.getTxCode())) {
                Notice1353Request nr = new Notice1353Request(noticeRequest.getDocument());
                // ！！！ 在这里添加商户处理逻辑！！！
                // 以下为演示代码
                logger.info("[TxName]       = [订购支付结果通知]");
                logger.info("[TxCode]       = [1353]");
                logger.info("[InstitutionID]= [" + nr.getInstitutionID() + "]");
                logger.info("[PaymentNO]       = [" + nr.getPaymentNo() + "]");
                logger.info("[Amount]       = [" + nr.getAmount() + "]");
                logger.info("[Status]       = [" + nr.getStatus() + "]");
                logger.info("[BankTxTime]       = [" + nr.getBankTxTime() + "]");
                logger.info("[BankResponseCode]       = [" + nr.getBankResponseCode() + "]");
                logger.info("[BankResponseMessage]       = [" + nr.getBankResponseMessage() + "]");
                logger.info("[Remark]       = [" + nr.getRemark() + "]");
                logger.info("[PayCardType]       = [" + nr.getPayCardType() + "]");

            } else if ("1363".equals(noticeRequest.getTxCode())) {
                Notice1363Request nr = new Notice1363Request(noticeRequest.getDocument());
                // ！！！ 在这里添加商户处理逻辑！！！
                // 以下为演示代码
                logger.info("[TxName]       = [单笔代收结果通知]");
                logger.info("[TxCode]       = [1363]");
                logger.info("[InstitutionID]= [" + nr.getInstitutionID() + "]");
                logger.info("[TxSN]    = [" + nr.getTxSN() + "]");
                logger.info("[OrderNo]      = [" + nr.getOrderNo() + "]");
                logger.info("[Amount]       = [" + nr.getAmount() + "]");
                logger.info("[Status]       = [" + nr.getStatus() + "]");
                logger.info("[BankTxTime]       = [" + nr.getBankTxTime() + "]");
                logger.info("[ResponseCode]       = [" + nr.getResponseCode() + "]");
                logger.info("[ResponseMessage]       = [" + nr.getResponseMessage() + "]");
                if (30 == nr.getStatus() || 40 == nr.getStatus()) {
                    logger.info("receive 1363 notification success");
                }
            } else if ("1462".equals(noticeRequest.getTxCode())) {
                Notice1462Request nr = new Notice1462Request(noticeRequest.getDocument());
                // ！！！ 在这里添加商户处理逻辑！！！
                // 以下为演示代码
                logger.info("[TxName]       = [市场订单单笔代收结果通知（短信确认）]");
                logger.info("[TxCode]       = [1462]");
                logger.info("[InstitutionID]= [" + nr.getInstitutionID() + "]");
                logger.info("[TxSN]    = [" + nr.getTxSN() + "]");
                logger.info("[OrderNo]      = [" + nr.getOrderNo() + "]");
                logger.info("[Amount]       = [" + nr.getAmount() + "]");
                logger.info("[Status]       = [" + nr.getStatus() + "]");
                logger.info("[BankTxTime]       = [" + nr.getBankTxTime() + "]");
                logger.info("[ResponseCode]       = [" + nr.getResponseCode() + "]");
                logger.info("[ResponseMessage]       = [" + nr.getResponseMessage() + "]");
                if (30 == nr.getStatus() || 40 == nr.getStatus()) {
                    logger.info("receive 1462 notification success");
                }
            } else if ("1388".equals(noticeRequest.getTxCode())) {
                Notice1388Request nr = new Notice1388Request(noticeRequest.getDocument());
                // ！！！ 在这里添加商户处理逻辑！！！
                // 以下为演示代码
                logger.info("[TxCode]       = [1388]");
                logger.info("[TxName]       = [市场订单POS支付成功通知]");
                logger.info("[InstitutionID]= [" + nr.getInstitutionID() + "]");
                logger.info("[PaymentNo]    = [" + nr.getPaymentNo() + "]");
                logger.info("[PhoneNumber]  = [" + nr.getPhoneNumber() + "]");
                logger.info("[Amount]       = [" + nr.getAmount() + "]");
                logger.info("[Status]       = [" + nr.getStatus() + "]");
                logger.info("[TxTime]       = [" + nr.getTxTime() + "]");
                logger.info("[BankTxTime]   = [" + nr.getBankTxTime() + "]");
                logger.info("[Remark]       = [" + nr.getRemark() + "]");
                if (20 == nr.getStatus()) {
                    logger.info("receive 1388 notification success");
                }
            } else if ("1394".equals(noticeRequest.getTxCode())) {
                Notice1394Request nr = new Notice1394Request(noticeRequest.getDocument());
                // ！！！ 在这里添加商户处理逻辑！！！
                // 以下为演示代码
                logger.info("[TxCode]       = [1394]");
                logger.info("[TxName]       = [市场订单认证支付结果通知]");
                logger.info("[InstitutionID]= [" + nr.getInstitutionID() + "]");
                logger.info("[PaymentNo]    = [" + nr.getPaymentNo() + "]");
                logger.info("[Status]       = [" + nr.getStatus() + "]");
                logger.info("[BankTxTime]       = [" + nr.getBankTxTime() + "]");
                logger.info("[ResponseCode]       = [" + nr.getResponseCode() + "]");
                logger.info("[ResponseMessage]       = [" + nr.getResponseMessage() + "]");
                logger.info("[PayCardType]       = [" + nr.getPayCardType() + "]");
                if (20 == nr.getStatus()) {
                    logger.info("receive 1394 notification success");
                }

            } else if ("1438".equals(noticeRequest.getTxCode())) {
                Notice1438Request nr = new Notice1438Request(noticeRequest.getDocument());
                // ！！！ 在这里添加商户处理逻辑！！！
                // 以下为演示代码
                logger.info("[TxCode]       = [1438]");
                logger.info("[TxName]       = [保证金退还处理结果通知]");
                logger.info("[InstitutionID]= [" + nr.getInstitutionID() + "]");
                logger.info("[SerialNumber] = [" + nr.getSerialNumber() + "]");
                logger.info("[PaymentNo]    = [" + nr.getPaymentNo() + "]");
                logger.info("[Amount]       = [" + nr.getAmount() + "]");
                logger.info("[Interest]     = [" + nr.getInterest() + "]");
                logger.info("[Status]       = [" + nr.getStatus() + "]");
                logger.info("receive 1438 notification success");
            } else if ("1712".equals(noticeRequest.getTxCode())) {
                Notice1712Request nr = new Notice1712Request(noticeRequest.getDocument());
                // ！！！ 在这里添加商户处理逻辑！！！
                // 以下为演示代码
                logger.info("[TxCode]       = [1712]");
                logger.info("[TxName]       = [预授权成功结果通知]");
                logger.info("[InstitutionID]= [" + nr.getInstitutionID() + "]");
                logger.info("[OrderNo]      = [" + nr.getOrderNo() + "]");
                logger.info("[Amount]       = [" + nr.getAmount() + "]");
                logger.info("[Status]       = [" + nr.getStatus() + "]");
                logger.info("[BankTxTime]       = [" + nr.getBankTxTime() + "]");
                if (20 == nr.getStatus()) {
                    logger.info("receive 1712 notification success");
                }
            } else if ("1722".equals(noticeRequest.getTxCode())) {
                Notice1722Request nr = new Notice1722Request(noticeRequest.getDocument());
                // ！！！ 在这里添加商户处理逻辑！！！
                // 以下为演示代码
                logger.info("[TxCode]       = [1722]");
                logger.info("[TxName]       = [预授权撤销结果通知]");
                logger.info("[InstitutionID]= [" + nr.getInstitutionID() + "]");
                logger.info("[TxSN]      = [" + nr.getTxSN() + "]");
                logger.info("[OrderNo]      = [" + nr.getOrderNo() + "]");
                logger.info("[Status]       = [" + nr.getStatus() + "]");
                logger.info("[BankTxTime]       = [" + nr.getBankTxTime() + "]");
                if (20 == nr.getStatus() || 30 == nr.getStatus()) {
                    logger.info("receive 1722 notification success");
                }
            } else if ("1732".equals(noticeRequest.getTxCode())) {
                Notice1732Request nr = new Notice1732Request(noticeRequest.getDocument());
                // ！！！ 在这里添加商户处理逻辑！！！
                // 以下为演示代码
                logger.info("[TxCode]       = [1732]");
                logger.info("[TxName]       = [预授权扣款结果通知]");
                logger.info("[InstitutionID]= [" + nr.getInstitutionID() + "]");
                logger.info("[TxSN]      = [" + nr.getTxSN() + "]");
                logger.info("[OrderNo]      = [" + nr.getOrderNo() + "]");
                logger.info("[Status]       = [" + nr.getStatus() + "]");
                logger.info("[BankTxTime]       = [" + nr.getBankTxTime() + "]");
                if (20 == nr.getStatus() || 30 == nr.getStatus()) {
                    logger.info("receive 1732 notification success");
                }
            } else if ("2018".equals(noticeRequest.getTxCode())) {
                Notice2018Request nr = new Notice2018Request(noticeRequest.getDocument());
                // ！！！ 在这里添加商户处理逻辑！！！
                // 以下为演示代码
                logger.info("[TxName]       = [单笔代收结果通知]");
                logger.info("[TxCode]       = [2018]");
                logger.info("[InstitutionID]= [" + nr.getInstitutionID() + "]");
                logger.info("[TxSN]    = [" + nr.getTxSN() + "]");
                logger.info("[Amount]       = [" + nr.getAmount() + "]");
                logger.info("[Status]       = [" + nr.getStatus() + "]");
                logger.info("[BankTxTime]       = [" + nr.getBankTxTime() + "]");
                logger.info("[ResponseCode]       = [" + nr.getResponseCode() + "]");
                logger.info("[ResponseMessage]       = [" + nr.getResponseMessage() + "]");
                if (30 == nr.getStatus() || 40 == nr.getStatus()) {
                    logger.info("receive 2018 notification success");
                }
            } else if ("2038".equals(noticeRequest.getTxCode())) {
                Notice2038Request nr = new Notice2038Request(noticeRequest.getDocument());
                // ！！！ 在这里添加商户处理逻辑！！！
                // 以下为演示代码
                logger.info("[TxName]       = [单笔代收结果通知（短信确认）]");
                logger.info("[TxCode]       = [2038]");
                logger.info("[InstitutionID]= [" + nr.getInstitutionID() + "]");
                logger.info("[TxSN]    = [" + nr.getTxSN() + "]");
                logger.info("[Amount]       = [" + nr.getAmount() + "]");
                logger.info("[Status]       = [" + nr.getStatus() + "]");
                logger.info("[BankTxTime]       = [" + nr.getBankTxTime() + "]");
                logger.info("[ResponseCode]       = [" + nr.getResponseCode() + "]");
                logger.info("[ResponseMessage]       = [" + nr.getResponseMessage() + "]");
                if (30 == nr.getStatus() || 40 == nr.getStatus()) {
                    logger.info("receive 2038 notification success");
                }
            } else if ("2118".equals(noticeRequest.getTxCode())) {
                Notice2118Request nr = new Notice2118Request(noticeRequest.getDocument());
                // ！！！ 在这里添加商户处理逻辑！！！
                // 以下为演示代码
                logger.info("[TxCode]       = [2118]");
                logger.info("[TxName]       = [分步支付状态变更通知]");
                logger.info("[InstitutionID]=[" + nr.getInstitutionID() + "]");
                logger.info("[PaymentNo]=[" + nr.getPaymentNo() + "]");
                logger.info("[Amount]=[" + nr.getAmount() + "]");
                logger.info("[Fee]=[" + nr.getFee() + "]");
                logger.info("[PayerID]=[" + nr.getPayerID() + "]");
                logger.info("[PayerName]=[" + nr.getPayerName() + "]");
                logger.info("[CustomerID]=[" + nr.getCustomerID() + "]");
                logger.info("[Usage]=[" + nr.getUsage() + "]");
                logger.info("[Remark]=[" + nr.getRemark() + "]");
                logger.info("[SettlementFlag]=[" + nr.getSettlementFlag() + "]");
                logger.info("[Status]       = [" + nr.getStatus() + "]");
                logger.info("[BankNotificationTime]       = [" + nr.getBankNotificationTime() + "]");
                if (20 == nr.getStatus()) {
                    logger.info("receive 2118 notification success");
                }

            } else if ("2138".equals(noticeRequest.getTxCode())) {
                Notice2138Request nr = new Notice2138Request(noticeRequest.getDocument());
                BankAccount bankAccount = nr.getBankAccount();
                // ！！！ 在这里添加商户处理逻辑！！！
                // 以下为演示代码
                logger.info("[TxCode]       = [2138]");
                logger.info("[TxName]       = [分步支付退款结算状态变更通知]");
                logger.info("[InstitutionID]= [" + nr.getInstitutionID() + "]");
                logger.info("[TxSN] = [" + nr.getTxSN() + "]");
                logger.info("[PaymentNo] = [" + nr.getPaymentNo() + "]");
                logger.info("[Amount]       = [" + nr.getAmount() + "]");
                logger.info("[AccountType]       = [" + nr.getAccountType() + "]");
                logger.info("[BankID]       = [" + bankAccount.getBankID() + "]");
                logger.info("[AccountName]       = [" + bankAccount.getAccountName() + "]");
                logger.info("[AccountNumber]       = [" + bankAccount.getAccountNumber() + "]");
                logger.info("[BranchName]       = [" + bankAccount.getBranchName() + "]");
                logger.info("[Province]       = [" + bankAccount.getProvince() + "]");
                logger.info("[City]       = [" + bankAccount.getCity() + "]");
                logger.info("[Status]       = [" + nr.getStatus() + "]");
                logger.info("[RefundTime]       = [" + nr.getRefundTime() + "]");
                logger.info("receive 2138 notification success");
            } else if ("2218".equals(noticeRequest.getTxCode())) {
                Notice2218Request nr = new Notice2218Request(noticeRequest.getDocument());
                // ！！！ 在这里添加商户处理逻辑！！！
                // 以下为演示代码
                logger.info("[TxCode]       = [2218]");
                logger.info("[TxName]       = [O2O订单支付结果通知]");
                logger.info("[InstitutionID]= [" + nr.getInstitutionID() + "]");
                logger.info("[OrderNo]      = [" + nr.getOrderNo() + "]");
                logger.info("[Amount]       = [" + nr.getAmount() + "]");
                logger.info("[SettlementFlag]       = [" + nr.getSettlemenFlag() + "]");
                logger.info("[Status]       = [" + nr.getStatus() + "]");
                logger.info("[BankTxTime]       = [" + nr.getBankTxTime() + "]");
                if (20 == nr.getStatus()) {
                    logger.info("receive 2218 notification success");
                }
            } else if ("2393".equals(noticeRequest.getTxCode())) {
                Notice2393Request nr = new Notice2393Request(noticeRequest.getDocument());
                // ！！！ 在这里添加商户处理逻辑！！！
                // 以下为演示代码
                logger.info("[TxCode]       = [2393]");
                logger.info("[TxName]       = [实名认证结果通知]");
                logger.info("[InstitutionID]= [" + nr.getInstitutionID() + "]");
                logger.info("[TxSN]= [" + nr.getTxSN() + "]");
                logger.info("[Status]       = [" + nr.getStatus() + "]");
                logger.info("[BankTxTime]       = [" + nr.getBankTxTime() + "]");
                logger.info("[AccountName]= [" + nr.getAccountName() + "]");
                logger.info("[AccountNumber]= [" + nr.getAccountNumber() + "]");
                logger.info("[Remark]= [" + nr.getRemark() + "]");
                logger.info("[PayCardType]= [" + nr.getPayCardType() + "]");
                if (20 == nr.getStatus()) {
                    logger.info("receive 2393 notification success");
                }

            } else if ("2552".equals(noticeRequest.getTxCode())) {
                Notice2552Request nr = new Notice2552Request(noticeRequest.getDocument());
                // ！！！ 在这里添加商户处理逻辑！！！
                // 以下为演示代码
                logger.info("[TxCode]       = [2552]");
                logger.info("[TxName]       = [开通认证支付结果通知]");
                logger.info("[InstitutionID]= [" + nr.getInstitutionID() + "]");
                logger.info("[SerialNumber]    = [" + nr.getSerialNumber() + "]");
                logger.info("[Status]       = [" + nr.getStatus() + "]");
                logger.info("[BankTxTime]       = [" + nr.getBankTxTime() + "]");
                logger.info("[ResponseCode]       = [" + nr.getResponseCode() + "]");
                logger.info("[ResponseMessage]       = [" + nr.getResponseMessage() + "]");
                logger.info("[PayCardType]       = [" + nr.getPayCardType() + "]");
                logger.info("[IssInsCode]       = [" + nr.getIssInsCode() + "]");
                if (20 == nr.getStatus()) {
                    logger.info("receive 2552 notification success");
                }

            } else if ("2566".equals(noticeRequest.getTxCode())) {
                Notice2566Request nr = new Notice2566Request(noticeRequest.getDocument());
                // ！！！ 在这里添加商户处理逻辑！！！
                // 以下为演示代码
                logger.info("[TxCode]       = [2566]");
                logger.info("[TxName]       = [绑定并支付前台模式支付状态变更通知]");
                logger.info("[InstitutionID]=[" + nr.getInstitutionID() + "]");
                logger.info("[TxSN]         =[" + nr.getTxSNBinding() + "]");
                logger.info("[PaymentNo]    =[" + nr.getPaymentNo() + "]");
                logger.info("[Status]       =[" + nr.getStatus() + "]");
                logger.info("[BankTxTime]   =[" + nr.getBankTxTime() + "]");
                logger.info("[ResponseCode] =[" + nr.getResponseCode() + "]");
                logger.info("[ResponseMessage]=[" + nr.getResponseMessage() + "]");
                logger.info("[BankID]        =[" + nr.getBankID() + "]");
                logger.info("[AccountName]   =[" + nr.getAccountName() + "]");
                logger.info("[AccountNumber] =[" + nr.getAccountNumberTail() + "]");
                if (20 == nr.getStatus()) {
                    logger.info("receive 2566 notification success");
                }
            } else if ("2818".equals(noticeRequest.getTxCode())) {
                Notice2818Request nr = new Notice2818Request(noticeRequest.getDocument());
                // ！！！ 在这里添加商户处理逻辑！！！
                // 以下为演示代码
                logger.info("[TxCode]       = [2818]");
                logger.info("[TxName]       = [二维码支付状态变更通知]");
                logger.info("[InstitutionID]= [" + nr.getInstitutionID() + "]");
                logger.info("[PaymentNo]    = [" + nr.getPaymentNo() + "]");
                logger.info("[Amount]       = [" + nr.getAmount() + "]");
                logger.info("[Status]       = [" + nr.getStatus() + "]");
                logger.info("[BankNotificationTime]       = [" + nr.getBankNotificationTime() + "]");
                if (20 == nr.getStatus()) {
                    logger.info("receive 2818 notification success");
                }

            } else if ("2838".equals(noticeRequest.getTxCode())) {
                Notice2838Request nr = new Notice2838Request(noticeRequest.getDocument());
                // ！！！ 在这里添加商户处理逻辑！！！
                // 以下为演示代码
                logger.info("[TxCode]       = [2838]");
                logger.info("[TxName]       = [二维码支付退款结果通知]");
                logger.info("[InstitutionID]= [" + nr.getInstitutionID() + "]");
                logger.info("[SerialNumber]= [" + nr.getSerialNumber() + "]");
                logger.info("[PaymentNo]    = [" + nr.getPaymentNo() + "]");
                logger.info("[Amount]       = [" + nr.getAmount() + "]");
                logger.info("[Status]       = [" + nr.getStatus() + "]");
                logger.info("[RefundTime]       = [" + nr.getRefundTime() + "]");
                if (20 == nr.getStatus()) {
                    logger.info("退款失败");
                } else if (40 == nr.getStatus()) {
                    logger.info("退款成功");
                }

            } else if ("3118".equals(noticeRequest.getTxCode())) {
                Notice3118Request nr = new Notice3118Request(noticeRequest.getDocument());
                // ！！！ 在这里添加商户处理逻辑！！！
                // 以下为演示代码
                logger.info("[TxCode]       = [3118]");
                logger.info("[TxName]       = [支付成功通知]");
                logger.info("[InstitutionID]=[" + nr.getInstitutionID() + "]");
                logger.info("[PaymentNo]=[" + nr.getPaymentNo() + "]");
                logger.info("[PaymentTime]=[" + nr.getPaymentTime() + "]");
                logger.info("[Amount]=[" + nr.getAmount() + "]");
                logger.info("[PaymentAccountName]=[" + nr.getPaymentAccountName() + "]");
                logger.info("[PaymentAccountNumber]=[" + nr.getPaymentAccountNumber() + "]");
                logger.info("[IdentificationNumber]=[" + nr.getIdentificationNumber() + "]");
                logger.info("[PhoneNumber]=[" + nr.getPhoneNumber() + "]");

            } else if ("3119".equals(noticeRequest.getTxCode())) {
                Notice3119Request nr = new Notice3119Request(noticeRequest.getDocument());
                // ！！！ 在这里添加商户处理逻辑！！！
                // 以下为演示代码
                logger.info("[TxCode]       = [3119]");
                logger.info("[TxName]       = [P2P支付成功通知（使用优惠券）]");
                logger.info("[InstitutionID]= [" + nr.getInstitutionID() + "]");
                logger.info("[PaymentNo]    = [" + nr.getPaymentNo() + "]");
                logger.info("[Amount]       = [" + nr.getAmount() + "]");
                logger.info("[InvestAmount] = [" + nr.getInvestAmount() + "]");
                logger.info("[CouponAmount] = [" + nr.getCouponAmount() + "]");
                logger.info("[PaymentAccountName] = [" + nr.getPaymentAccountName() + "]");
                logger.info("[PaymentAccountNumber] = [" + nr.getPaymentAccountNumber() + "]");
                logger.info("[CouponNo] = [" + nr.getCouponNo() + "]");
                logger.info("[IdentificationNumber] = [" + nr.getIdentificationNumber() + "]");
                logger.info("[PhoneNumber] = [" + nr.getPhoneNumber() + "]");

            } else if ("3218".equals(noticeRequest.getTxCode())) {
                Notice3218Request nr = new Notice3218Request(noticeRequest.getDocument());
                // ！！！ 在这里添加商户处理逻辑！！！
                // 以下为演示代码
                logger.info("[TxCode]       = [3218]");
                logger.info("[TxName]       = [支付成功通知]");
                logger.info("[InstitutionID]=[" + nr.getInstitutionID() + "]");
                logger.info("[PaymentNo]=[" + nr.getPaymentNo() + "]");
                logger.info("[PaymentTime]=[" + nr.getPaymentTime() + "]");
                logger.info("[Amount]=[" + nr.getAmount() + "]");
                logger.info("[LoanerPaymentAccountNumber]=[" + nr.getLoanerPaymentAccountNumber() + "]");
                logger.info("[PaymentWay]=[" + nr.getPaymentWay() + "]");

            } else if ("3219".equals(noticeRequest.getTxCode())) {
                Notice3219Request nr = new Notice3219Request(noticeRequest.getDocument());
                // ！！！ 在这里添加商户处理逻辑！！！
                // 以下为演示代码
                logger.info("[TxCode]       = [3219]");
                logger.info("[TxName]       = [P2P支付成功通知（使用优惠券）]");
                logger.info("[InstitutionID]= [" + nr.getInstitutionID() + "]");
                logger.info("[PaymentNo]    = [" + nr.getPaymentNo() + "]");
                logger.info("[Amount]       = [" + nr.getAmount() + "]");
                logger.info("[InvestAmount] = [" + nr.getInvestAmount() + "]");
                logger.info("[CouponNo] = [" + nr.getCouponNo() + "]");
                logger.info("[CouponAmount] = [" + nr.getCouponAmount() + "]");
                logger.info("[LoanerPaymentAccountNumber] = [" + nr.getLoanerPaymentAccountNumber() + "]");
                logger.info("[PaymentWay] = [" + nr.getPaymentWay() + "]");
                logger.info("[Status] = [" + nr.getStatus() + "]");

            } else if ("3618".equals(noticeRequest.getTxCode())) {
                Notice3618Request nr = new Notice3618Request(noticeRequest.getDocument());
                logger.info("[TxCode]       = [3218]");
                logger.info("[TxName]       = [P2P支付状态变更通知]");
                logger.info("[InstitutionID]= [" + nr.getInstitutionID() + "]");
                logger.info("[ProjectNo]= [" + nr.getProjectNo() + "]");
                logger.info("[PaymentNo]    = [" + nr.getPaymentNo() + "]");
                logger.info("[Amount]       = [" + nr.getAmount() + "]");
                logger.info("[LoanerPaymentAccountName]       = [" + nr.getLoanerPaymentAccountName() + "]");
                logger.info("[LoanerPaymentAccountNumber]       = [" + nr.getLoanerPaymentAccountNumber() + "]");
                logger.info("[LoanerIdentificationNumber]       = [" + nr.getLoanerIdentificationNumber() + "]");
                logger.info("[LoanerPhoneNumber]       = [" + nr.getAmount() + "]");
                logger.info("[Status] = [" + nr.getStatus() + "]");
                logger.info("[PaymentTime] = [" + nr.getPaymentTime() + "]");

            } else if ("3619".equals(noticeRequest.getTxCode())) {
                Notice3619Request nr = new Notice3619Request(noticeRequest.getDocument());
                logger.info("[TxCode]       = [3219]");
                logger.info("[TxName]       = [P2P支付状态变更通知(优惠券)]");
                logger.info("[InstitutionID]= [" + nr.getInstitutionID() + "]");
                logger.info("[ProjectNo]= [" + nr.getProjectNo() + "]");
                logger.info("[PaymentNo]    = [" + nr.getPaymentNo() + "]");
                logger.info("[InvestAmount]       = [" + nr.getInvestAmount() + "]");
                logger.info("[Amount]       = [" + nr.getAmount() + "]");
                logger.info("[CouponNo]       = [" + nr.getCouponNo() + "]");
                logger.info("[CouponAmount]       = [" + nr.getCouponAmount() + "]");
                logger.info("[LoanerPaymentAccountName]       = [" + nr.getLoanerPaymentAccountName() + "]");
                logger.info("[LoanerPaymentAccountNumber]       = [" + nr.getLoanerPaymentAccountNumber() + "]");
                logger.info("[LoanerIdentificationNumber]       = [" + nr.getLoanerIdentificationNumber() + "]");
                logger.info("[LoanerPhoneNumber]       = [" + nr.getAmount() + "]");
                logger.info("[Status] = [" + nr.getStatus() + "]");
                logger.info("[PaymentTime] = [" + nr.getPaymentTime() + "]");

            } else if ("4203".equals(noticeRequest.getTxCode())) {
                Notice4203Request nr = new Notice4203Request(noticeRequest.getDocument());
                logger.info("[TxCode]       = [4203]");
                logger.info("[TxName]       = [用户统一账户注册成功通知]");
                logger.info("[InstitutionID]=[" + nr.getInstitutionID() + "]");
                logger.info("[RegisterNo]=[" + nr.getRegisterNo() + "]");
                logger.info("[PhoneNumber]=[" + nr.getPhoneNumber() + "]");
                logger.info("[UserName]=[" + nr.getUserName() + "]");
                logger.info("[IdentificationNumber]=[" + nr.getIdentificationNumber() + "]");
                logger.info("[PaymentAccountNumber]=[" + nr.getPaymentAccountNumber() + "]");
                logger.info("[UserType]=[" + nr.getUserType() + "]");
                logger.info("[Status]=[" + nr.getStatus() + "]");

            } else if ("4018".equals(noticeRequest.getTxCode())) {
                Notice4018Request nr = new Notice4018Request(noticeRequest.getDocument());
                logger.info("[TxCode]       = [4018]");
                logger.info("[TxName]       = [充值成功通知]");
                logger.info("[InstitutionID]=[" + nr.getInstitutionID() + "]");
                logger.info("[PaymentNo]=[" + nr.getPaymentNo() + "]");
                logger.info("[PaymentTime]=[" + nr.getPaymentTime() + "]");
                logger.info("[Amount]=[" + nr.getAmount() + "]");
                logger.info("[PaymentAccountNumber]=[" + nr.getPaymentAccountNumber() + "]");
            } else if ("4233".equals(noticeRequest.getTxCode())) {
                Notice4233Request nr = new Notice4233Request(noticeRequest.getDocument());
                // ！！！ 在这里添加商户处理逻辑！！！
                // 以下为演示代码
                logger.info("[TxCode]       = [4228]");
                logger.info("[TxName]       = [扣款签约成功通知]");
                logger.info("[InstitutionID]=[" + nr.getInstitutionID() + "]");
                logger.info("[PhoneNumber]=[" + nr.getPhoneNumber() + "]");
                logger.info("[UserName]=[" + nr.getUserName() + "]");
                logger.info("[IdentificationNumber]=[" + nr.getIdentificationNumber() + "]");
                logger.info("[PaymentAccountNumber]=[" + nr.getPaymentAccountNumber() + "]");
                logger.info("[Status]=[" + nr.getStatus() + "]");

            } else if ("4243".equals(noticeRequest.getTxCode())) {
                Notice4243Request nr = new Notice4243Request(noticeRequest.getDocument());
                // ！！！ 在这里添加商户处理逻辑！！！
                // 以下为演示代码
                logger.info("[TxCode]       = [4243]");
                logger.info("[TxName]       = [银行卡绑定成功通知]");
                logger.info("[InstitutionID]=[" + nr.getInstitutionID() + "]");
                logger.info("[BindingSystemNo]=[" + nr.getBindingSystemNo() + "]");
                logger.info("[BankAccountNumber]=[" + nr.getBankAccountNumber() + "]");
                logger.info("[BankID]=[" + nr.getBankID() + "]");
                logger.info("[PaymentAccountNumber]=[" + nr.getPaymentAccountNumber() + "]");
                logger.info("[Status]=[" + nr.getStatus() + "]");

            } else if ("4247".equals(noticeRequest.getTxCode())) {
                Notice4247Request nr = new Notice4247Request(noticeRequest.getDocument());
                // ！！！ 在这里添加商户处理逻辑！！！
                // 以下为演示代码
                logger.info("[TxCode]       = [4247]");
                logger.info("[TxName]       = [银行卡解绑成功通知]");
                logger.info("[InstitutionID]=[" + nr.getInstitutionID() + "]");
                logger.info("[BankAccountNumber]=[" + nr.getBankAccountNumber() + "]");
                logger.info("[BankID]=[" + nr.getBankID() + "]");
                logger.info("[PaymentAccountNumber]=[" + nr.getPaymentAccountNumber() + "]");
                logger.info("[BindingSystemNo]=[" + nr.getBindingSystemNo() + "]");
            } else if ("4253".equals(noticeRequest.getTxCode())) {
                Notice4253Request nr = new Notice4253Request(noticeRequest.getDocument());
                // ！！！ 在这里添加商户处理逻辑！！！
                // 以下为演示代码
                logger.info("[TxCode]       = [4253]");
                logger.info("[TxName]       = [充值成功通知]");
                logger.info("[InstitutionID]=[" + nr.getInstitutionID() + "]");
                logger.info("[PaymentNo]=[" + nr.getPaymentNo() + "]");
                logger.info("[PaymentTime]=[" + nr.getPaymentTime() + "]");
                logger.info("[Amount]=[" + nr.getAmount() + "]");
                logger.info("[PaymentAccountNumber]=[" + nr.getPaymentAccountNumber() + "]");
            } else if ("4257".equals(noticeRequest.getTxCode())) {
                Notice4257Request nr = new Notice4257Request(noticeRequest.getDocument());
                // ！！！ 在这里添加商户处理逻辑！！！
                // 以下为演示代码
                logger.info("[TxCode]       = [4257]");
                logger.info("[TxName]       = [提现成功通知]");
                logger.info("[InstitutionID]=[" + nr.getInstitutionID() + "]");
                logger.info("[AcceptTime]=[" + nr.getAcceptTime() + "]");
                logger.info("[PaymentAccountNumber]=[" + nr.getPaymentAccountNumber() + "]");
                logger.info("[BankID]=[" + nr.getBankID() + "]");
                logger.info("[BankAccountNumber]=[" + nr.getBankAccountNumber() + "]");
                logger.info("[Amount]=[" + nr.getAmount() + "]");
                logger.info("[TxSN]=[" + nr.getTxSN() + "]");
                logger.info("[SwitchFlag]=[" + nr.getSwitchFlag() + "]");
            } else if ("4263".equals(noticeRequest.getTxCode())) {
                Notice4263Request nr = new Notice4263Request(noticeRequest.getDocument());
                // ！！！ 在这里添加商户处理逻辑！！！
                // 以下为演示代码
                logger.info("[TxCode]       = [4263]");
                logger.info("[TxName]       = [扣款签约成功通知]");
                logger.info("[InstitutionID]=[" + nr.getInstitutionID() + "]");
                logger.info("[PhoneNumber]=[" + nr.getPhoneNumber() + "]");
                logger.info("[AgreementNo]=[" + nr.getAgreementNo() + "]");
                logger.info("[IdentificationNumber]=[" + nr.getIdentificationNumber() + "]");
            } else if ("4278".equals(noticeRequest.getTxCode())) {
                Notice4278Request nr = new Notice4278Request(noticeRequest.getDocument());
                // ！！！ 在这里添加商户处理逻辑！！！
                // 以下为演示代码
                logger.info("[TxCode]       = [4278]");
                logger.info("[TxName]       = [专属账户签约成功通知]");
                logger.info("[InstitutionID]=[" + nr.getInstitutionID() + "]");
                logger.info("[AgreementNo]=[" + nr.getAgreementNo() + "]");
                logger.info("[AgreementType]=[" + nr.getAgreementType() + "]");
                logger.info("[PaymentAccountName]=[" + nr.getPaymentAccountName() + "]");
                logger.info("[PaymentAccountNumber]=[" + nr.getPaymentAccountNumber() + "]");
                logger.info("[PhoneNumber]=[" + nr.getPhoneNumber() + "]");
                logger.info("[IdentificationNumber]=[" + nr.getIdentificationNumber() + "]");
            } else if ("4322".equals(noticeRequest.getTxCode())) {
                Notice4322Request nr = new Notice4322Request(noticeRequest.getDocument());
                // ！！！ 在这里添加商户处理逻辑！！！
                // 以下为演示代码
                logger.info("[TxCode]       = [4322]");
                logger.info("[TxName]       = [自动投资签约成功通知]");
                logger.info("[InstitutionID]=[" + nr.getInstitutionID() + "]");
                logger.info("[AgreementNo]=[" + nr.getAgreementNo() + "]");
                logger.info("[PaymentAccountName]=[" + nr.getPaymentAccountName() + "]");
                logger.info("[PaymentAccountNumber]=[" + nr.getPaymentAccountNumber() + "]");
            } else if ("4338".equals(noticeRequest.getTxCode())) {
                Notice4338Request nr = new Notice4338Request(noticeRequest.getDocument());

                logger.info("[TxCode]       = [4338]");
                logger.info("[TxName]       = [统一户签约成功通知]");
                logger.info("[InstitutionID]=[" + nr.getInstitutionID() + "]");
                logger.info("[AgreementNo]=[" + nr.getAgreementNo() + "]");
                logger.info("[AgreementType]=[" + nr.getAgreementType() + "]");
                logger.info("[Status]=[" + nr.getStatus() + "]");

            } else if ("4522".equals(noticeRequest.getTxCode())) {
                Notice4522Request nr = new Notice4522Request(noticeRequest.getDocument());
                // ！！！ 在这里添加商户处理逻辑！！！
                // 以下为演示代码
                logger.info("[TxCode]       = [4522]");
                logger.info("[TxName]       = [机构支付账户网银充值成功通知]");
                logger.info("[InstitutionID]=[" + nr.getInstitutionID() + "]");
                logger.info("[PaymentAccountName]=[" + nr.getPaymentAccountName() + "]");
                logger.info("[PaymentAccountNumber]=[" + nr.getPaymentAccountNumber() + "]");
                logger.info("[Amount]=[" + nr.getAmount() + "]");
                logger.info("[BankTxTime]=[" + nr.getBankTxTime() + "]");
                logger.info("[PaymentNo]=[" + nr.getPaymentNo() + "]");
                logger.info("[Remark]=[" + nr.getRemark() + "]");
                logger.info("[Status]=[" + nr.getStatus() + "]");
            } else if ("5128".equals(noticeRequest.getTxCode())) {
                Notice5128Request nr = new Notice5128Request(noticeRequest.getDocument());
                // ！！！ 在这里添加商户处理逻辑！！！
                // 以下为演示代码
                logger.info("[TxCode]       = [5128]");
                logger.info("[TxName]       = [跨境汇款支付状态变更通知]");
                logger.info("[InstitutionID]= [" + nr.getInstitutionID() + "]");
                logger.info("[SerialNumber]=  [" + nr.getSerialNumber() + "]");
                logger.info("[PayeeCurrency]= [" + nr.getPayeeCurrency() + "]");
                logger.info("[PayeeAmount]=   [" + nr.getPayeeAmount() + "]");
                logger.info("[FXBuyingRate]=  [" + nr.getFXBuyingRate() + "]");
                logger.info("[Currency]=      [" + nr.getCurrency() + "]");
                logger.info("[Amount]=        [" + nr.getAmount() + "]");
                logger.info("[Fee]=           [" + nr.getFee() + "]");
                logger.info("[Status]=        [" + nr.getStatus() + "]");
                logger.info("[BankNotificationTime]= [" + nr.getBankNotificationTime() + "]");
            } else if ("6123".equals(noticeRequest.getTxCode())) {
                Notice6123Request nr = new Notice6123Request(noticeRequest.getDocument());
                // ！！！ 在这里添加商户处理逻辑！！！
                // 以下为演示代码
                logger.info("[TxCode]       = [6123]");
                logger.info("[TxName]       = [份额确认状态变更通知]");
                logger.info("[InstitutionID]=[" + nr.getInstitutionID() + "]");
                logger.info("[TxSN]=[" + nr.getTxSN() + "]");
                logger.info("[Amount]=[" + nr.getAmount() + "]");
                logger.info("[PaymentAccountNumber]=[" + nr.getPaymentAccountNumber() + "]");
                logger.info("[Status]=[" + nr.getStatus() + "]");
                logger.info("[BindingSN]=[" + nr.getBindingSN() + "]");
                logger.info("[ApplyTime]=[" + nr.getApplyTime() + "]");
                if (20 == nr.getStatus()) {
                    logger.info("receive 6123 notification success");
                }

            } else if ("7128".equals(noticeRequest.getTxCode())) {
                Notice7128Request nr = new Notice7128Request(noticeRequest.getDocument());
                // ！！！ 在这里添加商户处理逻辑！！！
                // 以下为演示代码
                logger.info("[TxCode]       = [7128]");
                logger.info("[TxName]       = [投资人支付成功通知]");
                logger.info("[InstitutionID]=[" + nr.getInstitutionID() + "]");
                logger.info("[PaymentNo]=[" + nr.getPaymentNo() + "]");
                logger.info("[Amount]=[" + nr.getAmount() + "]");
                logger.info("[OrderNo]=[" + nr.getOrderNo() + "]");
                logger.info("[Status]=[" + nr.getStatus() + "]");
                logger.info("[BankTxTime]=[" + nr.getBankTxTime() + "]");
                if (20 == nr.getStatus()) {
                    logger.info("receive 7128 notification success");
                }

            } else if ("7158".equals(noticeRequest.getTxCode())) {
                Notice7128Request nr = new Notice7128Request(noticeRequest.getDocument());
                // ！！！ 在这里添加商户处理逻辑！！！
                // 以下为演示代码
                logger.info("[TxCode]       = [7158]");
                logger.info("[TxName]       = [融资人支付成功通知]");
                logger.info("[InstitutionID]=[" + nr.getInstitutionID() + "]");
                logger.info("[OrderNo]=[" + nr.getOrderNo() + "]");
                logger.info("[PaymentNo]=[" + nr.getPaymentNo() + "]");
                logger.info("[Amount]=[" + nr.getAmount() + "]");
                logger.info("[Status]=[" + nr.getStatus() + "]");
                logger.info("[BankTxTime]=[" + nr.getBankTxTime() + "]");
                if (20 == nr.getStatus()) {
                    logger.info("receive 7158 notification success");
                }

            } else if ("7218".equals(noticeRequest.getTxCode())) {
                Notice7218Request nr = new Notice7218Request(noticeRequest.getDocument());
                // ！！！ 在这里添加商户处理逻辑！！！
                // 以下为演示代码
                logger.info("[TxCode]       = [7218]");
                logger.info("[TxName]       = [投资人支付状态变更通知]");
                logger.info("[InstitutionID]= [" + nr.getInstitutionID() + "]");
                logger.info("[PaymentNo]    = [" + nr.getPaymentNo() + "]");
                logger.info("[Amount]       = [" + nr.getAmount() + "]");
                logger.info("[Status]       = [" + nr.getStatus() + "]");
                logger.info("[BankNotificationTime]       = [" + nr.getBankNotificationTime() + "]");
                if (20 == nr.getStatus()) {
                    logger.info("receive 7218 notification success");
                }
            } else if ("7238".equals(noticeRequest.getTxCode())) {
                Notice7238Request nr = new Notice7238Request(noticeRequest.getDocument());
                // ！！！ 在这里添加商户处理逻辑！！！
                // 以下为演示代码
                logger.info("[TxCode]       = [7238]");
                logger.info("[TxName]       = [白名单状态通知]");
                logger.info("[InstitutionID]= [" + nr.getInstitutionID() + "]");
                logger.info("[OrderNo]    = [" + nr.getOrderNo() + "]");
                logger.info("[WhiteListType]       = [" + nr.getWhiteListType() + "]");
                logger.info("[WhiteListNo]       = [" + nr.getWhiteListNo() + "]");
                logger.info("[Status]       = [" + nr.getStatus() + "]");
            } else if ("7245".equals(noticeRequest.getTxCode())) {
                Notice7245Request nr = new Notice7245Request(noticeRequest.getDocument());
                // ！！！ 在这里添加商户处理逻辑！！！
                // 以下为演示代码
                logger.info("[TxCode]       = [7245]");
                logger.info("[TxName]       = [退汇通知]");
                logger.info("[InstitutionID]= [" + nr.getInstitutionID() + "]");
                logger.info("[SourceTxCode]    = [" + nr.getSourceTxCode() + "]");
                logger.info("[SourceTxSN]       = [" + nr.getSourceTxSN() + "]");
                logger.info("[Remark]       = [" + nr.getRemark() + "]");
            } else if ("7248".equals(noticeRequest.getTxCode())) {
                Notice7248Request nr = new Notice7248Request(noticeRequest.getDocument());
                // ！！！ 在这里添加商户处理逻辑！！！
                // 以下为演示代码
                logger.info("[TxCode]       = [7248]");
                logger.info("[TxName]       = [还款订单生成通知]");
                logger.info("[InstitutionID]= [" + nr.getInstitutionID() + "]");
                logger.info("[OrderNo]    = [" + nr.getOrderNo() + "]");
                logger.info("[OrderType]       = [" + nr.getOrderType() + "]");
                logger.info("[Amount]       = [" + nr.getAmount() + "]");
                logger.info("[TxTime]       = [" + nr.getTxTime() + "]");
            } else if ("7258".equals(noticeRequest.getTxCode())) {
                Notice7258Request nr = new Notice7258Request(noticeRequest.getDocument());
                // ！！！ 在这里添加商户处理逻辑！！！
                // 以下为演示代码
                logger.info("[TxCode]       = [7258]");
                logger.info("[TxName]       = [融资人支付成功通知]");
                logger.info("[InstitutionID]= [" + nr.getInstitutionID() + "]");
                logger.info("[PaymentNo]    = [" + nr.getPaymentNo() + "]");
                logger.info("[Amount]       = [" + nr.getAmount() + "]");
                logger.info("[Status]       = [" + nr.getStatus() + "]");
                logger.info("[BankTxTime]       = [" + nr.getBankNotificationTime() + "]");
                if (20 == nr.getStatus()) {
                    logger.info("receive 7258 notification success");
                }
            } else if ("7263".equals(noticeRequest.getTxCode())) {
                Notice7263Request nr = new Notice7263Request(noticeRequest.getDocument());
                // ！！！ 在这里添加商户处理逻辑！！！
                // 以下为演示代码
                logger.info("[TxCode]       = [7263]");
                logger.info("[TxName]       = [投资/还款订单单笔代收结果通知]");
                logger.info("[InstitutionID]= [" + nr.getInstitutionID() + "]");
                logger.info("[TxSN]    = [" + nr.getTxSN() + "]");
                logger.info("[Amount]       = [" + nr.getAmount() + "]");
                logger.info("[Status]       = [" + nr.getStatus() + "]");
                logger.info("[BankTxTime]       = [" + nr.getBankTxTime() + "]");
                logger.info("[ResponseCode]       = [" + nr.getResponseCode() + "]");
                logger.info("[ResponseMessage]       = [" + nr.getResponseMessage() + "]");
                if (20 == nr.getStatus()) {
                    logger.info("receive 7258 notification success");
                }
            } else if ("2568".equals(noticeRequest.getTxCode())) {
                Notice2568Request nr = new Notice2568Request(noticeRequest.getDocument());
                // ！！！ 在这里添加商户处理逻辑！！！
                // 以下为演示代码
                logger.info("[TxCode]       = [2568]");
                logger.info("[TxName]       = [中金界面绑卡成功通知]");
                logger.info("[InstitutionID]=[" + nr.getInstitutionID() + "]");
                logger.info("[TxSN]=[" + nr.getTxSN() + "]");
                logger.info("[AccountName]=[" + nr.getAccountName() + "]");
                logger.info("[AccountNumber]=[" + nr.getAccountNumber() + "]");
                logger.info("[IssueBankID]=[" + nr.getIssueBankID() + "]");
                logger.info("[IssueCardType]=[" + nr.getIssueCardType() + "]");
                logger.info("[IdentificationNumber]=[" + nr.getIdentificationNumber() + "]");
                logger.info("[PhoneNumber]=[" + nr.getPhoneNumber() + "]");

            } else if ("5613".equals(noticeRequest.getTxCode())) {
                Notice5613Request nr = new Notice5613Request(noticeRequest.getDocument());
                // ！！！ 在这里添加商户处理逻辑！！！
                // 以下为演示代码
                logger.info("[TxCode]       = [5613]");
                logger.info("[TxName]       = [跨境出口到账通知]");
                logger.info("[InstitutionID]=[" + nr.getInstitutionID() + "]");
                logger.info("[Status]=[" + nr.getStatus() + "]");
                logger.info("[BankID]=[" + nr.getBankID() + "]");
                logger.info("[BankTxNo]=[" + nr.getBankTxNo() + "]");
                logger.info("[FailureReason]=[" + nr.getFailureReason() + "]");
                logger.info("[RemitCurrency]=[" + nr.getRemitCurrency() + "]");
                logger.info("[RemitAmountUpper]=[" + nr.getRemitAmountUpper() + "]");
                logger.info("[RemitAmountLow]=[" + nr.getRemitAmountLow() + "]");
                logger.info("[PayerName]=[" + nr.getPayerName() + "]");
                logger.info("[RemittanceInformation]=[" + nr.getRemittanceInformation() + "]");

            } else if ("5614".equals(noticeRequest.getTxCode())) {
                Notice5614Request nr = new Notice5614Request(noticeRequest.getDocument());
                // ！！！ 在这里添加商户处理逻辑！！！
                // 以下为演示代码
                logger.info("[TxCode]       = [5614]");
                logger.info("[TxName]       = [跨境出口收款指令确认通知]");
                logger.info("[InstitutionID]=[" + nr.getInstitutionID() + "]");
                logger.info("[SerialNumber]=[" + nr.getSerialNumber() + "]");
                logger.info("[RemitCurrency]=[" + nr.getRemitCurrency() + "]");
                logger.info("[RemitAmount]=[" + nr.getRemitAmount() + "]");

            } else if ("5615".equals(noticeRequest.getTxCode())) {
                Notice5615Request nr = new Notice5615Request(noticeRequest.getDocument());
                // ！！！ 在这里添加商户处理逻辑！！！
                // 以下为演示代码
                logger.info("[TxCode]       = [5615]");
                logger.info("[TxName]       = [跨境出口到账通知]");
                logger.info("[InstitutionID]=[" + nr.getInstitutionID() + "]");
                logger.info("[SerialNumber]=[" + nr.getSerialNumber() + "]");
                logger.info("[FailureReason]=[" + nr.getFailureReason() + "]");

            } else if ("5518".equals(noticeRequest.getTxCode())) {
                Notice5518Request nr = new Notice5518Request(noticeRequest.getDocument());
                // ！！！ 在这里添加商户处理逻辑！！！
                // 以下为演示代码
                logger.info("[TxCode]       = [5518]");
                logger.info("[TxName]       = [跨境出口到账通知]");
                logger.info("[InstitutionID]=[" + nr.getInstitutionID() + "]");
                logger.info("[PayeeCode]=[" + nr.getPayeeCode() + "]");
                logger.info("[Status]=[" + nr.getStatus() + "]");
                logger.info("[RiskRating]=[" + nr.getRiskRating() + "]");
            } else if ("2702".equals(noticeRequest.getTxCode())) {
                Notice2702Request nr = new Notice2702Request(noticeRequest.getDocument());
                // ！！！ 在这里添加商户处理逻辑！！！
                // 以下为演示代码
                logger.info("[TxCode]       = [2702]");
                logger.info("[TxName]       = [代收授权结果通知]");
                logger.info("[InstitutionID]=[" + nr.getInstitutionID() + "]");
                logger.info("[TxSN]=[" + nr.getTxSN() + "]");
                logger.info("[Status]=[" + nr.getStatus() + "]");
                logger.info("[FailuerReason]=[" + nr.getFailuerReason() + "]");
                logger.info("[AccountName]=[" + nr.getAccountName() + "]");
                logger.info("[AccountNumber]=[" + nr.getAccountNumber() + "]");
                logger.info("[BankID]=[" + nr.getBankID() + "]");
                logger.info("[IdentificationType]=[" + nr.getIdentificationType() + "]");
                logger.info("[IdentificationNumber]=[" + nr.getIdentificationNumber() + "]");
                logger.info("[ContractNo]=[" + nr.getContractNo() + "]");
            } else {
                logger.info("！！！ 错误的通知 ！！！");
                logger.info("[txCode]       = [????]");
                logger.info("[txName]       = [未知通知类型]");
            }

            logger.info("[plainText]=[" + noticeRequest.getPlainText() + "]");

            // 4 响应支付平台 特别说明：为避免重复发通知，必须要求商户给予响应，响应的内容是固定的new
            // String(Base64.encode(new
            // NoticeResponse().getMessage().getBytes("UTF-8")));
            PrintWriter out = response.getWriter();
            String xmlString = "";
            if ("1119".equals(noticeRequest.getTxCode())) {
                Notice1119Response notice1119Response = new Notice1119Response();
                Notice1119Request nr = new Notice1119Request(noticeRequest.getDocument());
                notice1119Response.setPaymentNo(nr.getPaymentNo());
                xmlString = notice1119Response.process();
            } else {
                xmlString = new NoticeResponse().getMessage();
            }

            String base64String = new String(Base64.encode(xmlString.getBytes("UTF-8")));

            out.print(base64String);
            out.flush();
            out.close();

            logger.info("---------- End [ReceiveNotice] process.");
        } catch (Exception e) {
            logger.error("", e);
            // logger.warn("", e);
            e.printStackTrace();


        }
    }

    @Resource
    private SoftwareService softwareService;
    @Resource
    private CloudCenterPaymentService paymentService;
    @Resource
    private SoftwareBuyDao SoftwareBuyDao;
    @Resource
    OrderPayDao OrderPayDao;
    @Resource
    private SLDOrderDao SLDOrderDao;
    @Resource
    SLDOrderService SLDOrderService;
    @Resource
    ALDPayService ALDPayService;

}
