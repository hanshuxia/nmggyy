package com.anchorcms.icloud.service.commservice;

import com.anchorcms.common.page.Pagination;
import com.anchorcms.icloud.model.commservice.SStiteManager;
import com.anchorcms.icloud.model.supplychain.SSpareDemand;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/2/21 0021.
 */
public interface ShZwdhCreateService {
//    public Pagination getPageForMember(String status);
    public List manageList(String status);

    public String getJson(String status);
}
