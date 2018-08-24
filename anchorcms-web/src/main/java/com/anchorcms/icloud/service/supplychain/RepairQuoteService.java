package com.anchorcms.icloud.service.supplychain;


import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.model.supplychain.SRepairQuote;

import java.util.Date;

/**
*@Author 潘晓东
*@Date 2017/1/23 13:55
*@Return我的抢单报价
*/
public interface RepairQuoteService {
    /**
     *@Author 潘晓东
     *@Date 2017/1/5 14:51
     *@Return抢单报价列表查询分页
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
     * @Date 2017-05-03
     * @param orderId
     * @return
     * @Desc 根据id修改订单状态
     */
    public void mdyOrderStateById(String orderId, String state, String backReason);

    /**
     * @Author zhouyq
     * @Date 2017-05-03
     * @param
     * @return
     * @Desc 结算
     */
    String settle(String serialNumber,
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
                  String city, String orderId, String flag) throws Exception;

    /**
    *@Author 潘晓东
    *@Date 2017/1/23 13:55
    *@Return根据ID获取报价表信息
    */
    public SRepairQuote selectByQuoteID(String id);
}
