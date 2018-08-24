package com.anchorcms.icloud.dao.supplychain.impl;

import com.anchorcms.common.hibernate.HibernateBaseDao;
import com.anchorcms.icloud.dao.supplychain.SRepairAbilityDao;
import com.anchorcms.icloud.model.supplychain.SRepairAbility;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by hansx on 2017/1/2
 * 维修资源报价对象信息dao实现
 */
@Repository
public class SRepairAbilityDaoImpl extends HibernateBaseDao<SRepairAbility, String> implements SRepairAbilityDao {


    public SRepairAbility findById(String id) {
        SRepairAbility entity = get(id);
        return entity;
    }

    public SRepairAbility save(SRepairAbility sRepairAbility) {
        this.getSession().save(sRepairAbility);
        return sRepairAbility;
    }
    public List<SRepairAbility> getList() {
        String hql = " from SRepairAbility  ";
        Query query = getSession().createQuery(hql);
        List<SRepairAbility> list = query.list();
        return list;
    }


    @Override
    protected Class<SRepairAbility> getEntityClass() {
        return SRepairAbility.class;
    }
}
