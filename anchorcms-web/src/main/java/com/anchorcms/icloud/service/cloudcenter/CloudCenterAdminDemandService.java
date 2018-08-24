package com.anchorcms.icloud.service.cloudcenter;

import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsUser;

import java.util.Date;

public interface CloudCenterAdminDemandService {

    /**
     * @param demandId 询价编号
     * @param demandName 需求名称
     * @param startDate 发布开始时间
     * @param endDate 发布结束时间
     * @param serverType 服务器分类
     * @param status 标签状态
     * @return
     * @author maocheng
     * @description 云资源交易中心-管理员--云需求管理界面列表
     */
    public Pagination getPageForMember(Integer channelId, Integer siteId, Integer modelId, Integer memberId, int pageNo,
                                       int pageSize, String demandId, String demandName,  Date startDate, Date endDate,
                                       String serverType,String status);

    /**
     * @author lilimin
     * @descript 更新状态
     * @param id
     * @param states
     * @param channelId
     * @param user
     * @param charge
     * @param b
     */
    void updateState(Integer id, String states, Integer channelId, CmsUser user, Short charge, boolean b,String backReason);
    /**
     * 置为通过
     * @Author 闫浩芮
     * @param demandIdsStr  操作对象的Id字符串
     * @Date 2017/2/18 0018 11:07
     */
    public void passMany(String demandIdsStr);
    /**
     * 置为驳回
     * @param demandIdsStr  操作对象的Id字符串
     * @Author 闫浩芮
     * @Date 2017/2/18 0018 11:07
     */
    public void rejectMany(String demandIdsStr,String backReason);
}
