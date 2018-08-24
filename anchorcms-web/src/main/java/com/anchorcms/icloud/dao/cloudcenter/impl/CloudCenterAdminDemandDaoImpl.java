package com.anchorcms.icloud.dao.cloudcenter.impl;

import com.anchorcms.cms.model.main.Content;
import com.anchorcms.common.hibernate.Finder;
import com.anchorcms.common.hibernate.HibernateBaseDao;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.icloud.dao.cloudcenter.CloudCenterAdminDemandDao;
import com.anchorcms.icloud.model.cloudcenter.SIcloudDemand;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public class CloudCenterAdminDemandDaoImpl extends HibernateBaseDao<SIcloudDemand, Integer> implements CloudCenterAdminDemandDao {
    /**
     * @param demandId 询价编号
     * @param demandName 需求名称
     * @param startDate 发布开始时间
     * @param endDate 发布结束时间
     * @param serverType 服务器分类
     * @param status 标签状态
     * @return
     * @author maocheng
     * @description 管理员--云需求管理界面列表
     */
    public Pagination getPageBySelf(Object o, Integer memberId, boolean b, boolean b1, Content.ContentStatus all,
                                    Object o1, Integer siteId, Integer modelId, Integer channelId, int i,
                                    int pageNo, int pageSize, String demandId, String demandName,
                                    Date startDate, Date endDate, String serverType, String status) {
        Finder f = Finder.create("select  bean from SIcloudDemand bean");
        f.append(" where 1=1 and (bean.status=2 or bean.status=3 or bean.status=0)");
        if (status != null && !"".equals(status)) {
            f.append(" and bean.status =:status");
            f.setParam("status", status);
        }
        //添加查询条件
        appendQuery(f, demandId,demandName,startDate,endDate,serverType);
        f.append(" order by bean.demandId desc");
        f.setCacheable(true);
        return find(f, pageNo, pageSize);
    }

    /**
     * @Author 闫浩芮
     * 置为通过
     * @Date 2017/2/17 0017 18:45
     */
    public int passMany(String demandIdsStr) {
        java.sql.Date now = new java.sql.Date(System.currentTimeMillis());
        Query query = getSession().createQuery("update SIcloudDemand bean set bean.status='3', bean.releaseDt=(:releaseDt)" +
                "where bean.demandId in (" + demandIdsStr + ")").setParameter("releaseDt",now);
        return query.executeUpdate();
    }
    /**
     * @Author 闫浩芮
     * 置为驳回
     * @Date 2017/2/17 0017 18:45
     */
    public int rejectMany(String demandIdsStr,String bacReason) {
        Query query = getSession().createQuery("update SIcloudDemand bean set bean.status='0',bean.backReason='" +bacReason+ "' where bean.demandId in (" + demandIdsStr + ")");
        return query.executeUpdate();
    }

    /***
     * @author maocheng
     * @date 2017/1/4
     * @param f
     * @param demandId 查询询价编号
     * @param demandName 查询需求名称
     * @param startDate 查询开始时间
     * @param endDate 查询结束时间
     * @param serverType 查询服务器分类
     * @desc 查询条件
     */
    private void appendQuery(Finder f, String demandId, String demandName, Date startDate, Date endDate, String serverType) {
        if (demandId != null && !"".equals(demandId)) {
            f.append(" and cast(bean.demandId as string) like :demandId");
            f.setParam("demandId", demandId);
        }
        if (demandName != null && !"".equals(demandName)) {
            f.append(" and bean.demandName like :demandName");
            f.setParam("demandName", "%"+demandName+"%");
        }
        if (startDate != null) {
            f.append(" and bean.createDt >=:startDate");
            f.setParam("startDate", startDate);
        }
        if (endDate != null) {
            f.append(" and bean.createDt <=:endDate");
            f.setParam("endDate", endDate);
        }
        if (serverType != null && !"".equals(serverType)) {
            f.append(" and bean.serverType like :serverType");
            f.setParam("serverType", "%" + serverType + "%");
        }
    }

    @Override
    protected Class<SIcloudDemand> getEntityClass() {
        return SIcloudDemand.class;
    }
}
