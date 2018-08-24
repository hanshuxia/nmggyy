package com.anchorcms.icloud.dao.synergy;

import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.model.synergy.SCompanyDevice;

import java.util.List;

/**
 * Created by ly on 2016/12/30.
 */
public interface SCompanyDeviceDao {

    public SCompanyDevice save(SCompanyDevice device);

    public SCompanyDevice update(SCompanyDevice diploma);

    public SCompanyDevice findById(Integer id);

    public SCompanyDevice deleteById(Integer id);

    public Pagination getPage(Integer siteId, CmsUser user, int cpn, int pageSize);

    public List<SCompanyDevice> getDeviceList(Integer count, Integer orderBy, Integer listType);
}
