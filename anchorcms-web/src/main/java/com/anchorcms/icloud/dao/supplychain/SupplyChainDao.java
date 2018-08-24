package com.anchorcms.icloud.dao.supplychain;

import com.anchorcms.icloud.model.supplychain.SRepairRes;
import com.anchorcms.icloud.model.supplychain.SSpare;

import java.util.List;

/**
 * Created by DELL on 2017/1/3.
 */
public interface SupplyChainDao {
    List<SSpare> getAll();

    List<SRepairRes> getAl(String name, String place, String type);
    List<SRepairRes> getAl();
    List<SRepairRes> getAl(String uid);
    int delSupply(String delNum);
    int updateSupply(String uid);
}
