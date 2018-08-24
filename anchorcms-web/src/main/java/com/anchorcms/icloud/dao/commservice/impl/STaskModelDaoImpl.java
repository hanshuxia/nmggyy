package com.anchorcms.icloud.dao.commservice.impl;

import com.anchorcms.common.hibernate.Finder;
import com.anchorcms.common.hibernate.HibernateBaseDao;
import com.anchorcms.icloud.dao.commservice.STaskModelDao;
import com.anchorcms.icloud.model.commservice.STaskModel;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @anther 李利民
 * @descript
 * @data 2017/1/1217:52
 */
@Repository
public class STaskModelDaoImpl extends HibernateBaseDao<STaskModel, Integer> implements STaskModelDao {

    public STaskModel save(STaskModel stakeModel) {
        getSession().save(stakeModel);
        return stakeModel;
    }

    public List<STaskModel> getById(Integer taskId) {
        Finder finder = Finder.create("select bean from STaskModel bean where 1=1 and bean.taskId=:taskId");
        finder.setParam("taskId",taskId);
        Query query = getSession().createQuery(finder.getOrigHql());
        finder.setParamsToQuery(query);
        List<STaskModel> list = query.list();
        return list;
    }

    public void delete(STaskModel taskModel) {

    getSession().delete(taskModel);
    }

    /**
     * 或得当前用户填写的数据
     *
     * @param taskId
     * @param userId
     * @return
     */
    public List getByIdUserId(Integer taskId, Integer userId) {
        Finder f = Finder.create("select distinct(attr) from STaskModel bean,STask_attr attr where 1=1");
        f.append( "and bean.taskId=:taskId");
        f.setParam("taskId",taskId);
        f.append(" and attr.user_id =:user_id");
        f.setParam("user_id",userId);
        f.append(" and bean.taskModelId = attr.task_model_id ORDER BY GROUP_ID,TASK_ATTR_ID");

        Query query = getSession().createQuery(f.getOrigHql());
        f.setParamsToQuery(query);
        List list = query.list();
        return list;
    }

    /**
     * 获得Dao对于的实体类
     *
     * @return
     */
    @Override
    protected Class<STaskModel> getEntityClass() {
        return STaskModel.class;
    }


}
