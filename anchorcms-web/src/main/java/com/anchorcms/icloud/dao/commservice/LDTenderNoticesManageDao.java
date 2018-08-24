package com.anchorcms.icloud.dao.commservice;

import com.anchorcms.cms.model.main.Content;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.icloud.model.commservice.SBidNotice;
import com.anchorcms.icloud.model.commservice.STenderNotice;
import com.anchorcms.icloud.model.commservice.STenderTrailer;

/**
 * DESCRIPTION:招标公告管理Service
 * Author: DELL
 * Date:2017/1/13.
 */
public interface LDTenderNoticesManageDao {
    public Pagination getTenderNoticesList(String title, Integer typeId, Integer currUserId,
                                          Integer inputUserId, boolean topLevel, boolean recommend,
                                          Content.ContentStatus status, Byte checkStep, Integer siteId,
                                           Integer modelId, Integer channelId, int orderBy, int pageNo,
                                           int pageSize, String state);

/**
 * @author ldong
 * @Date 2017/1/13 16:25
 * @return
 * 招标预告
 */
    void deleteTenderTrailer(STenderTrailer bean);
    void saveOrUpdateTenderTrailer(STenderTrailer bean);
    public STenderTrailer getTenderTrailerById(int id);

    /**
     * @author ldong
     * @Date 2017/1/13 16:25
     * @return
     * 招标公告
     */
    void deleteTenderNotice(STenderNotice bean);
    void saveOrUpdateTenderNotice(STenderNotice bean);
    public STenderNotice getSTenderNoticeById(int id);

    /**
     * @author ldong
     * @Date 2017/1/13 16:25
     * @return
     * 中标公告
     */
    void deleteBidNotice(SBidNotice bean);
    void saveOrUpdateBidNotice(SBidNotice bean);
    public SBidNotice getSBidNoticeById(int id);
}

