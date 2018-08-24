package com.anchorcms.icloud.model.payment;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by DELL on 2017/5/4.
 */
@Entity
@Table(name = "s_p_admin_settle", catalog = "")
public class SPAdminSettle {
    private int id;
    private String ordeId;
    private Double orderPrice;
    private Double fee;
    private Double income;
    private String operater;
    private Date stDt;
    private String state;
    private String flag;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "ordeId")
    public String getOrdeId() {
        return ordeId;
    }

    public void setOrdeId(String ordeId) {
        this.ordeId = ordeId;
    }

    @Basic
    @Column(name = "orderPrice")
    public Double getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(Double orderPrice) {
        this.orderPrice = orderPrice;
    }

    @Basic
    @Column(name = "fee")
    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }
    @Basic
    @Column(name = "income")
    public Double getIncome() {
        return income;
    }

    public void setIncome(Double income) {
        this.income = income;
    }

    @Basic
    @Column(name = "operater")
    public String getOperater() {
        return operater;
    }

    public void setOperater(String operater) {
        this.operater = operater;
    }

    @Basic
    @Column(name = "st_dt")
    public Date getStDt() {
        return stDt;
    }

    public void setStDt(Date stDt) {
        this.stDt = stDt;
    }

    @Basic
    @Column(name = "flag")
    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
    @Basic
    @Column(name = "state")
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SPAdminSettle that = (SPAdminSettle) o;

        if (id != that.id) return false;
        if (ordeId != null ? !ordeId.equals(that.ordeId) : that.ordeId != null) return false;
        if (orderPrice != null ? !orderPrice.equals(that.orderPrice) : that.orderPrice != null) return false;
        if (fee != null ? !fee.equals(that.fee) : that.fee != null) return false;
        if (operater != null ? !operater.equals(that.operater) : that.operater != null) return false;
        if (stDt != null ? !stDt.equals(that.stDt) : that.stDt != null) return false;
        if (state != null ? !state.equals(that.state) : that.state != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (ordeId != null ? ordeId.hashCode() : 0);
        result = 31 * result + (orderPrice != null ? orderPrice.hashCode() : 0);
        result = 31 * result + (fee != null ? fee.hashCode() : 0);
        result = 31 * result + (operater != null ? operater.hashCode() : 0);
        result = 31 * result + (stDt != null ? stDt.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        return result;
    }
    private SPSettle spSettle;
    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "SETTLE_ID")
    public SPSettle getSpSettle() {
        return spSettle;
    }
    public void setSpSettle(SPSettle spSettle) {
        this.spSettle = spSettle;
    }
}
