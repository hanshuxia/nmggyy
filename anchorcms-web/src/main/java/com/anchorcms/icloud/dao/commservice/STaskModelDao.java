package com.anchorcms.icloud.dao.commservice;

import com.anchorcms.icloud.model.commservice.STaskModel;

import java.util.List;

/**
 * @anther 李利民
 * @descript
 * @data 2017/1/1217:50
 */
public interface STaskModelDao {
    STaskModel save(STaskModel stakeModel);

    List<STaskModel> getById(Integer taskId);

    void delete(STaskModel Model);

    /**
     * 或得当前用户填写的数据
     * @param taskId
     * @param userId
     * @return
     */
    List getByIdUserId(Integer taskId, Integer userId);
}
