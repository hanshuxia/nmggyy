package com.anchorcms.icloud.model.logistics;

import com.anchorcms.icloud.model.payment.SPOrder;
import com.anchorcms.icloud.model.payment.SPPay;

import javax.persistence.*;

/**
 * Created by DELL on 2017/3/22.
 */
@Entity
@Table(name = "s_logistics_info",catalog = "")
public class SLogistics {
    private String txlogisticId; // 物流订单号
    private String eccompanyId; // 电商标识
    private String logisticproviderId; // 物流公司代码
    private String customerId; // 客户标识
    private String tradeNo; // 业务交易号
    private String mailNo; // 运单编号
    private String returnNo; // 返单号
    private Integer orderType; // 订单类型
    private String serviceType; // 服务类型
    private String createOrderTime; // 订单创建时间
    private Double goodsValue; // 商品金额
    private Double itemsValue; // 代收货款金额
    private Integer payType; // 支付方式
    private String note; // 注释
    private String logisticsOrderState; // 物流订单状态
    private String senderProv; // 发货省份
    private String senderCity; // 发货城市
    private String senderArea; // 发货区域
    private String senderAddress; // 发货地址
    private String senderContact; // 发货人姓名
    private String senderMobile; // 发货人手机号
    private String receiverProv; // 收货省份
    private String receiverCity; // 收货城市
    private String receiverArea; // 收货区域
    private String receiverAddress; // 收货地址
    private String receiverContact; // 收货人姓名
    private String receiverMobile; // 收货人手机号
    private String isBackground; // 是否后台物流订单(1是 0否)
    private String senderPostcode; // 发货人邮编
    private String receiverPostcode; // 收货人邮编
    private String supplyCompany; // 发货人公司
    private String buyCompany; // 收货人公司

    @Id
    @Column(name = "TXLOGISTIC_ID")
    public String getTxlogisticId() {
        return txlogisticId;
    }

    public void setTxlogisticId(String txlogisticId) {
        this.txlogisticId = txlogisticId;
    }

    @Basic
    @Column(name = "ECCOMPANY_ID")
    public String getEccompanyId() {
        return eccompanyId;
    }

    public void setEccompanyId(String eccompanyId) {
        this.eccompanyId = eccompanyId;
    }

    @Basic
    @Column(name = "LOGISTICPROVIDER_ID")
    public String getLogisticproviderId() {
        return logisticproviderId;
    }

    public void setLogisticproviderId(String logisticproviderId) {
        this.logisticproviderId = logisticproviderId;
    }

    @Basic
    @Column(name = "CUSTOMER_ID")
    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    @Basic
    @Column(name = "TRADE_NO")
    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    @Basic
    @Column(name = "MAIL_NO")
    public String getMailNo() {
        return mailNo;
    }

    public void setMailNo(String mailNo) {
        this.mailNo = mailNo;
    }

    @Basic
    @Column(name = "RETURN_NO")
    public String getReturnNo() {
        return returnNo;
    }

    public void setReturnNo(String returnNo) {
        this.returnNo = returnNo;
    }

    @Basic
    @Column(name = "ORDER_TYPE")
    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    @Basic
    @Column(name = "SERVICE_TYPE")
    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    @Basic
    @Column(name = "CREATEORDER_TIME")
    public String getCreateOrderTime() {
        return createOrderTime;
    }

    public void setCreateOrderTime(String createOrderTime) {
        this.createOrderTime = createOrderTime;
    }

    @Basic
    @Column(name = "GOODS_VALUE")
    public Double getGoodsValue() {
        return goodsValue;
    }

    public void setGoodsValue(Double goodsValue) {
        this.goodsValue = goodsValue;
    }

    @Basic
    @Column(name = "ITEMS_VALUE")
    public Double getItemsValue() {
        return itemsValue;
    }

    public void setItemsValue(Double itemsValue) {
        this.itemsValue = itemsValue;
    }

    @Basic
    @Column(name = "PAY_TYPE")
    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    @Basic
    @Column(name = "NOTE")
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Basic
    @Column(name = "LOGISTICS_ORDER_STATE")
    public String getLogisticsOrderState() {
        return logisticsOrderState;
    }

    public void setLogisticsOrderState(String logisticsOrderState) {
        this.logisticsOrderState = logisticsOrderState;
    }

    @Basic
    @Column(name = "SENDER_PROV")
    public String getSenderProv() {
        return senderProv;
    }

    public void setSenderProv(String senderProv) {
        this.senderProv = senderProv;
    }

    @Basic
    @Column(name = "SENDER_CITY")
    public String getSenderCity() {
        return senderCity;
    }

    public void setSenderCity(String senderCity) {
        this.senderCity = senderCity;
    }

    @Basic
    @Column(name = "SENDER_AREA")
    public String getSenderArea() {
        return senderArea;
    }

    public void setSenderArea(String senderArea) {
        this.senderArea = senderArea;
    }

    @Basic
    @Column(name = "SENDER_ADDRESS")
    public String getSenderAddress() {
        return senderAddress;
    }

    public void setSenderAddress(String senderAddress) {
        this.senderAddress = senderAddress;
    }

    @Basic
    @Column(name = "SENDER_CONTACT")
    public String getSenderContact() {
        return senderContact;
    }

    public void setSenderContact(String senderContact) {
        this.senderContact = senderContact;
    }

    @Basic
    @Column(name = "SENDER_MOBILE")
    public String getSenderMobile() {
        return senderMobile;
    }

    public void setSenderMobile(String senderMobile) {
        this.senderMobile = senderMobile;
    }

    @Basic
    @Column(name = "RECEIVER_PROV")
    public String getReceiverProv() {
        return receiverProv;
    }

    public void setReceiverProv(String receiverProv) {
        this.receiverProv = receiverProv;
    }

    @Basic
    @Column(name = "RECEIVER_CITY")
    public String getReceiverCity() {
        return receiverCity;
    }

    public void setReceiverCity(String receiverCity) {
        this.receiverCity = receiverCity;
    }

    @Basic
    @Column(name = "RECEIVER_AREA")
    public String getReceiverArea() {
        return receiverArea;
    }

    public void setReceiverArea(String receiverArea) {
        this.receiverArea = receiverArea;
    }

    @Basic
    @Column(name = "RECEIVER_ADDRESS")
    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    @Basic
    @Column(name = "RECEIVER_CONTACT")
    public String getReceiverContact() {
        return receiverContact;
    }

    public void setReceiverContact(String receiverContact) {
        this.receiverContact = receiverContact;
    }

    @Basic
    @Column(name = "RECEIVER_MOBILE")
    public String getReceiverMobile() {
        return receiverMobile;
    }

    public void setReceiverMobile(String receiverMobile) {
        this.receiverMobile = receiverMobile;
    }

    @Basic
    @Column(name = "IS_BACKGROUND")
    public String getIsBackground() {
        return isBackground;
    }

    public void setIsBackground(String isBackground) {
        this.isBackground = isBackground;
    }

    @Basic
    @Column(name = "SENDER_POSTCODE")
    public String getSenderPostcode() {
        return senderPostcode;
    }

    public void setSenderPostcode(String senderPostcode) {
        this.senderPostcode = senderPostcode;
    }

    @Basic
    @Column(name = "RECEIVER_POSTCODE")
    public String getReceiverPostcode() {
        return receiverPostcode;
    }

    public void setReceiverPostcode(String receiverPostcode) {
        this.receiverPostcode = receiverPostcode;
    }

    @Basic
    @Column(name = "SUPPLY_COMPANY")
    public String getSupplyCompany() {
        return supplyCompany;
    }

    public void setSupplyCompany(String supplyCompany) {
        this.supplyCompany = supplyCompany;
    }

    @Basic
    @Column(name = "BUY_COMPANY")
    public String getBuyCompany() {
        return buyCompany;
    }

    public void setBuyCompany(String buyCompany) {
        this.buyCompany = buyCompany;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SLogistics that = (SLogistics) o;

        if (txlogisticId != null ? !txlogisticId.equals(that.txlogisticId) : that.txlogisticId != null) return false;
        if (eccompanyId != null ? !eccompanyId.equals(that.eccompanyId) : that.eccompanyId != null)
            return false;
        if (logisticproviderId != null ? !logisticproviderId.equals(that.logisticproviderId) : that.logisticproviderId != null) return false;
        if (customerId != null ? !customerId.equals(that.customerId) : that.customerId != null) return false;
        if (tradeNo != null ? !tradeNo.equals(that.tradeNo) : that.tradeNo != null) return false;
        if (mailNo != null ? !mailNo.equals(that.mailNo) : that.mailNo != null) return false;
        if (returnNo != null ? !returnNo.equals(that.returnNo) : that.returnNo != null) return false;
        if (orderType != null ? !orderType.equals(that.orderType) : that.orderType != null) return false;
        if (serviceType != null ? !serviceType.equals(that.serviceType) : that.serviceType != null) return false;
        if (createOrderTime != null ? !createOrderTime.equals(that.createOrderTime) : that.createOrderTime != null) return false;
        if (goodsValue != null ? !goodsValue.equals(that.goodsValue) : that.goodsValue != null) return false;
        if (itemsValue != null ? !itemsValue.equals(that.itemsValue) : that.itemsValue != null) return false;
        if (payType != null ? !payType.equals(that.payType) : that.payType != null) return false;
        if (note != null ? !note.equals(that.note) : that.note != null) return false;
        if (senderProv != null ? !senderProv.equals(that.senderProv) : that.senderProv != null) return false;
        if (senderCity != null ? !senderCity.equals(that.senderCity) : that.senderCity != null) return false;
        if (senderArea != null ? !senderArea.equals(that.senderArea) : that.senderArea != null) return false;
        if (senderAddress != null ? !senderAddress.equals(that.senderAddress) : that.senderAddress != null) return false;
        if (senderContact != null ? !senderContact.equals(that.senderContact) : that.senderContact != null) return false;
        if (senderMobile != null ? !senderMobile.equals(that.senderMobile) : that.senderMobile != null) return false;
        if (receiverProv != null ? !receiverProv.equals(that.receiverProv) : that.receiverProv != null) return false;
        if (receiverCity != null ? !receiverCity.equals(that.receiverCity) : that.receiverCity != null) return false;
        if (receiverArea != null ? !receiverArea.equals(that.receiverArea) : that.receiverArea != null) return false;
        if (receiverAddress != null ? !receiverAddress.equals(that.receiverAddress) : that.receiverAddress != null) return false;
        if (receiverContact != null ? !receiverContact.equals(that.receiverContact) : that.receiverContact != null) return false;
        if (receiverMobile != null ? !receiverMobile.equals(that.receiverMobile) : that.receiverMobile != null) return false;
        if (isBackground != null ? !isBackground.equals(that.isBackground) : that.isBackground != null) return false;
        if (senderPostcode != null ? !senderPostcode.equals(that.senderPostcode) : that.senderPostcode != null) return false;
        if (receiverPostcode != null ? !receiverPostcode.equals(that.receiverPostcode) : that.receiverPostcode != null) return false;
        if (supplyCompany != null ? !supplyCompany.equals(that.supplyCompany) : that.supplyCompany != null) return false;
        if (buyCompany != null ? !buyCompany.equals(that.buyCompany) : that.buyCompany != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = txlogisticId != null ? txlogisticId.hashCode() : 0;
        result = 31 * result + (eccompanyId != null ? eccompanyId.hashCode() : 0);
        result = 31 * result + (logisticproviderId != null ? logisticproviderId.hashCode() : 0);
        result = 31 * result + (customerId != null ? customerId.hashCode() : 0);
        result = 31 * result + (tradeNo != null ? tradeNo.hashCode() : 0);
        result = 31 * result + (mailNo != null ? mailNo.hashCode() : 0);
        result = 31 * result + (returnNo != null ? returnNo.hashCode() : 0);
        result = 31 * result + (orderType != null ? orderType.hashCode() : 0);
        result = 31 * result + (serviceType != null ? serviceType.hashCode() : 0);
        result = 31 * result + (createOrderTime != null ? createOrderTime.hashCode() : 0);
        result = 31 * result + (goodsValue != null ? goodsValue.hashCode() : 0);
        result = 31 * result + (itemsValue != null ? itemsValue.hashCode() : 0);
        result = 31 * result + (payType != null ? payType.hashCode() : 0);
        result = 31 * result + (note != null ? note.hashCode() : 0);
        result = 31 * result + (senderProv != null ? senderProv.hashCode() : 0);
        result = 31 * result + (senderCity != null ? senderCity.hashCode() : 0);
        result = 31 * result + (senderArea != null ? senderArea.hashCode() : 0);
        result = 31 * result + (senderAddress != null ? senderAddress.hashCode() : 0);
        result = 31 * result + (senderContact != null ? senderContact.hashCode() : 0);
        result = 31 * result + (senderMobile != null ? senderMobile.hashCode() : 0);
        result = 31 * result + (receiverProv != null ? receiverProv.hashCode() : 0);
        result = 31 * result + (receiverCity != null ? receiverCity.hashCode() : 0);
        result = 31 * result + (receiverArea != null ? receiverArea.hashCode() : 0);
        result = 31 * result + (receiverAddress != null ? receiverAddress.hashCode() : 0);
        result = 31 * result + (receiverContact != null ? receiverContact.hashCode() : 0);
        result = 31 * result + (receiverMobile != null ? receiverMobile.hashCode() : 0);
        result = 31 * result + (isBackground != null ? isBackground.hashCode() : 0);
        result = 31 * result + (senderPostcode != null ? senderPostcode.hashCode() : 0);
        result = 31 * result + (receiverPostcode != null ? receiverPostcode.hashCode() : 0);
        result = 31 * result + (supplyCompany != null ? supplyCompany.hashCode() : 0);
        result = 31 * result + (buyCompany != null ? buyCompany.hashCode() : 0);

        return result;
    }
    private SPOrder spOrder;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "TXLOGISTIC_ID")
    public SPOrder getSpOrder() {
        return spOrder;
    }

    public void setSpOrder(SPOrder spOrder) {
        this.spOrder = spOrder;
    }
}
