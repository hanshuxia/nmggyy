package com.anchorcms.icloud.service.synergy;

import com.anchorcms.common.page.Pagination;

import java.util.Date;

public interface DemandService {

    /**
     *@Author maocheng
     * 获得文章分页。协同制造-管理员-需求管理列表
     *@Date 2016/12/23 0023 16:14
     */
    public Pagination getPageForAll(Integer channelId, Integer siteId, Integer modelId, Integer memberId, int pageNo, int pageSize,
                                    String demandId, String inquiryTheme, String contact, Date startDate, Date endDate, String classifyType,
                                       String companyId, String statusType, String mobile, Date createDt, Date deadlineDt);
    /**
     * @Author jiangshuzhong
     * @Email
     * @Date 2016/12/29
     * @Desc 能力审批
     */
    void rejectDemand(Integer id,String backReason);
    //通过需求
    void passDemand(Integer id);
    /**
     * @Author 闫浩芮
     * 置为通过
     * @Date 2017/2/18 0018 11:07
     */
    public void passMany(String demandIdsStr);
    /**
     * @Author 闫浩芮
     * 置为驳回
     * @Date 2017/2/18 0018 11:07
     */
    public void rejectMany(String demandIdsStr,String backReason);
}
