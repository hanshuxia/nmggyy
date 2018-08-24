package com.anchorcms.icloud.dao.synergy.impl;

import com.anchorcms.common.hibernate.Finder;
import com.anchorcms.common.hibernate.HibernateBaseDao;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.icloud.dao.synergy.SDemandQuoteDao;
import com.anchorcms.icloud.model.payment.SPOrder;
import com.anchorcms.icloud.model.payment.SPOrderObj;
import com.anchorcms.icloud.model.software.SSoftwareBuy;
import com.anchorcms.icloud.model.synergy.SBigdataDemandQuote;
import com.anchorcms.icloud.model.synergy.SDemand;
import com.anchorcms.icloud.model.synergy.SDemandQuote;
import com.anchorcms.icloud.model.synergy.SDemandQuoteObj;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Copyright @ 2016 Shandong jxdinfo software co,ltd.
 * All right reserved.
 * Created by Gao Jiangning
 *
 * @Date 2016/12/30 0030
 * @Desc 对需求进行报价的dao层实现类
 */
@Repository
public class SDemandQuoteDaoImpl extends HibernateBaseDao<SDemandQuote, Integer> implements SDemandQuoteDao {
    /**
     * 获得Dao对于的实体类
     *
     * @return
     */
    @Override
    protected Class<SDemandQuote> getEntityClass() {
        return SDemandQuote.class;
    }

    /**
     * @author: gao jiangning
     * @desciption 2016/12/30 保存一条完整的需求报价
     */
    public SDemandQuote save(SDemandQuote bean) {
        getSession().save(bean);
        return bean;
    }
    public SBigdataDemandQuote save2(SBigdataDemandQuote bean) {
        getSession().save(bean);
        return bean;
    }



    public Pagination getQuoteList(Integer demandId, int pageNo, int pageSize) {
        Finder f = Finder.create("select bean from SDemandQuote bean where");
        f.append(" bean.demand.demandId=:demandId");
        f.setParam("demandId", demandId);
        return find(f, pageNo, pageSize);
    }

    public List<SDemandQuote> getListJson(Integer demandId){
        Finder f = Finder.create("select bean from SDemandQuote bean where bean.selectedStatus <>2 ");
        f.append(" and bean.demand.demandId=:demandId");
        f.setParam("demandId", demandId);
        return find(f);
    }

    /**
     * 判断公司是否已对某需求报价
     *
     * @param demandId
     * @param companyId
     * @return
     */
    public Boolean hasQuoted(Integer demandId, String companyId) {
        Finder f = Finder.create("select bean from SDemandQuote bean where");
        f.append(" bean.demand.demandId=:demandId and bean.company.companyId=:companyId");
        f.setParam("demandId", demandId);
        f.setParam("companyId", companyId);
        if (super.countQueryResult(f) > 0) {
            return true;
        } else {
            return false;
        }
    }
    public Boolean hasQuoted2(Integer demandId, String companyId) {
        Finder f = Finder.create("select bean from SBigdataDemandQuote bean where");
        f.append(" bean.demand.demandId=:demandId and bean.company.companyId=:companyId");
        f.setParam("demandId", demandId);
        f.setParam("companyId", companyId);
        if (super.countQueryResult(f) > 0) {
            return true;
        } else {
            return false;
        }
    }
    public Boolean hasQuoted3(Integer abilityId, String companyId) {
        Finder f = Finder.create("select bean from SBigdataDemandQuote bean where");
        f.append(" bean.ability.abilityId=:abilityId and bean.company.companyId=:companyId");
        f.setParam("abilityId", abilityId);
        f.setParam("companyId", companyId);
        if (super.countQueryResult(f) > 0) {
            return true;
        } else {
            return false;
        }
    }
    /**
     * 批量对报价进行优选
     *
     * @param quoteIds
     */
    public Integer selectQuotes(String quoteIds) {
        if (quoteIds == null || "".equals(quoteIds.trim())) {
            return 0;
        }
        Query query = getSession().createQuery("update SDemandQuote bean set bean.selectedStatus='1' " +
                "where bean.demandQuoteId in (" + quoteIds + ")");
        return query.executeUpdate();
    }
    /**
     * 批量对报价取消优选
     *
     * @param quoteIds
     */
    public Integer cancelSelectedQuotes(String quoteIds) {
        if (quoteIds == null || "".equals(quoteIds.trim())) {
            return 0;
        }
        Query query = getSession().createQuery("update SDemandQuote bean set bean.selectedStatus='0' " +
                "where bean.demandQuoteId in (" + quoteIds + ")");
        return query.executeUpdate();
    }

    /**
     * 根据Id查询DemandQuote实体
     *
     * @param demandQuoteId
     *
     */
    public SDemandQuote findById(Integer demandQuoteId) {
        SDemandQuote sDemandQuote=get(demandQuoteId);
        return sDemandQuote;
    }
    public List<SDemandQuoteObj> findByObjId(Integer demandQuoteId) {
        SDemandQuote sDemandQuote=get(demandQuoteId);
        List<SDemandQuoteObj> sDemandQuoteObj= sDemandQuote.getDemandQuoteObjList();
        return sDemandQuoteObj;
    }
//    public SDemand findDemandId(Integer demandId) {
//        SDemand sDemand=get(demandId);
//        return sDemand;
//    }
    /**
     * @author liuyang
     * @Date 2017/5/2 14:33
     * @return
     */
    public SPOrder findbean(String id) {
        return (SPOrder) getSession().get(SPOrder.class, id);
    }

    public SPOrder save(SPOrder bean) {
        getSession().save(bean);
        return bean;
    }

    public void saveSPOrderObj(SPOrderObj o) {
        getSession().save(o);
    }

}