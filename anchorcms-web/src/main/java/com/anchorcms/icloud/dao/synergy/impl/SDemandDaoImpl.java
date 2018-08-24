package com.anchorcms.icloud.dao.synergy.impl;

import com.anchorcms.common.hibernate.Finder;
import com.anchorcms.common.hibernate.HibernateBaseDao;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.dao.CmsUserDao;
import com.anchorcms.icloud.dao.synergy.SDemandDao;
import com.anchorcms.icloud.model.synergy.SDemand;
import com.anchorcms.icloud.model.synergy.SDemandObj;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Author Yhr
 * 查询需求列表
 * @Date 2016/12/23 0023 15:50
 */
@Repository
public class SDemandDaoImpl extends HibernateBaseDao<SDemand, Integer> implements SDemandDao {
    public Pagination getPageBySelfCompany(Integer canshu, Integer siteId, Integer modelId,
                                    Integer memberId, int pageNo, int pageSize, Date releaseDt, Date deadlineDt,
                                           Integer demandId, String inquiryTheme,
                                    Integer UserId, String selectedStatus, String payType,String statusType) {
            if(canshu!=null&&!"".equals(canshu)){
            Finder f = Finder.create("select bean from SAbilityInquiry bean where bean.company.companyId=:companyId");
            String userCompanyId = cmsUserDao.findById(UserId).getCompany().getCompanyId();
            f.setParam("companyId", userCompanyId);
            appendQuery(f,selectedStatus,statusType);
            f.append(" order by bean.updateDt desc,bean.inquiryId desc");
            f.setCacheable(true);
            return find(f, pageNo, pageSize);
        }else{
            if(selectedStatus !=null && !"".equals(selectedStatus)){
                Finder f = Finder.create("select bean from SDemandQuote bean where bean.demand.company.companyId=:companyId");
                String userCompanyId = cmsUserDao.findById(UserId).getCompany().getCompanyId();
                f.setParam("companyId", userCompanyId);
                if (inquiryTheme != null && !"".equals(inquiryTheme)) {
                    f.append(" and bean.demand.inquiryTheme like :inquiryTheme");
                    f.setParam("inquiryTheme", "%"+inquiryTheme+"%" );
                }
                appendQuery(f,selectedStatus,statusType);
                f.append(" order by bean.updateDt desc,bean.demandQuoteId desc");
                f.setCacheable(true);
                return find(f, pageNo, pageSize);
            }else {
                Finder f = Finder.create("select bean from SDemand bean where bean.company.companyId=:companyId " +
                        "and bean.deFlag='1'");
                String userCompanyId = cmsUserDao.findById(UserId).getCompany().getCompanyId();
                f.setParam("companyId", userCompanyId);
                if (inquiryTheme != null && !"".equals(inquiryTheme)) {
                    f.append(" and bean.inquiryTheme like :inquiryTheme");
                    f.setParam("inquiryTheme", "%"+inquiryTheme+"%" );
                }
                appendQuery(f,selectedStatus,statusType);
                f.append(" order by bean.updateDt desc,bean.demandId desc");
                f.setCacheable(true);
                return find(f, pageNo, pageSize);
            }
        }
    }
    public Pagination getPageBySelfCompany2(Integer canshu, Integer siteId, Integer modelId,
                                           Integer memberId, int pageNo, int pageSize, Date releaseDt, Date deadlineDt,
                                           Integer demandId, String inquiryTheme,
                                           Integer UserId, String selectedStatus, String payType,String statusType) {
        if(canshu!=null&&!"".equals(canshu)){
            Finder f = Finder.create("select bean from SAbilityInquiry bean where bean.company.companyId=:companyId");
            String userCompanyId = cmsUserDao.findById(UserId).getCompany().getCompanyId();
            f.setParam("companyId", userCompanyId);
            appendQuery(f,selectedStatus,statusType);
            f.append(" order by bean.updateDt desc,bean.inquiryId desc");
            f.setCacheable(true);
            return find(f, pageNo, pageSize);
        }else{
            if(selectedStatus !=null && !"".equals(selectedStatus)){
                Finder f = Finder.create("select bean from SDemandQuote bean where bean.demand.company.companyId=:companyId");
                String userCompanyId = cmsUserDao.findById(UserId).getCompany().getCompanyId();
                f.setParam("companyId", userCompanyId);
                if (inquiryTheme != null && !"".equals(inquiryTheme)) {
                    f.append(" and bean.demand.inquiryTheme like :inquiryTheme");
                    f.setParam("inquiryTheme", "%"+inquiryTheme+"%" );
                }
                appendQuery(f,selectedStatus,statusType);
                f.append(" order by bean.updateDt desc,bean.demandQuoteId desc");
                f.setCacheable(true);
                return find(f, pageNo, pageSize);
            }else {
                Finder f = Finder.create("select bean from SDemand bean where bean.company.companyId=:companyId " +
                        "and bean.deFlag='1'");
                String userCompanyId = cmsUserDao.findById(UserId).getCompany().getCompanyId();
                f.setParam("companyId", userCompanyId);
                if (inquiryTheme != null && !"".equals(inquiryTheme)) {
                    f.append(" and bean.inquiryTheme like :inquiryTheme");
                    f.setParam("inquiryTheme", "%"+inquiryTheme+"%" );
                }
                appendQuery(f,selectedStatus,statusType);
                f.append(" order by bean.updateDt desc,bean.demandId desc");
                f.setCacheable(true);
                return find(f, pageNo, pageSize);
            }
        }
    }
    /***
     * @author Yhr
     * @date 2016/12/22
     * @param f
     * @desc 查询条件
     */
    private void appendQuery(Finder f,String selectedStatus,String statusType ) {

        if(selectedStatus !=null && !"".equals(selectedStatus)){
            if("1".equals(selectedStatus))
            {f.append(" and  bean.demand.statusType=3");}
            f.append(" and bean.selectedStatus =:selectedStatus");
            f.setParam("selectedStatus", selectedStatus);
        }
        if(statusType !=null && !"".equals(statusType)){
            if ("1".equals(statusType)){//草稿
                f.append(" and bean.statusType =:statusType");
                f.setParam("statusType", statusType);
            }else if ("2".equals(statusType)){//待审批
                f.append(" and bean.statusType =:statusType");
                f.append(" and DATEDIFF(bean.deadlineDt,now())>=0");
                f.setParam("statusType", statusType);
            }else if ("3".equals(statusType)){//询价中
                f.append(" and bean.statusType =:statusType");
                f.append(" and DATEDIFF(bean.deadlineDt,now())>=0");
                f.setParam("statusType", statusType);
            }else if ("0".equals(statusType)){//被驳回
                f.append(" and bean.statusType =:statusType");
                f.setParam("statusType", statusType);
            }else if ("4".equals(statusType)){//已截止
                f.append(" and DATEDIFF(bean.deadlineDt,now())<0 and (bean.statusType ='2'or bean.statusType ='3')");
            }else if ("5".equals(statusType)){//已关闭
                f.append(" and bean.statusType ='5'");
            }else if ("6".equals(statusType)){//数据库中的4为下单
                f.append(" and bean.statusType ='4'");
            }
        }
    }
    /**
     * @Author jiangshuzhong
     * @param demandId
     * @Email netuser.orz@icloud.com
     * @Date 2016/12/26
     * @Desc 通过ID获取需求信息
     */
    public SDemand bySDemandId(Integer demandId) {
        SDemand sdemand = get(demandId);
        return sdemand;
    }
    public SDemandObj byDemandObjId(Integer demandId) {
        SDemandObj sDemandObj = (SDemandObj)getSession().get(SDemandObj.class,demandId);
        return sDemandObj;
    }

    /**
     * @Author 闫浩芮
     * 根据demandId查找
     * @Date 2016/12/28 0028 9:08
     */
    public SDemand findById(Integer id){
        SDemand sDemand = get(id);
        return sDemand;
    }

    /**
     * @Author 闫浩芮
     * 删除需求信息
     * @Date 2016/12/29 0029 11:02
     */
    public SDemand delete(SDemand sDemand) {
        if (sDemand != null) {
            getSession().delete(sDemand);
        }
        return sDemand;
    }

    /**
     * 手动更新需求状态位
     *
     * @param demandId
     * @param statusType
     */
    public void artUpdateStatusType(Integer demandId, String statusType) {
        Query query = getSession().createQuery("update SDemand bean set bean.statusType=:statusType " +
                "where bean.demandId=:demandId");
        query.setParameter("statusType",statusType);
        query.setParameter("demandId",demandId);
        query.executeUpdate();
    }
    /**
     * @author jsz
     * @date 2017/1/9 16:13
     * @desc 自定义标签——about需求池展示
     **/
    public List<SDemand> getList(Integer count, Integer orderBy, Integer listType, Integer overDate,String demandType) {
        Finder f = Finder.create("select bean from SDemand bean where bean.statusType = '3'");
            f.append(" and DATEDIFF(bean.deadlineDt,now())>=0");
        if (overDate !=null){
            f.append(" and DATEDIFF(bean.deadlineDt,now())<=7");
        }
        if(demandType!=null){
            f.append(" and classifyType =:classifyType");
            f.setParam("classifyType",""+demandType+"");
        }
        if (orderBy !=null){
            switch (orderBy) {
                case 1://发布日期降序
                    f.append(" order by bean.releaseDt desc");
                    break;
                case 2://截止日期升序
                    f.append(" order by bean.deadlineDt asc");
                    break;
            }
        }
        if (count != null) {
            f.setMaxResults(count);
        }
        return find(f);
    }

    public SDemand update(SDemand bean) {
        getSession().save(bean);
        return bean;
    }
    /**
     * @Author 闫浩芮
     * 置为通过
     * @Date 2017/2/17 0017 18:45
     */
    public int passMany(String demandIdsStr) {
        java.sql.Date now = new java.sql.Date(System.currentTimeMillis());
        Query query = getSession().createQuery("update SDemand bean set bean.statusType='3', bean.releaseDt=(:releaseDt)" +
                "where bean.demandId in (" + demandIdsStr + ")").setParameter("releaseDt",now);
        return query.executeUpdate();
    }
    /**
     * @Author 闫浩芮
     * 置为驳回
     * @Date 2017/2/17 0017 18:45
     */
    public int rejectMany(String demandIdsStr,String backReason) {
        java.sql.Date now = new java.sql.Date(System.currentTimeMillis());
        Query query = getSession().createQuery("update SDemand bean set bean.statusType='0', bean.releaseDt=(:releaseDt), bean.backReason='"+backReason+"' " +
                "where bean.demandId in (" + demandIdsStr + ")").setParameter("releaseDt",now);
        return query.executeUpdate();
    }
    /**
     * @author liuyang
     * @Date 2017/6/2 13:52
     * @return
     */
    public Pagination getPageByDeviceCompany(Integer canshu, Integer siteId, Integer modelId,
                                           Integer memberId, int pageNo, int pageSize, Date releaseDt, Date deadlineDt,
                                           Integer demandId, String inquiryTheme,
                                           Integer UserId, String selectedStatus, String payType,String statusType) {
        if(canshu!=null&&!"".equals(canshu)){
            Finder f = Finder.create("select bean from SDeviceInquiry bean where bean.company.companyId=:companyId");
            String userCompanyId = cmsUserDao.findById(UserId).getCompany().getCompanyId();
            f.setParam("companyId", userCompanyId);
            appendQuery(f,selectedStatus,statusType);
            f.append(" order by bean.updateDt desc,bean.inquiryId desc");
            f.setCacheable(true);
            return find(f, pageNo, pageSize);
        }else{
            if(selectedStatus !=null && !"".equals(selectedStatus)){
                Finder f = Finder.create("select bean from SDemandQuote bean where bean.demand.company.companyId=:companyId");
                String userCompanyId = cmsUserDao.findById(UserId).getCompany().getCompanyId();
                f.setParam("companyId", userCompanyId);
                if (inquiryTheme != null && !"".equals(inquiryTheme)) {
                    f.append(" and bean.demand.inquiryTheme like :inquiryTheme");
                    f.setParam("inquiryTheme", "%"+inquiryTheme+"%" );
                }
                appendQuery(f,selectedStatus,statusType);
                f.append(" order by bean.updateDt desc,bean.demandQuoteId desc");
                f.setCacheable(true);
                return find(f, pageNo, pageSize);
            }else {
                Finder f = Finder.create("select bean from SDemand bean where bean.company.companyId=:companyId " +
                        "and bean.deFlag='1'");
                String userCompanyId = cmsUserDao.findById(UserId).getCompany().getCompanyId();
                f.setParam("companyId", userCompanyId);
                if (inquiryTheme != null && !"".equals(inquiryTheme)) {
                    f.append(" and bean.inquiryTheme like :inquiryTheme");
                    f.setParam("inquiryTheme", "%"+inquiryTheme+"%" );
                }
                appendQuery(f,selectedStatus,statusType);
                f.append(" order by bean.updateDt desc,bean.demandId desc");
                f.setCacheable(true);
                return find(f, pageNo, pageSize);
            }
        }
    }
    @Resource
    private CmsUserDao cmsUserDao;
    @Override
    protected Class<SDemand> getEntityClass() {
        return SDemand.class;
    }
}
