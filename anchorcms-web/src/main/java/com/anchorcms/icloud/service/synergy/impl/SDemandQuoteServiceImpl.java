package com.anchorcms.icloud.service.synergy.impl;

import com.anchorcms.common.hibernate.Updater;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.icloud.dao.synergy.SDemandDao;
import com.anchorcms.icloud.dao.synergy.SDemandQuoteDao;
import com.anchorcms.icloud.dao.synergy.SDemandQuoteObjDao;
import com.anchorcms.icloud.model.payment.SPOrder;
import com.anchorcms.icloud.model.payment.SPOrderObj;
import com.anchorcms.icloud.model.synergy.*;
import com.anchorcms.icloud.service.synergy.SDemandQuoteService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * Copyright @ 2016 Shandong jxdinfo software co,ltd.
 * All right reserved.
 * Created by Gao Jiangning
 *
 * @Date 2016/12/30 0030
 * @Desc 对需求进行报价的业务层接口实现类
 */
@Service
@Transactional
public class SDemandQuoteServiceImpl implements SDemandQuoteService {
    /**
     * @param sDemandQuote 要保存的报价信息
     * @param demandQuoteObjsJsonStr 要保存报价信息的报价对像的json串
     * @author: gao jiangning
     * @desciption 2016/12/30 保存报价+报价对象list
     */
    public SDemandQuote save(SDemandQuote sDemandQuote, String demandQuoteObjsJsonStr) {
        List<SDemandQuoteObj> sdqList = convertJsonToList(sDemandQuote, demandQuoteObjsJsonStr);
        sDemandQuote.setDemandQuoteObjList(sdqList);
        return sDemandQuoteDao.save(sDemandQuote);
    }
    public SBigdataDemandQuote save2(SBigdataDemandQuote sBigdataDemandQuote) {

        return sDemandQuoteDao.save2(sBigdataDemandQuote);
    }
    /**
     * 获得前台优选页面需要的json串
     * @param demandId
     * @param pageNo
     * @param pageSize
     * @return
     */
    public String getQuoteListJson(Integer demandId,int pageNo, int pageSize) {
        Pagination qListPage = sDemandQuoteDao.getQuoteList(demandId,pageNo,pageSize);
        return convertQuoteList(qListPage, demandId);
    }

    public String getListJson(Integer demandId) {
        List<SDemandQuote> qList = sDemandQuoteDao.getListJson(demandId);
        return convertQuoteList2(qList, demandId);
    }

    /**
     * 获得 下单页面表格 需要的 json
     * @param quoteId
     * @return
     */
    public String getOrderListJson(Integer quoteId){
        SDemandQuote quote = sDemandQuoteDao.findById(quoteId);
        SDemand demand = quote.getDemand();
        List<SDemandObj> demandObjList = demand.getDemandObjList();
        Map<Integer,Double> quotePriceMap = quote.getQuotePriceMap();
        StringBuffer json = new StringBuffer();
        java.text.NumberFormat nf = java.text.NumberFormat.getInstance();
        nf.setGroupingUsed(false);
        json.append("{\"objs\":[");
        if(demandObjList !=null && demandObjList.size()>0){
            for(SDemandObj demandObj : demandObjList){
                Double qPrice = quotePriceMap.get(demandObj.getDemandObjid());
                Double distriAmount = sDemandQuoteObjDao.getSumDistriByDemandObjId(demandObj.getDemandObjid());
                json.append("{\"objName\":\"").append(demandObj.getObjectName()).append("\",");
                json.append("\"objId\":\"").append(nf.format(demandObj.getDemandObjid())).append("\",");
                json.append("\"objCode\":\"").append(demandObj.getClassifyCode()).append("\",");
                json.append("\"amount\":\"").append(nf.format(demandObj.getDemandCount())).append("\",");
                json.append("\"lastAmount\":\"").append(nf.format(demandObj.getDemandCount()-distriAmount)).append("\",");
                json.append("\"unit\":\"").append(demandObj.getUnit()).append("\",");
//                json.append("\"expPrice\":\"").append(nf.format(demandObj.getExpectPrice())).append("\",");
                json.append("\"qPrice\":\"").append((qPrice==null)?"未报价":nf.format(qPrice)).append("\"},");
            }
            json.deleteCharAt(json.length()-1);
        }
        json.append("],").append("\"company\":\"").append(quote.getCompany().getCompanyName()).append("\",");
        json.append("\"quoteId\":\"").append(nf.format(quoteId)).append("\"}");
        return json.toString();
    }

    /**
     * 获得前台需求下单后明细页面需要的json串
     * @param quoteId
     * @return
     */
    public String getDemandQuoteListJson(Integer quoteId){
        SDemandQuote quote = sDemandQuoteDao.findById(quoteId);
        SDemand demand = quote.getDemand();
        List<SDemandObj> demandObjList = demand.getDemandObjList();
        Map<Integer,Double> quotePriceMap = quote.getQuotePriceMap();
        StringBuffer json = new StringBuffer();
        java.text.NumberFormat nf = java.text.NumberFormat.getInstance();
        nf.setGroupingUsed(false);
        json.append("{\"objs\":[");
        if(demandObjList !=null && demandObjList.size()>0){
            for(SDemandObj demandObj : demandObjList){
                Double qPrice = quotePriceMap.get(demandObj.getDemandObjid());
                Double distriAmount = sDemandQuoteObjDao.getSumDistriByDemandObjId(demandObj.getDemandObjid());
                json.append("{\"objName\":\"").append(demandObj.getObjectName()).append("\",");
                json.append("\"objCode\":\"").append(demandObj.getClassifyCode()).append("\",");
                json.append("\"demandCount\":\"").append(nf.format(demandObj.getDemandCount())).append("\",");
                json.append("\"unit\":\"").append(demandObj.getUnit()).append("\",");
                json.append("\"remark\":\"").append(demandObj.getRemark()).append("\",");
//                json.append("\"expPrice\":\"").append(nf.format(demandObj.getExpectPrice())).append("\",");
                json.append("\"qPrice\":\"").append((qPrice==null)?"未报价":nf.format(qPrice)).append("\"},");
            }
            json.deleteCharAt(json.length()-1);
        }
        json.append("],").append("\"company\":\"").append(quote.getCompany().getCompanyName()).append("\",");
        json.append("\"quoteId\":\"").append(nf.format(quoteId)).append("\"}");
        return json.toString();
    }

    /**
     * @param quoteId
     * @author: gao jiangning
     * @desciption 2017/1/11 报价详情需要的json
     */
    public String getQuoteDetailJson(Integer quoteId) {
        SDemandQuote quote = sDemandQuoteDao.findById(quoteId);
        SDemand demand = quote.getDemand();
        List<SDemandObj> demandObjList = demand.getDemandObjList();
        Map<Integer,Double> quotePriceMap = quote.getQuotePriceMap();
        StringBuffer json = new StringBuffer();
        json.append("{\"objs\":[");
        java.text.NumberFormat nf = java.text.NumberFormat.getInstance();
        nf.setGroupingUsed(false);
        if(demandObjList !=null && demandObjList.size()>0){
            for(SDemandObj demandObj : demandObjList){
                Double qPrice = quotePriceMap.get(demandObj.getDemandObjid());
                json.append("{\"objName\":\"").append(demandObj.getObjectName()).append("\",");
                json.append("\"objCode\":\"").append(demandObj.getClassifyCode()).append("\",");
                json.append("\"amount\":\"").append(nf.format(demandObj.getDemandCount())).append("\",");
                json.append("\"unit\":\"").append(demandObj.getUnit()).append("\",");
                if (demandObj.getExpectPrice() != null) {
                    json.append("\"expPrice\":\"").append(nf.format(demandObj.getExpectPrice())).append("\",");
                }
                json.append("\"qPrice\":\"").append((qPrice==null)?"未报价":nf.format(qPrice)).append("\"},");
            }
            json.deleteCharAt(json.length()-1);
        }
        json.append("],").append("\"demandTheme\":\"").append(demand.getInquiryTheme()).append("\",");
        json.append("\"demandId\":\"").append(nf.format(demand.getDemandId())).append("\"}");
        return json.toString();
    }

    /**
     * @author: gao jiangning
     * @desciption 2017/1/5 前台优选页面需要的json串
     */
    private String convertQuoteList(Pagination qListPage,Integer demandId){
        List<SDemandQuote> qList = (List<SDemandQuote>) qListPage.getList();
        java.text.NumberFormat nf = java.text.NumberFormat.getInstance();
        nf.setGroupingUsed(false);
        StringBuffer json = new StringBuffer();
        json.append("{\"quotes\":[");
        if(qList!=null && qList.size()>0){
            for(SDemandQuote sq : qList){
                json.append("{\"quoteId\":\"").append(nf.format(sq.getDemandQuoteId())).append("\",");
                json.append("\"demandId\":\"").append(nf.format(sq.getDemandId())).append("\",");
                json.append("\"selected\":\"").append(sq.getSelectedStatus()).append("\",");//是否被优选
                json.append("\"companyCode\":\"").append(sq.getCompany().getCompanyCode() == null?"未填写":sq.getCompany().getCompanyCode()).append("\",");
                json.append("\"companyName\":\"").append(sq.getCompany().getCompanyName()).append("\",");
                json.append("\"companyType\":\"").append(sq.getCompany().getCompanyType()).append("\",");
                json.append("\"companyScale\":\"").append(sq.getCompany().getCompanyScale()).append("\"},");
            }
            json.deleteCharAt(json.length()-1);
        }
        json.append("],").append("\"count\":\"").append(nf.format(qListPage.getTotalCount())).append("\",");
        json.append("\"pageNo\":\"").append(qListPage.getPageNo()).append("\",");
        json.append("\"totalPage\":\"").append(qListPage.getTotalPage()).append("\",");
        json.append("\"demandId\":\"").append(nf.format(demandId)).append("\"");
        json.append("}");
        return json.toString();
    }

    private String convertQuoteList2(List<SDemandQuote> qList,Integer demandId){
        java.text.NumberFormat nf = java.text.NumberFormat.getInstance();
        nf.setGroupingUsed(false);
        StringBuffer json = new StringBuffer();
        json.append("{\"quotes\":[");
        if(qList!=null && qList.size()>0){
            for(SDemandQuote sq : qList){
                json.append("{\"quoteId\":\"").append(nf.format(sq.getDemandQuoteId())).append("\",");
                json.append("\"demandId\":\"").append(nf.format(sq.getDemandId())).append("\",");
                json.append("\"selected\":\"").append(sq.getSelectedStatus()).append("\",");//是否被优选
                json.append("\"companyCode\":\"").append(sq.getCompany().getCompanyCode() == null?"未填写":sq.getCompany().getCompanyCode()).append("\",");
                json.append("\"companyName\":\"").append(sq.getCompany().getCompanyName()).append("\",");
                json.append("\"companyType\":\"").append(sq.getCompany().getCompanyType()).append("\",");
                json.append("\"companyScale\":\"").append(sq.getCompany().getCompanyScale()).append("\",");
                json.append("\"demandCount\":\"").append(sq.getDemand().getDemandObjList().get(0).getDemandCount()).append("\",");
                json.append("\"unit\":\"").append(sq.getDemand().getDemandObjList().get(0).getUnit()).append("\",");
                json.append("\"offer\":\"").append(sq.getDemandQuoteObjList().get(0).getOffer()).append("\"},");
            }
            json.deleteCharAt(json.length()-1);
        }
        json.append("],").append("\"count\":\"").append(qList==null?0:qList.size()).append("\",");
        json.append("\"demandId\":\"").append(nf.format(demandId)).append("\"");
        json.append("}");
        return json.toString();
    }
    /**
     * 判断公司是否已对某需求报价
     * @param demandId
     * @param companyId
     * @return
     */
    public Boolean hasQuoted(Integer demandId, String companyId) {
        return sDemandQuoteDao.hasQuoted(demandId,companyId);
    }
    public Boolean hasQuoted2(Integer demandId, String companyId) {
        return sDemandQuoteDao.hasQuoted2(demandId,companyId);
    }
    public Boolean hasQuoted3(Integer abilityId, String companyId) {
        return sDemandQuoteDao.hasQuoted3(abilityId,companyId);
    }

    /**
     * 批量对报价进行优选
     *
     * @param quoteIds
     * @return
     */
    public Integer selectQuotes(String quoteIds) {
        if(quoteIds==null || "".equals(quoteIds)){
            return 0;
        }
        if(',' == (quoteIds.charAt(quoteIds.length()-1))){
            quoteIds = quoteIds.substring(0,quoteIds.length()-1);
        }
        return sDemandQuoteDao.selectQuotes(quoteIds);
    }

    /**
     * 批量对报价取消优选
     * @param quoteIds
     * @return
     */
    public Integer cancelSelectedQuotes(String quoteIds) {
        if(quoteIds==null || "".equals(quoteIds)){
            return 0;
        }
        if(',' == (quoteIds.charAt(quoteIds.length()-1))){
            quoteIds = quoteIds.substring(0,quoteIds.length()-1);
        }
        return sDemandQuoteDao.cancelSelectedQuotes(quoteIds);
    }

    /**
     * 根据demandQuoteId获得报价实体
     * @param demandQuoteId
     * @return
     */
    public SDemandQuote byDemandQuoteId(Integer demandQuoteId) {
        return sDemandQuoteDao.findById(demandQuoteId);
    }
    public List<SDemandQuoteObj> byDemandQuoteObjId(Integer demandQuoteId) {
        return sDemandQuoteDao.findByObjId(demandQuoteId);
    }
//    public SDemand byDemandId(Integer demandId) {
//        return sDemandQuoteDao.findDemandId(demandId);
//    }
    /**
     * 进行下单的业务逻辑处理【1.update报价表(状态位+分配量) 2.update需求表状态为已下单】
     *
     * @param orderJson
     * @param quoteId
     * @return
     */
    public void excuteOrder(String orderJson, Integer quoteId) {
        SDemandQuote quote = sDemandQuoteDao.findById(quoteId);
        JSONObject oJson = new JSONObject(orderJson);
        JSONArray jsonArr = (JSONArray) oJson.get("distribute");
        //先qObjList转map，然后再操作
        List<SDemandQuoteObj> oldList = quote.getDemandQuoteObjList();
        Map<Integer,SDemandQuoteObj> distriMap = castQuoteObjListToMap(oldList);
        if(jsonArr==null||jsonArr.length()<1){
            return;
        }
        //更新子表对象
        oldList.clear();
        for(int i = 0; i<jsonArr.length(); i++){
            JSONObject distribute = jsonArr.getJSONObject(i);
            SDemandQuoteObj o = distriMap.get(distribute.getInt("objId"));//demandObjId
            o.setDistributionAmount(distribute.getDouble("mount"));
            oldList.add(o);
        }
        quote.setSelectedStatus("2");
        quote.setDemandQuoteObjList(oldList);
        Updater<SDemandQuote> updater = new Updater<SDemandQuote>(quote);
        quote = sDemandQuoteDao.updateByUpdater(updater);
        //需要把需求的状态位也改为已下单
        //根据设计要求，即使对需求下完单，该需求仍会在页面中显示，直到需求截止日期。去除更新需求代码。--作废
        //验证询价单中的需求项是否都已下单，如果都已经下单 则将状态改变，不在询价中显示
        List<SDemandObj> sol = quote.getDemand().getDemandObjList();
        for(SDemandObj so :sol){
            double num = (so.getDemandCount());
           double a=  sDemandQuoteObjDao.getSumDistriByDemandObjId(so.getDemandObjid());
            if(so.getDemandCount().doubleValue()!=sDemandQuoteObjDao.getSumDistriByDemandObjId(so.getDemandObjid()).doubleValue()){
                return;
            }
        }
        demandDao.artUpdateStatusType(quote.getDemandId(),"4");
    }

    /**
     * @author: gao jiangning
     * @desciption 将前台传入的json String转化为需求对象的List
     */
    private List<SDemandQuoteObj> convertJsonToList(SDemandQuote sDemandQuote, String demandQuoteObjsJsonStr){
        JSONObject jsonObj = new JSONObject(demandQuoteObjsJsonStr);
        JSONArray jsonArr = (JSONArray) jsonObj.get("demandQuoteObjs");
        List<SDemandQuoteObj> sDemandQuoteObjList = new ArrayList<SDemandQuoteObj>();
        if(jsonArr==null||jsonArr.length()==0){
            return null;
        }
        //遍历传过来的json对象数组，取出值转换后置入对象，对象再置入list
        for(int i = 0; i<jsonArr.length(); i++){
            JSONObject demandQuoteJObj = jsonArr.getJSONObject(i);
            SDemandQuoteObj dqObj = new SDemandQuoteObj();
            dqObj.setDemandObjid(Integer.parseInt(demandQuoteJObj.getString("objId")));
            dqObj.setOffer(Double.parseDouble(demandQuoteJObj.getString("price")));
            dqObj.setCreateId(sDemandQuote.getCreaterId());
            dqObj.setCreateDt(sDemandQuote.getCreateDt());
            dqObj.setUpdateDt(dqObj.getCreateDt());
            dqObj.setDeFlag("1");
            sDemandQuoteObjList.add(dqObj);
        }
        return sDemandQuoteObjList;
    }

    /**
     * 将报价对象List转为Map方便为分配数量赋值
     * @param qObjList
     * @return
     */
    private Map<Integer,SDemandQuoteObj> castQuoteObjListToMap(List<SDemandQuoteObj> qObjList){
        Map<Integer,SDemandQuoteObj> distriMap = new HashMap<Integer,SDemandQuoteObj>();
        if(qObjList != null && qObjList.size()>0){
            for(SDemandQuoteObj o : qObjList){
                distriMap.put(o.getDemandObjid(),o);
            }
        }
        return distriMap;
    }

    @Resource
    private SDemandDao demandDao;
    @Resource
    private SDemandQuoteDao sDemandQuoteDao;
    @Resource
    private SDemandQuoteObjDao sDemandQuoteObjDao;
    /**
     * @author liuyang
     * @Date 2017/5/2 14:25
     * @return
     */
    public SPOrder findOrderById(String orderId) {
        return sDemandQuoteDao.findbean(orderId);
    }

    public void saveOrder(SPOrder buy) {
        sDemandQuoteDao.save(buy);
    }

    public void saveSPOrderObj(SPOrderObj o) {
        sDemandQuoteDao.saveSPOrderObj(o);
    }
}
