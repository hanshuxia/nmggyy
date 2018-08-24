package com.anchorcms.icloud.service.cloudcenter.impl;

import com.anchorcms.cms.model.main.Content;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.icloud.dao.cloudcenter.CloudCenterInquiryDao;
import com.anchorcms.icloud.model.cloudcenter.SIcloudDemandQuote;
import com.anchorcms.icloud.service.cloudcenter.CloudCenterInquiryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;


/**
 * @Author 阁楼麻雀
 * @Email netuser.orz@icloud.com
 * @Date 2016/12/19
 * @Desc
 */
@Service
@Transactional
public class CloudCenterInquiryServiceImpl implements CloudCenterInquiryService {

    /**
     * @param siteId
     * @param modelId
     * @param demandObjId
     * @param startDate
     * @param endDate
     * @param companyName
     * @param demandState //需方状态位
     * @Author wanjinyou
     * @Desc 我是需求--需求订单管理
     * @Date 2017/1/12
     */
    public Pagination getDeamdOrder(Integer channelId, Integer siteId, Integer modelId, Integer memberId,
                                    int pageNo, int pageSize, String demandObjId, Date startDate, Date endDate, String companyName, String demandState) {
        return dao.getDeamdOrder(null, memberId, false, false, Content.ContentStatus.all, null,
                siteId, channelId, pageNo, pageSize, demandObjId, startDate, endDate, companyName, demandState);
    }

    /**
     * @param channelId   栏目ID
     * @param siteId      站点ID
     * @param modelId     modelId
     * @param memberId    会员ID
     * @param pageNo      页数
     * @param pageSize    每页大小
     * @param demandObjId 查询订单编号
     * @param startDate   查询开始时间
     * @param endDate     查询结束时间
     * @param quoteState  报价方标签状态
     * @return 文章分页对象
     * @author maocheng
     * @date 2017-1-4
     * @desc 云资源交易中心-管理员--我的方案报价列表
     */
    public Pagination getPageForMember(Integer channelId, Integer siteId, Integer modelId, Integer memberId,
                                       int pageNo, int pageSize, String demandObjId, Date startDate, Date endDate, String demandName, String quoteState) {
        return dao.getPageBySelf(null, memberId, false, false, Content.ContentStatus.all, null,
                siteId, channelId, pageNo, pageSize, demandObjId, startDate, endDate, demandName, quoteState);
    }

    /**
     * @param demandObjId 文章id
     * @Author maocheng
     * @Date 2017/1/6
     * @Desc 需求管理详情
     */
    public SIcloudDemandQuote byDemandObjId(Integer demandObjId) {
        return dao.byDemandObjId(demandObjId);
    }

    @Resource
    private CloudCenterInquiryDao dao;

}
