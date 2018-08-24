package com.anchorcms.icloud.service.synergy.impl;

import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.dao.synergy.OrderPayDao;
import com.anchorcms.icloud.model.payment.*;
import com.anchorcms.icloud.model.synergy.SBigdataDemandQuote;
import com.anchorcms.icloud.service.payment.SettlementAndRefundService;
import com.anchorcms.icloud.service.payment.Tx1311Service;
import com.anchorcms.icloud.service.synergy.ALDPayService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@Service
@Transactional
public class ALDPayServiceImp implements ALDPayService {
    @Resource
    OrderPayDao OrderPayDao;
    @Resource
    private Tx1311Service Tx1322service;

    public SPOrder updateOrder(SPOrder bean) {
        return OrderPayDao.updateOrder(bean);
    }

    public SPOrder save(SPOrder bean) {
        return OrderPayDao.saveOrder(bean);
    }

    public SPOrder findOrderById(String orderId) {
        return OrderPayDao.getOrderById(orderId);
    }


    public Map pay(String orderId, CmsUser user, String institutionID, String paymentNo, String orderNo, Double amount, Double fee,
                   String payerID, String payerName, String usage, String remark,
                   String notificationURL, String payees, String bankID, String bankName, Integer accountType,
                   String cardType) {
        SPOrder order = OrderPayDao.getOrderById(orderId);
        SPPay sp = order.getSpPay();
        if (sp == null) {
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
        //OrderPayDao.savePay(sp);
        order.setSpPay(sp);
        OrderPayDao.saveOrder(order);
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
        SPPay sp = OrderPayDao.getBypaymentNo(paymentNo);
        String orderId = sp.getOrderNo();
        SPOrder bean = OrderPayDao.getOrderById(orderId);
        if ("20".equals(status)) {
//支付成功
            bean.setState("2");
        } else if ("10".equals(status)) {
//支付失败
            bean.setState(status);
        }
        OrderPayDao.updateOrder(bean);
        return;
    }

    public void editOrderStatus(String orderId, String setstatus,String isOnline ) {
        SPOrder sbuy = this.OrderPayDao.getOrderById(orderId);
        sbuy.setState(setstatus);
        if (isOnline != null) {
            sbuy.setIsOnline("1");
        }
        this.OrderPayDao.updateOrder(sbuy);
    }
    public void editOrderStatus2(Integer demandQuoteId, String setstatus,String isOnline ) {
        SBigdataDemandQuote sbuy = this.unionCityService.findRegistredById2(demandQuoteId);
        sbuy.setDeFlag(setstatus);
        this.OrderPayDao.updateOrder2(sbuy);

    }

    public void adminSaveSettle(String orderId, double fee,double income,String flag) {
        SPOrder or = OrderPayDao.getOrderById(orderId);
        double price = 0.00;
        if (or == null) {
            SSupplychainOrder sSupplychainOrder = OrderPayDao.getSupplychainOrderById(orderId);
            price = sSupplychainOrder.getPrice();
        } else {
            price = or.getPrice();
        }
        SPAdminSettle ase = new SPAdminSettle();
        ase.setFee(fee);
        ase.setOrdeId(orderId);
        ase.setOrderPrice(price);
        ase.setIncome(income);
        ase.setState("0");
        ase.setFlag(flag);
        ase.setStDt(new Date(System.currentTimeMillis()));
        OrderPayDao.saveAdminSet(ase);
    }

    public Map<String, Double> calc(double price, int type) {
        Map<String, Double> m = new HashMap<String, Double>();
        double a = 0;
        if (type == 12) {//b2c支付手续费计算
            if (price <= 100000) {
                a = 10;
            } else if (price <= 500000) {
                a = 15;
            } else {
                a = 30;
            }
        } else if (type == 11) {
            BigDecimal b1 = new BigDecimal(Double.toString(price));
            BigDecimal b2 = new BigDecimal(Double.toString(3));
            BigDecimal b3 = new BigDecimal(Double.toString(1000));
            a = (b1.multiply(b2)).divide(b3, 2, RoundingMode.HALF_UP).doubleValue();
            if (a < 1) {
                a = 1;
            }
        }

        BigDecimal b1 = new BigDecimal(Double.toString(price));
        BigDecimal b2 = new BigDecimal(Double.toString(a));
        double b = b1.subtract(b2).doubleValue();
        m.put("fee", a);
        m.put("price", b);
        return m;
    }

    public SPAdminSettle findSadminSettle(int id) {
        return this.OrderPayDao.findAdminSettleById(id);
    }
    @Resource
    private SettlementAndRefundService SettleService;
    @Resource
    com.anchorcms.icloud.service.payment.impl.SendMessageServiceImpl SendMessageServiceImpl;

    public String settle(String serialNumber,
                         String orderNo,
                         String remark,
                         long amount,
                         int accountType,
                         String bankID,
                         String bankName,
                         String accountName,
                         String accountNumber,
                         String branchName,
                         String province,
                         String city, int id)throws Exception {
        SPAdminSettle sadmin =  this.OrderPayDao.findAdminSettleById(id);
         orderNo = sadmin.getOrdeId();
        amount = (long) (sadmin.getFee()* 100);
        SendMessageVO vo = SettleService.tx1341(serialNumber, orderNo, remark, amount,
                    accountType,
                    bankID,
                    accountName,
                    accountNumber,
                    branchName,
                    province,
                    city);
        Map<String, String> fla = SendMessageServiceImpl.sendMessage(vo.getMessage(), vo.getSignature(), vo.getTxCode(), vo.getTxName(), vo.getFlag());
       SPSettle po = new SPSettle();
        String u = UUID.randomUUID().toString().replaceAll("-", "");
        po.setId(u);
        po.setAccountType(accountType);
        po.setAcountName(accountName);
        po.setAcountNumber(accountNumber);
        po.setBranchName(branchName);
        po.setBankId(bankID);
        po.setBankName(bankName);
        po.setProvince(province);
        po.setCity(city);
        po.setSerialNumber(serialNumber);
        po.setOrderNo(orderNo);
        sadmin.setSpSettle(po);
        if (fla.get("message").equals("success")) {
            po.setResultFlag("1");
            sadmin.setState("2");
            this.OrderPayDao.updateAdminSettle(sadmin);
            return "success";
        } else {
            this.OrderPayDao.updateAdminSettle(sadmin);
            //退款失败
            return fla.get("message");
        }

    }

    public void delAdminSettle(int id) {
      SPAdminSettle sa =   this.OrderPayDao.findAdminSettleById(id);
        SPSettle sp = sa.getSpSettle();
        this.OrderPayDao.deleteAdminSettle(sa);

    }
    @Resource
    com.anchorcms.icloud.service.unionCity.unionCityService unionCityService;
}
