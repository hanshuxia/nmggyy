package com.anchorcms.icloud.dao.commservice.impl;


import com.anchorcms.common.hibernate.Finder;
import com.anchorcms.common.hibernate.HibernateBaseDao;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.dao.CmsUserDao;
import com.anchorcms.icloud.dao.commservice.SAmplePolicyApplyDao;
import com.anchorcms.icloud.model.commservice.SAmplePolicyApply;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by hansx on 2017/1/13 0013.
 * 招标预告实现类
 */
@Repository
public class SAmplePolicyApplyDaoImpl extends  HibernateBaseDao<SAmplePolicyApply, Integer> implements SAmplePolicyApplyDao {

    /**
     * @author dongxuecheng
     * @Date 2017/1/13 15:27
     * @return
     * @description发布SSoldNote销售记录
     */
    public SAmplePolicyApply insert(SAmplePolicyApply sAmplePolicyApply) {
        getSession().save(sAmplePolicyApply);
        return sAmplePolicyApply;
    }
    /**
     * @author ldong
     * @Date 2017/2/13 19:05
     * @return 通过惠补政策的id获取SAmplePolicyApply
     */
    public List<SAmplePolicyApply> getBeanByAmplePolicyId(Integer id){
        return getSession().createQuery( " from SAmplePolicyApply bean where bean.sAmplePolicy.amplePolicyId = :id " ).setParameter("id",id).list();
    }


    /**
     * @author dongxuecheng
     * @Date 2017/1/7 11:05
     * @return
     * @description获取分页以及SRepairRes表中的数据
     */

    public Pagination getPageBySelf( Integer siteId, Boolean isadmin, int pageNo, int pageSize, String inquiryTheme, Integer UserId, String status) {
        Finder f = Finder.create("select  bean from SAmplePolicyApply bean inner join fetch bean.sAmplePolicy s");
        f.append(" where 1=1");
        //添加查询条件
        appendQuery(UserId,isadmin,f,inquiryTheme,status);
        f.append(" order by bean.updateDt desc");
        f.setCacheable(true);
        return find(f, pageNo, pageSize);
    }
    /**
     * @author gengwenlong
     * @Date 2017/2/24 16:46
     * @return
     * 惠补政策申请审核获取分页以及SAmplePolicyApply的数据
     */
    public Pagination getPageBySelf1( Integer siteId, Boolean isadmin, int pageNo, int pageSize, String inquiryTheme, Integer UserId, String status) {
        Finder f = Finder.create("select  bean from SAmplePolicyApply bean inner join fetch bean.sAmplePolicy s");
        f.append(" where 1=1");
        f.append(" and bean.status in ('0','2','3') ");
        //添加查询条件
        appendQuery(UserId,isadmin,f,inquiryTheme,status);
        f.append(" order by bean.updateDt desc");
        f.setCacheable(true);
        return find(f, pageNo, pageSize);
    }

    /***
     * @author dxc
     * @date 2016/12/22
     * @param
     * @desc 查询条件
     */
    private void appendQuery(Integer currUserId,Boolean isadmin,Finder f, String inquiryTheme,String status) {
        if(!isadmin) {
            String userCompanyId = cmsUserDao.findById(currUserId).getCompany().getCompanyId();
            f.append(" and bean.companyId=:companyId ").setParam("companyId", userCompanyId);
        }
        if (inquiryTheme != null && !"".equals(inquiryTheme)) {
            f.append(" and s.policyName like :inquiryTheme");
            f.setParam("inquiryTheme", "%" + inquiryTheme + "%" );
        }
        if (status != null && !"".equals(status)) {
            f.append(" and bean.status =:status");
            f.setParam("status",status);
        }
    }

    /**
     * @author dongxuecheng
     * @Date 2017/1/8 12:33
     * @return
     * @description 惠补政策申请审核查询
     */
    public Pagination getZxxqList(String repairName, Integer pageNo, Integer pageSize) {
        Finder f = getZxxqFinder(repairName);
        return find(f, pageNo, pageSize);
    }

    /**
     * @author dongxuecheng
     * @Date 2017/1/20 11:03
     * @return
     * @description查询已发布的SAmplePolicyApply信息
     */
    private Finder getZxxqFinder(String repairName) {
        Finder f = Finder.create("from SAmplePolicyApply bean where  bean.status = 2");
        if (repairName != null && repairName.trim().length() > 0) {
            f.append(" and bean.companyName like:companyName");
            f.setParam("companyName", "%" + repairName + "%");
        }
        return f;
    }


    /**
     * @Author dongxuecheng
     * @Date 2016/12/26
     * @param amplePolicyApplyId, status
     * @return
     * @Desc 根据id获取需求，改动状态（通过、驳回）
     */
    public int setStatus(String amplePolicyApplyId, String status,String backReason) {
        StringBuffer hql = new StringBuffer();
        if (status.equals("0")) { // ����
            hql.append(" UPDATE SAmplePolicyApply  SET status = '0' ,backReason ='"+ backReason+  "' " + " WHERE amplePolicyApplyId = '" + amplePolicyApplyId + "'");
        } else if (status.equals("3")) { // ͨ��
            hql.append(" UPDATE SAmplePolicyApply  SET status = '3'" + " WHERE amplePolicyApplyId = '" + amplePolicyApplyId + "'");
        }
        Query query = getSession().createQuery(hql.toString());
        return query.executeUpdate(); // ִ��delete��update��insert into
    }

    /**
     * @author dongxuecheng
     * @Date 2017/1/14 10:03
     * @return
     * @description删除惠补政策申请
     */
    public void delete(String amplePolicyApplyId) {
        StringBuffer hql = new StringBuffer();
//        hql.append(" delete from SRepairDemand sd, SRepairDemandObj sdo where sd." + repairId + "= sdo." + repairId);
        hql.append("delete from SAmplePolicyApply sd where sd.amplePolicyApplyId = '"+ amplePolicyApplyId +"' ");
        Query query = getSession().createQuery(hql.toString());
        query.executeUpdate(); // ����Ӵ˾���򲻻�ִ��ɾ������
    }

    /**
     * @author dongxuecheng
     * @Date 2017/1/14 10:04
     * @return
     * @description批量设置惠补政策申请的状态
     */
    public int setRepairDemandstatus(String amplePolicyApplyId, String status,String backReason){
        StringBuffer hql = new StringBuffer();
        String[] strArr = amplePolicyApplyId.split(","); // id�ַ�������
        for (int j = 0; j < strArr.length; j ++) { // ����ִ��update
            if ((status.equals("0")) && (strArr[j].trim().length() > 0)) { // ����
                String nopassIds = strArr[j].trim();
                hql.append(" UPDATE SAmplePolicyApply s  SET status = '0',backReason ='"+ backReason+  "' WHERE amplePolicyApplyId = '"+ nopassIds+"'");
                Query query = getSession().createQuery(hql.toString());
                query.executeUpdate();
                hql.setLength(0);
            } else if (status.equals("3") ) { // ͨ��
                String passIds = strArr[j].trim();
                hql.append(" UPDATE SAmplePolicyApply  SET status = '3' WHERE amplePolicyApplyId = '"+passIds+"' ");
                Query query = getSession().createQuery(hql.toString());
                query.executeUpdate();
                hql.setLength(0);
            }
        }
        return 1;
    }

/**
 * @author dongxuecheng
 * @Date 2017/1/14 17:08
 * @return
 * @description删除对应的正则惠补申请信息
 */
    public SAmplePolicyApply delete(SAmplePolicyApply sAmplePolicyApply) {
        if (sAmplePolicyApply != null) {
            getSession().delete(sAmplePolicyApply);
        }
        return sAmplePolicyApply;
    }

    /**
     * @Author dxc
     * 根据demandId查找
     * @Date 2016/12/28 0028 9:08
     */
    public SAmplePolicyApply findById(Integer id) {
        SAmplePolicyApply sAmplePolicyApply = get(id);
        return sAmplePolicyApply;
    }


    /**
     * @author machao
     * @Date 2017/2/7 22:11
     * @return aa
     */
    public SAmplePolicyApply updatemc(SAmplePolicyApply bean) {
        getSession().update(bean);
        return bean;
    }


    /**
     * @author dongxuecheng
     * @Date 2017/1/20 11:07
     * @return
     * @description更新政策申请的销售id功能
     */
    public int updatesoldNoteIds(String soldNoteIds,Integer id) {
        String hql="update SAmplePolicyApply s set s.soldNoteId ="+soldNoteIds+" where s.amplePolicyApplyId ="+id;
        return getSession().createQuery(hql).executeUpdate();
    }

    /**
     * @author dongxuecheng
     * @Date 2017/1/20 11:05
     * @return
     * @description惠补政策申请发布共鞥
     */
    public int relece(Integer id){
        StringBuffer hql = new StringBuffer();
        hql.append(" UPDATE SAmplePolicyApply  SET status = '2'" + " WHERE amplePolicyApplyId = '" + id + "'");
    Query query = getSession().createQuery(hql.toString());
    return query.executeUpdate();
    }

    /**
     * @author zhouyq
     * @Date 2017/1/20 11:05
     * @return
     * @description 惠补政策申请管理撤回功能
     */
    public int reback(Integer id){
        StringBuffer hql = new StringBuffer();
        hql.append(" UPDATE SAmplePolicyApply  SET status = '1'" + " WHERE amplePolicyApplyId = '" + id + "'");
        Query query = getSession().createQuery(hql.toString());
        return query.executeUpdate();
    }

    @Override
    protected Class<SAmplePolicyApply> getEntityClass() {
        return SAmplePolicyApply.class;
    }

    @Resource
    private CmsUserDao cmsUserDao;
}