package com.anchorcms.icloud.service.synergy;

import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsUser;

/**
 * @Author 姜舒中
 * @Email netuser.orz@icloud.com
 * @Date 2017/2/16 0016
 * @Desc
 */
public interface RegisterListService {
    public Pagination getRegisterPage(Integer siteId, CmsUser user, int cpn, int pageSize);
}
