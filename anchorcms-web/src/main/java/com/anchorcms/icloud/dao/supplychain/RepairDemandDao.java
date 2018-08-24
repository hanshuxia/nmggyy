package com.anchorcms.icloud.dao.supplychain;


import com.anchorcms.icloud.model.supplychain.SRepairDemand;

import java.util.List;
/**
*@Author 潘晓东
*@Date 2017/1/23 13:43
*@Return查询维修需求表
*/
public interface RepairDemandDao {
    public SRepairDemand selectReapirDemand(String id);

    /**
    *@Author 潘晓东
    *@Date 2017/1/23 13:44
    *@Return根据ID查询维修需求表
    */
    public List<SRepairDemand> selectReapirDemandById(String id);
    /**
     * Created by  hansx
     * 添加发布信息
     * @param srr
     * @return
     */
    public SRepairDemand save(SRepairDemand srr);
}
