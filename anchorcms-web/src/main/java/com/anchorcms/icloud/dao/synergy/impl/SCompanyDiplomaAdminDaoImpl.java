package com.anchorcms.icloud.dao.synergy.impl;

import com.anchorcms.common.hibernate.Finder;
import com.anchorcms.common.hibernate.HibernateBaseDao;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.dao.synergy.SCompanyDiplomaAdminDao;
import com.anchorcms.icloud.dao.synergy.SCompanyDiplomaDao;
import com.anchorcms.icloud.model.synergy.SCompanyDiploma;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * @author dongxuecheng
 * @Date 2017/2/20 13:17
 * @return
 * @description
 */
@Repository
public class SCompanyDiplomaAdminDaoImpl extends HibernateBaseDao<SCompanyDiploma, Integer> implements SCompanyDiplomaAdminDao {
    public Pagination getPage(Integer siteId, String companyId, int pageNo, int pageSize) {
        Finder f = Finder.create("select  bean from SCompanyDiploma bean");
        f.append(" where bean.companyId=:companyId");
        f.setParam("companyId",companyId);
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
