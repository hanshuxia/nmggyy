package com.anchorcms.icloud.service.synergy;

import com.anchorcms.common.page.Pagination;

import java.util.Date;


public interface SAbilityMyInquiryService {
    /**
     * @Author 闫浩芮
     * @Date 2017/1/10 0010 11:42
     */
    public Pagination getPageForMember(Integer canshu, Integer siteId, Integer modelId, Integer UserId,
                                       Integer memberId, int pageNo, int pageSize, Date releaseDt,
                                       Date deadlineDt, Integer demandId, String inquiryTheme,
                                       String selectedStatus,String payType,String statusType);

/*    *//**
     * @Author maocheng
     * @Email netuser.orz@icloud.com
     * @Date 2016/12/29
     * @Desc 询价明细
     *//*
    public SAbilityInquiry byInquiryId(Integer inquiryId);*/
}
