package com.anchorcms.icloud.service.cloudcenter.impl;

import com.anchorcms.cms.model.main.Content;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.dao.cloudcenter.CloudCenterAdminDemandDao;
import com.anchorcms.icloud.dao.cloudcenter.CloudCenterDemandDao;
import com.anchorcms.icloud.dao.cloudcenter.CloudCenterQuoteMangerDao;
import com.anchorcms.icloud.model.cloudcenter.SIcloudDemand;
import com.anchorcms.icloud.model.cloudcenter.SIcloudQuoteManage;
import com.anchorcms.icloud.service.cloudcenter.CloudCenterAdminDemandService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;


@Service
@Transactional
public class CloudCenterAdminDemandServiceImpl implements CloudCenterAdminDemandService {

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
    public Pagination getPageForMember(Integer channelId, Integer siteId, Integer modelId, Integer memberId, int pageNo, int pageSize,
                                       String demandId, String demandName, Date startDate, Date endDate, String serverType, String status) {
        return dao.getPageBySelf(null, memberId, false, false, Content.ContentStatus.all, null, siteId, modelId,
                channelId, 0, pageNo, pageSize, demandId, demandName, startDate, endDate, serverType, status);
    }

    /**
     * @param id
     * @param states
     * @param channelId
     * @param user
     * @param charge
     * @param b
     * @author lilimin
     * @descript 更新状态
     */
    public void updateState(Integer id, String states, Integer channelId, CmsUser user, Short charge, boolean b,String backReason) {
        SIcloudDemand demand = dao2.bySIcloudDemandId(id);
        //当点击撤回的时候吧数据库的状态改为1(草稿)
        if ("5".equals(states)) {
            SIcloudQuoteManage quoteManage = mangerDao.byDemand(demand.getDemandId());
            quoteManage.setStatus("1");
            mangerDao.update(quoteManage);
        }
        //当点击发布时候数据发布更烦数据库的状态
        if ("2".equals(states)) {
            demand.setReleaseDt(new java.sql.Date(System.currentTimeMillis()));
        }
        demand.setStatus(states);
        demand.setBackReason(backReason);
        dao2.update(demand);

    }

    /**
     * @Author 闫浩芮
     * 管理员置为通过需求
     * @Date 2017/2/17 0017 18:39
     */
    public void passMany(String demandIdsStr) {
        if (',' == (demandIdsStr.charAt(demandIdsStr.length() - 1))) {
            demandIdsStr = demandIdsStr.substring(0, demandIdsStr.length() - 1);
        }
        dao.passMany(demandIdsStr);
    }

    /**
     * @Author 闫浩芮
     * 管理员置为驳回需求
     * @Date 2017/2/17 0017 18:39
     */
    public void rejectMany(String demandIdsStr ,String backReason) {
        if (',' == (demandIdsStr.charAt(demandIdsStr.length() - 1))) {
            demandIdsStr = demandIdsStr.substring(0, demandIdsStr.length() - 1);
        }
        dao.rejectMany(demandIdsStr,backReason);
    }

    @Resource
    private CloudCenterAdminDemandDao dao;
    @Resource
    private CloudCenterDemandDao dao2;
    @Resource
    CloudCenterQuoteMangerDao mangerDao;
}
