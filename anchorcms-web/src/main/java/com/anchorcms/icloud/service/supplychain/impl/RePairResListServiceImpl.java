package com.anchorcms.icloud.service.supplychain.impl;

import com.anchorcms.common.page.Pagination;
import com.anchorcms.icloud.dao.supplychain.SRepairResListDao;
import com.anchorcms.icloud.service.supplychain.RePairResListService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by DELL on 2016/12/26.
 */

/**
 * @author liuyang
 * @Date 2017/1/4 15:26
 * 维修资源展示服务层实现类
 */
    @Service
    @Transactional
    public class RePairResListServiceImpl implements RePairResListService {
        @Resource
        private SRepairResListDao dao;

        public Pagination getList(String repairType, String addrProvince,
                                  String addrCity, String releaseId, String releaseDt,
                                  Integer pageNo, Integer pageSize) {
            Pagination page = dao.getPage(repairType, addrProvince, addrCity, releaseId,
                    releaseDt, pageNo, pageSize);
            return page;
        }
    }

