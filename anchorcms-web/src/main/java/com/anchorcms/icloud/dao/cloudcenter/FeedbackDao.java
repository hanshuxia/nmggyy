package com.anchorcms.icloud.dao.cloudcenter;

import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsUser;

import java.sql.Date;

/**
 * Created by ly on 2017/1/7.
 */
public interface FeedbackDao {
    public Pagination getFeedbackPage(Integer siteId, CmsUser user, int cpn, int i, Date startDate, Date endDate, String applierName, String policyType, String policyName, String policyCode, String state);
}
