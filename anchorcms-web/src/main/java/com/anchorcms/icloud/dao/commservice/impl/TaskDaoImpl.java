package com.anchorcms.icloud.dao.commservice.impl;

import com.anchorcms.common.hibernate.HibernateBaseDao;
import com.anchorcms.icloud.dao.commservice.TaskDao;
import com.anchorcms.icloud.model.commservice.STask;
import org.springframework.stereotype.Repository;

/**
 * @anther 李利民
 * @descript
 * @data 2017/1/1216:49
 */
@Repository
public class TaskDaoImpl extends HibernateBaseDao<STask, Integer> implements TaskDao {


    public STask save(STask sd) {
      getSession().save(sd);
        return sd;
    }

    public STask getByTaskId(Integer taskId) {
        return get(taskId);
    }

    public STask Update(STask sTask) {
        getSession().update(sTask);
        return sTask;
    }

    /**
     * 获得Dao对于的实体类
     *
     * @return
     */
    @Override
    protected Class<STask> getEntityClass() {
        return STask.class;
    }
}
