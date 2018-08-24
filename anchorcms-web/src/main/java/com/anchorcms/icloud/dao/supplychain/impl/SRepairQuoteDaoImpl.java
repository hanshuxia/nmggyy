package com.anchorcms.icloud.dao.supplychain.impl;

import com.anchorcms.common.hibernate.Finder;
import com.anchorcms.common.hibernate.HibernateBaseDao;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.icloud.dao.supplychain.SRepairQuoteDao;
import com.anchorcms.icloud.model.supplychain.SRepairQuote;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by hansx on 2017/1/2
 * 维修资源报价dao实现
 */
@Repository
public class SRepairQuoteDaoImpl extends HibernateBaseDao<SRepairQuote, String> implements SRepairQuoteDao {


    public SRepairQuote findById(String id) {
        SRepairQuote entity = get(id);
        return entity;
    }

    public SRepairQuote save(SRepairQuote SRepairQuote) {
        this.getSession().save(SRepairQuote);
        return SRepairQuote;
    }
    public List<SRepairQuote> getList() {
        String hql = " from SRepairQuote  ";
        Query query = getSession().createQuery(hql);
        List<SRepairQuote> list = query.list();
        return list;
    }
    /**
     * @author hansx
     * @Date 2017/1/4 0004 下午 2:17
     * @return
     * 
     */
    public Pagination getInquiryListForMember(String inquiryTheme, Date startDate, Date endDate, String companyId,
                                              Integer channelId, Integer siteId, Integer modelId, Integer memberId, int pageNo, int pageSize){
    Finder f = Finder.create(" select  bean from SRepairQuote bean ");
    f.append(" join bean.content content");
    f.append(" where content.contentId = bean.contentId ");
//        f.setParam("userId",UserId);
    //添加查询条件
    appendQuery(f,inquiryTheme,startDate,endDate,companyId);
    return find(f, pageNo, pageSize);
}
    /**
     * @author hansx
     * @Date 2017/1/4 0004 下午 2:17
     * @return
     *
     */
    private void appendQuery(Finder f, String inquiryTheme, Date startDate, Date endDate, String companyId ) {

        if (startDate != null && !"".equals(startDate)) {
            f.append(" and bean.createDt >=:createDt");
            f.setParam("createDt", startDate );
        }
        if(endDate !=null && !"".equals(endDate)){
            f.append(" and bean.createDt <=:createDt");
            f.setParam("createDt", endDate);
        }
        if(inquiryTheme !=null && !"".equals(inquiryTheme)) {
            f.append(" and bean.inquiryTheme like :inquiryTheme");
            f.setParam("inquiryTheme", inquiryTheme);
        }
        if (companyId !=null && !"".equals(companyId)){//待发布
            f.append(" and bean.companyId like:companyId");
            f.setParam("companyId", companyId);
        }
    }

    @Override
    protected Class<SRepairQuote> getEntityClass() {
        return SRepairQuote.class;
    }
}
