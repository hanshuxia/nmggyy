package com.anchorcms.icloud.service.commservice;

import com.anchorcms.cms.model.main.Content;
import com.anchorcms.cms.model.main.ContentExt;
import com.anchorcms.cms.model.main.ContentTxt;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.model.commservice.SBidNotice;
import com.anchorcms.icloud.model.commservice.STenderNotice;
import com.anchorcms.icloud.model.commservice.STenderTrailer;
import org.omg.CORBA.Object;

/**
 * DESCRIPTION:招标公告管理Service
 * Author: DELL
 * Date:2017/1/13.
 */
public interface LDTenderNoticesManageService {
    /**
     * @return 获取招标公告管理列表
     * @author ldong
     * @Date 2017/1/13 10:30
     */
    Pagination getTenderNoticesList(String title, Integer channelId,
                                    Integer siteId, Integer modelId, Integer memberId,
                                    int pageNo, int pageSize, String state);

    void deleteWithTenderManage(int id, String state);


    Object getObjforTender(int id, String state);

   /* Content updateTenderTrail(int contentId, STenderTrailer stt, HttpServletRequest request, Content c, ContentExt ext, ContentTxt t, String nextUrl, Integer modelId, Integer channelId, String textarea1, CmsUser user, Short charge, Integer typeId, boolean forMember, HttpServletResponse response, ModelMap model);

    public Content updateTenderNotice(int contentId, STenderNotice stn, HttpServletRequest request, Content c, ContentExt ext, ContentTxt t, String nextUrl, Integer modelId,
                                      Integer channelId, String textarea1, CmsUser user, Short charge, Integer typeId, boolean forMember,
                                      HttpServletResponse response, ModelMap model);

    public Content updateBidNotice(int contentId, SBidNotice ability, Content c, ContentExt ext, ContentTxt t,
                                   String[] attachmentPaths, String[] attachmentNames,
                                   String[] attachmentFilenames, String[] picPaths,
                                   String[] picDescs, Integer channelId, Integer typeId, CmsUser user, Short charge, boolean b);
*/

    void updateTenderTrail_new(STenderTrailer bean, Content c, ContentExt ext, ContentTxt t,
                               String[] attachmentPaths, String[] attachmentNames,
                               String[] attachmentFilenames, String[] picPaths,
                               String[] picDescs, Integer channelId, Integer typeId, CmsUser user, Short charge, boolean b);

    void updateTenderNotice_new(STenderNotice bean, Content c, ContentExt ext, ContentTxt t,
                                String[] attachmentPaths, String[] attachmentNames,
                                String[] attachmentFilenames, String[] picPaths,
                                String[] picDescs, Integer channelId, Integer typeId, CmsUser user, Short charge, boolean b);

    void updateBidNotice_new(SBidNotice bean, Content c, ContentExt ext, ContentTxt t,
                             String[] attachmentPaths, String[] attachmentNames,
                             String[] attachmentFilenames, String[] picPaths,
                             String[] picDescs, Integer channelId, Integer typeId, CmsUser user, Short charge, boolean b);
}