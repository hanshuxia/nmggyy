package com.anchorcms.icloud.service.cloudcenter.impl;

import com.anchorcms.cms.dao.main.ContentDao;
import com.anchorcms.cms.model.main.*;
import com.anchorcms.cms.service.assist.CmsCommentMng;
import com.anchorcms.cms.service.main.*;
import com.anchorcms.common.hibernate.Updater;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsSite;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.dao.cloudcenter.CloudCenterDao;
import com.anchorcms.icloud.model.cloudcenter.SIcloudCenter;
import com.anchorcms.icloud.service.cloudcenter.CloudCenterService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.anchorcms.cms.model.main.ContentCheck.DRAFT;

/**
 * @anther 李利民
 * @descript
 * @data 2017/1/411:05
 */
@Service
@Transactional
public class CloudCenterServiceImpl implements CloudCenterService {
    public Content save(SIcloudCenter sd, Content c, ContentExt ext, ContentTxt t, String[] attachmentPaths, String[] attachmentNames, String[] attachmentFilenames, String[] picPaths, String[] picDescs, Integer channelId, Integer typeId, CmsUser user, Short charge, boolean forMember) {
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
        sd.setContent(c);
        dao.save(sd);
        afterSave(c);
        return c;
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

    /**
     * @author wanjinyou
     * @descript 云计算机中心列表展示
     * @date 2017/1/7 10:31
     */
    public Pagination getPageForCenter(Integer channelId, Integer siteId, Integer modelId, Integer memberId, int pageNo, int pageSize, Integer centerId, String centerName, String addrCity, Date startDate, Date endDate) {
        return dao.getPageBySelf(null, memberId, false, false, Content.ContentStatus.all, null, siteId, modelId, channelId, 0, pageNo, pageSize,
                centerId, centerName, addrCity, startDate, endDate);
    }

    /**
     * @author wanjinyou
     * @descript 云计算机中心--编辑
     * @date 2017/1/7 13:19
     */
    public SIcloudCenter findById(Integer id) {
        return dao.findById(id);
    }

    /**
     * @author wanjinyou
     * @descript 云计算机中心--编辑更新
     * @date 2017/1/7 13:50
     */
    public SIcloudCenter update(Integer id, SIcloudCenter sd, String detailDesc, Integer channelId, CmsUser user, Short charge, boolean b, String[] picPaths, String[] picDescs) {
            SIcloudCenter icloudCenter = findById(id);
            icloudCenter.setCenterName(sd.getCenterName());
            icloudCenter.setContact(sd.getContact());
            icloudCenter.setMobile(sd.getMobile());
            icloudCenter.setEmail(sd.getEmail());
            icloudCenter.setAddrProvince(sd.getAddrProvince());
            icloudCenter.setAddrCity(sd.getAddrCity());
            icloudCenter.setAddrCounty(sd.getAddrCounty());
            icloudCenter.setAddrStreet(sd.getAddrStreet());
            icloudCenter.setSelectAddress(sd.getSelectAddress());
            icloudCenter.setAcreage(sd.getAcreage());

            Content c = icloudCenter.getContent();
            ContentExt ext = c.getContentExt();
            //富文本
            ContentTxt txt = c.getContentTxt();
            txt.setTxt(detailDesc);
        //图片
        c.getPictures().clear();//先清空图片
        if (picPaths != null && picPaths.length > 0) {
            for (int i = 0, len = picPaths.length; i < len; i++) {
                if (!StringUtils.isBlank(picPaths[i])) {
                    c.addToPictures(picPaths[i], picDescs[i]);
                }
            }
        }

           // 执行监听器
           List<Map<String, Object>> mapList = preChange(c);
           //更新content
           c = updateContent(c, ext, txt,channelId, charge, user, b);

           dao.update(icloudCenter);
           // 执行监听器
           afterChange(c, mapList);
           return null;
    }

    /**
     * @Author wanjinyou
     * @Date 2017/1/7 14:04
     * @Desc 更新content
     **/
    public Content updateContent(Content bean, ContentExt ext, ContentTxt txt, Integer channelId, Short charge, CmsUser user, boolean b) {

        // 更新主表
        Updater<Content> updater = new Updater<Content>(bean);
        bean = contentDao.updateByUpdater(updater);

        // 更新扩展表
        contentExtMng.update(ext);
        // 更新文本表
        contentTxtMng.update(txt, bean);

        return bean;
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
     * @param id
     * @author wanjinyou
     * @descript 云计算机中心--删除
     * @date 2017/1/7 16:30
     */
    public SIcloudCenter deleteById(Integer id) {
        SIcloudCenter bean = findById(id);
        Content contentBean = deleteContentById(bean.getContent().getContentId());
        dao.deleteById(id);
        afterDelete(contentBean);
        return bean;
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
    /**
     * @author jsz
     * @date 2017/1/9 13:47
     * @desc 自定义标签——能力列表获取
     **/
    public List<SIcloudCenter> getList(Integer count, Integer orderBy,Integer areaType) {
        return dao.getList(count, orderBy,areaType);
    }

    /**
     * @Author 闫浩芮
     * 批量删除
     * @Date 2017/2/20 0020 16:03
     */
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
                deleteById(Integer.parseInt(idarr[i]));
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
    private ContentTagMng contentTagMng;
    @Resource
    private CmsCommentMng cmsCommentMng;
    @Resource
    private ContentDao contentDao;
    @Resource
    private CloudCenterDao dao;
}
