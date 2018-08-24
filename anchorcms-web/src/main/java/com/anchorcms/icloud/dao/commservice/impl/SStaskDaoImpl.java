package com.anchorcms.icloud.dao.commservice.impl;

import com.anchorcms.common.hibernate.Finder;
import com.anchorcms.common.hibernate.HibernateBaseDao;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.dao.commservice.SStaskDao;
import com.anchorcms.icloud.model.commservice.STask;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public class SStaskDaoImpl extends HibernateBaseDao<STask, Integer> implements SStaskDao {
    /**
     * @Author 闫浩芮
     * 查询需求列表
     * @Date 2016/12/23 0023 15:50
     */
    public Pagination getPageBySelf(int pageNo,int pageSize,Date startDt,Date deadlineDt,String taskName,String status,CmsUser user) {
      Finder f = Finder.create("select bean from STask bean where 1=1 and bean.user.company=:company ");
        f.setParam("company",user.getCompany());
        appendQuery(f,taskName,status,startDt,deadlineDt);
        f.append(" order by bean.taskId desc");
        f.setCacheable(true);
        return find(f, pageNo, pageSize);
    }

    /***
     * @author Yhr
     * @date 2016/12/22
     * @param f
     * @desc 查询条件
     */
    private void appendQuery(Finder f, String taskName,String status,Date startDt,Date deadlineDt ) {
        if (startDt!= null && !"".equals(startDt)) {
            f.append(" and bean.startDt >=:startDt");
            f.setParam("startDt", startDt);
        }
        if (deadlineDt != null && !"".equals(deadlineDt)) {
            f.append(" and bean.startDt <=:deadlineDt");
            f.setParam("deadlineDt", deadlineDt);
        }
        if (taskName != null && !"".equals(taskName)) {
            f.append(" and bean.taskName like :taskName ");
            f.setParam("taskName", "%"+taskName+"%");
        }
        if(status !=null && !"".equals(status)){
            switch (Integer.parseInt(status)){
                case 1:
                    f.append( " and bean.status in ('1','2')");
                    break;
                case 0:
                    f.append( " and bean.status =:status");
                    f.setParam("status",status);
                    break;
                default:
            }
        }
    }
    /**
     * @Author 闫浩芮
     * 根据demandId查找
     * @Date 2016/12/28 0028 9:08
     */
    public STask findById(Integer taskId){
        return get(taskId);
    }

    public STask delete(STask sTask) {
        if(sTask !=null){
            getSession().delete(sTask);
        }
        return sTask;
    }

    @Override
    protected Class<STask> getEntityClass() {
        return STask.class;
    }
}
