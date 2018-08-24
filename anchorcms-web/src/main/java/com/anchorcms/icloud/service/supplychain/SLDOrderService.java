package com.anchorcms.icloud.service.supplychain;

import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.model.payment.SSupplychainOrder;

import java.util.Map;

/**
 * @Author 韩淑霞
 * 协同制造/供应链订单表业务类
 * @Date 2017/05/02 0023 15:50
 */
public interface SLDOrderService {
    /**
     * @Author 韩淑霞
     * 查询订单列表
     * @Date 2017/05/02  0023 15:50
     */
    public Pagination getPageForMember(Integer companyId, int pageNo, int pageSize, String statusType);

    /**
     * @Author 韩淑霞
     * 删除订单信息
     * @Date 2017/05/02  0029 10:56
     */
    public SSupplychainOrder delete(String orderId);

    SSupplychainOrder updateOrder(SSupplychainOrder bean);

    SSupplychainOrder save(SSupplychainOrder bean);

    SSupplychainOrder findOrderById(String orderId);

    Map pay(String orderId, CmsUser user, String institutionID, String paymentNo, String orderNo, Double amount, Double fee, String payerID, String payerName, String usage, String remark, String notificationURL, String payees, String bankID, String bankName, Integer accountType, String cardType);

    void receiveNotice(String message, String signature);

    void editOrderStatus(String orderId, String setstatus,String isOnline);

}
