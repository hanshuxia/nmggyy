package com.anchorcms.icloud.service.cloudcenter.impl;

import com.anchorcms.cms.dao.main.ContentDao;
import com.anchorcms.cms.model.main.Content;
import com.anchorcms.cms.model.main.ContentExt;
import com.anchorcms.cms.model.main.ContentTxt;
import com.anchorcms.cms.service.assist.CmsCommentMng;
import com.anchorcms.cms.service.assist.CmsConsultMng;
import com.anchorcms.cms.service.assist.CmsFileMng;
import com.anchorcms.cms.service.main.ContentExtMng;
import com.anchorcms.cms.service.main.ContentListener;
import com.anchorcms.cms.service.main.ContentTagMng;
import com.anchorcms.cms.service.main.ContentTxtMng;
import com.anchorcms.common.hibernate.Updater;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.dao.cloudcenter.CloudCenterDemandDao;
import com.anchorcms.icloud.model.cloudcenter.SIcloudDemand;
import com.anchorcms.icloud.service.cloudcenter.CloudCenterDemandService;
import org.apache.commons.lang.StringUtils;
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
 * @Email netuser.orz@icloud.com
 * @Date 2017/1/3 0003
 * @Desc
 */
@Service
@Transactional
public class CloudCenterDemandServiceImpl implements CloudCenterDemandService {
    public Pagination getPageForMember(Integer channelId, Integer siteId, Integer modelId, Integer UserId,
                                       Integer memberId, int pageNo, int pageSize, Date releaseDt, Date deadlineDt, Integer demandId, String demandName,
                                       String status, String payType) {
        return dao.getPageBySelf(channelId, siteId, modelId, UserId, memberId, pageNo, pageSize, releaseDt, deadlineDt, demandId, demandName, status, payType);
    }

    /**
     * @param demandId 文章id
     * @Author iangshuzhong
     * @Date 2016/1/6
     * @Desc 需求管理详情
     */
    public SIcloudDemand byDemandId(Integer demandId) {
        return dao.bySIcloudDemandId(demandId);
    }

    /**
     * @Author wanjinyou
     * @Date 2017/1/10
     * @Desc 云需求管理列表--编辑保存
     */
    public SIcloudDemand editSave(Integer id, SIcloudDemand sd, String detailDesc,
                                  String[] attachmentPaths, String[] attachmentNames,
                                  String[] attachmentFilenames, String[] picPaths,
                                  String[] picDescs, Integer channelId, CmsUser user, Short charge, boolean b) {

        SIcloudDemand icloudDemand = byDemandId(id);
        icloudDemand.setDemandName(sd.getDemandName());
        icloudDemand.setServerType(sd.getServerType());
        icloudDemand.setCount(sd.getCount());
        icloudDemand.setDealType(sd.getDealType());
        icloudDemand.setPayType(sd.getPayType());
        icloudDemand.setInvoiceType(sd.getInvoiceType());
        icloudDemand.setFreightBorne(sd.getFreightBorne());
        icloudDemand.setAddrProvince(sd.getAddrProvince());
        icloudDemand.setAddrCity(sd.getAddrCity());
        icloudDemand.setAddrCounty(sd.getAddrCounty());
        icloudDemand.setAddrStreet(sd.getAddrStreet());
        icloudDemand.setReleaseType(sd.getReleaseType());
        icloudDemand.setContact(sd.getContact());
        icloudDemand.setMobile(sd.getMobile());
        icloudDemand.setTelephone(sd.getTelephone());
        icloudDemand.setEmail(sd.getEmail());
        icloudDemand.setFax(sd.getFax());
        icloudDemand.setUnit(sd.getUnit());
        icloudDemand.setClassify_code(sd.getClassify_code());
        icloudDemand.setExpect_price(sd.getExpect_price());
        icloudDemand.setInquiryComp(sd.getInquiryComp());
        icloudDemand.setDeadlineDt(sd.getDeadlineDt());
        icloudDemand.setDeliverDt(sd.getDeliverDt());
        icloudDemand.setCompany(sd.getCompany());
        icloudDemand.setReleaseDt(sd.getReleaseDt());
        icloudDemand.setStatus(sd.getStatus());
        icloudDemand.setDeFlag(sd.getDeFlag());
        icloudDemand.setCreateDt(sd.getCreateDt());
        icloudDemand.setUpdateDt(sd.getUpdateDt());
        icloudDemand.setCreaterId(sd.getCreaterId());
        icloudDemand.setInvoiceCompany(sd.getInvoiceCompany());
        icloudDemand.setTaxRegNo(sd.getTaxRegNo());
        icloudDemand.setRegisteredAddress(sd.getRegisteredAddress());
        icloudDemand.setBankInfo(sd.getBankInfo());
        Content c = icloudDemand.getContent();
        ContentExt ext = c.getContentExt();
        ext.setTitle(sd.getDemandName());
        //富文本
        ContentTxt txt = c.getContentTxt();
        txt.setTxt(detailDesc);
        // 执行监听器
        List<Map<String, Object>> mapList = preChange(c);
        //更新content
        c = updateContent(c, ext, txt, attachmentPaths, attachmentNames, attachmentFilenames,
                picPaths, picDescs, channelId, charge, user, b);
        dao.editSave(icloudDemand);
        // 执行监听器
        afterChange(c, mapList);
        return null;
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
        if (txt != null) {
            contentTxtMng.update(txt, bean);
        }
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

    /**
     * @param id
     * @Author wanjinyou
     * @Date 2017/1/10
     * @Desc 云需求管理列表--删除
     */
    public SIcloudDemand deleteById(Integer id) {
        SIcloudDemand bean = byDemandId(id);
        Content contentBean = deleteContentById(bean.getContent().getContentId());
        dao.deleteById(id);
        afterDelete(contentBean);
        return bean;

    }

    /**
     * @author jsz
     * @date 2017/1/10 13:47
     * @desc 自定义标签——能力列表获取
     **/
    public List<SIcloudDemand> getList(Integer count, Integer orderBy, Integer listType, Integer listId) {
        return dao.getList(count, orderBy, listType, listId);
    }

    public Integer changeDemandStatus(Integer demandId, String status) {
        return dao.changeDemandStatus(demandId, status);
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
        // 删除留言
        cmsConsultMng.deleteByContentId(id);
        //删除附件记录
        fileMng.deleteByContentId(id);
        bean.clear();
        bean = contentDao.deleteById(id);
        // 执行监听器

        return bean;
    }

    public Content[] deleteContentByIds(Integer[] ids) {
        Content[] beans = new Content[ids.length];
        for (int i = 0, len = ids.length; i < len; i++) {
            beans[i] = deleteContentById(ids[i]);
        }
        return beans;
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
    private CloudCenterDemandDao dao;
    @Resource
    private List<ContentListener> listenerList;
    @Resource
    private ContentDao contentDao;
    @Resource
    private ContentTxtMng contentTxtMng;
    @Resource
    private ContentExtMng contentExtMng;
    @Resource
    private ContentTagMng contentTagMng;
    @Resource
    private CmsCommentMng cmsCommentMng;
    @Resource
    private CmsConsultMng cmsConsultMng;
    @Resource
    private CmsFileMng fileMng;
}
