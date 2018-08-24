package com.anchorcms.icloud.service.supplychain.impl;


import com.anchorcms.icloud.dao.supplychain.SSpareReleaseAdminsManageobjDao;
import com.anchorcms.icloud.model.supplychain.SSpareDemandObj;
import com.anchorcms.icloud.service.supplychain.SSpareReleaseAdminsManageobjService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by DELL on 2016/12/29.
 */
    /**
     * @Author liuyang
     * @Email
     * @Date 2016/12/26
     * @Desc 备品备件审核管理_管理员服务实现类
     * */
    @Service
    @Transactional
    public class SSpareReleaseAdminsManageobjServiceImpl implements SSpareReleaseAdminsManageobjService {
        @Resource
        private SSpareReleaseAdminsManageobjDao dao;

        /**
         * 根据id获取备品备件明细信息
         *
         * @param demandId
         * @return
         */
        public SSpareDemandObj findDetailAndPreviewByIdDemandObj(String demandId) {
            SSpareDemandObj oDemand = dao.findDetailAndPreviewByIdDemandObj(demandId);
            return oDemand;
        }

    }


