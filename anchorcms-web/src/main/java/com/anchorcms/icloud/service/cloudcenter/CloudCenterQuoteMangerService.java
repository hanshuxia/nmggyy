package com.anchorcms.icloud.service.cloudcenter;

import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.model.cloudcenter.SIcloudDemand;
import com.anchorcms.icloud.model.cloudcenter.SIcloudQuoteManage;

/**
 * @anther 李利民
 * @descript
 * @data 2017/1/1115:37
 */
public interface CloudCenterQuoteMangerService {
    /**
     * 保存报价管理表
     * @param manager
     */
    void save(SIcloudQuoteManage manager);

    /**
     * 由前台传来的json保存报价管理（订单？？）
     */
    Integer saveByJson(SIcloudDemand demand, String orderJson, CmsUser user);

    /**
     * @Author wanjinyou
     * @Desc 报价管理表--更新状态
     * @Date 2017/1/12
     */
    void updateState(Integer id, String demandState, String quoteState, Integer channelId, CmsUser user, Short charge, boolean b);

    /**
     * @Author wanjinyou
     * @Desc 报价管理表--明细
     * @Date 2017/1/13
     */
    SIcloudQuoteManage findById(Integer id);

    /**
     * @Author wanjinyou
     * @Desc 报价管理表--删除
     * @Date 2017/1/13
     */
    SIcloudQuoteManage deleteById(Integer id);

    /**
     * @Author wanjinyou
     * @Desc 报价管理表--撤单
     * @Date 2017/1/13
     */
    SIcloudQuoteManage remove(Integer id, Integer demandId, Integer channelId, CmsUser user, Short charge, boolean b);

    /**
     * 根据demandID查询订单数
     *
     * @param demandId
     * @return
     */
    Integer countByDemandId(Integer demandId);
}
