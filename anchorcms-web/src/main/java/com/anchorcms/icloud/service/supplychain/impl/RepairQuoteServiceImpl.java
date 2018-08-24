package com.anchorcms.icloud.service.supplychain.impl;


import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.dao.supplychain.RepairQuoteDao;
import com.anchorcms.icloud.dao.synergy.OrderPayDao;
import com.anchorcms.icloud.model.payment.SPSettle;
import com.anchorcms.icloud.model.payment.SSupplychainOrder;
import com.anchorcms.icloud.model.payment.SendMessageVO;
import com.anchorcms.icloud.model.supplychain.SRepairQuote;
import com.anchorcms.icloud.service.payment.SettlementAndRefundService;
import com.anchorcms.icloud.service.supplychain.RepairQuoteService;
import com.anchorcms.icloud.service.synergy.ALDPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import payment.api.util.GUIDGenerator;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * Created by 潘晓东 on 2016/12/27.
 */
@Service
@Transactional
public class RepairQuoteServiceImpl implements RepairQuoteService {
    /**
     *@Author 潘晓东
     *@Date 2017/1/5 14:51
     *@Return抢单报价列表查询分页
     */
    public Pagination getQuoteListForMember(CmsUser user,String repairName, String inquiryTheme, Date myStartDate, Date myEndDate, String companyId,
                                            int pageNo, int pageSize){
        return repairQuoteDao.getQuoteListForMember(user, repairName,inquiryTheme, myStartDate, myEndDate, companyId, pageNo,pageSize);
    }

    /***
     * @author zhouyq
     * @date 2017-05-03
     * @return
     * @desc 供应链维修方订单列表
     */
    public Pagination getSupplychainSellerOrder(Integer siteId, CmsUser user, int pageNo, int pageSize, Date startDate, String state, String orderId){
        return repairQuoteDao.getSupplychainSellerOrder(siteId, user, pageNo, pageSize, startDate, state, orderId);
    }

    /**
     * @param
     * @return
     * @Author zhouyq
     * @Date 2017/05/03
     * @Desc 根据id修改订单状态
     */
    public void mdyOrderStateById(String orderId, String state, String backReason) {
        SSupplychainOrder sSupplychainOrder = orderPayDao.getSupplychainOrderById(orderId);
        sSupplychainOrder.setBackReason(backReason);
        if (state != null) {
            repairQuoteDao.mdyOrderStateById(orderId, state);
        }
    }

    /**
     * @return 结算模块
     * @author zhouyq
     * @Date 2017/5/3
     */
    @Resource
    ALDPayService ALDPayService;
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
                         String city, String orderId, String flag) throws Exception {
        SSupplychainOrder sSupplychainOrder = orderPayDao.getSupplychainOrderById(orderNo);
        SPSettle spSettle = sSupplychainOrder.getSpPay().getSpSettle();
        serialNumber = GUIDGenerator.genGUID(); // 生成新的交易流水号
        SendMessageVO vo ;
        if (flag.equals("1")) { // 结算
            // 处理手续费
            Map<String,Double> m =   ALDPayService.calc(sSupplychainOrder.getPrice(),accountType);
            double fee = m.get("fee");
            amount=(long)(m.get("price") * 100);
            ALDPayService.adminSaveSettle(orderId,fee,m.get("price"),"1");

            vo = SettleService.tx1341(serialNumber, orderNo, remark, amount,
                    accountType,
                    bankID,
                    accountName,
                    accountNumber,
                    branchName,
                    province,
                    city);
        } else if (flag.equals("0")) { // 退款
            amount = (long) (sSupplychainOrder.getPrice() * 100);
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
        if (spSettle == null) {
            spSettle = new SPSettle();
            String u = UUID.randomUUID().toString().replaceAll("-", "");
            spSettle.setId(u);
        }
        spSettle.setAccountType(accountType);
        spSettle.setAcountName(accountName);
        spSettle.setAcountNumber(accountNumber);
        spSettle.setBranchName(branchName);
        spSettle.setBankId(bankID);
        spSettle.setBankName(bankName);
        spSettle.setProvince(province);
        spSettle.setCity(city);
        if (fla.get("message").equals("success")) { // 成功
            spSettle.setAmount(spSettle.getAmount());
            spSettle.setSerialNumber(serialNumber);
            spSettle.setOrderNo(orderNo);
            spSettle.setFlag(flag);
            spSettle.setResultFlag("1");
            sSupplychainOrder.getSpPay().setSpSettle(spSettle);
            if (flag.equals("1")) {
                sSupplychainOrder.setState("40");
            } else if (flag.equals("0")) {
                sSupplychainOrder.setState("31");
            }
            orderPayDao.updateSupplychainOrder(sSupplychainOrder);
            return "success";
        } else { // 失败
            spSettle.setAmount(sSupplychainOrder.getSpPay().getAmount());
            spSettle.setSerialNumber(serialNumber);
            spSettle.setOrderNo(orderNo);
            spSettle.setFlag(flag);
            spSettle.setResultFlag("2");
            sSupplychainOrder.getSpPay().setSpSettle(spSettle);
            orderPayDao.updateSupplychainOrder(sSupplychainOrder);
            // 退款失败
            return fla.get("message");
        }
    }

    /**
    *@Author 潘晓东
    *@Date 2017/1/23 13:57
    *@Return根据ID获取报价表信息
    */
    public SRepairQuote selectByQuoteID(String id){
        return repairQuoteDao.selectByQuoteID(id);
    }

    @Autowired
    private RepairQuoteDao repairQuoteDao;
    @Resource
    private OrderPayDao orderPayDao;
    @Resource
    private SettlementAndRefundService SettleService;
    @Resource
    com.anchorcms.icloud.service.payment.impl.SendMessageServiceImpl SendMessageServiceImpl;
}
