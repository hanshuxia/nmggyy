package com.anchorcms.icloud.service.commservice.impl;

import com.anchorcms.cms.dao.main.ContentDao;
import com.anchorcms.cms.model.main.Content;
import com.anchorcms.cms.model.main.ContentCheck;
import com.anchorcms.cms.model.main.ContentExt;
import com.anchorcms.cms.model.main.ContentTxt;
import com.anchorcms.cms.service.main.*;
import com.anchorcms.common.hibernate.Updater;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.dao.commservice.LDAmplepolicyManageDao;
import com.anchorcms.icloud.dao.commservice.impl.SAmplePolicyApplyDaoImpl;
import com.anchorcms.icloud.model.commservice.SAmplePolicy;
import com.anchorcms.icloud.model.commservice.SAmplePolicyApply;
import com.anchorcms.icloud.service.commservice.LDAmplePolicyManageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * DESCRIPTION:s
 * Author: DELL
 * Date:2017/1/13.
 */
@Service
@Transactional
public class LDAmplePolicyManageServiceImpl implements LDAmplePolicyManageService {

    public Pagination getAmplePolicyList(String TradeType, Date startDT, Date endDT, String title, Integer channelId,
                                         Integer siteId, Integer modelId, Integer memberId, int pageNo, int pageSize,
                                         String state) {
        return LDAmplepolicyManageDao.getAmplePolicyList(TradeType, startDT, endDT, title, null, memberId, memberId, false, false, Content.ContentStatus.all, null, siteId, modelId, channelId, 0, pageNo, pageSize, state);
    }

    /**
     * @author ldong
     * @Date 2017/1/14 11:24
     * @return
     */
    @Resource
    SAmplePolicyApplyDaoImpl SAmplePolicyApplyDaoImpl;

    public SAmplePolicy getAmplePolicyById(Integer itemId) {
        return LDAmplepolicyManageDao.getAmplePolicyById(itemId);
    }
/**
 * @author ldong
 * @Date 2017/2/15 15:12
 * @return 撤回/发布政策
 */
    public void updateAmplePolicyById(Integer itemId, String s) {
        SAmplePolicy bean = LDAmplepolicyManageDao.getAmplePolicyById(itemId);
        Content content = bean.getContent();
        // 执行监听器
        List<Map<String, Object>> mapList = preChange(content);
        if (s.equals("2")) {//已发布时将content状态改为已审核
            content.setStatus(ContentCheck.CHECKED);
        } else {//其他未审核状态
            content.setStatus(ContentCheck.CONTRIBUTE);
            List<SAmplePolicyApply> beas = SAmplePolicyApplyDaoImpl.getBeanByAmplePolicyId(itemId);
            if (beas.size() != 0) {
                for (SAmplePolicyApply b : beas) {
                    b.setStatus("4");
                    SAmplePolicyApplyDaoImpl.updatemc(b);
                }
            }
        }
        // 更新content表
        Updater<Content> updater = new Updater<Content>(content);
        content = contentDao.updateByUpdater(updater);
        afterChange(content, mapList);
        bean.setSelectedStatus(s);
        LDAmplepolicyManageDao.updateAmplePolicyByBean(bean);

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
     * @return 删除惠补政策信息
     * @author ldong
     * @Date 2017/1/14 11:39
     */
    public void deleteAmplePolicyById(Integer itemId) {
        SAmplePolicy bean = LDAmplepolicyManageDao.getAmplePolicyById(itemId);//获取能力实体类
        Content contentBean = deleteContentById(bean.getContent().getContentId());
        bean = LDAmplepolicyManageDao.deleteByBean(bean);// 执行删除
        afterDelete(contentBean);
    }

    @Resource
    private ContentMng contentMng;

    public void update(SAmplePolicy sp, Content c, ContentExt ext, ContentTxt t, String[] attachmentPaths, String[] attachmentNames, String[] attachmentFilenames, String[] picPaths, String[] picDescs, Integer channelId, Integer typeId, CmsUser user, Short charge, boolean b) {
        //更新cms表内容
        Content nc = contentMng.update(c, ext, t, null, null, null, null, attachmentPaths, attachmentNames, attachmentFilenames, picPaths,
                picDescs, null, channelId, typeId, null, charge, null, user, b);
        //更新需求内容
        sp.setContent(nc);
        LDAmplepolicyManageDao.updateAmplePolicyByBean(sp);
    }

    public Content deleteContentById(Integer id) {
        Content bean = contentDao.findById(id);
        // 执行监听器
        preDelete(bean);
        bean = contentDao.deleteById(id);
        // 执行监听器
        return bean;
    }

    private void preDelete(Content content) {
        if (listenerList != null) {
            for (ContentListener listener : listenerList) {
                listener.preDelete(content);
            }
        }
    }

    private void afterDelete(Content content) {
        if (listenerList != null) {
            for (ContentListener listener : listenerList) {
                listener.afterDelete(content);
            }
        }
    }

    @Resource
    LDAmplepolicyManageDao LDAmplepolicyManageDao;
    @Resource
    private ChannelMng channelMng;
    @Resource
    private ContentTypeMng contentTypeMng;
    @Resource
    private List<ContentListener> listenerList;
    @Resource
    private ContentTxtMng contentTxtMng;
    @Resource
    private ContentExtMng contentExtMng;
    @Resource
    private ContentCountMng contentCountMng;
    @Resource
    private ContentCheckMng contentCheckMng;
    @Resource
    private ContentDao contentDao;
}
