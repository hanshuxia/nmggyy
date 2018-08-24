package com.anchorcms.icloud.dao.common;

import com.anchorcms.icloud.model.common.PubCode;

import java.util.List;

/**
 * Copyright @ 2017 Shandong jxdinfo software co,ltd.
 * All right reserved.
 * Created by Gao Jiangning
 *
 * @Date 2017/1/13 0013
 * @Desc 公共字典表Dao接口
 */
public interface PubCodeDao {

    /**
     * 获得父节点下子节点List的方法
     *
     * @param parentId
     * @return
     */
    List<PubCode> getCodeListByParent(Integer parentId);

    /**
     * 根据typeCode获取所有第一父节点List的方法
     * @param typeCode
     * @return
     */
    List<PubCode> getGrandCodeList(String typeCode);

    /**
     * 用typeCode 和key 获取唯一pubcode对象
     */
    PubCode findUniqueCode(String typeCode, String parentKey);

    PubCode findById(Integer id);

    /**
     * @author dongxuecheng
     * @Date 2017/2/10 15:57
     * @return
     * @description根据条件查出对应的筛选条件
     */

    public List<PubCode> getMenu(Integer count,Integer level,String orderBy,String menuType);
}
