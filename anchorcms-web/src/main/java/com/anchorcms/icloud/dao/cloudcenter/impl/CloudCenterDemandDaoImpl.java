package com.anchorcms.icloud.dao.cloudcenter.impl;

import com.anchorcms.common.hibernate.Finder;
import com.anchorcms.common.hibernate.HibernateBaseDao;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.icloud.dao.cloudcenter.CloudCenterDemandDao;
import com.anchorcms.icloud.model.cloudcenter.SIcloudDemand;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @Author 姜舒中
 * @Email netuser.orz@icloud.com
 * @Date 2017/1/3 0003
 * @Desc
 */
@Repository
public class CloudCenterDemandDaoImpl extends HibernateBaseDao<SIcloudDemand, Integer> implements CloudCenterDemandDao {
    public Pagination getPageBySelf(Integer channelId, Integer siteId, Integer modelId, Integer UserId, Integer memberId, int pageNo, int pageSize, Date releaseDt, Date deadlineDt, Integer demandId, String demandName, String status, String payType) {
        Finder f = Finder.create("select bean from SIcloudDemand bean where 1=1 and createrId =:createrId");
        f.setParam("createrId", ""+UserId+"" );
        //添加查询条件
        appendQuery(f,demandName,status);
        f.append(" order by bean.updateDt desc,bean.demandId desc");
        f.setCacheable(true);
        return find(f, pageNo, pageSize);
    }

    /***
     * @author jiangshuzhong
     * @date 2017/1/3
     * @param f
     * @desc 查询条件
     */
    private void appendQuery(Finder f, String demandName,String status ) {

        if (demandName != null && !"".equals(demandName)) {
            f.append(" and bean.demandName like :demandName");
            f.setParam("demandName", "%"+demandName+"%" );
        }

        if(status !=null && !"".equals(status)){
            //当状态为6 的时候为已经截止时间
            if("6".equals(status)){
                f.append(" and (bean.status ='3' or bean.status='2') and DATEDIFF(bean.deadlineDt,now())<0 ");
                return;
            }
            f.append(" and bean.status =:status");
            f.setParam("status", status);
            //当状态为0:被驳回，1：草稿，，2：待审批，3： 通过，4，优选，进行过滤，5：已下单，6：已截止，7：已关闭
            if(status.equals("2") || status.equals("3")){
                f.append(" and DATEDIFF(bean.deadlineDt,now())>=0");
            }
        }

    }
    /**
     * @Author jiangshuzhong
     * @param demandId
     * @Email netuser.orz@icloud.com
     * @Date 2016/1/5
     * @Desc 通过ID获取需求信息
     */
    public SIcloudDemand bySIcloudDemandId(Integer demandId) {
        SIcloudDemand sIcloudDemand = get(demandId);
        return sIcloudDemand;
    }

    /**
     * 更新数据
     *
     * @param SIcloudDemand
     * @return
     */
    public SIcloudDemand update(SIcloudDemand SIcloudDemand) {
        getSession().update(SIcloudDemand);
        return SIcloudDemand;
    }

    /**
     * @param icloudDemand
     * @Author wanjinyou
     * @Date 2017/1/10
     * @Desc 云需求管理列表--编辑保存
     */
    public SIcloudDemand editSave(SIcloudDemand icloudDemand) {
        getSession().update(icloudDemand);
        return icloudDemand;
    }
    /**
     * @param id
     * @Author wanjinyou
     * @Date 2017/1/10
     * @Desc 云需求管理列表--删除
     */
    public SIcloudDemand deleteById(Integer id) {
        SIcloudDemand bean = bySIcloudDemandId(id);
        getSession().delete(bean);
        return bean;
    }
    /**
     * @author jsz
     * @date 2017/1/10
     * @desc 自定义标签——云需求列表
     **/
    public List<SIcloudDemand> getList(Integer count, Integer orderBy, Integer listType,Integer listId) {
        Finder f = Finder.create("select  bean from SIcloudDemand bean");
        f.append( " where 1=1  AND status='3' and DATEDIFF(bean.deadlineDt,now())>=0 ");
        if (listId !=null){
            f.append(" and bean.demandId !=:demandId");
            f.setParam("demandId",listId);
        }
        if (orderBy !=null){
            f.append(" order by bean.createDt desc,bean.demandId desc");
        }
        f.setCacheable(true);
        if (count != null) {
            f.setMaxResults(count);
        }
        return find(f);
    }

    public Integer changeDemandStatus(Integer demandId, String status) {
        if (demandId == null) {
            return 0;
        }
        Query query = getSession().createQuery("update SIcloudDemand bean set bean.status='"+status+"' " +
                "where bean.demandId = "+demandId);
        return query.executeUpdate();
    }

    @Override
    protected Class<SIcloudDemand> getEntityClass() {
        return SIcloudDemand.class;
    }
}
