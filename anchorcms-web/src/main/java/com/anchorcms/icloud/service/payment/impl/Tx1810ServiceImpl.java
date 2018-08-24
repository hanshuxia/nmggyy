package com.anchorcms.icloud.service.payment.impl;

import com.anchorcms.icloud.service.payment.Tx1810Service;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import payment.api.system.CMBEnvironment;
import payment.api.system.CrossBorderEnvironment;
import payment.api.system.PaymentUserEnvironment;
import payment.api.system.TxMessenger;
import payment.api.tx.statement.Tx1810Request;
import payment.api.tx.statement.Tx1810Response;
import payment.api.vo.Tx;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

/**
 * Created by ly on 2017/2/15.
 */
@Service
@Transactional
public class Tx1810ServiceImpl implements Tx1810Service {
    private static final Logger logger = Logger.getLogger("system");

    public String checkCode(String institutionID, Date checkDate) {

        try {
            // 创建交易请求对象
            Tx1810Request txRequest = new Tx1810Request();
            txRequest.setInstitutionID(institutionID);
            txRequest.setDate(checkDate);
            // 3.执行报文处理
            txRequest.process();
            // 获得参数message和signature
            /*String message = request.getParameter("message");
            String signature = request.getParameter("signature");
            String txCode = request.getParameter("txCode");
            String txName = request.getParameter("txName");
            String flag = request.getParameter("Flag");*/
            String message = txRequest.getRequestMessage();
            String signature = txRequest.getRequestSignature();
            String txCode = "1810";
            String txName = "下载交易对账单";
            String flag = "";
            // 与支付平台进行通讯
            TxMessenger txMessenger = new TxMessenger();
            String[] respMsg = null;
            // Flag=10:cmb, 20:paymentAccount
            if ("10".equals(flag)) {
                //HTTP默认连接超时时间是25s，读超时时间是25s
                //HTTPS默认连接超时时间是50s，读超时时间是50s
                respMsg = txMessenger.send(message, signature, CMBEnvironment.cmbtxURL);// 0:message;
                //设置连接超时时间和读超时时间
                //int connectTimeoutLimit = 50000;  设置连接超时时间（单位ms）
                //int readTimeoutLimit = 50000;  设置读超时时间（单位ms）
                //respMsg = txMessenger.send(message, signature, connectTimeoutLimit, readTimeoutLimit, CMBEnvironment.cmbtxURL);// 0:message;
            } else if ("20".equals(flag)) {
                //HTTP默认连接超时时间是25s，读超时时间是25s
                //HTTPS默认连接超时时间是50s，读超时时间是50s
                respMsg = txMessenger.send(message, signature, PaymentUserEnvironment.paymentusertxURL);
                //设置连接超时时间和读超时时间
                //int connectTimeoutLimit = 50000;  设置连接超时时间（单位ms）
                //int readTimeoutLimit = 50000;  设置读超时时间（单位ms）
                //respMsg = txMessenger.send(message, signature, connectTimeoutLimit, readTimeoutLimit, PaymentUserEnvironment.paymentusertxURL);// 0:message;
            } else if ("30".equals(flag)) {
                //HTTP默认连接超时时间是25s，读超时时间是25s
                //HTTPS默认连接超时时间是50s，读超时时间是50s
                respMsg = txMessenger.send(message, signature, CrossBorderEnvironment.crossBorderTxURL);
                //设置连接超时时间和读超时时间
                //int connectTimeoutLimit = 50000;  设置连接超时时间（单位ms）
                //int readTimeoutLimit = 50000;  设置读超时时间（单位ms）
                //respMsg = txMessenger.send(message, signature, connectTimeoutLimit, readTimeoutLimit, PaymentUserEnvironment.paymentusertxURL);// 0:message;
            } else {
                //HTTP默认连接超时时间是25s，读超时时间是25s
                //HTTPS默认连接超时时间是50s，读超时时间是50s
                respMsg = txMessenger.send(message, signature);// 0:message;
                //设置连接超时时间和读超时时间
                //int connectTimeoutLimit = 50000;  设置连接超时时间（单位ms）
                //int readTimeoutLimit = 50000;  设置读超时时间（单位ms）
                //respMsg = txMessenger.send(message, signature, connectTimeoutLimit, readTimeoutLimit);// 0:message;
            }
            // 1:signature
            String plainText = new String(payment.tools.util.Base64.decode(respMsg[0]), "UTF-8");

            logger.debug("[message]=[" + respMsg[0] + "]");
            logger.debug("[signature]=[" + respMsg[1] + "]");
            logger.debug("[plainText]=[" + plainText + "]");

            Tx1810Response txResponse = new Tx1810Response(respMsg[0], respMsg[1]);
            if ("2000".equals(txResponse.getCode())) {
                logger.info("[Message]=[" + txResponse.getMessage() + "]");
                List<Tx> txList = txResponse.getTxList();
                if (txList != null) {
                    int size = txList.size();
                    for (int i = 0; i < size; i++) {
                        Tx tx = txList.get(i);
                        logger.info("[TxType]=[" + tx.getTxType() + "]");
                        logger.info("[TxSn]=[" + tx.getTxSn() + "]");
                        logger.info("[TxAmount]=[" + tx.getTxAmount() + "]");
                        logger.info("[InstitutionAmount]=[" + tx.getInstitutionAmount() + "]");
                        logger.info("[PaymentAmount]=[" + tx.getPaymentAmount() + "]");
                        logger.info("[InstitutionFee]=[" + tx.getInstitutionFee() + "]");
                        logger.info("[Remark]=[" + tx.getRemark() + "]");
                        logger.info("[SettlementFlag]=[" + tx.getSettlementFlag() + "]");
                        logger.info("[BankNotificationTime]=[" + tx.getBankNotificationTime() + "]");
                    }
                }
                // 处理自己的业务
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "2000";
    }
}
