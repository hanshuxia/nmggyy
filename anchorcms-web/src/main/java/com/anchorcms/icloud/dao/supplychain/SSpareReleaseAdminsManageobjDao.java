package com.anchorcms.icloud.dao.supplychain;

import com.anchorcms.icloud.model.supplychain.SSpareDemandObj;


/**
 * Created by DELL on 2016/12/29.
 */
/**
 * @author liuyang
 * @Date 2017/1/4 16:07
 * 备品备件审核管理_管理员dao层
 */
public interface SSpareReleaseAdminsManageobjDao {
    /**
     * 根据id获取备品备件明细信息
     *
     * @param demandId
     * @return
     */
    public SSpareDemandObj findDetailAndPreviewByIdDemandObj(String demandId);
}
