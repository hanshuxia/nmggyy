package com.anchorcms.icloud.service.supplychain;

import com.anchorcms.icloud.model.supplychain.SSpareDemandObj;


/**
 * Created by DELL on 2016/12/29.
 */
    /**
     * @Author liuyang
     * @Email
     * @Date 2016/12/29
     * @Desc 备品备件审核管理_管理员服务层接口
     * */
    public interface SSpareReleaseAdminsManageobjService {

        /**
         * 根据id获取众修资源明细信息
         *
         * @param demandId
         * @return
         */
        public SSpareDemandObj findDetailAndPreviewByIdDemandObj(String demandId);

    }


