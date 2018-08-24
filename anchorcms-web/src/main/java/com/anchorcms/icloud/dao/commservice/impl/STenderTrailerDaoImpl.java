package com.anchorcms.icloud.dao.commservice.impl;


import com.anchorcms.common.hibernate.HibernateBaseDao;
import com.anchorcms.icloud.dao.commservice.STenderTrailerDao;
import com.anchorcms.icloud.model.commservice.STenderTrailer;
import org.springframework.stereotype.Repository;

/**
 * Created by hansx on 2017/1/13 0013.
 * 招标预告实现类
 */
@Repository
public class STenderTrailerDaoImpl extends HibernateBaseDao<STenderTrailer, Integer> implements STenderTrailerDao {


    public STenderTrailer findById(int id) {
        STenderTrailer entity = get(id);
        return entity;
    }
/**
 * @author gengwenlong
 * @Date 2017/1/14 21:23
 * @return
 * 发布招标预告保存
 */
    public STenderTrailer relese(STenderTrailer stt) {
        getSession().save(stt);
        return stt;
    }


    @Override
    protected Class<STenderTrailer> getEntityClass() {
        return STenderTrailer.class;
    }
}