package com.anchorcms.icloud.service.common;

import com.anchorcms.cms.model.main.Content;
import com.anchorcms.cms.model.main.ContentExt;
import com.anchorcms.cms.model.main.ContentTxt;
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
public interface CfcaService {
    /**
     * @Author zhouyq
     * @Date 2017/03/30
     * @param
     * @return
     * @Desc 获取电子签章管理列表分页后信息
     */
    public Pagination getList(String companyName, String status, Integer pageNo, Integer pageSize);

    /**
     * @Author zhouyq
     * @Date 2017/03/31
     * @param
     * @return
     * @Desc 获取电子签章管理明细信息
     */
    public SCfcaApply getCfcaEntity(String id);


    List getApplyList(CmsUser user);

    Pagination getApplyPage(CmsUser user, int cpn, int i);

    void editState(String state, int id);

    void savePayResult(int id, String remark, String orderNo, String accountNo, String filPath[],String attachmentNames[],Content c);

    Content save(SCfcaPay sd, Content c, ContentExt ext, ContentTxt t, String[] attachmentPaths, String[] attachmentNames, String channelId, Integer typeId, CmsUser user, Short charge, boolean b, Integer id);

    /**
     * @author zhouyq
     * @Date 2017/4/5
     * @return 判断是否可以进行签章
     */
    public SCfcaApply getSignFlag(String companyId);

    /**
     * @author ldong
     * @Date 2017/3/31
     * @return 更改申请数据的状态    通过
     */
    void setPass(int applyId1, String signNo, String signPwd);
    /**
     * @author machao
     * @Date 2017/3/31 16:41
     * @return
     * 签章申请保存
     */
    public SCfcaApply cfcaSealSave(SCfcaApply sCfcaApply);
    public Content cfcaSealEdit(SCfcaApply sCfcaApply, Content c, ContentExt ext, ContentTxt t,
                                    Integer channelId, Integer typeId, CmsUser user, boolean b, String[] attachmentPaths, String[] attachmentNames, String[] attachmentFilenames, String[] picPaths, String[] picDescs);


    public void modifyCfcaStateById(String applyId, String state, Date releaseDt, String backReason);
}
