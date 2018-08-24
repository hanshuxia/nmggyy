package com.anchorcms.icloud.model.payment;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by DELL on 2017/5/1.
 */
@Entity
@Table(name = "s_p__pay", catalog = "")
public class SPPay {
    private int id;      //主键
    private Integer userId;
    private String userName;
    private Date buyDt;   //购买时间
    private Double buyPrice;   //价格
    private Integer createrId;  //付款人
    private Date createDt;        //
    private String institutionId;   //机构号
    private String paymentNo;       //支付流水号
    private String orderNo;         //订单号
    private Double amount;          //支付金额()
    private Double fee;              //手续费
    private Integer payerId;          //支付人
    private String ueage;             //资金用途
    private String remark;            //备注
    private String payees;
    private String bankId;            //银行id
    private Integer accountType;      //账户类型   11 个人账户    12 企业账户
    private String cardType;          //卡类型    01借记卡    02  贷记卡
    private String status;              //状态
    //private String settlementId;
    private String backreason;           //拒绝退款原因
    private String bankName;            //银行名称

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "USER_ID")
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "USER_NAME")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Basic
    @Column(name = "BUY_DT")
    public Date getBuyDt() {
        return buyDt;
    }

    public void setBuyDt(Date buyDt) {
        this.buyDt = buyDt;
    }

    @Basic
    @Column(name = "BUY_PRICE")
    public Double getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(Double buyPrice) {
        this.buyPrice = buyPrice;
    }

    @Basic
    @Column(name = "CREATER_ID")
    public Integer getCreaterId() {
        return createrId;
    }

    public void setCreaterId(Integer createrId) {
        this.createrId = createrId;
    }

    @Basic
    @Column(name = "CREATE_DT")
    public Date getCreateDt() {
        return createDt;
    }

    public void setCreateDt(Date createDt) {
        this.createDt = createDt;
    }

    @Basic
    @Column(name = "INSTITUTION_ID")
    public String getInstitutionId() {
        return institutionId;
    }

    public void setInstitutionId(String institutionId) {
        this.institutionId = institutionId;
    }

    @Basic
    @Column(name = "PAYMENT_NO")
    public String getPaymentNo() {
        return paymentNo;
    }

    public void setPaymentNo(String paymentNo) {
        this.paymentNo = paymentNo;
    }

    @Basic
    @Column(name = "ORDER_NO")
    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    @Basic
    @Column(name = "AMOUNT")
    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Basic
    @Column(name = "FEE")
    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    @Basic
    @Column(name = "PAYER_ID")
    public Integer getPayerId() {
        return payerId;
    }

    public void setPayerId(Integer payerId) {
        this.payerId = payerId;
    }

    @Basic
    @Column(name = "UEAGE")
    public String getUeage() {
        return ueage;
    }

    public void setUeage(String ueage) {
        this.ueage = ueage;
    }

    @Basic
    @Column(name = "REMARK")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Basic
    @Column(name = "PAYEES")
    public String getPayees() {
        return payees;
    }

    public void setPayees(String payees) {
        this.payees = payees;
    }

    @Basic
    @Column(name = "BANK_ID")
    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    @Basic
    @Column(name = "ACCOUNT_TYPE")
    public Integer getAccountType() {
        return accountType;
    }

    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }

    @Basic
    @Column(name = "CARD_TYPE")
    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    @Basic
    @Column(name = "STATUS")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    //@Basic
    //@Column(name = "SETTLEMENT_ID")
    //public String getSettlementId() {
    //    return settlementId;
    //}
    //
    //public void setSettlementId(String settlementId) {
    //    this.settlementId = settlementId;
    //}

    @Basic
    @Column(name = "BACKREASON")
    public String getBackreason() {
        return backreason;
    }

    public void setBackreason(String backreason) {
        this.backreason = backreason;
    }

    @Basic
    @Column(name = "BANK_NAME")
    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SPPay spPay = (SPPay) o;

        if (id != spPay.id) return false;
        if (userId != null ? !userId.equals(spPay.userId) : spPay.userId != null) return false;
        if (userName != null ? !userName.equals(spPay.userName) : spPay.userName != null) return false;
        if (buyDt != null ? !buyDt.equals(spPay.buyDt) : spPay.buyDt != null) return false;
        if (buyPrice != null ? !buyPrice.equals(spPay.buyPrice) : spPay.buyPrice != null) return false;
        if (createrId != null ? !createrId.equals(spPay.createrId) : spPay.createrId != null) return false;
        if (createDt != null ? !createDt.equals(spPay.createDt) : spPay.createDt != null) return false;
        if (institutionId != null ? !institutionId.equals(spPay.institutionId) : spPay.institutionId != null)
            return false;
        if (paymentNo != null ? !paymentNo.equals(spPay.paymentNo) : spPay.paymentNo != null) return false;
        if (orderNo != null ? !orderNo.equals(spPay.orderNo) : spPay.orderNo != null) return false;
        if (amount != null ? !amount.equals(spPay.amount) : spPay.amount != null) return false;
        if (fee != null ? !fee.equals(spPay.fee) : spPay.fee != null) return false;
        if (payerId != null ? !payerId.equals(spPay.payerId) : spPay.payerId != null) return false;
        if (ueage != null ? !ueage.equals(spPay.ueage) : spPay.ueage != null) return false;
        if (remark != null ? !remark.equals(spPay.remark) : spPay.remark != null) return false;
        if (payees != null ? !payees.equals(spPay.payees) : spPay.payees != null) return false;
        if (bankId != null ? !bankId.equals(spPay.bankId) : spPay.bankId != null) return false;
        if (accountType != null ? !accountType.equals(spPay.accountType) : spPay.accountType != null) return false;
        if (cardType != null ? !cardType.equals(spPay.cardType) : spPay.cardType != null) return false;
        if (status != null ? !status.equals(spPay.status) : spPay.status != null) return false;
        //if (settlementId != null ? !settlementId.equals(spPay.settlementId) : spPay.settlementId != null) return false;
        if (backreason != null ? !backreason.equals(spPay.backreason) : spPay.backreason != null) return false;
        if (bankName != null ? !bankName.equals(spPay.bankName) : spPay.bankName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (buyDt != null ? buyDt.hashCode() : 0);
        result = 31 * result + (buyPrice != null ? buyPrice.hashCode() : 0);
        result = 31 * result + (createrId != null ? createrId.hashCode() : 0);
        result = 31 * result + (createDt != null ? createDt.hashCode() : 0);
        result = 31 * result + (institutionId != null ? institutionId.hashCode() : 0);
        result = 31 * result + (paymentNo != null ? paymentNo.hashCode() : 0);
        result = 31 * result + (orderNo != null ? orderNo.hashCode() : 0);
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        result = 31 * result + (fee != null ? fee.hashCode() : 0);
        result = 31 * result + (payerId != null ? payerId.hashCode() : 0);
        result = 31 * result + (ueage != null ? ueage.hashCode() : 0);
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        result = 31 * result + (payees != null ? payees.hashCode() : 0);
        result = 31 * result + (bankId != null ? bankId.hashCode() : 0);
        result = 31 * result + (accountType != null ? accountType.hashCode() : 0);
        result = 31 * result + (cardType != null ? cardType.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        //result = 31 * result + (settlementId != null ? settlementId.hashCode() : 0);
        result = 31 * result + (backreason != null ? backreason.hashCode() : 0);
        result = 31 * result + (bankName != null ? bankName.hashCode() : 0);
        return result;
    }
    private SPSettle spSettle;
    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "SETTLEMENT_ID")
    public SPSettle getSpSettle() {
        return spSettle;
    }
    public void setSpSettle(SPSettle spSettle) {
        this.spSettle = spSettle;
    }
}
