package com.anchorcms.icloud.dao.commservice.impl;


import com.anchorcms.common.hibernate.HibernateBaseDao;
import com.anchorcms.icloud.dao.commservice.SCompanyDao;
import com.anchorcms.icloud.model.synergy.SCompany;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 * Created by hansx on 2017/1/13 0013.
 * 企业信息dao实现类
 */
@Repository
public class SCompanyDaoImpl extends
        HibernateBaseDao<SCompany, String> implements SCompanyDao {


    public SCompany findById(String id) {
        SCompany entity = get(id);
        return entity;
    }

    /**
     * @return 更新状态
     * @author hansx
     * @Date 2017/1/4 0013 下午 2:36
     */
    public int updateStatusById(String id, String statusType) {
        StringBuffer hql = new StringBuffer("update SCompany s set ");
        hql.append(" s.isRecommend='" + statusType + "'");
        hql.append(" where s.companyId='" + id + "'");
        return getSession().createQuery(hql.toString()).executeUpdate();
    }
    /**
     * 置为推荐
     * @Author 闫浩芮
     *@param ids  操作对象的Id
     * @Date 2017/2/18 0018 16:48
     */
    public int recommendMany(String ids) {
        Query query = getSession().createQuery("update SCompany bean set bean.isRecommend='1'"+
                "where bean.companyId in (" + ids + ")");
        return query.executeUpdate();
    }
    /**
     * 置为撤销
     * @Author 闫浩芮
     * @param ids  操作对象的Id
     * @Date 2017/2/18 0018 16:48
     */
    public int revokeMany(String ids) {
        Query query = getSession().createQuery("update SCompany bean set bean.isRecommend='0'"+
                "where bean.companyId in (" + ids + ")");
        return query.executeUpdate();
    }

    public int revokeAll(String ids, String backReason, String relstatus) {
        java.sql.Date now = new java.sql.Date(System.currentTimeMillis());
        Query query = getSession().createQuery("update SCompany bean set bean.isRecommend='0', bean.releaseDt=(:releaseDt),bean.status="+relstatus+",bean.backReason='"+backReason+
                "' where bean.companyId in (" + ids + ")").setParameter("releaseDt",now);
        return query.executeUpdate();
    }

    /**
     * 更新公司数据
     * @param bean
     * @return
     */
    public SCompany update(SCompany bean) {
        getSession().update(bean);
        return bean;
    }


    @Override
    protected Class<SCompany> getEntityClass() {
        return SCompany.class;
    }


}