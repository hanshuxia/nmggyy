package com.anchorcms.icloud.service.cloudcenter.impl;

import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.dao.cloudcenter.CloudCenterPaymentDao;
import com.anchorcms.icloud.dao.cloudcenter.CloudMangerDao;
import com.anchorcms.icloud.model.cloudcenter.SIcloudManage;
import com.anchorcms.icloud.model.common.SOrderPayment;
import com.anchorcms.icloud.model.payment.PaySettlementRefund;
import com.anchorcms.icloud.model.payment.SendMessageVO;
import com.anchorcms.icloud.service.cloudcenter.CloudCenterPaymentService;
import com.anchorcms.icloud.service.payment.SettlementAndRefundService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import payment.api.util.GUIDGenerator;

import javax.annotation.Resource;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Administrator on 2017/2/19.
 */
@Service
@Transactional
public class CloudCenterPaymentServiceImpl implements CloudCenterPaymentService {
    public SOrderPayment save(SOrderPayment payment, Integer managerId) {
        SIcloudManage icloudManage = cloudMangerDao.getMangerById(managerId);
        icloudManage.setState("3");
        cloudMangerDao.update(icloudManage);
        return paymentDao.save(payment);
    }

    public SOrderPayment save(SOrderPayment payment) {
        return paymentDao.save(payment);
    }

    public Pagination getOrderPage(Integer siteId, CmsUser payUser, Integer pageNo, Integer pageSize, String status, String orderNo) {
        return paymentDao.getPage(siteId, payUser, pageNo, pageSize, status, orderNo);
    }

    /**
     * @param PaymentNo
     * @return
     * @Autner lilimin
     * 通过订单号查询信息并修改状态
     */
    public SOrderPayment getUpdateByPaymentNo(String PaymentNo) {
        //1:通过订单号查出信息或得messageID
        SOrderPayment payment = paymentDao.getByPaymentNo(PaymentNo);
        if (null == payment) {
            return null;
        }
        //把支付状态修改
        payment.setState(3);
        payment = paymentDao.update(payment);
        return payment;
    }

    /**
     * @param orderNo
     * @return
     * @Autner lilimin
     * 通过订单号查询信息
     */
    public SOrderPayment getByPaymentNo(String orderNo) {
        //1:通过订单号查出信息或得messageID
        SOrderPayment payment = paymentDao.getByPaymentNo(orderNo);
        return payment;
    }

    public SOrderPayment update(SOrderPayment paymentss) {
        paymentDao.update(paymentss);
        return paymentss;
    }

    public Pagination getOrderManagePage(Integer siteId, CmsUser user, int pageNo, int pageSize, String statusType, String orderNo) {
        return paymentDao.getManagePage(siteId, pageNo, pageSize, statusType, orderNo);
    }

    public void deleteByOrderId(SOrderPayment orderId) {
        paymentDao.deleteByOrderId(orderId);
    }


    /**
     * @return 云资源退款 结算模块
     * @author ldong
     * @Date 2017/3/22 10:07
     */

    public String settle(String serialNumber,
                       String orderNo,
                       String remark,
                       long amount,
                       int accountType,
                       String bankID,
                       String bankName,
                       String accountName,
                       String accountNumber,
                       String branchName,
                       String province,
                       String city, SOrderPayment co, String flag) throws Exception {

        amount = (long) (co.getAmount() * 100);
        orderNo = co.getOrderNo();
        serialNumber = GUIDGenerator.genGUID(); //生成新的交易流水号
        SendMessageVO vo ;
        if (flag.equals("1")) {
            vo = SettleService.tx1341(serialNumber, orderNo, remark, amount,
                    accountType,
                    bankID,
                    accountName,
                    accountNumber,
                    branchName,
                    province,
                    city);
        } else if (flag.equals("0")) {
            vo = SettleService.tx1343(serialNumber, orderNo, remark, amount,
                    accountType,
                    bankID,
                    accountName,
                    accountNumber,
                    branchName,
                    province,
                    city);
        } else {
            return "参数错误";
        }

        Map<String, String> fla = SendMessageServiceImpl.sendMessage(vo.getMessage(), vo.getSignature(), vo.getTxCode(), vo.getTxName(), vo.getFlag());
        PaySettlementRefund po = co.getPaySettlementRefund();
        if (po == null) {
            po = new PaySettlementRefund();
            String u = UUID.randomUUID().toString().replaceAll("-", "");
            po.setId(u);
        }

        po.setAccountType(accountType);
        po.setAcountName(accountName);
        po.setAcountNumber(accountNumber);
        po.setBranchName(branchName);
        po.setBankId(bankID);
        po.setBankName(bankName);
        po.setProvince(province);
        po.setCity(city);

        if (fla.get("message").equals("success")) {
            po.setAmount(co.getAmount());
            po.setSerialNumber(serialNumber);
            po.setOrderNo(orderNo);
            po.setFlag(flag);
            po.setResultFlag("1");
            if (flag.equals("1")) {
                co.setState(40);
            } else {
                co.setState(31);
            }

            co.setPaySettlementRefund(po);
            paymentDao.update(co);
            return "success";
        } else {
            po.setAmount(co.getAmount());
            po.setSerialNumber(serialNumber);
            po.setOrderNo(orderNo);
            po.setFlag(flag);
            po.setResultFlag("2");
            co.setPaySettlementRefund(po);
            paymentDao.update(co);
            //退款失败
            return fla.get("message");
        }

    }

    public SOrderPayment getByOrderNo(String orderNo) {
        //1:通过订单号查出信息或得messageID
        SOrderPayment payment = paymentDao.ByorderNo(orderNo);
        return payment;
    }

    @Resource
    private SettlementAndRefundService SettleService;
    @Resource
    private CloudCenterPaymentDao paymentDao;
    @Resource
    private CloudMangerDao cloudMangerDao;
    @Resource
    com.anchorcms.icloud.service.payment.impl.SendMessageServiceImpl SendMessageServiceImpl;
}
