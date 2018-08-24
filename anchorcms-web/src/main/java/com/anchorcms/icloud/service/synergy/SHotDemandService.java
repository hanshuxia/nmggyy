package com.anchorcms.icloud.service.synergy;

import com.anchorcms.common.page.Pagination;
import com.anchorcms.icloud.model.synergy.SDemand;

import java.util.Date;
import java.util.List;

public interface SHotDemandService {
    /**
     * @Author 闫浩芮
     * 分页查询需求信息
     * @Date 2016/12/28 0028 9:10
     */
    public Pagination getPageForMember(Integer channelId, Integer siteId, Integer modelId,int pageNo, int pageSize, Date releaseDt,Date deadlineDt, Integer demandId,String inquiryTheme,String status,String recommendedType,
                                       String addrCity,String addrCounty,String addrStreet,String operatorId);
    /**
     * @Author 闫浩芮
     * 更新需求信息的status
     * @Date 2016/12/28 0028 9:10
     */
    void update(Integer demandId);
    /**
     * @Author jiangshuzhong
     * 热门需求自定义标签
     * @Date 2016/1/22
     */
    public List<SDemand> getList(Integer count, Integer orderBy, Integer listType);

    /**
     * @Author 闫浩芮
     * 批量推荐
     * @Date 2017/2/18 0018 11:07
     */
    public void passMany(String demandIdsStr);
    /**
     * @Author 闫浩芮
     * 批量撤销
     * @Date 2017/2/18 0018 11:07
     */
    public void rejectMany(String demandIdsStr);
}