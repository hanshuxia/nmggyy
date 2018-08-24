package com.anchorcms.icloud.dao.supplychain.impl;

import com.anchorcms.cms.model.main.Content;
import com.anchorcms.cms.model.main.ContentCheck;
import com.anchorcms.common.hibernate.Finder;
import com.anchorcms.common.hibernate.HibernateBaseDao;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.icloud.dao.common.PubCodeDao;
import com.anchorcms.icloud.dao.supplychain.SupplychainCreateDao;
import com.anchorcms.icloud.model.common.PubCode;
import com.anchorcms.icloud.model.supplychain.SRepairDemand;
import com.anchorcms.icloud.model.supplychain.SRepairRes;
import com.anchorcms.icloud.model.supplychain.SSpare;
import com.anchorcms.icloud.model.supplychain.SSpareDemand;
import com.anchorcms.icloud.model.synergy.SCompany;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.anchorcms.cms.model.main.Content.ContentStatus.*;

/**
 * Created by machao on 2016/12/21.
 * 备品备件查询
 */
@Repository
public class SupplychainCreateDaoImpl extends HibernateBaseDao<SSpareDemand, Integer> implements SupplychainCreateDao {

    public List<SSpare> getAll(){
        String hql = " from SSpare ";
        Query query = getSession().createQuery(hql);
        List<SSpare> list = query.list();
        return list;
    }

    public int insertsSpareDemandEntity(SSpareDemand sSpareDemand) {
        getSession().save(sSpareDemand);
        return 0;
    }


    public SRepairRes addResource(SRepairRes srr) {
        this.getSession().save(srr);
        return srr;
    }
    /**
     * @author 苏和 13739980760
     * @Date 2017/1/4 12:31
     * @return
     * 备品备件求购编辑
     */

    public SSpareDemand updateSpareDemandEntity(SSpareDemand su){
//        StringBuffer hql = new StringBuffer("update SSpareDemand s set ");
//        hql.append("s.requestTheme='"+su.getRequestTheme()+"'");
//        hql.append("s.isUrgency='"+su.getIsUrgency()+"'");
//        hql.append("s.expectPrice='"+su.getExpectPrice()+"'");
//        hql.append("s.isShow='"+su.getIsShow()+"'");
//        hql.append("s.dealType='"+su.getDealType()+"'");
//        hql.append("s.payType='"+su.getPayType()+"'");
//        hql.append("s.invoiceType='"+su.getInvoiceType()+"'");
//        hql.append("s.addrProvince='"+su.getAddrProvince()+"'");
//        hql.append("s.addrCity='"+su.getAddrCity()+"'");
//        hql.append("s.addrCounty='"+su.getAddrCounty()+"'");
//        hql.append("s.addrStreet='"+su.getAddrStreet()+"'");
//        hql.append("s.mobile='"+su.getMobile()+"'");
//        hql.append("s.telephone='"+su.getTelephone()+"'");
//        hql.append("s.fax='"+su.getFax()+"'");
//        hql.append("s.email='"+su.getEmail()+"'");
//        hql.append("s.reinspection='"+su.getReinspection()+"'");
//        hql.append("s.dpa='"+su.getDpa()+"'");
//        hql.append("s.factoryInspection='"+su.getFactoryInspection()+"'");
//        hql.append("s.freightBorne='"+su.getFreightBorne()+"'");
//        hql.append("s.releaseId='"+su.getReleaseId()+"'");
//        hql.append("s.companyId='"+su.getCompanyId()+"'");
//        hql.append("s.createrId='"+su.getCreaterId()+"'");
//        hql.append("s.createDt='"+su.getCreateDt()+"'");
//        hql.append("s.updateDt='"+su.getUpdateDt()+"'");
//        hql.append("s.releaseDt='"+su.getReleaseDt()+"'");
//        hql.append("s.deadlineDt='"+su.getDeadlineDt()+"'");
//        hql.append("s.deliverDt='"+su.getDeliverDt()+"'");
//        StringBuffer sql = new StringBuffer("update SSpareDemandObj a set ");
//        sql.append("a.deliverDt='"+su.getDeliverDt()+"'");
//        return getSession().createQuery(hql.toString()).executeUpdate();
        getSession().update(su);
        return su;
    }

    /**
     *
     * @param pageNo
     * @param pageSize
     * @return
     */
    public Pagination getPage(String title, Integer typeId, Integer currUserId,
                              Integer inputUserId, boolean topLevel, boolean recommend,
                              Content.ContentStatus status, Byte checkStep, Integer siteId, Integer modelId,
                              Integer channelId, int orderBy, int pageNo, int pageSize) {
        Finder f = Finder.create("select  bean from Content bean  ");
        if (prepared == status || passed == status || rejected == status) {
            f.append(" join bean.contentCheckSet check");
        }
        if (channelId != null) {
            f.append(" join bean.channel channel,Channel parent");
            f.append(" where (channel.lft between parent.lft and parent.rgt");
            f.append(" and channel.site.siteId=parent.site.siteId");
            f.append(" and parent.channelId=:parentId)");
            f.setParam("parentId", channelId);
        } else if (siteId != null) {
            f.append(" where bean.site.siteId=:siteId  ");
            f.setParam("siteId", siteId);
        } else {
            f.append(" where 1=1");
        }
        if (prepared == status) {
            f.append(" and check.checkStep<:checkStep");
            f.append(" and check.isRejected=false");
            f.setParam("checkStep", checkStep);
        } else if (passed == status) {
            f.append(" and check.checkStep=:checkStep");
            f.append(" and check.isRejected=false");
            f.setParam("checkStep", checkStep);
        } else if (rejected == status) {
            //退回只有本级可以查看
            f.append(" and check.checkStep=:checkStep");
            f.append(" and check.isRejected=true");
            f.setParam("checkStep", checkStep);
        }
        if(modelId!=null){
            f.append(" and bean.model.modelId=:modelId").setParam("modelId", modelId);
        }
        appendQuery(f, title, typeId, inputUserId, status, topLevel, recommend);
        appendOrder(f, orderBy);
        f.setCacheable(true);
        return find(f, pageNo, pageSize);
    }

    /**
     *
     * @param inputUserId
     * @param status
     * @param topLevel
     * @param recommend
     */
    private void appendQuery(Finder f, String title, Integer typeId,
                             Integer inputUserId, Content.ContentStatus status, boolean topLevel,
                             boolean recommend) {
        if (!StringUtils.isBlank(title)) {
            f.append(" and bean.contentExt.title like :title");
            f.setParam("title", "%" + title + "%");
        }
        if (typeId != null) {
            f.append(" and bean.type.typeId=:typeId");
            f.setParam("typeId", typeId);
        }
        if (inputUserId != null&&inputUserId!=0) {
            f.append(" and bean.user.userId=:inputUserId");
            f.setParam("inputUserId", inputUserId);
        }else{
            //输入了没有的用户名的情况
            if(inputUserId==null){
                f.append(" and 1!=1");
            }
        }
        if (topLevel) {
            f.append(" and bean.topLevel>0");
        }
        if (recommend) {
            f.append(" and bean.isRecommend=true");
        }
        if (draft == status) {
            f.append(" and bean.status=:status");
            f.setParam("status", ContentCheck.DRAFT);
        }if (contribute == status) {
            f.append(" and bean.status=:status");
            f.setParam("status", ContentCheck.CONTRIBUTE);
        } else if (checked == status) {
            f.append(" and bean.status=:status");
            f.setParam("status", ContentCheck.CHECKED);
        } else if (prepared == status ) {
            f.append(" and bean.status=:status");
            f.setParam("status", ContentCheck.CHECKING);
        } else if (rejected == status ) {
            f.append(" and bean.status=:status");
            f.setParam("status", ContentCheck.REJECT);
        }else if (passed == status) {
            f.append(" and (bean.status=:checking or bean.status=:checked)");
            f.setParam("checking", ContentCheck.CHECKING);
            f.setParam("checked", ContentCheck.CHECKED);
        } else if (all == status) {
            f.append(" and bean.status<>:status");
            f.setParam("status", ContentCheck.RECYCLE);
        } else if (recycle == status) {
            f.append(" and bean.status=:status");
            f.setParam("status", ContentCheck.RECYCLE);
        } else if (pigeonhole == status) {
            f.append(" and bean.status=:status");
            f.setParam("status", ContentCheck.PIGEONHOLE);
        } else {
            // never
        }
    }

    /**
     * 排序
     * @param f
     * @param orderBy
     */
    private void appendOrder(Finder f, int orderBy) {
        switch (orderBy) {
            case 1:
                // ID升序
                f.append(" order by bean.id asc");
                break;
            case 2:
                // 发布时间降序
                f.append(" order by bean.sortDate desc");
                break;
            case 3:
                // 发布时间升序
                f.append(" order by bean.sortDate asc");
                break;
            case 4:
                // 固顶级别降序、发布时间降序
                f.append(" order by bean.topLevel desc, bean.sortDate desc");
                break;
            case 5:
                // 固顶级别降序、发布时间升序
                f.append(" order by bean.topLevel desc, bean.sortDate asc");
                break;
            case 6:
                // 日访问降序
                f.append(" order by bean.contentCount.viewsDay desc, bean.id desc");
                break;
            case 7:
                // 周访问降序
                f.append(" order by bean.contentCount.viewsWeek desc");
                f.append(", bean.id desc");
                break;
            case 8:
                // 月访问降序
                f.append(" order by bean.contentCount.viewsMonth desc");
                f.append(", bean.id desc");
                break;
            case 9:
                // 总访问降序
                f.append(" order by bean.contentCount.views desc");
                f.append(", bean.id desc");
                break;
            case 10:
                // 日评论降序
                f.append(" order by bean.commentsDay desc, bean.id desc");
                break;
            case 11:
                // 周评论降序
                f.append(" order by bean.contentCount.commentsWeek desc");
                f.append(", bean.id desc");
                break;
            case 12:
                // 月评论降序
                f.append(" order by bean.contentCount.commentsMonth desc");
                f.append(", bean.id desc");
                break;
            case 13:
                // 总评论降序
                f.append(" order by bean.contentCount.comments desc");
                f.append(", bean.id desc");
                break;
            case 14:
                // 日下载降序
                f.append(" order by bean.downloadsDay desc, bean.id desc");
                break;
            case 15:
                // 周下载降序
                f.append(" order by bean.contentCount.downloadsWeek desc");
                f.append(", bean.id desc");
                break;
            case 16:
                // 月下载降序
                f.append(" order by bean.contentCount.downloadsMonth desc");
                f.append(", bean.id desc");
                break;
            case 17:
                // 总下载降序
                f.append(" order by bean.contentCount.downloads desc");
                f.append(", bean.id desc");
                break;
            case 18:
                // 日顶降序
                f.append(" order by bean.upsDay desc, bean.id desc");
                break;
            case 19:
                // 周顶降序
                f.append(" order by bean.contentCount.upsWeek desc");
                f.append(", bean.id desc");
                break;
            case 20:
                // 月顶降序
                f.append(" order by bean.contentCount.upsMonth desc");
                f.append(", bean.id desc");
                break;
            case 21:
                // 总顶降序
                f.append(" order by bean.contentCount.ups desc, bean.id desc");
                break;
            case 22:
                // 推荐级别降序、发布时间降序
                f.append(" order by bean.recommendLevel desc, bean.sortDate desc");
                break;
            case 23:
                // 推荐级别升序、发布时间降序
                f.append(" order by bean.recommendLevel asc, bean.sortDate desc");
                break;
            default:
                // 默认： ID降序
                f.append(" order by bean.id desc");
        }
    }
    public List<SSpareDemand> getAllSpare() {
        String hql = " from SSpareDemand sp, SSpareDemandObj sd where sp.demandId = sd.demandId";
        Query query = getSession().createQuery(hql);
        List<SSpareDemand> list = query.list();
        return list;
    }
    /**
     * @author 苏和 13739980760
     * @Date 2017/1/4 12:32
     * @return
     * 备品备件求购检索
     */
    public List<SSpareDemand> search(String theme) {
        StringBuffer hql = new StringBuffer();
        hql.append(" from SSpareDemand sp, SSpareDemandObj sd where sp.demandId = sd.demandId ");
        if (theme!=null){
            try {
                theme = java.net.URLDecoder.decode(theme,"utf-8");
                hql.append(" and sp.requestTheme like '%"+theme+"%' ");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        Query query = getSession().createQuery(hql.toString());
        List<SSpareDemand> list = query.list();
        return list;
    }
    /**
     * @author 苏和 13739980760
     * @Date 2017/1/4 12:32
     * @return
     * 需求方备品备件求购管理展示
     */
    public List<SSpareDemand> BpbjqgManagelist() {
        String hql = " from SSpareDemand sp, SSpareDemandObj sd where sp.demandId = sd.demandId";
        Query query = getSession().createQuery(hql);
        List<SSpareDemand> list = query.list();
        return list;
    }
    /**
     * @author 苏和 13739980760
     * @Date 2017/1/4 12:32
     * @return
     * 需求方备品备件求购管理检索
     */
    public List<SSpareDemand> BpbjqgManagelistSearch(String theme, String type) {
        StringBuffer hql = new StringBuffer();
        hql.append(" from SSpareDemand sp, SSpareDemandObj sd where sp.demandId = sd.demandId ");
        if (theme!=null) {
            try {
                theme = java.net.URLDecoder.decode(theme,"utf-8");
                hql.append(" and sp.requestTheme like ('%" + theme + "%') ");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }if(type!=null){
            try {
                type = java.net.URLDecoder.decode(type,"utf-8");
                hql.append(" and sd.spareType like ('%"+type+"%') ");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        }
        Query query = getSession().createQuery(hql.toString());
        List<SSpareDemand> list = query.list();
        return list;
    }

    public Pagination getBP(/*Integer channelId, Integer siteId, Integer modelId,
                                     Integer memberId,*/ int pageNo, int pageSize/*,Date startDate, Date endDate,  String releaseName, String repairTyp*/,
                            String theme/*, String type*/){
        Finder f = Finder.create(" from SSpareDemand bean ");
        f.append(" where requestTheme like ('%" + theme + "%') ");
//        f.append(" join bean.content content");
//        f.append(" where content.contentId = bean.contentId ");
//        f.setParam("userId",UserId);
        //添加查询条件
//        appendQuery(f,startDate,endDate,releaseName,repairType,theme,type);
        return find(f, pageNo, pageSize);
    }

    /**
     * @author 苏和 13739980760
     * @Date 2017/1/4 12:33
     * @return
     * 需求方备品备件求购管理明细预览
     */
    public List<SSpareDemand> ManagelistSearch(String uid) {
        String hql="from SSpareDemand sp, SSpareDemandObj sd where sp.demandId = sd.demandId and " +
                "sp.demandId = ('"+uid+"')";
        Query query = getSession().createQuery(hql.toString());
        List<SSpareDemand> list = query.list();
        return list;
    }

    /**
     * @author 苏和 13739980760
     * @Date 2017/1/23 17:05
     * @return
     * 备品备件求购管理编辑（取数据）
     */
    public SSpareDemand ManagelistSearc(String uid) {
        return (SSpareDemand)this.getSession().get(SSpareDemand.class,String.valueOf(uid));
    }
    /**
     * @author 苏和 13739980760
     * @Date 2017/1/4 12:33
     * @return
     * 需求方备品备件求购管理撤回
     */
    public int ManagelistRe(String uid) throws UnsupportedEncodingException {
        String hql="UPDATE SSpareDemand  SET state = '1'" + "WHERE demandId = '"+uid+"'";
        uid = java.net.URLDecoder.decode(uid,"utf-8");
        return getSession().createQuery(hql).executeUpdate();
    }
    /**
     * @author 苏和 13739980760
     * @Date 2017/1/4 12:33
     * @return
     * 需求方备品备件求购管理发布
     */
    public int ManagelistIss(String uid) throws UnsupportedEncodingException {
        String hql="UPDATE SSpareDemand  SET state = '2'" + "WHERE demandId = '"+uid+"'";
        uid = java.net.URLDecoder.decode(uid,"utf-8");
        return getSession().createQuery(hql).executeUpdate();
    }
    /**
     * @author 苏和 13739980760
     * @Date 2017/1/4 12:33
     * @return
     * 需求方备品备件求购管理删除
     */
    public int ManagelistDell(String uid){
        StringBuffer hql = new StringBuffer();
        hql.append(" delete SSpareDemand as sd where sd.demandId ='"+uid+"' " );
        Query query = getSession().createQuery(hql.toString());
        return query.executeUpdate(); // 必须加此句否则不会执行删除操作
//        String hql="delete SSpareDemand a where a.demandId = "+uid+" ";
//        return getSession().createQuery(hql).executeUpdate();
    }

    /**
     * @author 苏和 13739980760
     * @Date 2017/1/12 14:56
     * @return
     * 求购删除新
     */
    public SSpareDemand findById(String id){
        SSpareDemand spareDemand = (SSpareDemand)this.getSession().get(SSpareDemand.class,id);
        return spareDemand;
    }

    public SSpareDemand delete(SSpareDemand spareDemand) {
        if (spareDemand != null) {
            getSession().delete(spareDemand);
        }
        return spareDemand;
    }


    /**
     * @author machao
     * @Date 2016/12/29 15:33
     * 金牌老师傅推荐
     * @return
     */
    public Pagination getJplsftjList(Integer channelId, Integer siteId, Integer modelId,
                                     Integer memberId, int pageNo, int pageSize,Date startDate, Date endDate,  String releaseName, String repairType,String flag,String companyName,String workYear,String addrCity){
        Finder f = Finder.create(" select  bean from SRepairRes bean ");
        f.append(" join bean.content content");
        f.append(" where content.contentId = bean.contentId and bean.status='3'  ");
        java.sql.Date currentDate = new java.sql.Date(System.currentTimeMillis());//获取当前时间
        f.append(" and bean.deadlineDt>:currentDate ");
        f.setParam("currentDate",currentDate);
        if(flag==null || flag==""){
            f.append(" and bean.isRecommend='1' ");
        }else if(flag.equals("tuijian")){
            f.append(" and ( bean.isRecommend <> '1' or bean.isRecommend is null or bean.isRecommend ='0') ");
        }
//        String sql = "SELECT SAmplePolicyApplyService.* from S_REPAIR_DEMAND SAmplePolicyApplyService,S_REPAIR_QUOTE e where SAmplePolicyApplyService.REPAIR_ID = e.DEMAND_ID";
//        SQLQuery sqlQuery = getSession().createSQLQuery(sql);
//        List list = sqlQuery.list();

//        Finder f = Finder.create(" select bean from SRepairDemand bean ");
        //添加查询条件
        appendQuery(f,startDate,endDate,releaseName,repairType,companyName,workYear,addrCity);
        f.setCacheable(true);
        return find(f, pageNo, pageSize);
    }
    /***
     * @author machao
     * @date 2016/12/22
     * @param f
     * @desc 查询条件
     */
    private void appendQuery(Finder f, Date startDate,Date endDate,String releaseName,String repairType,String companyName,String workYear,String addrCity) {
        if (startDate != null && !"".equals(startDate)) {
            f.append(" and bean.createDt >=:startDate");
            f.setParam("startDate", startDate );
        }
        if(endDate !=null && !"".equals(endDate)){
            f.append(" and bean.createDt <=:endDate");
            f.setParam("endDate", endDate);
        }
        if(releaseName !=null && !"".equals(releaseName)) {
            f.append(" and bean.releaseName like :releaseName");
            f.setParam("releaseName", "%" + releaseName + "%");
        }
        if (repairType !=null && !"".equals(repairType)){//待发布
            f.append(" and bean.repairType like :repairType");
            f.setParam("repairType", "%" + repairType + "%");
        }
        if (companyName != null && !"".equals(companyName)) {
            f.append(" and bean.scompany.companyName like:companyName");
//                f.append(" and bean.sRepairRes.scompany.companyName like :companyName");
            f.setParam("companyName", "%" + companyName + "%");
        }
        if (workYear != null && !"".equals(workYear)) {
            f.append(" and bean.workYear like:workYear");
            f.setParam("workYear", workYear);
        }
        if (addrCity != null && !"".equals(addrCity)) {
            f.append(" and bean.addrCity like:addrCity");
            f.setParam("addrCity", "%" + addrCity + "%");
        }
        f.append(" order by bean.createDt desc");
    }
    /**
     * @author machao
     * @Date 2017/1/7 14:44
     * @return
     * 金牌老师傅推荐、撤销
     */
    public boolean  recommendManage(String id,String flag){
        StringBuffer sb = new StringBuffer();
        String statu = "";

        if (id.contains(",")) { // 批量添加、撤销老师傅
            String[] strArr = id.split(","); // id字符串数组
            for (int j = 0; j < strArr.length; j++) { // 批量执行update
                if (flag.equals("backout")) { // 撤销
                    String backoutIds = strArr[j].trim();
                    sb.append(" update SRepairRes bean  set bean.isRecommend='0' where bean.repairId='" + backoutIds + "' ");
                    Query batchAddQuery = getSession().createQuery(sb.toString());
                    batchAddQuery.executeUpdate();
                    sb.setLength(0);
                } else if (flag.equals("add")) { // 添加
                    String batchAddIds = strArr[j].trim();
                    sb.append(" update SRepairRes bean  set bean.isRecommend='1' where bean.repairId='" + batchAddIds + "' ");
                    Query batchoutQuery = getSession().createQuery(sb.toString());
                    batchoutQuery.executeUpdate();
                    sb.setLength(0);
                }
            }
        } else {
            if (flag.equals("backout")) {
                statu = "0";
            } else if (flag.equals("add")) {
                Query q = getSession().createQuery(" from SRepairRes bean where bean.repairId='" + id + "' and bean.isRecommend='1' ");
                List<SRepairRes> list = q.list();
                if (list.size() > 0) {
                    return false;
                }
                statu = "1";
            }
            sb.append(" update SRepairRes bean  set bean.isRecommend='" + statu + "'  where bean.repairId='" + id + "' ");
            Query query = getSession().createQuery(sb.toString());
            query.executeUpdate();
        }
        return  true;
    }

    /**
     * @author 苏和 13739980760
     * @Date 2017/1/5 15:26
     * @return
     */
    public Pagination getPage(String isUrgency, String requestTheme,
                              Integer pageNo, Integer pageSize) throws UnsupportedEncodingException {
        Finder f = getFinder(isUrgency,requestTheme);
        return find(f, pageNo, pageSize);
    }

    private Finder getFinder(String isUrgency, String requestTheme) throws UnsupportedEncodingException {
        Finder f = Finder.create("from SSpareDemand bean where 1=1");
        if (isUrgency != null && isUrgency.trim().length()> 0) {
            f.append(" and bean.isUrgency = :isUrgency");
            f.setParam("isUrgency", isUrgency);
        } else if (requestTheme != null && requestTheme.trim().length() > 0) {
            requestTheme = java.net.URLDecoder.decode(requestTheme,"utf-8");
            f.append(" and bean.requestTheme like :requestTheme");
            f.setParam("requestTheme", "%" + requestTheme + "%");
        }
        return f;
    }
    /**
     * @author 苏和 13739980760
     * @Date 2017/1/6 15:12
     * @return
     * 备品备件求购管理
     */

    public Pagination getPageBySelf(String requestType, Integer typeId, Integer userId, boolean topLevel, boolean recommend,
                                    Content.ContentStatus contentStatus, Byte checkStep, Integer siteId,
                                    Integer channelId, int pageNo, int pageSize, Date startDate,
                                    Date endDate, String releaseId, String dealType,
                                    String expectPrice, String isUrgency, String requestTheme,String invoiceType,String status) {
        Finder f = Finder.create("select  bean from SSpareDemand bean");
        f.append(" join bean.content content");
        f.append(" where content.user.userId=:userId");
        f.setParam("userId", userId);
        if (requestType != null && requestType.trim().length() > 0) {
            f.append(" and bean.requestType like '%" + requestType + "%'");
        }
        //添加查询条件
        appendQuery(f,startDate,endDate, requestTheme, isUrgency,  dealType,  invoiceType, releaseId,status);
        f.append(" order by bean.createDt desc");
        return find(f, pageNo, pageSize);
    }
    private void appendQuery(Finder f,Date startDate, Date endDate, String requestTheme,
                              String isUrgency, String pay,String invoiceType,String releaseId,String status) {
        if (requestTheme != null && !"".equals(requestTheme)){
            f.append(" and bean.requestTheme like :requestTheme");
            f.setParam("requestTheme", "%" + requestTheme + "%");
        }
        if (status != null && !"".equals(status)){//全部
            if ("1".equals(status)){
                f.append(" and (bean.state=1 or bean.state=0) and bean.deadlineDt >= CURDATE() ");
            }else if("2".equals(status)) {
                f.append(" and bean.state=2  and bean.deadlineDt >= CURDATE() ");
            }else if("3".equals(status)) {
                //f.append(" and bean.state= 3 and bean.deadlineDt >= CURDATE() ");
                f.append(" and bean.state= 3 and bean.deadlineDt >= CURDATE() ");
            } else if("4".equals(status)) {
                //f.append(" and bean.state= 3 and bean.deadlineDt < CURDATE() ");
                f.append(" and  bean.deadlineDt < CURDATE() ");
            }
            /*else{
                f.append(" and bean.state =:status");
                f.setParam("status", status);}*/
            else{
                f.append(" bean.deadlineDt >= CURDATE() ");
               }
        }
        if (startDate != null) {
            f.append(" and bean.createDt >=:startDate");
            f.setParam("startDate", startDate);
        }
        if (endDate != null) {
            f.append(" and bean.createDt <=:endDate");
            f.setParam("endDate", endDate);
        }
    }


    /**
     * @author liuyang
     * @Date 2017/1/7 21:55
     * @return
     * 众修需求
     */
    public Pagination getPageBySiteIdZxxqId(Integer[] siteIds, Integer[] typeIds, Boolean titleImg, Boolean recommend, String title, Map<String, String[]> attr, int orderBy, int pageNo, int pageSize, String[] params){
        Finder f = Finder.create("select bean from SRepairDemand bean where bean.state=3 ");
        if(params.length>0){

            //能力分类
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
                f.setParam("repairType",repairType);
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
                f.setParam("addrProvince",addrProvince);
            }
            if (params[2] != null && (params[2].trim()).length() > 0 && !("undefined".equals(params[2]))) {
                orderBy = Integer.parseInt(params[2]);
            }
        }

        switch (orderBy) {
            case 1:
                // 截止时间降序
                f.append(" order by bean.deadlineDt asc");
                break;
            case 2:
                // 发布时间降序
                f.append(" order by bean.releaseDt asc");
                break;
            case 3:
                // 发布时间升序
                f.append(" order by bean.releaseDt desc");
                break;

            default:
                // 默认：截止时间升序
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
     * @author machao
     * @Date 2017/1/17 10:30
     * @return
     * 自定义标签--维修资源展
     */
    public List<SRepairRes> getRepairResList( Integer orderBy, Integer listType){
        Finder f = Finder.create(" select  bean from SRepairRes bean where bean.status=3 ");
        if(orderBy==null){
            f.append(" and bean.isRecommend=1 ");
        }
        return find(f);
    }

    public List<SRepairRes> getRepairResList(Integer count, Integer orderBy, Integer listType){
        Finder f = Finder.create(" select  bean from SRepairRes bean where bean.status='3' ");
        java.sql.Date currentDate = new java.sql.Date(System.currentTimeMillis());//获取当前时间
        f.append(" and bean.deadlineDt>:currentDate ");
        f.setParam("currentDate",currentDate);

        if(orderBy==null){
            f.append(" and bean.isRecommend=1 ");
        }
        if(count!=null){
            f.setMaxResults(count);
        }
        f.append(" order by bean.releaseDt DESC ");
        f.setCacheable(true);
        return find(f);
    }
    /**
     * @author machao
     * @Date 2017/1/17 10:56
     * @return
     * 自定义标签--备品备件求购
     */
    public List<SSpareDemand> getSpareDemandResList(Integer count, Integer orderBy, Integer listType,String requestType){
        Finder f = Finder.create(" select  bean from SSpareDemand bean where bean.state='3' ");
        f.append("and DATEDIFF(bean.deadlineDt,now())>=0 ");
        if(requestType!=null){
            f.append(" and bean.requestType ='"+requestType+"' ");
        }
        if(orderBy!=null){
            f.append(" order by bean.releaseDt DESC ");
        }
        if(count!=null){
            f.setMaxResults(count);
        }
        f.setCacheable(true);
        return find(f);
    }
    /**
     * @author gengwenlong
     * @Date 2017/2/18 11:13
     * @return
     * 自定义标签-备品备件求购即将过期
     */
    public List<SSpareDemand> getSpareDemandResLists(Integer count, Integer orderBy, Integer listType){
        Finder f = Finder.create(" select  bean from SSpareDemand bean where bean.state='3' ");
        f.append("and DATEDIFF(bean.deadlineDt,now())>=0 ");
        f.append("and DATEDIFF(bean.deadlineDt,now())<7 ");
        if(orderBy!=null){
            f.append(" order by bean.deadlineDt asc ");
        }
        if(count!=null){
            f.setMaxResults(count);
        }
        f.setCacheable(true);
        return find(f);
    }
    /**
     * @author machao
     * @Date 2017/2/9 14:02
     * @return
     * 自定义标签-备品备件
     */
    public List<SSpare> getSpareList(Integer count, Integer orderBy, Integer listType, String spareType){
        Finder f = Finder.create(" select  bean from SSpare bean where bean.status='3' and (bean.publicType=0 or bean.publicType=1) ");
        if(spareType!=null){
            f.append("and bean.spareType = '"+spareType+"'");
        }
        if(orderBy!=null){
            f.append(" order by bean.releaseDt DESC ");
        }
        if(count!=null){
            f.setMaxResults(count);
        }
        f.setCacheable(true);
        return find(f);
    }
    /**
     * @author machao
     * @Date 2017/2/19 14:28
     * @return
     * 企业下拉框
     */
    public List<SCompany> getCompanyLists(Integer count, String orderBy, Integer listType){
        Finder f = Finder.create(" select  bean from SCompany bean where 1=1 ");
        if(listType.equals(0)){
            f.append(" and bean.companyId = '"+orderBy+"' ");
        }
        f.setCacheable(true);
        return find(f);
    }
    /**
     * @author machao
     * @Date 2017/1/17 11:01
     * @return
     * 自定义标签-维修需求展示
     */
    public List<SRepairDemand> getRepairDemandList(Integer count, Integer orderBy, Integer listType,String repairType){
        Finder f = Finder.create(" select  bean from SRepairDemand bean where bean.state = 3 ");
        if(repairType!=null){
            f.append(" and repairType =:repairType ");
            f.setParam("repairType",""+repairType+"" );
        }
        if(orderBy!=null){
            f.append("order by bean.releaseDt desc  ");
        }
        if(count!=null){
            f.setMaxResults(count);
        }
        f.setCacheable(true);
        return find(f);
    }
    /**
     * @author gengwenlong
     * @Date 2017/2/17 10:45
     * @return
     * 自定义标签-维修需求即将过期展示
     */
    public List<SRepairDemand> getRepairDemandLists(Integer count, Integer orderBy, Integer listType){
        Finder f = Finder.create(" select  bean from SRepairDemand bean where bean.state = 3 ");
        f.append("and DATEDIFF(bean.deadlineDt,now())>=0 ");
        f.append("and DATEDIFF(bean.deadlineDt,now())<7 ");
        if(orderBy!=null){
            f.append("order by bean.deadlineDt asc  ");
        }
        if(count!=null){
            f.setMaxResults(count);
        }
        f.setCacheable(true);
        return find(f);
    }

    @Override
    protected Class<SSpareDemand> getEntityClass() {
        return SSpareDemand.class;
    }

    @Resource
    private PubCodeDao pubCodeDao;

}
