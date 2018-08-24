package com.anchorcms.icloud.dao.logistics;

import com.anchorcms.cms.model.main.Content;
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
 * 物流订单dao
 */
public interface LogisticsDao {

    /**
     * @author zhouyq
     * @Date 2017/6/30
     * @return
     * 物流订单保存
     */
    public SLogistics creLogisticsEntity(SLogistics sLogistics);

    public Pagination getPageBySelf(String title, Integer typeId,
                                    Integer inputUserId, boolean topLevel, boolean recommend,
                                    Content.ContentStatus status, Byte checkStep, Integer siteId,
                                    Integer channelId, Integer userId, int orderBy, int pageNo,
                                    int pageSize,String txlogisticId, Date createOrderTime, String logisticsOrderState);

    public Pagination getPage(Integer siteId, CmsUser user, int pageNo, int pageSize, Date createOrderTime, String logisticsOrderState, String txlogisticId);

    SLogistics save(SLogistics buy);
    public SLogistics bySAbilityId(String txlogisticId);
    public SLogistics getOrderById(String id);
}
