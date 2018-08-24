package com.anchorcms.icloud.service.payment.impl;

import com.anchorcms.icloud.dao.cloudcenter.CloudCenterPaymentDao;
import com.anchorcms.icloud.model.common.SOrderPayment;
import com.anchorcms.icloud.service.cloudcenter.CloudCenterPaymentService;
import com.anchorcms.icloud.service.payment.Tx1311Service;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import payment.api.tx.marketorder.Tx1311Request;
import payment.tools.util.XmlUtil;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ly on 2017/2/16.
 */
@Service
@Transactional
public class Tx1311ServiceImpl implements Tx1311Service {
    public Map checkCode(String institutionID, String paymentNo, String orderNo, Long amount, Long fee,
                         String payerID, String payerName, String usage, String remark,
                         String notificationURL, String payees, String bankID, Integer accountType,
                         String cardType) {
        Map attributes = new HashMap();
        try {
            // 创建交易请求对象
            Tx1311Request tx1311Request = new Tx1311Request();
            tx1311Request.setInstitutionID(institutionID);
            tx1311Request.setOrderNo(orderNo);
            tx1311Request.setPaymentNo(paymentNo);
            tx1311Request.setAmount(amount);
            tx1311Request.setFee(fee);
            tx1311Request.setPayerID(null);
            tx1311Request.setPayerName(null);
            tx1311Request.setUsage(usage);
            tx1311Request.setRemark(remark);
            tx1311Request.setNotificationURL(notificationURL);
            tx1311Request.setBankID(bankID);
            tx1311Request.setAccountType(11);
          if (null != payees && payees.length() > 0) {
                String[] payeeList = payees.split(";");
                for (int i = 0; i < payeeList.length; i++) {
                    //tx1311Request.addPayee(payeeList[i]);
                    tx1311Request.addPayee(null);
                }
            }
            tx1311Request.setCardType("01");

            // 3.执行报文处理
            tx1311Request.process();
            // 获得参数message和signature
            /*String requestPlainText = tx1311Request.getRequestPlainText();
            String message = tx1311Request.getRequestMessage();
            String signature = tx1311Request.getRequestSignature();
            String txCode = "1311";
            String txName = "市场订单支付直通车";
            String flag = "";*/
            String prettyPlainText = XmlUtil.createPrettyFormat(XmlUtil.createDocument((String)tx1311Request.getRequestPlainText()));
            attributes.put("RequestPlainText", prettyPlainText);
            attributes.put("message", tx1311Request.getRequestMessage());
            attributes.put("signature", tx1311Request.getRequestSignature());
            attributes.put("txCode", "1311");
            attributes.put("txName", "市场订单支付直通车");
            attributes.put("flag", "");

            // 处理自己的业务
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return attributes;
    }

    /**
     * @param
     * @return
     * @Author zhouyq
     * @Date 2017/03/25
     * @Desc 根据id修改退款订单状态（拒绝）
     */
    public void mdyOrderStateById(String orderNo, String status, String backReason) {
            SOrderPayment sOrderPayment = cloudCenterPaymentService.getByOrderNo(orderNo);
            sOrderPayment.setBackReason(backReason);
            if (status != null) {
                cloudCenterPaymentDao.mdyOrderStateById(orderNo, status);
            }
    }

    @Resource
    private CloudCenterPaymentService cloudCenterPaymentService;
    @Resource
    private CloudCenterPaymentDao cloudCenterPaymentDao;

}
