package com.anchorcms.icloud.dao.synergy;

/**
 * Copyright @ 2017 Shandong jxdinfo software co,ltd.
 * All right reserved.
 * Created by Gao Jiangning
 *
 * @Date 2017/2/6 0006
 * @Desc 报价的单条报价对象dao接口（用于计算已分配数量）
 */
public interface SDemandQuoteObjDao {

    /**
     * 计算需求对象的已分配数量
     * @param demandObjId
     * @return 已分配量(double)
     */
    Double getSumDistriByDemandObjId(Integer demandObjId);
}
