package com.anchorcms.icloud.dao.supplychain.impl;

import com.anchorcms.common.hibernate.Finder;
import com.anchorcms.common.hibernate.HibernateBaseDao;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.icloud.dao.supplychain.SRepairInquiryDao;
import com.anchorcms.icloud.model.payment.SSupplychainOrder;
import com.anchorcms.icloud.model.supplychain.SRepairInquiry;
import com.anchorcms.icloud.model.synergy.SDeviceInquiry;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

/**
 * Created by hansx on 2016/12/27.
 * 维修资源询价dao实现
 */
@Repository
public class SRepairInquiryDaoImpl extends HibernateBaseDao<SRepairInquiry, String> implements SRepairInquiryDao {


    public SRepairInquiry findById(String id) {
        SRepairInquiry entity = get(id);
        return entity;
    }

    public SRepairInquiry save(SRepairInquiry SRepairInquiry) {
        this.getSession().save(SRepairInquiry);
        return SRepairInquiry;
    }

    public SDeviceInquiry save(SDeviceInquiry sDeviceInquiry) {
        this.getSession().save(sDeviceInquiry);
        return sDeviceInquiry;
    }
    public List<SRepairInquiry> getList() {
        String hql = " from SRepairInquiry  ";
        Query query = getSession().createQuery(hql);
        List<SRepairInquiry> list = query.list();
        return list;
    }


    /**
     * @return 供应链-询价管理列表数据
     * @author hansx
     * @Date 2017/1/19 0019 上午 11:36
     */

    public Pagination getInquiryListForMember(String statusType, String inquiryTheme, Date startDate, Date endDate, String companyName, String companyId,
                                              Integer channelId, Integer siteId, Integer modelId, Integer memberId, int pageNo, int pageSize, Boolean flag) {
        Finder f = Finder.create(" select bean from SRepairInquiry bean");
        f.append(" where 1 = 1");
        //添加查询条件
        appendQuery(f, statusType, inquiryTheme, startDate, endDate, companyName, flag);
        if (flag) {//维修方-待报价管理列表
            f.append(" and bean.sRepairRes.scompany.companyId = :companyId");

        } else {//需求方-询价管理列表
            f.append(" and bean.company.companyId = :companyId");
        }
        f.setParam("companyId", companyId);
        f.append(" order by bean.createDt desc");
        return find(f, pageNo, pageSize);
    }

    /**
     * @return
     * @author hansx
     * @Date 2017/1/4 0004 下午 2:17
     */
    private void appendQuery(Finder f, String statusType, String inquiryTheme, Date startDate, Date endDate, String companyName, Boolean flag) {

        if (startDate != null && !"".equals(startDate)) {
            f.append(" and bean.deadlineDt >=:startDate");
            f.setParam("startDate", startDate);
        }
        if (endDate != null && !"".equals(endDate)) {
            f.append(" and bean.deadlineDt <=:endDate");
            f.setParam("endDate", endDate);
        }
        if (inquiryTheme != null && !"".equals(inquiryTheme)) {
            f.append(" and bean.inquiryTheme like :inquiryTheme");
            f.setParam("inquiryTheme", "%" + inquiryTheme + "%");
        }
        if (companyName != null && !"".equals(companyName)) {
            if (flag) {//维修方-待报价管理列表 询价企业
                f.append(" and bean.company.companyName like:companyName");
                f.setParam("companyName", "%" + companyName + "%");
            } else {//需求方-询价管理列表 能力企业
                f.append(" and bean.sRepairRes.scompany.companyName like :companyName");
                f.setParam("companyName", "%" + companyName + "%");
            }
        }

        if (statusType != null && !"".equals(statusType)) {//待发布
            f.append(" and bean.statusType = :statusType");
            f.setParam("statusType", statusType);
        }
//        else if (flag) {//待报价管理列表
//            f.append(" and bean.statusType in ('3','4','5','6')");
//        }
    }

    /**
     * @return 提交报价，更新状态
     * @author hansx
     * @Date 2017/1/4 0013 下午 2:36
     */
    public int updateById(String id, Double offer, String statusType) {
        StringBuffer hql = new StringBuffer("update SRepairInquiry s set ");
        hql.append("s.offer='" + offer + "'");
        hql.append(",s.statusType='" + statusType + "'");
        hql.append(" where s.inquiryId='" + id + "'");
        return getSession().createQuery(hql.toString()).executeUpdate();

    }

    /**
     * @return 更新状态
     * @author hansx
     * @Date 2017/1/4 0013 下午 2:36
     */
    public int updateStatusById(String id, String statusType) {
        StringBuffer hql = new StringBuffer("update SRepairInquiry s set ");
        hql.append(" s.statusType='" + statusType + "'");
        hql.append(" where s.inquiryId='" + id + "'");
        return getSession().createQuery(hql.toString()).executeUpdate();
    }

    /**
     * @author dongxuecheng
     * @Date 2017/2/22 10:08
     * @return
     * @description
     */
    public Integer setEvaluteValue(String inquiryId,String evaluteValue){
        String hql="update  SRepairInquiry  set evaluate ="+evaluteValue+" where inquiryId ='"+inquiryId+"'";
        return getSession().createQuery(hql).executeUpdate();
    }


    @Override
    protected Class<SRepairInquiry> getEntityClass() {
        return SRepairInquiry.class;
    }

    public SSupplychainOrder save(SSupplychainOrder bean) {
        getSession().save(bean);
        return bean;
    }
}
