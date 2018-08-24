package com.anchorcms.icloud.service.cloudcenter;

import com.anchorcms.common.page.Pagination;
import com.anchorcms.icloud.model.cloudcenter.SIcloudDemandQuote;

import java.util.Date;

/**
 * @Author wanjinyou
 * @Date 2017/2/20
 * @Desc 管理员--我的方案报价
 */
public interface CloudCenterMyInquiryService {

    /**
     * @Author wanjinyou
     * @Date 2017/2/20
     * @Desc 管理员--我的方案报价--列表
     */
    public Pagination getPageForMember(Integer queryChannelId, Integer siteId, Integer modelId, Integer userId,String selectedStatus, Integer userId1, int cpn, int i, Date releaseDt, Date deadlineDt, Integer demandId,String statusType);

    /**
     * @Author wanjinyou
     * @Date 2017/2/20
     * @Desc 管理员--我的方案报价--报价详情
     */
    String getQuoteDetailJson(Integer quoteId);

    /**
     * @Author wanjinyou
     * @Date 2017/2/21
     * @Description 管理员--我的方案报价--删除
     */
    public SIcloudDemandQuote delete(Integer id);
}
