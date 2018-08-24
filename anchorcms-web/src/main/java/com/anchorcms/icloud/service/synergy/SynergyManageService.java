package com.anchorcms.icloud.service.synergy;

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
public interface SynergyManageService {
    /**
     * 获得列表分页。供会员中心使用。
     *
     * @param title
     *            能力标题
     * @param siteId
     *            站点ID
     * @param memberId
     *            会员ID
     * @param pageNo
     *            页码
     * @param pageSize
     *            每页大小
     * @return 文章分页对象
     */

    public Pagination getPageForMember(String title, Integer channelId,
                                       Integer siteId, Integer modelId, Integer memberId,
                                       int pageNo, int pageSize, String abilityName, String abilityCode,Date createDt,String statusType);

    /**
     * @Author jiangshuzhong
     * @Email
     * @Date 2016/12/28
     * @Desc 能力审批
     */
    void rejectAdility(Integer id,String backReason);
    void passAdility(Integer id);

    /**
     * @Author yuantao
     * @Email
     * @Date 2016/12/28
     * @Desc 能力批量审批
     */
    void rejectAdilitys(String ids,String backReason);
    void passAdilitys(String ids);
    public SAbility byAbilityId(Integer abilityId);

    /**
    *@Author 苏和 【562763562@qq.com】
    *@Date 2017/5/2 11:18
    *@Return
     * 协同制造卖方订单列表
    */
    public Pagination getSyneryListPage(Integer siteId, CmsUser user, int pageNo, int pageSize, Date startDate, String state, String paramu);

    SPSettle findOrderById(String orderId);

    public void modifyWuliuStateById(String txlogisticId, String logisticsOrderState);

}
