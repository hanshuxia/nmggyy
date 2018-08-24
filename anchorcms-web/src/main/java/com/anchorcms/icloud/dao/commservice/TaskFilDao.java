package com.anchorcms.icloud.dao.commservice;

import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.model.commservice.STaskFill;

import java.util.Date;
import java.util.List;

/**
 * @anther 李利民
 * @descript
 * @data 2017/1/1314:23
 */
public interface TaskFilDao {

    STaskFill save(STaskFill sd);

    STaskFill getTasKFileById(Integer userId, int taskId);

    Pagination getPageByTaskFIle(int pageNo, int i, Date startDt, Date deadlineDt, String taskName, String status,CmsUser user);
    Pagination getPageByTaskFIle2(int pageNo, int i, Date startDt, Date deadlineDt, String taskName, String status,CmsUser user);
    List getByTaskId(Integer taskId);
}
