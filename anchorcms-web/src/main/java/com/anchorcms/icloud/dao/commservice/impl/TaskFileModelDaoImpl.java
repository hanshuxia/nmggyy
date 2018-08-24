package com.anchorcms.icloud.dao.commservice.impl;

import com.anchorcms.common.hibernate.HibernateBaseDao;
import com.anchorcms.icloud.dao.commservice.TaskFilDao;
import com.anchorcms.icloud.dao.commservice.TaskFileModelDao;
import com.anchorcms.icloud.model.commservice.STaskFill;
import com.anchorcms.icloud.model.commservice.STaskFillModel;
import org.springframework.stereotype.Repository;

/**
 * @anther 李利民
 * @descript
 * @data 2017/1/1315:36
 */
@Repository
public class TaskFileModelDaoImpl  extends HibernateBaseDao<STaskFillModel, Integer> implements TaskFileModelDao {
    public STaskFillModel save(STaskFillModel stakeModel) {
       getSession().save(stakeModel);
        return stakeModel;
    }
    /**
     * 获得Dao对于的实体类
     *
     * @return
     */
    @Override
    protected Class<STaskFillModel> getEntityClass() {
        return STaskFillModel.class;
    }


}
