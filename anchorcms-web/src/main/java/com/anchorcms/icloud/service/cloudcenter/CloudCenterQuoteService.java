package com.anchorcms.icloud.service.cloudcenter;

import com.anchorcms.common.page.Pagination;
import com.anchorcms.icloud.model.cloudcenter.SIcloudDemandQuote;

import java.util.List;

/**
 * @anther 李利民
 * @descript
 * @data 2017/1/1014:19
 */
public interface CloudCenterQuoteService {

    SIcloudDemandQuote save(SIcloudDemandQuote sDemandQuote);

    List<SIcloudDemandQuote> getICDemandQuoteByDemandId(Integer demandId);

    List<SIcloudDemandQuote> getICDemandQuoteSelectedByDemandId(Integer demandId);

    Pagination getICDemandQuoteByDemandIdPage(Integer demandId, Integer pageNo);

    SIcloudDemandQuote byDemand_obj_id(Integer demand_obj_id);
    /**
     * 判断公司是否已对某云需求报价
     * @param demandId
     * @param companyId
     * @return
     */
    Boolean hasQuoted(Integer demandId, String companyId);

    Integer selectQuotesChange(String quoteIds, String selectedStatus);

    /**
     * 根据quoteId获得报价实体
     * @param quoteId
     * @return
     */
    SIcloudDemandQuote byDemandQuoteId(Integer quoteId);
}
