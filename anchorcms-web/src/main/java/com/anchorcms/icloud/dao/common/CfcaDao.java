package com.anchorcms.icloud.dao.common;

import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.model.common.SCfcaApply;
import com.anchorcms.icloud.model.common.SCfcaPay;

import java.util.Date;
import java.util.List;


/**
 * @author machao
 * @Date 2017/3/30 14:45
 * @return
 * cfca审核
 */
public interface CfcaDao {
    /**
     * @Author zhouyq
     * @Date 2017/3/30
     * @param
     * @return
     * @Desc 电子签章申请查询
     */
    public Pagination getPage(String companyName, String status, Integer pageNo, Integer pageSize);

    /**
     * @Author zhouyq
     * @Date 2017/3/31
     * @param
     * @return
     * @Desc 电子签章明细信息
     */
    public SCfcaApply getCfcaEntity(String id);

    List<SCfcaApply> getList(CmsUser user);

    Pagination getPage(CmsUser user, int cpn, int i);

    SCfcaApply findById(int id);

    /**
     * @author zhouyq
     * @Date 2017/4/5
     * @return 判断是否可以进行签章
     */
    SCfcaApply getSignFlag(String companyId);

    /**
     * @author ldong
     * @Date 2017/3/31
     * @return 更改申请数据的状态    通过
     */
    SCfcaApply  updateBean(SCfcaApply bean );

    SCfcaPay savePay(SCfcaPay po);
    /**
     * @author machao
     * @Date 2017/3/31 17:02
     * @return
     * 申请保存
     */
    public SCfcaApply insert(SCfcaApply sCfcaApply);

    public void modifyCfcaStateById(String applyId, String state, Date releaseDt, String backReason);
}
