package com.anchorcms.icloud.service.cloudcenter.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.dao.cloudcenter.CloudCenterDemandDao;
import com.anchorcms.icloud.dao.cloudcenter.CloudCenterQuoteDao;
import com.anchorcms.icloud.dao.cloudcenter.CloudCenterQuoteMangerDao;
import com.anchorcms.icloud.model.cloudcenter.SIcloudDemand;
import com.anchorcms.icloud.model.cloudcenter.SIcloudDemandQuote;
import com.anchorcms.icloud.model.cloudcenter.SIcloudQuoteManage;
import com.anchorcms.icloud.service.cloudcenter.CloudCenterQuoteMangerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Date;

/**
 * @anther 李利民
 * @descript
 * @data 2017/1/11 15:39
 */
@Service
@Transactional
public class CloudCenterQuoteMangerServiceImpl implements CloudCenterQuoteMangerService {

    /** @Author lilimin
     * @Desc 报价管理表--保存
     * @Date 2017/1/11
     */
    public void save(SIcloudQuoteManage manager) {
        mangerDao.save(manager);
    }

    /**
     * 由前台传来的json保存报价管理（订单？？）
     *
     * @param demand
     * @param orderJson
     */
    public Integer saveByJson(SIcloudDemand demand, String orderJson, CmsUser user) {
        //SIcloudDemand demand = cloudCenterDemandDao.bySIcloudDemandId(demandId);
        JSONObject json = JSON.parseObject(orderJson);
        JSONArray distributeArray = json.getJSONArray("distribute");
        int orderCount = 0;
        if(distributeArray==null||distributeArray.size()<1){
            return 0;
        }
        for(int i = 0; i<distributeArray.size(); i++){
            JSONObject distribute = distributeArray.getJSONObject(i);
            String distAmountStr = distribute.getString("mount");
            Double distAmount = distribute.getDouble("mount");
            if("0".equals(distAmountStr)){
                continue;
            }
            SIcloudQuoteManage manager = new SIcloudQuoteManage();
            manager.setCreaterId(user.getUserId().toString());
            SIcloudDemandQuote quote = cloudCenterQuoteDao.byDemand_obj_id(distribute.getInteger("quoteId"));
            //分配数量，更改状态位 of 报价
            quote.setDistributionAmount(distAmount);
            quote.setSelectedStatus("2");//报价状态位 2已下单
            cloudCenterQuoteDao.update(quote);
            //添加订单到数据库
            manager.setDemand(demand);
            manager.setQuote(quote);
            manager.setOfferId(quote.getCreaterId());
            manager.setStatus("1");//为0是已经优选，1是已下单。【貌似现在没机会为0了】
            manager.setQuoteState("0");//默认报价方状态为o,即待确认
            manager.setDemandState("1");//默认需求方状态为1,即已下单
            manager.setCreateDt(new Date(System.currentTimeMillis()));
            save(manager);
            orderCount++;
        }
        return orderCount;
    }

    /**
     * @Author wanjinyou
     * @Desc 报价管理表--更新状态
     * @Date 2017/1/12
     */
    public void updateState(Integer id, String demandState, String quoteState, Integer channelId, CmsUser user, Short charge, boolean b) {
        SIcloudQuoteManage quoteManage = mangerDao.byDemandId(id);
        quoteManage.setQuoteState(quoteState);
        quoteManage.setDemandState(demandState);

    }

    /**
     * @param id
     * @Author wanjinyou
     * @Desc 报价管理表--明细
     * @Date 2017/1/13
     */
    public SIcloudQuoteManage findById(Integer id) {
        return mangerDao.findById(id);
    }

    /**
     * @param id
     * @Author wanjinyou
     * @Desc 报价管理表--删除
     * @Date 2017/1/13
     */
    public SIcloudQuoteManage deleteById(Integer id) {
        SIcloudQuoteManage bean = findById(id);
        mangerDao.deleteById(id);
        return bean;
    }

    /**
     * @param id
     * @param channelId
     * @param user
     * @param charge
     * @param b
     * @Author wanjinyou
     * @Desc 报价管理表--撤单
     * @Date 2017/1/13
     */
    public SIcloudQuoteManage remove(Integer id, Integer demandId, Integer channelId, CmsUser user, Short charge, boolean b) {
       SIcloudDemand demand = cloudCenterDemandDao.bySIcloudDemandId(demandId);
        demand.setStatus("3");
        demand.setUpdateDt(new java.sql.Date(System.currentTimeMillis()));
        cloudCenterDemandDao.update(demand);
        SIcloudQuoteManage bean = findById(id);
        mangerDao.deleteById(id);
        return bean;
    }

    /**
     * 根据demandID查询订单数
     *
     * @param demandId
     * @return
     */
    public Integer countByDemandId(Integer demandId) {
        return mangerDao.countByDemandId(demandId);
    }

    @Resource
    private CloudCenterQuoteMangerDao mangerDao;
    @Resource
    private CloudCenterDemandDao cloudCenterDemandDao;
    @Resource
    private CloudCenterQuoteDao cloudCenterQuoteDao;
}
