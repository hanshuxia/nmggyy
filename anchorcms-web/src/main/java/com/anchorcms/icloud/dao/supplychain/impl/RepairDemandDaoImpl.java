package com.anchorcms.icloud.dao.supplychain.impl;

import com.anchorcms.common.hibernate.HibernateBaseDao;
import com.anchorcms.icloud.dao.supplychain.RepairDemandDao;
import com.anchorcms.icloud.model.supplychain.SRepairDemand;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 潘晓东 on 2016/12/26.
 * 众修需求
 */
@Repository
public class RepairDemandDaoImpl extends HibernateBaseDao<SRepairDemand, String> implements RepairDemandDao {
    protected Class<SRepairDemand> getEntityClass() {
        return SRepairDemand.class;
    }

    /**
     * @Author 潘晓东
     * @Date 2017/1/5 11:40
     * @Return根据ID查询
     */
    public SRepairDemand selectReapirDemand(String id) {
        SRepairDemand sRepairDemand = get(id);
        return sRepairDemand;
    }

    /**
     * @return 根据ID查询维修需求信息
     * @author gengwenlong
     * @Date 2017/1/11 20:38
     */
    public List<SRepairDemand> selectReapirDemandById(String id) {
        String hql = "from SRepairDemand where repairId=" + id;
        List<SRepairDemand> list = getSession().createQuery(hql).list();
        return list;
    }

   /**
    * @author hansx
    * @Date 2017/1/24 0024 上午 10:51
    * @return 维修需求信息保存
    *
    */
    public SRepairDemand save(SRepairDemand sRepairDemand) {
        this.getSession().save(sRepairDemand);
        return sRepairDemand;
    }

}
