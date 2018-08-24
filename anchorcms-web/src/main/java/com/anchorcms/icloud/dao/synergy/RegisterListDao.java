package com.anchorcms.icloud.dao.synergy;

import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsUser;

/**
 * @Author 姜舒中
 * @Email netuser.orz@icloud.com
 * @Date 2017/2/16 0016
 * @Desc
 */
public interface RegisterListDao {
    public Pagination getPage(Integer siteId, CmsUser user, int cpn, int pageSize);
}
