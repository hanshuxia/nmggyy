package com.anchorcms.icloud.service.commservice.impl;

import com.anchorcms.cms.dao.main.ContentDao;
import com.anchorcms.cms.model.main.Content;
import com.anchorcms.cms.model.main.ContentExt;
import com.anchorcms.cms.model.main.ContentTxt;
import com.anchorcms.cms.service.main.*;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.model.commservice.SBidNotice;
import com.anchorcms.icloud.model.commservice.STenderNotice;
import com.anchorcms.icloud.model.commservice.STenderTrailer;
import com.anchorcms.icloud.service.commservice.LDTenderNoticesManageService;
import org.omg.CORBA.Object;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * DESCRIPTION:s
 * Author: DELL
 * Date:2017/1/13.
 */
@Service
@Transactional
public class LDTenderNoticesManageServiceImpl implements LDTenderNoticesManageService {
    /**
     * @return 获取招标公告管理列表
     * @author ldong
     * @Date 2017/1/13 10:30
     */
    public Pagination getTenderNoticesList(String title, Integer channelId, Integer siteId, Integer modelId, Integer memberId, int pageNo, int pageSize, String state) {
        return LDTenderNoticesManageDao.getTenderNoticesList(title, null, memberId, memberId, false, false, Content.ContentStatus.all, null, siteId, modelId, channelId, 0, pageNo, pageSize, state);
    }

    /**
     * @return 招标管理列表删除功能
     * @author ldong
     * @Date 2017/1/13 15:59
     */
    public void deleteWithTenderManage(int id, String state) {

        if ("1".equals(state)) {
            STenderTrailer bean = LDTenderNoticesManageDao.getTenderTrailerById(id);
            Content contentBean = deleteContentById(bean.getContent().getContentId());
            this.LDTenderNoticesManageDao.deleteTenderTrailer(bean);
            afterDelete(contentBean);
        } else if("2".equals(state)){
            STenderNotice bean =  LDTenderNoticesManageDao.getSTenderNoticeById(id);
            Content contentBean = deleteContentById(bean.getContent().getContentId());
             this.LDTenderNoticesManageDao.deleteTenderNotice(bean);
            afterDelete(contentBean);
        }else if("3".equals(state)){
            SBidNotice bean =   LDTenderNoticesManageDao.getSBidNoticeById(id);
            Content contentBean = deleteContentById(bean.getContent().getContentId());
            this.LDTenderNoticesManageDao.deleteBidNotice(bean);
            afterDelete(contentBean);
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
    private void afterDelete(Content content) {
        if (listenerList != null) {
            for (ContentListener listener : listenerList) {
                listener.afterDelete(content);
            }
        }
    }
    public Object getObjforTender(int id, String state) {
        return null;
    }
/**
 * @author ldong
 * @Date 2017/1/16 10:16
 * @return
 * 保存招标预告预告
 */
   /* public Content updateTenderTrail(int contentId,STenderTrailer stt, HttpServletRequest request, Content c, ContentExt ext, ContentTxt t, String nextUrl, Integer modelId,
                          Integer channelId, String textarea1, CmsUser user, Short charge, Integer typeId, boolean forMember,
                          HttpServletResponse response, ModelMap model){
        if(charge==null){
            charge= ContentCharge.MODEL_FREE;
        }
        saveContent(c, ext, t, channelId, typeId, null,true,user, forMember);
        // 主栏目也作为副栏目一并保存，方便查询，提高效率。
        Channel channel=channelMng.findById(channelId);
        c.addToChannels(channel);
        Content content = contentDao.findById(contentId);
        if (content != null) {
            preDelete(content);
            contentDao.deleteById(contentId);
            afterDelete(content);
        }
        stt.setContent(c);
        LDTenderNoticesManageDao.saveOrUpdateTenderTrailer(stt);
        afterSave(c);
        return c;
    }*/
    /**
     * @author ldong
     * @Date 2017/1/16 10:44
     * @return
     */
  /*  public Content updateTenderNotice(int contentId,STenderNotice stn, HttpServletRequest request, Content c, ContentExt ext, ContentTxt t, String nextUrl, Integer modelId,
                                      Integer channelId, String textarea1, CmsUser user, Short charge, Integer typeId, boolean forMember,
                                      HttpServletResponse response, ModelMap model){
        if(charge==null){
            charge= ContentCharge.MODEL_FREE;
        }
        saveContent(c, ext, t, channelId, typeId, null,true,user, forMember);
        // 主栏目也作为副栏目一并保存，方便查询，提高效率。
        Channel channel=channelMng.findById(channelId);
        c.addToChannels(channel);
        Content content = contentDao.findById(contentId);
        if (content != null) {
            preDelete(content);
            contentDao.deleteById(contentId);
            afterDelete(content);
        }
        stn.setContent(c);
        LDTenderNoticesManageDao.saveOrUpdateTenderNotice(stn);
        afterSave(c);
        return c;
    }*/


    /**
     * @author ldong
     * @Date 2017/1/16 11:03
     * @return
     * 中标公告保存
     */

   /* public Content updateBidNotice( int contentId,SBidNotice bean, Content c, ContentExt ext, ContentTxt t,
                        String[] attachmentPaths, String[] attachmentNames,
                        String[] attachmentFilenames, String[] picPaths,
                        String[] picDescs, Integer channelId, Integer typeId, CmsUser user, Short charge, boolean forMember) {
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
        Content content = contentDao.findById(contentId);
        if (content != null) {
            preDelete(content);
            contentDao.deleteById(contentId);
            afterDelete(content);
        }
        bean.setContent(c);
        LDTenderNoticesManageDao.saveOrUpdateBidNotice(bean);
        afterSave(c);
        return c;

    }*/


    /**
 * @author ldong
 * @Date 2017/1/17 16:33
 * @return
 * 保存招标预告——new
 */

    public void updateTenderTrail_new(STenderTrailer bean, Content c, ContentExt ext, ContentTxt t,
                                      String[] attachmentPaths, String[] attachmentNames, String[] attachmentFilenames,
                                      String[] picPaths, String[] picDescs, Integer channelId, Integer typeId,
                                      CmsUser user, Short charge, boolean b) {

        //更新cms表内容
        Content nc=contentMng.update(c,ext,t,null,null,null,null,attachmentPaths,attachmentNames,attachmentFilenames,picPaths,
                picDescs,null,channelId,typeId,null,charge,null,user,b);
        //更新需求内容
        bean.setContent(nc);
        LDTenderNoticesManageDao.saveOrUpdateTenderTrailer(bean);

    }

    public void updateTenderNotice_new(STenderNotice bean, Content c, ContentExt ext, ContentTxt t, String[] attachmentPaths, String[] attachmentNames, String[] attachmentFilenames, String[] picPaths, String[] picDescs, Integer channelId, Integer typeId, CmsUser user, Short charge, boolean b) {
        //更新cms表内容
        Content nc=contentMng.update(c,ext,t,null,null,null,null,attachmentPaths,attachmentNames,attachmentFilenames,picPaths,
                picDescs,null,channelId,typeId,null,charge,null,user,b);
        //更新需求内容
        bean.setContent(nc);
        LDTenderNoticesManageDao.saveOrUpdateTenderNotice(bean);

    }
/**
 * @author ldong
 * @Date 2017/1/17 17:08
 * @return
 * 保存 中标公告信息——new
 */
    public void updateBidNotice_new(SBidNotice bean, Content c, ContentExt ext, ContentTxt t, String[] attachmentPaths, String[] attachmentNames, String[] attachmentFilenames, String[] picPaths, String[] picDescs, Integer channelId, Integer typeId, CmsUser user, Short charge, boolean b) {
        //更新cms表内容
        Content nc=contentMng.update(c,ext,t,null,null,null,null,attachmentPaths,attachmentNames,attachmentFilenames,picPaths,
                picDescs,null,channelId,typeId,null,charge,null,user,b);
        //更新需求内容
        bean.setContent(nc);
        LDTenderNoticesManageDao.saveOrUpdateBidNotice(bean);
    }

   /* private Content saveContent(Content bean, ContentExt ext, ContentTxt txt,
                                Integer channelId, Integer typeId, Boolean draft,
                                Boolean contribute, CmsUser user, boolean forMember){
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
    }*/

  /*  private void preSave(Content content) {
        if (listenerList != null) {
            for (ContentListener listener : listenerList) {
                listener.preSave(content);
            }
        }
    }*/
  /*  private void afterSave(Content content) {
        if (listenerList != null) {
            for (ContentListener listener : listenerList) {
                listener.afterSave(content);
            }
        }
    }*/

    @Resource
    com.anchorcms.icloud.dao.commservice.LDTenderNoticesManageDao LDTenderNoticesManageDao;
    @Resource
    private ChannelMng channelMng;
    @Resource
    private ContentMng contentMng;
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
    private com.anchorcms.icloud.dao.supplychain.RepairDemandMnageDao RepairDemandMnageDao;
}
