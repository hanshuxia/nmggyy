package com.anchorcms.icloud.dao.cloudcenter;

import com.anchorcms.common.page.Pagination;
import com.anchorcms.icloud.model.cloudcenter.SIcloudDemandQuote;

import java.util.Date;

/**
 * @Author 姜舒中
 * @Email netuser.orz@icloud.com
 * @Date 2017/1/3 0003
 * @Desc
 */
public interface CloudCenterOrderDao {
    public Pagination getPageBySelf(Integer channelId, Integer siteId, Integer modelId, Integer UserId, Integer memberId, int pageNo, int pageSize, Date releaseDt, Date deadlineDt, Integer demandObjId, String demandName, String status, String payType);

    /**
     * @Author jiangshuzhong
     * @Email netuser.orz@icloud.com
     * @Date 2016/12/28
     * @Desc 需求详情
     */
    public SIcloudDemandQuote byDemandObjId(Integer demandObjId);
}
