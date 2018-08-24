package com.anchorcms.icloud.dao.synergy;

import com.anchorcms.icloud.model.synergy.SCompany;

/**
 * @Author Yhr
 * 查询需求列表
 * @Date 2016/12/23 0023 15:50
 */
public interface CompanyDao {
    /**
     * @lilimin
     * 企业能力查询
     * @param id
     * @return
     */
    SCompany getCompanyBy(String id);
}
