package com.anchorcms.icloud.dao.cloudcenter.impl;
import com.anchorcms.common.hibernate.HibernateBaseDao;
import com.anchorcms.icloud.dao.cloudcenter.CloudDemandDao;
import com.anchorcms.icloud.model.cloudcenter.SIcloudDemand;
import org.springframework.stereotype.Repository;

/**
 * @anther 李利民
 * @descript
 * @data 2017/1/316:02
 */

@Repository
public class CloudDemandDaoImpl extends HibernateBaseDao<SIcloudDemand, Integer> implements CloudDemandDao {

    public SIcloudDemand save(SIcloudDemand bean) {
        getSession().save(bean);
        return bean;
    }
    /**
     * 获得Dao对于的实体类
     *
     * @return
     */
    @Override
    protected Class<SIcloudDemand> getEntityClass() {
        return SIcloudDemand.class;
    }
}
