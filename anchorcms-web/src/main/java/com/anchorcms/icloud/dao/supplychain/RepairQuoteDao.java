package com.anchorcms.icloud.dao.supplychain;


import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.model.supplychain.SRepairQuote;

import java.util.Date;

/**
*@Author 潘晓东
*@Date 2017/1/5 18:34
*@Return抢单报价
*/
public interface RepairQuoteDao {
    /**
    *@Author 潘晓东
    *@Date 2017/1/5 18:33
    *@Return查询所有抢单报价
    */
    public Pagination getQuoteListForMember(CmsUser user,String repairName,String inquiryTheme, Date myStartDate, Date myEndDate, String companyId,
                                            int pageNo, int pageSize);

    /***
     * @author zhouyq
     * @date 2017-05-03
     * @return
     * @desc 供应链维修方订单列表
     */
    public Pagination getSupplychainSellerOrder(Integer siteId, CmsUser user, int pageNo, int pageSize, Date startDate, String state, String orderId);

    /**
     * @Author zhouyq
     * @Date 2017/05/03
     * @param
     * @return
     * @Desc 根据id修改订单状态
     */
    public void mdyOrderStateById(String orderId, String status);

    /**
    *@Author 潘晓东
    *@Date 2017/1/5 18:33
    *@Return抢单报价根据ID查询
    */
    public SRepairQuote selectByQuoteID(String id);
}
