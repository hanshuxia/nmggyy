package com.anchorcms.icloud.dao.synergy;

import com.anchorcms.common.page.Pagination;

import java.util.Date;


public interface SAbilityMyInquiryDao {
    /**
     * @Author 闫浩芮
     * @Date 2017/1/10 0010 11:46
     */
    public Pagination getPageByCompanyId(Integer canshu, Integer siteId, Integer modelId, Integer UserId,
                                           Integer memberId, int pageNo, int pageSize, Date releaseDt,
                                           Date deadlineDt, Integer demandId, String inquiryTheme,
                                           String selectedStatus,String payType,String statusType);

/*    *//**
     * @Author maocheng
     * @Email netuser.orz@icloud.com
     * @Date 2016/12/29
     * @Desc 通过ID获取需求信息
     *//*
    public SAbilityInquiry byInquiryId(Integer inquiryId);*/

}
