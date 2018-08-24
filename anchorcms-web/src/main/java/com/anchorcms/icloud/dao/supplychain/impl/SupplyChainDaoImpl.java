package com.anchorcms.icloud.dao.supplychain.impl;

import com.anchorcms.common.hibernate.HibernateBaseDao;
import com.anchorcms.icloud.dao.supplychain.SupplyChainDao;
import com.anchorcms.icloud.model.supplychain.SRepairRes;
import com.anchorcms.icloud.model.supplychain.SSpare;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by DELL on 2017/1/3.
 */
@Repository
public class SupplyChainDaoImpl extends HibernateBaseDao<SSpare, Integer> implements SupplyChainDao {
    public List<SSpare> getAll(){
        String hql = " from SSpare ";
        Query query = getSession().createQuery(hql);
        List<SSpare> list = query.list();
        return list;
    }
    public List<SRepairRes> getAl() {
        String hql = " from SRepairRes ";
        Query query = getSession().createQuery(hql);
        List<SRepairRes> list = query.list();
        return list;

    }
    public List<SRepairRes> getAl(String name, String place, String type){
        StringBuffer hql = new StringBuffer(" from SRepairRes s where 1=1 ");
        if(name!=null){
            hql.append(" and s.repairName like '%"+name+"%'");
        }
        if(place!=null){
            hql.append(" and s.addrCity like '%"+place+"%'");
        }if(type!=null){
            hql.append(" and s.status = '"+type+"'");
        }
        Query query = getSession().createQuery(hql.toString());
        List<SRepairRes> list = query.list();
        return list;
    }
    public int delSupply(String delNum){
        delNum = delNum.replace(",","','");
        String hql=" delete  from  SRepairRes s where s.repairId in ('"+delNum+"')";
        return getSession().createQuery(hql).executeUpdate();
    }

    public List<SRepairRes> getAl(String uid){
        String hql = " from SRepairRes s where s.repairId = ('"+uid+"')";
        Query query = getSession().createQuery(hql);
        List<SRepairRes> list = query.list();
        return list;
    }

    public int updateSupply(String uid) {
        String hql="update SRepairRes s set s.status =1 where s.repairId =('"+uid+"')";
        return getSession().createQuery(hql).executeUpdate();
    }

    @Override
    protected Class<SSpare> getEntityClass() {
        return SSpare.class;
    }
}
