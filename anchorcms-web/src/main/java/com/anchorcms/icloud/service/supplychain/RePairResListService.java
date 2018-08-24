package com.anchorcms.icloud.service.supplychain;

import com.anchorcms.common.page.Pagination;

/**
 * Created by DELL on 2016/12/26.
 * liuy
 * 众修资源展示服务层借口
 */
public interface RePairResListService {
        public Pagination getList(String repairType, String addrProvince,
                                  String addrCity, String releaseId, String releaseDt,
                                  Integer pageNo, Integer pageSize);
}
