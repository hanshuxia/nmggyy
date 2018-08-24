package com.anchorcms.icloud.dao.commservice;

import com.anchorcms.common.hibernate.Updater;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.model.commservice.STask;

import java.util.Date;


public interface SUploadStaskDao {
    /**
     * @Author 闫浩芮
     * 查询需求列表
     * @Date 2016/12/23 0023 15:50
     */
    public Pagination getPageBySelf(int pageNo, int pageSize, Date startDate, Date deadlineDt, String taskName, String status,CmsUser user);
    /**
     * @Author 闫浩芮
     * 更新需求信息的status
     * @Date 2016/12/28 0028 9:07
     */

    public STask updateByUpdater(Updater<STask> updater);
    /**
     * @Author 闫浩芮
     * 根据demandId查找
     * @Date 2016/12/28 0028 9:08
     */
    public STask findById(Integer taskId);
}
