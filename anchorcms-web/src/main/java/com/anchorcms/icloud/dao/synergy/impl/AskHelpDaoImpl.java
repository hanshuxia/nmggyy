package com.anchorcms.icloud.dao.synergy.impl;

import com.anchorcms.cms.model.main.Content;
import com.anchorcms.common.hibernate.Finder;
import com.anchorcms.common.hibernate.HibernateBaseDao;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.icloud.dao.synergy.AskHelpDao;
import com.anchorcms.icloud.model.synergy.SAbility;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @Author Yhr
 * @Date 2017/2/13 0013 11:14
 */
@Repository
public class AskHelpDaoImpl extends HibernateBaseDao<Content, Integer> implements AskHelpDao {
    public Pagination getPageBySelf(Object o, boolean b, boolean b1, Content.ContentStatus all, Object o1, Integer siteId,
                                    Integer channelId, int pageNo, int pageSize, Date startDate, Date endDate,
                                    String author, String title,Byte status) {
        Finder f = Finder.create("select bean from Content bean ");
        f.append("where channel_id = 122");
        //添加查询条件
        appendQuery(f,status, author,title,startDate,endDate);
        f.append(" order by bean.contentId desc");
        f.setCacheable(true);
        return find(f, pageNo, pageSize);
    }



    private void appendQuery(Finder f, Byte status, String author, String title,Date startDate, Date endDate) {
        if (status != null && !"".equals(status)){//全部
            f.append(" and bean.status =:status");
            f.setParam("status", status);
        }
        if (author != null && !"".equals(author)) {
            f.append(" and bean.contentExt.author like :author");
            f.setParam("author", "%"+author+"%");
        }
        if (title != null && !"".equals(title)){
            f.append(" and bean.contentExt.title like:title");
            f.setParam("title","%"+title+"%");
        }
        if (startDate != null) {
            f.append(" and date_format(bean.contentExt.releaseDt,'%Y-%m-%d')  >=:startDate");
            f.setParam("startDate", startDate);
        }
        if (endDate != null) {
            f.append(" and bean.releaseDate.toDate() <=:endDate");
            f.setParam("endDate", endDate);
        }
    }
    public Content findById(Integer contentId) {
        Content content =get(contentId);
        return content;
    }
    @Override
    protected Class<Content> getEntityClass() { return Content.class;}

    /**
     * @Author 闫浩芮
     * 置为通过
     * @Date 2017/2/17 0017 18:45
     */
    public int passMany(String demandIdsStr) {
        java.sql.Date now = new java.sql.Date(System.currentTimeMillis());
        Query query = getSession().createQuery("update Content bean set bean.status='3', bean.releaseDt=(:releaseDt)" +
                "where bean.demandId in (" + demandIdsStr + ")").setParameter("releaseDt",now);
        return query.executeUpdate();
    }
    /**
     * @Author 闫浩芮
     * 批量删除
     * @Date 2017/2/17 0017 18:45
     */
    public int deleteMany(String demandIdsStr) {
        Query query = getSession().createQuery("delete SDemand bean set bean.status='0' " +
                "where bean.demandId in (" + demandIdsStr + ")");
        return query.executeUpdate();
    }

    public List<Content> byContentIds(String ids) {
        Query query = getSession().createQuery("SELECT bean from Content bean where  bean.contentId in ("+ ids +") ");
        List<Content> sContentList = query.list();
        return sContentList;
    }
}
