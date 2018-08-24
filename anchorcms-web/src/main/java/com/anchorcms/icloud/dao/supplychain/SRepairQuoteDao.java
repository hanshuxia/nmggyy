package com.anchorcms.icloud.dao.supplychain;


import com.anchorcms.common.page.Pagination;
import com.anchorcms.icloud.model.supplychain.SRepairQuote;

import java.util.Date;
import java.util.List;

/**
 * Created by hansx on 2017/1/2.
 * <p>
 * 维修资源报价DAO
 */
public interface SRepairQuoteDao {
    /**
     * @author hansx
     * @Date 2017/1/5 0005 下午 2:25
     * @return
     * 根据id获取报价信息
     */
    public SRepairQuote findById(String id);

   /**
    * @author hansx
    * @Date 2017/1/5 0005 下午 2:25
    * @return
    * 添加报价信息
    */
    public SRepairQuote save(SRepairQuote srr);


    /**
     * @author hansx
     * @Date 2017/1/5 0005 下午 2:26
     * @return
     * 获取报价列表
     */
    public List<SRepairQuote> getList();

    /**
     * @author hansx
     * @Date 2017/1/5 0005 下午 2:26
     * @return
     * 条件查询，分页
     */
    public Pagination getInquiryListForMember(String inquiryTheme, Date startDate, Date endDate, String companyId,
                                              Integer channelId, Integer siteId, Integer modelId, Integer memberId, int pageNo, int pageSize);
}
