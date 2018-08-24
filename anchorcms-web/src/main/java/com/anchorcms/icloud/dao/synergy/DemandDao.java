package com.anchorcms.icloud.dao.synergy;

import com.anchorcms.cms.model.main.Content;
import com.anchorcms.common.page.Pagination;

import java.util.Date;

public interface DemandDao {

    /**
     *@Author maocheng
     * 获得文章分页。协同制造-管理员-需求管理列表
     *@Date 2016/12/23 0023 16:14
     */
    public Pagination getPageByAll(Integer typeId, Integer inputUserId, boolean topLevel, boolean recommend,
                                    Content.ContentStatus status, Byte checkStep, Integer siteId,
                                    Integer channelId, Integer userId, int orderBy, int pageNo, int pageSize,
                                   String demandId, String inquiryTheme, String contact, Date startDate, Date endDate, String classifyType,
                                    String companyId, String statusType, String mobile, Date createDt, Date deadlineDt);

    /**
     * @Author jiangshuzhong
     * @param id
     * @Email netuser.orz@icloud.com
     * @Date 2016/12/29
     * @Desc 需求审批
     */
    void rejectDemand(Integer id);
    void passDemand(Integer id);
}
