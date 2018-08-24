package com.anchorcms.icloud.service.cloudcenter.impl;

import com.anchorcms.cms.dao.main.ContentDao;
import com.anchorcms.cms.model.main.*;
import com.anchorcms.cms.service.main.*;
import com.anchorcms.core.model.CmsSite;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.dao.cloudcenter.CloudDemandDao;
import com.anchorcms.icloud.model.cloudcenter.SIcloudDemand;
import com.anchorcms.icloud.model.cloudcenter.SIcloudDemandObj;
import com.anchorcms.icloud.service.cloudcenter.CloudService;
import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static com.anchorcms.cms.model.main.ContentCheck.DRAFT;

/**
 * @anther 李利民
 * @descript
 * @data 2017/1/314:58
 */
@Service
@Transactional
public class CloudServiceImpl implements CloudService {
    public Content save(SIcloudDemand bean, Content c, ContentExt ext, ContentTxt t, String[] attachmentPaths, String[] attachmentNames, String[] attachmentFilenames, String[] picPaths, String[] picDescs, Integer channelId, Integer typeId, CmsUser user, Short charge, boolean forMember) {
        {
            if (charge == null) {
                charge = ContentCharge.MODEL_FREE;
            }
            saveContent(c, ext, t, channelId, typeId, null, true, user, forMember);
            // 主栏目也作为副栏目一并保存，方便查询，提高效率。
            Channel channel = channelMng.findById(channelId);
            c.addToChannels(channel);
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
            bean.setContent(c);
            cloudDemandDao.save(bean);
            afterSave(c);
            return c;
        }
    }

    private Content saveContent(Content bean, ContentExt ext, ContentTxt txt,
                                Integer channelId, Integer typeId, Boolean draft,
                                Boolean contribute, CmsUser user, boolean forMember) {
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
        if (contribute != null && contribute) {
            bean.setStatus(ContentCheck.CONTRIBUTE);
        } else if (draft != null && draft) {
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
    @Resource
    private CloudDemandDao cloudDemandDao;
}
