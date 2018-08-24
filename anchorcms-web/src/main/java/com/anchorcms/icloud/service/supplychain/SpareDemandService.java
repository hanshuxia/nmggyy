package com.anchorcms.icloud.service.supplychain;


import com.anchorcms.icloud.model.supplychain.SSpareDemand;

/**
 * Created by DELL on 2016/12/21.
 */
public interface SpareDemandService {
    public SSpareDemand selectById(String demandId);
}
