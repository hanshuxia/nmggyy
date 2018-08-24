package com.anchorcms.icloud.dao.unionCity.impl;

import com.anchorcms.common.hibernate.Finder;
import com.anchorcms.common.hibernate.HibernateBaseDao;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.dao.unionCity.unionCityDao;
import com.anchorcms.icloud.model.commservice.*;
import com.anchorcms.icloud.model.synergy.SBigdataDemandQuote;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * @author zhouyq
 * @Date 2017/8/7
 * @return
 * 盟市行dao实现类
 */
@Repository
public class unionCityDaoImpl extends HibernateBaseDao<SUnionCity, Integer> implements unionCityDao {

    /**
     * @author zhouyq
     * @Date 2017/8/7
     * @return
     * 盟市行实体类保存
     */
    public SUnionCity creUnionCityEntity(SUnionCity sUnionCity) {
        getSession().save(sUnionCity);
        return sUnionCity;
    }

    protected Class<SUnionCity> getEntityClass() {
        return SUnionCity.class;
    }


    public Pagination getPage(Integer siteId, CmsUser user, int pageNo, int pageSize,
                              java.sql.Date startDate, java.sql.Date endDate, String state, String paramu) {
           Finder f = Finder.create("select bean from SUnionCity bean where 1=1 ");
        if (startDate != null) {
            f.append(" and bean.updateDt >=:startDate");
            f.setParam("startDate", startDate);
        }
        if (endDate != null) {
            f.append(" and bean.updateDt <=:endDate");
            f.setParam("endDate", endDate);
        }
        f.append(" order by bean.updateDt desc");
        f.setCacheable(true);
        return find(f, pageNo, pageSize);
    }

    public Pagination getPage2(Integer siteId, CmsUser user, int pageNo, int pageSize, String paramu) {
        Finder f = Finder.create("select bean from SBigDataSign bean where 1=1 ");

        f.append(" order by bean.bigdataId desc");
        f.setCacheable(true);
        return find(f, pageNo, pageSize);
    }

    public Pagination getBigdataOnline(Integer siteId, CmsUser user, int pageNo, int pageSize, String paramu) {
        Finder f = Finder.create("select bean from SBigDataDemandSign bean where 1=1 ");

        f.append(" order by bean.bigdataId desc");
        f.setCacheable(true);
        return find(f, pageNo, pageSize);
    }

    public Pagination getBigdataNews(Integer siteId, CmsUser user, int pageNo, int pageSize, String paramu) {
        Finder f = Finder.create("select bean from SBigDataNews bean where 1=1 ");

        f.append(" order by bean.bigdataId desc");
        f.setCacheable(true);
        return find(f, pageNo, pageSize);
    }

    public Pagination getBigdataPolicy(Integer siteId, CmsUser user, int pageNo, int pageSize, String paramu) {
        Finder f = Finder.create("select bean from SBigDataPolicy bean where 1=1 ");

        f.append(" order by bean.bigdataId desc");
        f.setCacheable(true);
        return find(f, pageNo, pageSize);
    }



    public SBigDataSign findRegistredById(Integer id) {
        Query query = getSession().createQuery("SELECT bean from SBigDataSign bean where  bean.bigdataId=:bigdataId ")
                .setParameter("bigdataId", id);
        SBigDataSign sBigDataSign = (SBigDataSign) query.uniqueResult();
        return sBigDataSign;
    }
    public SBigdataDemandQuote findRegistredById2(Integer id) {
        Query query = getSession().createQuery("SELECT bean from SBigdataDemandQuote bean where  bean.demandQuoteId=:demandQuoteId ")
                .setParameter("demandQuoteId", id);
        SBigdataDemandQuote sBigdataDemandQuote = (SBigdataDemandQuote) query.uniqueResult();
        return sBigdataDemandQuote;
    }

    public SBigDataDemandSign findOnlineById(Integer id) {
        Query query = getSession().createQuery("SELECT bean from SBigDataDemandSign bean where  bean.bigdataId=:bigdataId ")
                .setParameter("bigdataId", id);
        SBigDataDemandSign sBigDataDemandSign = (SBigDataDemandSign) query.uniqueResult();
        return sBigDataDemandSign;
    }


    public Pagination getSearchBigdataNews(Integer[] siteIds, Integer[] typeIds, Boolean titleImg, Boolean recommend, String title,
                                     Map<String, String[]> attr, int orderBy, int pageNo, int pageSize, String[] params) {
        StringBuffer f = new StringBuffer("select * from s_bigdata_news_up s where 1=1");
        if(params.length>0){
            if(params[0] != null && (params[0].trim()).length()>0 && !("undefined".equals(params[0])) ) {
                String newsName = params[0];
                f.append(" and s.NEWS_NAME like '%"+newsName+"%' ");
            }
        }
        switch (orderBy) {
            default:
                f.append(" order by s.BIGDATA_ID desc");
        }
        SQLQuery sqlQueryl = getSession().createSQLQuery(f.toString());
        sqlQueryl.addEntity(SBigDataNews.class);
        List listl = sqlQueryl.list();

        int totalCount=  listl.size();
        Pagination p = new Pagination(pageNo, pageSize, totalCount);
        //当存在查询条件的情况下把查询条件返回前台提供下次查询使用
        /**
         *把查询条件返回前台
         */
        if(params.length>0) {
            if(params[0] != null && params[0].length() > 0){
                p.setSearchOne(params[0]);
            }
        }
        if (totalCount < 1) {
            p.setList(new ArrayList());
            return p;
        }
        SQLQuery sqlQuery = getSession().createSQLQuery(f.toString());
        sqlQuery.addEntity(SBigDataNews.class);
        sqlQuery.setFirstResult(p.getFirstResult());
        sqlQuery.setMaxResults(p.getPageSize());
        List list = sqlQuery.list();
        p.setList(list);

        return p;

    }

    public Pagination getSearchBigdataPolicy(Integer[] siteIds, Integer[] typeIds, Boolean titleImg, Boolean recommend, String title,
                                           Map<String, String[]> attr, int orderBy, int pageNo, int pageSize, String[] params) {
        StringBuffer f = new StringBuffer("select * from s_bigdata_policy_up s where 1=1");
        if(params.length>0){
            if(params[0] != null && (params[0].trim()).length()>0 && !("undefined".equals(params[0])) ) {
                String policyName = params[0];
                f.append(" and s.POLICY_NAME like '%"+policyName+"%' ");
            }
        }
        switch (orderBy) {
            default:
                f.append(" order by s.BIGDATA_ID desc");
        }
        SQLQuery sqlQueryl = getSession().createSQLQuery(f.toString());
        sqlQueryl.addEntity(SBigDataPolicy.class);
        List listl = sqlQueryl.list();

        int totalCount=  listl.size();
        Pagination p = new Pagination(pageNo, pageSize, totalCount);
        //当存在查询条件的情况下把查询条件返回前台提供下次查询使用
        /**
         *把查询条件返回前台
         */
        if(params.length>0) {
            if(params[0] != null && params[0].length() > 0){
                p.setSearchOne(params[0]);
            }
        }
        if (totalCount < 1) {
            p.setList(new ArrayList());
            return p;
        }
        SQLQuery sqlQuery = getSession().createSQLQuery(f.toString());
        sqlQuery.addEntity(SBigDataPolicy.class);
        sqlQuery.setFirstResult(p.getFirstResult());
        sqlQuery.setMaxResults(p.getPageSize());
        List list = sqlQuery.list();
        p.setList(list);

        return p;
    }





    public SBigDataNews findNewsById(Integer id) {
        Query query = getSession().createQuery("SELECT bean from SBigDataNews bean where  bean.bigdataId=:bigdataId ")
                .setParameter("bigdataId", id);
        SBigDataNews sBigDataNews = (SBigDataNews) query.uniqueResult();
        return sBigDataNews;
    }

    public SBigDataPolicy findPolicyById(Integer id) {
        Query query = getSession().createQuery("SELECT bean from SBigDataPolicy bean where  bean.bigdataId=:bigdataId ")
                .setParameter("bigdataId", id);
        SBigDataPolicy sBigDataPolicy = (SBigDataPolicy) query.uniqueResult();
        return sBigDataPolicy;
    }


    public void deleteNewsById(Integer id){
        StringBuffer hql = new StringBuffer();
        hql.append(" delete SBigDataNews as sd where sd.bigdataId = " + id);
        Query query = getSession().createQuery(hql.toString());
        query.executeUpdate(); // 必须加此句否则不会执行删除操作
    }

    public void deletePolicyById(Integer id){
        StringBuffer hql = new StringBuffer();
        hql.append(" delete SBigDataPolicy as sd where sd.bigdataId = " + id);
        Query query = getSession().createQuery(hql.toString());
        query.executeUpdate(); // 必须加此句否则不会执行删除操作
    }




    public Pagination getContactAddressList(Integer siteId, CmsUser user, int pageNo, int pageSize, String defAddr, String paramu,Integer userId) {
        Finder f = Finder.create("select bean from SMemberAddress bean where bean.userId=:userId");
        f.setParam("userId", userId);
        f.append(" order by bean.addrId desc");
        f.setCacheable(true);
        return find(f, pageNo, pageSize);
    }
//    public SMemberAddress deleteById(Integer addrId) {
//        SMemberAddress bean = findById(addrId);
//        getSession().delete(bean);
//        return bean;
//    }
//    public SMemberAddress findById(Integer addrId) {
//        SMemberAddress bean = get(addrId);
//        return bean;
//    }



    public void deleteContactAddress(Integer addrId){
        StringBuffer hql = new StringBuffer();
//        hql.append(" delete from SRepairDemandEntity sd, SRepairDemandObjEntity sdo where sd." + repairId + "= sdo." + repairId);
        hql.append(" delete SMemberAddress as sd where sd.addrId = " + addrId);
        Query query = getSession().createQuery(hql.toString());
        query.executeUpdate(); // 必须加此句否则不会执行删除操作
    }
    public void deleteSunionCity(Integer unionId){
        StringBuffer hql = new StringBuffer();
//        hql.append(" delete from SRepairDemandEntity sd, SRepairDemandObjEntity sdo where sd." + repairId + "= sdo." + repairId);
        hql.append(" delete SUnionCity as sd where sd.unionId = " + unionId);
        Query query = getSession().createQuery(hql.toString());
        query.executeUpdate(); // 必须加此句否则不会执行删除操作
    }
}
