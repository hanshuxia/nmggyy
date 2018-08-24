package com.anchorcms.icloud.service.supplychain.impl;

import com.anchorcms.icloud.dao.supplychain.SRepairResDao;
import com.anchorcms.icloud.model.supplychain.SRepairRes;
import com.anchorcms.icloud.service.supplychain.SRepairResService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by 刘鹏 on 2016/12/21.
 */
@Service
@Transactional
public class SRepairResServiceImpl implements SRepairResService {
    public SRepairRes findById(String id){
        return dao.findById(id);
    }
    public List<SRepairRes> findJplsfById(String id){
        return dao.findJplsfById(id);
    }
    public int updateSRepairRes(SRepairRes sRepairRes) {
        return dao.update(sRepairRes);
    }

    @Resource
    private SRepairResDao dao;
}
