package com.anchorcms.icloud.service.supplychain;


import com.anchorcms.icloud.model.supplychain.SRepairRes;

import java.util.List;

/**
 * Created by 刘鹏 on 2016/12/21.
 */
public interface SRepairResService {
    public SRepairRes findById(String id);
    public List<SRepairRes> findJplsfById(String id);
    public int updateSRepairRes(SRepairRes sRepairRes);

}
