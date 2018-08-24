package com.anchorcms.icloud.dao.synergy;

import com.anchorcms.common.hibernate.Updater;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.icloud.model.synergy.SDemand;

import java.util.Date;
import java.util.List;


public interface HotDemandDao {
    /**
     * @Author 闫浩芮
     * 查询需求列表
     * @Date 2016/12/23 0023 15:50
     */
    public Pagination getPageBySelf(Integer channelId, Integer siteId, Integer modelId,int pageNo, int pageSize, Date releaseDt,Date deadlineDt, Integer demandId,String inquiryTheme,String status,String addrProvince,
                                    String addrCity,String addrCounty,String addrStreet,String operatorId);
    /**
     * @Author 闫浩芮
     * 更新需求信息的status
     * @Date 2016/12/28 0028 9:07
     */

    public SDemand updateByUpdater(Updater<SDemand> updater);
    /**
     * @Author 闫浩芮
     * 根据demandId查找
     * @Date 2016/12/28 0028 9:08
     */
    public SDemand findById(Integer id);

    public List<SDemand> getList(Integer count, Integer orderBy, Integer listType);

    /**
     * @Author 闫浩芮
     * 置为推荐
     * @Date 2017/2/17 0017 17:25
     */
    public int passMany(String demandIdsStr);
    /**
     * @Author 闫浩芮
     * 置为撤销
     * @Date 2017/2/17 0017 18:40
     */
    public int rejectMany(String demandIdsStr);
}
