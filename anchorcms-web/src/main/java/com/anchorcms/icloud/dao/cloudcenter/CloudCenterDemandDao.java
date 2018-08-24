package com.anchorcms.icloud.dao.cloudcenter;

import com.anchorcms.common.page.Pagination;
import com.anchorcms.icloud.model.cloudcenter.SIcloudDemand;
import com.anchorcms.icloud.model.cloudcenter.SIcloudManage;

import java.util.Date;
import java.util.List;

/**
 * @Author 姜舒中
 * @Email netuser.orz@icloud.com
 * @Date 2017/1/3 0003
 * @Desc
 */
public interface CloudCenterDemandDao {
    public Pagination getPageBySelf(Integer channelId, Integer siteId, Integer modelId, Integer UserId, Integer memberId, int pageNo, int pageSize, Date releaseDt, Date deadlineDt, Integer demandId, String demandName, String status, String payType);

    /**
     * @Author jiangshuzhong
     * @param demandId
     * @Email netuser.orz@icloud.com
     * @Date 2016/1/5
     * @Desc 通过ID获取需求信息
     */
    public SIcloudDemand bySIcloudDemandId(Integer demandId);

    /**
     * 更新数据
     * @param SIcloudDemand
     * @return
     */
    public SIcloudDemand update(SIcloudDemand SIcloudDemand);

    /** @Author wanjinyou
     * @Date 2017/1/10
     * @Desc 云需求管理列表--编辑保存
     */
    public SIcloudDemand editSave(SIcloudDemand icloudDemand);

    /** @Author wanjinyou
      * @Date 2017/1/10
      * @Desc 云需求管理列表--删除
      */
    public SIcloudDemand deleteById(Integer id);

    /**
     *@Author jsz
     *@Date 2017/1/10
     *@desc 云需求标签查询
     */
    public List<SIcloudDemand> getList(Integer count, Integer orderBy, Integer listType,Integer listId);

    public Integer changeDemandStatus(Integer demandId, String status);
}
