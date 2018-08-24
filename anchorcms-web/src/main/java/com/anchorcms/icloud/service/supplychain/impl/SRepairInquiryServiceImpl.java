package com.anchorcms.icloud.service.supplychain.impl;

import com.anchorcms.cms.dao.main.ContentDao;
import com.anchorcms.cms.model.main.*;
import com.anchorcms.cms.service.main.*;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsSite;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.dao.supplychain.SRepairInquiryDao;
import com.anchorcms.icloud.model.payment.SSupplychainOrder;
import com.anchorcms.icloud.model.supplychain.SRepairInquiry;
import com.anchorcms.icloud.model.synergy.SDeviceInquiry;
import com.anchorcms.icloud.service.supplychain.SRepairInquiryService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Date;
import java.util.List;

import static com.anchorcms.cms.model.main.ContentCheck.DRAFT;

/**
 * Created by hansx on 2016/12/27.
 * 维修资源询价业务实现类
 */
@Service
@Transactional
public class SRepairInquiryServiceImpl implements SRepairInquiryService {

    /**
     * @return 根据id获取数据
     * @author hansx
     * @Date 2017/1/4 0004 下午 4:06
     */
    public SRepairInquiry findById(String id) {
        return sRepairInquiryDao.findById(id);
    }

    /**
     * @return 保存询价
     * @author hansx
     * @Date 2017/2/13 0013 下午 3:13
     */
    public SRepairInquiry save(SRepairInquiry srr) {
        return sRepairInquiryDao.save(srr);
    }

    /**
     * @return 保存询价信息
     * @author hansx
     * @Date 2017/1/4 0004 下午 4:06
     */
    public Content save(SRepairInquiry sRepairInquiry, Content c, ContentExt ext, ContentTxt t, Integer channelId, Integer typeId, CmsUser user, boolean forMember, String[] attachmentPaths,
                        String[] attachmentNames, String[] attachmentFilenames) {

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
        //CMS内容表主键id
        sRepairInquiry.setContent(c);
        this.sRepairInquiryDao.save(sRepairInquiry);
        afterSave(c);
        return c;
    }

    /**
     * @return 保存设备询价信息
     * @author hansx
     * @Date 2017/1/4 0004 下午 4:06
     */
    public Content save(SDeviceInquiry sDeviceInquiry, Content c, ContentExt ext, ContentTxt t, Integer channelId, Integer typeId, CmsUser user, boolean forMember, String[] attachmentPaths,
                        String[] attachmentNames, String[] attachmentFilenames) {

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
        //CMS内容表主键id
        sDeviceInquiry.setContent(c);
        this.sRepairInquiryDao.save(sDeviceInquiry);
        afterSave(c);
        return c;
    }
    /**
     * @author hansx
     * @Date 2017/1/5 0005 下午 2:28
     * @return
     * 获取询价列表
     */
    public List<SRepairInquiry> getList() {
        return sRepairInquiryDao.getList();
    }

    /**
     * @author hansx
     * @Date 2017/1/5 0005 下午 2:28
     * @return
     * 待报价管理列表
     */
    public Pagination getInquiryListForMember(String statusType,String inquiryTheme, Date startDate, Date endDate,String companyName, String companyId,
                                              Integer channelId, Integer siteId, Integer modelId, Integer memberId, int pageNo, int pageSize,Boolean flag) {
        return sRepairInquiryDao.getInquiryListForMember(statusType,inquiryTheme, startDate, endDate,companyName, companyId, channelId, siteId, modelId, memberId, pageNo, pageSize,flag);
    }

    public int updateById(String id, Double offer, String statusType) {
        return sRepairInquiryDao.updateById(id,offer,statusType);
    }
    public int updateStatusById(String id, String statusType) {
        return sRepairInquiryDao.updateStatusById(id,statusType);
    }

    public Integer setEvaluteValue(String inquiryId,String evaluteValue){
        return sRepairInquiryDao.setEvaluteValue(inquiryId,evaluteValue);
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

    public void saveOrder(SSupplychainOrder buy) {
        sRepairInquiryDao.save(buy);
    }


    @Resource
    private SRepairInquiryDao sRepairInquiryDao;
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
