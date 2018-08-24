package com.anchorcms.icloud.dao.synergy.impl;

import com.anchorcms.cms.service.main.ContentQueryFreshTimeCache;
import com.anchorcms.common.hibernate.Finder;
import com.anchorcms.common.hibernate.HibernateBaseDao;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.icloud.dao.common.PubCodeDao;
import com.anchorcms.icloud.dao.synergy.SelectAbilityContentDao;
import com.anchorcms.icloud.model.common.PubCode;
import com.anchorcms.icloud.model.synergy.SAbility;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.*;

import static com.anchorcms.cms.directive.abs.AbstractContentDirective.*;

/**
 * Created by 11239 on 2016/12/21.
 */
@Repository
public class SelectAbilityContentDaoImpl extends HibernateBaseDao<SAbility, Integer> implements SelectAbilityContentDao{

    public SAbility findById(Integer id) {
        SAbility entity = get(id);
        return entity;
    }


    /**
     * 协同制造--能力池展示
     * @author wanjinyou
     * @param siteIds
     * @param typeIds
     * @param titleImg
     * @param recommend
     * @param title
     * @param attr
     * @param orderBy
     * @param pageNo
     * @param pageSize
     * @param params
     * @return
     */
    public Pagination getPageBySiteIdAbilityById(Integer[] siteIds, Integer[] typeIds, Boolean titleImg, Boolean recommend, String title, Map<String, String[]> attr, int orderBy, int pageNo, int pageSize, String[] params) {
        StringBuffer f = new StringBuffer("select * from s_ability s");
        f.append( " where s.status_type = '3'");
        if(params.length>0){
            //能力分类
            if(params[0] != null && (params[0].trim()).length()>0 && !("undefined".equals(params[0])) ) {
                String typeKey = params[0];
                String abilityType = "-";
                if ("qb".equals(typeKey)) {
                    abilityType = "%%";
                    f.append(" and s.ABILITY_TYPE like '"+abilityType+"' ");
                } else {
                    PubCode pc = pubCodeDao.findUniqueCode("NLFL", typeKey);
                    if (pc != null) {
                        int level = pc.getLevel();
                        String keyHead = pc.getKey().substring(0, level * 2);
                        abilityType = "^"+keyHead;
                    }
                    f.append(" and s.ABILITY_TYPE regexp '"+abilityType+"' ");
                }
            }

            //企业性质
            if(params[1] != null && (params[1].trim()).length()>0 && !("undefined".equals(params[1])) ){

                String companyType = "%%";
                if(params[1].equals("gyqy")){
                    companyType="国有企业";
                } if(params[1].equals("jtqy")){
                    companyType="集体企业";
                } if(params[1].equals("gfhzqy")){
                    companyType="股份合作企业";
                } if(params[1].equals("lyqy")){
                    companyType="联营企业";
                } if(params[1].equals("yxzrgs")){
                    companyType="有限责任公司";
                } if(params[1].equals("gfyxgs")){
                    companyType="股份有限公司";
                } if(params[1].equals("syqy")){
                    companyType="私营企业";
                } if(params[1].equals("qtqy")){
                    companyType="其他企业";
                }if(params[1].equals("kfqy")){
                    companyType="大数据应用开发企业";
                }if(params[1].equals("ypt")){
                    companyType="大数据云平台服务商";
                }if(params[1].equals("yyy")){
                    companyType="大数据云应用服务商";
                }if(params[1].equals("jjfa")){
                    companyType="解决方案服务商";
                } if(params[1].equals("qb")){
                    companyType="%%";
                }
//                f.append(" and bean.company.companyType like:companyType");
//                f.setParam("companyType", "%" + companyType + "%");
                f.append(" and s.company_id in (select  y.company_id from s_company y where y.company_type like '%"+companyType+"%') ");
            }//经营方式
            if(params[2] != null && (params[2].trim()).length()>0 && !("undefined".equals(params[2])) ){
                String operateType = "%%";
                if(params[2].equals("qb")){
                    operateType="%%";
                } if(params[2].equals("scx")){
                    operateType="生产型";
                } if(params[2].equals("myx")){
                    operateType="贸易型";
                } if(params[2].equals("fwx")){
                    operateType="服务型";
                } if(params[2].equals("yfx")){
                    operateType="研发型";
                } if(params[2].equals("qtlx")){
                    operateType="其他类型";
                }
//                f.append(" and bean.company.operateType like :operateType");
//                f.setParam("operateType","%" +operateType+ "%");
                f.append(" and s.company_id in (SELECT  y.company_id from s_company y where y.operate_type like '%"+operateType+"%') ");
            }//排序
            if(params[4] != null && (params[4].trim()).length()>0 && !("undefined".equals(params[4])) ){
                orderBy = Integer.parseInt(params[4]);
            }
            if(params[6] != null && (params[6].trim()).length()>0 && !("undefined".equals(params[6])) ) {
                String abilityName = params[6];
                f.append(" and s.ABILITY_NAME like '%"+abilityName+"%' ");

            }

        }
        switch (orderBy) {
            case 0:
                // 发布时间降序
                f.append(" order by s.release_dt desc");
                break;
            case 1:
                // 发布时间升序
                f.append(" order by s.release_dt asc");
                break;
            default:
                // 发布时间降序
                f.append(" order by s.release_dt desc");
        }

        SQLQuery sqlQueryl = getSession().createSQLQuery(f.toString());
        sqlQueryl.addEntity(SAbility.class);
        List listl = sqlQueryl.list();

        int totalCount=  listl.size();
        Pagination p = new Pagination(pageNo, pageSize, totalCount);
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
        if (totalCount < 1) {
            p.setList(new ArrayList());
            return p;
        }
        SQLQuery sqlQuery = getSession().createSQLQuery(f.toString());
        sqlQuery.addEntity(SAbility.class);
        sqlQuery.setFirstResult(p.getFirstResult());
        sqlQuery.setMaxResults(p.getPageSize());
        List list = sqlQuery.list();
        p.setList(list);

        return p;
    }


    public Pagination getPageBySiteIdsForTag(Integer[] siteIds, Integer[] typeIds, Boolean titleImg, Boolean recommend, String title,
                                             Map<String, String[]> attr, int orderBy, int pageNo, int pageSize, String type, String name, String abilityType, String abilityName, int adilityordeyBy) {
        Finder f = Finder.create("select bean from SAbility bean");
       buildFinderBySiteIds(f,siteIds,typeIds,titleImg,recommend,title,attr,contentQueryFreshTimeCache);
        appendAbilityType(f,type);
        appendAbilityName(f,name);
        if(abilityType!=null){
            f.append(" and bean.addrStreet=:addrStreet");
            f.setParam("addrStreet",abilityType);
        }
        if(abilityName!=null){
            f.append(" and bean.contact=:contact");
            f.setParam("contact",abilityName);
        }
        appendOrder(f,adilityordeyBy);
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
        /**
         *把查询条件返回前台
         */
            if(type != null){
                p.setSearchOne(type);
            }
            if(name != null){
                p.setSearchTwo(name);
            }
            if(abilityType != null){
                p.setSearchThree(abilityType);
            }
            if(abilityName != null){
                p.setSearchFour(abilityName);
            }
                p.setSerchFive(adilityordeyBy+"");
        return p;
        //return find(f,pageNo,pageSize);
    }


    public List getListBySiteIdsForTag(Integer[] siteIds, Integer[] typeIds, Boolean titleImg,
                                       Boolean recommend, String title, Map<String, String[]> attr, int orderBy, Integer first, Integer count, String abilityType) {
        Finder f = Finder.create("select  bean from SAbility bean");
        buildFinderBySiteIds(f,siteIds, typeIds, titleImg, recommend, title,attr,contentQueryFreshTimeCache);
        if (first != null) {
            f.setFirstResult(first);
        }
        if (count != null) {
            f.setMaxResults(count);
        }
        appendAbilityType(f,abilityType);
        appendOrder(f,orderBy);
        f.setCacheable(true);
        return find(f);
    }

    public Pagination getPageByIdsForTag(Integer[] ids, int orderBy, int pageNo, int count, String abilityType) {
        Finder f = Finder.create("select  bean from SAbility bean");
        f.append(" join bean.content as content");
        f.append(" join bean.content.contentExt as ext where 1=1");
        if(ids!=null)
        {
            int len = ids.length;
            if (len == 1) {
                f.append(" where content.contentId=:contentId").setParam("contentId", ids[0]);
            } else {
                f.append(" where content.contentId in(:contentId)");
                f.setParamList("contentId", ids);
            }
        }
        appendAbilityType(f,abilityType);
        appendOrder(f,orderBy);
        f.setCacheable(true);
        return find(f,pageNo,count);
    }

    /***
     * 获取需求信息表中的内容并显示在前段
     *
     * @param ids
     * @param oderBy
     * @param pageNo
     * @param count
     * @param queryParams
     * @return
     */
    public Pagination getPageByIdsForTagById(Integer[] ids, int oderBy, int pageNo, int count, String[] queryParams) {
        Finder f = Finder.create("select  bean from SDemand bean");
        f.append(" join bean.content as content");
        f.append(" join bean.content.contentExt as ext where 1=1");
        if(ids!=null)
        {
            int len = ids.length;
            if (len == 1) {
                f.append(" where content.contentId=:contentId").setParam("contentId", ids[0]);
            } else {
                f.append(" where content.contentId in(:contentId)");
                f.setParamList("contentId", ids);
            }
        }
       // if(queryParams!=null){
       //     f.append(" and bean.abilityType=:abilityType");
      //      f.setParam("abilityType",abilityType);
     //   }

       // appendOrder(f,orderBy);
        f.setCacheable(true);
        return find(f,pageNo,count);
    }

    /***
     * 获取全部需求信息表中的内容并显示在前段
     *
     * @param siteIds
     * @param typeIds
     * @param titleImg
     * @param recommend
     * @param title
     * @param attr
     * @param orderBy
     * @param pageNo
     * @param pageSize
     * @param params
     * @return
     */
    public Pagination getPageBySiteIdsForTagDe(Integer[] siteIds, Integer[] typeIds, Boolean titleImg, Boolean recommend, String title, Map<String, String[]> attr, int orderBy, int pageNo, int pageSize, String[] params) {
        Finder f = Finder.create("select bean from SDemand bean");
        f.append(" join bean.content as content");
        f.append(" join bean.content.contentExt as ext where bean.statusType = 3 and DATEDIFF(bean.deadlineDt,now())>=0");
        if(siteIds!=null)
        {
            int len = siteIds.length;
            if (len == 1) {
                f.append(" where content.contentId=:contentId").setParam("contentId", siteIds[0]);
            } else {
                f.append(" where content.contentId in(:contentId)");
                f.setParamList("contentId", siteIds);
            }
        }
            if(params.length>0){
                //能力分类
                if(params[0] != null && (params[0].trim()).length()>0 && !("undefined".equals(params[0])) ) {
                    String typeKey = params[0];
                    String classifyType = "-";
                    if ("qb".equals(typeKey)) {
                        classifyType = "%%";
                    } else {
                        PubCode pc = pubCodeDao.findUniqueCode("NLFL", typeKey);
                        if (pc != null) {
                            int level = pc.getLevel();
                            String keyHead = pc.getKey().substring(0, level * 2);
                            classifyType = keyHead + "%";
                        }
                    }
                    f.append(" and bean.classifyType like :classifyType");
                    f.setParam("classifyType",""+classifyType+"" );
                }
                //企业性质
                if(params[1] != null &&(params[1].trim()).length()>0 && !("undefined".equals(params[1])) ){
                    String companyType = "%%";
                    if(params[1].equals("gyqy")){
                        companyType="国有企业";
                    } if(params[1].equals("jtqy")){
                        companyType="集体企业";
                    } if(params[1].equals("gfhzqy")){
                        companyType="股份合作企业";
                    } if(params[1].equals("lyqy")){
                        companyType="联营企业";
                    } if(params[1].equals("yxzrgs")){
                        companyType="有限责任公司";
                    } if(params[1].equals("gfyxgs")){
                        companyType="股份有限公司";
                    } if(params[1].equals("syqy")){
                        companyType="私营企业";
                    } if(params[1].equals("qtqy")){
                        companyType="其他企业";
                    } if(params[1].equals("qb")){
                        companyType="%%";
                    }

                    f.append(" and bean.company.companyType like:companyType");
                    f.setParam("companyType","%"+companyType+"%");
                }//经营方式
                if(params[2] != null &&(params[2].trim()).length()>0 && !("undefined".equals(params[2])) ){
                    String operateType = "%%";
                    if(params[2].equals("qb")){
                        operateType="%%";
                    } if(params[2].equals("scx")){
                        operateType="生产型";
                    } if(params[2].equals("myx")){
                        operateType="贸易型";
                    } if(params[2].equals("fwx")){
                        operateType="服务型";
                    } if(params[2].equals("yfx")){
                        operateType="研发型";
                    } if(params[2].equals("qtlx")){
                        operateType="其他类型";
                    }
                    f.append(" and bean.company.operateType like :operateType");
                    f.setParam("operateType","%"+operateType+"%");
                }
                if(params[3] != null &&(params[3].trim()).length()>0 && !("undefined".equals(params[3])) ){
                    orderBy = Integer.parseInt(params[3]);
                }
                if(params[4] != null &&(params[4].trim()).length()>0 && !("undefined".equals(params[4])) ){
                    String inquiryTheme =params[4];
                    f.append(" and bean.inquiryTheme like :inquiryTheme");
                    f.setParam("inquiryTheme","%"+inquiryTheme+"%");
                }
            }
        appendOrder(f,orderBy);
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
            if(params[0] != null &&params[0].length() > 0){
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
        //return find(f,pageNo,pageSize);
    }

    /***
     * 企业制造能力展示
     *
     * @param siteIds
     * @param typeIds
     * @param titleImg
     * @param recommend
     * @param title
     * @param attr
     * @param orderBy
     * @param pageNo
     * @param pageSize
     * @param params
     * @return
     */
    public Pagination etPageBySiteIdComporyById(Integer[] siteIds, Integer[] typeIds, Boolean titleImg, Boolean recommend, String title, Map<String, String[]> attr, int orderBy, int pageNo, int pageSize, String[] params) {
        Finder f = Finder.create("select bean from SCompany bean  ");
        f.append( " where 1=1 and bean.isRecommend=1");
        if(params.length>0){
            if(params[0] != null && (params[0].trim()).length()>0 && !("undefined".equals(params[0])) ){
                String addrCity = "%%";
                String addrProvince = "%%";
                if (params[0].equals("bjs")) {
                    addrProvince = "北京市";
                }
                if (params[0].equals("tjs")) {
                    addrProvince = "天津市";
                }
                if (params[0].equals("hbs")) {
                    addrProvince = "河北省";
                }
                if (params[0].equals("nmgzzq")) {
                    addrProvince = "内蒙古自治区";
                }
                if (params[0].equals("lns")) {
                    addrProvince = "辽宁省";
                }
                if (params[0].equals("jls")) {
                    addrProvince = "吉林省";
                }
                if (params[0].equals("hljs")) {
                    addrProvince = "黑龙江省";
                }
                if (params[0].equals("shs")) {
                    addrProvince = "上海市";
                }
                if (params[0].equals("jss")) {
                    addrProvince = "江苏省";
                }
                if (params[0].equals("zjs")) {
                    addrProvince = "浙江省";
                }
                if (params[0].equals("ahs")) {
                    addrProvince = "安徽省";
                }
                if (params[0].equals("fjs")) {
                    addrProvince = "福建省";
                }
                if (params[0].equals("jxs")) {
                    addrProvince = "江西省";
                }
                if (params[0].equals("sds")) {
                    addrProvince = "山东省";
                }
                if (params[0].equals("hns")) {
                    addrProvince = "河南省";
                }
                if (params[0].equals("hubs")) {
                    addrProvince = "湖北省";
                }
                if (params[0].equals("huns")) {
                    addrProvince = "湖南省";
                }
                if (params[0].equals("gds")) {
                    addrProvince = "广东省";
                }
                if (params[0].equals("gxzzzzq")) {
                    addrProvince = "广西壮族自治区";
                }
                if (params[0].equals("hains")) {
                    addrProvince = "海南省";
                }
                if (params[0].equals("cqs")) {
                    addrProvince = "重庆市";
                }
                if (params[0].equals("scs")) {
                    addrProvince = "四川省";
                }
                if (params[0].equals("gzs")) {
                    addrProvince = "贵州省";
                }
                if (params[0].equals("yns")) {
                    addrProvince = "云南省";
                }
                if (params[0].equals("zzzzq")) {
                    addrProvince = "西藏自治区";
                }
                if (params[0].equals("sxs")) {
                    addrProvince = "陕西省";
                }
                if (params[0].equals("gss")) {
                    addrProvince = "甘肃省";
                }
                if (params[0].equals("qhs")) {
                    addrProvince = "青海省";
                }
                if (params[0].equals("nxhzzzq")) {
                    addrProvince = "宁夏回族自治区";
                }
                if (params[0].equals("xjwwezzzq")) {
                    addrProvince = "新疆维吾尔自治区";
                }
                if (params[0].equals("qb")) {
                    addrProvince = "%%";
                }
                f.append(" and bean.addrProvince like :addrProvince");
                f.setParam("addrProvince","%" +addrProvince+ "%");
            }
            if(params[1] != null && (params[1].trim()).length()>0 && !("undefined".equals(params[1]))){
                String serverType = "%%";
                if(params[1].equals("gyqy")){
                    serverType="国有企业";
                } if(params[1].equals("jtqy")){
                    serverType="集体企业";
                } if(params[1].equals("gfhzqy")){
                    serverType="股份合作企业";
                } if(params[1].equals("lyqy")){
                    serverType="联营企业";
                } if(params[1].equals("yxzrgs")){
                    serverType="有限责任公司";
                } if(params[1].equals("gfyxgs")){
                    serverType="股份有限公司";
                } if(params[1].equals("syqy")){
                    serverType="私营企业";
                } if(params[1].equals("qtqy")){
                    serverType="其他企业";
                } if(params[1].equals("qb")){
                    serverType="%%";
                }
                f.append(" and bean.companyType like :companyType");
                f.setParam("companyType","%" +serverType+ "%");
            }
            if(params[2] != null && (params[2].trim()).length()>0 && !("undefined".equals(params[2])) ){
                String operateType = "%%";

                if(params[2].equals("qb")){
                    operateType="%%";
                } if(params[2].equals("myx")){
                    operateType="生产型";
                } if(params[2].equals("shty")){
                    operateType="贸易型";
                } if(params[2].equals("fwx")){
                    operateType="服务型";
                } if(params[2].equals("yfx")){
                    operateType="研发型";
                } if(params[2].equals("qtlx")){
                    operateType="其他类型";
                }
                f.append(" and bean.operateType like :operateType");
                f.setParam("operateType","%" +operateType+ "%");
            }
            if(params[3] != null && (params[3].trim()).length()>0 && !("undefined".equals(params[3])) ){
                orderBy = Integer.parseInt(params[3]);
            }
        }
        switch (orderBy) {
            case 1:
                // ID升序
                f.append(" order by bean.companyId asc");
                break;
            case 2:
                // 发布时间降序
                f.append(" order by bean.updateDt desc");
                break;
            case 3:
                // 发布时间升序
                f.append(" order by bean.updateDt asc");
                break;

            default:
                // 默认： 发布时间降序
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
            if( params[0] != null && params[0].length() > 0){
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

    public List getListByIdsForTag(Integer[] ids, int orderBy, String abilityType) {
        Finder f = Finder.create("select  bean from SAbility bean");
        f.append(" join bean.content as content");
        f.append(" join bean.content.contentExt as ext where 1=1");
        if(ids!=null)
        {
            int len = ids.length;
            if (len == 1) {
                f.append(" where content.contentId=:contentId").setParam("contentId", ids[0]);
            } else {
                f.append(" where content.contentId in(:contentId)");
                f.setParamList("contentId", ids);
            }
        }
        appendAbilityType(f,abilityType);
        appendOrder(f,orderBy);
        f.setCacheable(true);
        return find(f);
    }

    public Pagination getPageByTagIdsForTag(Integer[] tagIds, Integer[] siteIds, Integer[] channelIds, Integer[] typeIds, Integer excludeId, Boolean titleImg, Boolean recommend, String title, Map<String, String[]> attr, int orderBy, int pageNo, int count, String abilityType) {
        Finder f = Finder.create("select  bean from SAbility bean");
        buildFinderByTagIds(f, tagIds, siteIds,channelIds,typeIds,  excludeId, titleImg,  recommend,title,attr,contentQueryFreshTimeCache);
        appendAbilityType(f,abilityType);
        appendOrder(f, orderBy);
        f.setCacheable(true);
        return find(f,pageNo,count);
    }

    public List getListByTagIdsForTag(Integer[] tagIds, Integer[] siteIds, Integer[] channelIds, Integer[] typeIds, Integer excludeId, Boolean titleImg, Boolean recommend, String title, Map<String, String[]> attr, int orderBy, Integer first, Integer count, String abilityType) {
        Finder f = Finder.create("select  bean from SAbility bean");
        buildByTagIds(f, tagIds, siteIds,channelIds,typeIds,  excludeId, titleImg,  recommend,title,attr,contentQueryFreshTimeCache);
        if (first != null) {
            f.setFirstResult(first);
        }
        if (count != null) {
            f.setMaxResults(count);
        }
        appendAbilityType(f,abilityType);
        appendOrder(f, orderBy);
        f.setCacheable(true);
        return find(f);
    }

    public Pagination getPageByTopicIdForTag(Integer topicId, Integer[] siteIds, Integer[] channelIds, Integer[] typeIds, Boolean titleImg, Boolean recommend, String title, Map<String, String[]> attr, int orderBy, int pageNo, int count, String abilityType) {
        Finder f = Finder.create("select  bean from SAbility bean");
        buildByTopicId(f,topicId, siteIds, channelIds, typeIds, titleImg,recommend, title,attr,contentQueryFreshTimeCache);
        appendAbilityType(f,abilityType);
        appendOrder(f, orderBy);
        f.setCacheable(true);
        return find(f,pageNo,count);
    }

    public List getListByTopicIdForTag(Integer topicId, Integer[] siteIds, Integer[] channelIds, Integer[] typeIds, Boolean titleImg, Boolean recommend, String title, Map<String, String[]> attr, int orderBy, Integer first, Integer count, String abilityType) {
        Finder f = Finder.create("select  bean from SAbility bean");
        buildByTopicId(f,topicId, siteIds, channelIds, typeIds, titleImg,
                recommend, title,attr,contentQueryFreshTimeCache);
        if (first != null) {
            f.setFirstResult(first);
        }
        if (count != null) {
            f.setMaxResults(count);
        }
        appendAbilityType(f,abilityType);
        appendOrder(f, orderBy);
        f.setCacheable(true);
        return find(f);
    }

    public Pagination getPageByChannelIdsForTag(Integer[] channelIds, Integer[] typeIds, Boolean titleImg, Boolean recommend, String title, Map<String, String[]> attr, int orderBy, int option, int pageNo, int pageSize, String abilityType) {
        Finder f = Finder.create("select  bean from SAbility bean");
        buildByChannelIds(f,channelIds,typeIds,titleImg,recommend,title,attr,option,contentQueryFreshTimeCache);
        appendAbilityType(f,abilityType);
        appendOrder(f, orderBy);
        f.setCacheable(true);
        return find(f,pageNo,pageSize);
    }

    public List getListByChannelIdsForTag(Integer[] channelIds, Integer[] typeIds, Boolean titleImg, Boolean recommend, String title, Map<String, String[]> attr, int orderBy, int option, Integer first, Integer count, String abilityType) {
        Finder f = Finder.create("select  bean from SAbility bean");
        buildByChannelIds(f,channelIds, typeIds, titleImg, recommend,
                title,attr, option,contentQueryFreshTimeCache);
        if (first != null) {
            f.setFirstResult(first);
        }
        if (count != null) {
            f.setMaxResults(count);
        }
        appendAbilityType(f,abilityType);
        appendOrder(f, orderBy);
        f.setCacheable(true);
        return find(f);
    }

    public Pagination getPageByChannelPathsForTag(String[] paths, Integer[] siteIds, Integer[] typeIds, Boolean titleImg, Boolean recommend, String title, Map<String, String[]> attr, int orderBy, int pageNo, int pageSize, String abilityType) {
        Finder f = Finder.create("select  bean from SAbility bean");
        buildByChannelPaths(f,paths, siteIds, typeIds, titleImg, recommend,
                title,attr,contentQueryFreshTimeCache);
        appendAbilityType(f,abilityType);
        appendOrder(f, orderBy);
        f.setCacheable(true);
        return find(f,pageNo,pageSize);
    }

    public List getListByChannelPathsForTag(String[] paths, Integer[] siteIds, Integer[] typeIds, Boolean titleImg, Boolean recommend, String title, Map<String, String[]> attr, int orderBy, Integer first, Integer count, String abilityType) {
        Finder f = Finder.create("select  bean from SAbility bean");
        buildByChannelPaths(f,paths, siteIds, typeIds, titleImg, recommend,
                title,attr,contentQueryFreshTimeCache);
        if (first != null) {
            f.setFirstResult(first);
        }
        if (count != null) {
            f.setMaxResults(count);
        }
        appendAbilityType(f,abilityType);
        appendOrder(f, orderBy);
        f.setCacheable(true);
        return find(f);
    }


    //拼接hql语句，加入查询条件
    private  void appendAbilityType(Finder f,String abilityType)
    {
        if(abilityType!=null){
            f.append(" and bean.abilityType=:abilityType");
            f.setParam("abilityType",abilityType);
        }
    }

    //拼接hql语句，加入查询条件
    private  void appendAbilityName(Finder f,String abilityName)
    {
        if(abilityName!=null){
            f.append(" and bean.abilityName=:abilityName");
            f.setParam("abilityName",abilityName);
        }
    }


    public Finder buildFinderBySiteIds(Finder f, Integer[] siteIds, Integer[] typeIds,
                                              Boolean titleImg, Boolean recommend, String title, Map<String,String[]> attr,
                                              ContentQueryFreshTimeCache contentQueryFreshTimeCache){
        f.append(" join bean.content as content");
        f.append(" join bean.content.contentExt as ext where 1=1");
        if (titleImg != null) {
            f.append(" and content.hasTitleImg=:titleImg");
            f.setParam("titleImg", titleImg);
        }
        if (recommend != null) {
            f.append(" and content.isRecommend=:recommend");
            f.setParam("recommend", recommend);
        }
        appendReleaseDate(f,contentQueryFreshTimeCache);
        appendTypeIds(f, typeIds);
        appendSiteIds(f, siteIds);
        //f.append(" and content.status=" + ContentCheck.CHECKED);
        if (!StringUtils.isBlank(title)) {
            f.append(" and ext.title like :title");
            f.setParam("title", "%" + title + "%");
        }
        appendAttr(f, attr);
        return f;
    }

    public  Finder buildFinderByTagIds(Finder f, Integer[] tagIds, Integer[] siteIds,
                                              Integer[] channelIds, Integer[] typeIds, Integer excludeId,
                                              Boolean titleImg, Boolean recommend, String title,Map<String,String[]>attr,
                                              ContentQueryFreshTimeCache contentQueryFreshTimeCache) {
        int len = tagIds.length;
        if (len == 1) {
            f.append(" join bean.content as content join bean.content.tags tag");
            f.append(" join bean.content.contentExt as ext");
            f.append(" where tag.tagId=:tagId").setParam("tagId", tagIds[0]);
        } else {
            f.append(" join bean.content as content join bean.content.tags tag");
            f.append(" join bean.content.contentExt as ext");
            f.append(" where tag.tagId in(:tagIds)");
            f.setParamList("tagIds", tagIds);
        }
        if (titleImg != null) {
            f.append(" and content.hasTitleImg=:titleImg");
            f.setParam("titleImg", titleImg);
        }
        if (recommend != null) {
            f.append(" and content.isRecommend=:recommend");
            f.setParam("recommend", recommend);
        }
        appendReleaseDate(f,contentQueryFreshTimeCache);
        appendTypeIds(f, typeIds);
        appendChannelIds(f, channelIds);
        appendSiteIds(f, siteIds);
        if (excludeId != null) {
            f.append(" and content.contentId<>:excludeId");
            f.setParam("excludeId", excludeId);
        }
       // f.append(" and content.status=" + ContentCheck.CHECKED);
        if (!StringUtils.isBlank(title)) {
            f.append(" and ext.title like :title");
            f.setParam("title", "%" + title + "%");
        }
        appendAttr(f, attr);
        return f;
    }
    public Finder buildByTagIds(Finder f,Integer[] tagIds, Integer[] siteIds,
                                        Integer[] channelIds, Integer[] typeIds, Integer excludeId,
                                        Boolean titleImg, Boolean recommend, String title,Map<String,String[]>attr,ContentQueryFreshTimeCache contentQueryFreshTimeCache) {
        int len = tagIds.length;
        if (len == 1) {
            f.append(" join bean.content as content join bean.content.tags tag");
            f.append(" join bean.content.contentExt as ext");
            f.append(" where tag.tagId=:tagId").setParam("tagId", tagIds[0]);
        } else {
            f.append("join bean.content as content join bean.content.tags tag");
            f.append(" join bean.content.contentExt as ext");
            f.append(" where tag.tagId in(:tagIds)");
            f.setParamList("tagIds", tagIds);
        }
        if (titleImg != null) {
            f.append(" and content.hasTitleImg=:titleImg");
            f.setParam("titleImg", titleImg);
        }
        if (recommend != null) {
            f.append(" and content.isRecommend=:recommend");
            f.setParam("recommend", recommend);
        }

        appendReleaseDate(f,contentQueryFreshTimeCache);
        appendTypeIds(f, typeIds);
        appendChannelIds(f, channelIds);
        appendSiteIds(f, siteIds);
        if (excludeId != null) {
            f.append(" and content.contentId<>:excludeId");
            f.setParam("excludeId", excludeId);
        }
       // f.append(" and content.status=" + ContentCheck.CHECKED);
        if (!StringUtils.isBlank(title)) {
            f.append(" and ext.title like :title");
            f.setParam("title", "%" + title + "%");
        }
        appendAttr(f, attr);
        return f;
    }
    public Finder buildByTopicId(Finder f, Integer topicId, Integer[] siteIds,
                                        Integer[] channelIds, Integer[] typeIds, Boolean titleImg,
                                        Boolean recommend, String title,Map<String,String[]>attr,
                                        ContentQueryFreshTimeCache contentQueryFreshTimeCache) {
        f.append(" join bean.content as content join bean.content.topics topic");
        f.append(" join bean.content.contentExt as ext");
        f.append(" where topic.topicId=:topicId").setParam("topicId", topicId);
        if (titleImg != null) {
            f.append(" and content.hasTitleImg=:titleImg");
            f.setParam("titleImg", titleImg);
        }
        if (recommend != null) {
            f.append(" and content.isRecommend=:recommend");
            f.setParam("recommend", recommend);
        }
        appendReleaseDate(f,contentQueryFreshTimeCache);
        appendTypeIds(f, typeIds);
        appendChannelIds(f, channelIds);
        appendSiteIds(f, siteIds);
      //  f.append(" and content.status=" + ContentCheck.CHECKED);
        if (!StringUtils.isBlank(title)) {
            f.append(" and ext.title like :title");
            f.setParam("title", "%" + title + "%");
        }
        appendAttr(f, attr);
        return f;
    }

    public Finder  buildByChannelIds(Finder f, Integer[] channelIds, Integer[] typeIds,
                                            Boolean titleImg, Boolean recommend, String title,Map<String,String[]>attr,
                                            int option,ContentQueryFreshTimeCache contentQueryFreshTimeCache) {
        int len = channelIds.length;
        // 如果多个栏目
        if (option == 0 || len > 1) {
            f.append(" join bean.content as content ");
            f.append(" join bean.content.contentExt as ext");
            if (len == 1) {
                f.append(" where content.channel.channelId=:channelId ");
                f.setParam("channelId", channelIds[0]);
            } else {
                f.append(" where content.channel.channelId in (:channelIds)  ");
                f.setParamList("channelIds", channelIds);
            }
        } else if (option == 1) {
            // 包含子栏目
            f.append(" join bean.content as content ");
            f.append(" join bean.content.contentExt as ext");
            f.append(" join bean.content.channel node,Channel parent");
            f.append(" where node.lft between parent.lft and parent.rgt");
            f.append(" and content.site.siteId=parent.site.siteId");
            f.append(" and parent.channelId=:channelId");
            f.setParam("channelId", channelIds[0]);
        } else if (option == 2) {
            // 包含副栏目
            f.append(" join bean.content as content ");
            f.append(" join bean.content.contentExt as ext");
            f.append(" join bean.content.channels as channel");
            f.append(" where channel.channelId=:channelId");
            f.setParam("channelId", channelIds[0]);
        } else {
            throw new RuntimeException("option value must be 0 or 1 or 2.");
        }
        if (titleImg != null) {
            f.append(" and content.hasTitleImg=:titleImg");
            f.setParam("titleImg", titleImg);
        }
        if (recommend != null) {
            f.append(" and content.isRecommend=:recommend");
            f.setParam("recommend", recommend);
        }
        appendReleaseDate(f,contentQueryFreshTimeCache);
        appendTypeIds(f, typeIds);
      //  f.append(" and content.status=" + ContentCheck.CHECKED);
        if (!StringUtils.isBlank(title)) {
            f.append(" and ext.title like :title");
            f.setParam("title", "%" + title + "%");
        }
        appendAttr(f, attr);
        return f;
    }

    public Finder buildByChannelPaths(Finder f, String[] paths, Integer[] siteIds,
                                             Integer[] typeIds, Boolean titleImg, Boolean recommend,
                                             String title,Map<String,String[]>attr,ContentQueryFreshTimeCache contentQueryFreshTimeCache) {

        f.append(" join bean.content as content join bean.content.channel channel ");
        f.append(" join bean.contentExt as ext");
        int len = paths.length;
        if (len == 1) {
            f.append(" where channel.channelPath=:path").setParam("path", paths[0]);
        } else {
            f.append(" where channel.path in (:paths)");
            f.setParamList("paths", paths);
        }
        if (siteIds != null) {
            len = siteIds.length;
            if (len == 1) {
                f.append(" and channel.site.siteId=:siteId");
                f.setParam("siteId", siteIds[0]);
            } else if (len > 1) {
                f.append(" and channel.site.siteId in (:siteIds)");
                f.setParamList("siteIds", siteIds);
            }
        }
        if (titleImg != null) {
            f.append(" and content.hasTitleImg=:titleImg");
            f.setParam("titleImg", titleImg);
        }
        if (recommend != null) {
            f.append(" and content.isRecommend=:recommend");
            f.setParam("recommend", recommend);
        }
        appendReleaseDate(f,contentQueryFreshTimeCache);
        appendTypeIds(f, typeIds);
      //  f.append(" and content.status=" + ContentCheck.CHECKED);
        if (!StringUtils.isBlank(title)) {
            f.append(" and ext.title like :title");
            f.setParam("title", "%" + title + "%");
        }
        appendAttr(f, attr);
        return f;
    }





    private void  appendChannelIds(Finder f, Integer[] channelIds) {
        int len;
        if (channelIds != null) {
            len = channelIds.length;
            if (len == 1) {
                f.append(" and content.channel.channelId=:channelId");
                f.setParam("channelId", channelIds[0]);
            } else if (len > 1) {
                f.append(" and content.channel.channelId in (:channelIds)");
                f.setParamList("channelIds", channelIds);
            }
        }
    }


    private void appendReleaseDate(Finder f,ContentQueryFreshTimeCache contentQueryFreshTimeCache) {
        f.append(" and ext.releaseDate<:currentDate");
        f.setParam("currentDate", contentQueryFreshTimeCache.getTime());
    }

    private void appendTypeIds(Finder f, Integer[] typeIds) {
        int len;
        if (typeIds != null) {
            len = typeIds.length;
            if (len == 1) {
                f.append(" and content.type.typeId=:typeId");
                f.setParam("typeId", typeIds[0]);
            } else if (len > 1) {
                f.append(" and content.type.typeId in (:typeIds)");
                f.setParamList("typeIds", typeIds);
            }
        }
    }

    private void appendSiteIds(Finder f, Integer[] siteIds) {
        int len;
        if (siteIds != null) {
            len = siteIds.length;
            if (len == 1) {
                f.append(" and content.site.siteId=:siteId");
                f.setParam("siteId", siteIds[0]);
            } else if (len > 1) {
                f.append(" and content.site.siteId in (:siteIds)");
                f.setParamList("siteIds", siteIds);
            }
        }
    }

    private void appendAttr(Finder f, Map<String,String[]>attr){
        if(attr!=null&&!attr.isEmpty()){
            Set<String> keys=attr.keySet();
            Iterator<String> keyIterator=keys.iterator();
            while(keyIterator.hasNext()){
                String key=keyIterator.next();
                String[] mapValue=attr.get(key);
                String value=mapValue[0],operate=mapValue[1];
                if(StringUtils.isNotBlank(key)&&StringUtils.isNotBlank(value)){
                    if(operate.equals(PARAM_ATTR_EQ)){
                        f.append(" and content.attr[:k"+key+"]=:v"+key).setParam("k"+key, key).setParam("v"+key, value);
                    }else if(operate.equals(PARAM_ATTR_START)){
                        f.append(" and content.attr[:k"+key+"] like :v"+key).setParam("k"+key, key).setParam("v"+key, value+"%");
                    }else if(operate.equals(PARAM_ATTR_END)){
                        f.append(" and content.attr[:k"+key+"] like :v"+key).setParam("k"+key, key).setParam("v"+key, "%"+value);
                    }else if(operate.equals(PARAM_ATTR_LIKE)){
                        f.append(" and content.attr[:k"+key+"] like :v"+key).setParam("k"+key, key).setParam("v"+key, "%"+value+"%");
                    }else if(operate.equals(PARAM_ATTR_IN)){
                        if(StringUtils.isNotBlank(value)){
                            f.append(" and content.attr[:k"+key+"] in (:v"+key+")").setParam("k"+key, key);
                            f.setParamList("v"+key, value.split(","));
                        }
                    }
                    else {
                        //取绝对值比较大小
                        Float floatValue=Float.valueOf(value);
                        if(operate.equals(PARAM_ATTR_GT)){
                            if(floatValue>=0){
                                f.append(" and (content.attr[:k"+key+"]>=0 and abs(content.attr[:k"+key+"])>:v"+key+")").setParam("k"+key, key).setParam("v"+key, floatValue);
                            }else{
                                f.append(" and ((content.attr[:k"+key+"]<0 and abs(content.attr[:k"+key+"])<:v"+key+") or content.attr[:k"+key+"]>=0)").setParam("k"+key, key).setParam("v"+key, -floatValue);
                            }
                        }else if(operate.equals(PARAM_ATTR_GTE)){
                            if(floatValue>=0){
                                f.append(" and (abs(content.attr[:k"+key+"])>=:v"+key+" and content.attr[:k"+key+"]>=0)").setParam("k"+key, key).setParam("v"+key, floatValue);
                            }else{
                                f.append(" and ((abs(content.attr[:k"+key+"])<=:v"+key+" and content.attr[:k"+key+"]<0) or content.attr[:k"+key+"]>=0)").setParam("k"+key, key).setParam("v"+key, -floatValue);
                            }
                        }else if(operate.equals(PARAM_ATTR_LT)){
                            if(floatValue>=0){
                                f.append(" and ((abs(content.attr[:k"+key+"])<:v"+key+" and content.attr[:k"+key+"]>=0) or content.attr[:k"+key+"]<=0)").setParam("k"+key, key).setParam("v"+key, floatValue);
                            }else{
                                f.append(" and ((abs(content.attr[:k"+key+"])>:v"+key+" and content.attr[:k"+key+"]<0) or content.attr[:k"+key+"]>=0)").setParam("k"+key, key).setParam("v"+key, -floatValue);
                            }
                        }else if(operate.equals(PARAM_ATTR_LTE)){
                            if(floatValue>=0){
                                f.append(" and ((abs(content.attr[:k"+key+"])<=:v"+key+" and content.attr[:k"+key+"]>=0) or content.attr[:k"+key+"]<=0)").setParam("k"+key, key).setParam("v"+key, floatValue);
                            }else{
                                f.append(" and ((abs(content.attr[:k"+key+"])>=:v"+key+" and content.attr[:k"+key+"]<0) or content.attr[:k"+key+"]>=0)").setParam("k"+key, key).setParam("v"+key, -floatValue);
                            }
                        }
                    }
                }
            }
        }
    }

    //排序
    public static void appendOrder(Finder f, int orderBy) {
        switch (orderBy) {
            case 1:
                // 综合(截止时间升序)
                f.append(" order by bean.deadlineDt asc");
                break;
            case 2:
                // 发布时间降序
                f.append(" order by bean.releaseDt asc");
                break;
            case 3:
                // 截止时间降序
                f.append(" order by bean.deadlineDt desc");
                break;

            default:
                // 默认： (发布时间升序)
                f.append(" order by bean.releaseDt desc");
        }
    }

    protected Class<SAbility> getEntityClass() {
        return SAbility.class;
    }
    @Resource
    private ContentQueryFreshTimeCache contentQueryFreshTimeCache;

    @Resource
    private PubCodeDao pubCodeDao;
}
