package com.anchorcms.icloud.dao.supplychain;

import com.anchorcms.cms.model.main.Content;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.icloud.model.payment.SSupplychainOrder;
import com.anchorcms.icloud.model.payment.SSupplychainOrderObj;
import com.anchorcms.icloud.model.supplychain.SRepairAbility;
import com.anchorcms.icloud.model.supplychain.SRepairDemand;
import com.anchorcms.icloud.model.supplychain.SRepairDemandObj;
import com.anchorcms.icloud.model.supplychain.SRepairQuote;

import java.util.List;

/**
 * Created by DELL on 2017/1/3.
 */

public interface RepairDemandMnageDao {
    /**
     * @author ldong
     * @Date 2017/1/23 10:59
     * @return
     * 获取管理列表
     */
    public Pagination getRepairDemandPage(String repairType,String title, Integer typeId, Integer currUserId,
                                          Integer inputUserId, boolean topLevel, boolean recommend,
                                          Content.ContentStatus status, Byte checkStep, Integer siteId, Integer modelId,
                                          Integer channelId, int orderBy, int pageNo, int pageSize, String state);
/**
 * @author ldong
 * @Date 2017/1/23 11:00
 * @return
 * delete demand obj
 */

    SRepairDemand deleteRepairDemandById(String id);
    /**
     * @author ldong
     * @Date 2017/1/23 11:00
     * @return
     * edit S_Repair_demand state
     */

    SRepairDemand editCallBackRepairDemandById(String id, String callBack);
    List<SRepairQuote> getDoChoose(String ids);
    SRepairQuote editChooseState(String ids, String state);
    List getGoOrder(String demandId, String demandQuoteID);

    List getDemandObjByDemandId(String demandId);

    void delSRepairDemandObj(SRepairDemandObj bean);

    SRepairQuote getQuoteEntity(String demandQuoteID);

    SRepairDemand selectReapirDemand(String id);
    List<SRepairDemandObj> selectRepairDemandObj(String id);

    void updateRepairDemand(SRepairDemand p);

    void updateDemandObj(SRepairDemandObj po);

    SRepairQuote editStateBydemandObjId(String ids, String state);

    SRepairDemand getRepairDemandById(Integer demandId);

    SRepairDemand saveRepairDemand(SRepairDemand bean);
    public void deleteOrphan();
   SRepairQuote deleteRepairQuoteByBean(SRepairQuote bean);

    void deleteRepairAbilityByBean(SRepairAbility abbean);

    Pagination getQuoteList(Integer demandId, Integer pageNo, int pageSize);


    int selectQuotes(String quoteIds);
    Integer cancelSelectedQuotes(String quoteIds);
/**
 * @author ldong
 * @Date 2017/1/10 17:30
 * @return
 * 获取询价对象的map objid ：offer map
 */
    List<SRepairAbility> getQuotePrice(String quoteId);

    void updateSrepairQuote(SRepairQuote bean);

    void setRepairQuoteNull();

    List<SRepairQuote> getRepairDemandByDemandId(String id);

    /**
     * @author dongxuecheng
     * @Date 2017/2/22 10:07
     * @return
     * @description评价
     */
    public Integer setEvaluteValue(String demandObjId,String evaluteValue);
    /**
     * @author liuyang
     * @Date 2017/5/4 16:08
     * @return
     */
    public SRepairDemandObj byDemandObjId(String demandId);
    SSupplychainOrder save(SSupplychainOrder buy);

    void saveSPOrderObj(SSupplychainOrderObj o);
}
