package com.anchorcms.icloud.dao.supplychain.impl;

import com.anchorcms.common.hibernate.HibernateBaseDao;
import com.anchorcms.icloud.dao.supplychain.RepairAbilityDao;
import com.anchorcms.icloud.model.supplychain.SRepairAbility;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
*@Author 潘晓东
*@Date 2017/1/23 13:41
*@Return抢单报价对象表的数据获取
*/
@Repository
public class RepairAbilityDaoImpl extends HibernateBaseDao<SRepairAbility,String> implements RepairAbilityDao {
    protected Class<SRepairAbility> getEntityClass() {
        return SRepairAbility.class;
    }
    /**
    *@Author 潘晓东
    *@Date 2017/1/23 13:42
    *@Return根据抢单报价ID获取抢单报价对象表的数据
    */
    public List<SRepairAbility> selectByQuoteId(String id) {
        String hql = "from SRepairAbility where demandRequestId='"+id+"'";
        List<SRepairAbility> list = getSession().createQuery(hql).list();
        return list;
    }
}
