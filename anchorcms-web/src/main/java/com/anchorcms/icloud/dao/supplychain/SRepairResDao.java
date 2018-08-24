package com.anchorcms.icloud.dao.supplychain;


import com.anchorcms.common.page.Pagination;
import com.anchorcms.icloud.model.supplychain.SRepairRes;

import java.util.List;
import java.util.Map;

/**
 * Created by 刘鹏 on 2016/12/21.
 */
public interface SRepairResDao {

    public SRepairRes findById(String id);
    public List<SRepairRes> findJplsfById(String id);
    public int update(SRepairRes sRepairRes);

    /**
     * @author machao
     * @Date 2017/1/7 21:30
     * @return
     * 众修资源列表展示
     */
    Pagination getPageBySiteIdZxzyById(Integer[] siteIds, Integer[] typeIds, Boolean titleImg, Boolean recommend, String title, Map<String, String[]> attr, int orderBy, int pageNo, int pageSize, String[] params);


}
