package com.anchorcms.icloud.service.cloudcenter;

import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.model.cloudcenter.SIcloudDemand;
import com.anchorcms.icloud.model.synergy.SDemand;

import java.util.Date;
import java.util.List;

/**
 * @Author 姜舒中
 * @Email netuser.orz@icloud.com
 * @Date 2017/1/3 0003
 * @Desc
 */
public interface CloudCenterDemandService {
    public Pagination getPageForMember(Integer channelId, Integer siteId, Integer modelId, Integer UserId, Integer memberId, int pageNo, int pageSize, Date releaseDt, Date deadlineDt, Integer demandId, String demandName, String status, String payType);

    /**
     * @Author jiangshuzhong
     * @Email netuser.orz@icloud.com
     * @param demandId 需求id
     * @Date 2016/12/28
     * @Desc 需求详情
     */
    public SIcloudDemand byDemandId(Integer demandId);

    /**
     * @Author wanjinyou
     * @Date 2017/1/10
     * @Desc 云需求管理列表--编辑保存
     */
    public SIcloudDemand editSave(Integer id, SIcloudDemand sd, String detailDesc, String[] attachmentPaths, String[] attachmentNames, String[] attachmentFilenames, String[] picPaths, String[] picDescs, Integer channelId, CmsUser user, Short charge, boolean b);

    /**
     * @Author wanjinyou
     * @Date 2017/1/10
     * @Desc 云需求管理列表--删除
     */
    public SIcloudDemand deleteById(Integer id);

    /**
     * @author jsz
     * @date 2017/1/11
     * @desc 自定义标签——云需求列表获取
     **/
    public List<SIcloudDemand> getList(Integer count, Integer orderBy, Integer listType,Integer listId);

    public Integer changeDemandStatus(Integer demandId, String status);
}
