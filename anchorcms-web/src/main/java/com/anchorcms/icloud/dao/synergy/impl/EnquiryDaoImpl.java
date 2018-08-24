package com.anchorcms.icloud.dao.synergy.impl;

import com.anchorcms.common.hibernate.HibernateBaseDao;
import com.anchorcms.icloud.dao.synergy.EnquiryDao;
import com.anchorcms.icloud.model.synergy.SAbility;
import com.anchorcms.icloud.model.synergy.SAbilityInquiry;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 * @Author wanjinyou
 * @Email netuser.orz@icloud.com
 * @Date 2016/12/26
 * @Desc 我要询价
 */
@Repository
public class EnquiryDaoImpl extends HibernateBaseDao<SAbilityInquiry, Integer>implements EnquiryDao {

    /**
     * @Author wanjinyou
     * @Email netuser.orz@icloud.com
     * @Date 2016/12/26
     * @param id 能力主键
     * @Desc 我要询价
     */
    public SAbility bySAbilityId(Integer id) {
        Query query = getSession().createQuery("SELECT bean from SAbility bean where bean.abilityId=:abilityId")
                .setParameter("abilityId", id);
        SAbility sabilty = (SAbility) query.uniqueResult();
        return sabilty;
    }
    /**
     * @Author wanjinyou
     * @Email netuser.orz@icloud.com
     * @Date 2016/12/28
     * @Desc 我要询价保存
     */
    public SAbilityInquiry save(SAbilityInquiry bean) {
        getSession().save(bean);
        return bean;
    }

    @Override
    protected Class<SAbilityInquiry> getEntityClass() {
        return SAbilityInquiry.class;
    }
}
