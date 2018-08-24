package com.anchorcms.icloud.model.supplychain;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

/**
 * Created by machao on 2016/12/27.
 * 备品备件求购对象信息表
 */
@Entity
@Table(name = "s_spare_demand_obj")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE )
public class SSpareDemandObj implements Serializable {
    private static final long serialVersionUID = -7419069005031507026L;
    private String spareObjid;//唯一标识
    private String demandId;//求购信息ID
    private String spareType;//备件分类
    private String spareName;//备件名称
    private String spareCode;//备件编码
    private Double requestNum;//求购数量
    private String spareDesc;//备件描述
    private String unit;//计量单位
    private Double expectPrice;//期望单价
    private String createrId;//创建人
    private Date createDt;//创建时间
    private Date updateDt;//更新时间
    private Date deliverDt;//要求交货日期
    private String deFlag;//逻辑删除

    @Id
    @Column(name = "SPARE_OBJID")
    public String getSpareObjid() {
        return spareObjid;
    }

    public void setSpareObjid(String spareObjid) {
        this.spareObjid = spareObjid;
    }

    @Basic
    @Column(name = "DEMAND_ID")
    public String getDemandId() {
        return demandId;
    }

    public void setDemandId(String demandId) {
        this.demandId = demandId;
    }

    @Basic
    @Column(name = "SPARE_TYPE")
    public String getSpareType() {
        return spareType;
    }

    public void setSpareType(String spareType) {
        this.spareType = spareType;
    }

    @Basic
    @Column(name = "SPARE_NAME")
    public String getSpareName() {
        return spareName;
    }

    public void setSpareName(String spareName) {
        this.spareName = spareName;
    }

    @Basic
    @Column(name = "SPARE_CODE")
    public String getSpareCode() {
        return spareCode;
    }

    public void setSpareCode(String spareCode) {
        this.spareCode = spareCode;
    }

    @Basic
    @Column(name = "REQUEST_NUM")
    public Double getRequestNum() {
        return requestNum;
    }

    public void setRequestNum(Double requestNum) {
        this.requestNum = requestNum;
    }

    @Basic
    @Column(name = "SPARE_DESC")
    public String getSpareDesc() {
        return spareDesc;
    }

    public void setSpareDesc(String spareDesc) {
        this.spareDesc = spareDesc;
    }

    @Basic
    @Column(name = "UNIT")
    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Basic
    @Column(name = "EXPECT_PRICE")
    public Double getExpectPrice() {
        return expectPrice;
    }

    public void setExpectPrice(Double expectPrice) {
        this.expectPrice = expectPrice;
    }

    @Basic
    @Column(name = "CREATER_ID")
    public String getCreaterId() {
        return createrId;
    }

    public void setCreaterId(String createrId) {
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
    @Column(name = "UPDATE_DT")
    public Date getUpdateDt() {
        return updateDt;
    }

    public void setUpdateDt(Date updateDt) {
        this.updateDt = updateDt;
    }

    @Basic
    @Column(name = "DELIVER_DT")
    public Date getDeliverDt() {
        return deliverDt;
    }

    public void setDeliverDt(Date deliverDt) {
        this.deliverDt = deliverDt;
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

        SSpareDemandObj that = (SSpareDemandObj) o;

        if (spareObjid != null ? !spareObjid.equals(that.spareObjid) : that.spareObjid != null) return false;
        if (demandId != null ? !demandId.equals(that.demandId) : that.demandId != null) return false;
        if (spareType != null ? !spareType.equals(that.spareType) : that.spareType != null) return false;
        if (spareName != null ? !spareName.equals(that.spareName) : that.spareName != null) return false;
        if (spareCode != null ? !spareCode.equals(that.spareCode) : that.spareCode != null) return false;
        if (requestNum != null ? !requestNum.equals(that.requestNum) : that.requestNum != null) return false;
        if (spareDesc != null ? !spareDesc.equals(that.spareDesc) : that.spareDesc != null) return false;
        if (unit != null ? !unit.equals(that.unit) : that.unit != null) return false;
        if (expectPrice != null ? !expectPrice.equals(that.expectPrice) : that.expectPrice != null) return false;
        if (createrId != null ? !createrId.equals(that.createrId) : that.createrId != null) return false;
        if (createDt != null ? !createDt.equals(that.createDt) : that.createDt != null) return false;
        if (updateDt != null ? !updateDt.equals(that.updateDt) : that.updateDt != null) return false;
        if (deliverDt != null ? !deliverDt.equals(that.deliverDt) : that.deliverDt != null) return false;
        if (deFlag != null ? !deFlag.equals(that.deFlag) : that.deFlag != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = spareObjid != null ? spareObjid.hashCode() : 0;
        result = 31 * result + (demandId != null ? demandId.hashCode() : 0);
        result = 31 * result + (spareType != null ? spareType.hashCode() : 0);
        result = 31 * result + (spareName != null ? spareName.hashCode() : 0);
        result = 31 * result + (spareCode != null ? spareCode.hashCode() : 0);
        result = 31 * result + (requestNum != null ? requestNum.hashCode() : 0);
        result = 31 * result + (spareDesc != null ? spareDesc.hashCode() : 0);
        result = 31 * result + (unit != null ? unit.hashCode() : 0);
        result = 31 * result + (expectPrice != null ? expectPrice.hashCode() : 0);
        result = 31 * result + (createrId != null ? createrId.hashCode() : 0);
        result = 31 * result + (createDt != null ? createDt.hashCode() : 0);
        result = 31 * result + (updateDt != null ? updateDt.hashCode() : 0);
        result = 31 * result + (deliverDt != null ? deliverDt.hashCode() : 0);
        result = 31 * result + (deFlag != null ? deFlag.hashCode() : 0);
        return result;
    }
}
