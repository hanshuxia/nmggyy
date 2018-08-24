package com.anchorcms.icloud.dao.supplychain.impl;

import com.anchorcms.common.hibernate.HibernateBaseDao;
import com.anchorcms.icloud.dao.supplychain.SpareDemandDao;
import com.anchorcms.icloud.model.supplychain.SSpareDemand;
import org.springframework.stereotype.Repository;

/**
*@Author 潘晓东
*@Date 2017/1/23 13:49
*@Return备品备件求购
*/
@Repository
public class SpareDemanDaoImpl  extends
        HibernateBaseDao<SSpareDemand, String> implements SpareDemandDao {
    /**
    *@Author 潘晓东
    *@Date 2017/1/23 13:49
    *@Return备品备件求购根据ID查询
    */
    public SSpareDemand selectById(String id) {
        SSpareDemand entitiy = get(id);
        return entitiy;
    }


    protected Class<SSpareDemand> getEntityClass() {
        return SSpareDemand.class;
    }
}
