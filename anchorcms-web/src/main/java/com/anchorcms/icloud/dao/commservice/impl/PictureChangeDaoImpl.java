package com.anchorcms.icloud.dao.commservice.impl;

import com.anchorcms.common.hibernate.HibernateBaseDao;
import com.anchorcms.icloud.dao.commservice.PictureChangeDao;
import com.anchorcms.icloud.model.commservice.PictureFistpage;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by DELL on 2017/2/10.
 */
@Repository
public class PictureChangeDaoImpl extends HibernateBaseDao<PictureFistpage,Integer> implements PictureChangeDao {

    @Override
    protected Class<PictureFistpage> getEntityClass() {
        return PictureFistpage.class;
    }

    public PictureFistpage save(PictureFistpage pictureFistpage) {
            getSession().save(pictureFistpage);
        return pictureFistpage;
    }
    public int update(String imgPath, Date createDate,String nwebpath, int picId,int flag){
        StringBuffer hql = new StringBuffer("update PictureFistpage s set ");
        hql.append(" s.imgPath='" + imgPath + "'");
        hql.append(",s.createDate='" + createDate + "'");
        hql.append(",s.nwebpath='" + nwebpath + "'");
        hql.append(",s.status='"+flag+"'");
        hql.append(" where s.picId='" + picId + "'");
        return getSession().createQuery(hql.toString()).executeUpdate();
    }

    public List<PictureFistpage> get() {
        String hql="from PictureFistpage";
        Query query=getSession().createQuery(hql);
        List<PictureFistpage> list=query.list();
        return list;
    }
}

