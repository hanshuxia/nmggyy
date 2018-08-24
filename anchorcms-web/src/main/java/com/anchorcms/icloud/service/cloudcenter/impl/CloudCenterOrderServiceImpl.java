package com.anchorcms.icloud.service.cloudcenter.impl;

import com.anchorcms.common.page.Pagination;
import com.anchorcms.icloud.dao.cloudcenter.CloudCenterDemandDao;
import com.anchorcms.icloud.dao.cloudcenter.CloudCenterOrderDao;
import com.anchorcms.icloud.model.cloudcenter.SIcloudDemand;
import com.anchorcms.icloud.model.cloudcenter.SIcloudDemandQuote;
import com.anchorcms.icloud.service.cloudcenter.CloudCenterOrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Author 姜舒中
 * @Email netuser.orz@icloud.com
 * @Date 2017/1/3 0003
 * @Desc
 */
@Service
@Transactional
public class CloudCenterOrderServiceImpl implements CloudCenterOrderService {
    public Pagination getPageForMember(Integer channelId, Integer siteId, Integer modelId, Integer UserId,
                                       Integer memberId, int pageNo, int pageSize, Date releaseDt, Date deadlineDt, Integer demandObjId, String demandName,
                                       String status, String payType) {
        return dao.getPageBySelf(channelId, siteId, modelId, UserId, memberId, pageNo, pageSize, releaseDt, deadlineDt, demandObjId, demandName, status, payType);
    }

    /**
     * @param demandObjId 文章id
     * @Author iangshuzhong
     * @Date 2016/1/6
     * @Desc 需求管理详情
     */
    public SIcloudDemandQuote byDemandObjId(Integer demandObjId) {
        return dao.byDemandObjId(demandObjId);
    }

    @Resource
    private CloudCenterOrderDao dao;
}
