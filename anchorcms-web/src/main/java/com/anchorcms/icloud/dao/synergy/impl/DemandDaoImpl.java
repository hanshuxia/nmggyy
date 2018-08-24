package com.anchorcms.icloud.dao.synergy.impl;

import com.anchorcms.cms.model.main.Content;
import com.anchorcms.common.hibernate.Finder;
import com.anchorcms.common.hibernate.HibernateBaseDao;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.icloud.dao.synergy.DemandDao;
import com.anchorcms.icloud.model.synergy.SDemand;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

import static org.apache.commons.lang.StringUtils.isNumeric;

@Repository
public class DemandDaoImpl extends HibernateBaseDao<SDemand, Integer> implements DemandDao {

    /**
     *
     * @param typeId
     * @param UserId
     * @param topLevel
     * @param recommend
     * @param status
     * @param checkStep
     * @param siteId
     * @param channelId
     * @param userId
     * @param orderBy
     * @param pageNo
     * @param pageSize
     * @param demandId  询价编号
     * @param inquiryTheme  询价主题
     * @param contact  联系人
     * @param startDate  开始时间
     * @param endDate  结束时间
     * @param classifyType 所属分类
     * @param companyId  公司编号
     * @param statusType  状态
     * @param mobile  电话号码
     * @param createDt  创建日期
     * @param deadlineDt  报价截止日期
     * @Author maocheng
     * @return
     */
    public Pagination getPageByAll(Integer typeId, Integer UserId, boolean topLevel, boolean recommend,
                                    Content.ContentStatus status, Byte checkStep, Integer siteId,
                                    Integer channelId, Integer userId, int orderBy, int pageNo, int pageSize,
                                   String demandId, String inquiryTheme, String contact, Date startDate, Date endDate, String classifyType,
                                    String companyId, String statusType, String mobile, Date createDt, Date deadlineDt) {
        Finder f = Finder.create("select  bean from SDemand bean where 1=1");
        //modified by GJN 管理员应该能查询所有的需求
        f.append(" and bean.statusType in ('0','2','3') and DATEDIFF(bean.deadlineDt,now())>=0");
        //添加查询条件
        appendQuery(f, demandId,inquiryTheme,contact,startDate,endDate,classifyType,companyId,statusType,mobile,createDt,deadlineDt);
        f.append(" order by bean.updateDt desc,bean.demandId desc");
        return find(f, pageNo, pageSize);
    }
    private void appendQuery(Finder f, String demandId, String inquiryTheme, String contact, Date startDate, Date endDate, String classifyType,
                             String companyId, String statusType, String mobile, Date createDt, Date deadlineDt) {
        if (demandId != null && !"".equals(demandId)) {
            f.append(" and cast(bean.demandId as string) like :demandId");
            f.setParam("demandId", "%"+demandId+"%");
        }
        if (inquiryTheme != null && !"".equals(inquiryTheme)) {
            f.append(" and bean.inquiryTheme like :inquiryTheme");
            f.setParam("inquiryTheme", "%"+inquiryTheme+"%");
        }
        if (startDate != null) {
            f.append(" and bean.createDt >=:startDate");
            f.setParam("startDate", startDate);
        }
        if (endDate != null) {
            f.append(" and bean.createDt <=:endDate");
            f.setParam("endDate", endDate);
        }
        if (contact != null && !"".equals(contact)) {
            f.append(" and bean.content.user.username like :contact");
            f.setParam("contact", "%" +contact + "%");
        }
        if (classifyType != null && !"".equals(classifyType)) {
            f.append(" and bean.classifyType like :classifyType");
            f.setParam("classifyType", "%" +classifyType + "%");
        }
        if (companyId != null && !"".equals(companyId)) {
            f.append(" and bean.company.companyCode like :companyId");
            if(isNumeric(companyId)) {
                f.setParam("companyId", Integer.parseInt(companyId));
            }else{
                f.setParam("companyId", null);
            }
        }
        if (statusType != null && !"".equals(statusType)) {
            f.append(" and bean.statusType =:statusType");
            f.setParam("statusType",statusType);
        }
        if (mobile != null && !"".equals(mobile)) {
            f.append(" and bean.mobile like :mobile");
            f.setParam("mobile", "%" +mobile + "%");
        }
    }
    /**
     * @param id
     * @Author jiangshuzhong
     * @Date 2016/12/29
     * @Desc 需求发布审批
     */
    public void rejectDemand(Integer id) {
        Query query = getSession().createQuery("update SDemand bean  set bean.statusType=:status  where bean.content.contentId=:contentId ")

                .setParameter("status","0")
                .setParameter("contentId",id);
        int  a = query.executeUpdate();
        System.out.println(a);
    }
    public void passDemand(Integer id) {
        Query query = getSession().createQuery("update SDemand bean  set bean.statusType=:status ,bean.releaseDt=:releaseDt  where bean.content.contentId=:contentId ")

                .setParameter("status","3")
                .setParameter("releaseDt", new java.sql.Date(System.currentTimeMillis()))
                .setParameter("contentId",id);
        int  a = query.executeUpdate();
        System.out.println(a);
    }
    @Override
    protected Class<SDemand> getEntityClass() {
        return SDemand.class;
    }
}
