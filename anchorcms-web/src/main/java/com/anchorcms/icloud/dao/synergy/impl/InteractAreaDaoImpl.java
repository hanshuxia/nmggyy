package com.anchorcms.icloud.dao.synergy.impl;

import com.anchorcms.cms.model.main.Content;
import com.anchorcms.common.hibernate.Finder;
import com.anchorcms.common.hibernate.HibernateBaseDao;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.icloud.dao.synergy.InteractAreaDao;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @Author wanjinyou
 * @Date 2017/2/10
 * @Desc 互动专区
 */
@Repository
public class InteractAreaDaoImpl extends HibernateBaseDao<Content, Integer> implements InteractAreaDao {


    /**
     * @param id 主键id
     * @Author wanjinyou
     * @Date 2017/2/9
     * @Desc 后台-互动专区--获取实体类
     */
    public Content byContentId(Integer id) {
        Content bean = get(id);
        return bean;
    }

    public List<Content> byContentIds(String ids) {
        Query query = getSession().createQuery("SELECT bean from Content bean where  bean.contentId in ("+ ids +") ");
        List<Content> contents = query.list();
        return contents;
    }

    /**
     * @Author wanjinyou
     * @Date 2017/2/9
     * @param o
     * @param b
     * @param b1
     * @param all
     * @param o1
     * @param siteId 站点id
     * @param channelId 栏目id
     * @param pageNo 页码
     * @param pageSize 页码数
     * @param companyName 公司名称
     * @param title
     * @param status
     * @Desc 后台-互动专区--列表
     */
    public Pagination getPageBySelf(Object o, boolean b, boolean b1, Content.ContentStatus all, Object o1, Integer siteId, Integer channelId, int pageNo, int pageSize, Date startDate, Date endDate, String companyName, String title,Byte status) {
            Finder f = Finder.create("select bean from Content bean ");
            f.append(" where channel_id =:channelId");
            f.setParam("channelId", channelId);
            //添加查询条件
            appendQuery(f,status, companyName,title,startDate,endDate);
            f.append(" order by bean.contentId desc");
            return find(f, pageNo, pageSize);
        }
    private void appendQuery(Finder f, Byte status, String companyName, String title,Date startDate, Date endDate) {
        if (status != null && !"".equals(status)){//全部
                f.append(" and bean.status =:status");
                f.setParam("status", status);
         }
        if (companyName != null && !"".equals(companyName)) {
            f.append(" and user.company.companyName like :companyName");
            f.setParam("companyName", "%"+companyName+"%");
        }
        if (title != null && !"".equals(title)){
            f.append(" and bean.contentExt.title like:title");
            f.setParam("title","%"+title+"%");
        }
    }


    @Override
    protected Class<Content> getEntityClass() { return Content.class;}
}