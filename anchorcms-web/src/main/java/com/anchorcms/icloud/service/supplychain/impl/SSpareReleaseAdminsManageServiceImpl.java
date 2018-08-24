package com.anchorcms.icloud.service.supplychain.impl;

import com.anchorcms.cms.dao.main.ContentDao;
import com.anchorcms.cms.model.main.Content;
import com.anchorcms.cms.model.main.ContentCheck;
import com.anchorcms.cms.service.main.ContentListener;
import com.anchorcms.common.hibernate.Updater;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.icloud.dao.supplychain.SSpareReleaseAdminsManageDao;
import com.anchorcms.icloud.model.supplychain.SSpareDemand;
import com.anchorcms.icloud.service.supplychain.SSpareReleaseAdminsManageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author liuyang
 * @Date 2017/1/4 15:00
 * @return
 * 备品备件审核管理service实现类
 */
@Service
@Transactional
public class SSpareReleaseAdminsManageServiceImpl implements SSpareReleaseAdminsManageService {
    @Resource
    private SSpareReleaseAdminsManageDao dao;
    @Resource
    private ContentDao contentDao;
    @Resource
    private List<ContentListener> listenerList;


    public Pagination getList(String statusType,String requestTheme,Date createDt,Date deadlineDt, Integer pageNo, Integer pageSize) {
        Pagination page = dao.getPage(statusType,requestTheme,createDt,deadlineDt, pageNo, pageSize);
        return page;
    }

    /**
     * 根据id获取明细信息
     *
     * @param demandId
     * 备品备件管理员管理实现类
     */
//    public List findDetailAndPreviewById(String repairId) {
//        List repairDemandDetailList = dao.findDetailAndPreviewById(repairId);
//        return repairDemandDetailList;
//    }
    public SSpareDemand findDetailAndPreviewByIdDemand(String demandId) {
        SSpareDemand oDemand = dao.findDetailAndPreviewByIdDemand(demandId);
        return oDemand;
    }

    /**
     * 根据id修改备品备件审核状态（通过、驳回）
     *
     * @param demandId, state
     * @return
     */
    public void setRepairDemandStateById(String demandId, String state,Date releaseDt,String flag,String backReason){
        dao.setRepairDemandStateById(demandId, state,releaseDt,flag,backReason);
    }
    public void modifyRepairDemandStateById(String demandId, String state,Date releaseDt,String backReason){
        SSpareDemand sSpareDemand = dao.findDetailAndPreviewByIdDemand(demandId);

        if (state != null) {
            Content content = sSpareDemand.getContent();
            sSpareDemand.setBackReason(backReason);
            List<Map<String, Object>> mapList = preChange(content);
            if (state.equals("3")) {//已发布时将content状态改为已审核
                content.setStatus(ContentCheck.CHECKED);
            } else {//其他未审核状态
                content.setStatus(ContentCheck.CONTRIBUTE);
            }
            // 更新content表
            Updater<Content> updater = new Updater<Content>(content);
            content = contentDao.updateByUpdater(updater);
            afterChange(content, mapList);
        }

        dao.modifyRepairDemandStateById(demandId, state,releaseDt,backReason);
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
    /**
     * 根据id删除备品备件审核
     *
     * @param demandId
     * @return
     */
    public void delRepairDemandById(String demandId){
        dao.delRepairDemandById(demandId);
    }
}
