package com.anchorcms.icloud.dao.synergy.impl;

import com.anchorcms.common.hibernate.HibernateBaseDao;
import com.anchorcms.icloud.dao.synergy.SDemandQuoteObjDao;
import com.anchorcms.icloud.model.synergy.SDemandQuoteObj;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Copyright @ 2017 Shandong jxdinfo software co,ltd.
 * All right reserved.
 * Created by Gao Jiangning
 *
 * @Date 2017/2/6 0006
 * @Desc
 */
@Repository
public class SDemandQuoteObjDaoImpl extends HibernateBaseDao<SDemandQuoteObj, Integer> implements SDemandQuoteObjDao {
    /**
     * 计算需求对象的已分配数量
     *
     * @param demandObjId
     * @return 已分配量(double)
     */
    public Double getSumDistriByDemandObjId(Integer demandObjId) {
        Query query = getSession().createQuery("select sum(distributionAmount) from SDemandQuoteObj where demandObjid=:demandObjId and distributionAmount is not null ")
        .setParameter("demandObjId",demandObjId);
        List sum = query.list();
        if(sum.get(0)!=null){
            String result = sum.get(0).toString();
            return Double.parseDouble(result);
        }else {
            return new Double(0);
        }
    }

    /**
     * 获得Dao对于的实体类
     *
     * @return
     */
    @Override
    protected Class<SDemandQuoteObj> getEntityClass() {
        return SDemandQuoteObj.class;
    }
}
