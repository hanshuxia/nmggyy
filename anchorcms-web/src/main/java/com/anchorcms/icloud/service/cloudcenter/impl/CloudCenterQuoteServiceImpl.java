package com.anchorcms.icloud.service.cloudcenter.impl;

import com.anchorcms.common.page.Pagination;
import com.anchorcms.icloud.dao.cloudcenter.CloudCenterQuoteDao;
import com.anchorcms.icloud.model.cloudcenter.SIcloudDemandQuote;
import com.anchorcms.icloud.service.cloudcenter.CloudCenterQuoteService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @anther 李利民
 * @descript
 * @data 2017/1/1014:22
 */
@Service
@Transactional
public class CloudCenterQuoteServiceImpl implements CloudCenterQuoteService {
    public SIcloudDemandQuote save(SIcloudDemandQuote sDemandQuote) {
        return cloudCenterQuoteDao.save(sDemandQuote);
    }

    public List<SIcloudDemandQuote> getICDemandQuoteByDemandId(Integer demandId) {
        return cloudCenterQuoteDao.getICDemandQuoteByDemandId(demandId);
    }

    public List<SIcloudDemandQuote> getICDemandQuoteSelectedByDemandId(Integer demandId) {
        return cloudCenterQuoteDao.getICDemandQuoteSelectedByDemandId(demandId);
    }

    public Pagination getICDemandQuoteByDemandIdPage(Integer demandId, Integer pageNo) {
        return cloudCenterQuoteDao.getICDemandQuoteByDemandIdPage(demandId, pageNo);
    }

    public SIcloudDemandQuote byDemand_obj_id(Integer demand_obj_id) {
        return cloudCenterQuoteDao.byDemand_obj_id(demand_obj_id);
    }

    /**
     * 判断公司是否已对某云需求报价
     *
     * @param demandId
     * @param companyId
     * @return
     */
    public Boolean hasQuoted(Integer demandId, String companyId) {
        return cloudCenterQuoteDao.hasQuoted(demandId, companyId);
    }

    /**
     * 根据quoteId获得报价实体
     *
     * @param quoteId
     * @return
     */
    public SIcloudDemandQuote byDemandQuoteId(Integer quoteId) {
        return cloudCenterQuoteDao.findById(quoteId);
    }

    public Integer selectQuotesChange(String quoteIds, String selectedStatus) {
        if (quoteIds == null || "".equals(quoteIds)) {
            return 0;
        }
        if (',' == (quoteIds.charAt(quoteIds.length() - 1))) {
            quoteIds = quoteIds.substring(0, quoteIds.length() - 1);
        }
        return cloudCenterQuoteDao.selectQuotesChange(quoteIds, selectedStatus);
    }

    @Resource
    private CloudCenterQuoteDao cloudCenterQuoteDao;

}
