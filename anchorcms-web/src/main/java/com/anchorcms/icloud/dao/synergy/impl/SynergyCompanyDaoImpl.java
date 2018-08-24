package com.anchorcms.icloud.dao.synergy.impl;

import com.anchorcms.common.hibernate.Finder;
import com.anchorcms.common.hibernate.HibernateBaseDao;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.icloud.dao.synergy.SynergyCompanyDao;
import com.anchorcms.icloud.model.synergy.SCompany;
import com.anchorcms.icloud.model.synergy.SCompanyAptitude;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ly on 2016/12/27.
 */
@Repository
public class SynergyCompanyDaoImpl extends HibernateBaseDao<SCompany, Integer> implements SynergyCompanyDao {

    public SCompany save(SCompany bean) {
        getSession().save(bean);
        return bean;
    }

    public SCompany update(SCompany bean) {
        getSession().update(bean);
        return bean;
    }

    public SCompanyAptitude saveAptitude(SCompanyAptitude sCompanyAptitude) {
        getSession().save(sCompanyAptitude);
        getSession().flush();
        return sCompanyAptitude;
    }

    public SCompanyAptitude updateAptitude(SCompanyAptitude sCompanyAptitude) {
        getSession().update(sCompanyAptitude);
        getSession().flush();
        return sCompanyAptitude;
    }

    @Override
    protected Class<SCompany> getEntityClass() {
        return SCompany.class;
    }

    public List<SCompany> findSCompanyByName(String companyName) {
        Finder f = Finder.create("select  bean from SCompany bean where bean.companyName=:companyName");
        f.setParam("companyName",companyName);
        List<SCompany> list = find(f);
        return list;
    }
}
