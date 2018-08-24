package com.anchorcms.icloud.dao.cloudcenter.impl;

import com.anchorcms.common.hibernate.Finder;
import com.anchorcms.common.hibernate.HibernateBaseDao;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.dao.cloudcenter.CloudCenterPaymentDao;
import com.anchorcms.icloud.model.common.SOrderPayment;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by lilimin on 2017/2/19.
 */
@Repository
public class CloudCenterPaymentDaoImpl extends HibernateBaseDao<SOrderPayment, Integer> implements CloudCenterPaymentDao {

    public SOrderPayment save(SOrderPayment payment) {
        getSession().save(payment);
        return payment;
    }

    public Pagination getPage(Integer siteId, CmsUser payUser, Integer pageNo, Integer pageSize, String status, String orderNo) {
        Finder f = Finder.create("select  bean from SOrderPayment bean");
        f.append(" where bean.payUser.userId =:userId");
        f.setParam("userId", payUser.getUserId());
        if("3".equals(status)){
            f.append(" and bean.state in(3,4,32)");
        }else if("5".equals(status)){
            f.append(" and bean.state in(30,31)");
        }
        else if (status != null && !"".equals(status)) {
            f.append(" and bean.state =:status");
            f.setParam("status", Integer.parseInt(status));
        }
        if (orderNo != null && !"".equals(orderNo)) {
            f.append(" and bean.orderNo like :orderNo");
            f.setParam("orderNo", "%"+orderNo+"%");
        }
        /*if (channelId != null){
            f.append(" and bean.channelId =:channelId");
            f.setParam("channelId", channelId);
        }*/
        f.append(" order by bean.createDT desc");
        f.setCacheable(true);
        return find(f, pageNo, pageSize);
    }

    /**
     * 获取账单的信息
     * @param paymentNo
     * @return
     */
    public SOrderPayment getByPaymentNo(String paymentNo) {
        Finder f = Finder.create("select  bean from SOrderPayment bean");
        f.append(" where bean.paymentNo=:paymentNo");
        f.setParam("paymentNo", paymentNo);
        f.append(" order by bean.createDT desc");
        Query query = getSession().createQuery(f.getOrigHql());
        f.setParamsToQuery(query);
        f.setCacheable(true);
        if (f.isCacheable()) {
            query.setCacheable(true);
        }
        List list = query.list();
        if(list.size()>0){
            return (SOrderPayment)list.get(0);
        }
        return null;
    }

    /**
     * @param
     * @return
     * @Author zhouyq
     * @Date 2017/03/25
     * @Desc 根据id修改退款订单状态（拒绝）
     */
    public void mdyOrderStateById(String orderNo, String status) {
            StringBuffer hql = new StringBuffer();
            if (status.equals("31")) {
                hql.append(" UPDATE SOrderPayment  SET state = 31" + " WHERE orderNo = '" + orderNo + "'");
            } else if (status.equals("32")) {
                hql.append(" UPDATE SOrderPayment  SET state = 32" + " WHERE orderNo = '" + orderNo + "'");
            }
            Query query = getSession().createQuery(hql.toString());
            query.executeUpdate();
    }

    /**
     * 获取账单的信息
     * @param orderNo
     * @return
     */
    public SOrderPayment ByorderNo(String orderNo) {
        Finder f = Finder.create("select  bean from SOrderPayment bean");
        f.append(" where bean.orderNo=:orderNo");
        f.setParam("orderNo", orderNo);
        f.append(" order by bean.createDT desc");
        Query query = getSession().createQuery(f.getOrigHql());
        f.setParamsToQuery(query);
        if (f.isCacheable()) {
            query.setCacheable(true);
        }
        List list = query.list();
        if(list.size()>0){
            return (SOrderPayment)list.get(0);
        }
        return null;
    }
    public SOrderPayment update(SOrderPayment payment) {
        getSession().update(payment);
        return  payment;
    }

    /**
     * @author ly
     * @date 2017/2/20 21:18
     * @desc
     **/
    public Pagination getManagePage(Integer siteId, int pageNo, int pageSize, String status, String orderNo) {
        Finder f = Finder.create("select  bean from SOrderPayment bean where 1=1");
        if ("3".equals(status)){
            f.append(" and bean.state in(3, 4, 40)");
        } else if ("5".equals(status)) {
            f.append(" and bean.state in(30, 31, 32)");
        } else if (status != null && !"".equals(status)) {
            f.append(" and bean.state =:status");
            f.setParam("status", Integer.parseInt(status));
        }
        if (orderNo != null && !"".equals(orderNo)) {
            f.append(" and bean.orderNo like :orderNo");
            f.setParam("orderNo", "%"+orderNo+"%");
        }
        f.append(" order by bean.createDT desc");
        f.setCacheable(true);
        return find(f, pageNo, pageSize);
    }

    public void deleteByOrderId(SOrderPayment orderId) {
        getSession().delete(orderId);
    }

    /**
     * 获得Dao对于的实体类
     *
     * @return
     */
    @Override
    protected Class<SOrderPayment> getEntityClass() {
        return null;
    }
}
