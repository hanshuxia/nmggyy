package com.anchorcms.icloud.dao.cloudcenter.impl;
import com.anchorcms.cms.model.main.Content;
import com.anchorcms.common.hibernate.Finder;
import com.anchorcms.common.hibernate.HibernateBaseDao;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.icloud.dao.cloudcenter.CloudCenterDao;
import com.anchorcms.icloud.model.cloudcenter.SIcloudCenter;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @anther 李利民
 * @descript
 * @data 2017/1/316:02
 */

@Repository
public class CloudCenterDaoImpl extends HibernateBaseDao<SIcloudCenter, Integer> implements CloudCenterDao {

    public SIcloudCenter save(SIcloudCenter bean) {
        getSession().save(bean);
        return bean;
    }


     /** @author wanjinyou
       * @descript 云计算机中心列表展示
       * @date 2017/1/7 10:50
       */
    public Pagination getPageBySelf(Object o, Integer memberId, boolean b, boolean b1, Content.ContentStatus all, Object o1, Integer siteId, Integer modelId, Integer channelId, int i, int pageNo, int pageSize, Integer centerId, String centerName,String addrCity, Date startDate, Date endDate) {
      Finder f = Finder.create("select bean from SIcloudCenter bean ");
      f.append("where 1=1");
      //添加查询条件
      appendQuery(f, centerId,centerName,addrCity,startDate,endDate);
      f.append(" order by bean.centerId desc");
      f.setCacheable(true);
      return find(f, pageNo, pageSize);
    }

    /***
     * @author wanjinyou
     * @date 2017/1/7
     * @param f
     * @param centerId 中心id
     * @param centerName 查询中心名称
     * @param startDate 查询开始时间
     * @param endDate 查询结束时间
     * @param addrCity 查询地区
     * @desc 查询条件
     */
    private void appendQuery(Finder f, Integer centerId, String centerName,String addrCity, Date startDate, Date endDate) {
        if (centerId != null && !"".equals(centerId)) {
            f.append(" and bean.centerId like :centerId");
            f.setParam("centerId", centerId);
        }
        if (centerName != null && !"".equals(centerName)) {
            f.append(" and bean.centerName like :centerName");
            f.setParam("centerName", "%"+centerName+"%");
        }
        if (startDate != null) {
            f.append(" and bean.createDt >=:startDate");
            f.setParam("startDate", startDate);
        }
        if (endDate != null) {
            f.append(" and bean.createDt <=:endDate");
            f.setParam("endDate", endDate);
        }
        if (addrCity != null && !"".equals(addrCity)) {
            f.append(" and bean.addrCity like :addrCity");
            f.setParam("addrCity", addrCity);
        }
    }

    /**
     * @param id
     * @author wanjinyou
     * @descript 云计算机中心--编辑
     * @date 2017/1/7 13:19
     */
    public SIcloudCenter findById(Integer id) {
        SIcloudCenter bean=get(id);
        return bean;
    }

    /**
     * @author wanjinyou
     * @descript 云计算机中心--编辑更新
     * @date 2017/1/7 14:13
     */
    public SIcloudCenter update(SIcloudCenter bean) {
        getSession().update(bean);
        return bean;
    }

    /**
     * @param id
     * @author wanjinyou
     * @descript 云计算机中心--删除
     * @date 2017/1/7 16:48
     */
    public SIcloudCenter deleteById(Integer id) {
        SIcloudCenter bean = findById(id);
        getSession().delete(bean);
        return bean;
    }
    /**
     * @author jsz
     * @date 2017/1/10 16:13
     * @desc 自定义标签——云计算政策列表
     **/
    public List<SIcloudCenter> getList(Integer count, Integer orderBy,Integer areaType) {
        Finder f = Finder.create("select  bean from SIcloudCenter bean where 1 = 1");
        if(areaType!=null){
            switch (areaType){
                case 1:
                    f.append(" and bean.addrCity =:addrCity");
                    f.setParam("addrCity", "540100");
                    break;
                case 2:
                    f.append(" and bean.addrCity =:addrCity");
                    f.setParam("addrCity", "540700");
                    break;
                case 3:
                    f.append(" and bean.addrCity =:addrCity");
                    f.setParam("addrCity", "540200");
                    break;
                case 4:
                    f.append(" and bean.addrCity =:addrCity");
                    f.setParam("addrCity", "540300");
                    break;
                case 5:
                    f.append(" and bean.addrCity =:addrCity");
                    f.setParam("addrCity", "540500");
                    break;
                case 6:
                    f.append(" and bean.addrCity =:addrCity");
                    f.setParam("addrCity", "520000");
                    break;
                case 7:
                    f.append(" and bean.addrCity =:addrCity");
                    f.setParam("addrCity", "500000");
                    break;
                case 8:
                    f.append(" and bean.addrCity =:addrCity");
                    f.setParam("addrCity", "490000");
                    break;
                case 9:
                    f.append(" and bean.addrCity =:addrCity");
                    f.setParam("addrCity", "540400");
                    break;
                case 10:
                    f.append(" and bean.addrCity =:addrCity");
                    f.setParam("addrCity", "510000");
                    break;
                case 11:
                    f.append(" and bean.addrCity =:addrCity");
                    f.setParam("addrCity", "530000");
                    break;
                case 12:
                    f.append(" and bean.addrCity =:addrCity");
                    f.setParam("addrCity", "540600");
                    break;
            }
        }
        if (orderBy !=null){
            f.append(" order by bean.createDt desc");
        }
        f.setCacheable(true);
        if (count != null) {
            f.setMaxResults(count);
        }
        return find(f);
    }

    /**
     * @Author 闫浩芮
     * @Date 2017/2/20 0020 10:37
     * 批量删除
     */
    public int deleteMany(String demandIdsStr) {
        Query query = getSession().createQuery("delete SIcloudCenter bean where bean.centerId in (" + demandIdsStr + ")");
        return query.executeUpdate();
    }
    @Override
    protected Class<SIcloudCenter> getEntityClass() {
        return SIcloudCenter.class;
    }
}
