package com.anchorcms.icloud.dao.cloudcenter.impl;

import com.anchorcms.cms.model.main.Content;
import com.anchorcms.common.hibernate.Finder;
import com.anchorcms.common.hibernate.HibernateBaseDao;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.icloud.dao.cloudcenter.CloudCenterPolicyDao;
import com.anchorcms.icloud.model.cloudcenter.SIcloudApply;
import com.anchorcms.icloud.model.cloudcenter.SIcloudPolicy;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;


/**
 * @Author 阁楼麻雀
 * @Email netuser.orz@icloud.com
 * @Date 2016/12/20
 * @Desc
 */
@Repository
public class CloudCenterPolicyDaoImpl extends HibernateBaseDao<SIcloudPolicy, Integer> implements CloudCenterPolicyDao {

    /***
     * @author maocheng
     * @date 2017/01/04
     * @param typeId 内容类型ID
     * @param userId 用户ID
     * @param topLevel 是否固顶
     * @param recommend 是否推荐
     * @param checkStep 审核步骤
     * @param siteId 站点ID
     * @param channelId 栏目ID
     * @param pageNo 页数
     * @param pageSize 每页大小
     * @param state 状态
     * @param policyName 政策名称
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @param policyLevel 政策级别
     * @desc 云资源交易中心-云计算政策列表
     * @return
     */
    public Pagination getPageBySelf(Integer typeId, Integer userId, boolean topLevel, boolean recommend,
                                    Content.ContentStatus contentStatus, Byte checkStep, Integer siteId,
                                    Integer channelId, int pageNo, int pageSize, String state, String policyName, Date startDate, Date endDate, String policyLevel) {
        Finder f = Finder.create("select  bean from SIcloudPolicy bean where 1=1");
        //添加查询条件
        appendQuery(f,state,policyName,startDate,endDate,policyLevel);
        f.append(" order by bean.policyId desc");
        f.setCacheable(true);
        return find(f, pageNo, pageSize);
    }
    /***
     * @author llm
     * @date 2017/01/03
     * @param sd
     * @desc 保存
     */
    public SIcloudPolicy save(SIcloudPolicy sd) {
        getSession().save(sd);
        return sd;
    }

    /***
     * @author maocheng
     * @date 2017/01/04
     * @param f
     * @param state 状态
     * @desc 查询条件
     */
    private void appendQuery(Finder f, String state, String policyName, Date startDate, Date endDate, String policyLevel) {
        if(state !=null && !"".equals(state)){
            if ("0".equals(state)){//草稿
                f.append(" and bean.state =:state");
                f.setParam("state", state);
            }else if ("1".equals(state)){//待发布
                f.append(" and bean.state =:state");
                f.setParam("state", state);
            }
            else if ("2".equals(state)){//已发布
                f.append(" and bean.state =:state");
                f.setParam("state", state);
            }
        }
        if (policyName != null && !"".equals(policyName)) {
            f.append(" and bean.policyName like :policyName");
            f.setParam("policyName", "%"+policyName+"%");
        }
        if (policyLevel != null && !"".equals(policyLevel)) {
            f.append(" and bean.policyLevel like :policyLevel");
            f.setParam("policyLevel", "%"+policyLevel+"%");
        }
        if (startDate != null) {
            f.append(" and bean.releaseDt >=:startDate");
            f.setParam("startDate", startDate);
        }
        if (endDate != null) {
            f.append(" and bean.releaseDt <=:endDate");
            f.setParam("endDate", endDate);
        }
    }

    /**
     *@Author maocheng
     *@Date 2017/1/5 0005 17:39
     *@Desc 通过ID获取政策信息
     */
    public SIcloudPolicy byPolicyId(Integer policyId){
        SIcloudPolicy sicloudPolicy = get(policyId);
        return sicloudPolicy;
    }

    /**
     *@Author maocheng
     *@Date 2017/1/10 0010 9:26
     *@Desc 更新政策信息
     */
    public SIcloudPolicy update(SIcloudPolicy bean) {
        getSession().update(bean);
        return bean;
    }

    /**
     *@Author maocheng
     *@Date 2017/1/10 0010 9:26
     *@Desc 获取需要删除的政策id
     */
    public SIcloudPolicy findById(Integer policyId) {
        SIcloudPolicy bean = get(policyId);
        return bean;
    }

    /**
     *@Author maocheng
     *@Date 2017/1/10 0010 9:26
     *@Desc 通过政策id删除政策
     */
    public SIcloudPolicy deleteById(Integer policyId) {
        SIcloudPolicy bean = findById(policyId);
        getSession().delete(bean);
        return bean;
    }
    /**
     * @author jsz
     * @date 2017/1/10 16:13
     * @desc 自定义标签——云计算政策列表
     **/
    public List<SIcloudPolicy> getList(Integer count, Integer orderBy, Integer listType,Integer listId) {
            Finder f = Finder.create("select  bean from SIcloudPolicy bean where 1=1 and DATEDIFF(bean.endApplyDt,now())>=0 and bean.state =2");
        if(listType!=null) {
            if (0 == listType) {//国家级
                f.append(" and bean.policyLevel =:PolicyLevel");
                f.setParam("PolicyLevel", "国家级");
            } else if (1 == listType) {
                f.append(" and bean.policyLevel =:PolicyLevel");
                f.setParam("PolicyLevel", "省区级");
            } else if (2 == listType) {
                f.append(" and bean.policyLevel =:PolicyLevel");
                f.setParam("PolicyLevel", "盟市级");
            } else if (3 == listType) {
                f.append(" and bean.isSupport =:isSupport");
                f.setParam("isSupport", "是");
            }
        }
        if (listId != null) {
            f.append(" and bean.policyId !=:policyId");
            f.setParam("policyId", listId);
        }
        if (orderBy !=null){
            f.append(" order by bean.releaseDt desc");
        }
        if (count != null) {
            f.setMaxResults(count);
        }
        f.setCacheable(true);
        return find(f);
    }
    /**
     * @author jsz
     * @date 2017/1/1616:13
     * @desc 自定义标签——云计算政策列表
     **/
    public List<SIcloudApply> getApplyList(Integer count, Integer orderBy, Integer listType) {
        Finder f = Finder.create("select  bean from SIcloudPolicy bean");
        if (4==listType) {
            f.append(" where bean.state =:state");
            f.setParam("state", "2");
        }
        if (orderBy !=null){
            f.append(" order by bean.createDt desc");
        }
        if (count != null) {
            f.setMaxResults(count);
        }
        return find(f);
    }

    /**
     * 批量发布
     * @Author 闫浩芮
     * @param demandIdsStr 需要批量发布的Id字符串
     * @Date 2017/2/17 0017 18:45
     */
    public int passMany(String demandIdsStr) {
        java.sql.Date now = new java.sql.Date(System.currentTimeMillis());
        Query query = getSession().createQuery("update SIcloudPolicy bean set bean.state='2'" +
                "where bean.policyId in (" + demandIdsStr + ")");
        return query.executeUpdate();
    }
    /**
     *批量撤回
     * @Author 闫浩芮
     * @param demandIdsStr 需要批量撤回的Id字符串
     * @Date 2017/2/17 0017 18:45
     */
    public int rejectMany(String demandIdsStr) {
        Query query = getSession().createQuery("update SIcloudPolicy bean set bean.state='0' " +
                "where bean.policyId in (" + demandIdsStr + ")");
        return query.executeUpdate();
    }
    /**
     * 批量删除
     * @Author 闫浩芮
     * @param demandIdsStr 需要批量删除的Id字符串
     * @Date 2017/2/20 0020 10:37
     */
    public int deleteMany(String demandIdsStr) {
        Query query = getSession().createQuery("delete SIcloudPolicy bean where bean.policyId in (" + demandIdsStr + ")");
        return query.executeUpdate();
    }

    @Override
    protected Class<SIcloudPolicy> getEntityClass() {
        return SIcloudPolicy.class;
    }
}
