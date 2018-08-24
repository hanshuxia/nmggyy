package com.anchorcms.icloud.dao.synergy.impl;

import com.anchorcms.common.hibernate.Finder;
import com.anchorcms.common.hibernate.HibernateBaseDao;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.dao.synergy.SAbilityInquiryDao;
import com.anchorcms.icloud.model.synergy.SAbilityInquiry;
import com.anchorcms.icloud.model.synergy.SDeviceInquiry;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;


@Repository
public class SAbilityInquiryDaoImpl extends HibernateBaseDao<SAbilityInquiry, Integer> implements SAbilityInquiryDao {
    /**
     * 协同制造-能力方-待报价方案列表
     * @param statusType
     * @param user
     * @param inquiryTheme 询价主题
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @param companyId 询价企业
     * @param pageNo
     * @param pageSize
     * @Author maocheng
     * @return
     */
    public Pagination getPageBySelf(String statusType,CmsUser user, String inquiryTheme, Date startDate, Date endDate, String companyId,
                                    int pageNo, int pageSize) {
        Finder f = Finder.create("select bean from SAbilityInquiry bean");
        f.append(" join bean.ability ability");
        f.append(" where ability.company.companyId =:companyId");
        f.setParam("companyId",user.getCompany().getCompanyId());
        //添加查询条件
        appendQuery(f, inquiryTheme, startDate, endDate, companyId,statusType);
        f.append(" order by bean.updateDt desc,bean.inquiryId desc");
        f.setCacheable(true);
        return find(f, pageNo, pageSize);
    }

    /**
     * 协同制造-企业设备-待报价方案列表
     * @param statusType
     * @param user
     * @param inquiryTheme 询价主题
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @param companyId 询价企业
     * @param pageNo
     * @param pageSize
     * @Author zhouyq
     * @return
     */
    public Pagination getPageForDevice(String statusType,CmsUser user, String inquiryTheme, Date startDate, Date endDate, String companyId,
                                    int pageNo, int pageSize) {
        Finder f = Finder.create("select bean from SDeviceInquiry bean where 1 = 1");
//        f.append(" join bean.scompanyDevice scompanyDevice");
//        f.append(" where scompanyDevice.company.companyId =:companyId");
//        f.setParam("companyId",user.getCompany().getCompanyId());
        //添加查询条件
        appendQuery(f, inquiryTheme, startDate, endDate, companyId, statusType);
        f.append(" order by bean.updateDt desc,bean.inquiryId desc");
        f.setCacheable(true);
        return find(f, pageNo, pageSize);
    }

    private void appendQuery(Finder f, String inquiryTheme, Date startDate, Date endDate, String companyId,String statusType) {
        if (inquiryTheme != null && !"".equals(inquiryTheme)) {
            f.append(" and bean.inquiryTheme like :inquiryTheme");
            f.setParam("inquiryTheme", "%"+inquiryTheme+"%");
        }
        if (startDate != null) {
            f.append(" and bean.deadlineDt >=:startDate");
            f.setParam("startDate", startDate);
        }
        if (endDate != null) {
            f.append(" and bean.deadlineDt <=:endDate");
            f.setParam("endDate", endDate);
        }
        if (companyId != null && !"".equals(companyId)) {
            f.append(" and bean.company.companyName like :companyName");
            f.setParam("companyName", "%"+companyId+"%");
        }if (statusType != null && !"".equals(statusType)) {
            switch (Integer.parseInt(statusType)){
                case 0:
                    f.append(" and bean.statusType ='1'");
                    f.append(" and bean.quotePrice is null ");
                    break;
                case 1:
                    f.append(" and bean.statusType = '1'");
                    f.append(" and bean.quotePrice is not null ");
                    break;
                default:
                    f.append(" and bean.statusType =:statusType");
                    f.setParam("statusType", statusType);
            }

        }
    }

    /**
     * @Author maocheng
     * @Email netuser.orz@icloud.com
     * @Date 2016/12/29
     * @Desc 通过ID获取需求信息
     */
    public SAbilityInquiry byInquiryId(Integer inquiryId){
        SAbilityInquiry sabilityInquiry = get(inquiryId);
        return sabilityInquiry;
    }


    /**
     * @Author zhouyq
     * @Date 2017/06/05
     * @Desc 设备询价明细
     */
    public SDeviceInquiry byDeviceInquiryId(Integer inquiryId){
        SDeviceInquiry sdeviceInquiry = (SDeviceInquiry) this.getSession().get(SDeviceInquiry.class, inquiryId);
        return sdeviceInquiry;
    }

    /**
     * 根据能力询价表id 更改状态位
     *
     * @param inquiryId
     * @param statusType
     * @return
     */
    public Integer changeStatusType(Integer inquiryId, String statusType) {
        Query query = getSession().createQuery("update SAbilityInquiry bean set bean.statusType=:statusType where bean.inquiryId =:inquiryId");
        query.setParameter("inquiryId",inquiryId);
        query.setParameter("statusType",statusType);
        return query.executeUpdate();
    }
    public Integer changeDeviceStatusType(Integer inquiryId, String statusType) {
        Query query = getSession().createQuery("update SDeviceInquiry bean set bean.statusType=:statusType where bean.inquiryId =:inquiryId");
        query.setParameter("inquiryId",inquiryId);
        query.setParameter("statusType",statusType);
        return query.executeUpdate();
    }
    /**
     * 保存能力询价的报价
     *
     * @param inquiryId
     * @param bj        报价
     * @return
     */
    public Integer saveAbilityInquiryBj(Integer inquiryId, Double bj) {
        Query query = getSession().createQuery("update SAbilityInquiry bean set bean.quotePrice=:bj where bean.inquiryId =:inquiryId");
        query.setParameter("inquiryId",inquiryId);
        query.setParameter("bj",bj);
        return query.executeUpdate();
    }

    /**
     * 保存设备询价的报价
     *
     * @param inquiryId
     * @param bj 报价
     * @return
     */
    public Integer saveDeviceInquiryBj(Integer inquiryId, Double bj) {
        Query query = getSession().createQuery("update SDeviceInquiry bean set bean.quotePrice=:bj where bean.inquiryId =:inquiryId");
        query.setParameter("inquiryId", inquiryId);
        query.setParameter("bj", bj);
        return query.executeUpdate();
    }

    public SAbilityInquiry delete(SAbilityInquiry abilityINduiry) {
        if (abilityINduiry != null) {
            getSession().delete(abilityINduiry);
        }
        return abilityINduiry;
    }
    public SDeviceInquiry deleteDevice(SDeviceInquiry deviceInquiry) {
        if (deviceInquiry != null) {
            getSession().delete(deviceInquiry);
        }
        return deviceInquiry;
    }
    @Override
    protected Class<SAbilityInquiry> getEntityClass() {
        return SAbilityInquiry.class;
      }
}
