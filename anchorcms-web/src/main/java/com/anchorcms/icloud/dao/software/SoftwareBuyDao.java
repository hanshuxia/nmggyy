package com.anchorcms.icloud.dao.software;

import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.model.payment.PaySettlementRefund;
import com.anchorcms.icloud.model.software.SSoftwareBuy;

import java.sql.Date;

/**
 * Created by ly on 2017/1/6.
 */
public interface SoftwareBuyDao {
    public SSoftwareBuy save(SSoftwareBuy buy);

    public Pagination getPage(Integer siteId, CmsUser user, int pageNo, int pageSize, Date startDate,String status,String paramu);

    public SSoftwareBuy update(SSoftwareBuy bean);
    SSoftwareBuy findByPaymentNo(String id);

    SSoftwareBuy findByOrderNo(String orderNo);

    SSoftwareBuy findbean(int id);
    
    /**
     * @Author zhouyq
     * @Date 2017/03/24
     * @param
     * @return
     * @Desc ����id�޸��˿��״̬���ܾ���
     */
    public void mdyOrderStateById(int orderId, String status);

    public PaySettlementRefund findbySerialNumber(String serialNumber);

    void updatePaySettle(PaySettlementRefund pay);
}
