package com.anchorcms.icloud.model.synergy;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.sql.Date;

/**
 * Copyright @ 2016 Shandong jxdinfo software co,ltd.
 * All right reserved.
 * Created by Gao Jiangning
 *
 * @Date 2016/12/29 0029
 * @Desc 报价对象表
 */
@Entity
@Table(name = "s_demand_quote_obj")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE )
public class SDemandQuoteObj {
    private int demandQuoteObjid;
    private int demandObjid;
    private int demandQuoteId;
    private Double offer;
    private Double distributionAmount;
    private String createId;
    private Date createDt;
    private Date updateDt;
    private String deFlag;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DEMAND_QUOTE_OBJID")
    public int getDemandQuoteObjid() {
        return demandQuoteObjid;
    }

    public void setDemandQuoteObjid(int demandQuoteObjid) {
        this.demandQuoteObjid = demandQuoteObjid;
    }

    @Basic
    @Column(name = "DEMAND_OBJID")
    public int getDemandObjid() {
        return demandObjid;
    }

    public void setDemandObjid(int demandObjid) {
        this.demandObjid = demandObjid;
    }

    @Basic
    @Column(name = "DEMAND_QUOTE_ID")
    public int getDemandQuoteId() {
        return demandQuoteId;
    }

    public void setDemandQuoteId(int demandQuoteId) {
        this.demandQuoteId = demandQuoteId;
    }

    @Basic
    @Column(name = "OFFER")
    public Double getOffer() {
        return offer;
    }

    public void setOffer(Double offer) {
        this.offer = offer;
    }

    @Basic
    @Column(name = "DISTRIBUTION_AMOUNT")
    public Double getDistributionAmount() {
        return distributionAmount;
    }

    public void setDistributionAmount(Double distributionAmount) {
        this.distributionAmount = distributionAmount;
    }

    @Basic
    @Column(name = "CREATE_ID")
    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId;
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
    @Column(name = "UPDATE_DT")
    public Date getUpdateDt() {
        return updateDt;
    }

    public void setUpdateDt(Date updateDt) {
        this.updateDt = updateDt;
    }

    @Basic
    @Column(name = "DE_FLAG")
    public String getDeFlag() {
        return deFlag;
    }

    public void setDeFlag(String deFlag) {
        this.deFlag = deFlag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SDemandQuoteObj that = (SDemandQuoteObj) o;

        if (demandQuoteObjid != that.demandQuoteObjid) return false;
        if (demandObjid != that.demandObjid) return false;
        if (demandQuoteId != that.demandQuoteId) return false;
        if (offer != null ? !offer.equals(that.offer) : that.offer != null) return false;
        if (createId != null ? !createId.equals(that.createId) : that.createId != null) return false;
        if (createDt != null ? !createDt.equals(that.createDt) : that.createDt != null) return false;
        if (updateDt != null ? !updateDt.equals(that.updateDt) : that.updateDt != null) return false;
        if (deFlag != null ? !deFlag.equals(that.deFlag) : that.deFlag != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = demandQuoteObjid;
        result = 31 * result + demandObjid;
        result = 31 * result + demandQuoteId;
        result = 31 * result + (offer != null ? offer.hashCode() : 0);
        result = 31 * result + (createId != null ? createId.hashCode() : 0);
        result = 31 * result + (createDt != null ? createDt.hashCode() : 0);
        result = 31 * result + (updateDt != null ? updateDt.hashCode() : 0);
        result = 31 * result + (deFlag != null ? deFlag.hashCode() : 0);
        return result;
    }
}
