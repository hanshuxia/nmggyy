package com.anchorcms.icloud.service.synergy;

import com.anchorcms.icloud.model.payment.SPOrder;
import com.anchorcms.icloud.model.payment.SPOrderObj;
import com.anchorcms.icloud.model.synergy.SBigdataDemandQuote;
import com.anchorcms.icloud.model.synergy.SDemandQuote;
import com.anchorcms.icloud.model.synergy.SDemandQuoteObj;

import java.util.List;

/**
 * Copyright @ 2016 Shandong jxdinfo software co,ltd.
 * All right reserved.
 * Created by Gao Jiangning
 *
 * @Date 2016/12/30 0030
 * @Desc 对需求进行报价的业务层接口
 */
public interface SDemandQuoteService {

    /**
     * @author: gao jiangning
     * @desciption 2016/12/30 保存报价+报价对象list
     */
    SDemandQuote save(SDemandQuote sDemandQuote, String demandQuoteObjsJsonStr);
    SBigdataDemandQuote save2(SBigdataDemandQuote sBigdataDemandQuote);
    /**
     * @Author 闫浩芮
     * @Date 2017/1/5 获得前台优选页面需要的json串
     */
    String getQuoteListJson(Integer demandId,int pageNo, int pageSize);

    String getListJson(Integer demandId);
    /**
     * @Author Gao JN
     * @Date 2017/1/7 获得前台下单页面需要的json串
     */
    String getOrderListJson(Integer quoteId);

    /**
     * @Author zyq
     * @Date 2017/3/16 获得前台需求下单后明细页面需要的json串
     */
    String getDemandQuoteListJson(Integer quoteId);

    /**
     * @author: gao jiangning
     * @desciption 2017/1/11 报价详情需要的json
     */
    String getQuoteDetailJson(Integer quoteId);

    /**
     * 判断公司是否已对某需求报价
     * @param demandId
     * @param companyId
     * @return
     */
    Boolean hasQuoted(Integer demandId, String companyId);
    Boolean hasQuoted2(Integer demandId, String companyId);
    Boolean hasQuoted3(Integer abilityId, String companyId);

    /**
     * 批量对报价进行优选
     * @param quoteIds
     * @return
     */
    Integer selectQuotes(String quoteIds);

    /**
     * 批量对报价进行取消优选
     * @param quoteIds
     * @return
     */
    Integer cancelSelectedQuotes(String quoteIds);

    /**
     * 根据demandQuoteId获得报价实体
     * @param demandQuoteId
     * @return
     */
    SDemandQuote byDemandQuoteId(Integer demandQuoteId);
    List<SDemandQuoteObj> byDemandQuoteObjId(Integer demandQuoteId);
//    SDemand byDemandId(Integer demandId);

    /**
     * 进行下单的业务逻辑处理【1.update报价表(状态位+分配量) 2.update需求表状态为已下单】
     * @param orderJson
     * @param quoteId
     * @return
     */
    void excuteOrder(String orderJson, Integer quoteId);

    /**
     * @author liuyang
     * @Date 2017/5/2 14:19
     * @return
     */
    SPOrder findOrderById(String orderId);

    void saveOrder(SPOrder buy);

    void saveSPOrderObj( SPOrderObj o);
}
