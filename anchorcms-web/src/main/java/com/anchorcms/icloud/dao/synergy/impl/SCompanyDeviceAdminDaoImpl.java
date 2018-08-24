package com.anchorcms.icloud.dao.synergy.impl;

import com.anchorcms.common.hibernate.Finder;
import com.anchorcms.common.hibernate.HibernateBaseDao;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.dao.synergy.SCompanyDeviceAdminDao;
import com.anchorcms.icloud.model.synergy.SCompanyDevice;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author dongxuecheng
 * @Date 2017/2/20 13:17
 * @return
 * @description
 */

@Repository
public class SCompanyDeviceAdminDaoImpl extends HibernateBaseDao<SCompanyDevice, Integer> implements SCompanyDeviceAdminDao {
    public Pagination getPage(Integer siteId, String companyId, int pageNo, int pageSize) {
        Finder f = Finder.create("select  bean from SCompanyDevice bean");
        f.append(" where bean.company.companyId=:companyId");
        f.setParam("companyId",companyId);
        //添加查询条件
//        appendQuery(f, startDate, endDate, releaseId, abilityType, abilityName, abilityCode, status);
        f.append(" order by bean.updateDt desc,bean.deviceId desc");
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
