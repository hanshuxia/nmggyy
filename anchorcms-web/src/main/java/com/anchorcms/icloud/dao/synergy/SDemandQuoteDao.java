package com.anchorcms.icloud.dao.synergy;

import com.anchorcms.common.hibernate.Updater;
import com.anchorcms.common.page.Pagination;
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
 * @Desc 对需求进行报价的dao接口
 */
public interface SDemandQuoteDao {

    /**
     * 保存报价信息
     * @param bean
     * @return
     */
    SDemandQuote save(SDemandQuote bean);
    SBigdataDemandQuote save2(SBigdataDemandQuote bean);

    /**
     * 根据需求id查询报价列表
     * @param demandId
     * @param pageNo
     * @param pageSize
     * @return
     */
    Pagination getQuoteList(Integer demandId ,int pageNo, int pageSize);

    List<SDemandQuote> getListJson(Integer demandId);
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
     * @param quoteIds 以逗号隔开的 报价表id 字符串Like "1,2,10"
     */
    Integer selectQuotes(String quoteIds);

    /**
     * 批量对报价取消优选
     * @param quoteIds quoteIds 以逗号隔开的 报价表id 字符串Like "1,2,10"
     *
     */
    Integer cancelSelectedQuotes(String quoteIds);
    /**
     * 根据报价Id进行查询
     *@param demandQuoteId
     */
    SDemandQuote findById(Integer demandQuoteId);
    List<SDemandQuoteObj> findByObjId(Integer demandQuoteId);
//    SDemand findDemandId(Integer demandId);
    /**
     * yhr
     * 更新报价信息的状态
     */
    SDemandQuote updateByUpdater(Updater<SDemandQuote> updater);
    /**
     * @author liuyang
     * @Date 2017/5/2 14:27
     * @return
     */
    SPOrder findbean(String id);
    SPOrder save(SPOrder buy);

    void saveSPOrderObj(SPOrderObj o);
}
