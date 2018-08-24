package com.anchorcms.icloud.model.payment;

import com.anchorcms.icloud.model.synergy.SCompany;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

/**
 * Created by zhouyq on 2017/6/2.
 */
@Entity
@Table(name = "s_device_order", catalog = "")
public class SDeviceOrder {
    private String orderId;
//    private String buyCompanyId;
//    private String supplyCompanyId;
    private String buyContact;
    private String buyMobile;
    private String supplyContact;
    private String supplyMobile;
    private String operator;
    private String freighter;
    private Double price;
    private Double num;
    private String state;
    private String backReason;
    private Date orderDt;
    private String supplyName;
    @Basic
    @Column(name = "SUPPLY_NAME")
    public String getSupplyName() {
        return supplyName;
    }

    public void setSupplyName(String supplyName) {
        this.supplyName = supplyName;
    }
    @Basic
    @Column(name = "ORDER_DT")
    public Date getOrderDt() {
        return orderDt;
    }

    public void setOrderDt(Date orderDt) {
        this.orderDt = orderDt;
    }

    @Id
    @Column(name = "ORDER_ID")
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

//    @Basic
//    @Column(name = "BUY_COMPANY_ID")
//    public String getBuyCompanyId() {
//        return buyCompanyId;
//    }
//
//    public void setBuyCompanyId(String buyCompanyId) {
//        this.buyCompanyId = buyCompanyId;
//    }

//    @Basic
//    @Column(name = "SUPPLY_COMPANY_ID")
//    public String getSupplyCompanyId() {
//        return supplyCompanyId;
//    }
//
//    public void setSupplyCompanyId(String supplyCompanyId) {
//        this.supplyCompanyId = supplyCompanyId;
//    }

    @Basic
    @Column(name = "BUY_CONTACT")
    public String getBuyContact() {
        return buyContact;
    }

    public void setBuyContact(String buyContact) {
        this.buyContact = buyContact;
    }

    @Basic
    @Column(name = "BUY_MOBILE")
    public String getBuyMobile() {
        return buyMobile;
    }

    public void setBuyMobile(String buyMobile) {
        this.buyMobile = buyMobile;
    }

    @Basic
    @Column(name = "SUPPLY_CONTACT")
    public String getSupplyContact() {
        return supplyContact;
    }

    public void setSupplyContact(String supplyContact) {
        this.supplyContact = supplyContact;
    }

    @Basic
    @Column(name = "SUPPLY_MOBILE")
    public String getSupplyMobile() {
        return supplyMobile;
    }

    public void setSupplyMobile(String supplyMobile) {
        this.supplyMobile = supplyMobile;
    }

    @Basic
    @Column(name = "OPERATOR")
    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    @Basic
    @Column(name = "FREIGHTER")
    public String getFreighter() {
        return freighter;
    }

    public void setFreighter(String freighter) {
        this.freighter = freighter;
    }

    @Basic
    @Column(name = "PRICE")
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Basic
    @Column(name = "NUM")
    public Double getNum() {
        return num;
    }

    public void setNum(Double num) {
        this.num = num;
    }

    @Basic
    @Column(name = "STATE")
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
    @Basic
    @Column(name = "BACK_REASON")
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

        SDeviceOrder spOrder = (SDeviceOrder) o;

        if (orderId != null ? !orderId.equals(spOrder.orderId) : spOrder.orderId != null) return false;
        //if (buyCompanyId != null ? !buyCompanyId.equals(spOrder.buyCompanyId) : spOrder.buyCompanyId != null)
        //    return false;
        //if (supplyCompanyId != null ? !supplyCompanyId.equals(spOrder.supplyCompanyId) : spOrder.supplyCompanyId != null)
        //    return false;
        if (supplyName != null ? !supplyName.equals(spOrder.supplyName) : spOrder.supplyName != null) return false;
        if (buyContact != null ? !buyContact.equals(spOrder.buyContact) : spOrder.buyContact != null) return false;
        if (buyMobile != null ? !buyMobile.equals(spOrder.buyMobile) : spOrder.buyMobile != null) return false;
        if (supplyContact != null ? !supplyContact.equals(spOrder.supplyContact) : spOrder.supplyContact != null)
            return false;
        if (supplyMobile != null ? !supplyMobile.equals(spOrder.supplyMobile) : spOrder.supplyMobile != null)
            return false;
        if (operator != null ? !operator.equals(spOrder.operator) : spOrder.operator != null) return false;
        if (freighter != null ? !freighter.equals(spOrder.freighter) : spOrder.freighter != null) return false;
        if (price != null ? !price.equals(spOrder.price) : spOrder.price != null) return false;
        if (num != null ? !num.equals(spOrder.num) : spOrder.num != null) return false;
        if (state != null ? !state.equals(spOrder.state) : spOrder.state != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = orderId != null ? orderId.hashCode() : 0;
        //result = 31 * result + (buyCompanyId != null ? buyCompanyId.hashCode() : 0);
        //result = 31 * result + (supplyCompanyId != null ? supplyCompanyId.hashCode() : 0);
        result = 31 * result + (supplyName != null ? supplyName.hashCode() : 0);
        result = 31 * result + (buyContact != null ? buyContact.hashCode() : 0);
        result = 31 * result + (buyMobile != null ? buyMobile.hashCode() : 0);
        result = 31 * result + (supplyContact != null ? supplyContact.hashCode() : 0);
        result = 31 * result + (supplyMobile != null ? supplyMobile.hashCode() : 0);
        result = 31 * result + (operator != null ? operator.hashCode() : 0);
        result = 31 * result + (freighter != null ? freighter.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (num != null ? num.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        return result;
    }

    private SCompany buycompany;

    @ManyToOne(targetEntity = SCompany.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "BUY_COMPANY_ID")
    public SCompany getBuycompany() {
        return buycompany;
    }

    public void setBuycompany(SCompany buycompany) {
        this.buycompany = buycompany;
    }

    private SCompany supplycompany;

    @ManyToOne(targetEntity = SCompany.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "SUPPLY_COMPANY_ID")
    public SCompany getSupplycompany() {
        return supplycompany;
    }

    public void setSupplycompany(SCompany supplycompany) {
        this.supplycompany = supplycompany;
    }


    private List<SPOrderObj> sOrderObjList;

    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "ORDER_ID")
    public List<SPOrderObj> getSOrderObjList() {
        return sOrderObjList;
    }

    public void setSOrderObjList(List<SPOrderObj> sOrderObjList) {
        this.sOrderObjList = sOrderObjList;
    }

    private SPPay spPay;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "SP_PAY_ID")
    public SPPay getSpPay() {
        return spPay;
    }

    public void setSpPay(SPPay spPay) {
        this.spPay = spPay;
    }
    @Transient
    public double getSPOrderObjNum(){
        List <SPOrderObj> list = getSOrderObjList();
        double num = 0;
        if (list != null) {
            for (int i=0;i<list.size();i++){
                num += list.get(i).getObjNum();
            }
            return num;
        }else{
            return 0;
        }
    }

    @Transient
    public double getSPOrderObjPrice(){
        List <SPOrderObj> list = getSOrderObjList();
        double num = 0;
        double price = 0;
        double sumPrice = 0;
        if (list != null) {
            for (int i=0;i<list.size();i++){
                price = list.get(i).getObjPrice();
                num = list.get(i).getObjNum();
                sumPrice += price * num;
            }
            return sumPrice;
        }else{
            return 0;
        }
    }

}
