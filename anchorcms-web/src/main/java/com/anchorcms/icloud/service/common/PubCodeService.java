package com.anchorcms.icloud.service.common;

import com.anchorcms.icloud.model.common.PubCode;

import java.util.List;

/**
 * Copyright @ 2017 Shandong jxdinfo software co,ltd.
 * All right reserved.
 * Created by Gao Jiangning
 *
 * @Date 2017/1/13 0013
 * @Desc 公共字典表相关业务
 */
public interface PubCodeService {

    /**
     * 获得父节点下子节点Json的方法
     * @param parentKey
     * @return
     */
    String getCatagoryJson(String typeCode,String parentKey);

    /**
     * 根据typeCode获取所有第一父节点的方法
     * @param typeCode
     * @return
     */
    String getGrandCataJson(String typeCode);

    /**
     * 用typeCode 和key 获取唯一pubcode对象
     */
    PubCode findUniqueCode(String typeCode, String parentKey);

    PubCode findById(Integer id);

    /**
     * @author dongxuecheng
     * @Date 2017/2/10 15:53
     * @return
     * @description根据条件查出对应的筛选条件
     */

    public List<PubCode> getMenu(Integer count,Integer level,String orderBy,String menuType);


}
