package com.anchorcms.icloud.dao.commservice.impl;


import com.anchorcms.common.hibernate.Finder;
import com.anchorcms.common.hibernate.HibernateBaseDao;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.dao.CmsUserDao;
import com.anchorcms.icloud.dao.commservice.SSoldNoteDao;
import com.anchorcms.icloud.dao.commservice.STenderTrailerDao;
import com.anchorcms.icloud.model.commservice.SSoldNote;
import com.anchorcms.icloud.model.commservice.STenderTrailer;
import com.anchorcms.icloud.model.supplychain.SRepairRes;
import com.anchorcms.icloud.model.synergy.SDemand;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by dxc on 2017/1/13 0013.
 * 销售信息dao层类
 */
@Repository
public class SSoldNoteDaoImpl extends  HibernateBaseDao<SSoldNote, Integer> implements SSoldNoteDao {

    /**
     * @author dongxuecheng
     * @Date 2017/1/13 15:27
     * @return
     * @description发布SSoldNote销售记录
     */
    public SSoldNote insert(SSoldNote sSoldNote) {
        getSession().save(sSoldNote);
        return sSoldNote;
    }

    /**
     * @author dongxuecheng
     * @Date 2017/1/7 11:05
     * @return
     * @description获取分页以及SSoldNote表中的数据
     */

    public Pagination getPageBySelf(Integer channelId, Integer siteId, Integer modelId, Boolean isAdmin, int pageNo, int pageSize, Date releaseDt, Date deadlineDt, Integer demandId, String inquiryTheme, Integer UserId, String status, String payType, String statusType) {
        Finder f = Finder.create("select  bean from SSoldNote bean");

//        f.append(" where 1=1");
//        if(isAdmin){
//            f.append(" where 1=1");
//        }else{
            f.append(" where bean.company.companyId=:companyId");
            String userCompanyId = cmsUserDao.findById(UserId).getCompany().getCompanyId();
            f.setParam("companyId", userCompanyId);
       // }
        //添加查询条件
        appendQuerynew(f,inquiryTheme,statusType,releaseDt,deadlineDt);
        f.setCacheable(true);
        return find(f, pageNo, pageSize);
    }

    /**
     * @author dongxuecheng
     * @Date 2017/1/20 11:08
     * @return
     * @description惠补政策申请导入销售信息的时候获取销售信息
     */
    public Pagination getPage(Integer currUserId,int pageNo, int pageSize,String inquiryTheme,String statusType){
        Finder f = Finder.create("select  bean from SSoldNote bean");
        //f.append(" join bean.content content");
        f.append(" where 1=1");
        //f.setParam("userId",UserId);
        //添加查询条件
        appendQuery(currUserId,f,inquiryTheme,statusType);
        int totalCount = countQueryResult(f);//销售记录的总条数
        return find(f, pageNo, totalCount);
    }

    /**
     * @Author dxc
     * 根据demandId查找
     * @Date 2016/12/28 0028 9:08
     */
    public SSoldNote findById(Integer id) {
        SSoldNote sSoldNote = get(id);
        return sSoldNote;
    }

    /**
     * @Author dxc
     * 删除需求信息
     * @Date 2016/12/29 0029 11:02
     */
    public SSoldNote delete(SSoldNote sSoldNote) {
        if (sSoldNote != null) {
            getSession().delete(sSoldNote);
        }
        return sSoldNote;
    }


    /***
     * @author dxc
     * @date 2016/12/22
     * @param
     * @desc 查询条件
     */
    private void appendQuery(Integer currUserId, Finder f, String inquiryTheme,String statusType ) {

        if (inquiryTheme != null && !"".equals(inquiryTheme)) {
            f.append(" and bean.wareType like :inquiryTheme");
            f.setParam("inquiryTheme", "%" + inquiryTheme + "%");
        }
        String userCompanyId = cmsUserDao.findById(currUserId).getCompany().getCompanyId();
        f.append(" and bean.company.companyId=:companyId ").setParam("companyId", userCompanyId);
        if(statusType !=null && !"".equals(statusType)){
            if ("1".equals(statusType)){//草稿
                f.append(" and bean.status =:status");
                f.setParam("status", statusType);
            }else if ("2".equals(statusType)){//待发布
                f.append(" and bean.status =:status");
                f.setParam("status", statusType);
            }
        }else{
            f.append(" and bean.status =:status");
            f.setParam("status", "1");
        }
    }


    private void appendQuerynew(Finder f, String inquiryTheme,String statusType,Date StartTime,Date EndTime) {

        if (inquiryTheme != null && !"".equals(inquiryTheme)) {
            f.append(" and bean.wareType like :inquiryTheme");
            f.setParam("inquiryTheme", "%"+inquiryTheme+"%" );
        }
        if (StartTime != null && !"".equals(StartTime)) {
            f.append(" and bean.dealDt >= :StartTime");
            f.setParam("StartTime", StartTime );
        }
        if (EndTime != null && !"".equals(EndTime)) {
            f.append(" and bean.dealDt <= :EndTime");
            f.setParam("EndTime", EndTime );
        }
        if(statusType !=null && !"".equals(statusType)){
            if ("1".equals(statusType)){//草稿
                f.append(" and bean.status =:status");
                f.setParam("status", statusType);
            }else if ("2".equals(statusType)){//待发布
                f.append(" and bean.status =:status");
                f.setParam("status", statusType);
            }
        }else{
            f.append(" and bean.status =:status");
            f.setParam("status", "1");
        }
        f.append(" order by bean.dealDt desc");
    }



    @Override
    protected Class<SSoldNote> getEntityClass() {
        return SSoldNote.class;
    }

    @Resource
    private CmsUserDao cmsUserDao;
}