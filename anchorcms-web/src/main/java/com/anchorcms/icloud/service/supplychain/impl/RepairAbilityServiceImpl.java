package com.anchorcms.icloud.service.supplychain.impl;

import com.anchorcms.icloud.dao.supplychain.RepairAbilityDao;
import com.anchorcms.icloud.model.supplychain.SRepairAbility;
import com.anchorcms.icloud.service.supplychain.RepairAbilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
*@Author 潘晓东
*@Date 2017/1/23 14:00
*@Return抢单报价对象
*/
@Service
@Transactional
public class RepairAbilityServiceImpl implements RepairAbilityService {
    /**
    *@Author 潘晓东
    *@Date 2017/1/23 14:00
    *@Return根据报价ID查询报价对象信息
    */
    public List<SRepairAbility> selectByQuoteId(String id) {
        List<SRepairAbility> list = repairAbilityDao.selectByQuoteId(id);
        return list;
    }
    @Autowired
    private RepairAbilityDao repairAbilityDao;
}
