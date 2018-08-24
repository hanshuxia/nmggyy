package com.anchorcms.icloud.dao.synergy;

import com.anchorcms.cms.model.main.Content;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.icloud.model.cloudcenter.SIcloudCenter;

import java.util.Date;
import java.util.List;

/**
 * @anther 李利民
 * @descript
 * @data 2017/1/315:58
 */
public interface BrainStormDao {

    /**
     * @Author maocheng
     * @Date 2017/2/14
     * @Desc 后台-互动专区--获取实体类
     */
    Content byContentId(Integer id);

    /** @Author wanjinyou
     * @Date 2017/2/9
     * @Desc 后台-互动专区--列表
     */
    Pagination getPageBySelf(Object o, boolean b, boolean b1, Content.ContentStatus all, Object o1, Integer siteId, Integer channelId, int pageNo, int pageSize, String author, String title,Byte status);


}
