package com.anchorcms.icloud.dao.supplychain;

import com.anchorcms.icloud.model.supplychain.SRepairDemandObj;

/**
 * @Author zhouyq
 * @Date 2016/12/26
 * @Desc 众修资源管理_管理员Dao层接口
 */
public interface SrepairdemandObjDao {

    /**
     * @Author zhouyq
     * @Date 2016/12/26
     * @param repairId
     * @return
     * @Desc 根据id获取众修资源对象实体类信息
     */
    public SRepairDemandObj findDetailAndPreviewByIdDemandObj(String repairId);

}
