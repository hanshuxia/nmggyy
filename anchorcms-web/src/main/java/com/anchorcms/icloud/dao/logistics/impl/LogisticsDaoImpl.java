package com.anchorcms.icloud.dao.logistics.impl;

import com.anchorcms.cms.model.main.Content;
import com.anchorcms.common.hibernate.Finder;
import com.anchorcms.common.hibernate.HibernateBaseDao;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.dao.logistics.LogisticsDao;
import com.anchorcms.icloud.model.logistics.SLogistics;
import com.anchorcms.icloud.model.payment.SPOrder;
import com.anchorcms.icloud.model.synergy.SAbility;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * @author zhouyq
 * @Date 2017/6/30
 * @return
 * 物流订单dao实现类
 */
@Repository
public class LogisticsDaoImpl extends HibernateBaseDao<SLogistics, Integer> implements LogisticsDao {

    /**
     * @author zhouyq
     * @Date 2017/6/30
     * @return
     * 物流订单保存
     */
    public SLogistics creLogisticsEntity(SLogistics sLogistics){
        getSession().save(sLogistics);
        return sLogistics;
    }

    public Pagination getPageBySelf(String title, Integer typeId,
                                    Integer UserId, boolean topLevel, boolean recommend,
                                    Content.ContentStatus status, Byte checkStep, Integer siteId,
                                    Integer channelId, Integer userId, int orderBy, int pageNo,
                                    int pageSize, String txlogisticId, Date createOrderTime, String logisticsOrderState) {
        Finder f = Finder.create("from SLogistics bean where  bean.logisticsOrderState in ('00','10')");
//        f.append(" join bean.content content where 1=1");
//        f.append(" and bean.logisticsOrderState in ('00','10')");
        //f.append(" where content.user.userId=:userId");
        //f.setParam("userId", UserId);
        appendQuery(f,txlogisticId, createOrderTime, logisticsOrderState);
        f.append(" order by bean.createOrderTime desc");
        return find(f, pageNo, pageSize);
    }
    private void appendQuery(Finder f,  String txlogisticId, Date createOrderTime, String logisticsOrderState) {
        if (txlogisticId != null && !"".equals(txlogisticId)) {
            f.append(" and bean.txlogisticId =:txlogisticId");
            f.setParam("txlogisticId",txlogisticId);
        }
        if (createOrderTime != null) {
            f.append(" and bean.createOrderTime = :createOrderTime");
            f.setParam("createOrderTime", createOrderTime);
        }
        if (logisticsOrderState != null && !"".equals(logisticsOrderState)) {
            f.append(" and bean.logisticsOrderState =:logisticsOrderState");
            f.setParam("logisticsOrderState", logisticsOrderState);
        }
    }

    public Pagination getPage(Integer siteId, CmsUser user, int pageNo, int pageSize,
                              Date createOrderTime, String logisticsOrderState, String txlogisticId) {
        Finder f = null;

            f = Finder.create("select  bean from SLogistics bean");
            if (createOrderTime != null) {
                f.append(" and bean.createOrderTime =:createOrderTime");
                f.setParam("createOrderTime", createOrderTime);
            }
            if (txlogisticId != null) {
                f.append(" and bean.txlogisticId =:txlogisticId");
                f.setParam("txlogisticId", txlogisticId);
            }

        f.setCacheable(true);
        f.append(" order by bean.createOrderTime desc");
        return find(f, pageNo, pageSize);
    }
    protected Class<SLogistics> getEntityClass() {
        return SLogistics.class;
    }

    public SLogistics save(SLogistics bean) {
        getSession().save(bean);
        return bean;
    }
    public SLogistics bySAbilityId(String txlogisticId) {
        Query query = getSession().createQuery("SELECT bean from SLogistics bean where bean.txlogisticId=:txlogisticId ")
                .setParameter("txlogisticId", txlogisticId);
        SLogistics sLogistics = (SLogistics) query.uniqueResult();
        return sLogistics;
    }
    public SLogistics getOrderById(String id) {
        if (id == null || id.equals("")) {
            return null;
        }
        SLogistics bean = (SLogistics) this.getSession().get(SLogistics.class, id);
        if (bean != null) {
            return bean;
        } else {
            return null;
        }
    }
}
