package com.anchorcms.icloud.dao.supplychain;

import com.anchorcms.common.hibernate.Updater;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.icloud.model.payment.SPPay;
import com.anchorcms.icloud.model.payment.SSupplychainOrder;

/**
 * @Author 韩淑霞
 * 协同制造/供应链订单表Dao
 * @Date 2017/05/02 0023 15:50
 */
public interface SLDOrderDao {

    public Pagination getPageBySelfCompany(int pageNo, int pageSize, Integer companyId, String statusType);

    /**
     * @Author hansx
     * @Date 2017/05/02 0029 8:51
     */
    public SSupplychainOrder updateByUpdater(Updater<SSupplychainOrder> updater);

    /**
     * @Author hansx
     * 删除订单信息
     * @Date 2017/05/02 0029 11:01
     */
    public SSupplychainOrder delete(SSupplychainOrder sPOrder);

    public SSupplychainOrder saveOrder(SSupplychainOrder bean);
    public SSupplychainOrder updateOrder(SSupplychainOrder bean);
    public SSupplychainOrder getOrderById(String id);
    public SPPay savePay(SPPay bean);

    SPPay getBypaymentNo(String paymentNo);
}
