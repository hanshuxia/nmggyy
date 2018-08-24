package com.anchorcms.icloud.service.supplychain.impl;

import com.anchorcms.icloud.dao.supplychain.SpareDemandObjDao;
import com.anchorcms.icloud.model.supplychain.SSpareDemandObj;
import com.anchorcms.icloud.service.supplychain.SpareDemandObjService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
*@Author 潘晓东
*@Date 2017/1/23 14:01
*@Return备品备件求购对象
*/
@Service
@Transactional
public class SpareDemandObjServiceImpl implements SpareDemandObjService {

    @Autowired
    private SpareDemandObjDao spareDemandObjDao;
    /**
    *@Author 潘晓东
    *@Date 2017/1/10 13:23
    *@Return备品备件对象查询
    */
    public List<SSpareDemandObj> selectSSpareDemandObjEntity(String demandId) {
        return spareDemandObjDao.selectSSpareDemandObjEntity(demandId);
    }
}
