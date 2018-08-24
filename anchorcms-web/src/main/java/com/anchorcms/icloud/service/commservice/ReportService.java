package com.anchorcms.icloud.service.commservice;

import com.anchorcms.icloud.model.commservice.STaskFill;

/**
 * @anther 李利民
 * @descript
 * @data 2017/1/1314:17
 */
public interface ReportService {
    void save(Integer models, STaskFill sd, String demandObjListJsonString, Integer userId);
}
