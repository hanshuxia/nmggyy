package com.anchorcms.icloud.service.synergy.impl;

import com.anchorcms.cms.dao.main.ContentDao;
import com.anchorcms.cms.model.main.*;
import com.anchorcms.cms.service.assist.CmsCommentMng;
import com.anchorcms.cms.service.assist.CmsConsultMng;
import com.anchorcms.cms.service.assist.CmsFileMng;
import com.anchorcms.cms.service.main.*;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsSite;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.dao.synergy.BrainStormDao;
import com.anchorcms.icloud.service.synergy.BrainStormService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

import static com.anchorcms.cms.model.main.ContentCheck.DRAFT;

/**
 * @anther maocheng
 * @descript
 * @data 2017/2/10
 */
@Service
@Transactional
public class BrainStormServiceImpl implements BrainStormService {
    /**
     * @param bean
     * @param ext
     * @param t
     * @param channelId
     * @param typeId
     * @param charge
     * @param user
     * @param b
     * @Author maocheng
     * @Date 2017/2/10
     * @Desc 头脑风暴公告--保存
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

    /**
     * @param id
     * @Author maocheng
     * @Date 2017/2/14
     * @Desc 后台-互动专区--获取实体类
     */
    public Content byContentId(Integer id) {
        return dao.byContentId(id);
    }

    /**
     * @param id
     * @Author maocheng
     * @Date 2017/2/14
     * @Desc 后台-互动专区--删除
     */
    public Content delete(Integer id){
        Content bean = deleteContentById(id);
        return bean;
    }

    public void deletes(String ids) {
        deleteContentByIds(ids);
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

    public void deleteContentByIds(String ids) {
        List<Content> beans = contentDao.findByIds(ids);
        for(Content content : beans){
            // 执行监听器
            preDelete(content);
            // 移除tag
            contentTagMng.removeTags(content.getTags());
            content.getTags().clear();
            // 删除评论
            cmsCommentMng.deleteByContentId(content.getContentId());

            content.clear();
            contentDao.deleteById(content.getContentId());
        }

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
     * @Desc 后台-互动专区--列表
     */
    public Pagination getPageForMember(Integer channelId, Integer siteId, Integer modelId, Integer userId, int pageNo, int pageSize, String author,String title, Byte status) {
        return dao.getPageBySelf(null, false, false, Content.ContentStatus.all, null,
                siteId, channelId, pageNo, pageSize, author,title, status);
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
    private ContentDao contentDao;
    @Resource
    private BrainStormDao dao;
    @Resource
    private CmsConsultMng cmsConsultMng;
    @Resource
    private CmsFileMng fileMng;
}
