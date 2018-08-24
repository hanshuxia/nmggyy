package com.anchorcms.icloud.dao.synergy.impl;

import com.anchorcms.common.hibernate.Finder;
import com.anchorcms.common.hibernate.HibernateBaseDao;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.dao.CmsUserDao;
import com.anchorcms.icloud.dao.synergy.SPOrderDao;
import com.anchorcms.icloud.model.payment.SPOrder;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * @Author hansx
 * 协同制造/供应链订单表Dao实现类
 * @Date 2017/05/02 0023 15:50
 */
@Repository
public class SPOrderDaoImpl extends HibernateBaseDao<SPOrder, String> implements SPOrderDao {

    public Pagination getPageBySelfCompany( int pageNo, int pageSize,Integer userId,String statusType) {
            Finder f = Finder.create("select bean from SPOrder bean where bean.buycompany.companyId=:companyId");
            String userCompanyId = cmsUserDao.findById(userId).getCompany().getCompanyId();
            f.setParam("companyId", userCompanyId);
            appendQuery(f,statusType);
            f.append(" order by bean.orderDt desc");
            f.setCacheable(true);
            return find(f, pageNo, pageSize);

    }
    public Pagination getPageBySelfCompany2( int pageNo, int pageSize,Integer userId,String status,String deFlag) {
        Finder f = Finder.create("select bean from SBigdataDemandQuote bean where bean.companyCode=:companyId");
        String userCompanyId = cmsUserDao.findById(userId).getCompany().getCompanyId();
        f.setParam("companyId", userCompanyId);
        if ((status != null) && (!("").equals(status))) {
            if (("0").equals(status)) { // 需求
                f.append(" and bean.status = '0' ");
            } else if (("1").equals(status)) { // 能力
                f.append(" and bean.status in ('1') ");
            }
        }
        if ((deFlag != null) && (!("").equals(deFlag))) {
            if (("0").equals(deFlag)) { //对接中
                f.append(" and bean.deFlag = '0' ");
            } else if (("1").equals(deFlag)) { //合作中
                f.append(" and bean.deFlag in ('1') ");
            }else if (("2").equals(deFlag)) { //被拒绝
                f.append(" and bean.deFlag in ('2') ");
            }else if (("3").equals(deFlag)) { //待确认
                f.append(" and bean.deFlag in ('3') ");
            }else if (("4").equals(deFlag)) { //完成对接
                f.append(" and bean.deFlag in ('4') ");
            }
        }
//        appendQuery(f,status);
        f.append(" order by bean.releaseDt desc");
        f.setCacheable(true);
        return find(f, pageNo, pageSize);

    }
    public Pagination getPageBySelfCompany3( int pageNo, int pageSize,Integer userId,String status,String deFlag) {
        Finder f = Finder.create("select bean from SBigdataDemandQuote bean where bean.companyCode=:companyId");
        String userCompanyId = cmsUserDao.findById(userId).getCompany().getCompanyId();
        f.setParam("companyId", userCompanyId);
        if ((status != null) && (!("").equals(status))) {
            if (("0").equals(status)) { // 需求
                f.append(" and bean.status = '0' ");
            } else if (("1").equals(status)) { // 能力
                f.append(" and bean.status in ('1') ");
            }
        }
        if ((deFlag != null) && (!("").equals(deFlag))) {
            if (("0").equals(deFlag)) { //对接中
                f.append(" and bean.deFlag = '0' ");
            } else if (("1").equals(deFlag)) { //合作中
                f.append(" and bean.deFlag in ('1') ");
            }else if (("2").equals(deFlag)) { //被拒绝
                f.append(" and bean.deFlag in ('2') ");
            }else if (("3").equals(deFlag)) { //待确认
                f.append(" and bean.deFlag in ('3') ");
            }else if (("4").equals(deFlag)) { //完成对接
                f.append(" and bean.deFlag in ('4') ");
            }
        }

        f.append(" order by bean.releaseDt desc");
        f.setCacheable(true);
        return find(f, pageNo, pageSize);

    }
    public Pagination getPageBySelfCompany4( int pageNo, int pageSize,Integer userId,String status,String deFlag) {
        Finder f = Finder.create("select bean from SBigdataDemandQuote bean where bean.company.companyId=:companyId");
        String userCompanyId = cmsUserDao.findById(userId).getCompany().getCompanyId();
        f.setParam("companyId", userCompanyId);
        if ((status != null) && (!("").equals(status))) {
            if (("0").equals(status)) { // 需求
                f.append(" and bean.status = '0' ");
            } else if (("1").equals(status)) { // 能力
                f.append(" and bean.status in ('1') ");
            }
        }
        if ((deFlag != null) && (!("").equals(deFlag))) {
            if (("0").equals(deFlag)) { // 对接中
                f.append(" and bean.deFlag = '0' ");
            } else if (("1").equals(deFlag)) { // 合作中
                f.append(" and bean.deFlag in ('1') ");
            } else if (("2").equals(deFlag)) { // 被拒绝
                f.append(" and bean.deFlag in ('2') ");
            } else if (("3").equals(deFlag)) { // 待确认）
                f.append(" and bean.deFlag in('3') ");
            } else if (("4").equals(deFlag)) { // 完成对接
                f.append(" and bean.deFlag in('4') ");
            } else if (("9").equals(deFlag)) { // 已发货（已收货、未收货）
                f.append(" and bean.deFlag in('4','7') ");
            } else if (("10").equals(deFlag)) { // 交易完成（已结算）
                f.append(" and bean.deFlag in('8','40') ");
            }
        }
        f.append(" order by bean.releaseDt desc");
        f.setCacheable(true);
        return find(f, pageNo, pageSize);

    }

    public SPOrder findById(String orderId) {
        SPOrder sPOrder = get(orderId);
        return sPOrder;
    }

    /***
     * @author hansx
     * @date 2017/05/02
     * @param f
     * @desc 查询条件
     */
    private void appendQuery(Finder f,String state ) {
        if(state !=null && !"".equals(state)){
                if (("0").equals(state)) {
                    f.append(" and bean.state = '0' ");
                } else if (("1").equals(state)) {
                    f.append(" and bean.state in ('1') ");
                } else if (("2").equals(state)) {
                    f.append(" and bean.state in ('2','32') ");
                } else if (("3").equals(state)) {
                    f.append(" and bean.state in('30','31') ");
                }else if (("4").equals(state)) {
                    f.append(" and bean.state in('8','40') ");
                }else if (("5").equals(state)) {
                    f.append(" and bean.state = '5' ");
                }else if (("6").equals(state)) {
                    f.append(" and bean.state = '6' ");
                } else if (("7").equals(state)) {
                    f.append(" and bean.state in('4','7') ");
                }
        }
    }

    public SPOrder delete(SPOrder sPOrder) {
        if (sPOrder != null) {
            getSession().delete(sPOrder);
        }
        return sPOrder;
    }

    public SPOrder update(SPOrder sPOrder) {
        getSession().save(sPOrder);
        return sPOrder;
    }

    public void updateStatusType(Integer orderId, String statusType) {

    }


    @Resource
    private CmsUserDao cmsUserDao;
    @Override
    protected Class<SPOrder> getEntityClass() {
        return SPOrder.class;
    }
}
