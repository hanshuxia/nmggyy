package com.anchorcms.icloud.dao.supplychain.impl;


import com.anchorcms.cms.dao.main.ContentDao;
import com.anchorcms.cms.model.main.Content;
import com.anchorcms.cms.model.main.ContentCheck;
import com.anchorcms.cms.service.main.ContentListener;
import com.anchorcms.common.hibernate.Finder;
import com.anchorcms.common.hibernate.HibernateBaseDao;
import com.anchorcms.common.hibernate.Updater;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.dao.supplychain.MasterManagerDao;
import com.anchorcms.icloud.dao.supplychain.SSpareDetailDao;
import com.anchorcms.icloud.model.supplychain.SRepairRes;
import com.anchorcms.icloud.model.supplychain.SSpare;
import org.apache.shiro.util.Assert;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by jxd on 2016/12/27.
 */
@Repository
public class MasterManagerDaoImpl extends HibernateBaseDao<SRepairRes,Integer> implements MasterManagerDao {

    /**
     * @author dongxuecheng
     * @Date 2017/1/9 10:01
     * @return
     * @description获取所有维修资源的表的信息
     */
    public List<SRepairRes> getList() {
        String hql = " from SRepairRes  ";
        Query query = getSession().createQuery(hql);
        List<SRepairRes> list = query.list();
        return list;
    }

    /**
     * @author dongxuecheng
     * @Date 2017/1/9 10:00
     * @return
     * @description 获取维修资源表的信息
     */
    public SRepairRes getSRepairResEntity(String id) {
        String hql;
        if(id.equals("0")){
             hql = "from SRepairRes";
        }else{
             hql = " from SRepairRes s where s.repairId='"+id+"'";
        }
        Query query = getSession().createQuery(hql);
        List<SRepairRes> list = query.list();
        SRepairRes sRepairRes =list.get(0);
        return sRepairRes;
    }

    /**
     * @author dongxuecheng
     * @Date 2017/1/9 10:00
     * @return
     * @description发布维修资源
     */
    public void insert(SRepairRes sRepairRes) {
        getSession().save(sRepairRes);
    }


    /**
     * @author dongxuecheng
     * @Date 2017/1/7 11:05
     * @return
     * @description获取分页以及SRepairRes表中的数据
     * 供应链-维修方-维修资源管理
     */

    public Pagination getPageBySelf(String repairType,Integer channelId, Integer siteId, Integer modelId, Boolean isAdmin, int pageNo, int pageSize, Date releaseDt, Date deadlineDt, Integer demandId, String inquiryTheme, String companyId, String status, String payType, String statusType) {
        Finder f = Finder.create("select  bean from SRepairRes bean");

      //  f.append(" join bean.content content");
        f.append(" where 1=1");
       // f.setParam("userId",UserId);
        //添加查询条件
        appendQuery(repairType,f,inquiryTheme,status,statusType);
       // if(!isAdmin) {
            f.append(" and bean.scompany.companyId = :companyId");
            f.setParam("companyId", companyId);
       // }
        f.append(" order by bean.createDt desc");
        return find(f, pageNo, pageSize);
    }

    /**
     * @author dongxuecheng
     * @Date 2017/1/20 11:16
     * @return
     * @description通过维修资源表获取询价信息
     */
    public Pagination getPageInquiry(Integer channelId, Integer siteId, Integer modelId, Integer memberId, int pageNo, int pageSize, Date releaseDt, Date deadlineDt, Integer demandId, String inquiryTheme, String companyId, String status, String payType, String statusType) {
        Finder f = Finder.create("select  bean from SRepairRes bean");
        //  f.append(" join bean.content content");
        f.append(" where 1=1");
        // f.setParam("userId",UserId);
        //添加查询条件
        appendQuery("",f,inquiryTheme,status,statusType);

        // f.append(" order by bean.createDt desc");
        f.setCacheable(true);
        return find(f, pageNo, pageSize);
    }
/**
 * @author dongxuecheng
 * @Date 2017/1/7 17:43
 * @return
 * @description备品备件管理
 */
    public Pagination getSpare(Integer channelId, Integer siteId, Integer modelId, Integer memberId, int pageNo, int pageSize, Date releaseDt, Date deadlineDt, Integer demandId, String inquiryTheme, Integer UserId, String status, String payType, String statusType) {
        Finder f = Finder.create("select  bean from SSpare bean");
        //  f.append(" join bean.content content");
        //f.append(" where bean.status=2");
        //  f.setParam("userId",UserId);
        //添加查询条件
      //  appendQuery(f,inquiryTheme,status,statusType);
        f.setCacheable(true);
        return find(f, pageNo, pageSize);
    }
/**
 * @author dongxuecheng
 * @Date 2017/1/9 9:58
 * @return
 * @description备品备件上传审核
 */
    public Pagination getSpare_check(Integer channelId, Integer siteId, Integer modelId, Integer memberId, int pageNo, int pageSize, Date releaseDt, Date deadlineDt, Integer demandId, String inquiryTheme, Integer UserId, String status, String payType, String statusType ,String spareName) {
        Finder f = Finder.create("select  bean from SSpare bean");
        f.append(" where bean.status in ('0','2','3')");
        if (spareName != null && spareName.trim().length() > 0) {
            f.append(" and bean.spareName like '%" + spareName + "%'");
        }
        if (status !=null && !"".equals(status)) {
            f.append(" and bean.status=:status");
            f.setParam("status",status);
        }

        f.append(" order by bean.createDt desc");
        return find(f, pageNo, pageSize);
    }


    /**
     * @author dongxuecheng
     * @Date 2017/1/9 9:59
     * @return
     * @description删除
     */
    public SRepairRes  delete(SRepairRes sRepairRes) {
//        String hql=" delete  from  SRepairRes s where s.repairId in ('"+id+"')";
//        return getSession().createQuery(hql).executeUpdate();
        if (sRepairRes != null) {
            getSession().delete(sRepairRes);
        }
        return sRepairRes;
    }
/**
 * @author dongxuecheng
 * @Date 2017/1/9 9:59
 * @return
 * @description退回
 */
    public int reback(String id) {
        String hql="update SRepairRes s set s.status =1 where s.repairId =('"+id+"')";
        return getSession().createQuery(hql).executeUpdate();
    }
/**
 * @author dongxuecheng
 * @Date 2017/1/9 9:59
 * @return
 * @description发布
 */
    public int relece(String id,CmsUser user) {
        String name = user.getUserExt().getRealname();
        String hql="update SRepairRes s set s.status =2,s.releaseName='"+name+"' where s.repairId =('"+id+"')";
        return getSession().createQuery(hql).executeUpdate();
    }
/**
 * @author dongxuecheng
 * @Date 2017/1/9 9:59
 * @return
 * @description修改
 */
//    public int update(SRepairRes sRepairRes) {
//        StringBuffer hql = new StringBuffer("update SRepairRes s set ");
//        hql.append("s.repairName='"+sRepairRes.getRepairName()+"'");
//        hql.append(",s.addrProvince='"+sRepairRes.getAddrProvince()+"'");
//        hql.append(",s.addrCity='"+sRepairRes.getAddrCity()+"'");
//        hql.append(",s.addrCounty='"+sRepairRes.getAddrCounty()+"'");
//        hql.append(",s.addrStreet='"+sRepairRes.getAddrStreet()+"'");
//        hql.append(",s.goodAt='"+sRepairRes.getGoodAt()+"'");
//        hql.append(",s.workYear='"+sRepairRes.getWorkYear()+"'");
//        hql.append(",s.mobile='"+sRepairRes.getMobile()+"'");
//        hql.append(",s.description='"+sRepairRes.getDescription()+"'");
//        hql.append(",s.releaseName='"+sRepairRes.getReleaseName()+"'");
//        hql.append("where s.repairId='"+sRepairRes.getRepairId()+"'");
//        return getSession().createQuery(hql.toString()).executeUpdate();
//    }

    /**
     * @author dongxuecheng
     * @Date 2017/1/9 9:59
     * @return
     * @description备品备件上传审核通过
     */
    public int pass(String id) {
        String hql="update SSpare s set s.status =3 where s.sparepartsId =('"+id+"')";
        return getSession().createQuery(hql).executeUpdate();
    }

    /**
     * @author dongxuecheng
     * @Date 2017/1/9 9:59
     * @return
     * @description备品备件上传审核驳回
     */
    public int goback(String id) {
        String hql="update SSpare s set s.status =0 where s.sparepartsId =('"+id+"')";
        return getSession().createQuery(hql).executeUpdate();
    }


    /***
     * @author dxc
     * @date 2016/12/22
     * @param
     * @desc 查询条件
     */
    private void appendQuery(String repairType,Finder f, String inquiryTheme,String status,String statusType ) {

        if (inquiryTheme != null && !"".equals(inquiryTheme)) {
            f.append(" and bean.repairName like :inquiryTheme");
            f.setParam("inquiryTheme", "%"+inquiryTheme+"%" );
        }
    /*    if(status !=null && !"".equals(status)){
            if ("1".equals(status)){//已优选
                f.append(" and bean.status =:status");
                f.setParam("status", status);
            }
        }*/
        if(repairType != null && !"".equals(repairType)){
            f.append(" and bean.repairType like :repairType");
            f.setParam("repairType", "%"+repairType+"%" );
        }
        if(statusType !=null && !"".equals(statusType)){
            if ("0".equals(statusType)){//草稿
                f.append(" and bean.status =:status");
                f.setParam("status", statusType);
            } else if ("1".equals(statusType)){//待发布
                if (status != null) { // 草稿
                    f.append(" and bean.status =:status");
                    f.setParam("status", statusType);
                }
            } else if ("6".equals(statusType)){ // 被驳回
                if (status != null) {
                    f.append(" and bean.status in('0')");
                }
            }
            else if ("2".equals(statusType)){//询价中
                f.append(" and bean.status =:status");
                f.setParam("status", statusType);
            }
            else if ("3".equals(statusType)){//已下单
                f.append(" and bean.status =:status");
                f.setParam("status", statusType);
            }
            else if ("4".equals(statusType)){//已截止
                f.append(" and bean.status ='0' ");
//                f.setParam("status", statusType);
            }
            else if ("5".equals(statusType)){//已关闭
                f.append(" and bean.status =:status");
                f.setParam("status", statusType);
            }
        }
    }


   /**
    * @author 苏和 13739980760
    * @Date 2017/1/14 16:23
    * @return设置单个发布博会的状态
    */

    public void mdyRepairDemandStateById(String sparepartsId, String status) {
        StringBuffer hql = new StringBuffer();
        if (status.equals("0")) {
            hql.append(" UPDATE SSpare  SET status = '0'" + " WHERE sparepartsId = '" + sparepartsId + "'");
        } else if (status.equals("3")) {
            hql.append(" UPDATE SSpare  SET status = '3' , releaseDt = NOW()" + " WHERE sparepartsId = '" + sparepartsId + "'");
        }
        else if (status.equals("1")) {
            hql.append(" UPDATE SSpare  SET status = '1' , releaseDt = NOW()" + " WHERE sparepartsId = '" + sparepartsId + "'");
        }
        Query query = getSession().createQuery(hql.toString());
        query.executeUpdate();
    }

    public SRepairRes updateRepair(SRepairRes bean) {
        getSession().update(bean);
        return bean;
    }

    public List<SRepairRes> getList(Integer count, Integer orderBy, Integer listType, String repairType) {
        Finder f = Finder.create("select  bean from SRepairRes bean where status = 3");
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
     * @author 苏和 13739980760
     * @Date 2017/1/14 16:23
     * @return批量设置发布博会的状态
     */
    public void setRepairDemandStateByIds(String sparepartsId, String status,String backReason){
        StringBuffer hql = new StringBuffer();
        String[] strArr = sparepartsId.split(",");
        List<Map<String, Object>> mapList;
        SSpare sSpare;
        Content content;
        Updater<Content> updater;
        for (int j = 0; j < strArr.length; j ++) {
            if ((status.equals("0")) && (strArr[j].trim().length() > 0)) {
                String nopassIds = strArr[j].trim();
                sSpare = sSpareDao.findById(nopassIds);
                sSpare.setBackReason(backReason);
                hql.append(" UPDATE SSpare  SET status = '0' WHERE sparepartsId = '"+nopassIds+"' ");
                Query query = getSession().createQuery(hql.toString());
                query.executeUpdate();
                hql.setLength(0);
            } else if (status.equals("3") ) {
                String passIds = strArr[j].trim();
                sSpare = sSpareDao.findById(passIds);
                content  = sSpare.getContent();
                // 执行监听器
                mapList  = preChange(content);
                content.setStatus(ContentCheck.CHECKED);
                updater  = new Updater<Content>(content);
                content = contentDao.updateByUpdater(updater);
                afterChange(content, mapList);

                hql.append(" UPDATE SSpare  SET status = '3' , releaseDt = NOW() WHERE sparepartsId = '"+passIds+"' ");
                Query query = getSession().createQuery(hql.toString());
                query.executeUpdate();
                hql.setLength(0);
            }
        }
    }

    private List<Map<String, Object>> preChange(Content content) {
        if (listenerList != null) {
            int len = listenerList.size();
            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>(
                    len);
            for (ContentListener listener : listenerList) {
                list.add(listener.preChange(content));
            }
            return list;
        } else {
            return null;
        }
    }

    private void afterChange(Content content, List<Map<String, Object>> mapList) {
        if (listenerList != null) {
            Assert.notNull(mapList);
            Assert.isTrue(mapList.size() == listenerList.size());
            int len = listenerList.size();
            ContentListener listener;
            for (int i = 0; i < len; i++) {
                listener = listenerList.get(i);
                listener.afterChange(content, mapList.get(i));
            }
        }
    }

    @Resource
    protected SessionFactory sessionFactory;

    protected Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    protected Class<SRepairRes> getEntityClass() {
        return SRepairRes.class;
    }
    @Resource
    private ContentDao contentDao;
    @Resource
    private SSpareDetailDao sSpareDao;

    @Resource
    private List<ContentListener> listenerList;

}
