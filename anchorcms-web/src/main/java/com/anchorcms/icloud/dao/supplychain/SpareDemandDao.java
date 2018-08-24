package com.anchorcms.icloud.dao.supplychain;


import com.anchorcms.icloud.model.supplychain.SSpareDemand;

/**
*@Author 潘晓东
*@Date 2017/1/23 13:48
*@Return备品备件求购详情
*/
public interface SpareDemandDao {
    //备品备件求购根据ID详细查询
    public SSpareDemand selectById(String id);
}
