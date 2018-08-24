package com.anchorcms.icloud.dao.synergy.impl;

import com.anchorcms.common.hibernate.Finder;
import com.anchorcms.common.hibernate.HibernateBaseDao;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.icloud.dao.synergy.HotDemandDao;
import com.anchorcms.icloud.model.synergy.SDemand;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class HotDemandDaoImpl extends HibernateBaseDao<SDemand, Integer> implements HotDemandDao {
    /**
     * @Author 闫浩芮
     * 查询需求列表
     * @Date 2016/12/23 0023 15:50
     */
    public Pagination getPageBySelf(Integer channelId, Integer siteId, Integer modelId,int pageNo, int pageSize,
                                    Date releaseDt,Date deadlineDt, Integer demandId,String inquiryTheme,
                                    String status,String recommendedType,String addrCity,String addrCounty,
                                    String addrStreet,String operatorId) {
        if(recommendedType==null||"".equals(recommendedType)||"1".equals(recommendedType)) {
            Finder f = Finder.create("select bean from SDemand bean where bean.recommendedType='1' and" +
                    " bean.statusType='3' and  DATEDIFF(bean.deadlineDt,now())>=0");
            f.setCacheable(true);
            return find(f, pageNo, pageSize);
        }else{
            Finder f = Finder.create("select bean from SDemand bean where bean.recommendedType='0' and" +
                    " bean.statusType='3' and  DATEDIFF(bean.deadlineDt,now())>=0");
            f.setCacheable(true);
            return find(f, pageNo, pageSize);
        }

    }
    /**
     * @Author 闫浩芮
     * 根据demandId查找
     * @Date 2016/12/28 0028 9:08
     */
    public SDemand findById(Integer id){
        return get(id);
    }
    public List<SDemand> getList(Integer count, Integer orderBy, Integer listType) {
        Finder f = Finder.create("select bean from SDemand bean where bean.recommendedType='1'and bean.statusType='3'" +
                "and DATEDIFF(bean.deadlineDt,now())>=0");
        if (orderBy !=null){
            switch (orderBy) {
                case 1:
                    f.append(" order by bean.updateDt desc");
                    break;
                case 2:
                    f.append(" order by bean.content.contentCount.downloadsMonth desc");
                    break;
            }
        }
        if (count != null) {
            f.setMaxResults(count);
        }
        return find(f);
    }


    /**
     * @Author 闫浩芮
     * 批量推荐
     * @Date 2017/2/17 0017 18:45
     */
    public int passMany(String demandIdsStr) {
        Query query = getSession().createQuery("update SDemand bean set bean.recommendedType='1'"+
                "where bean.demandId in (" + demandIdsStr + ")");
        return query.executeUpdate();
    }
    /**
     * @Author 闫浩芮
     *批量撤销
     * @Date 2017/2/17 0017 18:45
     */
    public int rejectMany(String demandIdsStr) {
        Query query = getSession().createQuery("update SDemand bean set bean.recommendedType='0' " +
                "where bean.demandId in (" + demandIdsStr + ")");
        return query.executeUpdate();
    }
    @Override
    protected Class<SDemand> getEntityClass() {
        return SDemand.class;
    }
}
