package com.anchorcms.icloud.service.common.impl;

import com.alibaba.fastjson.JSON;
import com.anchorcms.common.hibernate.Finder;
import com.anchorcms.icloud.dao.common.PubCodeDao;
import com.anchorcms.icloud.model.common.PubCode;
import com.anchorcms.icloud.service.common.PubCodeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Copyright @ 2017 Shandong jxdinfo software co,ltd.
 * All right reserved.
 * Created by Gao Jiangning
 *
 * @Date 2017/1/13 0013
 * @Desc
 */
@Service
public class PubCodeServiceImpl implements PubCodeService {

    /**
     * 获得父节点下子节点Json的方法
     *
     * @param parentKey
     * @return
     */
    public String getCatagoryJson(String typeCode,String parentKey) {
        PubCode parent = pubCodeDao.findUniqueCode(typeCode,parentKey);
        List<PubCode> cataList = pubCodeDao.getCodeListByParent(parent.getId());
        return "{\"cataList\":"+JSON.toJSONString(cataList)+"}";
    }

    /**
     * 根据typeCode获取所有第一父节点的方法
     *
     * @param typeCode
     * @return
     */
    public String getGrandCataJson(String typeCode) {
        return "{\"cataList\":"+JSON.toJSONString(pubCodeDao.getGrandCodeList(typeCode))+"}";
    }

    /**
     * 用typeCode 和key 获取唯一pubcode对象
     *
     * @param typeCode
     * @param parentKey
     */
    public PubCode findUniqueCode(String typeCode, String parentKey) {
        return pubCodeDao.findUniqueCode(typeCode,parentKey);
    }

    public PubCode findById(Integer id) {
        return pubCodeDao.findById(id);
    }

    /**
     * @author dongxuecheng
     * @Date 2017/2/10 15:56
     * @return
     * @description根据条件查出对应的筛选条件
     */

    public List<PubCode> getMenu(Integer count,Integer level,String orderBy,String menuType){
       return pubCodeDao.getMenu(count,level,orderBy,menuType);
    }

    @Resource
    private PubCodeDao pubCodeDao;
}
