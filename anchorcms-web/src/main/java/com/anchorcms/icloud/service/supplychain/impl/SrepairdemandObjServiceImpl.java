package com.anchorcms.icloud.service.supplychain.impl;

import com.anchorcms.icloud.dao.supplychain.SrepairdemandObjDao;
import com.anchorcms.icloud.model.supplychain.SRepairDemandObj;
import com.anchorcms.icloud.service.supplychain.SrepairdemandObjService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @Author zhouyq
 * @Date 2016/12/26
 * @Desc 众修资源管理_管理员服务实现类
 * */
@Service
@Transactional
public class SrepairdemandObjServiceImpl implements SrepairdemandObjService {
    @Resource
    private SrepairdemandObjDao dao;

    /**
     * @Author zhouyq
     * @Date 2016/12/26
     * @param repairId
     * @return
     * @Desc 根据id获取众修资源明细信息
     */
    public SRepairDemandObj findDetailAndPreviewByIdDemandObj(String repairId) {
        SRepairDemandObj oDemand = dao.findDetailAndPreviewByIdDemandObj(repairId);
        return oDemand;
    }

}
