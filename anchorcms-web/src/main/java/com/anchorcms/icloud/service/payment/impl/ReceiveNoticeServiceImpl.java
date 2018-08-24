package com.anchorcms.icloud.service.payment.impl;

import com.anchorcms.icloud.service.payment.ReceiveNoticeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import payment.api.notice.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ly on 2017/2/17.
 */
@Service
@Transactional
public class ReceiveNoticeServiceImpl implements ReceiveNoticeService {

    private static final Logger logger = LoggerFactory.getLogger(ReceiveNoticeServiceImpl.class);
    public Map receiveNotice(String message, String signature) {
        // 定义变量
        String txName = "";
        Map receiveMessage= new HashMap();
        try {
            logger.info("---------- Begin [ReceiveNoticePage] process......");

            // 获得参数message和signature
            /*String message = request.getParameter("message");
            String signature = request.getParameter("signature");*/

            logger.info("[message]=[" + message + "]");
            logger.info("[signature]=[" + signature + "]");

            // 生成交易结果对象
            NoticeRequest noticeRequest = new NoticeRequest(message, signature);
            System.out.println("收到！");

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
                txName = "商户订单支付状态变更通知";
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
                txName = "市场订单支付状态变更通知";
                logger.info("[TxCode]       = [1318]");
                logger.info("[TxName]       = [市场订单支付状态变更通知]");
                logger.info("[InstitutionID]= [" + nr.getInstitutionID() + "]");
                logger.info("[PaymentNo]    = [" + nr.getPaymentNo() + "]");
                logger.info("[Amount]       = [" + nr.getAmount() + "]");
                logger.info("[Status]       = [" + nr.getStatus() + "]");
                logger.info("[BankNotificationTime]       = [" + nr.getBankNotificationTime() + "]");
                if (20 == nr.getStatus()) {
                    logger.info("receive 1318 notification success");
                }
                receiveMessage.put("txName","市场订单支付状态变更通知");
                receiveMessage.put("TxCode","1318");
                receiveMessage.put("InstitutionID",nr.getInstitutionID());
                receiveMessage.put("PaymentNo",nr.getPaymentNo());
                receiveMessage.put("Amount",nr.getAmount());
                receiveMessage.put("Status",nr.getStatus());
                receiveMessage.put("BankNotificationTime",nr.getBankNotificationTime());
                //把订单号返回前台
               //return nr.getPaymentNo();
                return receiveMessage;
                //把订单号返回
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

            } else if ("1394".equals(noticeRequest.getTxCode())) {
                Notice1394Request nr = new Notice1394Request(noticeRequest.getDocument());
                // ！！！ 在这里添加商户处理逻辑！！！
                // 以下为演示代码
                txName = "市场订单认证支付结果通知";
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
            } else if ("2118".equals(noticeRequest.getTxCode())) {
                Notice2118Request nr = new Notice2118Request(noticeRequest.getDocument());
                // ！！！ 在这里添加商户处理逻辑！！！
                // 以下为演示代码
                txName = "分步支付结果通知";
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
                txName = "开通认证支付结果通知";
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
            }else if ("2818".equals(noticeRequest.getTxCode())) {
                Notice2818Request nr = new Notice2818Request(noticeRequest.getDocument());
                // ！！！ 在这里添加商户处理逻辑！！！
                // 以下为演示代码
                txName = "商户订单支付状态变更通知";
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

            }  else if ("3118".equals(noticeRequest.getTxCode())) {
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
                // ！！！ 在这里添加商户处理逻辑！！！
                // 以下为演示代码
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
                // ！！！ 在这里添加商户处理逻辑！！！
                // 以下为演示代码
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
                // ！！！ 在这里添加商户处理逻辑！！！
                // 以下为演示代码
                logger.info("[TxCode]       = [4018]");
                logger.info("[TxName]       = [充值成功通知]");
                logger.info("[InstitutionID]=[" + nr.getInstitutionID() + "]");
                logger.info("[PaymentNo]=[" + nr.getPaymentNo() + "]");
                logger.info("[PaymentTime]=[" + nr.getPaymentTime() + "]");
                logger.info("[Amount]=[" + nr.getAmount() + "]");
                logger.info("[PaymentAccountNumber]=[" + nr.getPaymentAccountNumber() + "]");
            } else if ("4218".equals(noticeRequest.getTxCode())) {
                Notice4218Request nr = new Notice4218Request(noticeRequest.getDocument());
                // ！！！ 在这里添加商户处理逻辑！！！
                // 以下为演示代码
                logger.info("[TxCode]       = [4218]");
                logger.info("[TxName]       = [余额查询签约成功通知]");
                logger.info("[InstitutionID]=[" + nr.getInstitutionID() + "]");
                logger.info("[AgreementNo]=[" + nr.getAgreementNo() + "]");
                logger.info("[PaymentAccountName]=[" + nr.getPaymentAccountName() + "]");
                logger.info("[PaymentAccountNumber]=[" + nr.getPaymentAccountNumber() + "]");
                logger.info("[IdentificationNumber]=[" + nr.getIdentificationNumber() + "]");
                logger.info("[PhoneNumber]=[" + nr.getPhoneNumber() + "]");

            } else if ("4228".equals(noticeRequest.getTxCode())) {
                Notice4228Request nr = new Notice4228Request(noticeRequest.getDocument());
                // ！！！ 在这里添加商户处理逻辑！！！
                // 以下为演示代码
                logger.info("[TxCode]       = [4228]");
                logger.info("[TxName]       = [扣款签约成功通知]");
                logger.info("[InstitutionID]=[" + nr.getInstitutionID() + "]");
                logger.info("[AgreementNo]=[" + nr.getAgreementNo() + "]");
                logger.info("[PaymentAccountName]=[" + nr.getPaymentAccountName() + "]");
                logger.info("[PaymentAccountNumber]=[" + nr.getPaymentAccountNumber() + "]");
                logger.info("[IdentificationNumber]=[" + nr.getIdentificationNumber() + "]");
                logger.info("[PhoneNumber]=[" + nr.getPhoneNumber() + "]");

            } else if ("4233".equals(noticeRequest.getTxCode())) {
                Notice4233Request nr = new Notice4233Request(noticeRequest.getDocument());
                // ！！！ 在这里添加商户处理逻辑！！！
                // 以下为演示代码
                logger.info("[TxCode]       = [4233]");
                logger.info("[TxName]       = [用户支付账户注册成功通知]");
                logger.info("[InstitutionID]=[" + nr.getInstitutionID() + "]");
                logger.info("[PhoneNumber]=[" + nr.getPhoneNumber() + "]");
                logger.info("[UserName]=[" + nr.getUserName() + "]");
                logger.info("[IdentificationNumber]=[" + nr.getIdentificationNumber() + "]");
                logger.info("[PaymentAccountNumber]=[" + nr.getPaymentAccountNumber() + "]");
                logger.info("[OrganizationCode]=[" + nr.getOrganizationCode() + "]");
                logger.info("[Status]=[" + nr.getStatus() + "]");
            } else if ("4243".equals(noticeRequest.getTxCode())) {
                Notice4243Request nr = new Notice4243Request(noticeRequest.getDocument());
                // ！！！ 在这里添加商户处理逻辑！！！
                // 以下为演示代码
                logger.info("[TxCode]       = [4243]");
                logger.info("[TxName]       = [银行卡绑定成功通知]");
                logger.info("[InstitutionID]=[" + nr.getInstitutionID() + "]");
                logger.info("[BankAccountNumber]=[" + nr.getBankAccountNumber() + "]");
                logger.info("[BindingSystemNo]=[" + nr.getBindingSystemNo() + "]");
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
                logger.info("[getPaymentAccountNumber]=[" + nr.getPaymentAccountNumber() + "]");
            } else if ("4338".equals(noticeRequest.getTxCode())) {
                Notice4338Request nr = new Notice4338Request(noticeRequest.getDocument());
                // ！！！ 在这里添加商户处理逻辑！！！
                // 以下为演示代码
                // 以下为演示代码
                logger.info("[TxCode]       = [4338]");
                logger.info("[TxName]       = [统一账户签约成功通知]");
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
            } else if ("7128".equals(noticeRequest.getTxCode())) {
                Notice7128Request nr = new Notice7128Request(noticeRequest.getDocument());
                // ！！！ 在这里添加商户处理逻辑！！！
                // 以下为演示代码
                txName = "投资人支付成功通知";
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
                txName = "投资人支付成功通知";
                logger.info("[TxCode]       = [7158]");
                logger.info("[TxName]       = [融资人支付成功通知]");
                logger.info("[InstitutionID]=[" + nr.getInstitutionID() + "]");
                logger.info("[PaymentNo]=[" + nr.getPaymentNo() + "]");
                logger.info("[Amount]=[" + nr.getAmount() + "]");
                logger.info("[OrderNo]=[" + nr.getOrderNo() + "]");
                logger.info("[Status]=[" + nr.getStatus() + "]");
                logger.info("[BankTxTime]=[" + nr.getBankTxTime() + "]");
                if (20 == nr.getStatus()) {
                    logger.info("receive 7158 notification success");
                }
            } else if ("7218".equals(noticeRequest.getTxCode())) {
                Notice7218Request nr = new Notice7218Request(noticeRequest.getDocument());
                // ！！！ 在这里添加商户处理逻辑！！！
                // 以下为演示代码
                txName = "投资人支付状态变更通知";
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
                logger.info("[InstitutionFee]=[" + nr.getInstitutionFee() + "]");
                logger.info("[TxSN]=[" + nr.getTxSN() + "]");
                logger.info("[SwitchFlag]=[" + nr.getSwitchFlag() + "]");
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
            }else if("2702".equals(noticeRequest.getTxCode())){
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
                txName = "未知通知类型";
                logger.info("！！！ 错误的通知 ！！！");
                logger.info("[txCode]       = [????]");
                logger.info("[txName]       = [未知通知类型]");
            }

            logger.info("[plainText]=[" + noticeRequest.getPlainText() + "]");

            /*request.setAttribute("txCode", noticeRequest.getTxCode());
            request.setAttribute("txName", txName);
            request.setAttribute("plainText", noticeRequest.getPlainText());

            // 转向Response.jsp页面
            request.getRequestDispatcher("/Response.jsp").forward(request, response);*/

            logger.info("---------- End [ReceiveNoticePage] process.");

        } catch (Exception e) {
            logger.error("", e);
            e.printStackTrace();
        }
        return receiveMessage;
    }
}
