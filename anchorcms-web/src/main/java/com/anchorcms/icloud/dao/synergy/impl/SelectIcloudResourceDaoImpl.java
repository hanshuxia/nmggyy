package com.anchorcms.icloud.dao.synergy.impl;

import com.anchorcms.cms.service.main.ContentQueryFreshTimeCache;
import com.anchorcms.common.hibernate.Finder;
import com.anchorcms.common.hibernate.HibernateBaseDao;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.icloud.dao.synergy.SelectIcloudResourceDao;
import com.anchorcms.icloud.model.cloudcenter.SIcloudPolicy;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by 11239 on 2016/12/21.
 */
@Repository
public class SelectIcloudResourceDaoImpl extends HibernateBaseDao<SIcloudPolicy, Integer> implements SelectIcloudResourceDao{

    public SIcloudPolicy findById(Integer id) {
        SIcloudPolicy policy = get(id);
        return policy;
    }
    /***
     * @author wanjinyou
     * @description 云资源交易中心--云计算政策页面查询
     * @param siteIds 站点id
     * @param typeIds
     * @param titleImg
     * @param recommend
     * @param title
     * @param attr
     * @param orderBy 排序
     * @param pageNo 页码
     * @param pageSize 页码大小
     * @param params 查询条件参数
     * @return
     */
    public Pagination getPageBySiteIdPolicyById(Integer[] siteIds, Integer[] typeIds, Boolean titleImg, Boolean recommend, String title, Map<String, String[]> attr, int orderBy, int pageNo, int pageSize, String[] params) {
        Finder f = Finder.create("select bean from SIcloudPolicy bean  ");
        f.append( " where 1=1 and bean.state='2'");
        if(params.length>0){
            if(params[0] != null && (params[0].trim()).length()>0 && !("undefined".equals(params[0])) ){
                String policyLevel = "%%";
                if(params[0].equals("qb")){
                    policyLevel="%%";
                } if(params[0].equals("gjj")){
                    policyLevel="国家级";
                } if(params[0].equals("sqj")){
                    policyLevel="省区级";
                } if(params[0].equals("msj")){
                    policyLevel="盟市级";
                }
                f.append(" and bean.policyLevel like :policyLevel");
                f.setParam("policyLevel","%"+policyLevel+"%");
            }
            if(params[1] != null && (params[1].trim()).length()>0 && !("undefined".equals(params[1])) ){
                String isSupport = "%%";
                if(params[1].equals("qb")){
                    isSupport="%%";
                } if(params[1].equals("shi")){
                    isSupport="是";
                } if(params[1].equals("fou")){
                    isSupport="否";
                }
                f.append(" and bean.isSupport like:isSupport");
                f.setParam("isSupport",isSupport);
            }
            if(params[2] != null && (params[2].trim()).length()>0 && !("undefined".equals(params[2])) ){
                String releaseDt = "%%";
                if(params[2].equals("qb")){
                    releaseDt="%%";
                } if(params[2].equals("2017")){
                    releaseDt="2017";
                } if(params[2].equals("2016")){
                    releaseDt="2016";
                } if(params[2].equals("2015")){
                    releaseDt="2015";
                }if(params[2].equals("2014")){
                    releaseDt="2014";
                }if(params[2].equals("2013")){
                    releaseDt="2013";
                }if(params[2].equals("2012")){
                    releaseDt="2012";
                }
                f.append(" and date_format(bean.releaseDt,'%Y') like :releaseDt");
                f.setParam("releaseDt","%"+releaseDt+"%");
            }
            if(params[3] != null && (params[3].trim()).length()>0 && !("undefined".equals(params[3])) ){
                orderBy = Integer.parseInt(params[3]);
            }
        }
        switch (orderBy) {
            case 1:
                // 发布时间降序
                f.append(" order by bean.releaseDt asc");
                break;

            default:
                //默认: 发布时间升序
                f.append(" order by bean.releaseDt desc");
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
        f.setCacheable(true);
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
            if(params[0]!= null && params[0].length() > 0){
                p.setSearchOne(params[0]);
            }
            if(params[1]!= null && params[1].length() > 0){
                p.setSearchTwo(params[1]);
            }
            if(params[2]!= null && params[2].length() > 0){
                p.setSearchThree(params[2]);
            }
            if(params[3]!= null && params[3].length() > 0){
                p.setSearchFour(params[3]);
            }
        }
        return p;
    }

    /***
     * @author wanjinyou
     * @description 云资源交易中心--扶持政策
     * @param siteIds 站点id
     * @param typeIds
     * @param titleImg
     * @param recommend
     * @param title
     * @param attr
     * @param orderBy 排序
     * @param pageNo 页码
     * @param pageSize 页码数
     * @param params 查询条件的参数
     * @return
     */
    public Pagination getPageBySiteIdFostPolicyById(Integer[] siteIds, Integer[] typeIds, Boolean titleImg, Boolean recommend, String title, Map<String, String[]> attr, int orderBy, int pageNo, int pageSize, String[] params) {
        Finder f = Finder.create("select bean from SIcloudPolicy bean  ");
        f.append( " where 1=1 and bean.state='2' ");
        f.append( " and isSupport = '是' ");
        if(params.length>0){
            if(params[0] != null && (params[0].trim()).length()>0 && !("undefined".equals(params[0])) ){
                String policyLevel = "%%";
                if(params[0].equals("qb")){
                    policyLevel="%%";
                } if(params[0].equals("gjj")){
                    policyLevel="国家级";
                } if(params[0].equals("sqj")){
                    policyLevel="省区级";
                } if(params[0].equals("msj")){
                    policyLevel="盟市级";
                }
                f.append(" and bean.policyLevel like :policyLevel");
                f.setParam("policyLevel","%"+policyLevel+"%");
            }
            if(params[1] != null && (params[1].trim()).length()>0 && !("undefined".equals(params[1])) ){
                String releaseDt = "%%";
                if(params[1].equals("qb")){
                    releaseDt="%%";
                } if(params[1].equals("2017")){
                    releaseDt="2017";
                } if(params[1].equals("2016")){
                    releaseDt="2016";
                } if(params[1].equals("2015")){
                    releaseDt="2015";
                }if(params[1].equals("2014")){
                    releaseDt="2014";
                }if(params[1].equals("2013")){
                    releaseDt="2013";
                }if(params[1].equals("2012")){
                    releaseDt="2012";
                }
                f.append(" and date_format(bean.releaseDt,'%Y') like :releaseDt");
                f.setParam("releaseDt","%"+releaseDt+"%");
            }
            if(params[2] != null && (params[2].trim()).length()>0 && !("undefined".equals(params[2])) ){
                orderBy = Integer.parseInt(params[2]);
            }
        }
        switch (orderBy) {
            case 1:
                // 发布时间降序
                f.append(" order by bean.releaseDt asc");
                break;

            default:
                //默认: 发布时间升序
                f.append(" order by bean.releaseDt desc");
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
          if(params[0] != null && params[0].length() > 0){
                p.setSearchOne(params[0]);
            }
            if(params[1] != null && params[1].length() > 0){
                p.setSearchTwo(params[1]);
            }
            if(params[2] != null && params[2].length() > 0){
                p.setSearchThree(params[2]);
            }
        }
        return p;
    }

    /***
     * @author wanjinyou
     * @description 云资源交易中心--云资源查看
     * @param siteIds 站点id
     * @param typeIds
     * @param titleImg
     * @param recommend
     * @param title
     * @param attr
     * @param orderBy 排序
     * @param pageNo 页码
     * @param pageSize 页码数
     * @param params 查询条件参数
     * @return
     */
    public Pagination getPageBySiteIdCheckById(Integer[] siteIds, Integer[] typeIds, Boolean titleImg, Boolean recommend, String title, Map<String, String[]> attr, int orderBy, int pageNo, int pageSize, String[] params) {
        Finder f = Finder.create("from SIcloudManage bean where bean.state in ('1','3')  ");
        if(params.length>0){
            if(params[0] != null && (params[0].trim()).length()>0 && !("undefined".equals(params[0])) ){
                String cityKey = params[0];
                if(!cityKey.equals("qb")){
                    f.append(" and bean.addrCity = :addrCity");
                    f.setParam("addrCity",cityKey);
                }
            }

            if(params[1] != null && (params[1].trim()).length()>0 && !("undefined".equals(params[1])) ){
                String resourceType = "%%";
                if(params[1].equals("qb")){
                    resourceType="%%";
                } if(params[1].equals("ycc")){
                    resourceType="云存储";
                } if(params[1].equals("yjs")){
                    resourceType="云计算";
                } if(params[1].equals("ysjk")){
                    resourceType="云数据库";
                }if(params[1].equals("qt")) {
                    resourceType = "其他";
                }
                f.append(" and bean.resourceType like :resourceType");
                f.setParam("resourceType","%"+resourceType+"%");
            }
            if(params[2] != null && (params[2].trim()).length()>0 && !("undefined".equals(params[2])) ){
                orderBy = Integer.parseInt(params[2]);
            }
        }
        switch (orderBy) {
            case 1:
                // 实名认证
                f.append(" order by bean.createDt asc");
                break;
            case 2:
                // 实名认证
                f.append(" order by bean.createDt desc");
                break;
            case 3:
                // 发布时间降序
                f.append(" order by bean.releaseDt asc");
                break;

            default:
                // 默认：发布时间升序
                f.append(" order by bean.releaseDt desc");
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
            if(params[0] != null && params[0].length() > 0){
                p.setSearchOne(params[0]);
            }
            if(params[1] != null && params[1].length() > 0){
                p.setSearchTwo(params[1]);
            }
            if(params[2] != null && params[2].length() > 0){
                p.setSearchThree(params[2]);
            }
        }
        return p;
    }

    /***
     * @author wanjinyou
     * @description 云资源交易中心--云需求
     * @param siteIds 站点id
     * @param typeIds
     * @param titleImg
     * @param recommend
     * @param title
     * @param attr
     * @param orderBy 排序
     * @param pageNo 页码
     * @param pageSize 页码数
     * @param params 查询条件参数
     * @return
     */
    public Pagination getPageBySiteIdDemandById(Integer[] siteIds, Integer[] typeIds, Boolean titleImg, Boolean recommend, String title, Map<String, String[]> attr, int orderBy, int pageNo, int pageSize, String[] params) {
        Finder f = Finder.create("select bean from SIcloudDemand bean  ");
        f.append( " where 1=1  and bean.status='3' ");
//        and DATEDIFF(bean.deadlineDt,now())>=0    //注释 掉了云需求 截止大于当前时间
        if(params.length>0){
            if(params[0] != null && (params[0].trim()).length()>0 && !("undefined".equals(params[0])) ){
                String serverType = "%%";
                if(params[0].equals("qb")){
                    serverType="%%";
                } if(params[0].equals("mkhfwq")){
                    serverType="模块化服务器";
                } if(params[0].equals("bzhfwq")){
                    serverType="标准化服务器";
                }
                f.append(" and bean.serverType LIKE :serverType");
                f.setParam("serverType",serverType);
            }
            if(params[1] != null && (params[1].trim()).length()>0 && !("undefined".equals(params[1])) ){
                String addrProvince = "%%";
                if (params[1].equals("bjs")) {
                    addrProvince = "北京市";
                }
                if (params[1].equals("tjs")) {
                    addrProvince = "天津市";
                }
                if (params[1].equals("hbs")) {
                    addrProvince = "河北省";
                }
                if (params[1].equals("nmgzzq")) {
                    addrProvince = "内蒙古自治区";
                }
                if (params[1].equals("lns")) {
                    addrProvince = "辽宁省";
                }
                if (params[1].equals("jls")) {
                    addrProvince = "吉林省";
                }
                if (params[1].equals("hljs")) {
                    addrProvince = "黑龙江省";
                }
                if (params[1].equals("shs")) {
                    addrProvince = "上海市";
                }
                if (params[1].equals("jss")) {
                    addrProvince = "江苏省";
                }
                if (params[1].equals("zjs")) {
                    addrProvince = "浙江省";
                }
                if (params[1].equals("ahs")) {
                    addrProvince = "安徽省";
                }
                if (params[1].equals("fjs")) {
                    addrProvince = "福建省";
                }
                if (params[1].equals("jxs")) {
                    addrProvince = "江西省";
                }
                if (params[1].equals("sds")) {
                    addrProvince = "山东省";
                }
                if (params[1].equals("hns")) {
                    addrProvince = "河南省";
                }
                if (params[1].equals("hubs")) {
                    addrProvince = "湖北省";
                }
                if (params[1].equals("huns")) {
                    addrProvince = "湖南省";
                }
                if (params[1].equals("gds")) {
                    addrProvince = "广东省";
                }
                if (params[1].equals("gxzzzzq")) {
                    addrProvince = "广西壮族自治区";
                }
                if (params[1].equals("hains")) {
                    addrProvince = "海南省";
                }
                if (params[1].equals("cqs")) {
                    addrProvince = "重庆市";
                }
                if (params[1].equals("scs")) {
                    addrProvince = "四川省";
                }
                if (params[1].equals("gzs")) {
                    addrProvince = "贵州省";
                }
                if (params[1].equals("yns")) {
                    addrProvince = "云南省";
                }
                if (params[1].equals("zzzzq")) {
                    addrProvince = "西藏自治区";
                }
                if (params[1].equals("sxs")) {
                    addrProvince = "陕西省";
                }
                if (params[1].equals("gss")) {
                    addrProvince = "甘肃省";
                }
                if (params[1].equals("qhs")) {
                    addrProvince = "青海省";
                }
                if (params[1].equals("nxhzzzq")) {
                    addrProvince = "宁夏回族自治区";
                }
                if (params[1].equals("xjwwezzzq")) {
                    addrProvince = "新疆维吾尔自治区";
                }
                if (params[1].equals("qb")) {
                    addrProvince = "%%";
                }
                f.append(" and bean.addrProvince LIKE:addrProvince");
                f.setParam("addrProvince","%"+addrProvince+"%");
            }
            //此处前台查询条件为购买价格，并无此字段，先放上个别的字段
            if(params[2] != null && (params[2].trim()).length()>0 && !("undefined".equals(params[2])) ){
                if(params[2].equals("0")) {
                    f.append(" and bean.expect_price>=0 and expect_price <=5000");
                }if(params[2].equals("5000")){
                    f.append(" and bean.expect_price>=5000 and expect_price <=10000");
                }if(params[2].equals("10000")){
                    f.append(" and bean.expect_price>=10000");
                }
            }
            if(params[3] != null && (params[3].trim()).length()>0 && !("undefined".equals(params[3])) ){
                orderBy = Integer.parseInt(params[3]);
            }
        }
        switch (orderBy) {
            case 1:
                // 截止时间降序
                f.append(" order by bean.deadlineDt desc");
                break;
            case 2:
                // 发布时间降序
                f.append(" order by bean.releaseDt asc");
                break;
            case 3:
                // 发布时间升序
                f.append(" order by bean.releaseDt asc");
                break;

            default:
                // 默认：发布时间降序
                f.append(" order by bean.releaseDt desc");
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
            if(params[0] != null && params[0].length() > 0){
                p.setSearchOne(params[0]);
            }
            if(params[1] != null && params[1].length() > 0){
                p.setSearchTwo(params[1]);
            }
            if(params[2] != null && params[2].length() > 0){
                p.setSearchThree(params[2]);
            }
            if(params[3] != null && params[3].length() > 0){
                p.setSearchFour(params[3]);
            }
        }
        return p;
    }


    protected Class<SIcloudPolicy> getEntityClass() {
        return SIcloudPolicy.class;
    }
    @Resource
    private ContentQueryFreshTimeCache contentQueryFreshTimeCache;
}
