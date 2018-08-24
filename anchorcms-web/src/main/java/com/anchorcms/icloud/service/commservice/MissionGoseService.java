package com.anchorcms.icloud.service.commservice;

import com.anchorcms.icloud.model.commservice.STask;
import com.anchorcms.icloud.model.commservice.STaskModel;

import java.util.List;

/**
 * @anther 李利民
 * @descript
 * @data 2017/1/1216:42
 */
public interface MissionGoseService {
    void save(STask sd, String sTaskModels);

    STask getByTaskId(Integer taskId);

    List<STaskModel> getModelById(Integer taskId);

    /**
     * lilimin 获取用户的数据
     * @param taskId
     * @param userId
     * @return
     */
    List getModelByIdUserId(Integer taskId, Integer userId);
}

