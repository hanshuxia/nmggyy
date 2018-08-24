package com.anchorcms.icloud.dao.cloudcenter;

import com.anchorcms.cms.model.main.Content;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.icloud.model.cloudcenter.SIcloudDemandQuote;
import com.anchorcms.icloud.model.cloudcenter.SIcloudQuoteManage;

import java.util.Date;

/**
 * @Author 阁楼麻雀
 * @Email netuser.orz@icloud.com
 * @Date 2016/12/20
 * @Desc
 */
public interface CloudCenterInquiryDao {

    /***
     * @author maocheng
     * @date 2017/1/4
     * @param typeId 内容类型ID
     * @param userId 用户ID
     * @param topLevel 是否固顶
     * @param recommend 是否推荐
     * @param checkStep 审核步骤
     * @param siteId 站点ID
     * @param channelId 栏目ID
     * @param pageNo 页数
     * @param pageSize 每页大小
     * @param demandObjId 查询订单编号
     * @param startDate 查询开始时间
     * @param endDate 查询结束时间
     * @param demandName 查询采购商
     * @param quoteState 报价方标签状态
     * @desc 管理员--我的方案报价
     * @return
     */
    public Pagination getPageBySelf(Integer typeId, Integer userId, boolean topLevel, boolean recommend,
                                    Content.ContentStatus contentStatus, Byte checkStep, Integer siteId,
                                    Integer channelId, int pageNo, int pageSize,String demandObjId, Date startDate,
                                    Date endDate, String demandName, String quoteState);

    /**
     * @Author maocheng
     * @Email netuser.orz@icloud.com
     * @Date 2017/1/6
     * @Desc 需求详情
     */
    public SIcloudDemandQuote byDemandObjId(Integer demandObjId);

    /**@Author wanjinyou
     * @Desc 我是需求--需求订单管理
     * @Date 2017/1/12
     */
    Pagination getDeamdOrder(Integer typeId, Integer userId, boolean topLevel, boolean recommend,
                             Content.ContentStatus contentStatus, Byte checkStep, Integer siteId,
                             Integer channelId, int pageNo, int pageSize,String demandObjId, Date startDate,
                             Date endDate, String companyName, String demandState);


}
