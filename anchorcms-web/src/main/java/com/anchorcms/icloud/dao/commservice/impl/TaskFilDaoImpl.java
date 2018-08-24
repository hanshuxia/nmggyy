package com.anchorcms.icloud.dao.commservice.impl;

import com.anchorcms.common.hibernate.Finder;
import com.anchorcms.common.hibernate.HibernateBaseDao;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.dao.commservice.TaskFilDao;
import com.anchorcms.icloud.model.commservice.STaskFill;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @anther 李利民
 * @descript
 * @data 2017/1/1314:25
 */
@Repository
public class TaskFilDaoImpl extends HibernateBaseDao<STaskFill, Integer> implements TaskFilDao {
    public STaskFill save(STaskFill sd) {
        getSession().save(sd);
        return sd;
    }

    public STaskFill getTasKFileById(Integer userId, int taskId) {
        Finder f = Finder.create("select  bean from STaskFill bean");
        f.append(" where 1 = 1 and bean.user.userId =:createrId");
        f.setParam("createrId",userId);
        f.append(" and bean.sTaskList.taskId=:taskId");
        f.setParam("taskId",taskId);
        Query query = getSession().createQuery(f.getOrigHql());
        f.setParamsToQuery(query);
        if (f.isCacheable()) {
            query.setCacheable(true);
        }
        List list = query.list();
        if(list.size()==0){
            return null;
        }else {
            return (STaskFill) (list.get(0));
        }
    }

    /**
     * 获取已经上报的数据
     * @param pageNo
     * @param pageSize
     * @param startDt
     * @param deadlineDt
     * @param taskName
     * @param status
     * @return
     */
    public Pagination getPageByTaskFIle(int pageNo, int pageSize, Date startDt, Date deadlineDt, String taskName, String status,CmsUser user) {
        Finder f = Finder.create("select bean from STaskFill bean where 1=1 and bean.user.company=:company");
        f.setParam("company",user.getCompany());
        if(taskName!=null && !"".equals(taskName)){
            f.append(" and bean.sTaskList.taskName like:taskName");
            f.setParam("taskName","%"+taskName+"%");
        }
        if(startDt!=null && !"".equals(startDt)){
            f.append(" and bean.sTaskList.startDt >=:startDt");
            f.setParam("startDt",startDt);
        }
        if(deadlineDt!=null && !"".equals(deadlineDt)){
            f.append(" and bean.sTaskList.startDt <=:deadlineDt");
            f.setParam("deadlineDt",deadlineDt);
        }
        f.append(" order by bean.taskFillId desc");

        return find(f, pageNo, pageSize);
    }
    public Pagination getPageByTaskFIle2(int pageNo, int pageSize, Date startDt, Date deadlineDt, String taskName, String status,CmsUser user) {
        Finder f = Finder.create("from STask bean where  bean.status in ('1','2') and bean.user.company=:company ");
        f.setParam("company",user.getCompany());
        appendQuery(f,taskName, startDt,deadlineDt,status);
        f.append(" order by bean.taskId desc");
        return find(f, pageNo, pageSize);
    }

    private void appendQuery(Finder f,  String taskName, Date startDt, Date deadlineDt,String status) {
        if(taskName!=null && !"".equals(taskName)){
            f.append(" and bean.taskName like:taskName");
            f.setParam("taskName","%"+taskName+"%");
        }
        if(startDt!=null && !"".equals(startDt)){
            f.append(" and bean.startDt >=:startDt");
            f.setParam("startDt",startDt);
        }
        if(deadlineDt!=null && !"".equals(deadlineDt)){
            f.append(" and bean.deadlineDt >=:deadlineDt");
            f.setParam("deadlineDt",deadlineDt);
        }
        if (status != null && !"".equals(status)) {
            f.append(" and bean.status =:status");
            f.setParam("status", status);
        }
    }

    public List getByTaskId(Integer taskId) {
        Finder f = Finder.create("select bean from STaskFill bean where 1=1");
        f.append(" and bean.sTaskList.taskId=:taskId");
        f.setParam("taskId",taskId);
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
    protected Class<STaskFill> getEntityClass() {
        return STaskFill.class;
    }
}
