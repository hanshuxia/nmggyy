package com.anchorcms.icloud.service.supplychain.impl;

import com.anchorcms.icloud.dao.supplychain.SpareDemandDao;
import com.anchorcms.icloud.model.supplychain.SSpareDemand;
import com.anchorcms.icloud.service.supplychain.SpareDemandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
*@Author 潘晓东
*@Date 2017/1/23 14:01
*@Return备品备件求购
*/
@Service
@Transactional
public class SpareDemandServiceImpl implements SpareDemandService {
    /**
    *@Author 潘晓东
    *@Date 2017/1/23 14:02
    *@Return根据ID获取备品备件求购信息
    */
    public SSpareDemand selectById(String id) {
        SSpareDemand sSpareDemand = spareDemandDao.selectById(id);
        return sSpareDemand;
    }

    @Autowired
    private SpareDemandDao spareDemandDao;
}
