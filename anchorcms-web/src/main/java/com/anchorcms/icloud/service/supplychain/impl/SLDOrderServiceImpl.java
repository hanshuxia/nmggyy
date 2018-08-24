package com.anchorcms.icloud.service.supplychain.impl;

import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.dao.supplychain.SLDOrderDao;
import com.anchorcms.icloud.model.payment.SPPay;
import com.anchorcms.icloud.model.payment.SSupplychainOrder;
import com.anchorcms.icloud.service.payment.Tx1311Service;
import com.anchorcms.icloud.service.supplychain.SLDOrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Date;
import java.util.Map;

/**
 * @Author 韩淑霞
 * 协同制造/供应链订单表业务实现类
 * @Date 2017/05/02 0023 15:50
 */
@Service
@Transactional
public class SLDOrderServiceImpl implements SLDOrderService {
    /**
     * @Author 韩淑霞
     * 查询订单列表
     * @Date 2017/05/02 0023 15:50
     */
    public Pagination getPageForMember(Integer companyId, int pageNo, int pageSize, String statusType) {
        return dao.getPageBySelfCompany(pageNo, pageSize, companyId, statusType);
    }

    /**
     * @Author 韩淑霞
     * 删除订单信息
     * @Date 2016/12/29 0029 10:55
     */
    public SSupplychainOrder delete(String orderId) {
        SSupplychainOrder bean = dao.getOrderById(orderId);//获取能力实体类
        bean = dao.delete(bean);// 执行删除
        return bean;
    }

    @Resource
    private Tx1311Service Tx1322service;

    public SSupplychainOrder updateOrder(SSupplychainOrder bean) {
        return dao.updateOrder(bean);
    }

    public SSupplychainOrder save(SSupplychainOrder bean) {
        return dao.saveOrder(bean);
    }

    public SSupplychainOrder findOrderById(String orderId) {
        return dao.getOrderById(orderId);
    }


    public Map pay(String orderId, CmsUser user, String institutionID, String paymentNo, String orderNo, Double amount, Double fee,
                   String payerID, String payerName, String usage, String remark,
                   String notificationURL, String payees, String bankID, String bankName, Integer accountType,
                   String cardType) {
        SSupplychainOrder order = dao.getOrderById(orderId);
        SPPay sp = order.getSpPay();
        if(sp==null){
            sp = new SPPay();
        }
        sp.setUserId(user.getUserId());
        sp.setUserName(payerName);
        sp.setBuyDt(new Date(System.currentTimeMillis()));
        sp.setInstitutionId(institutionID);
        sp.setPaymentNo(paymentNo);
        sp.setOrderNo(orderNo);
        sp.setAmount(amount);
        sp.setFee(fee);
        sp.setPayerId(user.getUserId());
        //buy.setpayerName;
        sp.setUeage(usage);
        sp.setRemark(remark);
        sp.setPayees(payees);
        sp.setBankId(bankID);
        sp.setAccountType(accountType);
        sp.setCardType(cardType);
        order.setSpPay(sp);
        dao.saveOrder(order);
        Long Allamount = new Double(amount * 100).longValue();
        long fees = new Double(fee * 100).longValue();
        return Tx1322service.checkCode(institutionID, paymentNo, orderNo, Allamount, fees, payerID, payerName,
                usage, remark, notificationURL, payees, bankID, accountType, cardType);
    }

    @Resource
    private com.anchorcms.icloud.service.payment.ReceiveNoticeService ReceiveNoticeService;

    public void receiveNotice(String message, String signature) {
        message = message.replace(" ", "+");
        Map resultMessage = ReceiveNoticeService.receiveNotice(message, signature);
        String status = String.valueOf(resultMessage.get("Status"));
        String paymentNo = (String) resultMessage.get("PaymentNo");
        SPPay sp = dao.getBypaymentNo(paymentNo);
        String orderId = sp.getOrderNo();
        SSupplychainOrder bean = dao.getOrderById(orderId);
        if ("20".equals(status)) {
//支付成功
            bean.setState("2");
        } else if ("10".equals(status)) {
//支付失败
            bean.setState(status);
        }
        dao.updateOrder(bean);
        return;
    }

    public void editOrderStatus(String orderId, String setstatus,String isOnline) {
        SSupplychainOrder sbuy = this.dao.getOrderById(orderId);
        sbuy.setState(setstatus);
        if (isOnline != null) {
            sbuy.setIsOnline("1");
        }
        this.dao.updateOrder(sbuy);
    }

    @Resource
    private SLDOrderDao dao;
}
