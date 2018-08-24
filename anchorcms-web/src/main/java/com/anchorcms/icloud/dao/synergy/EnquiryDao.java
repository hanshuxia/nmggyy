package com.anchorcms.icloud.dao.synergy;

import com.anchorcms.cms.model.main.Content;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.icloud.model.synergy.SAbility;
import com.anchorcms.icloud.model.synergy.SAbilityInquiry;
import com.anchorcms.icloud.model.synergy.SDemand;

/**
 * @Author wanjinyou
 * @Email netuser.orz@icloud.com
 * @Date 2016/12/26
 * @Desc 我要询价
 */
public interface EnquiryDao {

    /**
     * @Author wanjinyou
     * @Email netuser.orz@icloud.com
     * @Date 2016/12/26
     * @Desc 我要询价
     */
    public SAbility bySAbilityId(Integer id);
    /**
     * @Author wanjinyou
     * @Email netuser.orz@icloud.com
     * @Date 2016/12/28
     * @Desc 我要询价界面保存
     */
    public SAbilityInquiry save(SAbilityInquiry bean);
}



