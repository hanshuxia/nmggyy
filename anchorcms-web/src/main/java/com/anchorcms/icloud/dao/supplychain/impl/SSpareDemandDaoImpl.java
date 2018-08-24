package com.anchorcms.icloud.dao.supplychain.impl;


import com.anchorcms.common.hibernate.Finder;
import com.anchorcms.common.hibernate.HibernateBaseDao;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.icloud.dao.common.PubCodeDao;
import com.anchorcms.icloud.dao.supplychain.SSpareDemandDao;
import com.anchorcms.icloud.model.common.PubCode;
import com.anchorcms.icloud.model.supplychain.*;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by ${耿文龙} on 2016/12/21.
 */
@Repository
public class SSpareDemandDaoImpl extends HibernateBaseDao<SSpareDemand,Integer> implements SSpareDemandDao {
    /**
     * @author gengwenlong
     * @Date 2017/1/4 18:57
     * @return报价
     */
    public SRepairQuote save(SRepairQuote srq) {
        getSession().save(srq);
        return srq;
    }
    /**
     * @author gengwenlong
     * @Date 2017/1/9 10:27
     * 报价检索
     * @return
     */
    public List<SRepairDemand> getQuoteSearch(String repairId) {
        StringBuffer hql =  new StringBuffer(" from SRepairDemand s  ");
        hql.append(" where s.repairId = '"+repairId+"'");
        Query query = getSession().createQuery(hql.toString());
        List<SRepairDemand> list = query.list();
        return list;
    }

    /**
     * @author gengwenlong
     * @Date 2017/1/4 18:57
     * @return金牌老师傅列表展示
     */
    public Pagination getAllSearch(Integer[] siteIds, Integer[] typeIds, Boolean titleImg, Boolean recommend, String title,
                                   Map<String, String[]> attr, int orderBy, int pageNo, int pageSize, String[] params) {
        Finder f = Finder.create("select bean from SRepairRes bean  ");
        f.append( " where 1=1 and bean.isRecommend=1 and bean.status = 3" );
        java.sql.Date currentDate = new java.sql.Date(System.currentTimeMillis());//获取当前时间
        f.append(" and bean.deadlineDt>:currentDate ");
        f.setParam("currentDate",currentDate);

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
                f.setParam("addrProvince",addrProvince);
            }
            if(params[1] != null && (params[1].trim()).length()>0 && !("undefined".equals(params[1])) ){
                String repairType = "%%";
                if(params[1].equals("qb")){
                    repairType="%%";
                } if(params[1].equals("440000")){
                    repairType="440000";
                } if(params[1].equals("450000")){
                    repairType="450000";
                } if(params[1].equals("460000")){
                    repairType="460000";
                } if(params[1].equals("470000")){
                    repairType="470000";
                } if(params[1].equals("480000")){
                    repairType="480000";
                }
                f.append(" and bean.repairType like :repairType");
                f.setParam("repairType",repairType);
            }
            if(params[3] != null && (params[3].trim()).length()>0 && !("undefined".equals(params[3])) ){
                orderBy = Integer.parseInt(params[3]);
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
            /*if(params[4].length() > 0){
                p.setSearchFive(params[4]);
            }*/
        }
        return p;
    }
    /**
     * @author gengwenlong
     * @Date 2017/1/4 18:58
     * @return金牌老师傅查看详情
     */
    public List<SRepairRes> getSearch(String repairId) {
        StringBuffer hql =  new StringBuffer(" from SRepairRes s  ");
        hql.append(" where s.repairId = '"+repairId+"'");
        Query query = getSession().createQuery(hql.toString());
        List<SRepairRes> list = query.list();
        return list;
    }

    /**
     * @author gengwenlong
     * @Date 2017/1/4 17:50
     * @return
     * 金牌老师傅列表展示分页
     */

    public Pagination getPageBySelf(Integer channelId, Integer siteId, Integer modelId, Integer memberId, int pageNo, int pageSize, Date releaseDt, Date deadlineDt, Integer demandId, String inquiryTheme, Integer UserId, String status, String payType, String statusType) {
        Finder f = Finder.create("select  bean from SRepairRes bean");
        //  f.append(" join bean.content content");
        f.append(" where 1=1");
        //  f.setParam("userId",UserId);
        //添加查询条件
//        appendQuery(f,inquiryTheme,status,statusType);
        return find(f, pageNo, pageSize);
    }

//    public Pagination getPageBySiteIdBpbjqgById(Integer[] siteIds, Integer[] typeIds, Boolean titleImg, Boolean recommend, String title, Map<String, String[]> attr, int orderBy, int pageNo, int pageSize, String[] params) {
//        return null;
//    }

/**
 * @author 苏和 13739980760
 * @Date 2017/1/5 18:38
 * @return
 * 备品备件求购展示检索
 */

    public SSpareDemand findById(Integer id) {
        SSpareDemand spareDemand = get(id);
        return spareDemand;
    }



    /**
     * @author 苏和 13739980760
     * @Date 2017/1/23 17:12
     * @return
     * 备品备件求购列表展示检索
     */
    public Pagination getPageBySiteIdBpbjqgById(Integer[] siteIds, Integer[] typeIds, Boolean titleImg, Boolean recommend, String title, Map<String, String[]> attr, int orderBy, int pageNo, int pageSize, String[] params) {
//        Finder f = Finder.create("select bean from SSpareDemand bean  ");
//        f.append( " where 1=1 and bean.state='3' " );
        Finder f = Finder.create("select bean from SSpareDemand  bean inner join fetch bean.spareDemandObjLists s where  bean.state= '3'");
        f.append(" and DATEDIFF(bean.deadlineDt,now())>=0 ");
//        for(int i = 0;i < params.length; i ++ ){
//            params[i] = params[i].trim();
//        }
        if(params.length>0){
            if((params[0].trim()).length()>0 && !("undefined".equals(params[0])) ){

                String typeKey = params[0];
                String requestType = "-";
                if ("qb".equals(typeKey)) {
                    requestType = "%%";
                } else {
                    PubCode pc = pubCodeDao.findUniqueCode("BPBJLX", typeKey);
                    if (pc != null) {
                        int level = pc.getLevel();
                        String keyHead = pc.getKey().substring(0, level * 2);
                        requestType = keyHead + "%";
                    }
                }
                f.append(" and bean.requestType like :requestType");
                f.setParam("requestType", requestType);

//                f.append(" and bean.requestType like '%"+params[0]+"%' ");
                /* f.append(" and bean.addrCity=:addrCity");
                f.setParam("addrCity",params[0]);*/
            }
            if((params[1].trim()).length()>0 && !("undefined".equals(params[1])) ){
                String isUrgency = "%%";
                if(params[1].equals("0")){
                    isUrgency="0";
                } if(params[1].equals("1")){
                    isUrgency="1";
                } if(params[1].equals("2")){
                    isUrgency="2";
                }if(params[1].equals("qb")){
                    isUrgency="%%";
                }
                f.append(" and bean.isUrgency like :isUrgency");
                f.setParam("isUrgency",isUrgency);
//                f.append(" and bean.isUrgency like '%"+params[1]+"%' ");
            }
            if((params[2].trim()).length()>0 && !("undefined".equals(params[2])) ){
                String addrCity = "%%";
                String addrProvince = "%%";
                if (params[2].equals("bjs")) {
                    addrProvince = "北京市";
                }
                if (params[2].equals("tjs")) {
                    addrProvince = "天津市";
                }
                if (params[2].equals("hbs")) {
                    addrProvince = "河北省";
                }
                if (params[2].equals("nmgzzq")) {
                    addrProvince = "内蒙古自治区";
                }
                if (params[2].equals("lns")) {
                    addrProvince = "辽宁省";
                }
                if (params[2].equals("jls")) {
                    addrProvince = "吉林省";
                }
                if (params[2].equals("hljs")) {
                    addrProvince = "黑龙江省";
                }
                if (params[2].equals("shs")) {
                    addrProvince = "上海市";
                }
                if (params[2].equals("jss")) {
                    addrProvince = "江苏省";
                }
                if (params[2].equals("zjs")) {
                    addrProvince = "浙江省";
                }
                if (params[2].equals("ahs")) {
                    addrProvince = "安徽省";
                }
                if (params[2].equals("fjs")) {
                    addrProvince = "福建省";
                }
                if (params[2].equals("jxs")) {
                    addrProvince = "江西省";
                }
                if (params[2].equals("sds")) {
                    addrProvince = "山东省";
                }
                if (params[2].equals("hns")) {
                    addrProvince = "河南省";
                }
                if (params[2].equals("hubs")) {
                    addrProvince = "湖北省";
                }
                if (params[2].equals("huns")) {
                    addrProvince = "湖南省";
                }
                if (params[2].equals("gds")) {
                    addrProvince = "广东省";
                }
                if (params[2].equals("gxzzzzq")) {
                    addrProvince = "广西壮族自治区";
                }
                if (params[2].equals("hains")) {
                    addrProvince = "海南省";
                }
                if (params[2].equals("cqs")) {
                    addrProvince = "重庆市";
                }
                if (params[2].equals("scs")) {
                    addrProvince = "四川省";
                }
                if (params[2].equals("gzs")) {
                    addrProvince = "贵州省";
                }
                if (params[2].equals("yns")) {
                    addrProvince = "云南省";
                }
                if (params[2].equals("zzzzq")) {
                    addrProvince = "西藏自治区";
                }
                if (params[2].equals("sxs")) {
                    addrProvince = "陕西省";
                }
                if (params[2].equals("gss")) {
                    addrProvince = "甘肃省";
                }
                if (params[2].equals("qhs")) {
                    addrProvince = "青海省";
                }
                if (params[2].equals("nxhzzzq")) {
                    addrProvince = "宁夏回族自治区";
                }
                if (params[2].equals("xjwwezzzq")) {
                    addrProvince = "新疆维吾尔自治区";
                }
                if (params[2].equals("qb")) {
                    addrProvince = "%%";
                }
                f.append(" and bean.addrProvince like :addrProvince");
                f.setParam("addrProvince",addrProvince);
//                f.append(" and bean.addrCity like '%"+params[2]+"%' ");
               /* f.append(" and bean.goodAt=:goodAt");
                f.setParam("goodAt",params[2]);*/
            }
            if((params[3].trim()).length()>0 && !("undefined".equals(params[3])) ){
                orderBy = Integer.parseInt(params[3]);
            }
            if(params.length>4){
                if((params[4].trim()).length()>0 && !("undefined").equals(params[4])){
                    f.append(" and bean.requestTheme like '%"+params[4]+"%' ");
                   /* f.append(" and bean.repairName=:repairName");// 维修资源名称
                    f.setParam("repairName",params[4]);*/
                }
            }
        }
        switch (orderBy) {
            case 2:
                // 截止时间降序
                f.append(" order by bean.deadlineDt desc");
                break;
            case 1:
                // 发布时间降序
                f.append(" order by bean.releaseDt asc");
                break;
            default:
                // 默认：发布时间升序
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
            /*if(params[4].length() > 0){
                p.setSearchFive(params[4]);
            }*/
        }
        query = getSession().createQuery(f.getOrigHql());
        f.setParamsToQuery(query);
        query.setFirstResult(p.getFirstResult());
        query.setMaxResults(p.getPageSize());
        if (f.isCacheable()) {
            query.setCacheable(true);
        }
        list = query.list();
        p.setList(list);


        return p;
    }
    /**
     * @author gengwenlong
     * @Date 2017/2/13 15:03
     * @return
     * 获取金牌老师傅相似能力的自定义标签
     */
    public List<SRepairRes> getList(Integer count, Integer orderBy, Integer listType, String repairType) {
        Finder f = Finder.create("select  bean from SRepairRes bean where bean.status = 3 and bean.isRecommend=1");
        if(repairType!=null){
            f.append(" and bean.repairType =:repairType");
            f.setParam("repairType",""+repairType+"");
        }
        if (orderBy !=null){
            f.append(" order by bean.releaseDt desc");
        }
        if (count != null) {
            f.setMaxResults(count);
        }
        return find(f);
    }
    /**
     * @author gengwenlong
     * @Date 2017/2/21 9:49
     * @return
     * 判断公司是否已对某维修需求报价
     */
    public Boolean hasQuoted(String repairId, String companyId) {
        Finder f = Finder.create("select bean from SRepairQuote bean where");
        f.append(" bean.demandId=:demandId and bean.company.companyId=:companyId");
        f.setParam("demandId", repairId);
        f.setParam("companyId", companyId);
        if (super.countQueryResult(f) > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected Class<SSpareDemand> getEntityClass() {
        return SSpareDemand.class;
    }

    @Resource
    private PubCodeDao pubCodeDao;
}
