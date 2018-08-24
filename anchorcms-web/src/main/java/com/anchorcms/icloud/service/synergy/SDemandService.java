package com.anchorcms.icloud.service.synergy;

import com.anchorcms.common.page.Pagination;
import com.anchorcms.icloud.model.synergy.SCompany;
import com.anchorcms.icloud.model.synergy.SDemand;
import com.anchorcms.icloud.model.synergy.SDemandObj;

import java.util.Date;
import java.util.List;

public interface SDemandService {
    /**
     * @Author Yhr
     * 查询需求列表
     * @Date 2016/12/23 0023 15:50
     */
    public Pagination getPageForMember(Integer canshu, Integer siteId, Integer modelId, Integer UserId,
                                       Integer memberId, int pageNo, int pageSize, Date releaseDt,
                                       Date deadlineDt, Integer demandId, String inquiryTheme,
                                       String selectedStatus,String payType,String statusType);
    public Pagination getPageForMember2(Integer canshu, Integer siteId, Integer modelId, Integer UserId,
                                       Integer memberId, int pageNo, int pageSize, Date releaseDt,
                                       Date deadlineDt, Integer demandId, String inquiryTheme,
                                       String selectedStatus,String payType,String statusType);
    /**
     * @Author jiangshuzhong
     * @Email netuser.orz@icloud.com
     * @Date 2016/12/28
     * @Desc 需求详情
     */
    public SDemand byDemandId(Integer demandId);
    public SDemandObj byDemandObjId(Integer demandId);
    /**
     * @Author 闫浩芮
     * 更新需求信息的status
     * @Date 2016/12/28 0028 9:10
     */
    void update(Integer demandId,String a);
    /**
     * @Author 闫浩芮
     * 删除需求信息
     * @Date 2016/12/29 0029 10:56
     */
    public SDemand delete(Integer demandId);
    /**
     *
     * @param id
     * @return
     */
    SCompany byCompanyId(String id);

    public List<SDemand> getList(Integer count, Integer orderBy, Integer listType,Integer overDate,String demandType);

    /**
     * @author liuyang
     * @Date 2017/6/2 13:45
     * @return
     */
    public Pagination getDevicePage(Integer canshu, Integer siteId, Integer modelId, Integer UserId,
                                    Integer memberId, int pageNo, int pageSize, Date releaseDt,
                                    Date deadlineDt, Integer demandId, String inquiryTheme,
                                    String selectedStatus,String payType,String statusType);
}
