package com.anchorcms.icloud.service.synergy.impl;

import com.anchorcms.common.page.Pagination;
import com.anchorcms.icloud.dao.synergy.SPOrderDao;
import com.anchorcms.icloud.model.payment.SPOrder;
import com.anchorcms.icloud.service.synergy.SPOrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @Author 韩淑霞
 * 协同制造/供应链订单表业务实现类
 * @Date 2017/05/02 0023 15:50
 */
@Service
@Transactional
public class SPOrderServiceImpl implements SPOrderService {
    /**
     * @Author 韩淑霞
     * 查询订单列表
     * @Date 2017/05/02 0023 15:50
     */
    public Pagination getPageForMember(Integer companyId,int pageNo, int pageSize, String statusType) {
        return dao.getPageBySelfCompany( pageNo, pageSize,companyId,statusType);
    }
    public Pagination getPageForMember2(Integer companyId,int pageNo, int pageSize, String status,String deFlag) {
        return dao.getPageBySelfCompany2( pageNo, pageSize,companyId,status,deFlag);
    }
    public Pagination getPageForMember3(Integer companyId,int pageNo, int pageSize, String status,String deFlag) {
        return dao.getPageBySelfCompany3( pageNo, pageSize,companyId,status,deFlag);
    }
    public Pagination getPageForMember4(Integer companyId,int pageNo, int pageSize, String status ,String deFlag) {
        return dao.getPageBySelfCompany4( pageNo, pageSize,companyId,status,deFlag);
    }
    /**
     * @Author 韩淑霞
     * @param orderId
     * @Date 2017/05/02
     * @Desc 订单详情
     */
    public SPOrder byDemandId(String orderId) {
        return dao.findById(orderId);
    }

    /**
     * @Author 韩淑霞
     * 删除订单信息
     * @Date 2016/12/29 0029 10:55
     */
    public SPOrder delete(String orderId){
        SPOrder bean = dao.findById(orderId);//获取能力实体类
        bean = dao.delete(bean);// 执行删除
        return bean;
    }

    @Resource
    private SPOrderDao dao;
}
