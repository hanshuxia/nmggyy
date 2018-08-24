package com.anchorcms.icloud.dao.cloudcenter;

import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.model.cloudcenter.SIcloudApply;

import java.sql.Date;
import java.util.List;

/**
 * @anther 李利民
 * @descript
 * @data 2017/1/711:57
 */
public interface CloudApplyDao {
    SIcloudApply save(SIcloudApply bean);

    Pagination getPage(Integer siteId, CmsUser user, int pageNo, int pageSize);
    public SIcloudApply findById(Integer id);

    public SIcloudApply deleteById(Integer id);

    public SIcloudApply update(SIcloudApply software);

    public Pagination getFeedbackPage(Integer siteId, CmsUser user, int cpn, int i, Date startDate, Date endDate, String applierName, String policyLevel, String policyName, String policyCode, String state);

    /**
     * @anther wanjinyou
     * @descript 政策申请跟踪
     * @data 2017/1/12
     */
    public Pagination getFollowbackPage(Integer siteId, CmsUser user, int pageNo, int pageSize, Date startDate, Date endDate, String applierName, String policyLevel, String policyName, String policyCode, String state,String userName);

    /**
     * @Author 闫浩芮
     * 置为通过
     * @Date 2017/2/17 0017 17:25
     */
    public int passMany(String demandIdsStr,String state);
    /**
     * @Author 闫浩芮
     * 置为驳回
     * @Date 2017/2/17 0017 18:40
     */
    public int rejectMany(String demandIdsStr,String backReason);

    List<SIcloudApply> byIds(String ids);

}
