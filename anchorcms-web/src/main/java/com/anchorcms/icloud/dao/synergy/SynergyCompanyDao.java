package com.anchorcms.icloud.dao.synergy;

import com.anchorcms.common.page.Pagination;
import com.anchorcms.icloud.model.synergy.SCompany;
import com.anchorcms.icloud.model.synergy.SCompanyAptitude;

import java.util.List;

/**
 * Created by ly on 2016/12/27.
 */
public interface SynergyCompanyDao {

    public SCompany save(SCompany company);

    public SCompany update(SCompany company);

    public SCompanyAptitude saveAptitude(SCompanyAptitude sCompanyAptitude);

    public SCompanyAptitude updateAptitude(SCompanyAptitude sCompanyAptitude);

    public List<SCompany> findSCompanyByName(String companyName);
}
