package com.anchorcms.icloud.service.cloudcenter.impl;

import com.anchorcms.cms.dao.main.ContentDao;
import com.anchorcms.cms.model.main.Content;
import com.anchorcms.cms.service.assist.CmsCommentMng;
import com.anchorcms.cms.service.assist.CmsFileMng;
import com.anchorcms.cms.service.main.ContentListener;
import com.anchorcms.cms.service.main.ContentTagMng;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.dao.cloudcenter.CloudApplyDao;
import com.anchorcms.icloud.model.cloudcenter.SIcloudApply;
import com.anchorcms.icloud.service.cloudcenter.FeedbackService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Date;
import java.util.List;

/**
 * Created by ly on 2017/1/7.
 */
@Service
@Transactional
public class FeedbackServiceImpl implements FeedbackService {

    public Pagination getFeedbackPage(Integer siteId, CmsUser user, int pageNo, int pageSize, Date startDate, Date endDate, String applierName, String policyType, String policyName, String policyCode, String state) {
        return dao.getFeedbackPage(siteId, user, pageNo, pageSize, startDate, endDate, applierName, policyType, policyName, policyCode, state);
    }

    public SIcloudApply updateState(Integer id, String state, Integer channelId, CmsUser user, Short charge, boolean b) {
        SIcloudApply apply = dao.findById(id);
        apply.setState(state);
        dao.save(apply);
        return null;
    }

    public SIcloudApply delete(Integer id) {
        SIcloudApply bean = findById(id);//获取软件实体类
        Content contentBean = deleteContentById(bean.getContent().getContentId());
        dao.deleteById(id);
        afterDelete(contentBean);
        return bean;
    }

    public SIcloudApply findById(Integer id) {
        return dao.findById(id);
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
    private CloudApplyDao dao;
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
