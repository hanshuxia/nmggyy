package com.anchorcms.icloud.dao.synergy;

import com.anchorcms.cms.model.main.Content;
import com.anchorcms.common.page.Pagination;

import java.util.Date;
import java.util.List;

/**
 * @Author wanjinyou
 * @Date 2017/2/10
 * @Desc 互动专区
 */
public interface InteractAreaDao {

    /** @Author wanjinyou
     * @Date 2017/2/9
     * @Desc 后台-互动专区--列表
     */
    Pagination getPageBySelf(Object o, boolean b, boolean b1, Content.ContentStatus all, Object o1, Integer siteId, Integer channelId, int pageNo, int pageSize, Date startDate, Date endDate, String companyName, String title,Byte status);

    /**
     * @Author wanjinyou
     * @Date 2017/2/9
     * @Desc 后台-互动专区--获取实体类
     */
    Content byContentId(Integer id);

    List<Content> byContentIds(String ids);
}
