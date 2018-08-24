package com.anchorcms.icloud.dao.commservice.impl;

import com.anchorcms.cms.model.main.Content;
import com.anchorcms.common.hibernate.Finder;
import com.anchorcms.common.hibernate.HibernateBaseDao;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.icloud.dao.commservice.LDTenderNoticesManageDao;
import com.anchorcms.icloud.model.commservice.SBidNotice;
import com.anchorcms.icloud.model.commservice.STenderNotice;
import com.anchorcms.icloud.model.commservice.STenderTrailer;
import org.springframework.stereotype.Repository;

/**
 * DESCRIPTION:f
 * Author: DELL
 * Date:2017/1/13.
 */
@Repository
public class LDTenderNoticesManageDaoImpl extends HibernateBaseDao implements LDTenderNoticesManageDao {
    /**
     * @author ldong
     * @Date 2017/1/23 10:25
     * @return
     * 获取招标公告管理的sql
     */

    public Pagination getTenderNoticesList(String title, Integer typeId, Integer currUserId, Integer inputUserId,
                                           boolean topLevel, boolean recommend, Content.ContentStatus status,
                                           Byte checkStep, Integer siteId, Integer modelId, Integer channelId,
                                           int orderBy, int pageNo, int pageSize, String state) {
        Finder f = null;
        if (state == null||"".equals(state)) {
            state = "1";
        }
        //管理员权限无限制，可以查询所有数据。
        if ("1".equals(state)) {
            f = Finder.create("select  bean from STenderTrailer bean where 1=1 ");
        } else if ("2".equals(state)) {
            f = Finder.create("select  bean from STenderNotice bean where 1=1 ");
        } else {
            f = Finder.create("select  bean from SBidNotice bean where 1=1 ");
        }
        if (title != null && !"".equals(title)) {
            f.append(" and bean.projectName like :title ").setParam("title", "%" + title + "%");
        }
        f.append(" order by bean desc");
        f.setCacheable(true);
        return find(f, pageNo, pageSize);
    }
/**
 * @author ld
 * @Date 2017/1/23 10:24
 * @return 招标预告表  删除  保存   获取
 */

    public void deleteTenderTrailer(STenderTrailer bean) {
       getSession().delete(bean);
    }

    public void saveOrUpdateTenderTrailer(STenderTrailer bean) {
        getSession().saveOrUpdate(bean);
    }

    public STenderTrailer getTenderTrailerById(int id) {
     return (STenderTrailer)getSession().get(STenderTrailer.class,id);
    }


/**
 * @author ldong
 * @Date 2017/1/23 10:25
 * @return
 * 招标公告表 删除  保存
 */

    public void deleteTenderNotice(STenderNotice bean) {
        getSession().delete(bean);
    }

    public void saveOrUpdateTenderNotice(STenderNotice bean) {
        getSession().saveOrUpdate(bean);
    }

    public STenderNotice getSTenderNoticeById(int id) {
        return (STenderNotice)getSession().get(STenderNotice.class,id);
    }


/**
 * @author ldong
 * @Date 2017/1/23 10:26
 * @return
 * 中标公告管理
 */

    public void deleteBidNotice(SBidNotice bean) {
        getSession().delete(bean);
    }

    public void saveOrUpdateBidNotice(SBidNotice bean) {
        getSession().saveOrUpdate(bean);
    }

    public SBidNotice getSBidNoticeById(int id) {
        return (SBidNotice)getSession().get(SBidNotice.class,id);
    }


    protected Class getEntityClass() {
        return null;
    }
}
