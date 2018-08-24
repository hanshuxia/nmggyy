package com.anchorcms.icloud.service.cloudcenter;

import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.model.cloudcenter.SIcloudApply;

import java.sql.Date;

/**
 * Created by ly on 2017/1/7.
 */
public interface FeedbackService {

    public Pagination getFeedbackPage(Integer siteId, CmsUser user, int pageNo, int pageSize, Date startDate, Date endDate, String applierName, String policyType, String policyName, String policyCode, String state);

    public SIcloudApply updateState(Integer id, String state, Integer channelId, CmsUser user, Short charge, boolean b);

    public SIcloudApply delete(Integer id);

    public SIcloudApply findById(Integer id);
}
