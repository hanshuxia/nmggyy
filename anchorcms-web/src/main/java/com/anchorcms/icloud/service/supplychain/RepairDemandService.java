package com.anchorcms.icloud.service.supplychain;


import com.anchorcms.cms.model.main.Content;
import com.anchorcms.cms.model.main.ContentExt;
import com.anchorcms.cms.model.main.ContentTxt;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.model.supplychain.SRepairDemand;

import java.util.List;

/**
*@Author 潘晓东
*@Date 2017/1/23 13:53
*@Return维修需求
*/
public interface RepairDemandService {
    /**
    *@Author 潘晓东
    *@Date 2017/1/10 10:59
    *@Return根据ID查询维修需求信息
    */
    public SRepairDemand selectReapirDemand(String id);
/**
 * @author gengwenlong
 * @Date 2017/1/11 20:33
 * @return
 * 根据ID查询维修需求信息
 */
    public List<SRepairDemand> selcetByRepairDemandId(String id);
    /**
     * Created by  hansx
     * 保存维修需求信息
     */
    Content save(SRepairDemand srr, Content c, ContentExt ext, ContentTxt t, Integer channelId, Integer typeId,
                 CmsUser user, boolean forMember,String[] attachmentPaths, String[] attachmentNames,
                 String[] attachmentFilenames, String[] picPaths, String[] picDescs,String demandObjListJsonString);

}
