package com.anchorcms.icloud.service.synergy.impl;

import com.anchorcms.cms.dao.main.ContentDao;
import com.anchorcms.cms.model.main.*;
import com.anchorcms.cms.service.assist.CmsCommentMng;
import com.anchorcms.cms.service.assist.CmsConsultMng;
import com.anchorcms.cms.service.assist.CmsFileMng;
import com.anchorcms.cms.service.main.*;
import com.anchorcms.common.hibernate.Updater;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsSite;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.dao.synergy.InteractAreaDao;
import com.anchorcms.icloud.service.synergy.InteractAreaService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.anchorcms.cms.model.main.ContentCheck.DRAFT;


/**
 * @Author wanjinyou
 * @Date 2017/2/9
 * @Desc 互动专区
 */
@Service
@Transactional
public class InteractAreaServiceImpl implements InteractAreaService {

    /**
     * @Author wanjinyou
     * @Date 2017/2/9
     * @Desc 后台-互动专区--列表
     */
    public Pagination getPageForMember(Integer channelId, Integer siteId, Integer modelId, Integer userId, int pageNo, int pageSize, Date startDate, Date endDate, String companyName,String title, Byte status) {
        return dao.getPageBySelf(null, false, false, Content.ContentStatus.all, null,
                siteId, channelId, pageNo, pageSize, startDate, endDate, companyName,title, status);
    }

    /**
     * @param id
     * @Author wanjinyou
     * @Date 2017/2/9
     * @Desc 后台-互动专区--获取实体类
     */
    public Content byContentId(Integer id) {
        return dao.byContentId(id);
    }

    public List<Content> byContentIds(String ids) {
        return dao.byContentIds(ids);
    }

    /**
     * @Author wanjinyou
     * @Date 2017/2/9
     * @Desc 后台-互动专区--通过
     */
    public Content pass(Integer id, Integer channelId, CmsUser user, Short charge, boolean b) {
        Content content = byContentId(id);
        // 执行监听器
        List<Map<String, Object>> mapList = preChange(content);
        content.setStatus(ContentCheck.CHECKED);
        content.getContentExt().setReleaseDate(new java.sql.Date(System.currentTimeMillis()));
        // 更新content表
        Updater<Content> updater = new Updater<Content>(content);
        content = contentDao.updateByUpdater(updater);
        // 执行监听器
        afterChange(content, mapList);
        return content;
    }

    public void passTotal(String ids, Integer channelId, CmsUser user, Short charge, boolean b) {
        List<Content> contents = byContentIds(ids);
        // 执行监听器
        for(Content content : contents){
            List<Map<String, Object>> mapList = preChange(content);
            content.setStatus(ContentCheck.CHECKED);
            // 更新content表
            Updater<Content> updater = new Updater<Content>(content);
            content = contentDao.updateByUpdater(updater);
            // 执行监听器
            afterChange(content, mapList);
        }
    }

    /**
     * @param id
     * @Author wanjinyou
     * @Date 2017/2/9
     * @Desc 后台-互动专区--删除
     */
    public Content delete(Integer id) {
        Content bean = deleteContentById(id);
        return bean;
    }
    public Content deleteContentById(Integer id) {
        Content bean = contentDao.findById(id);
        // 执行监听器
        preDelete(bean);
        // 移除tag
        contentTagMng.removeTags(bean.getTags());
        bean.getTags().clear();
        // 删除评论
        cmsCommentMng.deleteByContentId(id);

        bean.clear();
        bean = contentDao.deleteById(id);

        return bean;
    }
    private void preDelete(Content content) {
        if (listenerList != null) {
            for (ContentListener listener : listenerList) {
                listener.preDelete(content);
            }
        }
    }

    /**
     * @Author wanjinyou
     * @Date 2017/2/9
     * @Desc 互动专区--保存
     */
    public Content save(Content bean, ContentExt ext, ContentTxt t, Integer channelId, Integer typeId, Short charge, CmsUser user, boolean b) {
        if(charge==null){
            charge= ContentCharge.MODEL_FREE;
        }
        saveContent(bean, ext, t, channelId, typeId, null,true,user, b);
        // 主栏目也作为副栏目一并保存，方便查询，提高效率。
        Channel channel=channelMng.findById(channelId);
        bean.addToChannels(channel);
        afterSave(bean);
        return bean;

    }
    private Content saveContent(Content bean, ContentExt ext, ContentTxt txt,
                                Integer channelId,Integer typeId, Boolean draft,
                                Boolean contribute,CmsUser user, boolean forMember){
        Channel channel = channelMng.findById(channelId);
        bean.setChannel(channel);
        bean.setType(contentTypeMng.findById(typeId));
        bean.setUser(user);
        Byte userStep;
        if (forMember) {
            // 会员的审核级别按0处理
            userStep = 0;
        } else {
            CmsSite site = bean.getSite();
            userStep = user.getCheckStep(site.getSiteId());
        }
        // 流程处理
        if(contribute!=null&&contribute){
            bean.setStatus(ContentCheck.CONTRIBUTE);
        }else if (draft != null && draft) {
            // 草稿
            bean.setStatus(DRAFT);
        } else {
            if (userStep >= bean.getChannel().getFinalStepExtends()) {
                bean.setStatus(ContentCheck.CHECKED);
            } else {
                bean.setStatus(ContentCheck.CHECKING);
            }
        }
        // 是否有标题图
        bean.setHasTitleImg(!StringUtils.isBlank(ext.getTitleImg()));
        bean.init();
        // 执行监听器
        preSave(bean);
        contentDao.save(bean);
        contentExtMng.save(ext, bean);
        contentTxtMng.save(txt, bean);
        ContentCheck check = new ContentCheck();
        check.setCheckStep(userStep);
        contentCheckMng.save(check, bean);
        contentCountMng.save(new ContentCount(), bean);
        return bean;
    }

    private void preSave(Content content) {
        if (listenerList != null) {
            for (ContentListener listener : listenerList) {
                listener.preSave(content);
            }
        }
    }
    private void afterSave(Content content) {
        if (listenerList != null) {
            for (ContentListener listener : listenerList) {
                listener.afterSave(content);
            }
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
    private ContentTagMng contentTagMng;
    @Resource
    private CmsCommentMng cmsCommentMng;
    @Resource
    private CmsFileMng fileMng;
    @Resource
    private ContentDao contentDao;
    @Resource
    private InteractAreaDao dao;
    @Resource
    private CmsConsultMng cmsConsultMng;

}