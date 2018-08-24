package com.anchorcms.icloud.dao.synergy.impl;

import com.anchorcms.cms.model.main.Content;
import com.anchorcms.common.hibernate.Finder;
import com.anchorcms.common.hibernate.HibernateBaseDao;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.dao.synergy.SynergyManageDao;
import com.anchorcms.icloud.model.payment.SPPay;
import com.anchorcms.icloud.model.payment.SPSettle;
import com.anchorcms.icloud.model.synergy.SAbility;
import com.anchorcms.icloud.model.synergy.SAbilityInquiry;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import payment.tools.util.StringUtil;

import java.util.Date;

/**
 * @Author 姜舒中
 * @Date 2016/12/23 0023
 * @Desc 协同制造
 */
@Repository
public class SynergyManageDaoImpl extends HibernateBaseDao<SAbility, Integer> implements SynergyManageDao {

    /**
     * @Author 姜舒中
     * @Date 2016/12/21
     * @Desc 只能管理自己的数据不能审核他人信息   ←这是管理员的，可以查看所有的 by GJN
     */
    public Pagination getPageBySelf(String title, Integer typeId,
                                    Integer UserId, boolean topLevel, boolean recommend,
                                    Content.ContentStatus status, Byte checkStep, Integer siteId,
                                    Integer channelId, Integer userId, int orderBy, int pageNo,
                                    int pageSize, String abilityName, String abilityCode, Date createDt, String statusType) {
        Finder f = Finder.create("select  bean from SAbility bean");
        f.append(" join bean.content content where 1=1");
        f.append(" and bean.statusType in ('0','2','3')");
        //f.append(" where content.user.userId=:userId");
        //f.setParam("userId", UserId);
        appendQuery(f, abilityName, abilityCode, createDt, statusType);
        f.append(" order by bean.updateDt desc,bean.abilityId desc");
        return find(f, pageNo, pageSize);
    }

    private void appendQuery(Finder f, String abilityName, String abilityCode, Date createDt, String statusType) {
        if (abilityName != null && abilityName != "") {
            f.append(" and bean.abilityName like :abilityName");
            f.setParam("abilityName", "%" + abilityName + "%");
        }
        if (abilityCode != null && abilityCode != "") {
            f.append(" and bean.abilityCode like :abilityCode");
            f.setParam("abilityCode", "%" + abilityCode + "%");
        }
        if (createDt != null) {
            f.append(" and bean.createDt = :createDt");
            f.setParam("createDt", createDt);
        }
        if (statusType != null && !"".equals(statusType)) {
            f.append(" and bean.statusType =:statusType");
            f.setParam("statusType", statusType);
        }
    }

    /**
     * @param id
     * @Author jiangshuzhong
     * @Date 2016/12/28
     * @Desc 发布能力审批通过与驳回
     * 修改记录：gjn：前台传过来的参数id并不是contentId 而是abilityId,修改了hql
     */
    public void rejectAdility(Integer id) {
        Query query = getSession().createQuery("update SAbility bean  set bean.statusType=:status  where bean.abilityId=:contentId ")

                .setParameter("status", "0")
                .setParameter("contentId", id);
        int a = query.executeUpdate();
        System.out.println(a);
    }

    public void passAdility(Integer id) {
        Query query = getSession().createQuery("update SAbility bean  set bean.statusType=:status,bean.releaseDt=:releaseDt " +
                " where bean.abilityId=:contentId ")

                .setParameter("status", "3")
                .setParameter("releaseDt", new java.sql.Date(System.currentTimeMillis()))
                .setParameter("contentId", id);
        int a = query.executeUpdate();
        System.out.println(a);
    }

    /**
     * @Author 苏和 【562763562@qq.com】
     * @Date 2017/5/2 11:21
     * @Return 协同制造卖方订单列表
     */
    public Pagination getPage(Integer siteId, CmsUser user, int pageNo, int pageSize,
                              Date startDate, String state, String paramu) {
        Finder f = null;
        if (StringUtil.isEmpty(state)||state.equals("1")) {
            f = Finder.create("select  bean from SPOrder bean where bean.state=6 and bean.isDevice != 1 ");
            if (startDate != null) {
                f.append(" and bean.orderDt =:startDate");
                f.setParam("startDate", startDate);
            }
        } else if (state.equals("2")) {
            f = Finder.create("select  bean from SPAdminSettle bean where bean.state=0 and bean.flag=0 ");
        } else if (state.equals("3")) {
            f = Finder.create("select  bean from SPAdminSettle bean where bean.state=2 and bean.flag=0 ");
        }
        f.setCacheable(true);
        return find(f, pageNo, pageSize);
    }

    public SPSettle findbean(String id) {
        return (SPSettle) getSession().get(SPSettle.class, id);
    }

    @Override

    protected Class<SAbility> getEntityClass() {
        return SAbility.class;
    }

    public SAbility byAbilityId(Integer abilityId){
        SAbility sAbility = get(abilityId);
        return sAbility;
    }
    public void modifyWuliuStateById(String txlogisticId, String logisticsOrderState) {
        StringBuffer hql = new StringBuffer();

            hql.append(" UPDATE SLogistics  SET logisticsOrderState = '10'" + " WHERE txlogisticId = '"+txlogisticId+"'");

//
        Query query = getSession().createQuery(hql.toString());
        query.executeUpdate(); // 执行delete，update和insert into
    }
}