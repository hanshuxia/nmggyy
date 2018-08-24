package com.anchorcms.icloud.service.cloudcenter.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.anchorcms.cms.dao.main.ContentDao;
import com.anchorcms.cms.service.assist.CmsCommentMng;
import com.anchorcms.cms.service.assist.CmsConsultMng;
import com.anchorcms.cms.service.assist.CmsFileMng;
import com.anchorcms.cms.service.main.*;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.icloud.dao.cloudcenter.CloudCenterDemandDao;
import com.anchorcms.icloud.dao.cloudcenter.CloudCenterMyInquiryDao;
import com.anchorcms.icloud.dao.cloudcenter.CloudCenterQuoteDao;
import com.anchorcms.icloud.model.cloudcenter.SIcloudDemand;
import com.anchorcms.icloud.model.cloudcenter.SIcloudDemandQuote;
import com.anchorcms.icloud.service.cloudcenter.CloudCenterMyInquiryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Author wanjinyou
 * @Date 2017/2/20
 * @Desc 云资源交易中心-管理员-我的方案报价
 */
@Service
@Transactional
public class CloudCenterMyInquiryServiceImpl implements CloudCenterMyInquiryService {

    /**
     * @Author wanjinyou
     * @Date 2017/2/20
     * @Desc 管理员--我的方案报价--列表
     */
    public Pagination getPageForMember(Integer queryChannelId, Integer siteId, Integer modelId, Integer userId, String selectedStatus, Integer memberId, int pageNo, int pageSize, Date releaseDt, Date deadlineDt, Integer demandId, String statusType) {
        return dao.getPageBySelf(queryChannelId, siteId, modelId, userId, selectedStatus, memberId, pageNo, pageSize, releaseDt, deadlineDt, demandId, statusType);
    }

    /**
     * @param quoteId
     * @Author wanjinyou
     * @Date 2017/2/20
     * @Desc 管理员--我的方案报价--报价详情
     */
    public String getQuoteDetailJson(Integer quoteId) {

        SIcloudDemandQuote demandQuote = cloudCenterQuoteDao.findById(quoteId);
        SIcloudDemand demand = demandQuote.getIcloudDemand();

        JSONObject jObj = new JSONObject();
        java.text.NumberFormat nf = java.text.NumberFormat.getInstance();
        nf.setGroupingUsed(false);
        jObj.fluentPut("objName", demand.getDemandName())//需求名称
                .fluentPut("objCode", demand.getClassify_code()) //需求编码
                .fluentPut("amount", nf.format(demand.getCount())) //数量
                .fluentPut("unit", demand.getUnit()) //计量单位
                .fluentPut("expPrice", nf.format(demand.getExpect_price())) //期望单价(元)
                .fluentPut("qPrice", nf.format(demandQuote.getPrice())); //报价单价（元）
        String jsonString2 = JSON.toJSONString(jObj);
        return jsonString2;
    }

    /**
     * @param id
     * @Author wanjinyou
     * @Date 2017/2/21
     * @Description 管理员--我的方案报价--删除
     */
    public SIcloudDemandQuote delete(Integer id) {
        SIcloudDemandQuote bean = dao.findById(id);
        dao.deleteById(id);
        return bean;
    }

    @Resource
    private CloudCenterMyInquiryDao dao;
    @Resource
    private CloudCenterDemandDao cloudCenterDemandDao;
    @Resource
    private CloudCenterQuoteDao cloudCenterQuoteDao;
    @Resource
    private ChannelMng channelMng;
    @Resource
    private ContentTypeMng contentTypeMng;
    @Resource
    private List<ContentListener> listenerList;
    @Resource
    private ContentTagMng contentTagMng;
    @Resource
    private CmsCommentMng cmsCommentMng;
    @Resource
    private CmsFileMng fileMng;
    @Resource
    private ContentTxtMng contentTxtMng;
    @Resource
    private ContentExtMng contentExtMng;
    @Resource
    private ContentCountMng contentCountMng;
    @Resource
    private ContentCheckMng contentCheckMng;
    @Resource
    private ContentDao contentDao;
    @Resource
    private CmsConsultMng cmsConsultMng;

}
