package com.anchorcms.icloud.dao.cloudcenter.impl;

import com.anchorcms.common.hibernate.Finder;
import com.anchorcms.common.hibernate.HibernateBaseDao;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.icloud.dao.cloudcenter.CloudCenterResourceDao;
import com.anchorcms.icloud.model.cloudcenter.SIcloudManage;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @Author 姜舒中
 * @Email netuser.orz@icloud.com
 * @Date 2017/1/7 0007
 * @Desc
 */
@Repository
public class CloudCenterResourceDaoImpl extends HibernateBaseDao<SIcloudManage, Integer> implements CloudCenterResourceDao {
    public Pagination getPageBySelf(Integer channelId, Integer siteId, Integer modelId, Integer UserId, Integer memberId, int pageNo, int pageSize, Date startDt, Date endDt, Integer managerId, String resourceType, String state, String addrCity){
        Finder f = Finder.create("select bean from SIcloudManage bean where 1=1");
        if (state != null && !"".equals(state)) {
            f.append(" and bean.state =:state");
            f.setParam("state", state);
        }
        //添加查询条件
        appendQuery(f,resourceType,addrCity,startDt,endDt);
        f.append(" order by bean.managerId desc");
        f.setCacheable(true);
        return find(f, pageNo, pageSize);
    }
    /***
     * @author jiangshuzhong
     * @date 2017/1/3
     * @param resourceType 资源类型
     * @param addrCity 所在城市
     * @param startDt 开始日期
     * @param endDt 结束日期
     * @param f
     * @desc 查询条件
     */
    private void appendQuery(Finder f, String resourceType,String addrCity,Date startDt,Date endDt) {

        if (resourceType != null && !"".equals(resourceType)) {
            f.append(" and bean.resourceType like :resourceType");
            f.setParam("resourceType", "%" + resourceType + "%");
        }
        if (addrCity != null && !"".equals(addrCity)) {
            f.append(" and bean.addrCity like :addrCity");
            f.setParam("addrCity", "%" + addrCity + "%");
        }
        if (startDt != null && !"".equals(startDt) && endDt != null && !"".equals(endDt)) {
            f.append(" and bean.createDt between :startDt and :endDt");
            f.setParam("startDt", startDt);
            f.setParam("endDt", endDt);
        }
    }
        /**
         * @Author jsz
         * managerId
         * @Date 2016/1/7
         */
    public SIcloudManage findById(Integer managerId){
        SIcloudManage sIcloudManage = get(managerId);
        return sIcloudManage;
    }
    /**
     * @Author jsz
     * 删除云资源信息
     * @Date 2016/1/7
     */
    public SIcloudManage delete(SIcloudManage sIcloudManage) {
        if (sIcloudManage != null) {
            getSession().delete(sIcloudManage);
        }
        return sIcloudManage;
    }

    /**
     * @param id
     * @Author wanjinyou
     * @Date 2016/1/20
     * @Desc 云资源管理删除 【用户公司一条需求的明细】
     */
    public SIcloudManage deleteById(Integer id) {
        SIcloudManage bean = findById(id);
        getSession().delete(bean);
        return bean;
    }

    /**
     * @Author jsz
     * 更新云资源信息
     * @Date 2016/1/9
     */
    public SIcloudManage update(SIcloudManage bean) {
        getSession().update(bean);
        return bean;
    }
    /**
     * @author jsz
     * @date 2017/1/10 16:13
     * @desc 自定义标签——云计算政策列表
     **/
    public List<SIcloudManage> getList(Integer count, Integer orderBy, Integer listType,Integer areaType,Integer state) {
        Finder f = Finder.create("select  bean from SIcloudManage bean where 1 = 1");
        if(areaType!=null){
            switch (areaType){
                case 1:
                    f.append(" and bean.addrCity =:addrCity");
                    f.setParam("addrCity", "呼伦贝尔");
                    break;
                case 2:
                    f.append(" and bean.addrCity =:addrCity");
                    f.setParam("addrCity", "兴安盟");
                    break;
                case 3:
                    f.append(" and bean.addrCity =:addrCity");
                    f.setParam("addrCity", "通辽市");
                    break;
                case 4:
                    f.append(" and bean.addrCity =:addrCity");
                    f.setParam("addrCity", "赤峰市");
                    break;
                case 5:
                    f.append(" and bean.addrCity =:addrCity");
                    f.setParam("addrCity", "锡林郭勒盟");
                    break;
                case 6:
                    f.append(" and bean.addrCity =:addrCity");
                    f.setParam("addrCity", "乌兰察布市");
                    break;
                case 7:
                    f.append(" and bean.addrCity =:addrCity");
                    f.setParam("addrCity", "包头市");
                    break;
                case 8:
                    f.append(" and bean.addrCity =:addrCity");
                    f.setParam("addrCity", "呼和浩特");
                    break;
                case 9:
                    f.append(" and bean.addrCity =:addrCity");
                    f.setParam("addrCity", "巴彦淖尔市");
                    break;
                case 10:
                    f.append(" and bean.addrCity =:addrCity");
                    f.setParam("addrCity", "鄂尔多斯市");
                    break;
                case 11:
                    f.append(" and bean.addrCity =:addrCity");
                    f.setParam("addrCity", "乌海市");
                    break;
                case 12:
                    f.append(" and bean.addrCity =:addrCity");
                    f.setParam("addrCity", "阿拉善盟");
                    break;
            }
        }
        if (listType !=null) {
            switch (listType) {
                case 1:
                    f.append(" and bean.resourceType =:resourceType");
                    f.setParam("resourceType", "云存储");
                    break;
                case 2:
                    f.append(" and bean.resourceType =:resourceType");
                    f.setParam("resourceType", "云计算");
                    break;
                case 3:
                    f.append(" and bean.resourceType =:resourceType");
                    f.setParam("resourceType", "云数据库");
                    break;
                case 4:
                    f.append(" and bean.resourceType =:resourceType");
                    f.setParam("resourceType", "其他");
                    break;
            }
        }
        if (state !=null) {
            switch (state) {
                case 0:
                    f.append(" and bean.state =:state");
                    f.setParam("state", "0");
                    break;
                case 1:
                    f.append(" and bean.state =:state");
                    f.setParam("state", "1");
                    break;
            }
        }
        if (orderBy !=null){
            f.append(" order by bean.releaseDt desc");
        }
        if (count != null) {
            f.setMaxResults(count);
        }
        return find(f);
    }


    /**
     * @Author 闫浩芮
     * 批量发布
     * @Date 2017/2/17 0017 18:45
     */
    public int passMany(String demandIdsStr) {
        java.sql.Date now = new java.sql.Date(System.currentTimeMillis());
        Query query = getSession().createQuery("update SIcloudManage bean set bean.state='1', bean.releaseDt=(:updateDt)" +
                "where bean.managerId in (" + demandIdsStr + ")").setParameter("updateDt",now);
        return query.executeUpdate();
    }
    /**
     * @Author 闫浩芮
     *批量下架
     * @Date 2017/2/17 0017 18:45
     */
    public int rejectMany(String demandIdsStr) {
        Query query = getSession().createQuery("update SIcloudManage bean set bean.state='2' " +
                "where bean.managerId in (" + demandIdsStr + ")");
        return query.executeUpdate();
    }
    /**
     * @Author 闫浩芮
     * @Date 2017/2/20 0020 10:37
     * 批量删除
     */
    public int deleteMany(String demandIdsStr) {
        Query query = getSession().createQuery("delete SIcloudManage bean where bean.managerId in (" + demandIdsStr + ")");
        return query.executeUpdate();
    }
    @Override
    protected Class<SIcloudManage> getEntityClass() {
        return SIcloudManage.class;
    }
}
