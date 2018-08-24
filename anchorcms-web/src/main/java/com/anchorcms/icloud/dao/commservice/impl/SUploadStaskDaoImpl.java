package com.anchorcms.icloud.dao.commservice.impl;

import com.anchorcms.common.hibernate.Finder;
import com.anchorcms.common.hibernate.HibernateBaseDao;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.dao.commservice.SUploadStaskDao;
import com.anchorcms.icloud.model.commservice.STask;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class SUploadStaskDaoImpl extends HibernateBaseDao<STask, Integer> implements SUploadStaskDao {
    /**
     * @Author 闫浩芮
     * 查询需求列表
     * @Date 2016/12/23 0023 15:50
     */
    public Pagination getPageBySelf(int pageNo,int pageSize,Date startDt,Date deadlineDt,String taskName,String status,CmsUser user) {

        StringBuffer f = new StringBuffer("select bean.* from s_task bean where   status='1' and bean.COMPANY_ID ='"+user.getCompany().getCompanyId()+"' ");
        StringBuffer fq = new StringBuffer("select bean.* from s_task bean where   status='1' and bean.COMPANY_ID ='"+user.getCompany().getCompanyId()+"' ");
        if(taskName!=null && !"".equals(taskName)){
            f.append(" and bean.task_Name like'%"+taskName+"%'");
            fq.append(" and bean.task_Name like'%"+taskName+"%'");
        }
        if(startDt!=null && !"".equals(startDt)){
            f.append(" and bean.start_Dt >="+startDt+"");
            fq.append(" and bean.start_Dt >="+startDt+"");
        }
        if(deadlineDt!=null && !"".equals(deadlineDt)){
            f.append(" and bean.start_Dt <="+deadlineDt+"");
            fq.append(" and bean.start_Dt <="+deadlineDt+"");

        }
        if(status!=null && "1".equals(status)){
            f.append(" and task_id in ( select a.task_id from s_task_fill a where a.creater_id= '"+user.getUserId()+"')");
            fq.append(" and task_id in ( select a.task_id from s_task_fill a where a.creater_id= '"+user.getUserId()+"')");
        }
        if(status!=null && "2".equals(status)){
            f.append(" and task_id not in ( select a.task_id from s_task_fill a where a.creater_id= '"+user.getUserId()+"')");
            fq.append(" and task_id not in ( select a.task_id from s_task_fill a where a.creater_id= '"+user.getUserId()+"')");
        }
        f.append(" order by bean.task_Id desc");
        fq.append(" order by bean.task_Id desc");
        SQLQuery queryq=getSession().createSQLQuery(fq.toString());
        List<Integer> lis =  queryq.list();
        Pagination p = new Pagination(pageNo, pageSize, lis.size());
        SQLQuery query=getSession().createSQLQuery(f.toString());
        query.setFirstResult(p.getFirstResult());
        query.setMaxResults(p.getPageSize());
        query.addEntity(STask.class);
        List<STask> list= query.list();
        p.setList(list);
        return p;
      /*  return ((Number) query.iterate().next()).intValue()
        int totalCount = countQueryResult(finder);
        Pagination p = new Pagination(pageNo, pageSize, totalCount);
        if (totalCount < 1) {
            p.setList(new ArrayList());
            return p;
        }
        Query query = getSession().createQuery(finder.getOrigHql());
        finder.setParamsToQuery(query);
        query.setFirstResult(p.getFirstResult());
        query.setMaxResults(p.getPageSize());
        if (finder.isCacheable()) {
            query.setCacheable(true);
        }
        List list = query.list();
        p.setList(list);
        return p;*/
     /* Finder f = Finder.create("select bean from STask bean where 1=1 and status='1' and bean.user.company=:company ");
        f.setParam("company",user.getCompany());
        if(taskName!=null && !"".equals(taskName)){
            f.append(" and bean.taskName like:taskName");
            f.setParam("taskName","%"+taskName+"%");
        }
        if(startDt!=null && !"".equals(startDt)){
            f.append(" and bean.startDt >=:startDt");
            f.setParam("startDt",startDt);
        }
        if(deadlineDt!=null && !"".equals(deadlineDt)){
            f.append(" and bean.startDt <=:deadlineDt");
            f.setParam("deadlineDt",deadlineDt);
        }
        if(status!=null && "1".equals(status)){
            f.append(" and task_id in ( select a.task_id from STaskFill a where a.user= :userid) ");
            f.setParam("userid",user);
        }
        f.append(" order by bean.taskId desc");
        f.setCacheable(true);
        return find(f, pageNo, pageSize);*/
    }

    /**
     * @Author 闫浩芮
     * 根据demandId查找
     * @Date 2016/12/28 0028 9:08
     */
    public STask findById(Integer taskId){
        return get(taskId);
    }

    @Override
    protected Class<STask> getEntityClass() {
        return STask.class;
    }
}
