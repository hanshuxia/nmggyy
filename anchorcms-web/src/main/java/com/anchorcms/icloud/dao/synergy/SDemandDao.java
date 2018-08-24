package com.anchorcms.icloud.dao.synergy;

import com.anchorcms.common.hibernate.Updater;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.icloud.model.synergy.SDemand;
import com.anchorcms.icloud.model.synergy.SDemandObj;

import java.util.Date;
import java.util.List;

/**
 * @Author Yhr
 * 查询需求列表
 * @Date 2016/12/23 0023 15:50
 */
public interface SDemandDao {

    public Pagination getPageBySelfCompany(Integer canshu, Integer siteId, Integer modelId,
                                    Integer memberId, int pageNo, int pageSize, Date releaseDt, Date deadlineDt, Integer demandId, String inquiryTheme,
                                    Integer UserId, String selectedStatus, String payType,String statusType);
    public Pagination getPageBySelfCompany2(Integer canshu, Integer siteId, Integer modelId,
                                           Integer memberId, int pageNo, int pageSize, Date releaseDt, Date deadlineDt, Integer demandId, String inquiryTheme,
                                           Integer UserId, String selectedStatus, String payType,String statusType);

    /**
     * @Author jiangshuzhong
     * @param demandId
     * @Email netuser.orz@icloud.com
     * @Date 2016/12/21
     * @Desc 通过ID获取需求信息
     */
    public SDemand bySDemandId(Integer demandId);
    public SDemandObj byDemandObjId(Integer demandId);

    /**
     * @Author 闫浩芮
     * @Date 2016/12/29 0029 8:51
     */
    public SDemand updateByUpdater(Updater<SDemand> updater);
    /**
     * @Author 闫浩芮
     * 根据demandId查找
     * @Date 2016/12/28 0028 9:08
     */
    public SDemand findById(Integer id);
    /**
     * @Author 闫浩芮
     * 删除需求信息
     * @Date 2016/12/29 0029 11:01
     */
    public SDemand delete(SDemand sDemand);

    /**
     * 手动更新需求状态位
     * @param demandId
     */
    void artUpdateStatusType(Integer demandId, String statusType);

    public List<SDemand> getList(Integer count, Integer orderBy, Integer listType,Integer overDate,String demandType);

    public SDemand update(SDemand demand);
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
    /**
     * @author liuyang
     * @Date 2017/6/2 13:47
     * @return
     */
    public Pagination getPageByDeviceCompany(Integer canshu, Integer siteId, Integer modelId,
                                           Integer memberId, int pageNo, int pageSize, Date releaseDt, Date deadlineDt, Integer demandId, String inquiryTheme,
                                           Integer UserId, String selectedStatus, String payType,String statusType);
}
