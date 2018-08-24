package com.anchorcms.icloud.service.supplychain;


import com.anchorcms.cms.model.main.Content;
import com.anchorcms.cms.model.main.ContentExt;
import com.anchorcms.cms.model.main.ContentTxt;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.model.supplychain.SRepairDemand;
import com.anchorcms.icloud.model.supplychain.SRepairDemandObj;
import com.anchorcms.icloud.model.supplychain.SRepairDemandObjPageBean;

import java.util.List;

/**
*@Author 潘晓东
*@Date 2017/1/23 13:52
*@Return维修需求对象
*/
public interface RepairDemandObjService {
    /**
    *@Author 潘晓东
    *@Date 2017/1/23 13:53
    *@Return根据维修需求ID查询维修对象信息
    */
    public List<SRepairDemandObj> selcetByRepairDemandId(String id);

    /**
     * @author hansx
     * @Date 2017/1/5 0005 下午 4:00
     * @return
     * 维修需求对象信息保存
     */
    Content save(SRepairDemand sRepairDemand, SRepairDemandObjPageBean srr,CmsUser user);

}
