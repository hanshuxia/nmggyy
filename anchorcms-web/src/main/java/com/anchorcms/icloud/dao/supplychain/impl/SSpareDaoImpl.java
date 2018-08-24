package com.anchorcms.icloud.dao.supplychain.impl;

import com.anchorcms.common.hibernate.Finder;
import com.anchorcms.common.hibernate.HibernateBaseDao;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.icloud.dao.common.PubCodeDao;
import com.anchorcms.icloud.dao.supplychain.SSpareDao;
import com.anchorcms.icloud.model.common.PubCode;
import com.anchorcms.icloud.model.supplychain.SSpare;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author zhouyq
 * @Email
 * @Date 2016/12/20
 * @Desc 备品备件dao层实现类
 */
@Repository
public class SSpareDaoImpl extends HibernateBaseDao<SSpare, Integer> implements SSpareDao {
    /**
     * @Author zhouyq
     * @Date 2016/12/20
     * @param spareType, spareName, spareDesc, companyType, addrCity, pageNo, pageSize
     * @return
     * @Desc 获取备品备件分页后信息
     */
    public Pagination getPage(String spareType, String spareName, String spareDesc, String companyType, String addrCity, String addrProvince,
                                  Integer pageNo, Integer pageSize) {
        Finder f = getFinder(spareType, spareName, spareDesc, companyType, addrCity , addrProvince);
        f.setCacheable(true);
        return find(f, pageNo, pageSize);
    }
    public Pagination getSSpareStorageList(String spareId, Integer pageNo, Integer pageSize) {
        Finder f = Finder.create("from SSpareStorage bean where 1=1");
        if (spareId != null && spareId.trim().length()> 0) {
            f.append(" and bean.spareId =:spareId");
            f.setParam("spareId", spareId);
        }
        f.append(" order by bean.createDt desc");
        f.setCacheable(true);
        return find(f, pageNo, pageSize);
    }

    /*
    刘鹏
    备品备件信息上传
    * */
    public SSpare save(SSpare sse) {
        getSession().save(sse);
        return sse;
    }

    /**
     * @Author zhouyq
     * @Date 2016/12/20
     * @param spareType, spareName, spareDesc, companyType, addrCity, pageNo, pageSize
     * @return
     * @Desc 获取备品备件列表信息
     */
    private Finder getFinder(String spareType, String spareName, String spareDesc, String companyType, String addrCity,String addrProvince){
        Finder f = Finder.create("from SSpare bean where 1=1");
        if (spareType != null && spareType.trim().length()> 0) {
            f.append(" and bean.spareType =:spareType");
            f.setParam("spareType", spareType);
        }
        if (spareName != null && spareName.trim().length() > 0) {
            f.append(" and bean.spareName like:spareName");
            f.setParam("spareName", "%" + spareName + "%");
        }
        if (spareDesc != null && spareDesc.trim().length() > 0) {
            f.append(" and bean.spareDesc =:spareDesc");
            f.setParam("spareDesc", spareDesc);
        }
        if (companyType != null && companyType.trim().length() > 0) {
            f.append(" and bean.companyType =:companyType");
            f.setParam("companyType", companyType);
        }
        if (addrCity != null && addrCity.trim().length() > 0) {
            f.append(" and bean.addrCity =:addrCity");
            f.setParam("addrCity", addrCity);
        }
        if (addrProvince != null && addrProvince.trim().length() > 0) {
            f.append(" and bean.addrProvince =:addrProvince");
            f.setParam("addrProvince", addrProvince);
        }
        return f;
    }
    /**
     * @Author zhouyq
     * @Date 2017/01/07
     * @param
     * @return
     * @Desc 获取备品备件列表信息（公共方法）
     */
    public Pagination getPageBySiteIdBpbjById(Integer[] siteIds, Integer[] typeIds, Boolean titleImg, Boolean recommend, String title, Map<String, String[]> attr, int orderBy, int pageNo, int pageSize, String[] params) {
        Finder f = Finder.create("select bean from SSpare  bean  where bean.status=3 and (bean.publicType=0 or bean.publicType=1)");
//        f.append(" and bean.deadlineDt >= curdate()"); // 截止日期大于等于当前日期
        if (params.length > 0) {
            //能力分类
            if (params[0] != null && (params[0].trim()).length() > 0 && !("undefined".equals(params[0]))) {
                String typeKey = params[0];
                String spareType = "-";
                if ("qb".equals(typeKey)) {
                    spareType = "%%";
                } else {
                    PubCode pc = pubCodeDao.findUniqueCode("BPBJLX", typeKey);
                    if (pc != null) {
                        int level = pc.getLevel();
                        String keyHead = pc.getKey().substring(0, level * 2);
                        spareType = keyHead + "%";
                    }
                }
                f.append(" and bean.spareType like :spareType");
                f.setParam("spareType", spareType);
            }

            // 地区
            if (params[1] != null && (params[1].trim()).length() > 0 && !("undefined".equals(params[1]))) {
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
            //排序
            if (params[2] != null && (params[2].trim()).length() > 0 && !("undefined".equals(params[2]))) {
                orderBy = Integer.parseInt(params[2]);
            }
        }
        switch (orderBy) {
            case 1:
                // ID升序
                f.append(" order by bean.sparepartsId asc");
                break;
            case 2:
                // 发布时间降序
                f.append(" order by bean.releaseDt asc");
                break;
            case 3:
                // 发布时间升序
                f.append(" order by bean.sparepartsId desc");
                break;

            default:
                // 默认： ID降序
                f.append(" order by bean.releaseDt desc");
        }

        f.setCacheable(true);
        int totalCount = countQueryResult(f);
        Pagination p = new Pagination(pageNo, pageSize, totalCount);
        //当存在查询条件的情况下把查询条件返回前台提供下次查询使用
        /**
         *把查询条件返回前台
         */
        if (params.length > 0) {
            if (params[0] != null && params[0].length() > 0) {
                p.setSearchOne(params[0]);
            }
            if (params[1] != null && params[1].length() > 0) {
                p.setSearchTwo(params[1]);
            }
            if (params[2] != null && params[2].length() > 0) {
                p.setSearchThree(params[2]);
            }
        }
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

        return p;
    }

    /**
     * @return
     * @Author 李洪波
     * @Desc 根据创建时间，备品分类，来源和所属企业来分类并分页
     */
    public Pagination findByTimeCateSourCompany(Date startDate, Date endDate, String publicType, String status, String spareType, String source, String companyId, Integer pageNo, Integer pageSize, Integer userId, String flag) {
        Finder ff = Finder.create(" select bean from SSpare bean");
        if (flag==null || flag.equals("") || flag.equals("0")){
            ff.append(" where bean.company.companyId=:companyId");
            ff.setParam("companyId", companyId);
        }else if(flag.equals("1")) {
            ff.append(" where 1=1");
        }
        if (status != null && status.trim().length() > 0) {//被驳回+草稿+待审核+已发布
            if(status.equals("1")){
                ff.append(" and bean.status in('0','1')");
            }else{
                ff.append(" and bean.status =:status");
                ff.setParam("status",status);
            }
        }
        if (publicType != null && publicType.trim().length() > 0) {//全部+已公开+未公开
            ff.append(" and bean.publicType =:publicType");
            ff.setParam("publicType", publicType);
        }
        if (startDate != null) {
            ff.append(" and bean.createDt >=:startDate");
            ff.setParam("startDate", startDate);
        }
        if (endDate != null) {
            ff.append(" and bean.createDt <=:endDate");
            ff.setParam("endDate", endDate);
        }
        if (spareType != null && spareType.trim().length() > 0) {
            ff.append(" and bean.spareType like '%" + spareType + "%'");
        }
        if (source != null && source.trim().length() > 0) {
           ff.append(" and bean.source like '%" + source + "%'");
       }
            ff.append(" order by bean.createDt desc");
        pageSize=20;
        ff.setCacheable(true);
        return find(ff, pageNo, pageSize);
    }

    /**
     * @return
     * @author李洪波
     * @Desc 根据ID查询备品备件信息
     */
    public SSpare findByID(String id) {
        return null;
    }

    /**
     * @return
     * @author李洪波
     * @Desc 备品备件公开状态修改
     */
    public void updateSSpare(String id, String publicType) {
        StringBuffer hql = new StringBuffer();
        String[] strArr = id.split(","); // id字符串数组

        for (int j = 0; j < strArr.length; j ++) { // 批量执行update
            String spareId =strArr[j].trim();
            hql.append("update SSpare set publicType= '"+publicType+"' where sparepartsId= '"+spareId+"'");
            Query query = getSession().createQuery(hql.toString());
            query.executeUpdate();
            hql.setLength(0);
             }
    }

    /**
     * @Author李洪波
     * @Desc 备品备件发布状态修改
     */
    public void updateSpareStatus(String id, String status, Date date, String flag) {
        SSpare s= (SSpare) this.getSession().get(SSpare.class, id);
        if (s!=null){
            if(flag.equals("1"))
            {
            s.setStatus("3");
            }else{
                s.setStatus("2");
            }
            s.setCreateDt(date);
        }
    }

    /**
     * @author李洪波
     * @Desc根据ID删除备品备件
     * @param bean
     */
    public SSpare delete(SSpare bean) {
        if (bean!=null){
        getSession().delete(bean);
        }
        return bean;
    }

    protected Class<SSpare> getEntityClass() {
        return SSpare.class;
    }
    @Resource
    private SSpareDao spareDao;
    @Resource
    private PubCodeDao pubCodeDao;

}
