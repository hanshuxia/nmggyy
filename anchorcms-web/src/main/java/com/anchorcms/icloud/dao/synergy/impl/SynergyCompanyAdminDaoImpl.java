package com.anchorcms.icloud.dao.synergy.impl;

import com.anchorcms.common.hibernate.Finder;
import com.anchorcms.common.hibernate.HibernateBaseDao;
import com.anchorcms.icloud.dao.synergy.SynergyCompanyAdminDao;
import com.anchorcms.icloud.dao.synergy.SynergyCompanyDao;
import com.anchorcms.icloud.model.synergy.SAbility;
import com.anchorcms.icloud.model.synergy.SCompany;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 * @author dongxuecheng
 * @Date 2017/2/20 13:17
 * @return
 * @description
 */
@Repository
public class SynergyCompanyAdminDaoImpl extends HibernateBaseDao<SCompany, String> implements SynergyCompanyAdminDao {

    public SCompany save(SCompany bean) {
        getSession().save(bean);
        return bean;
    }

    public SCompany update(SCompany bean) {
        getSession().update(bean);
        return bean;
    }

    public SCompany findSCompanybyid(String Companyid) {
        SCompany bean=get(Companyid);
        return bean;
    }


   public Integer findBytypeAndname(SCompany bean){
       Finder f = Finder.create("select  bean from SCompany bean where bean.companyCode=:companyCode and bean.companyName=:companyName");
       f.setParam("companyCode",bean.getCompanyCode());
       f.setParam("companyName",bean.getCompanyName());
       int totalCount = countQueryResult(f);
       return totalCount;
   }

   public SCompany findSCompanyBytypeAndname(SCompany bean) {
       Query query = getSession().createQuery("SELECT  bean FROM SCompany bean where bean.companyCode=:companyCode and bean.companyName=:companyName");
       query.setParameter("companyCode", bean.getCompanyCode());
       query.setParameter("companyName", bean.getCompanyName());
       SCompany company = (SCompany) query.uniqueResult();
       return company;
   }
    @Override
    protected Class<SCompany> getEntityClass() {
        return SCompany.class;
    }
}
