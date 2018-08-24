package com.anchorcms.icloud.model.software;

import com.anchorcms.icloud.model.payment.PaySettlementRefund;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by DELL on 2017/2/20.
 */
@Entity
@Table(name = "s_software_buy")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE )
public class SSoftwareBuy {
    private static final long serialVersionUID = 8907361038465531530L;
    private int buyId;
    private Integer softwareId;
    private String softwareName;
    private Integer userId;
    private String userName;
    private Date buyDt;
    private Double buyPrice;
    private Integer createrId;
    private Date createDt;
    private String deFlag;
    private String institutionId;
    private String paymentNo;
    private String orderNo;
    private Double amount;
    private Double fee;
    private Integer payerId;
    private String ueage;
    private String remark;
    private String payees;
    private String bankId;
    private String bankName;
    private Integer accountType;
    private String cardType;
    private String status;
    private String backReason; // 拒绝退款原因
    private String isOnline;//是否在线应用
    private String onlinePay;//是否在线付款
    @Basic
    @Column(name = "IS_ONLINE")
    public String getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(String isOnline) {
        this.isOnline = isOnline;
    }

    @Basic
    @Column(name = "ONLINE_PAY")
    public String getOnlinePay() {
        return onlinePay;
    }

    public void setOnlinePay(String onlinePay) {
        this.onlinePay = onlinePay;
    }
    @Id
    @Column(name = "BUY_ID")
    public int getBuyId() {
        return buyId;
    }

    public void setBuyId(int buyId) {
        this.buyId = buyId;
    }

    @Basic
    @Column(name = "SOFTWARE_ID")
    public Integer getSoftwareId() {
        return softwareId;
    }

    public void setSoftwareId(Integer softwareId) {
        this.softwareId = softwareId;
    }
    @Basic
    @Column(name = "SOFTWARE_NAME")
    public String getSoftwareName() {
        return softwareName;
    }

    public void setSoftwareName(String softwareId) {
        this.softwareName = softwareId;
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

    public void setUserName(String userId) {
        this.userName = userId;
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
    @Column(name = "DE_FLAG")
    public String getDeFlag() {
        return deFlag;
    }

    public void setDeFlag(String deFlag) {
        this.deFlag = deFlag;
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
    @Column(name = "BANK_NAME")
    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
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

    @Basic
    @Column(name = "BACKREASON")
    public String getBackReason() {
        return backReason;
    }

    public void setBackReason(String backReason) {
        this.backReason = backReason;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SSoftwareBuy that = (SSoftwareBuy) o;

        if (buyId != that.buyId) return false;
        //if (softwareId != null ? !softwareId.equals(that.softwareId) : that.softwareId != null) return false;
        //if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (buyDt != null ? !buyDt.equals(that.buyDt) : that.buyDt != null) return false;
        if (buyPrice != null ? !buyPrice.equals(that.buyPrice) : that.buyPrice != null) return false;
        if (createrId != null ? !createrId.equals(that.createrId) : that.createrId != null) return false;
        if (createDt != null ? !createDt.equals(that.createDt) : that.createDt != null) return false;
        if (deFlag != null ? !deFlag.equals(that.deFlag) : that.deFlag != null) return false;
        if (institutionId != null ? !institutionId.equals(that.institutionId) : that.institutionId != null)
            return false;
        if (paymentNo != null ? !paymentNo.equals(that.paymentNo) : that.paymentNo != null) return false;
        if (orderNo != null ? !orderNo.equals(that.orderNo) : that.orderNo != null) return false;
        if (amount != null ? !amount.equals(that.amount) : that.amount != null) return false;
        if (fee != null ? !fee.equals(that.fee) : that.fee != null) return false;
        if (payerId != null ? !payerId.equals(that.payerId) : that.payerId != null) return false;
        if (ueage != null ? !ueage.equals(that.ueage) : that.ueage != null) return false;
        if (remark != null ? !remark.equals(that.remark) : that.remark != null) return false;
        if (payees != null ? !payees.equals(that.payees) : that.payees != null) return false;
        if (bankId != null ? !bankId.equals(that.bankId) : that.bankId != null) return false;
        if (accountType != null ? !accountType.equals(that.accountType) : that.accountType != null) return false;
        if (cardType != null ? !cardType.equals(that.cardType) : that.cardType != null) return false;
        if (backReason != null ? !backReason.equals(that.backReason) : that.backReason != null) return false;
        if (onlinePay != null ? !onlinePay.equals(that.onlinePay) : that.onlinePay != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = buyId;
        //result = 31 * result + (softwareId != null ? softwareId.hashCode() : 0);
        //result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (buyDt != null ? buyDt.hashCode() : 0);
        result = 31 * result + (buyPrice != null ? buyPrice.hashCode() : 0);
        result = 31 * result + (createrId != null ? createrId.hashCode() : 0);
        result = 31 * result + (createDt != null ? createDt.hashCode() : 0);
        result = 31 * result + (deFlag != null ? deFlag.hashCode() : 0);
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
        result = 31 * result + (backReason != null ? backReason.hashCode() : 0);
        result = 31 * result + (onlinePay != null ? onlinePay.hashCode() : 0);
        return result;
    }
  /*  private SSoftware software;
    private CmsUser user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "SOFTWARE_ID")
    public SSoftware getSoftware() {
        return software;
    }

    public void setSoftware(SSoftware software) {
        this.software = software;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID")
    public CmsUser getUser() {
        return user;
    }

    public void setUser(CmsUser user) {
        this.user = user;
    }*/
    private PaySettlementRefund paySettlementRefund;
  @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
  @JoinColumn(name = "SETTLEMENT_ID")
  public PaySettlementRefund getPaySettlementRefund() {
      return paySettlementRefund;
  }
    public void setPaySettlementRefund(PaySettlementRefund paySettlementRefund) {
        this.paySettlementRefund = paySettlementRefund;
    }
}
