package com.anchorcms.icloud.dao.supplychain;


import com.anchorcms.icloud.model.supplychain.SSpareDemandObj;

import java.util.List;

/**
*@Author 潘晓东
*@Date 2017/1/23 13:48
*@Return备品备件求购对象查询
*/
public interface SpareDemandObjDao {
    //备品备件求购对象根据备件ID查询
    public List<SSpareDemandObj> selectSSpareDemandObjEntity(String demandId);
}
