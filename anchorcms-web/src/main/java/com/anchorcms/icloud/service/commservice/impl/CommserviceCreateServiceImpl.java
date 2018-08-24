package com.anchorcms.icloud.service.commservice.impl;

import com.anchorcms.cms.dao.main.ContentDao;
import com.anchorcms.cms.model.main.*;
import com.anchorcms.cms.service.main.*;
import com.anchorcms.core.model.CmsSite;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.dao.commservice.CommserviceCreateDao;
import com.anchorcms.icloud.model.commservice.SBidNotice;
import com.anchorcms.icloud.model.commservice.SStiteManager;
import com.anchorcms.icloud.model.commservice.STenderNotice;
import com.anchorcms.icloud.model.commservice.STenderTrailer;
import com.anchorcms.icloud.service.commservice.CommserviceCreateService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

import static com.anchorcms.cms.model.main.ContentCheck.DRAFT;

/**
 * Created by ${耿文龙} on 2017/1/13.
 */
@Service
@Transactional
public class CommserviceCreateServiceImpl implements CommserviceCreateService {

    public Content save(SBidNotice bean, Content c, ContentExt ext, ContentTxt t,
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
//        java.sql.Date createDate = new java.sql.Date(System.currentTimeMillis());
        bean.setContent(c);
        //下面这些set挪到controller里了 by GJN
//        bean.setOperatorId(user.getUserId()+"");
//        bean.setReleaseId(user.getUserId()+"");发布的时候set
//        bean.setCreateDt(createDate);
//        bean.setReleaseDt(createDate);发布的时候set
//        bean.setUpdateDt(createDate);
//        bean.setStatus("1");
//        bean.setDeFlag("0");1为有效
//        bean.setCompanyId(user.getCompany().getCompanyId());
        commserviceCreateDao.save(bean);
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
        //暂时在新增时将content审核状态改为通过来生成全文检索索引
        bean.setStatus(ContentCheck.CHECKED);
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
     * @author machao
     * @Date 2017/1/17 10:59
     * @return
     * 自定义标签-招标公告
     */
    public List<STenderNotice> getStenderNoticeList(Integer count, Integer orderBy, Integer listType){
        return commserviceCreateDao.getStenderNoticeList(count, orderBy, listType);
    }

    /**
     * @author machao
     * @Date 2017/1/17 10:59
     * @return
     * 自定义标签-招标预告
     */
    public List<STenderTrailer> getStenderTrailerList(Integer count, Integer orderBy, Integer listType){
        return commserviceCreateDao.getStenderTrailerList(count, orderBy, listType);
    }

    /**
     * @author machao
     * @Date 2017/1/17 10:59
     * @return
     * 自定义标签-中标公告
     */
    public List<SBidNotice> getSBidNoticeList(Integer count, Integer orderBy, Integer listType){
        return commserviceCreateDao.getSbidNoticeList(count, orderBy, listType);
    }
    /**
     * @author machao
     * @Date 2017/1/17 10:59
     * @return
     * 自定义标签-政务导航
     */
    public List<SStiteManager> getSstiteManagerList(Integer count, Integer orderBy, Integer listType){
        return commserviceCreateDao.getSstiteManagerList(count, orderBy, listType);
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
    private CommserviceCreateDao commserviceCreateDao;
}
