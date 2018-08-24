package com.anchorcms.icloud.service.logistics.impl;

import com.anchorcms.cms.model.main.Content;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.dao.logistics.LogisticsDao;
import com.anchorcms.icloud.model.logistics.SLogistics;
import com.anchorcms.icloud.model.payment.SPOrder;
import com.anchorcms.icloud.model.synergy.SAbility;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anchorcms.icloud.service.logistics.LogisticsService;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author zhouyq
 * @Date 2017/6/30
 * @return
 * 物流订单service实现类
 */
@Service
@Transactional
public class LogisticsServiceImpl implements LogisticsService {

    public SLogistics creLogisticsEntity(SLogistics sLogistics) {
        return logisticsDao.creLogisticsEntity(sLogistics);
    }
    public Pagination getPageForMember(String title, Integer channelId, Integer siteId, Integer modelId, Integer memberId, int pageNo, int pageSize, String txlogisticId, Date createOrderTime, String logisticsOrderState) {
        return logisticsDao.getPageBySelf(title, null,memberId, false, false, Content.ContentStatus.all, null, siteId,modelId,  channelId, 0, pageNo,pageSize,txlogisticId,createOrderTime,logisticsOrderState);
    }

    public Pagination getSyneryListPage(Integer siteId, CmsUser user, int pageNo, int pageSize, Date createOrderTime, String logisticsOrderState, String txlogisticId) {
        return logisticsDao.getPage(siteId, user, pageNo, pageSize, createOrderTime, logisticsOrderState, txlogisticId);
    }

    public void saveOrder(SLogistics buy) {
        logisticsDao.save(buy);
    }
    public SLogistics byAbilityId(String txlogisticId) {
        return logisticsDao.bySAbilityId(txlogisticId);
    }
    public SLogistics findOrderById(String txlogisticId) {
        return logisticsDao.getOrderById(txlogisticId);
    }
    @Resource
    private LogisticsDao logisticsDao;
}

