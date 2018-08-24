package com.anchorcms.icloud.service.commservice;

import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsUser;

import java.util.Date;

public interface ManagerUploadTaskService {
    /**
     * @Author 闫浩芮
     * 分页查询待发布的任务
     * @Date 2017/01/12
     */
    public Pagination getPageForMember(int pageNo, int pageSize, Date startDate, Date deadlineDt, String taskName, String status, CmsUser user);

    /**
     * @Authwr lilimin
     * 分页查询所有的已经上报的信息
     * @param cpn
     * @param i
     * @param startDt
     * @param deadlineDt
     * @param taskName
     * @param status
     * @param user
     * @return
     */
    Pagination getPageForTaskFill(int cpn, int i, Date startDt, Date deadlineDt, String taskName, String status, CmsUser user);
    Pagination getPageForTaskFill2(int cpn, int i, Date startDt, Date deadlineDt, String taskName, String status, CmsUser user);
}
