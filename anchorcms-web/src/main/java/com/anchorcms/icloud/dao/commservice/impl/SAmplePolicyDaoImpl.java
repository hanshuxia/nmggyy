package com.anchorcms.icloud.dao.commservice.impl;


import com.anchorcms.common.hibernate.Finder;
import com.anchorcms.common.hibernate.HibernateBaseDao;
import com.anchorcms.icloud.dao.commservice.SAmplePolicyDao;
import com.anchorcms.icloud.dao.commservice.SBidNoticeDao;
import com.anchorcms.icloud.model.commservice.SAmplePolicy;
import com.anchorcms.icloud.model.commservice.SBidNotice;
import com.anchorcms.icloud.model.supplychain.SSpareDemand;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by hansx on 2017/1/13 0013.
 * 惠补政策dao实现类
 */
@Repository
public class SAmplePolicyDaoImpl extends
        HibernateBaseDao<SAmplePolicy, Integer> implements SAmplePolicyDao {


    public SAmplePolicy findById(int id) {
        SAmplePolicy entity = get(id);
        return entity;
    }

    public List<SAmplePolicy> getSpareDemandResList(Integer count, Integer orderBy, Integer listType){
        Finder f = Finder.create(" select  bean from SAmplePolicy bean where bean.selectedStatus='2' ");
        if(orderBy!=null){
            f.append(" order by bean.releaseDt DESC ");
        }
        f.setCacheable(true);
        if(count!=null){
            f.setMaxResults(count);
        }
        return find(f);
    }

    @Override
    protected Class<SAmplePolicy> getEntityClass() {
        return SAmplePolicy.class;
    }


}