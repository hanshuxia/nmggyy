package com.anchorcms.icloud.service.supplychain;

import com.anchorcms.cms.model.main.Content;
import com.anchorcms.cms.model.main.ContentExt;
import com.anchorcms.cms.model.main.ContentTxt;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.model.payment.SPOrder;
import com.anchorcms.icloud.model.payment.SPOrderObj;
import com.anchorcms.icloud.model.payment.SSupplychainOrder;
import com.anchorcms.icloud.model.payment.SSupplychainOrderObj;
import com.anchorcms.icloud.model.supplychain.SRepairDemand;
import com.anchorcms.icloud.model.supplychain.SRepairDemandObj;
import com.anchorcms.icloud.model.supplychain.SRepairDemandObjPageBean;
import com.anchorcms.icloud.model.supplychain.SRepairQuote;
import com.anchorcms.icloud.model.synergy.SDemandObj;

import java.util.List;

/**
 * @author ldong
 * @Date 2017/1/4 19:39
 * @return
 */
public interface RepairDemandMangeService {


    Pagination getRepairDemandPage(String repairType,String title, Integer channelId,
                                   Integer siteId, Integer modelId, Integer memberId,
                                   int pageNo, int pageSize ,String state);

    SRepairDemand[] deleteRepairDemandByIds(String[] ids);
    SRepairDemand editCallBackRepairDemandByIds(String ids, String callBack);
    List<SRepairQuote> getDoChoose(String ids);
    List<SRepairQuote> editChooseState(String ids, String state,String demandId);
    List getGoOrder(String demandId, String demandQuoteID);
    /*获得订单信息下单师傅*/
    SRepairQuote getQuoteEntity(String demandQuoteID);



    public SRepairDemand selectReapirDemand(String id);

    SRepairDemand getRepairDemandById(Integer demandId);

    public List<SRepairDemandObj> selcetByRepairDemandId(String id);
    void updateRepairDemand(String[] indexId, SRepairDemand sRepairDemand, SRepairDemandObjPageBean sRepairDemandObjPageBean);



    void doOrder(String demandId, String demandQuoteId);


    /**
     * @author ldong
     * @Date ] 10:51
     * @return
     * 保存维修需求
     */
    Content save(SRepairDemand sd, Content c, ContentExt ext, ContentTxt t, String[] attachmentPaths, String[] attachmentNames, String[] attachmentFilenames, String[] picPaths, String[] picDescs, Integer channelId, Integer typeId, CmsUser user, Short charge, boolean b, String demandObjListJsonString);
    /**
     * @author ldong
     * @Date ] 10:51
     * @return
     * 保存修改后的维修需求
     */
    Content repairDemandEditSave(SRepairDemand sd, Content c, ContentExt ext, ContentTxt t, String[] attachmentPaths, String[] attachmentNames, String[] attachmentFilenames, String[] picPaths, String[] picDescs, Integer channelId, Integer typeId, CmsUser user, Short charge, boolean b, String demandObjListJsonString);
/**
 * @author ldong
 * @Date 2017/1/23 10:46
 * @return 设置需求状态   发布 撤回  关闭
 */

    void setStateByRepairDemandByIds(CmsUser user, String demandId, String setState);
    /**
     * @author ldong
     * @Date 2017/1/10 10:59
     * @return
     * 获取优选界面的list
     */
    String getQuoteListJson(Integer demandId, Integer pageNo, int i);

    /**
     * @author ldong
     * @Date 2017/1/10 15:17
     * @return
     * 批量对报价单单进行优选 取消优选
     */
    int selectQuotes(String quoteIds);
    int cancelSelectedQuotes(String quoteIds);
    /**
     * @author ldong
     * @Date 2017/1/10 16:01
     * @return
     * 获取下单界面list(json)
     */
    String getOrderListJson(String quoteId);
    /**
     * @author ldong
     * @Date 2017/1/10 20:27
     * @return
     * 对下单结果保存
     */
    boolean excuteOrder(String orderJson, String quoteId);

    /**
     * @author ldong
     * @Date 2017/1/23 10:47
     * @return编辑保存
     */

    void update(SRepairDemand sd, Content c, ContentExt ext, ContentTxt t, String[] attachmentPaths, String[] attachmentNames, String[] attachmentFilenames, String[] picPaths, String[] picDescs, Integer channelId, Integer typeId, CmsUser user, Short charge, boolean b, String demandObjListJsonString);

    /**
     * @author dongxuecheng
     * @Date 2017/2/22 10:04
     * @return
     * @description评价赋值
     */
   public Integer setEvaluteValue(String demandObjId, String evaluteValue);
   /**
    * @author liuyang
    * @Date 2017/5/4 16:03
    * @return
    */
    public SRepairDemandObj byDemandObjId(String demandId);
    void saveOrder(SSupplychainOrder buy);

    void saveSPOrderObj( SSupplychainOrderObj o);

}
