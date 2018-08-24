package com.anchorcms.icloud.service.cloudcenter;

import com.anchorcms.cms.model.main.Content;
import com.anchorcms.cms.model.main.ContentExt;
import com.anchorcms.cms.model.main.ContentTxt;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.model.cloudcenter.SIcloudApply;

import java.sql.Date;

/**
 * @anther 李利民
 * @descript
 * @data 2017/1/711:51
 */
public interface CloudCenterApplyService {
    Content save(SIcloudApply sd, Content c, ContentExt ext, ContentTxt t, String[] attachmentPaths, String[] attachmentNames, String[] attachmentFilenames, String[] picPaths, String[] picDescs, Integer channelId, Integer typeId, CmsUser user, Short charge, boolean b);

    /**
     * @anther lilimin
     * @descript
     * @data 2017/1/711:51
     */
    Pagination getSoftwarePage(Integer siteId, CmsUser user, int cpn, int i);
    /**
     * @anther lilimin
     * @descript 通过ID 或得数据
     * @data 2017/1/711:51
     */
    SIcloudApply byApplyId(Integer id);

    void update(Integer applyId, SIcloudApply sd, String editor, String[] attachmentPaths, String[] attachmentNames, String[] attachmentFilenames, String[] picPaths, String[] picDescs, Integer channelId, CmsUser user, Short charge, boolean b);

    public Pagination getFeedbackPage(Integer siteId, CmsUser user, int pageNo, int pageSize, Date startDate, Date endDate, String applierName, String policyLevel, String policyName, String policyCode, String state);
    /**
     * @anther wanjinyou
     * @descript 政策申请跟踪
     * @data 2017/1/12
     */
    public Pagination getFollowbackPage(Integer siteId, CmsUser user, int pageNo, int pageSize, Date startDate, Date endDate, String applierName, String policyLevel, String policyName, String policyCode, String state,String userName);

    public SIcloudApply updateState(Integer id, String state, Integer channelId, CmsUser user, Short charge, boolean b,String backReason);

    public SIcloudApply delete(Integer id);

    public SIcloudApply findById(Integer id);

    /**
     * @Author 闫浩芮
     * 置为通过
     * @Date 2017/2/18 0018 11:07
     */
    public void passMany(String demandIdsStr,String state);
    /**
     * @Author 闫浩芮
     * 置为驳回
     * @Date 2017/2/18 0018 11:07
     */
    public void rejectMany(String demandIdsStr,String backReason);
    /**
     * @Author 闫浩芮
     * 批量删除
     * @Date 2017/2/20 0020 10:37
     */
    public void deleteMany(String demandIdsStr);

}
