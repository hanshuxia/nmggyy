package com.anchorcms.icloud.service.synergy.impl;

import com.anchorcms.cms.dao.main.ContentDao;
import com.anchorcms.cms.model.main.Content;
import com.anchorcms.cms.model.main.ContentCheck;
import com.anchorcms.cms.service.main.ContentListener;
import com.anchorcms.common.hibernate.Updater;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.dao.synergy.SynergyCreateDao;
import com.anchorcms.icloud.dao.synergy.SynergyManageDao;
import com.anchorcms.icloud.model.payment.SPSettle;
import com.anchorcms.icloud.model.synergy.SAbility;
import com.anchorcms.icloud.service.synergy.SynergyManageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author 姜舒中
 * @Date 2016/12/23 0023
 * @Desc 协同制造
 */
@Service
@Transactional
public class SynergyManageServiceImpl implements SynergyManageService {
    public Pagination getPageForMember(String title, Integer channelId, Integer siteId, Integer modelId, Integer memberId, int pageNo, int pageSize, String abilityName, String abilityCode,Date createDt,String statusType) {
        return dao.getPageBySelf(title, null,memberId, false, false, Content.ContentStatus.all, null, siteId,modelId,  channelId, 0, pageNo,pageSize,abilityName,abilityCode,createDt,statusType);
    }
    /**
     * @param id
     * @Author jiangshuzhong
     * @Date 2016/12/28
     * @Desc 能力发布审批
     */
    public void rejectAdility(Integer id,String backReason) {
        SAbility ability = synergyCreateDao.byAbilityId(id);
        Content content = ability.getContent();
        // 执行监听器
        List<Map<String, Object>> mapList = preChange(content);
        ability.setStatusType("0");
        ability.setBackReason(backReason);
        ability.setReleaseDt(new java.sql.Date(System.currentTimeMillis()));
        content.setStatus(ContentCheck.CONTRIBUTE);
        // 更新content表
        Updater<Content> updater = new Updater<Content>(content);
        content = contentDao.updateByUpdater(updater);
        //更新能力表
        synergyCreateDao.update(ability);
        // 执行监听器
        afterChange(content, mapList);
    }

    public void passAdility(Integer id) {
        SAbility ability = synergyCreateDao.byAbilityId(id);
        Content content = ability.getContent();
        // 执行监听器
        List<Map<String, Object>> mapList = preChange(content);
        ability.setStatusType("3");
        ability.setReleaseDt(new java.sql.Date(System.currentTimeMillis()));
        content.setStatus(ContentCheck.CHECKED);
        // 更新content表
        Updater<Content> updater = new Updater<Content>(content);
        content = contentDao.updateByUpdater(updater);
        //更新能力表
        synergyCreateDao.update(ability);
        // 执行监听器
        afterChange(content, mapList);
    }

    public void rejectAdilitys(String ids,String backReason) {
        List<SAbility> sAbilityList = synergyCreateDao.byAbilityIds(ids);
        Content[] content = new Content[sAbilityList.size()];
        int i = 0;
        for(SAbility sa : sAbilityList){
            content[i] = sa.getContent();
            // 执行监听器
            List<Map<String, Object>> mapList = preChange(content[i]);
            sa.setStatusType("0");
            sa.setBackReason(backReason);
            sa.setReleaseDt(new java.sql.Date(System.currentTimeMillis()));
            content[i].setStatus(ContentCheck.CHECKED);
            // 更新content表
            Updater<Content> updater = new Updater<Content>(content[i]);
            content[i] = contentDao.updateByUpdater(updater);
            //更新能力表
            synergyCreateDao.update(sa);
            // 执行监听器
            afterChange(content[i], mapList);
            i++;
        }
    }

    public void passAdilitys(String ids) {
//        String[] idsSpilt = ids.split(",");
//        Integer[] idss = new Integer[idsSpilt.length];
//        int j = 0;
//        for (String id : idsSpilt){
//            idss[j] = Integer.valueOf(idsSpilt[j]);
//        }
        List<SAbility> sAbilityList = synergyCreateDao.byAbilityIds(ids);
        Content[] content = new Content[sAbilityList.size()];
        int i = 0;
        for(SAbility sa : sAbilityList){
            content[i] = sa.getContent();
            // 执行监听器
            List<Map<String, Object>> mapList = preChange(content[i]);
            sa.setStatusType("3");
            sa.setReleaseDt(new java.sql.Date(System.currentTimeMillis()));
            content[i].setStatus(ContentCheck.CHECKED);
            // 更新content表
            Updater<Content> updater = new Updater<Content>(content[i]);
            content[i] = contentDao.updateByUpdater(updater);
            //更新能力表
            synergyCreateDao.update(sa);
            // 执行监听器
            afterChange(content[i], mapList);
            i++;
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
    /**
    *@Author 苏和 【562763562@qq.com】
    *@Date 2017/5/2 11:19
    *@Return
     * 协同制造卖方订单列表
    */
    public Pagination getSyneryListPage(Integer siteId, CmsUser user, int pageNo, int pageSize, Date startDate, String state, String paramu) {
        return dao.getPage(siteId, user, pageNo, pageSize, startDate, state, paramu);
    }

    public SPSettle findOrderById(String orderId) {
        return dao.findbean(orderId);
    }

    public SAbility byAbilityId(Integer abilityId){return dao.byAbilityId(abilityId);}
    public void modifyWuliuStateById(String txlogisticId, String logisticsOrderState){
        dao.modifyWuliuStateById(txlogisticId, logisticsOrderState);
    }
    @Resource
    private SynergyManageDao dao;
    @Resource
    private List<ContentListener> listenerList;
    @Resource
    private SynergyCreateDao synergyCreateDao;
    @Resource
    private ContentDao contentDao;

}
