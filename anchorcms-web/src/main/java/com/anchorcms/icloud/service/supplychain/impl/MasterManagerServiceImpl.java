package com.anchorcms.icloud.service.supplychain.impl;

import com.anchorcms.cms.dao.main.ContentDao;
import com.anchorcms.cms.model.main.*;
import com.anchorcms.cms.service.main.*;
import com.anchorcms.common.hibernate.Updater;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsSite;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.dao.supplychain.MasterManagerDao;
import com.anchorcms.icloud.dao.supplychain.SSpareDetailDao;
import com.anchorcms.icloud.model.supplychain.SRepairRes;
import com.anchorcms.icloud.model.supplychain.SSpare;
import com.anchorcms.icloud.service.supplychain.MasterManagerService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.util.Assert;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.anchorcms.cms.model.main.ContentCheck.DRAFT;

/**
 * Created by jxd on 2016/12/27.
 */
@Service
@Transactional
public class MasterManagerServiceImpl implements MasterManagerService {
    /**
     * @return
     * @author dongxuecheng
     * @Date 2017/1/8 16:15
     * @description获取所有维修资源列表
     */
    public List<SRepairRes> getList() {
        return masterManagerDao.getList();
    }

    /**
     * @return
     * @author dongxuecheng
     * @Date 2017/1/8 16:16
     * @description获取耽搁维修资源列表
     */
    public SRepairRes getSRepairResEntity(String id) {
        return masterManagerDao.getSRepairResEntity(id);
    }

    /**
     * @return
     * @author dongxuecheng
     * @Date 2017/1/8 16:16
     * @description发布维修资源
     */
    public Content supplychain_save(SRepairRes sRepairRes, Content c, ContentExt ext, ContentTxt t, Integer channelId, Integer typeId, CmsUser user, boolean b, String[] attachmentPaths, String[] attachmentNames, String[] attachmentFilenames, String[] picPaths, String[] picDescs) {
        Content bean = saveContent(c, ext, t, channelId, typeId, null, true, user, b);
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
        sRepairRes.setContent(c);
        sRepairRes.setContentId(bean.getContentId().toString());//给资源表（s_repair_res）附上contentid
        Channel channel = channelMng.findById(channelId);
        c.addToChannels(channel);
        masterManagerDao.insert(sRepairRes);
        afterSave(c);
        return c;
    }

    /**
     * @return
     * @author dongxuecheng
     * @Date 2017/1/7 10:55
     * @description 获取分页以及SRepairRes表中的数据
     */
    public Pagination getPageForMember(String repairType, Integer channelId, Integer siteId, Integer modelId, String companyId, Boolean isAdmin, int pageNo, int pageSize, Date releaseDt, Date deadlineDt, Integer demandId, String inquiryTheme, String status, String payType, String statusType) {
        return masterManagerDao.getPageBySelf(repairType, channelId, siteId, modelId, isAdmin, pageNo, pageSize, releaseDt, deadlineDt, demandId, inquiryTheme, companyId, status, payType, statusType);
    }

    /**
     * @return
     * @author dongxuecheng
     * @Date 2017/1/20 11:16
     * @description通过信息表获取询价信息
     */
    public Pagination getPageInquiry(Integer channelId, Integer siteId, Integer modelId, String companyId, Integer memberId, int pageNo, int pageSize, Date releaseDt, Date deadlineDt, Integer demandId, String inquiryTheme, String status, String payType, String statusType) {
        return masterManagerDao.getPageInquiry(channelId, siteId, modelId, memberId, pageNo, pageSize, releaseDt, deadlineDt, demandId, inquiryTheme, companyId, status, payType, statusType);
    }

    /**
     * @return
     * @author dongxuecheng
     * @Date 2017/1/7 17:31
     * @description备品备件管理
     */
    public Pagination getSpare(Integer channelId, Integer siteId, Integer modelId, Integer UserId, Integer memberId, int pageNo, int pageSize, Date releaseDt, Date deadlineDt, Integer demandId, String inquiryTheme, String status, String payType, String statusType) {
        return masterManagerDao.getSpare(channelId, siteId, modelId, memberId, pageNo, pageSize, releaseDt, deadlineDt, demandId, inquiryTheme, UserId, status, payType, statusType);
    }

    /**
     * @return
     * @author dongxuecheng
     * @Date 2017/1/9 9:55
     * @description备品备件上传审核
     */
    public Pagination getSpare_chexk(Integer channelId, Integer siteId, Integer modelId, Integer UserId, Integer memberId, int pageNo, int pageSize, Date releaseDt, Date deadlineDt, Integer demandId, String inquiryTheme, String status, String payType, String statusType, String spareName) {
        return masterManagerDao.getSpare_check(channelId, siteId, modelId, memberId, pageNo, pageSize, releaseDt, deadlineDt, demandId, inquiryTheme, UserId, status, payType, statusType, spareName);
    }

    /**
     * @return
     * @author dongxuecheng
     * @Date 2017/1/8 16:19
     * @description根据id删除资源表中对应的信息
     */
    public void delete(String id) {
        SRepairRes sRepairRes = getSRepairResEntity(id);
        Content content = deleteContentById(sRepairRes.getContent().getContentId());
        masterManagerDao.delete(sRepairRes);
        afterDelete(content);
    }

    /**
     * @return
     * @author dongxuecheng
     * @Date 2017/1/8 16:19
     * @description退回
     */
    public void reback(String id) {
        masterManagerDao.reback(id);
    }

    /**
     * @return
     * @author dongxuecheng
     * @Date 2017/1/8 16:19
     * @description发布
     */
    public void relece(String id,CmsUser user) {
        masterManagerDao.relece(id,user);
    }


    /**
     * @return
     * @author dongxuecheng
     * @Date 2017/1/8 16:19
     * @description修改
     */
    // public void update(SRepairRes sRepairRes) {masterManagerDao.update(sRepairRes);}
    public void update(SRepairRes sRepairRes, Content c, ContentExt ext, ContentTxt t, String[] attachmentPaths, String[] attachmentNames, String[] attachmentFilenames, String[] picPaths, String[] picDescs, Integer channelId, Integer typeId, CmsUser user, Short charge, boolean b) {
        //更新cms表内容
        Content nc = contentMng.update(c, ext, t, null, null, null, null, attachmentPaths, attachmentNames, attachmentFilenames, picPaths,
                picDescs, null, channelId, typeId, null, charge, null, user, b);
        //更新需求内容
        sRepairRes.setContent(nc);
        Updater<SRepairRes> updater = new Updater<SRepairRes>(sRepairRes);
//        masterManagerDao.updateByUpdater(updater);
        masterManagerDao.updateRepair(sRepairRes);
        // sDemandCreateDao.deleteOrphan();
    }

    /**
     * @return
     * @author dongxuecheng
     * @Date 2017/1/7 18:44
     * @description备品备件通过
     */
    public void pass(String id) {
        masterManagerDao.pass(id);
    }


    /**
     * @return
     * @author dongxuecheng
     * @Date 2017/1/7 18:44
     * @description备品备件驳回
     */
    public void goback(String id) {
        masterManagerDao.goback(id);
    }

    /**
     * @return
     * @author dongxuecheng
     * @Date 2017/1/8 16:19
     * @description保存content表
     */
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


    /**
     * @return 根据id修改备品备件状态（通过、驳回）
     * @author 苏和 13739980760
     * @Date 2017/1/14 16:20
     */
    public void mdyRepairDemandStateById(String sparepartsId, String status,String backReason) {
        SSpare sSpare = sSpareDao.findById(sparepartsId);
        Content content = sSpare.getContent();
        // 执行监听器
        List<Map<String, Object>> mapList = preChange(content);
        content.setStatus(ContentCheck.CHECKED);
        sSpare.setBackReason(backReason);
        Updater<Content> updater = new Updater<Content>(content);
        content = contentDao.updateByUpdater(updater);
        afterChange(content, mapList);
        masterManagerDao.mdyRepairDemandStateById(sparepartsId, status);
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

    /**
     * @return 根据id批量修改备品备件状态（通过、驳回）
     * @author 苏和 13739980760
     * @Date 2017/1/14 16:20
     */
    public void setRepairDemandStateByIds(String sparepartsId, String status,String backReason) {

        masterManagerDao.setRepairDemandStateByIds(sparepartsId, status,backReason);
    }

    /**
     * @return
     * @author dongxuecheng
     * @Date 2017/2/10 11:07
     * @description获取相似资源的自定义标签方法
     */
    public List<SRepairRes> getList(Integer count, Integer orderBy, Integer listType, String repairType) {
        return masterManagerDao.getList(count, orderBy, listType, repairType);
    }

/**
 * @author dongxuecheng
 * @Date 2017/2/20 17:03
 * @return
 * @description
 */


    @Resource
    private SSpareDetailDao sSpareDao;
    @Resource
    private MasterManagerDao masterManagerDao;
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
    private ContentMng contentMng;

}
