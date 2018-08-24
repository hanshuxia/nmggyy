package com.anchorcms.icloud.dao.commservice.impl;

import com.anchorcms.common.hibernate.HibernateBaseDao;
import com.anchorcms.icloud.dao.commservice.SPolicyDao;
import com.anchorcms.icloud.model.commservice.SAmplePolicy;
import org.springframework.stereotype.Repository;

/**
 * @Author lisong
 * @Date 2017/1/16 11:19
 *惠补政策添加
 */
@Repository
public class SpolicyDaoImpl extends HibernateBaseDao<SAmplePolicy, Integer> implements SPolicyDao {
    public void insert(SAmplePolicy sAmplePolicy) {
        getSession().save(sAmplePolicy);
    }

    @Override
    protected Class<SAmplePolicy> getEntityClass() {
        return null;
    }
}
