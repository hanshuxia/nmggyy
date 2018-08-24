package com.anchorcms.icloud.dao.cloudcenter;

import com.anchorcms.common.page.Pagination;
import com.anchorcms.icloud.model.cloudcenter.SIcloudDemandQuote;

import java.util.List;

/**
 * @anther 李利民
 * @descript
 * @data 2017/1/615:20
 */
public interface CloudCenterQuoteDao {
    SIcloudDemandQuote save(SIcloudDemandQuote sDemandQuote);

    SIcloudDemandQuote update(SIcloudDemandQuote sDemandQuote);


    List<SIcloudDemandQuote> getICDemandQuoteByDemandId(Integer demandId);

    List getICDemandQuoteSelectedByDemandId(Integer demandId);

    Pagination getICDemandQuoteByDemandIdPage(Integer demandId, Integer pageNo);

    SIcloudDemandQuote byDemand_obj_id(Integer demand_obj_id);

    /**
     * 判断公司是否已对某云需求报价
     * @param demandId
     * @param companyId
     * @return
     */
    boolean hasQuoted(Integer demandId, String companyId);

    //批量优选(selectedStatus=1)/取消优选(selectedStatus=0)
    Integer selectQuotesChange(String quoteIds, String selectedStatus);

    /**
     * 根据quoteId获得报价实体
     * @param quoteId
     * @return
     */
    SIcloudDemandQuote findById(Integer quoteId);
}
