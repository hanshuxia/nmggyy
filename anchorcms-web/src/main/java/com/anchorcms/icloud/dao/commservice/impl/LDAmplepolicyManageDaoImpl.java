package com.anchorcms.icloud.dao.commservice.impl;

import com.anchorcms.cms.model.main.Content;
import com.anchorcms.common.hibernate.Finder;
import com.anchorcms.common.hibernate.HibernateBaseDao;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.icloud.dao.commservice.LDAmplepolicyManageDao;
import com.anchorcms.icloud.model.commservice.SAmplePolicy;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.Date;


/**
 * DESCRIPTION:f
 * Author: DELL
 * Date:2017/1/13.
 */
@Repository
public class LDAmplepolicyManageDaoImpl extends HibernateBaseDao implements LDAmplepolicyManageDao {

/**
 * @author ldong
 * @Date 2017/1/23 10:27
 * @return
 * 获取惠补政策管理列表
 */

    public Pagination getAmplePolicyList(String TradeType, Date startDT, Date endDT, String title,
                                         Integer typeId, Integer currUserId, Integer inputUserId,
                                         boolean topLevel, boolean recommend, Content.ContentStatus status,
                                         Byte checkStep, Integer siteId, Integer modelId, Integer channelId,
                                         int orderBy, int pageNo, int pageSize, String state) {
        Finder f = null;
        if (state == null || "".equals(state)) {
            state = "1";
        }
        //管理员权限无限制，可以查询所有数据。
        f = Finder.create("select  bean from SAmplePolicy bean where bean.selectedStatus=:state  ").setParam("state", state);
        if (!StringUtils.isBlank(title)) {
            f.append(" and bean.policyName like :title ").setParam("title", "%" + title + "%");
        }
        if(!StringUtils.isBlank(TradeType)){
            f.append(" and bean.tradeType like :tr").setParam("tr","%"+TradeType+"%");
        }
        if(startDT!=null){
            f.append( " and bean.releaseDt>= :sdt").setParam("sdt",startDT);
        }
        if(endDT!=null){
            f.append( " and bean.releaseDt<= :edt").setParam("edt",endDT);
        }
        f.append(" order by bean desc");
        f.setCacheable(true);
        return find(f, pageNo, pageSize);
    }

    public SAmplePolicy getAmplePolicyById(Integer itemId) {
      return (SAmplePolicy)getSession().get(SAmplePolicy.class,itemId);
    }

    public void updateAmplePolicyByBean(SAmplePolicy bean) {
       getSession().update(bean);
    }

    public SAmplePolicy deleteByBean(SAmplePolicy bean) {
        if(bean!=null)
         getSession().delete(bean);
        return bean;
    }

    protected Class getEntityClass() {
        return null;
    }
}
