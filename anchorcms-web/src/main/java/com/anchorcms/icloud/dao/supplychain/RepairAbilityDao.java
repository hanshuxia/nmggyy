package com.anchorcms.icloud.dao.supplychain;



import com.anchorcms.icloud.model.supplychain.SRepairAbility;

import java.util.List;

/**
*@Author 潘晓东
*@Date 2017/1/23 13:41
*@Return抢单报价对象获取
*/
public interface RepairAbilityDao {
    public List<SRepairAbility> selectByQuoteId(String id);
}
