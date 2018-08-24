package com.anchorcms.icloud.service.payment;

import com.anchorcms.icloud.model.payment.SendMessageVO;

/**
 * 结算退款类service
 */
public interface SettlementAndRefundService {
    /**
     * @author ldong
     * @Date 2017/2/22 11:14
     * @return    市场订单交易支付查询
     */
    public  SendMessageVO tx1320(String InstitutionID,String PaymentNo) throws Exception;
    /**
     * @author ldong
     * @Date 2017/2/22 11:14
     * @return    市场订单结算交易
     */
    public  SendMessageVO tx1350(String InstitutionID,String serialNumber) throws Exception;
    /**
     * @author ldong
     * @Date 2017/2/22 11:14
     * @return    下载交易对账单
     */
    public  SendMessageVO tx1810(String InstitutionID,String PaymentNo) throws Exception;
    /**
     * @author ldong
     * @Date 2017/2/22 11:14
     * @return    市场订单结算 service
     */

    public SendMessageVO tx1341(String serialNumber,
                                String orderNo,
                                String remark,
                                long amount,
                               int accountType,
                                String bankID,
                                String accountName,
                                String accountNumber,
                                String branchName,
                                String province,
                                String city)throws Exception;
    SendMessageVO tx1343(String serialNumber,
                         String orderNo,
                         String remark,
                         long amount,
                         int accountType,
                         String bankID,
                         String accountName,
                         String accountNumber,
                         String branchName,
                         String province,
                         String city) throws Exception ;
/**
 * @author ldong
 * @Date 2017/2/22 14:27
 * @return  原路退款
 */

    public SendMessageVO tx1333(String institutionID,
                                String serialNumber,
                                String orderNo,
                                String paymentNo,
                                Long amount,
                                String remark   )throws Exception;
}
