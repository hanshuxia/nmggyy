package com.anchorcms.icloud.service.supplychain;


import com.anchorcms.icloud.model.supplychain.SRepairAbility;
import org.springframework.stereotype.Service;

import java.util.List;

/**
*@Author 潘晓东
*@Date 2017/1/23 13:52
*@Return抢单报价对象
*/
public interface RepairAbilityService {
    public List<SRepairAbility> selectByQuoteId(String id);
}
