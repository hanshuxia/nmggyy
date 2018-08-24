package com.anchorcms.icloud.service.synergy;

import com.anchorcms.cms.model.main.Content;
import com.anchorcms.cms.model.main.ContentExt;
import com.anchorcms.cms.model.main.ContentTxt;
import com.anchorcms.core.model.CmsUser;

/**
 * @Author 姜舒中
 * @Email netuser.orz@icloud.com
 * @Date 2017/2/10 0010
 * @Desc
 */
public interface SuggestionService {
    /**
     * @Author jsz
     * @Date 2017/2/10
     * @Desc 建议意见--保存
     */
    public Content save(Content c, ContentExt ext, ContentTxt t, Integer channelId, Integer typeId, Short charge, CmsUser user, boolean b);
}
