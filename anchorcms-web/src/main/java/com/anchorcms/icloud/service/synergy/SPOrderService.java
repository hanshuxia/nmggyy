package com.anchorcms.icloud.service.synergy;

import com.anchorcms.common.page.Pagination;
import com.anchorcms.icloud.model.payment.SPOrder;
/**
 * @Author 韩淑霞
 * 协同制造/供应链订单表业务类
 * @Date 2017/05/02 0023 15:50
 */
public interface SPOrderService {
    /**
     * @Author 韩淑霞
     * 查询订单列表
     * @Date 2017/05/02  0023 15:50
     */
    public Pagination getPageForMember(Integer companyId, int pageNo, int pageSize, String statusType);
    public Pagination getPageForMember2(Integer companyId, int pageNo, int pageSize, String status,String deFlag);
    public Pagination getPageForMember3(Integer companyId, int pageNo, int pageSize, String status,String deFlag);
    public Pagination getPageForMember4(Integer companyId, int pageNo, int pageSize, String status,String deFlag);
    /**
     * @Author 韩淑霞
     * @Date 2017/05/02
     * @Desc 订单详情
     */
    public SPOrder byDemandId(String orderId);
    /**
     * @Author 韩淑霞
     * 删除订单信息
     * @Date 2017/05/02  0029 10:56
     */
    public SPOrder delete(String orderId);

}
