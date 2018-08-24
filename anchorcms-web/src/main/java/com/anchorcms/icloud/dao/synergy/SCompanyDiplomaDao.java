package com.anchorcms.icloud.dao.synergy;

import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.model.synergy.SCompanyAptitude;
import com.anchorcms.icloud.model.synergy.SCompanyDiploma;

import java.util.List;

/**
 * Created by ly on 2016/12/27.
 */
public interface SCompanyDiplomaDao {

    public Pagination getPage(Integer siteId, CmsUser user, int cpn, int pageSize);

    public SCompanyDiploma save(SCompanyDiploma diploma);

    public SCompanyDiploma update(SCompanyDiploma diploma);

    public SCompanyDiploma findById(Integer id);

    /**
     *@Author hansx
     *@Date 2017/5/11 08:48
     *@Desc 根据企业id查找企业资质
     **/
    public SCompanyAptitude findByCompanyId(String id);

    public SCompanyDiploma deleteById(Integer id);

    public List<SCompanyDiploma> getDiplomaList(Integer count, Integer orderBy, Integer listType);
}
