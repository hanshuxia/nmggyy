package com.anchorcms.icloud.dao.supplychain.impl;

import com.anchorcms.cms.model.main.Content;
import com.anchorcms.cms.model.main.ContentCheck;
import com.anchorcms.common.hibernate.Finder;
import com.anchorcms.common.hibernate.HibernateBaseDao;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.dao.CmsUserDao;
import com.anchorcms.icloud.dao.supplychain.RepairDemandMnageDao;
import com.anchorcms.icloud.model.payment.SSupplychainOrder;
import com.anchorcms.icloud.model.payment.SSupplychainOrderObj;
import com.anchorcms.icloud.model.supplychain.SRepairAbility;
import com.anchorcms.icloud.model.supplychain.SRepairDemand;
import com.anchorcms.icloud.model.supplychain.SRepairDemandObj;
import com.anchorcms.icloud.model.supplychain.SRepairQuote;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

import static com.anchorcms.cms.model.main.Content.ContentStatus.*;

/**
 * DESCRIPTION:维修需求管理Dao
 * Author: DELL
 * Date:2017/1/3.
 */
@Repository
public class RepairDemandMnageDaoImp extends HibernateBaseDao<SRepairDemand, Integer> implements RepairDemandMnageDao {
    /**
     * @param title 维修资源需求管理
     */
    public Pagination getRepairDemandPage(String repairType,String title, Integer typeId, Integer currUserId, Integer inputUserId, boolean topLevel, boolean recommend, Content.ContentStatus status, Byte checkStep, Integer siteId, Integer modelId, Integer channelId, int orderBy, int pageNo, int pageSize, String state) {
        Finder f = null;
        Pagination p = null;
        if ("4".equals(state)) {
            //在程序中设置deFlag为优选标志位，且1为优选状态。
           /* f = Finder.create("from SRepairDemand a,SRepairQuote b where a.state=4 and a.repairId=b.demandId and " +
                    "b.deFlag=1");*/
            f = Finder.create(" from SRepairDemand bean inner join fetch bean.sRepairQuote s where bean.state=3  and s.selectStatus=1");

        } else if ("5".equals(state)) {
            f = Finder.create(" from SRepairDemand bean inner join fetch bean.sRepairQuote s where bean.state=3 and s.selectStatus=2");
           /* f = Finder.create("from SRepairDemand a,SRepairQuote b where a.state=5 and a.repairId=b.demandId and " +
                    "b.deFlag=3");*/
        } else if ("1".equals(state)) {
            f = Finder.create(" from SRepairDemand bean  where (bean.state=1) ");

        }
        /*else if ("3".equals(state)){
            f = Finder.create(" from SRepairDemand bean  where bean.state=3  ");
        }*/
        else {
            f = Finder.create(" from SRepairDemand bean where 1=1");
            if (!("00".equals(state) || "6".equals(state))) {
                f.append(" and bean.state=:state ").setParam("state", state);
            }
        }
        if ("6".equals(state)) {
            f.append(" and bean.deadlineDt< CURDATE() and bean.state<>7");
        }else if ("7".equals(state)){}
         else {
//            f.append(" and bean.deadlineDt>=CURDATE() ");
        }
        String userCompanyId = cmsUserDao.findById(currUserId).getCompany().getCompanyId();
        f.append(" and bean.company.companyId=:companyId ").setParam("companyId", userCompanyId);
        if (!StringUtils.isBlank(title)) {
            f.append(" and bean.repairName like :title ").setParam("title", "%" + title + "%");
        }
        if(repairType != null && repairType.trim().length()>0){
            f.append(" and bean.repairType like '%" + repairType + "%'");
        }
        f.append(" order by bean.createDt desc ");
        return find(f, pageNo, pageSize);
    }

    /**
     * ld  删除需求池
     *
     * @param id
     * @return
     */
    public SRepairDemand deleteRepairDemandById(String id) {
        SRepairDemand entity = (SRepairDemand) this.getSession().get(SRepairDemand.class, id);
        if (entity != null) {
            getSession().delete(entity);
        }
        return entity;
    }
/**
 * @author ldong
 * @Date 2017/1/23 11:01
 * @return
 * 编辑需求状态  发布  撤回  关闭
 */
    public SRepairDemand editCallBackRepairDemandById(String id, String callBack) {
        SRepairDemand entity = (SRepairDemand) this.getSession().get(SRepairDemand.class, id);
        if (entity != null) {
            entity.setState(callBack);
        }
        return entity;
    }
/**
 * @author ldong
 * @Date 2017/1/23 11:02
 * @return
 * 获取优选列表
 */
    public List<SRepairQuote> getDoChoose(String ids) {
        return getSession().createQuery("from SRepairQuote bean where bean.demandId=:ids").setParameter("ids", ids).list();
    }
/**
 * @author ldong
 * @Date 2017/1/23 11:02
 * @return
 * 编辑下单列表状态
 */
    public SRepairQuote editChooseState(String ids, String state) {
        SRepairQuote bean = (SRepairQuote) getSession().get(SRepairQuote.class, ids);
        if (bean != null) {
            bean.setSelectStatus(state);
        }
        return bean;
    }
/**
 * @author ldong
 * @Date 2017/1/23 11:02
 * @return
 * 获取下单列表
 */
    public List getGoOrder(String demandId, String demandQuoteId) {
        return getSession().createQuery(" from SRepairDemandObj ob,SRepairAbility aq   " +
                "where aq.demandId=ob.repairId and aq.demandId=:demandId and aq.demandObjId=ob.repairObjid " +
                " and aq.demandRequestId=:demandQuoteId").setParameter("demandId", demandId).setParameter("demandQuoteId", demandQuoteId).list();
     /* return   getSession().createQuery(" from SRepairDemandObj ob,SRepairAbility aq   where aq.demandId=ob.repairId and aq.demandId='2' and aq.demandObjId=ob.repairObjid and aq.demandRequestId='2'").list();*/
    }
/**
 * @author ldong
 * @Date 2017/1/23 11:10
 * @return
 * 获取需求对象 by 需求id
 */
    public List getDemandObjByDemandId(String demandId) {
        return getSession().createQuery(" from SRepairDemandObj bean where bean.repairId=:demandId ").setParameter("demandId", demandId).list();
    }
/**
 * @author ldong
 * @Date 2017/1/23 11:11
 * @return
 * 删除需求对象表数据
 */
    public void delSRepairDemandObj(SRepairDemandObj bean) {
        if (bean != null)
            getSession().delete(bean);
    }
/**
 * @author ldong
 * @Date 2017/1/23 11:06
 * @return
 * 获得下单表对象
 */
    public SRepairQuote getQuoteEntity(String demandQuoteID) {
        return (SRepairQuote) getSession().get(SRepairQuote.class, demandQuoteID);
    }


    /**
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
        if (inputUserId != null && inputUserId != 0) {
            f.append(" and bean.user.userId=:inputUserId");
            f.setParam("inputUserId", inputUserId);
        } else {
            //输入了没有的用户名的情况
            if (inputUserId == null) {
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
        }
        if (contribute == status) {
            f.append(" and bean.status=:status");
            f.setParam("status", ContentCheck.CONTRIBUTE);
        } else if (checked == status) {
            f.append(" and bean.status=:status");
            f.setParam("status", ContentCheck.CHECKED);
        } else if (prepared == status) {
            f.append(" and bean.status=:status");
            f.setParam("status", ContentCheck.CHECKING);
        } else if (rejected == status) {
            f.append(" and bean.status=:status");
            f.setParam("status", ContentCheck.REJECT);
        } else if (passed == status) {
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

        }
    }

    /**
     * 排序
     *
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

/**
 * @author ldong
 * @Date 2017/1/23 11:10
 * @return
 * 获取需求对象
 */
    public SRepairDemand selectReapirDemand(String id) {
        SRepairDemand sRepairDemand = (SRepairDemand) getSession().get(SRepairDemand.class, id);
        return sRepairDemand;
    }
/**
 * @author ldong
 * @Date 2017/1/23 11:09
 * @return
 * 获取需求对象列表
 */
    public List<SRepairDemandObj> selectRepairDemandObj(String id) {
        String hql = "from SRepairDemandObj where repairId=:id";
        List<SRepairDemandObj> sRepairDemandObjList = getSession().createQuery(hql).setParameter("id", id).list();
        return sRepairDemandObjList;
    }

    /*编辑update;*/
    public void updateRepairDemand(SRepairDemand p) {
        this.getSession().update(p);
    }
    /*编辑update;*/
    public void updateDemandObj(SRepairDemandObj po) {
        this.getSession().saveOrUpdate(po);
    }


    /**
     * @return 改变下单表的状态
     * @author ldong
     * @Date 2017/1/4 19:41
     */
    public SRepairQuote editStateBydemandObjId(String ids, String state) {
        SRepairQuote entity = (SRepairQuote) this.getSession().get(SRepairQuote.class, ids);
        if (entity != null) {
            entity.setSelectStatus(state);
        }
        return entity;
    }
/**
 * @author ldong
 * @Date 2017/1/23 11:09
 * @return
 * 获取需求对象  通过 int id
 */
    public SRepairDemand getRepairDemandById(Integer demandId) {
        return (SRepairDemand) getSession().createQuery(" from SRepairDemand as b where b.repairId=:demandId").setParameter("demandId", String.valueOf(demandId)).list().get(0);
        // return (SRepairDemand)this.getSession().get(SRepairDemand.class,String.valueOf(demandId));
    }

    /**
     * @author ldong
     * @Date 2017/1/23 11:08
     * @return
     * 保存需求对象   更新需求对象
     */
    public SRepairDemand saveRepairDemand(SRepairDemand bean) {
        //防止同主键冲突
        getSession().clear();
        this.getSession().saveOrUpdate(bean);
        return bean;
    }
    /**
     * @author gengwenlong
     * @Date 2017/1/20 15:41
     * @return
     * 手动删除需求对象表中的orphan
     */
    /**
     * 手动删除子表被去掉关系的对象
     * @return 删除的行数
     */
    public void deleteOrphan() {
        Query query = getSession().createQuery("delete SRepairDemandObj bean where bean.repairId=null");
        System.out.print("----->>>>>SDemandCreateDao.deleteOrphan\n----->>>>>deleted mount of rows:"+query.executeUpdate()+"\n");
    }

    public void setRepairQuoteNull() {
        Query query = getSession().createQuery("update SRepairQuote set selectStatus='00' where demandId = null");
        System.out.print("update mount of rows:"+query.executeUpdate()+"\n");

    }

    public List<SRepairQuote> getRepairDemandByDemandId(String id) {
        return getSession().createQuery(" from SRepairQuote where demandId = :id  ").setParameter("id",id).list();
    }

    /**
     * @return 删除询价表
     * @author ldong
     * @Date 2017/1/9 11:30
     */
    public SRepairQuote deleteRepairQuoteByBean(SRepairQuote bean) {
        getSession().delete(bean);
        return bean;
    }

    public void deleteRepairAbilityByBean(SRepairAbility abbean) {
        getSession().delete(abbean);
    }

    /**
     * @return 获取优选界面的对象。报价单列表
     * @author ldong
     * @Date 2017/1/10 11:03
     */
    public Pagination getQuoteList(Integer demandId, Integer pageNo, int pageSize) {
        Finder f = Finder.create("select bean from SRepairQuote bean where");
        f.append(" bean.demandId=:demandId");
        f.setParam("demandId", String.valueOf(demandId));
        return find(f, pageNo, pageSize);
    }

    /**
     * @return 进行优选
     * @author ldong
     * @Date 2017/1/10 15:34
     */
    @SuppressWarnings("JpaQlInspection")
    public int selectQuotes(String quoteIds) {
        if (quoteIds == null || "".equals(quoteIds.trim())) {
            return 0;
        }
        quoteIds = "'"+quoteIds.replaceAll(",","\',\'")+"'";
        Query query = getSession().createQuery("update SRepairQuote bean set bean.selectStatus='1' " +
                "where bean.demandObjId in (" +quoteIds+ ")");
        return query.executeUpdate();
    }

    /**
     * @return 取消优选
     * @author ldong
     * @Date 2017/1/10 15:35
     */
    @SuppressWarnings("JpaQlInspection")
    public Integer cancelSelectedQuotes(String quoteIds) {
        if (quoteIds == null || "".equals(quoteIds.trim())) {
            return 0;
        }
        quoteIds = "'"+quoteIds.replaceAll(",","\',\'")+"'";
        Query query = getSession().createQuery("update SRepairQuote bean set bean.selectStatus='0' " +
                // "where bean.demandObjId in (" + quoteIds + ")");
                "where bean.demandObjId in ("+ quoteIds+")");
        return query.executeUpdate();
    }
/**
 * @author ldong
 * @Date 2017/1/23 11:07
 * @return
 * 获取报价
 */
    public List<SRepairAbility> getQuotePrice(String quoteId) {
        return getSession().createQuery(" from SRepairAbility " +
                "bean where bean.demandRequestId =:quo").setParameter("quo", quoteId).list();
    }
/**
 * @author ldong
 * @Date 2017/1/23 11:07
 * @return
 * 更新报价对象
 */

    public void updateSrepairQuote(SRepairQuote bean) {
        getSession().update(bean);
    }

    /**
     * @author dongxuecheng
     * @Date 2017/2/22 10:08
     * @return
     * @description
     */
    public Integer setEvaluteValue(String demandObjId,String evaluteValue){
        String hql="update  SRepairQuote  set evaluate ="+evaluteValue+" where demandObjId ='"+demandObjId+"'";
        return getSession().createQuery(hql).executeUpdate();
    }


    protected Class getEntityClass() {
        return SRepairDemand.class;
    }
/**
 * @author liuyang
 * @Date 2017/5/4 16:10
 * @return
 */
    public SRepairDemandObj byDemandObjId(String demandId) {
        SRepairDemandObj sRepairDemandObj = (SRepairDemandObj)getSession().get(SRepairDemandObj.class,demandId);
        return sRepairDemandObj;
    }
    public SSupplychainOrder save(SSupplychainOrder bean) {
        getSession().save(bean);
        return bean;
    }

    public void saveSPOrderObj(SSupplychainOrderObj o) {
        getSession().save(o);
    }


    @Resource
    private CmsUserDao cmsUserDao;
}
