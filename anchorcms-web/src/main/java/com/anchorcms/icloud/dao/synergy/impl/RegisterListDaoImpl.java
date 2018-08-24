package com.anchorcms.icloud.dao.synergy.impl;

import com.anchorcms.common.hibernate.Finder;
import com.anchorcms.common.hibernate.HibernateBaseDao;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.dao.synergy.RegisterListDao;
import org.springframework.stereotype.Repository;

/**
 * @Author 姜舒中
 * @Email netuser.orz@icloud.com
 * @Date 2017/2/16 0016
 * @Desc
 */
@Repository
public class RegisterListDaoImpl extends HibernateBaseDao<CmsUser, Integer> implements RegisterListDao {
    public Pagination getPage(Integer siteId, CmsUser user, int pageNo, int pageSize) {
        Finder f = Finder.create("select bean from CmsUser bean where 1=1");
        f.append(" and bean.company.companyId=:companyId");
        f.setParam("companyId",user.getCompany().getCompanyId());
        f.append(" and bean.isMain is null");
        f.append(" order by bean.registerTime desc,bean.userId desc");
        return find(f, pageNo, pageSize);
    }

    @Override
    protected Class<CmsUser> getEntityClass() {
            return CmsUser.class;
    }
}
