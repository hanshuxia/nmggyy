package com.anchorcms.icloud.dao.cloudcenter;

import com.anchorcms.common.page.Pagination;
import com.anchorcms.icloud.model.cloudcenter.SIcloudManage;

import java.util.Date;
import java.util.List;

/**
 * @Author 姜舒中
 * @Email netuser.orz@icloud.com
 * @Date 2017/1/7 0007
 * @Desc
 */
public interface CloudCenterResourceDao {
    public Pagination getPageBySelf(Integer channelId, Integer siteId, Integer modelId, Integer UserId, Integer memberId, int pageNo, int pageSize, Date startDt, Date endDt, Integer managerId, String resourceType, String state, String addrCity);
    /**
     * @Author jsz
     * managerId
     * @Date 2016/1/7
     */
    public SIcloudManage findById(Integer managerId);

    /**
     * @Author jsz
     * 删除需求信息
     * @Date 2016/1/7
     */
    public SIcloudManage delete(SIcloudManage sIcloudManage);

    /**
     * @Author jsz
     * 更新云资源信息
     * @Date 2016/1/7
     */
    public SIcloudManage update(SIcloudManage sIcloudManage);
    /**
     *@Author jsz
     *@Date 2017/1/10
     *@desc 云资源标签查询
     */
    public List<SIcloudManage> getList(Integer count, Integer orderBy, Integer listType,Integer areaType,Integer state);

    /**
     * @param id
     * @Author wanjinyou
     * @Date 2016/1/20
     * @Desc 云资源管理删除 【用户公司一条需求的明细】
     */
    public SIcloudManage deleteById(Integer id);

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
