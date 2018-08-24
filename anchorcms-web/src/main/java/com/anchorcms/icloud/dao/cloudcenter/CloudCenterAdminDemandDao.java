package com.anchorcms.icloud.dao.cloudcenter;

import com.anchorcms.cms.model.main.Content;
import com.anchorcms.common.page.Pagination;

import java.util.Date;

public interface CloudCenterAdminDemandDao {

    /**
     * @author maocheng
     * @description 管理员--云需求管理界面列表
     * @return
     */

    public Pagination getPageBySelf(Object o, Integer memberId, boolean b, boolean b1, Content.ContentStatus all,
                                    Object o1, Integer siteId, Integer modelId, Integer channelId, int i, int pageNo,
                                    int pageSize, String demandId, String demandName, Date startDate, Date endDate,
                                    String serverType, String status);
    /**
     * @Author 闫浩芮
     * 置为通过
     * @Date 2017/2/17 0017 17:25
     */
    public int passMany(String demandIdsStr);
    /**
     * @Author 闫浩芮
     * 置为驳回
     * @Date 2017/2/17 0017 18:40
     */
    public int rejectMany(String demandIdsStr,String backReason);
}
