package com.anchorcms.icloud.dao.synergy;

import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.model.synergy.SCompanyDiploma;

import java.util.List;
/**
 * @author dongxuecheng
 * @Date 2017/2/20 13:18
 * @return
 * @description
 */
public interface SCompanyDiplomaAdminDao {

    public Pagination getPage(Integer siteId, String companyId, int cpn, int pageSize);

    public SCompanyDiploma save(SCompanyDiploma diploma);

    public SCompanyDiploma update(SCompanyDiploma diploma);

    public SCompanyDiploma findById(Integer id);

    public SCompanyDiploma deleteById(Integer id);

    public List<SCompanyDiploma> getDiplomaList(Integer count, Integer orderBy, Integer listType);
}
