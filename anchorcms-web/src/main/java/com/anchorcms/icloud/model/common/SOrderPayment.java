package com.anchorcms.icloud.model.common;

import com.anchorcms.cms.model.main.Content;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.model.cloudcenter.SIcloudManage;
import com.anchorcms.icloud.model.payment.PaySettlementRefund;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * Created by ly on 2017/2/18.
 */
@Entity
@Table(name = "s_order_payment")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE )
public class SOrderPayment implements Serializable{
    private static final long serialVersionUID = -8075160117153589416L;
    private int orderId;
   // private Integer commodityId;
    private Integer channelId;
    private String institutionId;
    private String paymentNo;
    private String orderNo;
    private Double amount;
    private Double fee;
    private String ueage;
    private String remark;
    private String payees;
    private String bankName;
    private String bankId;
    private Integer accountType;
    private String cardType;
    private Integer state;
    private Date createDT;
    private Date updateDT;
    private String count;//数量

    private Date rentTimeStart; // 租赁开始时间
    private Date rentAllTime; // 租赁结束时间
    private String rentTime; // 使用期限(月)
    private String contact; // 联系人
    private String mobile; // 联系电话
    private String msg; // 申请说明
    private String backReason; // 拒绝退款原因
    private String capacityUnit; // 容量
    private String specVersion; // 租赁单位
    private String isOnline; // 是否在线支付状态
    @Basic
    @Column(name = "CAPACITY_UNIT")
    public String getCapacityUnit() {
        return capacityUnit;
    }

    public void setCapacityUnit(String capacityUnit) {
        this.capacityUnit = capacityUnit;
    }

    @Column(name = "SPEC_VERSION")
    public String getSpecVersion() {
        return specVersion;
    }

    public void setSpecVersion(String specVersion) {
        this.specVersion = specVersion;
    }

    @Basic
    @Column(name = "BACK_REASON")
    public String getBackReason() {
        return backReason;
    }

    public void setBackReason(String backReason) {
        this.backReason = backReason;
    }

    @Basic
    @Column(name="RENTTIMESTART")
    public Date getRentTimeStart() {
        return rentTimeStart;
    }

    public void setRentTimeStart(Date rentTimeStart) {
        this.rentTimeStart = rentTimeStart;
    }



    @Basic
    @Column(name="RENTTIMEEND")
    public Date getRentAllTime() {
        return rentAllTime;
    }

    public void setRentAllTime(Date rentAllTime) {
        this.rentAllTime = rentAllTime;
    }


    @Basic
    @Column(name="RENTTIME")
    public String getRentTime() {
        return rentTime;
    }

    public void setRentTime(String rentTime) {
        this.rentTime = rentTime;
    }


    @Basic
    @Column(name="CONTACT")

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }


    @Basic
    @Column(name="MOBILE")
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }


    @Basic
     @Column(name="MSG")

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }



    @Basic
    @Column(name="COUNT")
    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_ID")
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

   /* @Basic
    @Column(name = "COMMODITY_ID")
    public Integer getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(Integer commodityId) {
        this.commodityId = commodityId;
    }
*/
    @Basic
    @Column(name = "CHANNEL_ID")
    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
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
    @Column(name = "STATE")
    public Integer getState() {
        return state;
    }


    public void setState(Integer state) {
        this.state = state;
    }

    @Basic
    @Column(name = "CREATE_DT")
    public Date getCreateDT() {
        return createDT;
    }

    public void setCreateDT(Date createDT) {
        this.createDT = createDT;
    }

    @Basic
    @Column(name = "UPDATE_DT")
    public Date getUpdateDT() {
        return updateDT;
    }

    public void setUpdateDT(Date updateDT) {
        this.updateDT = updateDT;
    }

    @Basic
    @Column(name = "IS_ONLINE")
    public String getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(String isOnline) {
        this.isOnline = isOnline;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SOrderPayment that = (SOrderPayment) o;

        if (orderId != that.orderId) return false;
        //if (commodityId != null ? !commodityId.equals(that.commodityId) : that.commodityId != null) return false;
        if (channelId != null ? !channelId.equals(that.channelId) : that.channelId != null) return false;
        if (institutionId != null ? !institutionId.equals(that.institutionId) : that.institutionId != null)
            return false;
        if (paymentNo != null ? !paymentNo.equals(that.paymentNo) : that.paymentNo != null) return false;
        if (orderNo != null ? !orderNo.equals(that.orderNo) : that.orderNo != null) return false;
        if (amount != null ? !amount.equals(that.amount) : that.amount != null) return false;
        if (fee != null ? !fee.equals(that.fee) : that.fee != null) return false;
        if (ueage != null ? !ueage.equals(that.ueage) : that.ueage != null) return false;
        if (remark != null ? !remark.equals(that.remark) : that.remark != null) return false;
        if (payees != null ? !payees.equals(that.payees) : that.payees != null) return false;
        if (bankId != null ? !bankId.equals(that.bankId) : that.bankId != null) return false;
        if (accountType != null ? !accountType.equals(that.accountType) : that.accountType != null) return false;
        if (cardType != null ? !cardType.equals(that.cardType) : that.cardType != null) return false;
        if (state != null ? !state.equals(that.state) : that.state != null) return false;
        if (isOnline != null ? !isOnline.equals(that.isOnline) : that.isOnline != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = orderId;
       // result = 31 * result + (commodityId != null ? commodityId.hashCode() : 0);
        result = 31 * result + (channelId != null ? channelId.hashCode() : 0);
        result = 31 * result + (institutionId != null ? institutionId.hashCode() : 0);
        result = 31 * result + (paymentNo != null ? paymentNo.hashCode() : 0);
        result = 31 * result + (orderNo != null ? orderNo.hashCode() : 0);
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        result = 31 * result + (fee != null ? fee.hashCode() : 0);
        result = 31 * result + (ueage != null ? ueage.hashCode() : 0);
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        result = 31 * result + (payees != null ? payees.hashCode() : 0);
        result = 31 * result + (bankId != null ? bankId.hashCode() : 0);
        result = 31 * result + (accountType != null ? accountType.hashCode() : 0);
        result = 31 * result + (cardType != null ? cardType.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (isOnline != null ? isOnline.hashCode() : 0);
        return result;
    }

    private CmsUser payUser;
    @ManyToOne
    @JoinColumn(name="payer_id")

    public CmsUser getPayUser() {
        return payUser;
    }

    public void setPayUser(CmsUser payUser) {
        this.payUser = payUser;
    }

    private SIcloudManage manage;
    @OneToOne
    @JoinColumn(name="COMMODITY_ID")

    public SIcloudManage getManage() {
        return manage;
    }

    public void setManage(SIcloudManage manage) {
        this.manage = manage;
    }
    private PaySettlementRefund paySettlementRefund;
    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "SETTLEMENT_ID")
    public PaySettlementRefund getPaySettlementRefund() {
        return paySettlementRefund;
    }
    public void setPaySettlementRefund(PaySettlementRefund paySettlementRefund) {
        this.paySettlementRefund = paySettlementRefund;
    }


    private Content content;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "content_id")
    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }
}
