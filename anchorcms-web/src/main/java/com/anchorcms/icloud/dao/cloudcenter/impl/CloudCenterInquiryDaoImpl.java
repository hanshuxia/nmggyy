package com.anchorcms.icloud.dao.cloudcenter.impl;

import com.anchorcms.cms.model.main.Content;
import com.anchorcms.common.hibernate.Finder;
import com.anchorcms.common.hibernate.HibernateBaseDao;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.icloud.dao.cloudcenter.CloudCenterInquiryDao;
import com.anchorcms.icloud.model.cloudcenter.SIcloudDemandQuote;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * @Author 阁楼麻雀
 * @Email netuser.orz@icloud.com
 * @Date 2016/12/20
 * @Desc
 */
@Repository
public class CloudCenterInquiryDaoImpl extends HibernateBaseDao<SIcloudDemandQuote, Integer> implements CloudCenterInquiryDao {

    /**
     * @author maocheng
     * @date 2017/1/4
     * @param typeId 内容类型ID
     * @param userId 用户ID
     * @param topLevel 是否固顶
     * @param recommend 是否推荐
     * @param contentStatus 状态
     * @param checkStep 审核步骤
     * @param siteId 站点ID
     * @param channelId 栏目ID
     * @param pageNo 页数
     * @param pageSize 每页大小
     * @param demandObjId 查询订单号
     * @param companyName 查询采购商
     * @param startDate 查询开始时间
     * @param endDate 查询结束时间
     * @param quoteState 报价方标签状态
     * @desc 云资源交易中心-管理员--我的方案报价列表
     * @return
     */
    public Pagination getPageBySelf(Integer typeId, Integer userId, boolean topLevel, boolean recommend,
        Content.ContentStatus contentStatus, Byte checkStep, Integer siteId,
                Integer channelId, int pageNo, int pageSize,String demandObjId, Date startDate,
                Date endDate, String companyName, String quoteState) {
            Finder f = Finder.create("select  bean from SIcloudQuoteManage bean where bean.offerId=:offerId");
            f.append(" and bean.status='1' ");
            f.setParam("offerId", ""+userId+"" );
        //添加查询条件
        appendQuery(f,demandObjId, startDate, endDate,companyName);
        appendQuery3(f,quoteState);
        f.append(" order by bean.updateDt desc,bean.quoteId desc");
        f.setCacheable(true);
        return find(f, pageNo, pageSize);
    }
    /**@Author wanjinyou
     * @Desc 我是需求--需求订单管理 列表状态标签查询
     * @Date 2017/1/12
     */
    private void appendQuery3(Finder f, String quoteState) {
        if (quoteState != null && !"".equals(quoteState)){//全部
            if ("0".equals(quoteState)){//待确认
                f.append(" and bean.quoteState =:quoteState");
                f.setParam("quoteState", quoteState);
            }else if ("1".equals(quoteState)){//待买家确认
                f.append(" and bean.quoteState =:quoteState");
                f.setParam("quoteState", quoteState);
            }
            else if ("2".equals(quoteState)){//未付款
                f.append(" and bean.quoteState =:quoteState");
                f.setParam("quoteState", quoteState);
            }
            else if ("3".equals(quoteState)){//未完成订单
                f.append(" and bean.quoteState =:quoteState");
                f.setParam("quoteState", quoteState);
            }
            else if ("4".equals(quoteState)){//已完成订单
                f.append(" and bean.quoteState =:quoteState");
                f.setParam("quoteState", quoteState);
            }
        }
    }


    /***
     * @author maocheng
     * @date 2017/1/4
     * @param f
     * @param demandObjId 查询订单号
     * @param startDate 查询开始时间
     * @param endDate 查询结束时间
     * @param companyName 查询采购商/提供商
     * @desc 查询条件
     */
    private void appendQuery(Finder f,String demandObjId, Date startDate, Date endDate, String companyName) {
        if (demandObjId != null && !"".equals(demandObjId)) {
            f.append(" and cast(bean.quoteId as string) like :demandObjId");
            f.setParam("demandObjId","%"+demandObjId+"%" );
        }
        if (companyName != null && !"".equals(companyName)) {
            f.append(" and bean.demand.company.companyName like :companyName");
            f.setParam("companyName", "%"+companyName+"%");
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
     * @Author maocheng
     * @param demandObjId
     * @Email netuser.orz@icloud.com
     * @Date 2017/1/6
     * @Desc 通过ID获取需求订单信息
     */
    public SIcloudDemandQuote byDemandObjId(Integer demandObjId) {
        SIcloudDemandQuote sIcloudDemandQuote = get(demandObjId);
        return sIcloudDemandQuote;
    }

    /**
     * @param typeId
     * @param userId
     * @param topLevel
     * @param recommend
     * @param contentStatus
     * @param checkStep
     * @param siteId
     * @param channelId
     * @param pageNo
     * @param pageSize
     * @param demandObjId
     * @param startDate
     * @param endDate
     * @param demandName
     * @param demandState
     * @Author wanjinyou
     * @Desc 我是需求--需求订单管理
     * @Date 2017/1/12
     */
        public Pagination getDeamdOrder(Integer typeId, Integer userId, boolean topLevel, boolean recommend,
        Content.ContentStatus contentStatus, Byte checkStep, Integer siteId,
                Integer channelId, int pageNo, int pageSize,String demandObjId, Date startDate,
                Date endDate, String companyName, String demandState) {
            Finder f = Finder.create("select  bean from SIcloudQuoteManage bean where bean.createrId=:createrId");
            f.append(" and bean.status='1'");
            f.setParam("createrId", ""+userId+"" );
            //添加查询条件
            appendQuery(f,demandObjId, startDate, endDate,companyName);
            appendQuery2(f,demandState);
            f.append(" order by bean.updateDt desc,bean.quoteId desc");
            f.setCacheable(true);
            return find(f, pageNo, pageSize);
    }


    /**@Author wanjinyou
     * @Desc 我是需求--需求订单管理 列表状态标签查询
     * @Date 2017/1/12
     */
    private void appendQuery2(Finder f, String demandState) {
        if (demandState != null && !"".equals(demandState)){//全部
            if ("1".equals(demandState)){//已下单
                f.append(" and bean.demandState =:demandState");
                f.setParam("demandState", demandState);
            }else if ("2".equals(demandState)){//合同待确认订单
                f.append(" and bean.demandState =:demandState");
                f.setParam("demandState", demandState);
            }
            else if ("3".equals(demandState)){//待付款订单
                f.append(" and bean.demandState =:demandState");
                f.setParam("demandState", demandState);
            }
            else if ("4".equals(demandState)){//待完成订单
                f.append(" and bean.demandState =:demandState");
                f.setParam("demandState", demandState);
            }
            else if ("5".equals(demandState)){//已完成订单
                f.append(" and bean.demandState =:demandState");
                f.setParam("demandState", demandState);
            }
        }
    }

    @Override
    protected Class<SIcloudDemandQuote> getEntityClass() {
        return SIcloudDemandQuote.class;
    }
}
