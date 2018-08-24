package com.anchorcms.icloud.dao.commservice;

import com.anchorcms.cms.model.main.Content;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.icloud.model.commservice.SAmplePolicy;


import java.util.Date;



/**
 * DESCRIPTION:招标公告管理Service
 * Author: DELL
 * Date:2017/1/13.
 */
public interface LDAmplepolicyManageDao {

    public Pagination getAmplePolicyList(String tradeType,Date startDT,Date endDT,String title,
                                         Integer typeId, Integer currUserId, Integer inputUserId,
                                         boolean topLevel, boolean recommend, Content.ContentStatus status,
                                         Byte checkStep, Integer siteId, Integer modelId, Integer channelId,
                                         int orderBy, int pageNo, int pageSize, String state);

    SAmplePolicy getAmplePolicyById(Integer itemId);

    void updateAmplePolicyByBean(SAmplePolicy bean);

    SAmplePolicy deleteByBean(SAmplePolicy bean);

}

