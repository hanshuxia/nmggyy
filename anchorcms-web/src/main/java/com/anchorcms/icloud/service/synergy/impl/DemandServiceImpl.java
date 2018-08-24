package com.anchorcms.icloud.service.synergy.impl;

import com.anchorcms.cms.dao.main.ContentDao;
import com.anchorcms.cms.model.main.Content;
import com.anchorcms.cms.model.main.ContentCheck;
import com.anchorcms.cms.service.main.ContentListener;
import com.anchorcms.common.hibernate.Updater;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.icloud.dao.synergy.DemandDao;
import com.anchorcms.icloud.dao.synergy.SDemandDao;
import com.anchorcms.icloud.model.synergy.SDemand;
import com.anchorcms.icloud.service.synergy.DemandService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class DemandServiceImpl implements DemandService {

    /**
     *@Author maocheng
     * 获得文章分页。协同制造-管理员-需求管理列表
     *@Date 2016/12/23 0023 16:14
     */
    public Pagination getPageForAll(Integer channelId, Integer siteId, Integer modelId, Integer memberId, int pageNo, int pageSize,
                                    String demandId, String inquiryTheme, String contact, Date startDate, Date endDate, String classifyType,
                                       String companyId, String statusType, String mobile, Date createDt, Date deadlineDt) {
        return dao.getPageByAll(null,memberId, false, false, Content.ContentStatus.all, null, siteId,modelId,  channelId, 0, pageNo,pageSize,
                demandId,inquiryTheme,contact,startDate,endDate,classifyType,companyId,statusType,mobile,createDt,deadlineDt);
    }
    /**
     * @param id 能力id
     * @Author jiangshuzhong
     * @Date 2016/12/28
     * @Desc 能力发布审批
     */
    public void rejectDemand(Integer id,String backReason) {
        SDemand demand = demandDao.findById(id);
        Content content = demand.getContent();
        // 执行监听器
        List<Map<String, Object>> mapList = preChange(content);
        demand.setStatusType("0");
        demand.setBackReason(backReason);
        demand.setReleaseDt(new java.sql.Date(System.currentTimeMillis()));
        content.setStatus(ContentCheck.CONTRIBUTE);
        // 更新content表
        Updater<Content> updater = new Updater<Content>(content);
        content = contentDao.updateByUpdater(updater);
        //更新需求表
        demandDao.update(demand);
        // 执行监听器
        afterChange(content, mapList);
    }

    /**
     * @author ly
     * @date 2017/1/22 16:06
     * @desc
     **/
    public void passDemand(Integer id) {
        SDemand demand = demandDao.findById(id);
        Content content = demand.getContent();
        // 执行监听器
        List<Map<String, Object>> mapList = preChange(content);
        demand.setStatusType("3");
        demand.setReleaseDt(new java.sql.Date(System.currentTimeMillis()));
        content.setStatus(ContentCheck.CHECKED);
        // 更新content表
        Updater<Content> updater = new Updater<Content>(content);
        content = contentDao.updateByUpdater(updater);
        //更新需求表
        demandDao.update(demand);
        // 执行监听器
        afterChange(content, mapList);
    }
    /**
     * @Author 闫浩芮
     * 管理员置为通过需求
     * @Date 2017/2/17 0017 18:39
     */
    public void passMany(String demandIdsStr) {
        if(',' == (demandIdsStr.charAt(demandIdsStr.length()-1))){
            demandIdsStr = demandIdsStr.substring(0,demandIdsStr.length()-1);
        }
        demandDao.passMany(demandIdsStr);
    }
    /**
     * @Author 闫浩芮
     * 管理员置为驳回需求
     * @Date 2017/2/17 0017 18:39
     */
    public void rejectMany(String demandIdsStr,String backReason) {
        if(',' == (demandIdsStr.charAt(demandIdsStr.length()-1))){
            demandIdsStr = demandIdsStr.substring(0,demandIdsStr.length()-1);
        }
        demandDao.rejectMany(demandIdsStr,backReason);
    }

    private List<Map<String, Object>> preChange(Content content) {
        if (listenerList != null) {
            int len = listenerList.size();
            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>(
                    len);
            for (ContentListener listener : listenerList) {
                list.add(listener.preChange(content));
            }
            return list;
        } else {
            return null;
        }
    }

    private void afterChange(Content content, List<Map<String, Object>> mapList) {
        if (listenerList != null) {
            Assert.notNull(mapList);
            Assert.isTrue(mapList.size() == listenerList.size());
            int len = listenerList.size();
            ContentListener listener;
            for (int i = 0; i < len; i++) {
                listener = listenerList.get(i);
                listener.afterChange(content, mapList.get(i));
            }
        }
    }
    @Resource
    private DemandDao dao;
    @Resource
    private SDemandDao demandDao;
    @Resource
    private List<ContentListener> listenerList;
    @Resource
    private ContentDao contentDao;
}
