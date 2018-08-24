package com.anchorcms.icloud.dao.supplychain;

import com.anchorcms.common.page.Pagination;

/**
 * Created by DELL on 2016/12/26.
 * 众修需求列表展示dao层
 */
/**
 * @author liuyang
 * @Date 2017/1/4 18:32
 * @return
 */
public interface SRepairResListDao {
    public Pagination getPage(String repairType, String addrProvince,
                              String addrCity, String releaseId, String releaseDt,
                              Integer pageNo, Integer pageSize);
}
