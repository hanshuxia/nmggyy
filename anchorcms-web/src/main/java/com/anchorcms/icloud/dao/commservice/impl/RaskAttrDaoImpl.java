package com.anchorcms.icloud.dao.commservice.impl;

import com.anchorcms.common.hibernate.HibernateBaseDao;
import com.anchorcms.icloud.dao.commservice.RaskAttrDao;
import com.anchorcms.icloud.model.commservice.SAmplePolicy;
import com.anchorcms.icloud.model.commservice.STask_attr;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2017/2/13.
 */
@Repository
public  class RaskAttrDaoImpl extends HibernateBaseDao<SAmplePolicy,Integer> implements RaskAttrDao {

    public STask_attr save(STask_attr task_attr) {
        getSession().save(task_attr);
        return task_attr;
    }
    @Override
    protected Class<SAmplePolicy> getEntityClass() {
        return null;
    }

}
