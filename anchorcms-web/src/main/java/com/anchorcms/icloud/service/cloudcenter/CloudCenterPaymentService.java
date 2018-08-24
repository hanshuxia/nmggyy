package com.anchorcms.icloud.service.cloudcenter;

import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.model.common.SOrderPayment;

/**
 * Created by Administrator on 2017/2/19.
 */
public interface CloudCenterPaymentService {
    SOrderPayment save(SOrderPayment payment, Integer managerId);
    SOrderPayment save(SOrderPayment payment);

    public Pagination getOrderPage(Integer siteId, CmsUser payUser, Integer pageNo, Integer pageSize, String status, String orderNo);

    /**
     * @Autner lilimin
     * 通过订单号查询信息
     * @param orderNo
     * @return
     */
    SOrderPayment getByPaymentNo(String orderNo);
    /**
     * @Autner lilimin
     * 通过订单号查询信息支付成功并修改状态
     * @param orderNo
     * @return
     */
    SOrderPayment getUpdateByPaymentNo(String PaymentNo);
    SOrderPayment update(SOrderPayment paymentss);

    public Pagination getOrderManagePage(Integer siteId, CmsUser user, int pageNo, int pageSize, String statusType, String orderNo);

    void deleteByOrderId(SOrderPayment orderId);

    String settle(String serialNumber, String orderNo, String remark, long l, int accountType, String bankID,String bankName, String accountName, String accountNumber, String branchName, String province, String city, SOrderPayment co, String flag) throws Exception;

    SOrderPayment getByOrderNo(String orderNo);
}
