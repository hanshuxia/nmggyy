package com.anchorcms.icloud.dao.cloudcenter;

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
public interface CloudCenterDao {
    SIcloudCenter save(SIcloudCenter bean);

    /**
     * @author wanjinyou
     * @descript 云计算机中心列表展示
     * @date 2017/1/7 10:31
     */
    Pagination getPageBySelf(Object o, Integer memberId, boolean b, boolean b1, Content.ContentStatus all, Object o1, Integer siteId, Integer modelId, Integer channelId, int i, int pageNo, int pageSize, Integer centerId, String centerName,String addrStreet,Date startDate, Date endDate);

    /**
     * @author wanjinyou
     * @descript 云计算机中心--编辑
     * @date 2017/1/7 13:19
     */
    public SIcloudCenter findById(Integer id);

    /**
     * @author wanjinyou
     * @descript 云计算机中心--编辑更新
     * @date 2017/1/7 14:13
     */
    public SIcloudCenter update(SIcloudCenter icloudCenter);

    /**
     * @author wanjinyou
     * @descript 云计算机中心--删除
     * @date 2017/1/7 16:48
     */
    public SIcloudCenter deleteById(Integer id);
    /**
     *@Author jsz
     *@Date 2017/1/19
     *@desc 云资源交易中心标签查询
     */
    public List<SIcloudCenter> getList(Integer count, Integer orderBy,Integer areaType);

    /**
     * @Author 闫浩芮
     * 批量删除
     * @Date 2017/2/20 0020 10:35
     */
    public int deleteMany(String demandIdsStr);
}
