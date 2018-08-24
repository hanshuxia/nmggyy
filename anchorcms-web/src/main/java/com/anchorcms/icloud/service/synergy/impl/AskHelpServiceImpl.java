package com.anchorcms.icloud.service.synergy.impl;

import com.anchorcms.cms.dao.main.ContentDao;
import com.anchorcms.cms.model.main.Content;
import com.anchorcms.cms.model.main.ContentCheck;
import com.anchorcms.cms.service.assist.CmsCommentMng;
import com.anchorcms.cms.service.assist.CmsConsultMng;
import com.anchorcms.cms.service.assist.CmsFileMng;
import com.anchorcms.cms.service.main.*;
import com.anchorcms.common.hibernate.Updater;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.icloud.dao.synergy.AskHelpDao;
import com.anchorcms.icloud.dao.synergy.InteractAreaDao;
import com.anchorcms.icloud.service.synergy.AskHelpService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Service
@Transactional
public class AskHelpServiceImpl  implements  AskHelpService {
    /**
     * @Author wanjinyou
     * @Date 2017/2/9
     * @Desc 后台-公司公告--列表
     */
    public Pagination getPageForMember(Integer channelId, Integer siteId, Integer modelId, Integer userId, int pageNo,
                                       int pageSize, Date startDate, Date endDate, String author,String title, Byte status) {
        return yhrAskHelpDao.getPageBySelf(null, false, false, Content.ContentStatus.all, null,
                siteId, channelId, pageNo, pageSize, startDate, endDate, author,title, status);
    }

    public Content findById(Integer contentId) {
        return yhrAskHelpDao.findById(contentId);
    }

    /**
     * @Author 闫浩芮
     * 管理员置为通过
     * @Date 2017/2/17 0017 18:39
     */
    public void passMany(String ids) {
        if(',' == (ids.charAt(ids.length()-1))){
            ids = ids.substring(0,ids.length()-1);
        }
        List<Content> sContentList = yhrAskHelpDao.byContentIds(ids);
        Content[] content = new Content[sContentList.size()];
        int i = 0;
        for(Content sa : sContentList){
            // 执行监听器
            List<Map<String, Object>> mapList = preChange(sa);
            sa.setStatus(ContentCheck.CHECKED);
            sa.getContentExt().setReleaseDate(new java.sql.Date(System.currentTimeMillis()));
            // 更新content表
            Updater<Content> updater = new Updater<Content>(sa);
            sa = contentDao.updateByUpdater(updater);
            // 执行监听器
            afterChange(sa, mapList);
            i++;

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
     * @Author 闫浩芮
     * 管理员批量删除问题公告
     * @Date 2017/2/17 0017 18:39
     */
    public void deleteMany(String ids) {
        if(',' == (ids.charAt(ids.length()-1))){
            ids = ids.substring(0,ids.length()-1);
        }
        List<Content> sContentList = yhrAskHelpDao.byContentIds(ids);
        Content[] content = new Content[sContentList.size()];
        int i = 0;
        for(Content sa : sContentList){
            // 执行监听器
            List<Map<String, Object>> mapList = preChange(sa);
            // 执行监听器
            preDelete(sa);
            // 移除tag
            contentTagMng.removeTags(sa.getTags());
            sa.getTags().clear();
            // 删除评论
            cmsCommentMng.deleteByContentId(sa.getContentId());
            sa.clear();
            contentDao.deleteById(sa.getContentId());
            i++;
        }
    }
    private void preDelete(Content content) {
        if (listenerList != null) {
            for (ContentListener listener : listenerList) {
                listener.preDelete(content);
            }
        }
    }

    @Resource
    private AskHelpDao yhrAskHelpDao;
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
    private CmsFileMng fileMng;
    @Resource
    private ContentDao contentDao;
    @Resource
    private InteractAreaDao dao;
    @Resource
    private CmsConsultMng cmsConsultMng;
}
