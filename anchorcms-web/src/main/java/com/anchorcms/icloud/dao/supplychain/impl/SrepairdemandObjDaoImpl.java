package com.anchorcms.icloud.dao.supplychain.impl;

import com.anchorcms.common.hibernate.HibernateBaseDao;
import com.anchorcms.icloud.dao.supplychain.SrepairdemandObjDao;
import com.anchorcms.icloud.model.supplychain.SRepairDemandObj;
import org.springframework.stereotype.Repository;

/**
 * @Author zhouyq
 * @Email
 * @Date 2016/12/29
 * @Desc 众修资源管理_管理员Dao层实现类
 */
@Repository
public class SrepairdemandObjDaoImpl extends HibernateBaseDao<SRepairDemandObj, String> implements SrepairdemandObjDao {

    /**
     * @Author zhouyq
     * @Date 2016/12/29
     * @param repairId
     * @return
     * @Desc 根据id返回众修资源对象实体类
     */
    public SRepairDemandObj findDetailAndPreviewByIdDemandObj(String repairId) {
        SRepairDemandObj entitiy = get(repairId);
        return entitiy;
    }

    protected Class<SRepairDemandObj> getEntityClass() {
        return SRepairDemandObj.class;
    }
}
