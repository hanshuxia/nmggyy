package com.anchorcms.icloud.dao.synergy;

import com.anchorcms.cms.model.main.Content;
import com.anchorcms.common.page.Pagination;

import java.util.Date;
import java.util.List;


public interface AskHelpDao {
    /**
     * @Author 闫浩芮
     * 查询需求列表
     * @Date 2016/12/23 0023 15:50
     */
    Pagination getPageBySelf(Object o, boolean b, boolean b1, Content.ContentStatus all, Object o1, Integer siteId,
                             Integer channelId, int pageNo, int pageSize, Date startDate, Date endDate, String author,
                             String title,Byte status);
    /**
     * @Author 闫浩芮
     * 根据Id查询信息
     * @Date 2017/2/13 0013 16:33
     */
    Content findById(Integer contentId);
    /**
     * @Author 闫浩芮
     * 置为通过
     * @Date 2017/2/17 0017 17:25
     */
    public int passMany(String demandIdsStr);
    /**
     * @Author 闫浩芮
     * 批量删除
     * @Date 2017/2/17 0017 18:40
     */
    public int deleteMany(String demandIdsStr);

    List<Content> byContentIds(String ids);
}
