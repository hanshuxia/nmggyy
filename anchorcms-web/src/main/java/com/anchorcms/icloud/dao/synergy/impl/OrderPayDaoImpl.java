package com.anchorcms.icloud.dao.synergy.impl;

import com.anchorcms.common.hibernate.HibernateBaseDao;
import com.anchorcms.icloud.dao.synergy.OrderPayDao;
import com.anchorcms.icloud.model.payment.*;
import com.anchorcms.icloud.model.synergy.SBigdataDemandQuote;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by LD on 2017/5/2.
 */
@Repository
public class OrderPayDaoImpl extends HibernateBaseDao implements OrderPayDao {
    public SPOrder saveOrder(SPOrder bean) {
        if (bean == null) {
            return null;
        }
        this.getSession().save(bean);
        return bean;
    }

    public SPOrder updateOrder(SPOrder bean) {
        if (bean == null) {
            return null;
        }
        this.getSession().update(bean);
        return bean;
    }
    public SBigdataDemandQuote updateOrder2(SBigdataDemandQuote bean) {
        if (bean == null) {
            return null;
        }
        this.getSession().update(bean);
        int quoteId = bean.getDemandQuoteId();
        StringBuffer sql1 = new StringBuffer();
        sql1.append(" select demand_id from s_bigdata_demand_quote where demand_quote_id ="+quoteId );
        Query query1 = getSession().createSQLQuery(sql1.toString());
         List list= query1.list();
        Object demand_id =  list.get(0);
        if (("1").equals(bean.getDeFlag())&&demand_id!=null&&!("").equals(demand_id)) {
            int demandid = bean.getDemandId();
            StringBuffer sql = new StringBuffer();
            sql.append(" UPDATE SBigdataDemandQuote bean SET bean.deFlag = 2 where bean.demandQuoteId != " + quoteId + " and bean.demand.demandId  = " + demandid );
            Query query = getSession().createQuery(sql.toString());
            query.executeUpdate();
        }
        return bean;
    }


    public SSupplychainOrder updateSupplychainOrder(SSupplychainOrder bean) {
        if (bean == null) {
            return null;
        }
        this.getSession().update(bean);
        return bean;
    }

    public SPOrder getOrderById(String id) {
        if (id == null || id.equals("")) {
            return null;
        }
        SPOrder bean = (SPOrder) this.getSession().get(SPOrder.class, id);
        if (bean != null) {
            return bean;
        } else {
            return null;
        }
    }
    public SPOrderObj getOrderObjById(String id) {
        if (id == null || id.equals("")) {
            return null;
        }
        SPOrderObj bean = (SPOrderObj) this.getSession().get(SPOrderObj.class, id);
        if (bean != null) {
            return bean;
        } else {
            return null;
        }
    }

    public SSupplychainOrder getSupplychainOrderById(String id){
        if (id == null||id.equals("")) {
            return null;
        }
        SSupplychainOrder bean = (SSupplychainOrder) this.getSession().get(SSupplychainOrder.class, id);
        if (bean != null) {
            return bean;
        } else {
            return null;
        }
    }


    public SPPay getBypaymentNo(String paymentNo) {
        SPPay sp;
        List<SPPay> list = this.getSession().createQuery(" from SPPay bean where bean.paymentNo=:id ").setParameter("id", paymentNo).list();
        if (list.size() == 0) {
            return null;
        } else {
            sp = list.get(0);
            return sp;
        }
    }


    public SPPay savePay(SPPay bean) {
        if (bean == null) {
            return null;
        }
        this.getSession().save(bean);
        return bean;
    }

    public SPAdminSettle findAdminSettleById(int id) {
        SPAdminSettle bean = (SPAdminSettle) this.getSession().get(SPAdminSettle.class, id);
        if (bean != null) {
            return bean;
        } else {
            return null;
        }
    }

    public SPAdminSettle updateAdminSettle(SPAdminSettle sadmin) {
        if (sadmin == null) {
            return null;
        }
        this.getSession().update(sadmin);
        return sadmin;

    }

    public SPAdminSettle deleteAdminSettle(SPAdminSettle bean) {
       if(bean==null){return null;}
        this.getSession().delete(bean);
        return bean;
    }

    public SPAdminSettle saveAdminSet(SPAdminSettle bean) {
        if (bean == null) {
            return null;
        }
        this.getSession().save(bean);
        return bean;
    }

    protected Class getEntityClass() {
        return null;
    }
}
