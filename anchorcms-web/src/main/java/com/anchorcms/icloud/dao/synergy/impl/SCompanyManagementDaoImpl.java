package com.anchorcms.icloud.dao.synergy.impl;

import com.anchorcms.common.hibernate.Finder;
import com.anchorcms.common.hibernate.HibernateBaseDao;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.dao.CmsUserDao;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.dao.synergy.SCompanyManagementDao;
import com.anchorcms.icloud.model.common.SCfcaApply;
import com.anchorcms.icloud.model.synergy.SCompany;
import com.anchorcms.icloud.model.synergy.SCompanyAptitude;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Copyright @ 2016 Shandong jxdinfo software co,ltd.
 * All right reserved.
 * Created by Gao Jiangning
 *
 * @Date 2016/12/27 0027
 * @Desc
 */
@Repository
public class SCompanyManagementDaoImpl extends HibernateBaseDao<SCompany, String> implements SCompanyManagementDao {

    /**
     * @author: gao jiangning
     * @desciption 分页查询，用父类的方法
     */
    public Pagination findByPage(Finder finder, int pageNo, int pageSize) {
        return super.find(finder,pageNo,pageSize);
    }

    /**
     * @author jsz
     * @date 2017/1/10 16:13
     * @desc 自定义标签——企业制造能力列表
     **/
    public List<SCompany> getList(Integer count, Integer orderBy, Integer listType) {
        Finder f = Finder.create("select  bean from SCompany bean where bean.isRecommend=1");
        if (orderBy !=null){
            f.append(" order by bean.updateDt desc");
        }
        if (count != null) {
            f.setMaxResults(count);
        }
        return find(f);
    }
    /**
     * @author liuyang
     * @Date 2017/5/11 8:47
     * @return
     */
    public Pagination getPage(String companyId,String companyName, String status, Integer pageNo, Integer pageSize) {
        Finder f = Finder.create("from SCompany as b inner join fetch b.aptitude as s where 1=1");
        if(!StringUtils.isBlank(status)&&!status.equals("all")){
            f.append(" and s.status =:status ").setParam("status",status);
        }
        if (companyId != null && !"".equals(companyId)) {
            f.append(" and b.companyId like :companyId");
            f.setParam("companyId", "%"+companyId+"%");
        }

        if (companyName != null && !"".equals(companyName)) {
            f.append(" and b.companyName = :companyName");
            f.setParam("companyName", "%"+companyName+"%");
        }
        f.append(" order by s.createDt desc");
        f.setCacheable(true);
        return find(f, pageNo, pageSize);
    }
    /**
     * @author liuyang
     * @Date 2017/5/11 10:41
     * @return
     */
    public void modifyCfcaStateById(String aptitudeId, String status, Date updateDt, String backReason) {
        StringBuffer hql = new StringBuffer();
        if (status.equals("0")) {
            hql.append(" UPDATE SCompanyAptitude  SET updateDt = CURDATE(),status = '0'" + " WHERE aptitudeId = '"+aptitudeId+"'");
        } else if (status.equals("3")) {
            hql.append(" UPDATE SCompanyAptitude  SET updateDt = CURDATE(),status = '3'" + " WHERE aptitudeId = '"+aptitudeId+"'");
        }
        Query query = getSession().createQuery(hql.toString());
        query.executeUpdate(); // 执行delete，update和insert into
    }

    public SCompanyAptitude getCfcaEntity(String id) {
        String hql;
        if(id.equals("0")){
            hql = "from SCompanyAptitude";
        }else{
            hql = " from SCompanyAptitude s where s.aptitudeId='"+id+"'";
        }
        Query query = getSession().createQuery(hql);
        List<SCompanyAptitude> list = query.list();
        SCompanyAptitude sCompanyAptitude =list.get(0);
        return sCompanyAptitude;
    }
    public List getList(CmsUser user) {
        return getSession().createQuery(" from SCompanyAptitude bean where bean.companyId=:id ").setParameter("id", user.getCompany().getCompanyName()).list();
    }

    public Pagination getPage(CmsUser user, int cpn, int i) {
        Pagination p = null;
        String companyId = user.getCompany().getCompanyId();
        Finder f = Finder.create(" from SCompanyAptitude bean where bean.companyId=:com").setParam("com",companyId);
        return find(f, cpn, i);
    }

    public SCompanyAptitude findById(String id) {
        return getCfcaEntity(id);
    }

    public SCompanyAptitude updateBean(SCompanyAptitude bean) {
        if (bean == null) {
            return null;
        } else {
            getSession().saveOrUpdate(bean);
            getSession().flush();
            return bean;
        }
    }
    @Override
    protected Class<SCompany> getEntityClass() {
        return SCompany.class;
    }

    @Resource
    private CmsUserDao cmsUserDao;

}