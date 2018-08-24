package com.anchorcms.icloud.dao.supplychain.impl;

import com.anchorcms.common.hibernate.Finder;
import com.anchorcms.common.hibernate.HibernateBaseDao;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.icloud.dao.common.PubCodeDao;
import com.anchorcms.icloud.dao.supplychain.SRepairResDao;
import com.anchorcms.icloud.model.common.PubCode;
import com.anchorcms.icloud.model.supplychain.SRepairRes;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by 刘鹏 on 2016/12/21.
 */
@Repository
public class SRepairResDaoImpl extends HibernateBaseDao<SRepairRes, String> implements SRepairResDao {

    public SRepairRes findById(String id) {
        SRepairRes entity = get(id);
        return entity;
    }
    public List<SRepairRes> findJplsfById(String id) {
        String hql = " from SRepairRes bean where bean.releaseId = '"+id+"' and bean.isRecommend='1' ";
        Query query = getSession().createQuery(hql);
        List<SRepairRes> list = query.list();
        return list;
    }

    public int update(SRepairRes sRepairRes) {
        StringBuffer hql = new StringBuffer("update SRepairRes s set ");


        hql.append("s.releaseName='"+sRepairRes.getReleaseName()+"'");
        hql.append(",s.addrProvince='"+sRepairRes.getAddrProvince()+"'");
        hql.append(",s.addrCity='"+sRepairRes.getAddrCity()+"'");
        hql.append(",s.addrCounty='"+sRepairRes.getAddrCounty()+"'");
        hql.append(",s.addrStreet='"+sRepairRes.getAddrStreet()+"'");
        hql.append(",s.goodAt='"+sRepairRes.getGoodAt()+"'");
        hql.append(",s.workYear='"+sRepairRes.getWorkYear()+"'");
        hql.append(",s.mobile='"+sRepairRes.getMobile()+"'");
        hql.append(",s.repairPrice='"+sRepairRes.getRepairPrice()+"'");
        hql.append(",s.description='"+sRepairRes.getDescription()+"'");
        hql.append("where s.repairId='"+sRepairRes.getRepairId()+"'");

        //  getSession().createQuery(hql);
        return getSession().createQuery(hql.toString()).executeUpdate();
    }
    /**
     * @author machao
     * @Date 2017/1/7 21:33
     * @return
     * 众修资源列表展示
     */

    public Pagination getPageBySiteIdZxzyById(Integer[] siteIds, Integer[] typeIds, Boolean titleImg, Boolean recommend, String title, Map<String, String[]> attr, int orderBy, int pageNo, int pageSize, String[] params) {

        Finder f = Finder.create("select bean from SRepairRes bean ");
        f.append(" where bean.status='3' ");
        if (params.length > 0) {

            if(params[0] != null && (params[0].trim()).length()>0 && !("undefined".equals(params[0])) ) {
                String typeKey = params[0];
                String repairType = "-";
                if ("qb".equals(typeKey)) {
                    repairType = "%%";
                } else {
                    PubCode pc = pubCodeDao.findUniqueCode("ZXFWLX", typeKey);
                    if (pc != null) {
                        int level = pc.getLevel();
                        String keyHead = pc.getKey().substring(0, level * 2);
                        repairType = keyHead + "%";
                    }
                }
                f.append(" and bean.repairType like :repairType");
                f.setParam("repairType", repairType);
            }


            if(params[1] != null && (params[1].trim()).length()>0 && !("undefined".equals(params[1])) ){
                String addrCity = "%%";
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
                f.append(" and bean.addrProvince like :addrProvince");
                f.setParam("addrProvince", addrProvince);
            }
            if (params[2] != null && (params[2].trim()).length() > 0 && !("undefined".equals(params[2]))) {
                orderBy = Integer.parseInt(params[2]);
            }

        }

        switch (orderBy) {
            case 1:
                // 截止时间降序
                f.append(" order by bean.deadlineDt desc");
                break;
            case 2:
                // 发布时间降序
                f.append(" order by bean.releaseDt desc");
                break;
            case 3:
                // 发布时间升序
                f.append(" order by bean.releaseDt desc");
                break;

            default:
                // 默认：截止时间升序
                f.append(" order by bean.releaseDt asc");
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

    @Override
    protected Class<SRepairRes> getEntityClass() {
        return SRepairRes.class;
    }
    @Resource
    private PubCodeDao pubCodeDao;
}
