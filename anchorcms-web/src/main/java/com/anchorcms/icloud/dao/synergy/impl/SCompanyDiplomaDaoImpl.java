package com.anchorcms.icloud.dao.synergy.impl;

import com.anchorcms.common.hibernate.Finder;
import com.anchorcms.common.hibernate.HibernateBaseDao;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.dao.synergy.SCompanyDiplomaDao;
import com.anchorcms.icloud.model.synergy.SCompanyAptitude;
import com.anchorcms.icloud.model.synergy.SCompanyDiploma;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ly on 2016/12/29.
 */
@Repository
public class SCompanyDiplomaDaoImpl extends HibernateBaseDao<SCompanyDiploma, Integer> implements SCompanyDiplomaDao {
    public Pagination getPage(Integer siteId, CmsUser user, int pageNo, int pageSize) {
        Finder f = Finder.create("select  bean from SCompanyDiploma bean");
        f.append(" where bean.companyId=:companyId");
        f.setParam("companyId",user.getCompany().getCompanyId());
        //添加查询条件
//        appendQuery(f, startDate, endDate, releaseId, abilityType, abilityName, abilityCode, status);
        f.append(" order by bean.diplomaId desc");
        return find(f, pageNo, pageSize);
    }

    public SCompanyDiploma save(SCompanyDiploma bean) {
        getSession().save(bean);
        return bean;
    }

    public SCompanyDiploma update(SCompanyDiploma bean) {
        getSession().update(bean);
        return bean;
    }

    public SCompanyDiploma findById(Integer id) {
        SCompanyDiploma bean = get(id);
        return bean;
    }

    public SCompanyAptitude findByCompanyId(String id) {
        StringBuffer hql = new StringBuffer();
        hql.append("from SCompanyAptitude s where s.company.companyId='"+id+"'");
        Query query = getSession().createQuery(hql.toString());
        List<SCompanyAptitude> list = query.list();
        if(list!=null&&list.size()>0){
            return list.get(0);
        }
        return null;
    }


    public SCompanyDiploma deleteById(Integer id) {
        SCompanyDiploma bean = findById(id);
        getSession().delete(bean);
        return bean;
    }

    public List<SCompanyDiploma> getDiplomaList(Integer count, Integer orderBy, Integer listType) {
        Finder f = Finder.create("select  bean from SCompanyDiploma bean");
        if (orderBy !=null){
            f.append(" order by bean.createDt desc");
        }
        if (count != null) {
            f.setMaxResults(count);
        }
        return find(f);
    }

    @Override
    protected Class<SCompanyDiploma> getEntityClass() {
        return SCompanyDiploma.class;
    }
}
