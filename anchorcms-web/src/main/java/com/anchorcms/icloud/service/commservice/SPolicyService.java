package com.anchorcms.icloud.service.commservice;

import com.anchorcms.cms.model.main.Content;
import com.anchorcms.cms.model.main.ContentExt;
import com.anchorcms.cms.model.main.ContentTxt;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.model.commservice.SAmplePolicy;

/**
 * @Author lisong
 * @Date 2017/1/16 11:20
 *惠补政策添加
 */
public interface SPolicyService {
    public Content supplychain_save_hbzc(SAmplePolicy sAmplePolicy, Content c, ContentExt ext, ContentTxt t,
                                         Integer channelId, Integer typeId, CmsUser user, boolean b, String[] attachmentPaths, String[] attachmentNames, String[] attachmentFilenames, String[] picPaths, String[] picDescs);
}
