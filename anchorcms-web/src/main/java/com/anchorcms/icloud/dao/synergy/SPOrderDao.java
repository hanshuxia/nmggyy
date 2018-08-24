package com.anchorcms.icloud.dao.synergy;

import com.anchorcms.common.hibernate.Updater;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.icloud.model.payment.SPOrder;

/**
 * @Author 韩淑霞
 * 协同制造/供应链订单表Dao
 * @Date 2017/05/02 0023 15:50
 */
public interface SPOrderDao {

    public Pagination getPageBySelfCompany(int pageNo, int pageSize,Integer companyId,  String statusType);
    public Pagination getPageBySelfCompany2(int pageNo, int pageSize,Integer companyId,  String status,String deFlag);
    public Pagination getPageBySelfCompany3(int pageNo, int pageSize,Integer companyId,  String status,String deFlag);
    public Pagination getPageBySelfCompany4(int pageNo, int pageSize,Integer companyId,  String status ,String deFlag);
    /**
     * @Author hansx
     * @param orderId
     * @Date 2017/05/02
     * @Desc 通过ID获取订单信息
     */
    public SPOrder findById(String orderId);

    /**
     * @Author hansx
     * @Date 2017/05/02 0029 8:51
     */
    public SPOrder updateByUpdater(Updater<SPOrder> updater);

    /**
     * @Author hansx
     * 删除订单信息
     * @Date 2017/05/02 0029 11:01
     */
    public SPOrder delete(SPOrder sPOrder);

    /**
     * @Author hansx
     * 修改订单信息
     * @Date 2017/05/02 0029 11:01
     */
    public SPOrder update(SPOrder sPOrder);

    /**
     * @Author hansx
     * 更新订单状态位
     * @param orderId
     * @Date 2017/05/02 0029 11:01
     */
    void updateStatusType(Integer orderId, String statusType);

}
