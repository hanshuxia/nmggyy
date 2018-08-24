package com.anchorcms.icloud.dao.cloudcenter.impl;

import com.anchorcms.common.hibernate.Finder;
import com.anchorcms.common.hibernate.HibernateBaseDao;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.dao.CmsUserDao;
import com.anchorcms.icloud.dao.cloudcenter.CloudCenterMyInquiryDao;
import com.anchorcms.icloud.model.cloudcenter.SIcloudDemandQuote;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Author wanjinyou
 * @Date 2017/2/20
 * @Desc 云资源交易中心-管理员-我的方案报价
 */
@Repository
public class CloudCenterMyInquiryDaoImpl extends HibernateBaseDao<SIcloudDemandQuote, Integer> implements CloudCenterMyInquiryDao {

    /**
     * @Author wanjinyou
     * @Date 2017/2/20
     * @Desc 管理员--我的方案报价--列表
     */
    public Pagination getPageBySelf(Integer queryChannelId, Integer siteId, Integer modelId, Integer userId,String selectedStatus, Integer memberId, int pageNo, int pageSize, Date releaseDt, Date deadlineDt, Integer demandId,String statusType) {
        Finder f = Finder.create("select bean from SIcloudDemandQuote bean where bean.createrId=1 ");
        //添加查询条件
        appendQuery(f,statusType);
        f.append(" order by bean.demandObjId desc");
        f.setCacheable(true);
        return find(f, pageNo, pageSize);
    }
    private void appendQuery(Finder f, String statusType) {
        if (statusType != null&& !"".equals(statusType)) {
            f.append(" and bean.selectedStatus =:statusType");
            f.setParam("statusType", statusType);
        }
    }

    /**
     * @param id
     * @Author wanjinyou
     * @Date 2017/2/21
     * @Description 管理员--我的方案报价--获取报价信息
     */
    public SIcloudDemandQuote findById(Integer id){
        SIcloudDemandQuote bean = get(id);
        return bean;
    }

    /**
     * @param id
     * @Author wanjinyou
     * @Date 2017/2/21
     * @Description 管理员--我的方案报价--删除
     */
    public SIcloudDemandQuote deleteById(Integer id) {
        SIcloudDemandQuote demandQuote = findById(id);
        getSession().delete(demandQuote);
        return demandQuote;
    }



    @Resource
    private CmsUserDao cmsUserDao;
    @Override
    protected Class<SIcloudDemandQuote> getEntityClass() {
        return SIcloudDemandQuote.class;
    }

}
