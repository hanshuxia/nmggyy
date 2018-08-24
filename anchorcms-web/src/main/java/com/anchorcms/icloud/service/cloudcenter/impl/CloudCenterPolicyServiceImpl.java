package com.anchorcms.icloud.service.cloudcenter.impl;

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
import com.anchorcms.icloud.dao.cloudcenter.CloudCenterPolicyDao;
import com.anchorcms.icloud.model.cloudcenter.SIcloudApply;
import com.anchorcms.icloud.model.cloudcenter.SIcloudPolicy;
import com.anchorcms.icloud.service.cloudcenter.CloudCenterPolicyService;
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
 *@Author 毛成
 *@Date 2017/1/4 0003 15:43
 */
@Service
@Transactional
public class CloudCenterPolicyServiceImpl implements CloudCenterPolicyService {

    /**
     * @author mc
     * @date 2017/1/4
     * @param channelId 栏目ID
     * @param siteId 站点ID
     * @param modelId modelId
     * @param memberId 会员ID
     * @param pageNo 页数
     * @param pageSize 每页大小
     * @param state 状态
     * @desc 获得文章分页
     * @return 文章分页对象
     */
    public Pagination getPageForMember(Integer channelId, Integer siteId, Integer modelId, Integer memberId,
                                       int pageNo, int pageSize, String state, String policyName, Date startDate, Date endDate, String policyLevel) {
        return dao.getPageBySelf(null,memberId, false, false, Content.ContentStatus.all, null,
                siteId, channelId, pageNo, pageSize, state, policyName, startDate, endDate, policyLevel);
    }

    public Content save(SIcloudPolicy sd, Content c, ContentExt ext, ContentTxt t, String[] attachmentPaths, String[] attachmentNames, String[] attachmentFilenames, String[] picPaths, String[] picDescs, Integer channelId, Integer typeId, CmsUser user, Short charge, boolean forMember) {
        if(charge==null){
            charge= ContentCharge.MODEL_FREE;
        }
        saveContent(c, ext, t, channelId, typeId, null,true,user, forMember);
        // 主栏目也作为副栏目一并保存，方便查询，提高效率。
        Channel channel=channelMng.findById(channelId);
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
        sd.setContent(c);
        dao.save(sd);
        afterSave(c);
        return c;
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

    public void update(Integer policyId, SIcloudPolicy sp, String editor, String[] attachmentPaths, String[] attachmentNames, String[] attachmentFilenames, String[] picPaths, String[] picDescs, Integer channelId, CmsUser user, Short charge, boolean b) {
        Content c = sp.getContent();
        ContentExt ext = c.getContentExt();
        //富文本
        ContentTxt txt = c.getContentTxt();
        txt.setTxt(editor);
        // 执行监听器
        List<Map<String, Object>> mapList = preChange(c);
        //更新content
        c = updateContent(c, ext, txt, attachmentPaths, attachmentNames, attachmentFilenames,
                picPaths, picDescs, channelId, charge, user, b);
        CloudCenterPolicyDao.update(sp);
        // 执行监听器
        afterChange(c, mapList);
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

    public SIcloudPolicy delete(Integer policyId) {
        SIcloudPolicy bean = findById(policyId);//获取软件实体类
        Content contentBean = deleteContentById(bean.getContent().getContentId());
        CloudCenterPolicyDao.deleteById(policyId);
        afterDelete(contentBean);
        return bean;
    }

    public SIcloudPolicy findById(Integer policyId) {
        return CloudCenterPolicyDao.findById(policyId);
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
        cmsConsultMng.deleteByContentId(id);
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

    public SIcloudPolicy updateState(Integer policyId, String state, Integer channelId, CmsUser user, Short charge, boolean b) {
        SIcloudPolicy policy = findById(policyId);
        Content content = policy.getContent();
        if (state != null) {
            // 执行监听器
            List<Map<String, Object>> mapList = preChange(content);
            policy.setState(state);
            if (Integer.parseInt(state) == 2){//已发布时将content状态改为已审核
                content.setStatus(ContentCheck.CHECKED);
            }else {//其他未审核状态
                content.setStatus(ContentCheck.CONTRIBUTE);
            }
            // 更新content表
            Updater<Content> updater = new Updater<Content>(content);
            content = contentDao.updateByUpdater(updater);
            //更新政策表
            CloudCenterPolicyDao.update(policy);
            // 执行监听器
            afterChange(content, mapList);
        }
        return null;
    }

    public SIcloudPolicy byPolicyId(Integer policyId){return dao.byPolicyId(policyId);}
    /**
     * @author jsz
     * @date 2017/1/9 13:47
     * @desc 自定义标签——能力列表获取
     **/
    public List<SIcloudPolicy> getList(Integer count, Integer orderBy, Integer listType,Integer listId) {
        return dao.getList(count, orderBy, listType,listId);
    }
    /**
     * @author jsz
     * @date 2017/1/9 13:47
     * @desc 自定义标签——能力列表获取
     **/
    public List<SIcloudApply> getApplyList(Integer count, Integer orderBy, Integer listType) {
        return dao.getApplyList(count, orderBy, listType);
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
        dao.passMany(demandIdsStr);
    }
    /**
     * @Author 闫浩芮
     * 管理员置为驳回需求
     * @Date 2017/2/17 0017 18:39
     */
    public void rejectMany(String demandIdsStr) {
        if(',' == (demandIdsStr.charAt(demandIdsStr.length()-1))){
            demandIdsStr = demandIdsStr.substring(0,demandIdsStr.length()-1);
        }
        dao.rejectMany(demandIdsStr);
    }

    /**
     * @Author 闫浩芮
     * 管理员批量删除
     * @Date 2017/2/17 0017 18:39
     */
    public void deleteMany(String ids) {
        if(',' == (ids.charAt(ids.length()-1))){
            ids = ids.substring(0,ids.length()-1);
            String[] idarr=ids.split(",");
            for(int i =0;i<idarr.length;i++){
                delete(Integer.parseInt(idarr[i]));
            }
        }
    }

    @Resource
    private CloudCenterPolicyDao dao;
    @Resource
    private ChannelMng channelMng;
    @Resource
    private ContentTypeMng contentTypeMng;
    @Resource
    private List<ContentListener> listenerList;
    @Resource
    private ContentTagMng contentTagMng;
    @Resource
    private CmsCommentMng cmsCommentMng;
    @Resource
    private CmsFileMng fileMng;
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
    private CloudCenterPolicyDao CloudCenterPolicyDao;
    @Resource
    private CmsConsultMng cmsConsultMng;

}
