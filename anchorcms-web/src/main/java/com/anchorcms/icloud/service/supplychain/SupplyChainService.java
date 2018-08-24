package com.anchorcms.icloud.service.supplychain;

import com.anchorcms.icloud.model.supplychain.SRepairRes;
import com.anchorcms.icloud.model.supplychain.SSpare;

import java.util.List;

/**
 * Created by DELL on 2017/1/3.
 */
public interface SupplyChainService {
    public List<SSpare> getList();
    public List<SRepairRes> getAl(String name, String place, String type);
    public List<SRepairRes> getAl();
    public int delSupply(String delNum);
    public List<SRepairRes> getAl(String uid);
    public int updateSupply(String uid);
}
