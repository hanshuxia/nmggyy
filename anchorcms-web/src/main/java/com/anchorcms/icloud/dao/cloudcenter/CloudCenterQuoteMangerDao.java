package com.anchorcms.icloud.dao.cloudcenter;

import com.anchorcms.icloud.model.cloudcenter.SIcloudDemand;
import com.anchorcms.icloud.model.cloudcenter.SIcloudQuoteManage;

/**
 * @anther 李利民
 * @descript
 * @data 2017/1/1115:41
 */
public interface CloudCenterQuoteMangerDao {
    void save(SIcloudQuoteManage manager);


    /** @Author wanjinyou
     * @Desc 报价管理表--更新状态
     * @Date 2017/1/12
     */
    SIcloudQuoteManage byDemandId(Integer id);

    /** @Author wanjinyou
     * @Desc 报价管理表--明细
     * @Date 2017/1/13
     */
    SIcloudQuoteManage findById(Integer id);

    /**
     * @param id
     * @Author wanjinyou
     * @Desc 报价管理表--删除
     * @Date 2017/1/13
     */
    SIcloudQuoteManage deleteById(Integer id);

    public SIcloudQuoteManage update(SIcloudQuoteManage quoteManage);

    public SIcloudQuoteManage  byDemand(Integer id);

    /**
     * 根据demandID查询订单数
     * @param demandId
     * @return
     */
    Integer countByDemandId(Integer demandId);
}
