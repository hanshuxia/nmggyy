package com.anchorcms.icloud.dao.supplychain.impl;

import com.anchorcms.common.hibernate.HibernateBaseDao;
import com.anchorcms.icloud.dao.supplychain.SpareDemandObjDao;
import com.anchorcms.icloud.model.supplychain.SSpareDemandObj;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
*@Author 潘晓东
*@Date 2017/1/23 13:50
*@Return备品备件求购对象
*/
@Repository
public class SpareDemanDaoObjImpl extends
        HibernateBaseDao<SSpareDemandObj, String> implements SpareDemandObjDao {


    protected Class<SSpareDemandObj> getEntityClass() {
        return SSpareDemandObj.class;
    }
    /**
    *@Author 潘晓东 
    *@Date 2017/1/10 19:21
    *@Return根据备品备件求购ID查询备品备件求购对象ID
    */
    public List<SSpareDemandObj> selectSSpareDemandObjEntity(String demandId) {
        String hql = " from SSpareDemandObj sp where sp.demandId= '"+demandId+"' ";
        List<SSpareDemandObj> list = getSession().createQuery(hql).list();
        return list;
    }
}
