package com.anchorcms.icloud.model.supplychain;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author machao
 * @Date 2017/1/9 15:56
 * @return
 * 维修资源对象报价信息表
 */
@Entity
@Table(name = "s_repair_ability")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE )
public class SRepairAbility implements Serializable{
    public static  final String MODEL_PATH = "repairAbility";
    private static final long serialVersionUID = -9079705929363099677L;
    private String demandObjId;
    private String demandId;
    private Double offer;
    private String createId;
    private Date createDt;

    private String demandRequestId;

    @Id
    @Column(name = "DEMAND_OBJ_ID", nullable = false, length = 32)
    public String getDemandObjId() {
        return demandObjId;
    }

    public void setDemandObjId(String demandObjId) {
        this.demandObjId = demandObjId;
    }

    @Basic
    @Column(name = "DEMAND_ID", nullable = true, length = 32)
    public String getDemandId() {
        return demandId;
    }

    public void setDemandId(String demandId) {
        this.demandId = demandId;
    }

    @Basic
    @Column(name = "DEMAND_REQUEST_ID", nullable = true, length = 32)
    public String getDemandRequestId() {
        return demandRequestId;
    }

    public void setDemandRequestId(String demandRequestId) {
        this.demandRequestId = demandRequestId;
    }

    @Basic
    @Column(name = "OFFER", nullable = true, precision = 0)
    public Double getOffer() {
        return offer;
    }

    public void setOffer(Double offer) {
        this.offer = offer;
    }

    @Basic
    @Column(name = "CREATE_ID", nullable = true, length = 32)
    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId;
    }

    @Basic
    @Column(name = "CREATE_DT", nullable = true)
    public Date getCreateDt() {
        return createDt;
    }

    public void setCreateDt(Date createDt) {
        this.createDt = createDt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SRepairAbility that = (SRepairAbility) o;

        if (demandObjId != null ? !demandObjId.equals(that.demandObjId) : that.demandObjId != null) return false;
        if (demandId != null ? !demandId.equals(that.demandId) : that.demandId != null) return false;
        if (offer != null ? !offer.equals(that.offer) : that.offer != null) return false;
        if (createId != null ? !createId.equals(that.createId) : that.createId != null) return false;
        if (createDt != null ? !createDt.equals(that.createDt) : that.createDt != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = demandObjId != null ? demandObjId.hashCode() : 0;
        result = 31 * result + (demandId != null ? demandId.hashCode() : 0);
        result = 31 * result + (offer != null ? offer.hashCode() : 0);
        result = 31 * result + (createId != null ? createId.hashCode() : 0);
        result = 31 * result + (createDt != null ? createDt.hashCode() : 0);
        return result;
    }
}
