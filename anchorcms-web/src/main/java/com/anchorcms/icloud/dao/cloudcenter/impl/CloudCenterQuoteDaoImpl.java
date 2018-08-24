package com.anchorcms.icloud.dao.cloudcenter.impl;

import com.anchorcms.common.hibernate.Finder;
import com.anchorcms.common.hibernate.HibernateBaseDao;
import com.anchorcms.common.hibernate.Updater;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.icloud.dao.cloudcenter.CloudCenterQuoteDao;
import com.anchorcms.icloud.model.cloudcenter.SIcloudDemandQuote;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @anther 李利民
 * @descript
 * @data 2017/1/615:21
 */
@Repository
public class CloudCenterQuoteDaoImpl  extends HibernateBaseDao<SIcloudDemandQuote, Integer> implements CloudCenterQuoteDao {
    public SIcloudDemandQuote save(SIcloudDemandQuote sDemandQuote) {
        getSession().save(sDemandQuote);
        return sDemandQuote;
    }

    public SIcloudDemandQuote update(SIcloudDemandQuote sDemandQuote) {
        Updater<SIcloudDemandQuote> updater = new Updater<SIcloudDemandQuote>(sDemandQuote);
        return updateByUpdater(updater);
    }

    public List<SIcloudDemandQuote> getICDemandQuoteByDemandId(Integer demandId) {
        Finder finder = Finder.create("select bean from SIcloudDemandQuote bean where bean.icloudDemand.demandId=:demandId");
        finder.setParam("demandId",demandId);
        finder.setCacheable(true);
        Query query = getSession().createQuery(finder.getOrigHql());
        finder.setParamsToQuery(query);
        List<SIcloudDemandQuote> list = query.list();
        return list;
    }

    @SuppressWarnings("unchecked")
    public List<SIcloudDemandQuote> getICDemandQuoteSelectedByDemandId(Integer demandId) {
        Finder finder = Finder.create("select bean from SIcloudDemandQuote bean where bean.icloudDemand.demandId=:demandId and bean.selectedStatus='1'");
        finder.setParam("demandId",demandId);
        finder.setCacheable(true);
        return find(finder);
    }

    public Pagination getICDemandQuoteByDemandIdPage(Integer demandId, Integer pageNo) {
        Finder f = Finder.create("select bean from SIcloudDemandQuote bean where");
        f.append(" bean.icloudDemand.demandId=:demandId");
        f.setParam("demandId", demandId);
        f.setCacheable(true);
        return find(f, pageNo, 8);
    }

    public SIcloudDemandQuote byDemand_obj_id(Integer demand_obj_id) {
        SIcloudDemandQuote bean = get(demand_obj_id);
        System.out.println(bean);
        return bean;
    }

    /**
     * 判断公司是否已对某云需求报价
     *
     * @param demandId
     * @param companyId
     * @return
     */
    public boolean hasQuoted(Integer demandId, String companyId) {
        Finder f = Finder.create("select bean from SIcloudDemandQuote bean where");
        f.append(" bean.icloudDemand.demandId=:demandId and bean.company.companyId=:companyId");
        f.setParam("demandId", demandId);
        f.setParam("companyId", companyId);
        if (super.countQueryResult(f) > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 根据quoteId获得报价实体
     *
     * @param quoteId
     * @return
     */
    public SIcloudDemandQuote findById(Integer quoteId) {
        SIcloudDemandQuote demandQuote = get(quoteId);
        return demandQuote;
    }

    public Integer selectQuotesChange(String quoteIds, String selectedStatus) {
        if (quoteIds == null || "".equals(quoteIds.trim())) {
            return 0;
        }
        Query query = getSession().createQuery("update SIcloudDemandQuote bean set bean.selectedStatus='"+selectedStatus+"' " +
                "where bean.demandObjId in (" + quoteIds + ")");
        return query.executeUpdate();
    }

    /**
     * 获得Dao对于的实体类
     *
     * @return
     */
    @Override
    protected Class<SIcloudDemandQuote> getEntityClass() {
        return SIcloudDemandQuote.class;
    }
}
