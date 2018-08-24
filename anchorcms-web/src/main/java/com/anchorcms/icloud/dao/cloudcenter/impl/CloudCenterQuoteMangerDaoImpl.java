package com.anchorcms.icloud.dao.cloudcenter.impl;

import com.anchorcms.common.hibernate.Finder;
import com.anchorcms.common.hibernate.HibernateBaseDao;
import com.anchorcms.icloud.dao.cloudcenter.CloudCenterQuoteMangerDao;
import com.anchorcms.icloud.model.cloudcenter.SIcloudDemand;
import com.anchorcms.icloud.model.cloudcenter.SIcloudManage;
import com.anchorcms.icloud.model.cloudcenter.SIcloudQuoteManage;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @anther 李利民
 * @descript
 * @data 2017/1/1115:42
 */
@Repository
public class CloudCenterQuoteMangerDaoImpl extends HibernateBaseDao<SIcloudQuoteManage, Integer> implements CloudCenterQuoteMangerDao {


    public void save(SIcloudQuoteManage manager) {
        getSession().save(manager);
    }

    /**
     * @param id
     * @Author wanjinyou
     * @Desc 我是需求--需求订单管理--更新状态
     * @Date 2017/1/12
     */
    public SIcloudQuoteManage byDemandId(Integer id) {
        SIcloudQuoteManage quoteManage = get(id);
        return quoteManage;
    }

    /**
     * @param id
     * @Author wanjinyou
     * @Desc 报价管理表--明细
     * @Date 2017/1/13
     */
    public SIcloudQuoteManage findById(Integer id) {
        SIcloudQuoteManage bean = get(id);
        return  bean;
    }

    /**
     * @param id
     * @Author wanjinyou
     * @Desc 报价管理表--删除
     * @Date 2017/1/13
     */
    public SIcloudQuoteManage deleteById(Integer id) {
        SIcloudQuoteManage bean = get(id);
        getSession().delete(bean);
        return bean;
    }

    public SIcloudQuoteManage update(SIcloudQuoteManage quoteManage) {
        getSession().update(quoteManage);
        return quoteManage;
    }


    public SIcloudQuoteManage byDemand(Integer id) {
        Finder f = Finder.create("select bean from SIcloudQuoteManage bean  ");
        f.append( " where 1=1 and bean.demand.demandId="+id+"");
        f.setCacheable(true);
        Query query = getSession().createQuery(f.getOrigHql());
        List list = query.list();
        return (SIcloudQuoteManage) list.get(0);
    }

    /**
     * 根据demandID查询订单数
     *
     * @param demandId
     * @return
     */
    public Integer countByDemandId(Integer demandId) {
        Finder f = Finder.create("select bean from SIcloudQuoteManage bean  ");
        f.append( " where bean.demand.demandId="+demandId);
        return super.countQueryResult(f);
    }

    @Override
    protected Class<SIcloudQuoteManage> getEntityClass() {
        return SIcloudQuoteManage.class;
    }
}
