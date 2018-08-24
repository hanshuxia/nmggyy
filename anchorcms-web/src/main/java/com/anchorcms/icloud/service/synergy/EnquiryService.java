package com.anchorcms.icloud.service.synergy;


import com.anchorcms.cms.model.main.Content;
import com.anchorcms.cms.model.main.ContentExt;
import com.anchorcms.cms.model.main.ContentTxt;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.model.synergy.SAbility;
import com.anchorcms.icloud.model.synergy.SAbilityInquiry;

import java.util.List;

/**
 * @Author wanjinyou
 * @Email netuser.orz@icloud.com
 * @Date 2016/12/26
 * @Desc 我要询价
 */
public interface EnquiryService {

    /**
     * @Author wanjinyou
     * @Email netuser.orz@icloud.com
     * @Date 2016/12/26
     * @Desc 我要询价页面
     */
     SAbility byAbilityId(Integer id);
    /**
     * @Author wanjinyou
     * @Email netuser.orz@icloud.com
     * @Date 2016/12/28
     * @Desc 询价页面保存
     */

    public Content save(Integer abilityId,SAbilityInquiry enquiry, Content c, ContentExt ext, ContentTxt t,
                        String[] attachmentPaths, String[] attachmentNames,
                        String[] attachmentFilenames, String[] picPaths,
                        String[] picDescs, Integer channelId, Integer typeId, CmsUser user, Short charge, boolean b);

}

