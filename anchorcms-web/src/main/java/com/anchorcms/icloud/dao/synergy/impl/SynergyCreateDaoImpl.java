package com.anchorcms.icloud.dao.synergy.impl;

import com.anchorcms.cms.model.main.Content;
import com.anchorcms.common.hibernate.Finder;
import com.anchorcms.common.hibernate.HibernateBaseDao;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.dao.CmsUserDao;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.dao.synergy.SynergyCreateDao;
import com.anchorcms.icloud.model.synergy.SAbility;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Author 阁楼麻雀
 * @Email netuser.orz@icloud.com
 * @Date 2016/12/20
 * @Desc
 */
@Repository
public class SynergyCreateDaoImpl extends HibernateBaseDao<SAbility, Integer> implements SynergyCreateDao {

    /**
     * @Author 阁楼麻雀
     * @Email netuser.orz@icloud.com
     * @Date 2016/12/20
     * @Desc 发布能力保存
     */
    public SAbility save(SAbility bean) {
        getSession().save(bean);
        return bean;
    }

    /**
     * @Author 李利民
     * @param id
     * @Email netuser.orz@icloud.com
     * @Date 2016/12/21
     * @Desc 通过ID获取能力信息
     */
    public SAbility byAbilityId(Integer id) {
        Query query = getSession().createQuery("SELECT bean from SAbility bean where  bean.abilityId=:abilityId ")
                .setParameter("abilityId", id);
        SAbility sabilty = (SAbility) query.uniqueResult();
        return sabilty;
    }

    /**
     * @Author yuantao
     * @param ids
     * @Email netuser.orz@icloud.com
     * @Date 2016/12/21
     * @Desc 通过ID获取能力信息
     */
    public List<SAbility> byAbilityIds(String ids){
        Query query = getSession().createQuery("SELECT bean from SAbility bean where  bean.abilityId in ("+ ids +") ");
        List<SAbility> sAbilityList = query.list();
        return sAbilityList;
    }

    /**
     * @Author wanjinyou
     * @param id
     * @Email netuser.orz@icloud.com
     * @Date 2016/12/29
     * @Desc 通过ID获取能力信息
     */
    public SAbility bySAbilityId(Integer id) {
        Query query = getSession().createQuery("SELECT bean from SAbility bean where bean.abilityId=:abilityId ")
                .setParameter("abilityId", id);
        SAbility abilty = (SAbility) query.uniqueResult();
        return abilty;
    }

    /**
     * @author ly
     * @date 2016/12/21
     * @param typeId 内容类型ID
     * @param userId 用户ID
     * @param topLevel 是否固顶
     * @param recommend 是否推荐
     * @param status 状态
     * @param checkStep 审核步骤
     * @param siteId 站点ID
     * @param channelId 栏目ID
     * @param pageNo 页数
     * @param pageSize 每页大小
     * @param startDate 查询开始时间
     * @param endDate 查询结束时间
     * @param releaseId 发布人
     * @param abilityType 能力分类
     * @param abilityName 能力名称
     * @param abilityCode 能力代码
     * @desc 获得自己【公司】发布的能力列表
     * @return
     */
    public Pagination getPageBySelfCompany(Integer typeId, Integer userId, boolean topLevel, boolean recommend,
                                    Content.ContentStatus contentStatus, Byte checkStep, Integer siteId,
                                    Integer channelId, int pageNo, int pageSize, Date startDate,
                                    Date endDate, String releaseId, String abilityType,
                                    String abilityName, String abilityCode, String status) {
        Finder f = Finder.create("select  bean from SAbility bean");
        f.append(" join bean.content content");
        //f.append(" where content.user.userId=:userId");
        //f.setParam("userId", userId);
        f.append(" where bean.company.companyId=:companyId");
        f.setParam("companyId",cmsUserDao.findById(userId).getCompany().getCompanyId());
        //添加查询条件
        appendQuery(f, startDate, endDate, releaseId, abilityType, abilityName, abilityCode, status);
        f.append(" order by bean.updateDt desc,bean.abilityId desc");
        return find(f, pageNo, pageSize);
    }

    /**
     * @author zhouyq
     * @date 2017-05-02
     * @desc 获得能力订单分页
     * @return 文章分页对象
     */
    public Pagination getAbilitySellerOrder(Integer siteId, CmsUser user, int pageNo, int pageSize,
                                            Date startDate, String state, String orderId) {
        // 只查询登录用户订单信息
        Finder f = Finder.create("select  bean from SPOrder bean where bean.supplycompany.companyId=:companyId");
        String userCompanyId = cmsUserDao.findById(user.getUserId()).getCompany().getCompanyId();
        f.setParam("companyId", userCompanyId);
        if ((state != null) && (!("").equals(state))) {
            if (("0").equals(state)) { // 未确认
                f.append(" and bean.state = '0' ");
            } else if (("1").equals(state)) { // 待付款
                f.append(" and bean.state in ('1') ");
            } else if (("2").equals(state)) { // 已付款
                f.append(" and bean.state in ('2') ");
            } else if (("3").equals(state)) { // 退款（退款中、退款成功、拒绝退款）
                f.append(" and bean.state in('30','31','32') ");
            } else if (("6").equals(state)) { // 平台介入中
                f.append(" and bean.state in('6') ");
            } else if (("9").equals(state)) { // 已发货（已收货、未收货）
                f.append(" and bean.state in('4','7') ");
            } else if (("10").equals(state)) { // 交易完成（已结算）
                f.append(" and bean.state in('8','40') ");
            }
        }

        if ((startDate != null) && (!("").equals(startDate))) { // 购买时间
            f.append(" and bean.orderDt =:startDate ");
            f.setParam("startDate", startDate);
        }

        if ((orderId != null) && (!("").equals(orderId))) { // 订单号
            f.append(" and bean.orderId like :orderId ");
            f.setParam("orderId", "%"+orderId+"%");
        }

        f.append(" order by bean.orderDt desc, bean.orderId desc "); // 排序
        f.setCacheable(true);
        return find(f, pageNo, pageSize);
    }

    /**
     * @author zhouyq
     * @date 2017-06-02
     * @desc 获得企业设备订单分页
     * @return 文章分页对象
     */
    public Pagination getDeviceSellerOrder(Integer siteId, CmsUser user, int pageNo, int pageSize,
                                            Date startDate, String state, String orderId) {
        // 只查询登录用户订单信息
        Finder f = Finder.create("select  bean from SPOrder bean where bean.isDevice=1");
//        String userCompanyId = cmsUserDao.findById(user.getUserId()).getCompany().getCompanyId();
//        f.setParam("companyId", userCompanyId);
        if ((state != null) && (!("").equals(state))) {
            if (("0").equals(state)) { // 未确认
                f.append(" and bean.state = '0' ");
            } else if (("1").equals(state)) { // 待付款
                f.append(" and bean.state in ('1') ");
            } else if (("2").equals(state)) { // 已付款
                f.append(" and bean.state in ('2') ");
            } else if (("3").equals(state)) { // 退款（退款中、退款成功、拒绝退款）
                f.append(" and bean.state in('30','31','32') ");
            } else if (("6").equals(state)) { // 平台介入中
                f.append(" and bean.state in('6') ");
            } else if (("9").equals(state)) { // 已发货（已收货、未收货）
                f.append(" and bean.state in('4','7') ");
            } else if (("10").equals(state)) { // 交易完成（已结算）
                f.append(" and bean.state in('8','40') ");
            }
        }

        if ((startDate != null) && (!("").equals(startDate))) { // 购买时间
            f.append(" and bean.orderDt =:startDate ");
            f.setParam("startDate", startDate);
        }

        if ((orderId != null) && (!("").equals(orderId))) { // 订单号
            f.append(" and bean.orderId like :orderId ");
            f.setParam("orderId", "%"+orderId+"%");
        }

        f.append(" order by bean.orderDt desc, bean.orderId desc "); // 排序
        f.setCacheable(true);
        return find(f, pageNo, pageSize);
    }

    /**
     * @param
     * @return
     * @Author zhouyq
     * @Date 2017/05/02
     * @Desc 根据id修改订单状态
     */
    public void mdyOrderStateById(String orderId, String state) {
        StringBuffer hql = new StringBuffer();
        if (state.equals("1")) { // 确认订单
            hql.append(" UPDATE SPOrder  SET state = '1'" + " WHERE orderId = '" + orderId + "'");
        } else if (state.equals("4")) { // 确认收货
            hql.append(" UPDATE SPOrder  SET state = '4'" + " WHERE orderId = '" + orderId + "'");
        } else if (state.equals("7")) { // 已发货
            hql.append(" UPDATE SPOrder  SET state = '7'" + " WHERE orderId = '" + orderId + "'");
        } else if (state.equals("32")) { // 拒绝退款
            hql.append(" UPDATE SPOrder  SET state = '32'" + " WHERE orderId = '" + orderId + "'");
        }
        Query query = getSession().createQuery(hql.toString());
        query.executeUpdate();
    }

    /***
     * @author ly
     * @date 2016/12/22
     * @param f
     * @param startDate 查询开始时间
     * @param endDate 查询结束时间
     * @param releaseId 发布人
     * @param abilityType 能力分类
     * @param abilityName 能力名称
     * @param abilityCode 能力代码
     * @desc 查询条件
     */
    private void appendQuery(Finder f, Date startDate, Date endDate, String releaseId, String abilityType,
                             String abilityName, String abilityCode, String status) {
        //gjn：status to statusType
        if (status != null && !"".equals(status)){//全部
            f.append(" and bean.statusType =:status");
            f.setParam("status", status);
        }
        if (startDate != null) {
            f.append(" and bean.createDt >=:startDate");
            f.setParam("startDate", startDate);
        }
        if (endDate != null) {
            f.append(" and bean.createDt <=:endDate");
            f.setParam("endDate", endDate);
        }
//        if (releaseId != null && !"".equals(releaseId)) {
//            f.append(" and bean.releaseUser.userExtObj.realname like :releaseId");
//            f.setParam("releaseId", "%" +releaseId + "%");
//        }
        if (releaseId != null && !"".equals(releaseId)) {
            f.append(" and bean.createUser.username like :releaseId");
            f.setParam("releaseId", "%" +releaseId + "%");
        }
        if (abilityType != null && !"".equals(abilityType)) {
            f.append(" and bean.abilityType like :abilityType");
            f.setParam("abilityType", "%" +abilityType + "%");
        }
        if (abilityName != null && !"".equals(abilityName)) {
            f.append(" and bean.abilityName like :abilityName");
            f.setParam("abilityName", "%" +abilityName + "%");
        }
        if (abilityCode != null && !"".equals(abilityCode)) {
            f.append(" and bean.abilityCode like :abilityCode");
            f.setParam("abilityCode", "%" +abilityCode + "%");
        }
    }

    /***
     * @author ly
     * @date 2016/12/22
     * @param sabilty
     * @desc 删除
     */
    public SAbility delete(SAbility sabilty) {
        if (sabilty != null) {
            getSession().delete(sabilty);
        }
        return sabilty;
    }

    /**
     * @param ability
     * @param c
     * @Author 李利民
     * @Date 2016/12/22
     * @Desc 发布能力修改
     */
    public void updateAdility(SAbility ability, Content c) {
        Query query = getSession().createQuery("update SAbility bean  set bean.abilityType=:abilityType," +
                "bean.abilityName=:abilityName,bean.abilityCode=:abilityCode,bean.unit=:unit,bean.referPrice=:referPrice," +
                "bean.addrProvince=:addrProvince, bean.addrCity=:addrCity, bean.addrCounty=:addrCounty, "+
                "bean.addrStreet=:addrStreet,bean.contact=:contact,bean.mobile=:mobile, " +
                "bean.operatorId=:operatorId, bean.updateDt=:updateDt " +
                ((ability.getReleaseUser()!=null)?"bean.releaseUser=:releaseUser ":"") +
                "where bean.content.contentId=:contentId ")

                .setParameter("abilityType",ability.getAbilityType())
                .setParameter("abilityName",ability.getAbilityName())
                .setParameter("abilityCode",ability.getAbilityCode())
                .setParameter("unit",ability.getUnit())
                .setParameter("referPrice",ability.getReferPrice())
                .setParameter("addrProvince",ability.getAddrProvince())
                .setParameter("addrCity",ability.getAddrCity())
                .setParameter("addrCounty",ability.getAddrCounty())
                .setParameter("addrStreet",ability.getAddrStreet())
                .setParameter("contact",ability.getContact())
                .setParameter("mobile",ability.getMobile())
                .setParameter("operatorId",ability.getOperatorId())
                .setParameter("updateDt",ability.getUpdateDt())
                .setParameter("contentId",c.getContentId());
        if(ability.getReleaseUser()!=null){
            query.setParameter("releaseUser",ability.getReleaseUser());
        }
         int  a = query.executeUpdate();
         System.out.println(a);
    }

    /**
     * @author: gao jiangning
     * @desciption 2016/12/29 查询某公司下的能力列表 分页+条件
     */
    public Pagination getPageForCompany(String companyId, int pageNo, int pageSize, Date startDate, Date endDate,
                                        String releaseId, String abilityType, String abilityName, String abilityCode) {
        Finder f = Finder.create("select  bean from SAbility bean");
        f.append(" where bean.company.companyId=:companyId");
        f.setParam("companyId", companyId);
        //添加查询条件
        appendQuery(f, startDate, endDate, releaseId, abilityType, abilityName, abilityCode, null);
        f.append(" order by bean.abilityId desc");
        return find(f, pageNo, pageSize);
    }

    /**
     * @author ly
     * @date 2017/1/9 16:13
     * @desc 自定义标签——能力列表
     **/
    public List<SAbility> getList(Integer count, Integer orderBy, Integer listType,String abilityType) {
        Finder f = Finder.create("select  bean from SAbility bean where statusType = 3");
        if(abilityType!=null){
            f.append(" and abilityType =:abilityType");
            f.setParam("abilityType",""+abilityType+"");
        }
        if (orderBy !=null){
            f.append(" order by bean.updateDt desc");
        }
        if (count != null) {
            f.setMaxResults(count);
        }
        return find(f);
    }

    /**
     * @author ly
     * @date 2017/1/22 15:10
     * @desc
     **/
    public SAbility update(SAbility bean) {
        getSession().update(bean);
        return bean;
    }
    @Override
    protected Class<SAbility> getEntityClass() {
        return SAbility.class;
    }

    @Resource
    private CmsUserDao cmsUserDao;
}
