package com.anchorcms.icloud.service.commservice;

import com.anchorcms.cms.model.main.Content;
import com.anchorcms.cms.model.main.ContentExt;
import com.anchorcms.cms.model.main.ContentTxt;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.model.commservice.SAmplePolicy;
import org.omg.CORBA.Object;

import java.util.Date;

/**
 * DESCRIPTION:招标公告管理Service
 * Author: DELL
 * Date:2017/1/13.
 */
public interface LDAmplePolicyManageService {
    /**
     * @author ldong
     * @Date 2017/1/14 11:23
     * @return
     * 惠补政策管理列表
     */
    Pagination getAmplePolicyList(String TradeType, Date startDT, Date endDT,
                                  String title, Integer channelId,
                                  Integer siteId, Integer modelId, Integer memberId,
                                  int pageNo, int pageSize , String state);

    SAmplePolicy getAmplePolicyById(Integer itemId);

    void updateAmplePolicyById(Integer itemId, String s);

    void deleteAmplePolicyById(Integer itemId);

    void update(SAmplePolicy sp, Content c, ContentExt ext, ContentTxt t, String[] attachmentPaths, String[] attachmentNames, String[] attachmentFilenames, String[] picPaths, String[] picDescs, Integer channelId, Integer typeId, CmsUser user, Short charge, boolean b);
}
