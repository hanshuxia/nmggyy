package com.anchorcms.icloud.dao.supplychain.impl;

import com.anchorcms.common.hibernate.Finder;
import com.anchorcms.common.hibernate.HibernateBaseDao;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.dao.CmsUserDao;
import com.anchorcms.icloud.dao.supplychain.SLDOrderDao;
import com.anchorcms.icloud.model.payment.SPPay;
import com.anchorcms.icloud.model.payment.SSupplychainOrder;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author hansx
 * 协同制造/供应链订单表Dao实现类
 * @Date 2017/05/02 0023 15:50
 */
@Repository
public class SLDOrderDaoImpl extends HibernateBaseDao<SSupplychainOrder, String> implements SLDOrderDao {

    public Pagination getPageBySelfCompany(int pageNo, int pageSize, Integer userId, String statusType) {
        Finder f = Finder.create("select bean from SSupplychainOrder bean where bean.buycompany.companyId=:companyId");
        String userCompanyId = cmsUserDao.findById(userId).getCompany().getCompanyId();
        f.setParam("companyId", userCompanyId);
        appendQuery(f, statusType);
            f.append(" order by bean.orderDt desc");
        f.setCacheable(true);
        return find(f, pageNo, pageSize);

    }

    /***
     * @param f
     * @author hansx
     * @date 2017/05/02
     * @desc 查询条件
     */
    private void appendQuery(Finder f, String state) {
        if (state != null && !"".equals(state)) {
            if (("0").equals(state)) {
                f.append(" and bean.state = '0' ");
            } else if (("1").equals(state)) {
                f.append(" and bean.state in ('1') ");
            } else if (("2").equals(state)) {
                f.append(" and bean.state in ('2','32') ");
            } else if (("3").equals(state)) {
                f.append(" and bean.state in('30','31') ");
            }else if (("4").equals(state)) {
                f.append(" and bean.state in('8','40') ");
            }else if (("5").equals(state)) {
                f.append(" and bean.state = '5' ");
            }else if (("6").equals(state)) {
                f.append(" and bean.state = '6' ");
            } else if (("7").equals(state)) {
                f.append(" and bean.state in('4','7') ");
            }
        }
    }

    public SSupplychainOrder delete(SSupplychainOrder sPOrder) {
        if (sPOrder != null) {
            getSession().delete(sPOrder);
        }
        return sPOrder;
    }


    public SSupplychainOrder saveOrder(SSupplychainOrder bean) {
        if (bean == null) {
            return null;
        }
        this.getSession().save(bean);
        return bean;
    }

    public SSupplychainOrder updateOrder(SSupplychainOrder bean) {
        if (bean == null) {
            return null;
        }
        this.getSession().update(bean);
        return bean;
    }

    public SSupplychainOrder getOrderById(String id) {
        if (id == null || id.equals("")) {
            return null;
        }
        SSupplychainOrder bean = (SSupplychainOrder) this.getSession().get(SSupplychainOrder.class, id);
        if (bean != null) {
            return bean;
        } else {
            return null;
        }
    }

    public SPPay savePay(SPPay bean) {
        if (bean == null) {
            return null;
        }
        this.getSession().save(bean);
        return bean;
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


    @Resource
    private CmsUserDao cmsUserDao;

    @Override
    protected Class<SSupplychainOrder> getEntityClass() {
        return SSupplychainOrder.class;
    }
}
