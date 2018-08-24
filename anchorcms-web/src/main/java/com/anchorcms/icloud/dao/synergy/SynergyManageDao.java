package com.anchorcms.icloud.dao.synergy;

import com.anchorcms.cms.model.main.Content;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.icloud.model.synergy.SAbility;
import com.anchorcms.icloud.model.synergy.SAbilityInquiry;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.model.payment.SPPay;
import com.anchorcms.icloud.model.payment.SPSettle;


import java.util.Date;

/**
 * @Author 姜舒中
 * @Date 2016/12/23 0023
 * @Desc 协同制造
 */
public interface SynergyManageDao {
    /**
     * 获得自己发布的内容列表
     *
     * @param title
     *            标题。支持模糊搜索，可以为null。
     * @param typeId
     *            内容类型ID。可以为null。
     * @param inputUserId
     *            内容录入员。可以为null。
     * @param topLevel
     *            是否固顶。
     * @param recommend
     *            是否推荐。
     * @param status
     *            状态。
     * @param checkStep
     *            审核步骤。当状态为prepared、passed、rejected时，不能为null。
     * @param siteId
     *            站点ID。可以为null。
     * @param channelId
     *            站点ID。可以为null。
     * @param userId
     *            用户ID
     * @param orderBy
     *            排序方式
     * @param pageNo
     * @param pageSize
     * @return
     */
    public Pagination getPageBySelf(String title, Integer typeId,
                                    Integer inputUserId, boolean topLevel, boolean recommend,
                                    Content.ContentStatus status, Byte checkStep, Integer siteId,
                                    Integer channelId, Integer userId, int orderBy, int pageNo,
                                    int pageSize, String abilityName, String abilityCode,Date createDt,String statusType);
    /**
     * @Author jiangshuzhong
     * @param id
     * @Email netuser.orz@icloud.com
     * @Date 2016/12/28
     * @Desc 能力审批通过与驳回
     */
    void rejectAdility(Integer id);
    void passAdility(Integer id);
    /**
    *@Author 苏和 【562763562@qq.com】
    *@Date 2017/5/2 11:20
    *@Return
     * 协同制造卖方订单列表
    */
    public Pagination getPage(Integer siteId, CmsUser user, int pageNo, int pageSize, Date startDate, String state, String paramu);

    SPSettle findbean(String id);
    public SAbility byAbilityId(Integer abilityId);
    public void modifyWuliuStateById(String txlogisticId, String logisticsOrderState);
}
