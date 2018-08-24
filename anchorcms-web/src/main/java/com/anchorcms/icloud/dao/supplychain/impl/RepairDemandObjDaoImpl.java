package com.anchorcms.icloud.dao.supplychain.impl;

import com.anchorcms.common.hibernate.HibernateBaseDao;
import com.anchorcms.icloud.dao.supplychain.RepairDemandObjDao;
import com.anchorcms.icloud.model.supplychain.SRepairDemandObj;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
*@Author 潘晓东
*@Date 2017/1/23 13:38
*@Return维修需求对象
*/
@Repository
public class RepairDemandObjDaoImpl extends HibernateBaseDao<SRepairDemandObj,String> implements RepairDemandObjDao {
    protected Class<SRepairDemandObj> getEntityClass() {
        return SRepairDemandObj.class;
    }

    /**
     *@Author 潘晓东
     *@Date 2017/1/23 10:03
     *@Return根据ID获取维修需求对象表中的数据
     */
    public List<SRepairDemandObj> selectRepairDemand(String id) {
        String hql = " from SRepairDemandObj s where s.repairId='"+id+"'";
        List<SRepairDemandObj> sRepairDemandObjList = getSession().createQuery(hql).list();
        return sRepairDemandObjList;
    }

    /**
     * Created by  hansx
     * 保存
     * @param sRepairDemandObj
     * @return
     */
    public SRepairDemandObj save(SRepairDemandObj sRepairDemandObj) {
        this.getSession().save(sRepairDemandObj);
        return sRepairDemandObj;
    }
}
