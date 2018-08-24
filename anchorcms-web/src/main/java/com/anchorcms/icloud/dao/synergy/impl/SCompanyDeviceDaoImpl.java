package com.anchorcms.icloud.dao.synergy.impl;

import com.anchorcms.common.hibernate.Finder;
import com.anchorcms.common.hibernate.HibernateBaseDao;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.dao.synergy.SCompanyDeviceDao;
import com.anchorcms.icloud.model.synergy.SCompanyDevice;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ly on 2016/12/29.
 */
@Repository
public class SCompanyDeviceDaoImpl extends HibernateBaseDao<SCompanyDevice, Integer> implements SCompanyDeviceDao {
    public Pagination getPage(Integer siteId, CmsUser user, int pageNo, int pageSize) {
        Finder f = Finder.create("select  bean from SCompanyDevice bean");
        f.append(" where bean.company.companyId=:companyId");
        f.setParam("companyId",user.getCompany().getCompanyId());
        //添加查询条件
//        appendQuery(f, startDate, endDate, releaseId, abilityType, abilityName, abilityCode, status);
        f.append(" order by bean.updateDt desc,bean.deviceId desc");
        f.setCacheable(true);
        return find(f, pageNo, pageSize);
    }

    public SCompanyDevice save(SCompanyDevice bean) {
        getSession().save(bean);
        return bean;
    }

    public SCompanyDevice update(SCompanyDevice bean) {
        getSession().update(bean);
        return bean;
    }

    public SCompanyDevice findById(Integer id) {
        SCompanyDevice bean = get(id);
        return bean;
    }

    public SCompanyDevice deleteById(Integer id) {
        SCompanyDevice bean = findById(id);
        getSession().delete(bean);
        return bean;
    }

    public List<SCompanyDevice> getDeviceList(Integer count, Integer orderBy, Integer listType){
        Finder f = Finder.create("select  bean from SCompanyDevice bean");
        if (orderBy !=null){
            f.append(" order by bean.createDt desc");
        }
        if (count != null) {
            f.setMaxResults(count);
        }
        return find(f);
    }

    @Override
    protected Class<SCompanyDevice> getEntityClass() {
        return SCompanyDevice.class;
    }
}
