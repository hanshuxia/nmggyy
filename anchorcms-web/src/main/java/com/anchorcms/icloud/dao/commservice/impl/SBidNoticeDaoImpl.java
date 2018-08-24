package com.anchorcms.icloud.dao.commservice.impl;


import com.anchorcms.common.hibernate.HibernateBaseDao;
import com.anchorcms.icloud.dao.commservice.SBidNoticeDao;
import com.anchorcms.icloud.model.commservice.SBidNotice;
import org.springframework.stereotype.Repository;

/**
 * Created by hansx on 2017/1/13 0013.
 * 中标公告实现类
 */
@Repository
public class SBidNoticeDaoImpl extends
        HibernateBaseDao<SBidNotice, Integer> implements SBidNoticeDao {


    public SBidNotice findById(int id) {
        SBidNotice entity = get(id);
        return entity;
    }

    @Override
    protected Class<SBidNotice> getEntityClass() {
        return SBidNotice.class;
    }
}