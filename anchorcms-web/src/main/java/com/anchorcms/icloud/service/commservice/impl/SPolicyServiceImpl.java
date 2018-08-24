package com.anchorcms.icloud.service.commservice.impl;

import com.anchorcms.cms.dao.main.ContentDao;
import com.anchorcms.cms.model.main.*;
import com.anchorcms.cms.service.main.*;
import com.anchorcms.core.model.CmsSite;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.dao.commservice.SPolicyDao;
import com.anchorcms.icloud.model.commservice.SAmplePolicy;
import com.anchorcms.icloud.service.commservice.SPolicyService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

import static com.anchorcms.cms.model.main.ContentCheck.CHECKED;
import static com.anchorcms.cms.model.main.ContentCheck.DRAFT;

/**
 * @Author lisong
 * @Date 2017/1/16 11:20
 *惠补政策添加
 */
@Service
@Transactional
public class SPolicyServiceImpl implements SPolicyService {
    public Content supplychain_save_hbzc(SAmplePolicy sAmplePolicy, Content c, ContentExt ext, ContentTxt t, Integer channelId, Integer typeId, CmsUser user, boolean b, String[] attachmentPaths, String[] attachmentNames, String[] attachmentFilenames, String[] picPaths, String[] picDescs) {
        Content bean=saveContent(c, ext, t, channelId, typeId, sAmplePolicy.getSelectedStatus(), true, user, b);
        // 主栏目也作为副栏目一并保存，方便查询，提高效率。

        // 保存附件
        if (attachmentPaths != null && attachmentPaths.length > 0) {
            for (int i = 0, len = attachmentPaths.length; i < len; i++) {
                if (!StringUtils.isBlank(attachmentPaths[i])) {
                    c.addToAttachmemts(attachmentPaths[i],
                            attachmentNames[i], attachmentFilenames[i]);
                }
            }
        }
        // 保存图片集
        if (picPaths != null && picPaths.length > 0) {
            for (int i = 0, len = picPaths.length; i < len; i++) {
                if (!StringUtils.isBlank(picPaths[i])) {
                    c.addToPictures(picPaths[i], picDescs[i]);
                }
            }
        }
        sAmplePolicy.setContent(c);
        Channel channel = channelMng.findById(channelId);
        c.addToChannels(channel);
        masterManagerDao.insert(sAmplePolicy);
        afterSave(c);
        return c;
    }
    private Content saveContent(Content bean, ContentExt ext, ContentTxt txt,
                                Integer channelId,Integer typeId, String status,
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
        if(status.equals("1")){
            // 草稿
            bean.setStatus(DRAFT);
        }else  if(status.equals("2")){
            // 已发布
            bean.setStatus(CHECKED);
        }else if(contribute!=null&&contribute){
            // 流程处理
            bean.setStatus(ContentCheck.CONTRIBUTE);
        }

        //暂时在新增时将content审核状态改为通过来生成全文检索索引
//        bean.setStatus(ContentCheck.CHECKED);
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

    private void afterDelete(Content content) {
        if (listenerList != null) {
            for (ContentListener listener : listenerList) {
                listener.afterDelete(content);
            }
        }
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

    @Resource
    private ContentDao contentDao;
    @Resource
    private SPolicyDao masterManagerDao ;
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
}
