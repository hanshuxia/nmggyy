package com.anchorcms.icloud.dao.software.impl;

import com.anchorcms.common.hibernate.Finder;
import com.anchorcms.common.hibernate.HibernateBaseDao;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.dao.software.SoftwareDao;
import com.anchorcms.icloud.model.software.SSoftware;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by ly on 2017/1/4.
 */
@Repository
public class SoftwareDaoImpl extends HibernateBaseDao<SSoftware, Integer> implements SoftwareDao {

    public Pagination getPage(Integer siteId, CmsUser user, int pageNo, int pageSize,
                              String softwareType, String softwareName, String status) {
        Finder f = Finder.create("select  bean from SSoftware bean");
        f.append(" where 1 = 1");
        if (status != null && !"".equals(status)){//全部
            f.append(" and bean.status =:status");
            f.setParam("status", status);
        }
        if (softwareType != null && !"".equals(softwareType)) {
            f.append(" and bean.softwareType like :softwareType");
            f.setParam("softwareType", "%" +softwareType + "%");
        }
        if (softwareName != null && !"".equals(softwareName)) {
            f.append(" and bean.softwareName like :softwareName");
            f.setParam("softwareName", "%" + softwareName + "%");
        }
        f.append(" order by bean.updateDt desc,bean.softwareId desc");
        f.setCacheable(true);
        return find(f, pageNo, pageSize);
    }

    /**
     * @author ly
     * @date 2017/1/5 16:48
     * @desc
     **/
    public Pagination getSitePage(Integer[] siteIds, Integer[] typeIds, Boolean titleImg, Boolean recommend, String title, Map<String, String[]> attr, int orderBy, int pageNo, int pageSize, String[] params) {
        Finder f = Finder.create("select bean from SSoftware bean  ");
        f.append( " where bean.status = '1'");
        if(params.length>0){
            if((params[0].trim()).length()>0 && !("undefined".equals(params[0])) ){
                String payType = "%%";
                if(params[0].equals("qb")){
                    payType="%%";
                } if(params[0].equals("mf")){
                    payType="免费";
                } if(params[0].equals("ff")){
                    payType="付费";
                }if(params[0].equals("zl")){
                    payType="租赁";
                }
                f.append(" and bean.payType like:payType");
                f.setParam("payType","%" + payType + "%");
            }
            if((params[1].trim()).length()>0 && !("undefined".equals(params[1])) ){
                String softwareType = "%%";
                if(params[1].equals("qb")){
                    softwareType="%%";
                } if(params[1].equals("zzgcs")){
                    softwareType="制造工程师";
                } if(params[1].equals("twd")){
                    softwareType="图文档";
                } if(params[1].equals("stsj")){
                    softwareType="实体设计";
                } if(params[1].equals("gytb")){
                    softwareType="工艺图表";
                } if(params[1].equals("skc")){
                    softwareType="数控车";
                } if(params[1].equals("dztb")){
                    softwareType="电子图版";
                } if(params[1].equals("xqg")){
                    softwareType="线切割";
                } if(params[1].equals("qt")) {
                    softwareType = "其他";
                }
                f.append(" and bean.softwareType like:softwareType");
                f.setParam("softwareType","%" + softwareType + "%");
            }
            if((params[2].trim()).length()>0 && !("undefined".equals(params[2])) ){
                orderBy = Integer.parseInt(params[2]);
            }
        }
        switch (orderBy) {
            case 1:
                // 更新时间降序
                f.append(" order by bean.updateDt desc");
                break;
            case 2:
                // 更新时间升序
                f.append(" order by bean.content.contentCount.downloads desc");
                break;

            default:
                // 默认：更新时间降序
                f.append(" order by bean.updateDt desc");
        }

        f.setCacheable(true);
        int totalCount = countQueryResult(f);
        Pagination p = new Pagination(pageNo, pageSize, totalCount);
        if (totalCount < 1) {
            p.setList(new ArrayList());
            return p;
        }
        Query query = getSession().createQuery(f.getOrigHql());
        f.setParamsToQuery(query);
        query.setFirstResult(p.getFirstResult());
        query.setMaxResults(p.getPageSize());
        if (f.isCacheable()) {
            query.setCacheable(true);
        }
        List list = query.list();
        p.setList(list);
        //当存在查询条件的情况下把查询条件返回前台提供下次查询使用
        /**
         *把查询条件返回前台
         */
        if(params.length>0) {
            if(params[0].length() > 0){
                p.setSearchOne(params[0]);
            }
            if(params[1].length() > 0){
                p.setSearchTwo(params[1]);
            }
            if(params[2].length() > 0){
                p.setSearchThree(params[2]);
            }
        }
        return p;
    }

    public SSoftware save(SSoftware bean) {
        getSession().save(bean);
        return bean;
    }

    public SSoftware update(SSoftware bean) {
        getSession().update(bean);
        return bean;
    }

    public SSoftware findById(Integer id) {
        SSoftware bean = get(id);
        return bean;
    }

    public SSoftware deleteById(Integer id) {
        SSoftware bean = findById(id);
        getSession().delete(bean);
        return bean;
    }
    /**
     * @author jsz
     * @date 2017/1/10
     * @desc 自定义标签——软件列表
     **/
    public List<SSoftware> getList(Integer count, Integer orderBy, Integer listType,Integer listId) {
        Finder f = Finder.create("select  bean from SSoftware bean where bean.status = 1");
        if (0==listType) {//全部
            f.append(" and 1=1");
        }else if (1==listType) {//免费
            f.append(" and bean.payType =:payType");
            f.setParam("payType", "免费");
        }else if (2==listType) {//付费(购买+租赁)
            f.append(" and (bean.payType =:FuFei or bean.payType =:ZuLin)");
            f.setParam("FuFei", "付费");
            f.setParam("ZuLin", "租赁");
        }
        if (listId != null) {
            f.append(" and bean.softwareId !=:softwareId");
            f.setParam("softwareId", listId);
        }
        if (orderBy !=null){
            if (1==orderBy) {//创建时间
                f.append(" order by bean.createDt desc");
            }else if (2==orderBy) {//下载次数
                f.append(" order by content.downloadsDay desc");
            }
        }
        if (count != null) {
            f.setMaxResults(count);
        }
        return find(f);
    }


    /**
     * @Author 闫浩芮
     * 批量发布
     * @Date 2017/2/17 0017 18:45
     */
    public int passMany(String demandIdsStr) {
        java.sql.Date now = new java.sql.Date(System.currentTimeMillis());
        Query query = getSession().createQuery("update SSoftware bean set bean.status='1', bean.updateDt=(:updateDt)" +
                "where bean.softwareId in (" + demandIdsStr + ")").setParameter("updateDt",now);
        return query.executeUpdate();
    }
    /**
     * @Author 闫浩芮
     *批量下架
     * @Date 2017/2/17 0017 18:45
     */
    public int rejectMany(String demandIdsStr) {
        Query query = getSession().createQuery("update SSoftware bean set bean.status='2' " +
                "where bean.softwareId in (" + demandIdsStr + ")");
        return query.executeUpdate();
    }
    /**
     * @Author 闫浩芮
     * @Date 2017/2/20 0020 10:37
     * 批量删除
     */
    public int deleteMany(String demandIdsStr) {
        Query query = getSession().createQuery("delete SSoftware bean where bean.softwareId in (" + demandIdsStr + ")");
        return query.executeUpdate();
    }

    @Override
    protected Class<SSoftware> getEntityClass() {
        return SSoftware.class;
    }
}
