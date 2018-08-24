package com.anchorcms.icloud.service.synergy;

import com.anchorcms.common.page.Pagination;
import com.anchorcms.icloud.model.synergy.SCompany;

import java.sql.Date;
import java.util.List;

/**
 * Copyright @ 2016 Shandong jxdinfo software co,ltd.
 * All right reserved.
 * Created by Gao Jiangning
 *
 * @Date 2016/12/27 0027
 * @Desc
 */

public interface SCompanyManagementService {

    Pagination getCompanyListByPage(String companyName, String companyCode,String mobile, Date createDtStart, Date createDtEnd, Integer pageNo,String status);
    Pagination getCompanyListByPage_check(String companyName, String companyCode,String mobile, Date createDtStart, Date createDtEnd, Integer pageNo,String status);


    /**
     * @Author jsz
     * @Date 2016/1/10
     * @Desc 自定义标签——企业制造能力展示
     */
    public List<SCompany> getList(Integer count, Integer orderBy, Integer listType);
    /**
     * @author liuyang
     * @Date 2017/5/10 19:03
     * @return
     */
    public Pagination getCredentialsList(String companyId, String companyName,String status, Integer pageNo, Integer pageSize);

    /**
     * @author liuyang
     * @Date 2017/5/11 9:56
     * @return资质审核通过驳回
     *
     */
    public void modifyAptitudeStateById(String aptitudeId, String status, java.util.Date updateDt, String backReason);

    /**
     * @author liuyang
     * @Date 2017/5/11 18:20
     * @return
     */
    SCompany byCompanyId(String id);
}
