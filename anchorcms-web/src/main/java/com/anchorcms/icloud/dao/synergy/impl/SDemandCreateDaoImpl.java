package com.anchorcms.icloud.dao.synergy.impl;

import com.anchorcms.common.hibernate.HibernateBaseDao;
import com.anchorcms.icloud.dao.synergy.SDemandCreateDao;
import com.anchorcms.icloud.model.common.SMemberAddress;
import com.anchorcms.icloud.model.commservice.SBigDataDemandSign;
import com.anchorcms.icloud.model.commservice.SBigDataNews;
import com.anchorcms.icloud.model.commservice.SBigDataPolicy;
import com.anchorcms.icloud.model.commservice.SBigDataSign;
import com.anchorcms.icloud.model.synergy.SDemand;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 * Created by gao jiangning on 2016/12/22 0022
 */
@Repository
public class SDemandCreateDaoImpl extends HibernateBaseDao<SDemand, Integer> implements SDemandCreateDao {
    public SDemand save(SDemand bean) {
        getSession().save(bean);
        return bean;
    }

    public SBigDataSign save2(SBigDataSign bean) {
        getSession().save(bean);
        return bean;
    }

    public SBigDataDemandSign save3(SBigDataDemandSign bean) {
        getSession().save(bean);
        return bean;
    }

    public SBigDataNews saveBigdataNews(SBigDataNews bean) {
        getSession().save(bean);
        return bean;
    }

    public SBigDataPolicy saveBigdataPolicy(SBigDataPolicy bean) {
        getSession().save(bean);
        return bean;
    }
    /**
     * @Author 闫浩芮
     * @param demandId
     * @Date 2017/01/04
     * @Desc 通过ID获取需求信息
     */
    public SDemand bySDemandId(Integer demandId) {
        SDemand sdemand = get(demandId);
        return sdemand;
    }

    /**
     * 手动删除子表被去掉关系的对象
     * @return 删除的行数
     */
    public void deleteOrphan() {
        Query query = getSession().createQuery("delete SDemandObj bean where bean.demandId=null");
        System.out.print("----->>>>>SDemandCreateDao.deleteOrphan\n----->>>>>deleted mount of rows:"+query.executeUpdate()+"\n");
    }

    @Override
    protected Class<SDemand> getEntityClass() {
        return SDemand.class;
    }
}
