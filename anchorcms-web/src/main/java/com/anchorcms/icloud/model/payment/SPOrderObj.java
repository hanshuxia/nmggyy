package com.anchorcms.icloud.model.payment;

import javax.persistence.*;

/**
 * Created by DELL on 2017/5/1.
 */
@Entity
@Table(name = "s_p_order_obj", catalog = "")
public class SPOrderObj {
    private String orderObjId;
    private String orderId;
    private String objName;
    private Double objNum;
    private Double objPrice;

    @Id
    @Column(name = "ORDER_OBJ_ID")
    public String getOrderObjId() {
        return orderObjId;
    }

    public void setOrderObjId(String orderObjId) {
        this.orderObjId = orderObjId;
    }

    @Basic
    @Column(name = "ORDER_ID")
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Basic
    @Column(name = "OBJ_NAME")
    public String getObjName() {
        return objName;
    }

    public void setObjName(String objName) {
        this.objName = objName;
    }

    @Basic
    @Column(name = "OBJ_NUM")
    public Double getObjNum() {
        return objNum;
    }

    public void setObjNum(Double objNum) {
        this.objNum = objNum;
    }

    @Basic
    @Column(name = "OBJ_PRICE")
    public Double getObjPrice() {
        return objPrice;
    }

    public void setObjPrice(Double objPrice) {
        this.objPrice = objPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SPOrderObj that = (SPOrderObj) o;

        if (orderObjId != null ? !orderObjId.equals(that.orderObjId) : that.orderObjId != null) return false;
        if (orderId != null ? !orderId.equals(that.orderId) : that.orderId != null) return false;
        if (objName != null ? !objName.equals(that.objName) : that.objName != null) return false;
        if (objNum != null ? !objNum.equals(that.objNum) : that.objNum != null) return false;
        if (objPrice != null ? !objPrice.equals(that.objPrice) : that.objPrice != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = orderObjId != null ? orderObjId.hashCode() : 0;
        result = 31 * result + (orderId != null ? orderId.hashCode() : 0);
        result = 31 * result + (objName != null ? objName.hashCode() : 0);
        result = 31 * result + (objNum != null ? objNum.hashCode() : 0);
        result = 31 * result + (objPrice != null ? objPrice.hashCode() : 0);
        return result;
    }
}
