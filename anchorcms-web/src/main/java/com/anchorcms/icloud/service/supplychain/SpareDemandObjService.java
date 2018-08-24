package com.anchorcms.icloud.service.supplychain;



import com.anchorcms.icloud.model.supplychain.SSpareDemandObj;

import java.util.List;


/**
 * Created by DELL on 2016/12/21.
 */
public interface SpareDemandObjService {
    public List<SSpareDemandObj> selectSSpareDemandObjEntity(String demandId);
}
