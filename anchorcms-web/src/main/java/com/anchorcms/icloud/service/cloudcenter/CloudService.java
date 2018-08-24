package com.anchorcms.icloud.service.cloudcenter;

import com.anchorcms.cms.model.main.Content;
import com.anchorcms.cms.model.main.ContentExt;
import com.anchorcms.cms.model.main.ContentTxt;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.model.cloudcenter.SIcloudDemand;


/**
 * @anther 李利民
 * @descript
 * @data 2017/1/313:05
 */
public interface CloudService {
    /**
     * @anther 李利民
     * @descript 云发布新增保存
     * @data 2017/1/313:05
     * @param sd
     * @param c
     * @param ext
     * @param t
     * @param attachmentPaths
     * @param attachmentNames
     * @param attachmentFilenames
     * @param picPaths
     * @param picDescs
     * @param channelId
     * @param typeId
     * @param user
     * @param charge
     * @param b
     * @return
     */
    Content save(SIcloudDemand sd, Content c, ContentExt ext, ContentTxt t, String[] attachmentPaths, String[] attachmentNames, String[] attachmentFilenames, String[] picPaths, String[] picDescs, Integer channelId, Integer typeId, CmsUser user, Short charge, boolean b);
}
