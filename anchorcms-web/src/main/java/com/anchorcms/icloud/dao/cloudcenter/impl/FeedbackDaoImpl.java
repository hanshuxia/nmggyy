package com.anchorcms.icloud.dao.cloudcenter.impl;

import com.anchorcms.common.hibernate.Finder;
import com.anchorcms.common.hibernate.HibernateBaseDao;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.dao.cloudcenter.FeedbackDao;
import com.anchorcms.icloud.model.cloudcenter.SIcloudApply;
import org.springframework.stereotype.Repository;

import java.sql.Date;

/**
 * Created by ly on 2017/1/7.
 */
@Repository

public class FeedbackDaoImpl extends HibernateBaseDao<SIcloudApply, Integer> implements FeedbackDao {

    public Pagination getFeedbackPage(Integer siteId, CmsUser user, int pageNo, int pageSize,
                                      Date startDate, Date endDate, String applierName, String policyType,
                                      String policyName, String policyCode, String state) {
        Finder f = Finder.create("select  bean from SIcloudApply bean");
        f.append(" where 1 = 1");
        if (state != null && !"".equals(state)){//全部
            f.append(" and bean.state =:state");
            f.setParam("state", state);
        }
        if (startDate != null) {
            f.append(" and bean.createDt >=:startDate");
            f.setParam("startDate", startDate);
        }
        if (endDate != null) {
            f.append(" and bean.createDt <=:endDate");
            f.setParam("endDate", endDate);
        }
        if (applierName != null && !"".equals(applierName)) {
            f.append(" and bean.user.userName like :applierName");
            f.setParam("applierName", "%" +applierName + "%");
        }
        if (policyType != null && !"".equals(policyType)) {
            f.append(" and bean.policyType like :policyType");
            f.setParam("policyType", "%" +policyType + "%");
        }
        if (policyName != null && !"".equals(policyName)) {
            f.append(" and bean.policy.policyName like :policyName");
            f.setParam("policyName", "%" +policyName + "%");
        }
        if (policyCode != null && !"".equals(policyCode)) {
            f.append(" and bean.policy.policyNumber like :policyCode");
            f.setParam("policyCode", "%" +policyCode + "%");
        }
        f.append(" order by bean.applyId desc");
        f.setCacheable(true);
        return find(f, pageNo, pageSize);
    }
    @Override
    protected Class<SIcloudApply> getEntityClass() {
        return SIcloudApply.class;
    }
}
