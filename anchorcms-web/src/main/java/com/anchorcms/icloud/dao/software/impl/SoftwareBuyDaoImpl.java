package com.anchorcms.icloud.dao.software.impl;

import com.anchorcms.common.hibernate.Finder;
import com.anchorcms.common.hibernate.HibernateBaseDao;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.dao.software.SoftwareBuyDao;
import com.anchorcms.icloud.model.common.SOrderPayment;
import com.anchorcms.icloud.model.payment.PaySettlementRefund;
import com.anchorcms.icloud.model.software.SSoftwareBuy;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

/**
 * Created by ly on 2017/1/6.
 */
@Repository
public class SoftwareBuyDaoImpl extends HibernateBaseDao<SSoftwareBuy, Integer> implements SoftwareBuyDao {

    public Pagination getPage(Integer siteId, CmsUser user, int pageNo, int pageSize,
                              Date startDate, String status, String paramu) {
        Finder f = Finder.create("select  bean from SSoftwareBuy bean where 1=1 ");
        if (paramu.equals("u")) {
            f.append(" and bean.userId =:userId");
            f.setParam("userId", user.getUserId());
            if (org.apache.commons.lang.StringUtils.isBlank(status) || status.equals("1")) {
                f.append(" and bean.status = '1' ");
            } else if (status.equals("2")) {
                f.append(" and bean.status in ('2','4','32') ");
            } else if (status.equals("3")) {
                f.append(" and bean.status in('30','31') ");
            }
        } else if (paramu.equals("a")) {
            /*f.append(" and bean.userId =:userId");
            f.setParam("userId", user.getUserId());*/
            if (org.apache.commons.lang.StringUtils.isBlank(status) || ("1").equals(status)) {
                f.append(" and bean.status = '1' ");
            } else if (("2").equals(status)) {
                f.append(" and bean.status in ('2','4','40') ");
            } else if (("3").equals(status)) {
                f.append(" and bean.status in('30','31','32') ");
            }

        }

        if (startDate != null) {
            f.append(" and bean.buyDt =:startDate");
            f.setParam("startDate", startDate);
        }

        f.append(" order by bean.buyDt desc,bean.buyId desc");
        f.setCacheable(true);
        return find(f, pageNo, pageSize);
    }

    public SSoftwareBuy findByPaymentNo(String id) {
        List<SSoftwareBuy> list = getSession().createQuery("   select s from  SSoftwareBuy s where s.paymentNo=:id ").setParameter("id", id).list();
        if (list.size() > 0) {
            return  list.get(0);
        }
        return null;
    }

    public SSoftwareBuy findByOrderNo(String orderNo) {
        List<SSoftwareBuy> list = getSession().createQuery("  select s from  SSoftwareBuy s where s.orderNo=:id ").setParameter("id", orderNo).list();
        if (list.size() > 0) {
            return  list.get(0);
        }
        return null;

    }

    public SSoftwareBuy findbean(int id) {
        return (SSoftwareBuy) getSession().get(SSoftwareBuy.class, id);
    }

    public SSoftwareBuy save(SSoftwareBuy bean) {
        getSession().save(bean);
        return bean;
    }

    public SSoftwareBuy update(SSoftwareBuy bean) {
        getSession().update(bean);
        return bean;
    }

    public SSoftwareBuy findById(Integer id) {
        SSoftwareBuy bean = get(id);
        return bean;
    }

    public SSoftwareBuy deleteById(Integer id) {
        SSoftwareBuy bean = findById(id);
        getSession().delete(bean);
        return bean;
    }

    /**
     * @param
     * @return
     * @Author zhouyq
     * @Date 2017/03/24
     * @Desc ����id�޸��˿��״̬���ܾ���
     */
    public void mdyOrderStateById(int orderId, String status) {
        StringBuffer hql = new StringBuffer();
        if (status.equals("31")) { // ���˿�
            hql.append(" UPDATE SSoftwareBuy  SET status = '31'" + " WHERE buyId = '" + orderId + "'");
        } else if (status.equals("32")) { // ?�Ѿܾ�
            hql.append(" UPDATE SSoftwareBuy  SET status = '32'" + " WHERE buyId = '" + orderId + "'");
        }
        Query query = getSession().createQuery(hql.toString());
        query.executeUpdate();
    }

    public PaySettlementRefund findbySerialNumber(String serialNumber) {
      List<PaySettlementRefund> list =   getSession().createQuery("select  bean from PaySettlementRefund bean where bean.serialNumber=:paymentNo ").setParameter("paymentNo", serialNumber).list();
        if(list.size()>0){
            return (PaySettlementRefund)list.get(0);
        }
        return null;
    }

    public void updatePaySettle(PaySettlementRefund pay) {
        getSession().update(pay);
    }

    @Override
    protected Class<SSoftwareBuy> getEntityClass() {
        return SSoftwareBuy.class;
    }
}
