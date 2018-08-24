package com.anchorcms.icloud.service.synergy.impl;

import com.anchorcms.common.page.Pagination;
import com.anchorcms.icloud.dao.synergy.SAbilityMyInquiryDao;
import com.anchorcms.icloud.service.synergy.SAbilityMyInquiryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

@Service
@Transactional
public class SAbilityMyInquiryServiceImpl implements SAbilityMyInquiryService {
    /**
     * @Author 闫浩芮
     * @Date 2017/1/10 0010 11:56
     */
    public Pagination getPageForMember(Integer canshu, Integer siteId, Integer modelId, Integer UserId,
                                       Integer memberId, int pageNo, int pageSize, Date releaseDt,
                                       Date deadlineDt, Integer demandId, String inquiryTheme,
                                       String selectedStatus,String payType,String statusType) {
        return dao. getPageByCompanyId(canshu,siteId,modelId,UserId,memberId,pageNo,pageSize,releaseDt,deadlineDt,
                                       demandId, inquiryTheme,selectedStatus,payType,statusType);
    }
    /**
     * @Author maocheng
     * @Email netuser.orz@icloud.com
     * @Date 2016/12/29
     * @Desc 询价明细
     */
/*    public SAbilityInquiry byInquiryId(Integer inquiryId){return dao.byInquiryId(inquiryId);}*/

    @Resource
    private SAbilityMyInquiryDao dao;

}
