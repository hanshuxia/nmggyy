package com.anchorcms.icloud.service.commservice;

import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.model.commservice.STask;

import java.util.Date;
import java.util.List;

public interface ManagerReleaseTaskService {
    /**
     * @Author 闫浩芮
     * 分页查询待发布的任务
     * @Date 2017/01/12
     */
    public Pagination getPageForMember(int pageNo, int pageSize, Date startDate, Date deadlineDt, String taskName, String status, CmsUser user);
    /**
     * @Author 闫浩芮
     * 更新任务信息的status
     * @Date 2017/01/13
     */
    void update(Integer taskId);
    /**
     * @Author jiangshuzhong
     * @Email netuser.orz@icloud.com
     * @param taskId 任务id
     * @Date 2016/12/28
     * @Desc 需求详情
     */
    public STask byTaskId(Integer taskId);
    /**
     * @Author 闫浩芮
     * 编辑后保存任务信息
     * @Date 2017/01/04
     */
    void updateSave(STask sTask,
                    String sTaskModels);
    /**
     * @Author 闫浩芮
     * 删除任务信息
     * @Date 2017/1/16 0016 9:07
     */
    STask delete(Integer taskId);

    List getFileTaskByTaskId(Integer taskId);
}