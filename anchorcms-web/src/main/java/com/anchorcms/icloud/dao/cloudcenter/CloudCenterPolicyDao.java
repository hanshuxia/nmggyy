package com.anchorcms.icloud.dao.cloudcenter;

import com.anchorcms.cms.model.main.Content;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.icloud.model.cloudcenter.SIcloudApply;
import com.anchorcms.icloud.model.cloudcenter.SIcloudPolicy;

import java.sql.Date;
import java.util.List;


public interface CloudCenterPolicyDao {

    /***
     * @author maocheng
     * @date 2017/01/04
     * @param typeId 内容类型ID
     * @param userId 用户ID
     * @param topLevel 是否固顶
     * @param recommend 是否推荐
     * @param checkStep 审核步骤
     * @param siteId 站点ID
     * @param channelId 栏目ID
     * @param pageNo 页数
     * @param pageSize 每页大小
     * @param state 状态
     * @param policyName 政策名称
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @param policyLevel 政策级别
     * @desc 云资源交易中心-云计算政策列表
     * @return
     */
    public Pagination getPageBySelf(Integer typeId, Integer userId, boolean topLevel, boolean recommend,
                                    Content.ContentStatus contentStatus, Byte checkStep, Integer siteId,
                                    Integer channelId, int pageNo, int pageSize, String state, String policyName, Date startDate, Date endDate, String policyLevel);

    SIcloudPolicy save(SIcloudPolicy sd);

    /**
     *@Author maocheng
     *@Date 2017/1/10 0010 9:26
     *@Desc 获取需要删除的政策id
     */
    public SIcloudPolicy findById(Integer policyId);

    /**
     *@Author maocheng
     *@Date 2017/1/10 0010 9:26
     *@Desc 通过政策id删除政策
     */
    public SIcloudPolicy deleteById(Integer policyId);

    /**
     *@Author maocheng
     *@Date 2017/1/10 0010 9:26
     *@Desc 更新政策信息
     */
    public SIcloudPolicy update(SIcloudPolicy software);

    /**
     *@Author maocheng
     *@Date 2017/1/5 0005 17:38
     *@desc 获取政策明细列表
     */
    public SIcloudPolicy byPolicyId(Integer policyId);
    /**
     *@Author jsz
     *@Date 2017/1/10
     *@desc 云计算政策标签查询
     */
    public List<SIcloudPolicy> getList(Integer count, Integer orderBy, Integer listType,Integer listId);
    /**
     *@Author jsz
     *@Date 2017/1/16
     *@desc 云计算政策标签查询
     */
    public List<SIcloudApply> getApplyList(Integer count, Integer orderBy, Integer listType);
    /**
     * @Author 闫浩芮
     * 置为通过
     * @Date 2017/2/17 0017 17:25
     */
    public int passMany(String demandIdsStr);
    /**
     * @Author 闫浩芮
     * 置为驳回
     * @Date 2017/2/17 0017 18:40
     */
    public int rejectMany(String demandIdsStr);
    /**
     * @Author 闫浩芮
     * 批量删除
     * @Date 2017/2/20 0020 10:35
     */
    public int deleteMany(String demandIdsStr);
}
