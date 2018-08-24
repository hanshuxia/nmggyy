package com.anchorcms.icloud.service.commservice.impl;

import com.anchorcms.cms.dao.main.ContentDao;
import com.anchorcms.cms.model.main.*;
import com.anchorcms.cms.service.main.*;
import com.anchorcms.common.hibernate.Updater;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsSite;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.dao.commservice.SSoldNoteDao;
import com.anchorcms.icloud.model.commservice.SSoldNote;
import com.anchorcms.icloud.service.commservice.SSoldNoteService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.util.Assert;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.anchorcms.cms.model.main.ContentCheck.CHECKED;
import static com.anchorcms.cms.model.main.ContentCheck.DRAFT;

/**
 * Created by dxc on 2017/1/13 0013.
 * <p>
 * 招标预告信息业务实现类
 */
@Service
@Transactional
public class SSoldNoteServiceImpl implements SSoldNoteService {
    /**
     * @return
     * @author dongxuecheng
     * @Date 2017/1/14 9:53
     * @description销售记录发布保存功能
     */
    public Content supplychain_save(SSoldNote sSoldNote, Content c, ContentExt ext, ContentTxt t, Integer channelId, Integer typeId, CmsUser user, boolean b, String[] attachmentPaths, String[] attachmentNames, String[] attachmentFilenames, String[] picPaths, String[] picDescs) {
        Content bean = saveContent(c, ext, t, channelId, typeId, sSoldNote.getStatus(), true, user, b);
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
        sSoldNote.setContent(c);
        sSoldNote.setContentId(bean.getContentId());//给资源表（s_repair_res）附上contentid
        Channel channel = channelMng.findById(channelId);
        c.addToChannels(channel);
        sSoldNoteDao.insert(sSoldNote);
        afterSave(c);
        return c;
    }


    /**
     * @return
     * @author dongxuecheng
     * @Date 2017/1/7 10:55
     * @description 销售记录获取分页和数据功能
     */
    public Pagination getPageForMember(Integer channelId, Integer siteId, Integer modelId, Integer UserId, Boolean isAdmin, int pageNo, int pageSize, Date releaseDt, Date deadlineDt, Integer demandId, String inquiryTheme, String status, String payType, String statusType) {
        return sSoldNoteDao.getPageBySelf(channelId, siteId, modelId, isAdmin, pageNo, pageSize, releaseDt, deadlineDt, demandId, inquiryTheme, UserId, status, payType, statusType);
    }

    /**
     * @return
     * @author dongxuecheng
     * @Date 2017/1/19 8:53
     * @description填写政策申请的时候导入的销售记录
     */

    public String getPage(Integer currUserId, int pageNo, int pageSize, String inquiryTheme, String statusType) {
        Pagination p = sSoldNoteDao.getPage(currUserId, pageNo, pageSize, inquiryTheme, statusType);

        return convertRepairQuoteList(p);
    }

    /**
     * @return 转换为优选界面需要的json
     * @author ldong
     * @Date 2017/1/10 11:02
     */

    public String convertRepairQuoteList(Pagination qListPage) {
        List<SSoldNote> qList = (List<SSoldNote>) qListPage.getList();
        StringBuffer json = new StringBuffer();
        java.text.NumberFormat nf = java.text.NumberFormat.getInstance();
        nf.setGroupingUsed(false);
        json.append("{\"SSoldNotes\":[");
        if (qList != null && qList.size() > 0) {
            for (SSoldNote sq : qList) {
                json.append("{\"wareType\":\"").append(sq.getWareType()).append("\",");
                json.append("\"soldNoteId\":\"").append(nf.format(sq.getSoldNoteId())).append("\",");
                json.append("\"amount\":\"").append(sq.getAmount()).append("\",");
                json.append("\"charger\":\"").append(sq.getCharger()).append("\",");//是否被优选
                json.append("\"salesArea\":\"").append(sq.getAddrProvince() + sq.getAddrCity()).append("\",");
                json.append("\"dealDt\":\"").append(sq.getDealDt()).append("\",");
                json.append("\"contact\":\"").append(sq.getContact()).append("\",");
                json.append("\"mobile\":\"").append(sq.getMobile()).append("\"},");
            }
            json.deleteCharAt(json.length() - 1);//删除最后的逗号
        }
        json.append("],").append("\"count\":\"").append(qListPage.getTotalCount()).append("\",");
        json.append("\"pageNo\":\"").append(qListPage.getPageNo()).append("\",");
        json.append("\"totalPage\":\"").append(qListPage.getTotalPage()).append("\"");
        json.append("}");
        return json.toString();
    }

    /**
     * @Author dxc
     * 更新需求信息的status
     * @Date 2016/12/27 0027 16:03
     */
    public void update(Integer demandId) {
        SSoldNote sSoldNote = sSoldNoteDao.findById(demandId);
        Content content = sSoldNote.getContent();
        // 执行监听器
        List<Map<String, Object>> mapList = preChange(content);

        if (sSoldNote.getStatus() != null) {
            switch (Integer.parseInt(sSoldNote.getStatus())) {
                case 1:
                    sSoldNote.setStatus("2");
                    //已发布时将content状态改为已审核
                    content.setStatus(ContentCheck.CHECKED);
                    break;
                case 2:
                    sSoldNote.setStatus("1");
                    //其他未审核状态
                    content.setStatus(ContentCheck.CONTRIBUTE);
                    break;
            }
        }

        // 更新content表
        Updater<Content> updatercontnet = new Updater<Content>(content);
        content = contentDao.updateByUpdater(updatercontnet);
        afterChange(content, mapList);


        Updater<SSoldNote> updater = new Updater<SSoldNote>(sSoldNote);
        sSoldNoteDao.updateByUpdater(updater);

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
     * @Author dxc
     * 删除销售记录
     * @Date 2016/12/29 0029 10:55
     */
    public SSoldNote delete(Integer id) {
        SSoldNote bean = sSoldNoteDao.findById(id);//获取能力实体类
        //表中还没有content暂时注释掉
        Content contentBean = deleteContentById(bean.getContent().getContentId());
        bean = sSoldNoteDao.delete(bean);// 执行删除
        afterDelete(contentBean);
        return bean;
    }

    /**
     * @return
     * @author dongxuecheng
     * @Date 2017/1/14 11:17
     * @description
     */
    public SSoldNote findById(Integer id) {
        SSoldNote bean = sSoldNoteDao.findById(id);
        return bean;
    }

    public void update(SSoldNote sSoldNote, Content c, ContentExt ext, ContentTxt t, String[] attachmentPaths,
                       String[] attachmentNames, String[] attachmentFilenames, String[] picPaths, String[] picDescs,
                       Integer channelId, Integer typeId, CmsUser user, Short charge, boolean b,
                       String demandObjListJsonString) {
        //更新cms表内容
        Content nc = contentMng.update(c, ext, t, null, null, null, null, attachmentPaths, attachmentNames, attachmentFilenames, picPaths,
                picDescs, null, channelId, typeId, null, charge, null, user, b);
        //更新需求内容
//		List<SDemandObj> sDemandObjList =convertJStringToList(demandObjListJsonString);
//		sDemand.setDemandObjList(sDemandObjList);
        sSoldNote.setContent(nc);
        Updater<SSoldNote> updater = new Updater<SSoldNote>(sSoldNote);
        sSoldNoteDao.updateByUpdater(updater);
    }

    /**
     * @return
     * @author dongxuecheng
     * @Date 2017/1/14 9:54
     * @description保存Content表功能
     */
    private Content saveContent(Content bean, ContentExt ext, ContentTxt txt,
                                Integer channelId, Integer typeId, String status,
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
        if (status.equals("1")) {
            // 草稿
            bean.setStatus(DRAFT);
        } else if (status.equals("2")) {
            // 已发布
            bean.setStatus(CHECKED);
        }
        // 流程处理
        else if (contribute != null && contribute) {
            bean.setStatus(ContentCheck.CONTRIBUTE);
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
    private SSoldNoteDao sSoldNoteDao;
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