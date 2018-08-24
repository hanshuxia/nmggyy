package com.anchorcms.icloud.dao.cloudcenter;

import com.anchorcms.common.page.Pagination;
import com.anchorcms.icloud.model.cloudcenter.SIcloudDemandQuote;

import java.util.Date;

/**
 * @Author wanjinyou
 * @Date 2017/2/20
 * @Desc 云资源交易中心-管理员-我的方案报价
 */
public interface CloudCenterMyInquiryDao  {

    /**
     * @Author wanjinyou
     * @Date 2017/2/20
     * @Desc 管理员--我的方案报价--列表
     */
   public Pagination getPageBySelf(Integer queryChannelId, Integer siteId, Integer modelId, Integer userId,String selectedStatus, Integer memberId, int pageNo, int pageSize, Date releaseDt, Date deadlineDt, Integer demandId,String statusType);

    /**
     * @param id
     * @Author wanjinyou
     * @Date 2017/2/21
     * @Description 管理员--我的方案报价--删除
     */
    public SIcloudDemandQuote deleteById(Integer id);
    /**
     * @param id
     * @Author wanjinyou
     * @Date 2017/2/21
     * @Description 管理员--我的方案报价--获取报价信息
     */
    public SIcloudDemandQuote findById(Integer id);
}
