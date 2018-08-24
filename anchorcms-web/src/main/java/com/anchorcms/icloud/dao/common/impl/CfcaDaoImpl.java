package com.anchorcms.icloud.dao.common.impl;

import com.anchorcms.cms.service.main.ContentListener;
import com.anchorcms.common.hibernate.Finder;
import com.anchorcms.common.hibernate.HibernateBaseDao;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.dao.common.CfcaDao;
import com.anchorcms.icloud.model.common.SCfcaApply;
import com.anchorcms.icloud.model.common.SCfcaPay;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author machao
 * @Date 2017/3/30 14:45
 * @return cfca审核
 */
@Repository
public class CfcaDaoImpl extends HibernateBaseDao<SCfcaApply, Integer> implements CfcaDao {
    @Resource
    private CfcaDao cfcaDao;

    /**
     * @Author zhouyq
     * @Date 2017/03/30
     * @param
     * @return
     * @Desc 获取电子签章列表信息
     */
    public Pagination getPage(String companyName, String status, Integer pageNo, Integer pageSize) {
        Finder f = Finder.create("select  bean from SCfcaApply bean where 1=1");
        /*if ("3".equals(status)){
            f.append(" and bean.state in(3, 31)");
        } else if ("4".equals(status)) {
            f.append(" and bean.state in(4, 41, 42)");
        } else if (status != null && !"".equals(status)) {
            f.append(" and bean.state =:status");
            f.setParam("status", status);
        }*/
        if(!StringUtils.isBlank(status)&&!status.equals("all")){
            f.append(" and bean.state =:state ").setParam("state",status);
        }
        if (companyName != null && !"".equals(companyName)) {
            f.append(" and bean.companyName like :companyName");
            f.setParam("companyName", "%"+companyName+"%");
        }
        f.append(" order by bean.applyDt desc");
        f.setCacheable(true);
        return find(f, pageNo, pageSize);
    }

    /**
     * @Author zhouyq
     * @Date 2017/03/31
     * @param
     * @return
     * @Desc 获取电子签章明细信息
     */
    public SCfcaApply getCfcaEntity(String id) {
        String hql;
        if(id.equals("0")){
            hql = "from SCfcaApply";
        }else{
            hql = " from SCfcaApply s where s.applyId='"+id+"'";
        }
        Query query = getSession().createQuery(hql);
        List<SCfcaApply> list = query.list();
        SCfcaApply sCfcaApply =list.get(0);
        return sCfcaApply;
    }

    public List getList(CmsUser user) {
        return getSession().createQuery(" from SCfcaApply bean where bean.companyId=:id ").setParameter("id", user.getCompany().getCompanyId()).list();
    }

    public Pagination getPage(CmsUser user, int cpn, int i) {
        Pagination p = null;
        String companyId = user.getCompany().getCompanyId();
        //Finder  f = Finder.create(" from SCfcaApply bean where bean.companyId=:id ").setParam("id",user.getCompany().getCompanyId());
        Finder f = Finder.create(" from SCfcaApply bean where bean.companyId=:com").setParam("com",companyId);
        return find(f, cpn, i);
    }

    public SCfcaApply findById(int id) {
        return get(id);
    }

    /**
     * @author zhouyq
     * @Date 2017/4/5
     * @return 判断是否可以进行签章
     */
    public SCfcaApply getSignFlag(String companyId){ StringBuffer hql = new StringBuffer();
        hql.append("from SCfcaApply s where s.companyId='"+companyId+"'"+"and s.state='4'");
        Query query = getSession().createQuery(hql.toString());
        List<SCfcaApply> list = query.list();
        if(list.size()>0){
            return list.get(0);
        }
        return null;
    }

    /**
     * @author ldong
     * @Date 2017/3/31
     * @return 更改申请数据的状态    通过
     */
    public SCfcaApply updateBean(SCfcaApply bean) {
        if (bean == null) {
            return null;
        } else {
           getSession().saveOrUpdate(bean);
            getSession().flush();
            return bean;
        }
    }

    public SCfcaPay savePay(SCfcaPay po) {
        if (po == null) {
            return null;
        } else {
            getSession().save(po);
            return po;
        }

    }
    /**
     * @author machao
     * @Date 2017/3/31 17:02
     * @return
     * 申请保存
     */
    public SCfcaApply insert(SCfcaApply sCfcaApply){
        getSession().save(sCfcaApply);
        return sCfcaApply;
    }

    protected Class<SCfcaApply> getEntityClass() {
        return SCfcaApply.class;
    }

    @Resource
    private List<ContentListener> listenerList;
    public void modifyCfcaStateById(String applyId, String state, Date releaseDt, String backReason) {
        StringBuffer hql = new StringBuffer();
        if (state.equals("0")) {
            hql.append(" UPDATE SCfcaApply  SET state = '0'" + " WHERE applyId = '"+applyId+"'");
        } else if (state.equals("3")) {
            hql.append(" UPDATE SCfcaApply  SET releaseDt = CURDATE(),state = '3'" + " WHERE applyId = '"+applyId+"'");
        }
        Query query = getSession().createQuery(hql.toString());
        query.executeUpdate(); // 执行delete，update和insert into
    }
}
