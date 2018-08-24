package com.anchorcms.icloud.service.logistics;

import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.model.logistics.SLogistics;
import com.anchorcms.icloud.model.payment.SPOrder;
import com.anchorcms.icloud.model.synergy.SAbility;

import java.util.Date;

/**
 * @author zhouyq
 * @Date 2017/6/30
 * @return
 * 物流订单service
 */
public interface LogisticsService {

    /**
     * @author zhouyq
     * @Date 2017/6/30
     * @return
     * 物流订单保存
     */
    public SLogistics creLogisticsEntity(SLogistics sLogistics);


    public Pagination getPageForMember(String title, Integer channelId,
                                       Integer siteId, Integer modelId, Integer memberId,
                                       int pageNo, int pageSize, String txlogisticId, Date createOrderTime, String logisticsOrderState);

    public Pagination getSyneryListPage(Integer siteId, CmsUser user, int pageNo, int pageSize, Date createOrderTime, String logisticsOrderState, String txlogisticId);

    void saveOrder(SLogistics buy);
    public SLogistics byAbilityId(String txlogisticId);
    SLogistics findOrderById(String txlogisticId);
}
