package com.anchorcms.icloud.dao.synergy.impl;

import com.anchorcms.common.hibernate.Finder;
import com.anchorcms.common.hibernate.HibernateBaseDao;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.dao.CmsUserDao;
import com.anchorcms.icloud.dao.synergy.SAbilityMyInquiryDao;
import com.anchorcms.icloud.model.synergy.SAbilityInquiry;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;

@Repository
public class SAbilityMyInquiryDaoImpl extends HibernateBaseDao<SAbilityInquiry, Integer> implements SAbilityMyInquiryDao {
    /**
     * @Author 闫浩芮
     * @Date 2017/1/10 0010 11:46
     */
    public Pagination getPageByCompanyId(Integer canshu, Integer siteId, Integer modelId, Integer UserId,
                                         Integer memberId, int pageNo, int pageSize, Date releaseDt,
                                         Date deadlineDt, Integer demandId, String inquiryTheme,
                                         String selectedStatus, String payType, String statusType) {
        Finder f = Finder.create("select bean from SDemandQuote bean where bean.company.companyId=:companyId");
        String userCompanyId = cmsUserDao.findById(UserId).getCompany().getCompanyId();
        f.setParam("companyId", userCompanyId);
        //添加查询条件
        appendQuery(f, inquiryTheme, releaseDt, deadlineDt,statusType);
        f.append(" order by bean.updateDt desc,bean.demandQuoteId desc");
        f.setCacheable(true);
        return find(f, pageNo, pageSize);
    }

    private void appendQuery(Finder f, String inquiryTheme, Date myStartDate, Date myEndDate,String statusType) {
        if (inquiryTheme != null && !"".equals(inquiryTheme)) {
            f.append(" and bean.inquiryTheme like :inquiryTheme");
            f.setParam("inquiryTheme", "%" + inquiryTheme + "%");
        }
        if (myStartDate != null) {
            f.append(" and bean.deadlineDt >=:myStartDate");
            f.setParam("myStartDate", myStartDate);
        }
        if (myEndDate != null) {
            f.append(" and bean.deadlineDt <=:myEndDate");
            f.setParam("myEndDate", myEndDate);
        }
        if (statusType != null&& !"".equals(statusType)) {
            f.append(" and bean.selectedStatus =:statusType");
            f.setParam("statusType", statusType);
        }
    }
    /**
     * @Author maocheng
     * @Email netuser.orz@icloud.com
     * @Date 2016/12/29
     * @Desc 通过ID获取需求信息
     */
    public SAbilityInquiry byInquiryId(Integer inquiryId) {
        SAbilityInquiry sabilityInquiry = get(inquiryId);
        return sabilityInquiry;
    }

    @Resource
    private CmsUserDao cmsUserDao;

    @Override
    protected Class<SAbilityInquiry> getEntityClass() {
        return SAbilityInquiry.class;
    }
}