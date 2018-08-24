package com.anchorcms.icloud.dao.commservice.impl;


import com.anchorcms.common.hibernate.HibernateBaseDao;
import com.anchorcms.icloud.dao.commservice.STenderNoticeDao;
import com.anchorcms.icloud.model.commservice.STenderNotice;
import org.springframework.stereotype.Repository;

/**
 * Created by hansx on 2017/1/13 0013.
 * 招标公告实现类
 */
@Repository
public class STenderNoticeDaoImpl extends
        HibernateBaseDao<STenderNotice, Integer> implements STenderNoticeDao {


    public STenderNotice findById(int id) {
        STenderNotice entity = get(id);
        return entity;
    }
    /**
     * @author gengwenlong
     * @Date 2017/1/14 23:34
     * @return
     * 发布招标公告
     */
    public STenderNotice tenderNoticeRelese(STenderNotice stn) {
        getSession().save(stn);
        return stn;
    }

    @Override
    protected Class<STenderNotice> getEntityClass() {
        return STenderNotice.class;
    }
}