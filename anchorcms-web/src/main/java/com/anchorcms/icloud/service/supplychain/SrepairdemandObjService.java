package com.anchorcms.icloud.service.supplychain;

import com.anchorcms.icloud.model.supplychain.SRepairDemandObj;

/**
 * @Author zhouyq
 * @Date 2016/12/29
 * @Desc 众修资源管理_管理员服务层接口
 * */
public interface SrepairdemandObjService {

    /**
     * @Author zhouyq
     * @Date 2016/12/29
     * @param repairId
     * @return
     * @Desc 根据id获取众修资源明细或预览信息
     */
    public SRepairDemandObj findDetailAndPreviewByIdDemandObj(String repairId);

}
