package com.anchorcms.icloud.service.synergy;

import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.model.payment.SPAdminSettle;
import com.anchorcms.icloud.model.payment.SPOrder;

import java.util.Map;

public interface ALDPayService {
    SPOrder updateOrder(SPOrder bean);
    SPOrder save(SPOrder bean);

    SPOrder findOrderById(String orderId);

    Map pay(String orderId, CmsUser user, String institutionID, String paymentNo, String orderNo, Double amount, Double fee, String payerID, String payerName, String usage, String remark, String notificationURL, String payees, String bankID, String bankName, Integer accountType, String cardType);

    void receiveNotice(String message, String signature);

    void editOrderStatus(String orderId, String setstatus,String isOnline);
    void editOrderStatus2(Integer demandQuoteId, String setstatus,String isOnline);

    void adminSaveSettle(String orderId,double price,double income,String flag);

    Map<String,Double> calc(double Price,int type);

    SPAdminSettle findSadminSettle(int id);

    String settle(String serialNumber, String orderNo, String s, long l, int accountType, String bankID, String bankName, String accountName, String accountNumber, String branchName, String province, String city, int id) throws Exception;

    void delAdminSettle(int id);
}
