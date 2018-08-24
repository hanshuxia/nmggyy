package com.anchorcms.icloud.dao.cloudcenter;

import com.anchorcms.icloud.model.cloudcenter.SIcloudCenter;
import com.anchorcms.icloud.model.cloudcenter.SIcloudManage;

import java.util.List;

/**SIcloudManage
 * @anther 李利民
 * @descript
 * @data 2017/1/516:14
 */
public interface CloudMangerDao {
    SIcloudManage save(SIcloudManage bean);
    SIcloudManage getMangerById(Integer mangerId);
    SIcloudManage update(SIcloudManage bean);

    void deleteBymanager(SIcloudManage manager);
    public List<SIcloudCenter> getsIcloudCenter();
}
