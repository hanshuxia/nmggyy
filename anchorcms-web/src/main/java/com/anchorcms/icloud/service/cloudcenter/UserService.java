package com.anchorcms.icloud.service.cloudcenter;

import com.anchorcms.core.model.CmsUser;

/**
 * @anther 李利民
 * @descript
 * @data 2017/1/179:17
 */
public interface UserService {
    CmsUser getByYserId(String createrId);
}
