package com.anchorcms.icloud.service.supplychain.impl;

import com.anchorcms.cms.dao.main.ContentDao;
import com.anchorcms.cms.model.main.Content;
import com.anchorcms.cms.model.main.ContentCheck;
import com.anchorcms.cms.service.main.ContentListener;
import com.anchorcms.common.hibernate.Updater;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.dao.supplychain.RepairDemandDao;
import com.anchorcms.icloud.dao.supplychain.SrepairdemandDao;
import com.anchorcms.icloud.model.supplychain.SRepairDemand;
import com.anchorcms.icloud.model.supplychain.SRepairRes;
import com.anchorcms.icloud.service.supplychain.SrepairdemandService;
import net.sf.json.JSONArray;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author zhouyq
 * @Date 2016/12/26
 * @Desc 众修资源管理_管理员服务实现类
 * */
@Service
@Transactional
public class SrepairdemandServiceImpl implements SrepairdemandService {
    @Resource
    private SrepairdemandDao dao;
    @Resource
    private ContentDao contentDao;

    @Resource
    RepairDemandDao repairDemandDao;
    /**
     * @Author zhouyq
     * @Date 2016/12/26
     * @param repairName, pageNo, pageSize
     * @return
     * @Desc 获取众修资源分页后信息
     */
    public Pagination getList(String repairName,String status ,Integer pageNo, Integer pageSize) {
        Pagination page = dao.getPage(repairName,status, pageNo, pageSize);
        return page;
    }

    /**
     * @author dongxuecheng
     * @Date 2017/1/8 12:32
     * @return
     * @description众修需求管理
     */
    public Pagination getZxxqList(String repairName, Integer pageNo, Integer pageSize, String statusType) {
        Pagination page = dao.getZxxqList(repairName, pageNo, pageSize,statusType);
        return page;
    }
    /**
    *@Author 苏和 【562763562@qq.com】
    *@Date 2017/5/3 11:26
    *@Return
    */
    public Pagination getSyneryListPage(Integer siteId, CmsUser user, int pageNo, int pageSize, java.sql.Date startDate, String status, String paramu) {
        return dao.getPage(siteId, user, pageNo, pageSize, startDate, status, paramu);
    }


    /**
     * @Author zhouyq
     * @Date 2016/12/26
     * @param repairId
     * @return
     * @Desc 根据id获取众修资源明细或预览信息
     */
//    public List findDetailAndPreviewById(String repairId) {
//        List repairDemandDetailList = dao.findDetailAndPreviewById(repairId);
//        return repairDemandDetailList;
//    }
    public SRepairRes findDetailAndPreviewByIdDemand(String repairId) {
        SRepairRes oDemand = dao.findDetailAndPreviewByIdDemand(repairId);
        return oDemand;
    }

    /**
     * @Author zhouyq
     * @Date 2016/12/26
     * @param repairId, state
     * @return
     * @Desc 根据id修改众修资源状态（通过、驳回）
     */
    public void mdyRepairDemandStateById(String repairId, String status, String backReason){

        SRepairRes res = dao.findDetailAndPreviewByIdDemand(repairId);
        res.setBackReason(backReason);
        Content content = res.getContent();
        // 执行监听器
        List<Map<String, Object>> mapList = preChange(content);

        if (status != null) {
            if (status.equals("3")) {//已发布时将content状态改为已审核
                content.setStatus(ContentCheck.CHECKED);
            } else {//其他未审核状态
                content.setStatus(ContentCheck.CONTRIBUTE);
            }
            // 更新content表
            Updater<Content> updater = new Updater<Content>(content);
            content = contentDao.updateByUpdater(updater);
            afterChange(content, mapList);
            dao.mdyRepairDemandStateById(repairId, status);
        }
    }

    /**
     * @param repairIds, state
     * @return
     * @author zhouyq
     * @Date 2017/01/06
     * @Desc 根据id批量修改众修资源状态（通过、驳回）
     */
    public void setRepairDemandStateByIds(String repairIds, String status, String backReason) {
        dao.setRepairDemandStateByIds(repairIds, status, backReason);
    }


    /**
     * @Author zhouyq
     * @Date 2016/12/26
     * @param repairId, state
     * @return
     * @Desc 根据id删除众修资源
     */
    public void delRepairDemandById(String repairId){
        dao.delRepairDemandById(repairId);
    }

    public void setStatus(String repairId, String status,String backReason) {
        SRepairDemand sRepairDemand = repairDemandDao.selectReapirDemand(repairId);
        Content content = sRepairDemand.getContent();
        if (status != null) {
            // 执行监听器
            List<Map<String, Object>> mapList = preChange(content);
            if (status.equals("3")) {//已发布时将content状态改为已审核
                content.setStatus(ContentCheck.CHECKED);
            } else {//其他未审核状态
                if(status.equals("0"))
                sRepairDemand.setBackReason(backReason);
                content.setStatus(ContentCheck.CONTRIBUTE);
            }
            // 更新content表
            Updater<Content> updater = new Updater<Content>(content);
            content = contentDao.updateByUpdater(updater);
            dao.setStatus(repairId, status,backReason);
            afterChange(content, mapList);
        }
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
    public void delete(String repairId) {
        dao.delete(repairId);
    }

    public void setall(String repairIds, String status,String backReason) {
        dao.setRepairDemandstatus(repairIds, status,backReason);
    }

    public String getSpareStatisticsJson(String region) throws IllegalAccessException {
        List<Object[]> al = dao.getSpareStatisticsJson(region);
        String listStr = JSONArray.fromObject(al).toString();
        StringBuffer json = new StringBuffer();
//        java.text.NumberFormat nf = java.text.NumberFormat.getInstance();
//        nf.setGroupingUsed(false);
        json.append("{\"spareData\":[");
//        if(demandObjList !=null && demandObjList.size()>0){
            for(int i = 0; i < al.size(); i ++){
//                json.append("{\"spareTypeCode\":\"").append(al.get(i)[0]).append("\",");
                json.append("{\"spareNum\":\"").append((al.get(i)[1])).append("\",");
                json.append("\"spareType\":\"").append(al.get(i)[2]).append("\"},");
            }
            json.deleteCharAt(json.length()-1);
//        }
        json.append("]}");
        return json.toString();
    }

    @Resource
    private List<ContentListener> listenerList;
}
