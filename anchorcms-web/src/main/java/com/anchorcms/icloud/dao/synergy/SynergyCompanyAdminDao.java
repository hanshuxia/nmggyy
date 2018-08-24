package com.anchorcms.icloud.dao.synergy;

import com.anchorcms.icloud.model.synergy.SCompany;
/**
 * @author dongxuecheng
 * @Date 2017/2/20 13:18
 * @return
 * @description
 */
public interface SynergyCompanyAdminDao {

    public SCompany save(SCompany company);

    public SCompany update(SCompany company);


    public SCompany findSCompanybyid(String Companyid);

    public Integer findBytypeAndname(SCompany bean);

    public SCompany findSCompanyBytypeAndname(SCompany bean);

}
