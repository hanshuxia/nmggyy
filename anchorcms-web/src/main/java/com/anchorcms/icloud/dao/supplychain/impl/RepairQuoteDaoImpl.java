package com.anchorcms.icloud.dao.supplychain.impl;

import com.anchorcms.common.hibernate.Finder;
import com.anchorcms.common.hibernate.HibernateBaseDao;

import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.dao.CmsUserDao;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.dao.supplychain.RepairQuoteDao;
import com.anchorcms.icloud.model.supplychain.SRepairQuote;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;

/**
*@Author 潘晓东
*@Date 2017/1/23 13:47
*@Return我的抢单报价
*/
@Repository
public class RepairQuoteDaoImpl extends HibernateBaseDao<SRepairQuote,String> implements RepairQuoteDao {
    protected Class<SRepairQuote> getEntityClass() {
        return SRepairQuote.class;
    }

    /**
     *@Author 潘晓东
     *@Date 2017/1/5 14:42
     *@Return抢单报价列表分页
     */
    public Pagination getQuoteListForMember(CmsUser user,String repairName,String inquiryTheme, Date myStartDate, Date myEndDate, String companyName,
                                            int pageNo, int pageSize){
    if("admin".equals(user.getUsername())){
            Finder f = Finder.create("  from SRepairQuote bean ");
            f.append(" where bean.selectStatus <>'00'");
            appendQuery(f,repairName, inquiryTheme, myStartDate, myEndDate, companyName);
            f.append(" order by bean.createDt desc");
            return find(f, pageNo, pageSize);
        }
        else {
            Finder f = Finder.create("  from SRepairQuote bean ");
            f.append(" where bean.company.companyId =:companyId");
            f.setParam("companyId", user.getCompany().getCompanyId());
        f.append(" and bean.selectStatus <>'00'");
            appendQuery(f,repairName, inquiryTheme, myStartDate, myEndDate, companyName);
            f.append(" order by bean.createDt desc");
            return find(f, pageNo, pageSize);
        }
    }

    // 添加查询条件
    private void appendQuery(Finder f,String repairName,String inquiryTheme, Date myStartDate, Date myEndDate, String companyName) {

        if (repairName != null && !"".equals(repairName)) {
            f.append(" and bean.demand.repairName like :repairName");
            f.setParam("repairName","%" +repairName + "%");
        }

        if (inquiryTheme != null && !"".equals(inquiryTheme)) {
            f.append(" and bean.selectStatus =:inquiryTheme");
            f.setParam("inquiryTheme", inquiryTheme);
        }


        if (myStartDate != null) {
            f.append(" and bean.deadlineDt >=:myStartDate");
            f.setParam("myStartDate", myStartDate);
        }
        if (myEndDate != null) {
            f.append(" and bean.deadlineDt <=:myEndDate");
            f.setParam("myEndDate", myEndDate);
        }
        if (companyName != null && !"".equals(companyName)) {
            f.append(" and bean.demand.company.companyName like :companyName");
            f.setParam("companyName","%" +companyName + "%");
        }
    }

    /***
     * @author zhouyq
     * @date 2017-05-03
     * @return
     * @desc 供应链维修方订单列表
     */
    public Pagination getSupplychainSellerOrder(Integer siteId, CmsUser user, int pageNo, int pageSize,
                                            Date startDate, String state, String orderId) {
        // 只查询登录用户订单信息
        Finder f = Finder.create("select  bean from SSupplychainOrder bean where bean.supplycompany.companyId=:companyId");
        String userCompanyId = cmsUserDao.findById(user.getUserId()).getCompany().getCompanyId();
        f.setParam("companyId", userCompanyId);
        if ((state != null) && (!("").equals(state))) {
            if (("0").equals(state)) { // 未确认
                f.append(" and bean.state = '0' ");
            } else if (("1").equals(state)) { // 待付款
                f.append(" and bean.state in ('1') ");
            } else if (("2").equals(state)) { // 已付款
                f.append(" and bean.state in ('2') ");
            } else if (("3").equals(state)) { // 退款（退款中、退款成功、拒绝退款）
                f.append(" and bean.state in('30','31','32') ");
            } else if (("6").equals(state)) { // 平台介入中
                f.append(" and bean.state in('6') ");
            } else if (("9").equals(state)) { // 已发货（已收货、未收货）
                f.append(" and bean.state in('4','7') ");
            } else if (("10").equals(state)) { // 交易完成（已结算）
                f.append(" and bean.state in('8','40') ");
            }
        }

        if ((startDate != null) && (!("").equals(startDate))) { // 订单日期
            f.append(" and bean.orderDt =:startDate");
            f.setParam("startDate", startDate);
        }

        if ((orderId != null) && (!("").equals(orderId))) { // 订单号
            f.append(" and bean.orderId like:orderId ");
            f.setParam("orderId", "%"+orderId+"%");
        }

        f.append(" order by bean.orderDt desc, bean.orderId desc");  // 排序
        f.setCacheable(true);
        return find(f, pageNo, pageSize);
    }

    /**
     * @param
     * @return
     * @Author zhouyq
     * @Date 2017/05/03
     * @Desc 根据id修改订单状态
     */
    public void mdyOrderStateById(String orderId, String state) {
        StringBuffer hql = new StringBuffer();
        if (state.equals("1")) { // 确认订单
            hql.append(" UPDATE SSupplychainOrder  SET state = '1'" + " WHERE orderId = '" + orderId + "'");
        } else if (state.equals("4")) { // 确认收货
            hql.append(" UPDATE SSupplychainOrder  SET state = '4'" + " WHERE orderId = '" + orderId + "'");
        } else if (state.equals("7")) { // 已发货
            hql.append(" UPDATE SSupplychainOrder  SET state = '7'" + " WHERE orderId = '" + orderId + "'");
        } else if (state.equals("32")) { // 拒绝退款
            hql.append(" UPDATE SSupplychainOrder  SET state = '32'" + " WHERE orderId = '" + orderId + "'");
        }else if (state.equals("40")) { // 确认完成
            hql.append(" UPDATE SSupplychainOrder  SET state = '40'" + " WHERE orderId = '" + orderId + "'");
        }
        Query query = getSession().createQuery(hql.toString());
        query.executeUpdate();
    }

    /**
    *@Author 潘晓东
    *@Date 2017/1/5 18:36
    *@Return抢单报价根据ID
    */
    public SRepairQuote selectByQuoteID(String id) {
        SRepairQuote entity = get(id);
        return entity;
    }

    @Resource
    private CmsUserDao cmsUserDao;

}
