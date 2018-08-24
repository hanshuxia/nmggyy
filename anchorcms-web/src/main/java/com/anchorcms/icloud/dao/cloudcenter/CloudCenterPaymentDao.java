package com.anchorcms.icloud.dao.cloudcenter;

import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.model.common.SOrderPayment;

/**
 * Created by Administrator on 2017/2/19.
 */
public interface CloudCenterPaymentDao {
    SOrderPayment save(SOrderPayment payment);

    public Pagination getPage(Integer siteId, CmsUser payUser, Integer pageNo, Integer pageSize, String status, String orderNo);

    SOrderPayment ByorderNo(String orderNo);

    SOrderPayment update(SOrderPayment payment);

    public Pagination getManagePage(Integer siteId, int pageNo, int pageSize, String statusType, String orderNo);

    void deleteByOrderId(SOrderPayment orderId);

    SOrderPayment getByPaymentNo(String paymentNo);

    /**
     * @Author zhouyq
     * @Date 2017/03/25
     * @param
     * @return
     * @Desc 根据id修改退款订单状态（拒绝）
     */
    public void mdyOrderStateById(String orderNo, String status);
}
