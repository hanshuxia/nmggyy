package com.anchorcms.icloud.service.payment.impl;

import com.anchorcms.icloud.service.payment.SendMessegeService;
import org.springframework.stereotype.Service;
import payment.api.system.CMBEnvironment;
import payment.api.system.CrossBorderEnvironment;
import payment.api.system.PaymentUserEnvironment;
import payment.api.system.TxMessenger;
import payment.api.tx.marketorder.Tx1320Response;
import payment.api.tx.marketorder.Tx1333Response;
import payment.api.tx.marketorder.Tx134xResponse;
import payment.api.tx.statement.Tx1810Response;
import payment.api.vo.Tx;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ldong on 2017/2/17.
 */
@Service
public class SendMessageServiceImpl implements SendMessegeService {

    public Map<String, String> sendMessage(String message,
                                           String signature,
                                           String txCode,
                                           String txName,
                                           String flag) throws Exception {
        Map map = new HashMap();
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

      /*  logger.debug("[message]=[" + respMsg[0] + "]");
        logger.debug("[signature]=[" + respMsg[1] + "]");
        logger.debug("[plainText]=[" + plainText + "]");*/
        if ("1320".equals(txCode)) {
            Tx1320Response tx1320Response = new Tx1320Response(respMsg[0], respMsg[1]);
           /* request.setAttribute("txCode", txCode);
            request.setAttribute("txName", txName);
            request.setAttribute("plainText", tx1320Response.getResponsePlainText());*/
            if ("2000".equals(tx1320Response.getCode())) {
               /* logger.info("[Message]=[" + tx1320Response.getMessage() + "]");
                logger.info("[InstitutionID]=[" + tx1320Response.getInstitutionID() + "]");
                logger.info("[PaymentNo]=[" + tx1320Response.getPaymentNo() + "]");
                logger.info("[Amount]=[" + tx1320Response.getAmount() + "]");
                logger.info("[Remark]=[" + tx1320Response.getRemark() + "]");
                logger.info("[Status]=[" + tx1320Response.getStatus() + "]");
                logger.info("[BankNotificationTime]=[" + tx1320Response.getBankNotificationTime() + "]");
               */ // 处理业务
            }
        } else if ("1333".equals(txCode)) {
            Tx1333Response tx1333Response = new Tx1333Response(respMsg[0], respMsg[1]);
           /* request.setAttribute("txCode", txCode);
            request.setAttribute("txName", txName);
            request.setAttribute("plainText", tx1333Response.getResponsePlainText());*/
            if ("2000".equals(tx1333Response.getCode())) {
                /*logger.info("[Message]=[" + tx1333Response.getMessage() + "]");
                logger.info("[InstitutionID]=[" + tx1333Response.getInstitutionID() + "]");
                logger.info("[PaymentNo]=[" + tx1333Response.getPaymentNo() + "]");
                logger.info("[OrderNo]=[" + tx1333Response.getOrderNo() + "]");
                logger.info("[SerialNumber]=[" + tx1333Response.getSerialNumber() + "]");
                // 处理业务*/
                return null;
            }
        } else if ("1341".equals(txCode) || "1343".equals(txCode)) {
            Tx134xResponse tx134xResponse = new Tx134xResponse(respMsg[0], respMsg[1]);
           /* request.setAttribute("txCode", txCode);
            request.setAttribute("txName", txName);
            request.setAttribute("plainText", tx134xResponse.getResponsePlainText());*/
            map.put("code", tx134xResponse.getCode());
            if ("2000".equals(tx134xResponse.getCode())) {
                map.put("message", "success");
            /*    logger.info("[Message]=[" + tx134xResponse.getMessage() + "]");*/
                // 处理业务
                return map;
            } else {
                map.put("message", tx134xResponse.getMessage());
                return map;
            }
        } else if ("1810".equals(txCode)) {
            Tx1810Response txResponse = new Tx1810Response(respMsg[0], respMsg[1]);
         /*   request.setAttribute("txCode", txCode);
            request.setAttribute("txName", txName);
            request.setAttribute("plainText", txResponse.getResponsePlainText());*/
            if ("2000".equals(txResponse.getCode())) {
                //logger.info("[Message]=[" + txResponse.getMessage() + "]");
                List<Tx> txList = txResponse.getTxList();
                if (txList != null) {
                    int size = txList.size();
                    for (int i = 0; i < size; i++) {
                        Tx tx = txList.get(i);
                       /* logger.info("[TxType]=[" + tx.getTxType() + "]");
                        logger.info("[TxSn]=[" + tx.getTxSn() + "]");
                        logger.info("[TxAmount]=[" + tx.getTxAmount() + "]");
                        logger.info("[InstitutionAmount]=[" + tx.getInstitutionAmount() + "]");
                        logger.info("[PaymentAmount]=[" + tx.getPaymentAmount() + "]");
                        logger.info("[InstitutionFee]=[" + tx.getInstitutionFee() + "]");
                        logger.info("[Remark]=[" + tx.getRemark() + "]");
                        logger.info("[SettlementFlag]=[" + tx.getSettlementFlag() + "]");
                        logger.info("[BankNotificationTime]=[" + tx.getBankNotificationTime() + "]");*/
                    }
                }
                // 处理业务
            }
        }
        return null;
    }

}