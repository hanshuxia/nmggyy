package com.anchorcms.icloud.dao.commservice.impl;

import com.anchorcms.common.hibernate.Finder;
import com.anchorcms.common.hibernate.HibernateBaseDao;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.icloud.dao.commservice.CommserviceCreateDao;
import com.anchorcms.icloud.model.commservice.*;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by ${user} on 2017/1/13.
 */
@Repository
public class CommserviceCreateDaoImpl extends HibernateBaseDao<SAmplePolicy,Integer> implements CommserviceCreateDao {
    /**
     * @author gengwenlong
     * @Date 2017/1/14 10:28
     * @return
     * 惠补政策
     */
    public Pagination getSearchHuibu(Integer[] siteIds, Integer[] typeIds, Boolean titleImg, Boolean recommend, String title,
                                   Map<String, String[]> attr, int orderBy, int pageNo, int pageSize, String[] params) {
        Finder f = Finder.create("select bean from SAmplePolicy bean  ");
        f.append( " where 1=1 and bean.selectedStatus=2 " );
        if(params.length>0) {
            if (params[0] != null && (params[0].trim()).length() > 0 && !("undefined".equals(params[0]))) {
                String addrCity = "%%";
                if (params[0].equals("hhht")) {
                    addrCity = "呼和浩特市";
                }
                if (params[0].equals("bt")) {
                    addrCity = "包头市";
                }
                if (params[0].equals("eeds")) {
                    addrCity = "鄂尔多斯市";
                }
                if (params[0].equals("wlcb")) {
                    addrCity = "乌兰察布市";
                }
                if (params[0].equals("wh")) {
                    addrCity = "乌海市";
                }
                if (params[0].equals("hlbe")) {
                    addrCity = "呼伦贝尔市";
                }
                if (params[0].equals("tl")) {
                    addrCity = "通辽市";
                }
                if (params[0].equals("cf")) {
                    addrCity = "赤峰市";
                }
                if (params[0].equals("byze")) {
                    addrCity = "巴彦淖尔市";
                }
                if (params[0].equals("xlglm")) {
                    addrCity = "锡林郭勒盟";
                }
                if (params[0].equals("alsm")) {
                    addrCity = "阿拉善盟";
                }
                if (params[0].equals("xam")) {
                    addrCity = "兴安盟";
                }
                if (params[0].equals("qb")) {
                    addrCity = "%%";
                    f.append(" and (bean.address like :addrCity or bean.address is null ) ");
                    f.setParam("addrCity", addrCity);
                }else{
                    f.append(" and bean.address like :addrCity");
                    f.setParam("addrCity", addrCity);
                }
                if((params[2].trim()).length()>0 && !("undefined".equals(params[2])) ){
                    orderBy = Integer.parseInt(params[2]);
                }
                if((params[3].trim()).length()>0 && !("undefined".equals(params[3])) ){
                    f.append(" and bean.policyName like '%"+params[3]+"%' ");
                }
            }
        }
        switch (orderBy) {
            /*case 2:
                // 截止时间降序
                f.append(" order by bean.deadlineDt desc");
                break;*/
            case 1:
                // 发布时间升序
                f.append(" order by bean.releaseDt asc");
                break;
            default:
                // 默认：发布时间降序
                f.append(" order by bean.releaseDt desc");
                break;
            /*default:
                // 默认： 截止时间升序
                f.append(" order by bean.deadlineDt asc");*/
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
            if(params[3].length() > 0){
                p.setSearchFour(params[3]);
            }
        }
        return p;
    }
    /**
     * @author liuyang
     * @Date 2017/12/12 11:09
     * @return
     */
    public Pagination getSearchZhengWu(Integer[] siteIds, Integer[] typeIds, Boolean titleImg, Boolean recommend, String title,
                                       Map<String, String[]> attr, int orderBy, int pageNo, int pageSize, String[] params){
        Finder f = Finder.create("select bean from SStiteManager bean  ");
        f.append( " where 1=1 and bean.status=3 " );
        if(params.length>0) {
            if (params[0] != null && (params[0].trim()).length() > 0 && !("undefined".equals(params[0]))) {
                String address = "%%";
                if (params[0].equals("hhht")) {
                    address = "呼和浩特市";
                }
                if (params[0].equals("nmgzzqzf")) {
                    address = "内蒙古自治区政府";
                }
                if (params[0].equals("zfcgw")) {
                    address = "政府采购网";
                }
                if (params[0].equals("bt")) {
                    address = "包头市";
                }
                if (params[0].equals("eeds")) {
                    address = "鄂尔多斯市";
                }
                if (params[0].equals("wlcb")) {
                    address = "乌兰察布市";
                }
                if (params[0].equals("wh")) {
                    address = "乌海市";
                }
                if (params[0].equals("hlbe")) {
                    address = "呼伦贝尔市";
                }
                if (params[0].equals("tl")) {
                    address = "通辽市";
                }
                if (params[0].equals("cf")) {
                    address = "赤峰市";
                }
                if (params[0].equals("byze")) {
                    address = "巴彦淖尔市";
                }
                if (params[0].equals("xlglm")) {
                    address = "锡林郭勒盟";
                }
                if (params[0].equals("alsm")) {
                    address = "阿拉善盟";
                }
                if (params[0].equals("xam")) {
                    address = "兴安盟";
                }
                if (params[0].equals("qb")) {
                    address = "%%";
                    f.append(" and (bean.address like :address or bean.address is null ) ");
                    f.setParam("address", address);
                }else{
                    f.append(" and bean.address like :address");
                    f.setParam("address", address);
                }
                if((params[2].trim()).length()>0 && !("undefined".equals(params[2])) ){
                    orderBy = Integer.parseInt(params[2]);
                }
                if((params[3].trim()).length()>0 && !("undefined".equals(params[3])) ){
                    f.append(" and bean.stiteName like '%"+params[3]+"%' ");
                }
            }
            if((params[3].trim()).length()>0 && !("undefined".equals(params[3])) ){
                try {
                    String lll= URLDecoder.decode(params[3],"utf-8");
                    f.append(" and bean.stiteName like '%"+lll+"%' ");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }



            }
        }

        switch (orderBy) {
            /*case 2:
                // 截止时间降序
                f.append(" order by bean.deadlineDt desc");
                break;*/
            case 1:
                // 发布时间升序
                f.append(" order by bean.createrDt asc");
                break;
            default:
                // 默认：发布时间降序
                f.append(" order by bean.createrDt desc");
                break;
            /*default:
                // 默认： 截止时间升序
                f.append(" order by bean.deadlineDt asc");*/
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
            if(params[3].length() > 0){
                p.setSearchFour(params[3]);
            }
        }
        return p;
    }
    /**
     * @author gengwenlong
     * @Date 2017/1/13 16:12
     * @return
     * 项目招投标
     */
    public Pagination getSearchTender(Integer[] siteIds, Integer[] typeIds, Boolean titleImg, Boolean recommend, String title, Map<String, String[]> attr, int orderBy, int pageNo, int pageSize, String[] params) {
        Finder f;
        Pagination p;
        if(params.length==0){
            f = Finder.create("select bean from STenderTrailer bean  ");
            f.append( " where 1=1  " );
            if(params.length>0) {
                if (params[0] != null && (params[0].trim()).length() > 0 && !("undefined".equals(params[0]))) {
//                    String addrCity = "%%";
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
                    f.setParam("addrProvince", addrProvince);
                }
            }
            for(int i = 0;i < params.length; i ++ ){
                params[i] = params[i].trim();
            }
            //发布时间降序
            f.append(" order by bean.releaseDt desc");
            f.setCacheable(true);
            int totalCount = countQueryResult(f);
            p = new Pagination(pageNo, pageSize, totalCount);
            p.setSearchThree("1");
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
        }else{
            if(params[2].equals("zhaobiaoyugao")){
                f = Finder.create("select bean from STenderTrailer bean  ");
                f.append( " where 1=1  " );
                if(params.length>0) {
                    if (params[0] != null && (params[0].trim()).length() > 0 && !("undefined".equals(params[0]))) {
//                        String addrCity = "%%";
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
                        f.setParam("addrProvince", addrProvince);
                    }
                }
                for(int i = 0;i < params.length; i ++ ){
                    params[i] = params[i].trim();
                }
                //发布时间降序
                f.append(" order by bean.releaseDt desc");
                f.setCacheable(true);
                int totalCount = countQueryResult(f);
                p = new Pagination(pageNo, pageSize, totalCount);
                p.setSearchThree("1");
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
            }else if(params[2].equals("zhaobiaogonggao")){
                f = Finder.create("select bean from STenderNotice bean  ");
                f.append( " where 1=1  " );
                if(params.length>0){
                    if(params[0] != null && (params[0].trim()).length()>0 && !("undefined".equals(params[0])) ){
//                        String addrCity = "%%";
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
                        f.setParam("addrProvince",addrProvince);
                    }
                }
                for(int i = 0;i < params.length; i ++ ){
                    params[i] = params[i].trim();
                }
                //发布时间降序
                f.append(" order by bean.releaseDt desc");
                f.setCacheable(true);
                int totalCount = countQueryResult(f);
                p = new Pagination(pageNo, pageSize, totalCount);
                p.setSearchThree("2");
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
            }else{
                f = Finder.create("select bean from SBidNotice bean  ");
                f.append( " where 1=1  " );
                if(params.length>0) {
                    if (params[0] != null && (params[0].trim()).length() > 0 && !("undefined".equals(params[0]))) {
//                        String addrCity = "%%";
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
                        f.setParam("addrProvince", addrProvince);
                    }
                }
                for(int i = 0;i < params.length; i ++ ){
                    params[i] = params[i].trim();
                }
                //发布时间降序
                f.append(" order by bean.releaseDt desc");
                f.setCacheable(true);
                int totalCount = countQueryResult(f);
                p = new Pagination(pageNo, pageSize, totalCount);
                p.setSearchThree("3");
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
            }
        }
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
            /*if(params[2].length() > 0){
                p.setSearchThree(params[2]);
            }*/
            if(params[3].length() > 0){
                p.setSearchFour(params[3]);
            }
        }
        return p;
    }
    /**
     * @author machao
     * @Date 2017/1/15 12:40
     * @return
     * 发布中标公告
     */
    public SBidNotice save(SBidNotice bean) {
        getSession().save(bean);
        return bean;
    }
    /**
     * @author machao
     * @Date 2017/1/17 10:59
     * @return
     * 自定义标签-招标公告
     */
    public List<STenderNotice> getStenderNoticeList(Integer count, Integer orderBy, Integer listType){

        Finder f = Finder.create(" select  bean from STenderNotice bean  ");
        if(orderBy!=null){
            f.append("  ");
        }
        if(count!=null){
            f.setMaxResults(count);
        }
        return find(f);
    }

    /**
     * @author machao
     * @Date 2017/1/17 10:59
     * @return
     * 自定义标签-招标预告
     */
    public List<STenderTrailer> getStenderTrailerList(Integer count, Integer orderBy, Integer listType){

        Finder f = Finder.create(" select  bean from STenderTrailer bean ");
        if(orderBy!=null){
            f.append("  ");
        }
        if(count!=null){
            f.setMaxResults(count);
        }
        return find(f);
    }
    /**
     * @author machao
     * @Date 2017/1/17 10:59
     * @return
     * 自定义标签-中标公告
     */
    public List<SBidNotice> getSbidNoticeList(Integer count, Integer orderBy, Integer listType){
        Finder f = Finder.create(" select  bean from SBidNotice bean  ");
        if(orderBy!=null){
            f.append("  ");
        }
        if(count!=null){
            f.setMaxResults(count);
        }
        return find(f);
    }
    /**
     * @author machao
     * @Date 2017/1/17 10:59
     * @return
     * 自定义标签-政务导航
     */
    public List<SStiteManager> getSstiteManagerList(Integer count, Integer orderBy, Integer listType){
        Finder f = Finder.create(" select  bean from SStiteManager bean  ");
        if(count!=null){
            f.setMaxResults(count);
        }
        f.setCacheable(true);
//        long a=System.currentTimeMillis();
        return find(f);
//        System.out.println("\r<br>执行耗时 : "+(System.currentTimeMillis()-a)/1000f+" 秒 ");
    }

    @Override
    protected Class<SAmplePolicy> getEntityClass() {
        return SAmplePolicy.class;
    }
}
