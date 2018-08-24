package com.anchorcms.icloud.dao.common.impl;

import com.anchorcms.common.hibernate.Finder;
import com.anchorcms.common.hibernate.HibernateBaseDao;
import com.anchorcms.icloud.dao.common.PictureFirstpageDao;
import com.anchorcms.icloud.model.commservice.PictureFistpage;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ${耿文龙} on 2017/2/10.
 */
@Repository
public class PictureFirstpageDaoImpl extends HibernateBaseDao<PictureFistpage,Integer> implements PictureFirstpageDao {
    /**
     * @author gengwenlong
     * @Date 2017/2/10 13:54
     * @return
     * 自定义标签-图片轮播
     */
    public List<PictureFistpage> getPictureFistpageList(Integer count, Integer orderBy, Integer listType) {
        Finder f = Finder.create(" select  bean from PictureFistpage bean  ");
        if(orderBy!=null){
            f.append("  ");
        }
        if(count!=null){
            f.setMaxResults(count);
        }
        return find(f);
    }
    @Override
    protected Class<PictureFistpage> getEntityClass() {
        return PictureFistpage.class;
    }
}
