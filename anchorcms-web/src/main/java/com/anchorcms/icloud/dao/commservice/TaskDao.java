package com.anchorcms.icloud.dao.commservice;

import com.anchorcms.icloud.model.commservice.STask;

/**
 * @anther 李利民
 * @descript
 * @data 2017/1/1216:46
 */
public interface TaskDao {
    STask save(STask sd);

    STask getByTaskId(Integer taskId);

    STask Update(STask sTask);
}
