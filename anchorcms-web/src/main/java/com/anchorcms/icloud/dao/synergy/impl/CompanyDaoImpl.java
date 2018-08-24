package com.anchorcms.icloud.dao.synergy.impl;

import com.anchorcms.common.hibernate.HibernateBaseDao;
import com.anchorcms.icloud.dao.synergy.CompanyDao;
import com.anchorcms.icloud.model.synergy.SCompany;
import org.springframework.stereotype.Repository;

/**
 * @Author Yhr
 * 查询需求列表
 * @Date 2016/12/23 0023 15:50
     */
    @Repository
    public class CompanyDaoImpl extends HibernateBaseDao<SCompany, String> implements CompanyDao {
        /**
         * @param id
         * @return
     * @lilimin 企业能力查询
     */
    public SCompany getCompanyBy(String id) {
        SCompany company = get(id);
        return company;
    }

    @Override
    protected Class<SCompany> getEntityClass() {
        return SCompany.class;
    }
}
