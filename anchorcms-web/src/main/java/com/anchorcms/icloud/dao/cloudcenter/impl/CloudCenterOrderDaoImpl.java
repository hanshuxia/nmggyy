package com.anchorcms.icloud.dao.cloudcenter.impl;

import com.anchorcms.common.hibernate.Finder;
import com.anchorcms.common.hibernate.HibernateBaseDao;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.icloud.dao.cloudcenter.CloudCenterOrderDao;
import com.anchorcms.icloud.model.cloudcenter.SIcloudDemand;
import com.anchorcms.icloud.model.cloudcenter.SIcloudDemandQuote;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * @Author 姜舒中
 * @Email netuser.orz@icloud.com
 * @Date 2017/1/3 0003
 * @Desc
 */
@Repository
public class CloudCenterOrderDaoImpl extends HibernateBaseDao<SIcloudDemandQuote, Integer> implements CloudCenterOrderDao {
    public Pagination getPageBySelf(Integer channelId, Integer siteId, Integer modelId, Integer UserId, Integer memberId, int pageNo, int pageSize, Date releaseDt, Date deadlineDt, Integer demandObjId, String demandName, String status, String payType) {
        Finder f = Finder.create("select bean from SIcloudDemandQuote bean where 1=1");
        //添加查询条件
        appendQuery(f,demandName,status);
        f.setCacheable(true);
        return find(f, pageNo, pageSize);
    }

    /***
     * @author jiangshuzhong
     * @date 2017/1/3
     * @param f
     * @desc 查询条件
     */
    private void appendQuery(Finder f, String demandName,String status ) {

        if (demandName != null && !"".equals(demandName)) {
            f.append(" and bean.demandName like :demandName");
            f.setParam("demandName", "%"+demandName+"%" );
        }
        if(status !=null && !"".equals(status)){
            if ("1".equals(status)){//草稿
                f.append(" and bean.status =:status");
                f.setParam("status", status);
            }
            else if ("2".equals(status)){//待发布
                f.append(" and bean.status =:status");
                f.setParam("status", status);
            }
            else if ("3".equals(status)){//询价中
                f.append(" and bean.status =:status");
                f.setParam("status", status);
            }
            else if ("4".equals(status)){//已优选
                f.append(" and bean.status =:status");
                f.setParam("status", status);
            }
            else if ("5".equals(status)){//已已下单
                f.append(" and bean.status =:status");
                f.setParam("status", status);
            }else if ("6".equals(status)){//已截止
                f.append(" and bean.status =:status");
                f.setParam("status", status);
            }else if ("7".equals(status)){//已关闭
                f.append(" and bean.status =:status");
                f.setParam("status", status);
            }
        }
    }
    /**
     * @Author jiangshuzhong
     * @param demandObjId
     * @Email netuser.orz@icloud.com
     * @Date 2016/1/6
     * @Desc 通过ID获取需求订单信息
     */
    public SIcloudDemandQuote byDemandObjId(Integer demandObjId) {
        SIcloudDemandQuote sIcloudDemandQuote = get(demandObjId);
        return sIcloudDemandQuote;
    }
    @Override
    protected Class<SIcloudDemandQuote> getEntityClass() {
        return SIcloudDemandQuote.class;
    }
}
