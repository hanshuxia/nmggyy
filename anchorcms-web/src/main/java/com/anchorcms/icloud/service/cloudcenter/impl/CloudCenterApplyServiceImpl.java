package com.anchorcms.icloud.service.cloudcenter.impl;

import com.anchorcms.cms.dao.main.ContentDao;
import com.anchorcms.cms.model.main.*;
import com.anchorcms.cms.service.assist.CmsCommentMng;
import com.anchorcms.cms.service.assist.CmsFileMng;
import com.anchorcms.cms.service.main.*;
import com.anchorcms.common.hibernate.Updater;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsSite;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.dao.cloudcenter.CloudApplyDao;
import com.anchorcms.icloud.model.cloudcenter.SIcloudApply;
import com.anchorcms.icloud.service.cloudcenter.CloudCenterApplyService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.anchorcms.cms.model.main.ContentCheck.DRAFT;

/**
 * @anther 李利民
 * @descript
 * @data 2017/1/711:52
 */
@Service
@Transactional
public class CloudCenterApplyServiceImpl implements CloudCenterApplyService {
    public Content save(SIcloudApply bean, Content c, ContentExt ext, ContentTxt t, String[] attachmentPaths, String[] attachmentNames, String[] attachmentFilenames, String[] picPaths, String[] picDescs, Integer channelId, Integer typeId, CmsUser user, Short charge, boolean forMember) {
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
        cloudApplyDao.save(bean);
        afterSave(c);
        return c;
    }

    /**
     * @param siteId
     * @param user
     * @param pageNo
     * @anther lilimin
     * @descript
     * @data 2017/1/711:51
     */
    public Pagination getSoftwarePage(Integer siteId, CmsUser user, int pageNo, int pageSize) {
        return cloudApplyDao.getPage(siteId, user, pageNo, pageSize);
    }

    /**
     * @param id
     * @anther lilimin
     * @descript 通过ID 或得数据
     * @data 2017/1/711:51
     */
    public SIcloudApply byApplyId(Integer id) {
        return cloudApplyDao.findById(id);
    }

    public void update(Integer applyId, SIcloudApply sd, String editor, String[] attachmentPaths, String[] attachmentNames, String[] attachmentFilenames, String[] picPaths, String[] picDescs, Integer channelId, CmsUser user, Short charge, boolean b) {
        Content c = sd.getContent();
        ContentExt ext = c.getContentExt();
        //富文本
        ContentTxt txt = c.getContentTxt();
        txt.setTxt(editor);
        // 执行监听器
        List<Map<String, Object>> mapList = preChange(c);
        //更新content
        c = updateContent(c, ext, txt, attachmentPaths, attachmentNames, attachmentFilenames,
                picPaths, picDescs, channelId, charge, user, b);
        cloudApplyDao.update(sd);
        // 执行监听器
        afterChange(c, mapList);
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

    public Content updateContent(Content bean, ContentExt ext, ContentTxt txt,
                                 String[] attachmentPaths, String[] attachmentNames,
                                 String[] attachmentFilenames, String[] picPaths,
                                 String[] picDescs, Integer channelId, Short charge,
                                 CmsUser user, boolean forMember) {

        // 更新主表
        Updater<Content> updater = new Updater<Content>(bean);
        bean = contentDao.updateByUpdater(updater);

        // 更新扩展表
        contentExtMng.update(ext);
        // 更新文本表
        contentTxtMng.update(txt, bean);
        bean.getAttachments().clear();
        if (attachmentPaths != null && attachmentPaths.length > 0) {
            for (int i = 0, len = attachmentPaths.length; i < len; i++) {
                if (!StringUtils.isBlank(attachmentPaths[i])) {
                    bean.addToAttachmemts(attachmentPaths[i],
                            attachmentNames[i], attachmentFilenames[i]);
                }
            }
        }
        // 更新图片集
        bean.getPictures().clear();
        if (picPaths != null && picPaths.length > 0) {
            for (int i = 0, len = picPaths.length; i < len; i++) {
                if (!StringUtils.isBlank(picPaths[i])) {
                    bean.addToPictures(picPaths[i], picDescs[i]);
                }
            }
        }

        return bean;
    }

    public Pagination getFeedbackPage(Integer siteId, CmsUser user, int pageNo, int pageSize, Date startDate, Date endDate, String applierName, String policyLevel, String policyName, String policyCode, String state) {
        return cloudApplyDao.getFeedbackPage(siteId, user, pageNo, pageSize, startDate, endDate, applierName, policyLevel, policyName, policyCode, state);
    }

    /**
     * @anther wanjinyou
     * @descript 政策申请跟踪
     * @data 2017/1/12
     */
    public Pagination getFollowbackPage(Integer siteId, CmsUser user, int pageNo, int pageSize, Date startDate, Date endDate, String applierName, String policyLevel, String policyName, String policyCode, String state, String userName) {
        return cloudApplyDao.getFollowbackPage(siteId, user, pageNo, pageSize, startDate, endDate, applierName, policyLevel, policyName, policyCode, state, userName);
    }

    public SIcloudApply updateState(Integer id, String state, Integer channelId, CmsUser user, Short charge, boolean b,String backReason) {
        SIcloudApply apply = cloudApplyDao.findById(id);
        apply.setState(state);
        apply.setBackReason(backReason);
        cloudApplyDao.save(apply);
        return null;
    }

    public SIcloudApply delete(Integer id) {
        SIcloudApply bean = findById(id);//获取软件实体类
        Content contentBean = deleteContentById(bean.getContent().getContentId());
        cloudApplyDao.deleteById(id);
        afterDelete(contentBean);
        return bean;
    }

    public SIcloudApply findById(Integer id) {
        return cloudApplyDao.findById(id);
    }

    /**
     * @Author 闫浩芮
     * 管理员置为通过需求
     * @Date 2017/2/17 0017 18:39
     */
    public void passMany(String demandIdsStr, String state) {
        if (',' == (demandIdsStr.charAt(demandIdsStr.length() - 1))) {
            demandIdsStr = demandIdsStr.substring(0, demandIdsStr.length() - 1);
        }
        cloudApplyDao.passMany(demandIdsStr, state);
    }

    /**
     * @Author 闫浩芮
     * 管理员置为驳回需求
     * @Date 2017/2/17 0017 18:39
     */
    public void rejectMany(String demandIdsStr,String backReason) {
        if (',' == (demandIdsStr.charAt(demandIdsStr.length() - 1))) {
            demandIdsStr = demandIdsStr.substring(0, demandIdsStr.length() - 1);
        }
        cloudApplyDao.rejectMany(demandIdsStr,backReason);
    }

    /**
     * @Author 闫浩芮
     * 管理员批量删除
     * @Date 2017/2/17 0017 18:39
     */
    public void deleteMany(String ids) {
        if (',' == (ids.charAt(ids.length() - 1))) {
            ids = ids.substring(0, ids.length() - 1);
            String[] idarr = ids.split(",");
            for (int i = 0; i < idarr.length; i++) {
                delete(Integer.parseInt(idarr[i]));
            }
        }

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
        //删除附件记录
        fileMng.deleteByContentId(id);
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

    private void afterDelete(Content content) {
        if (listenerList != null) {
            for (ContentListener listener : listenerList) {
                listener.afterDelete(content);
            }
        }
    }

    @Resource
    private ChannelMng channelMng;
    @Resource
    private ContentTypeMng contentTypeMng;
    @Resource
    private ContentTxtMng contentTxtMng;
    @Resource
    private ContentExtMng contentExtMng;
    @Resource
    private ContentCountMng contentCountMng;
    @Resource
    private ContentCheckMng contentCheckMng;
    @Resource
    private CloudApplyDao cloudApplyDao;
    @Resource
    private List<ContentListener> listenerList;
    @Resource
    private ContentDao contentDao;
    @Resource
    private ContentTagMng contentTagMng;
    @Resource
    private CmsCommentMng cmsCommentMng;
    @Resource
    private CmsFileMng fileMng;
}

