package com.anchorcms.icloud.dao.common.impl;

import com.anchorcms.common.hibernate.Finder;
import com.anchorcms.common.hibernate.HibernateBaseDao;
import com.anchorcms.icloud.dao.common.SMemberAddrDao;
import com.anchorcms.icloud.model.common.SMemberAddress;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zhouyq
 * @Date 2017/8/12
 * @return
 * 联系人信息dao实现类
 */
@Repository
public class SMemberAddrDaoImpl extends HibernateBaseDao<SMemberAddress, Integer> implements SMemberAddrDao {
    /**
     * @Author zhouyq
     * @Date 2017/8/12
     * @param
     * @return
     * @Desc 获取联系人信息
     */
    public SMemberAddress getContactInfo(Integer userId){
        SMemberAddress sMemberAddress = get(userId);
        return sMemberAddress;
    }

    /**
     * @Author zhouyq
     * @Date 2016/8/13
     * @Desc 获取联系人信息list
     */
    public List<SMemberAddress> getContactJsonList(Integer userId){
        Finder finder = Finder.create("select bean from SMemberAddress bean where bean.userId=:userId");
        finder.setParam("userId", userId);
        finder.setCacheable(true);
        Query query = getSession().createQuery(finder.getOrigHql());
        finder.setParamsToQuery(query);
        List<SMemberAddress> list = query.list();
        return list;
    }

    /**
     * @Author zhouyq
     * @Date 2017/8/13
     * @param
     * @return
     * @Desc 新增保存联系人信息
     */
    public SMemberAddress saveContactInfo(SMemberAddress sMemberAddress, Integer userId, Integer defAddr, Integer addrId){
        if (defAddr == 1) {
            updateContactNoDef(userId, addrId);
        }
        getSession().save(sMemberAddress);
        getSession().flush();
        return sMemberAddress;
    }

    /**
     * @Author zhouyq
     * @Date 2017/8/15
     * @param
     * @return
     * @Desc 更新addrId数据为默认地址
     */
    public int updateContactNoDef( Integer userId, Integer addrId){
        StringBuffer hql = new StringBuffer("update SMemberAddress s set ");
        hql.append(" s.defAddr = 0");
        hql.append(" where s.userId='" + userId + "'" + "and s.addrId<>" + addrId);
        return getSession().createQuery(hql.toString()).executeUpdate();
    }

    public SMemberAddress findById(Integer addrId) {
        SMemberAddress bean = get(addrId);
        return bean;
    }

    protected Class<SMemberAddress> getEntityClass() {
        return SMemberAddress.class;
    }
}
