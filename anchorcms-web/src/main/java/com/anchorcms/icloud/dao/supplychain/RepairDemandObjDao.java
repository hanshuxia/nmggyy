package com.anchorcms.icloud.dao.supplychain;


import com.anchorcms.icloud.model.supplychain.SRepairDemandObj;

import java.util.List;

/**
*@Author 潘晓东
*@Date 2017/1/23 10:01
*@Return维修需求对象获取
*/
public interface RepairDemandObjDao {
    /**
    *@Author 潘晓东
    *@Date 2017/1/23 10:03
    *@Return根据ID获取维修需求对象表中的数据
    */
    public List<SRepairDemandObj> selectRepairDemand(String id);

    /**
     * Created by  hansx
     * 添加发布信息
     * @param srr
     * @return
     */
    public SRepairDemandObj save(SRepairDemandObj srr);
}
