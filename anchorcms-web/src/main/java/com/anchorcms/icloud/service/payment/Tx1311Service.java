package com.anchorcms.icloud.service.payment;

import java.util.Map;

/**
 * Created by ly on 2017/2/16.
 */
public interface Tx1311Service {
    Map checkCode(String institutionID, String paymentNo, String orderNo, Long amount, Long fee, String payerID, String payerName, String usage, String remark, String notificationURL, String payees, String bankID, Integer accountType, String cardType);

    /**
     * @Author zhouyq
     * @Date 2017/03/25
     * @param orderNo
     * @return
     * @Desc 根据id修改退款订单状态（拒绝）
     */
    public void mdyOrderStateById(String orderNo, String status, String backReason);
}
