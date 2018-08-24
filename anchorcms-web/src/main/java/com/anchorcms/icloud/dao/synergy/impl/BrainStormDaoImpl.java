package com.anchorcms.icloud.dao.synergy.impl;
import com.anchorcms.cms.model.main.Content;
import com.anchorcms.common.hibernate.Finder;
import com.anchorcms.common.hibernate.HibernateBaseDao;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.icloud.dao.synergy.BrainStormDao;
import org.springframework.stereotype.Repository;

/**
 * @anther maocheng
 * @descript
 * @data 2017/2/10
 */

@Repository
public class BrainStormDaoImpl extends HibernateBaseDao<Content, Integer> implements BrainStormDao {

    /**
     * @param id
     * @Author maocheng
     * @Date 2017/2/14
     * @Desc 后台-互动专区--获取实体类
     */
    public Content byContentId(Integer id) {
        Content bean = get(id);
        return bean;
    }

    /***
     * @Author maocheng
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
     * @param author
     * @param title
     * @param status
     * @Desc 后台-头脑风暴--列表
     * @return
     */
    public Pagination getPageBySelf(Object o, boolean b, boolean b1, Content.ContentStatus all, Object o1, Integer siteId, Integer channelId, int pageNo, int pageSize, String author, String title,Byte status) {
        Finder f = Finder.create("select bean from Content bean ");
        f.append(" where channel_id =:channelId");
        f.setParam("channelId", channelId);
        //添加查询条件
        appendQuery(f,status, author,title);
        f.append(" order by bean.contentId desc");
        return find(f, pageNo, pageSize);
    }
    private void appendQuery(Finder f, Byte status, String author, String title) {
        if (status != null && !"".equals(status)){//全部
            f.append(" and bean.status =:status");
            f.setParam("status", status);
        }
        if (author != null && !"".equals(author)) {
            f.append(" and user.company.companyName like :author");
            f.setParam("author", "%"+author+"%");
        }
        if (title != null && !"".equals(title)){
            f.append(" and bean.contentExt.title like:title");
            f.setParam("title","%"+title+"%");
        }
    }

    @Override
    protected Class<Content> getEntityClass() {
        return Content.class;
    }
}
