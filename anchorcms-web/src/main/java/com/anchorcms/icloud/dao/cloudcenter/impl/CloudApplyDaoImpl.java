package com.anchorcms.icloud.dao.cloudcenter.impl;

import com.anchorcms.common.hibernate.Finder;
import com.anchorcms.common.hibernate.HibernateBaseDao;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.dao.cloudcenter.CloudApplyDao;
import com.anchorcms.icloud.model.cloudcenter.SIcloudApply;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * @anther 李利民
 * @descript
 * @data 2017/1/711:58
 */
@Repository
public class CloudApplyDaoImpl  extends HibernateBaseDao<SIcloudApply, Integer> implements CloudApplyDao {
    public SIcloudApply save(SIcloudApply bean) {
        getSession().save(bean);
        return bean;
    }

    /**
     * @auther lim
     * @deprecated 获得政策信息
     * @param siteId
     * @param user
     * @param pageNo
     * @param pageSize
     * @return
     */
    public Pagination getPage(Integer siteId, CmsUser user, int pageNo, int pageSize) {
        Finder f = Finder.create("select bean from SIcloudApply bean where 1=1 ");
        f.append("  and bean.user.userId =:userId ");
        f.setParam("userId",user.getUserId());
        // 更新时间降序
        f.append(" order by bean.createDt desc,bean.applyId desc");
        f.setCacheable(true);
        int totalCount = countQueryResult(f);
        Pagination p = new Pagination(pageNo, pageSize, totalCount);
        if (totalCount < 1) {
            p.setList(new ArrayList());
            return p;
        }
        Query query = getSession().createQuery(f.getOrigHql());
        f.setParamsToQuery(query);
        query.setFirstResult(p.getFirstResult());
        query.setMaxResults(p.getPageSize());
        if (f.isCacheable()) {
            query.setCacheable(true);
        }
        List list = query.list();
        p.setList(list);
        return p;
    }


    public SIcloudApply update(SIcloudApply bean) {
        getSession().update(bean);
        return bean;
    }

    public SIcloudApply findById(Integer id) {
        SIcloudApply bean = get(id);
        return bean;
    }

    public SIcloudApply deleteById(Integer id) {
        SIcloudApply bean = findById(id);
        getSession().delete(bean);
        return bean;
    }

    /**
     * @author ly
     * @date 2017/1/7 16:12
     * @desc 获取申请反馈列表-管理员
     **/
    public Pagination getFeedbackPage(Integer siteId, CmsUser user, int pageNo, int pageSize,
                                      Date startDate, Date endDate, String applierName, String policyLevel,
                                      String policyName, String policyCode, String state) {
        Finder f = Finder.create("select  bean from SIcloudApply bean");
        f.append(" where 1 = 1 ");
        if (state != null && !"".equals(state)){//全部
            f.append(" and bean.state =:state");
            f.setParam("state", state);
        }
        if (startDate != null) {
            f.append(" and bean.createDt >=:startDate");
            f.setParam("startDate", startDate);
        }
        if (endDate != null) {
            f.append(" and bean.createDt <=:endDate");
            f.setParam("endDate", endDate);
        }
        if (applierName != null && !"".equals(applierName)) {
            f.append(" and bean.user.username like :applierName");
            f.setParam("applierName", "%" +applierName + "%");
        }
        if (policyLevel != null && !"".equals(policyLevel)) {
            f.append(" and bean.policy.policyLevel like :policyLevel");
            f.setParam("policyLevel", "%" +policyLevel + "%");
        }
        if (policyName != null && !"".equals(policyName)) {
            f.append(" and bean.policy.policyName like :policyName");
            f.setParam("policyName", "%" +policyName + "%");
        }
        if (policyCode != null && !"".equals(policyCode)) {
            f.append(" and bean.policy.policyNumber like :policyCode");
            f.setParam("policyCode", "%" +policyCode + "%");
        }
        f.append(" order by bean.applyId desc");
        f.setCacheable(true);
        return find(f, pageNo, pageSize);
    }

    /**
     * @param siteId
     * @param user
     * @param pageNo
     * @param pageSize
     * @param startDate
     * @param endDate
     * @param applierName
     * @param policyLevel
     * @param policyName
     * @param policyCode
     * @param state
     * @anther wanjinyou
     * @descript 政策申请跟踪
     * @data 2017/1/12
     */
    public Pagination getFollowbackPage(Integer siteId, CmsUser user, int pageNo, int pageSize, Date startDate, Date endDate, String applierName, String policyLevel, String policyName, String policyCode, String state,String userName) {
        Finder f = Finder.create("select  bean from SIcloudApply bean");
        f.append(" where 1 = 1 and bean.user.username =:userName ");
        f.setParam("userName", ""+userName+"" );
        if (state != null && !"".equals(state)){//全部
            f.append(" and bean.state =:state");
            f.setParam("state", state);
        }
        if (startDate != null) {
            f.append(" and bean.createDt >=:startDate");
            f.setParam("startDate", startDate);
        }
        if (endDate != null) {
            f.append(" and bean.createDt <=:endDate");
            f.setParam("endDate", endDate);
        }
        if (applierName != null && !"".equals(applierName)) {
            f.append(" and bean.user.username like :applierName");
            f.setParam("applierName", "%" +applierName + "%");
        }
        if (policyLevel != null && !"".equals(policyLevel)) {
            f.append(" and bean.policy.policyLevel like :policyLevel");
            f.setParam("policyLevel", "%" +policyLevel + "%");
        }
        if (policyName != null && !"".equals(policyName)) {
            f.append(" and bean.policy.policyName like :policyName");
            f.setParam("policyName", "%" +policyName + "%");
        }
        if (policyCode != null && !"".equals(policyCode)) {
            f.append(" and bean.policy.policyNumber like :policyCode");
            f.setParam("policyCode", "%" +policyCode + "%");
        }
        f.append(" order by bean.applyId desc");
        f.setCacheable(true);
        return find(f, pageNo, pageSize);
    }

    /**
     * @Author 闫浩芮
     * 批量报送、置为通过
     * @Date 2017/2/17 0017 18:45
     */
    public int passMany(String demandIdsStr,String state) {
        if("0".equals(state)){
            Query query = getSession().createQuery("update SIcloudApply bean set bean.state='2'" +
                    "where bean.applyId in (" + demandIdsStr + ")");
            return query.executeUpdate();
        }else{
            Query query = getSession().createQuery("update SIcloudApply bean set bean.state='3'" +
                    "where bean.applyId in (" + demandIdsStr + ")");
            return query.executeUpdate();
        }

    }
    /**
     * @Author 闫浩芮
     * 置为驳回
     * @Date 2017/2/17 0017 18:45
     */
    public int rejectMany(String demandIdsStr,String backReason) {
        demandIdsStr = demandIdsStr.replace(",","','");
        Query query = getSession().createQuery("update SIcloudApply bean set bean.state='1',bean.backReason='"+backReason+"' where bean.applyId in ('"+demandIdsStr+"') ");
        return query.executeUpdate();
    }
    /**
     * @Author 闫浩芮
     * 根据Ids查询出多条信息
     * @Date 2017/2/22 0022 15:16
     */

    public List<SIcloudApply> byIds(String ids) {
        Query query = getSession().createQuery("SELECT bean from SIcloudApply bean where  bean.applyId in ("+ ids +") ");
        List<SIcloudApply> sIcloudApply = query.list();
        return sIcloudApply;
    }

    /**
     * 获得Dao对于的实体类
     *
     * @return
     */
    @Override
    protected Class<SIcloudApply> getEntityClass() {
        return SIcloudApply.class;
    }
}
