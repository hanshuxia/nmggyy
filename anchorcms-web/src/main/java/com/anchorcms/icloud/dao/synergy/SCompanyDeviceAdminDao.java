package com.anchorcms.icloud.dao.synergy;

import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.model.synergy.SCompanyDevice;

import java.util.List;
/**
 * @author dongxuecheng
 * @Date 2017/2/20 13:18
 * @return
 * @description
 */
public interface SCompanyDeviceAdminDao {

    public SCompanyDevice save(SCompanyDevice device);

    public SCompanyDevice update(SCompanyDevice diploma);

    public SCompanyDevice findById(Integer id);

    public SCompanyDevice deleteById(Integer id);

    public Pagination getPage(Integer siteId, String companyId, int cpn, int pageSize);

    public List<SCompanyDevice> getDeviceList(Integer count, Integer orderBy, Integer listType);
}
