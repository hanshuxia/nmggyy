package com.anchorcms.icloud.service.cloudcenter;

import com.anchorcms.cms.model.main.Content;
import com.anchorcms.cms.model.main.ContentExt;
import com.anchorcms.cms.model.main.ContentTxt;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.model.cloudcenter.SIcloudApply;
import com.anchorcms.icloud.model.cloudcenter.SIcloudPolicy;

import java.sql.Date;
import java.util.List;

/**
 *@Author 毛成
 *@Date 2017/1/4 0003 15:43
 */
public interface CloudCenterPolicyService {

    /**
     * @author mc
     * @date 2017-1-4
     * @param channelId 栏目ID
     * @param siteId 站点ID
     * @param modelId modelId
     * @param memberId 会员ID
     * @param pageNo 页数
     * @param pageSize 每页大小
     * @param state 状态
     * @desc 获得文章分页
     * @return 文章分页对象
     */
    public Pagination getPageForMember(Integer channelId, Integer siteId, Integer modelId,
                                       Integer memberId, int pageNo, int pageSize, String state, String policyName, Date startDate, Date endDate, String policyLevel);

    Content save(SIcloudPolicy sd, Content c, ContentExt ext, ContentTxt t, String[] attachmentPaths, String[] attachmentNames, String[] attachmentFilenames, String[] picPaths, String[] picDescs, Integer channelId, Integer typeId, CmsUser user, Short charge, boolean b);


    /**
     *@Author 毛成
     *@Date 2017/1/10 0010 9:26
     *@Desc 更新政策信息
     */
    void update(Integer policyId, SIcloudPolicy sp, String editor, String[] attachmentPaths, String[] attachmentNames, String[] attachmentFilenames, String[] picPaths, String[] picDescs, Integer channelId, CmsUser user, Short charge, boolean b);

    /**
     *@Author 毛成
     *@Date 2017/1/10 0010 9:26
     *@Desc 通过政策id删除政策
     */
    public SIcloudPolicy delete(Integer policyId);

    /**
     *@Author 毛成
     *@Date 2017/1/5 0005 19:33
     *@desc  获取政策明细
     */
    public SIcloudPolicy byPolicyId(Integer policyId);

    /**
     *@Author 毛成
     *@Date 2017/1/5 0005 19:33
     *@desc  修改状态
     */
    public SIcloudPolicy updateState(Integer policyId, String state, Integer channelId, CmsUser user, Short charge, boolean b);

    /**
     *@Author jsz
     *@Date 2017/1/10
     *@desc  云计算政策标签查询
     */
    public List<SIcloudPolicy> getList(Integer count, Integer orderBy, Integer listType,Integer listId);
    /**
     *@Author jsz
     *@Date 2017/1/16
     *@desc  云计算政策标签查询
     */
    public List<SIcloudApply> getApplyList(Integer count, Integer orderBy, Integer listType);

    /**
     * @Author 闫浩芮
     * 置为通过
     * @Date 2017/2/18 0018 11:07
     */
    public void passMany(String demandIdsStr);
    /**
     * @Author 闫浩芮
     * 置为驳回
     * @Date 2017/2/18 0018 11:07
     */
    public void rejectMany(String demandIdsStr);
    /**
     * @Author 闫浩芮
     * 批量删除
     * @Date 2017/2/20 0020 10:37
     */
    public void deleteMany(String demandIdsStr);
}
