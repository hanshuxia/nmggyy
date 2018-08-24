package com.anchorcms.icloud.dao.commservice.impl;

import com.anchorcms.common.hibernate.Finder;
import com.anchorcms.common.hibernate.HibernateBaseDao;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.icloud.dao.commservice.SiteManagerDao;
import com.anchorcms.icloud.model.commservice.SStiteManager;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author zhouyq
 * @Date 2017/01/14
 * @Desc 政府网站Dao层实现类
 */
@Repository
public class SiteManagerDaoImpl extends HibernateBaseDao<SStiteManager, String> implements SiteManagerDao {

    /**
     * @param siteName, pageNo, pageSize
     * @return
     * @Author zhouyq
     * @Date 2017/01/14
     * @Desc 政府网站分页后信息
     */
    public Pagination getPage(String siteName, String addrProvince, String address, String status, Integer pageNo, Integer pageSize) {
        Finder f = getFinder(addrProvince, siteName, address, status);
        f.setCacheable(true);
        return find(f, pageNo, pageSize);
    }

    /**
     * @param stiteName, address
     * @return
     * @Author zhouyq
     * @Date 2017/01/14
     * @Desc 政府网站列表信息
     */
    private Finder getFinder(String addrProvince, String stiteName, String address,String status) {
        Finder f = Finder.create("from SStiteManager bean where  1 = 1");
        if (addrProvince != null && addrProvince.trim().length() > 0) { // 省（区）
            f.append(" and bean.addrProvince like:addrProvince");
            f.setParam("addrProvince", "%" + addrProvince + "%");
        }
        if (address != null && address.trim().length() > 0) { // 地区
            f.append(" and bean.address like:address");
            f.setParam("address", "%" + address + "%");
        }
        if (stiteName != null && stiteName.trim().length() > 0) { // 网站名称
            f.append(" and bean.stiteName like:stiteName");
            f.setParam("stiteName", "%" + stiteName + "%");
        }

        if (status != null && status.trim().length() > 0) { // 状态
            f.append(" and bean.status =:status");
            f.setParam("status",status);
        }
        f.append(" order by bean.updateDt desc"); // 按更新日期降序
        return f;
    }

    /**
     * @param stiteId
     * @return
     * @Author zhouyq
     * @Date 2017/01/14
     * @Desc 根据id删除政府网站记录
     */
    public void delSiteById(String stiteId) {
        StringBuffer hql = new StringBuffer();
        hql.append("delete from SStiteManager sd where sd.stiteId = '" + stiteId + "' ");
        Query query = getSession().createQuery(hql.toString());
        query.executeUpdate(); // 必须加此句否则不会执行删除操作
    }

    /**
     * @param stiteId
     * @return
     * @author zhouyq
     * @Date 2017/1/14
     * @description 获取政府网站实体类的信息
     */
    public SStiteManager getSiteManagerEntity(String stiteId) {
        SStiteManager entitiy = get(stiteId);
        return entitiy;
    }

    /**
     * @param sSiteManager
     * @return
     * @author zhouyq
     * @Date 2017/1/14
     * @description 政府网站新增保存
     */
    public void siteAddSave(SStiteManager sSiteManager) {
        getSession().save(sSiteManager);
    }

    /**
     * @param sSiteManager
     * @return
     * @author zhouyq
     * @Date 2017/1/14
     * @description 政府网站修改保存
     */
    public int siteMdySave(SStiteManager sSiteManager) {
        StringBuffer hql = new StringBuffer("update SStiteManager s set ");
        hql.append(" s.stiteName='" + sSiteManager.getStiteName() + "'");
        hql.append(",s.stiteAddress='" + sSiteManager.getStiteAddress() + "'");
        hql.append(",s.address='" + sSiteManager.getAddress() + "'");
        hql.append(",s.updateDt='" + sSiteManager.getUpdateDt() + "'");
        hql.append(" where s.stiteId='" + sSiteManager.getStiteId() + "'");
        return getSession().createQuery(hql.toString()).executeUpdate();
    }

    /**
     * @author dongxuecheng
     * @Date 2017/1/20 10:57
     * @return
     * @description设置发布与撤回的状态
     */
    public int setstatus(String stiteId, String status) {
        String hql="";
        if(status.equals("1")){
          hql="update SStiteManager s set s.status =1 where s.stiteId =('"+stiteId+"')";

        }else if(status.equals("3")){
            hql="update SStiteManager s set s.status =3 where s.stiteId =('"+stiteId+"')";
        }
        return getSession().createQuery(hql).executeUpdate();
    }

    protected Class<SStiteManager> getEntityClass() {
        return SStiteManager.class;
    }
}
