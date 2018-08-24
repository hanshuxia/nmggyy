package com.anchorcms.icloud.model.cloudcenter;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

/**
 * @Author 阁楼麻雀
 * @Email netuser.orz@icloud.com
 * @Date 2017/1/3
 * @Desc 云需求对象信息表
 */
@Entity
@Table(name = "s_icloud_demand_obj")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE )
public class SIcloudDemandObj implements Serializable{
    private static final long serialVersionUID = 8864590309090596623L;
    private Integer icloudObjid;
    private String demandId;
    private String objectName;
    private String classifyCode;
    private Double demandCount;
    private String remark;
    private String unit;
    private Double expectPrice;
    private String createrId;
    private Date createDt;
    private Date updateDt;
    private String deFlag;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ICLOUD_OBJID")
    public Integer getIcloudObjid() {
        return icloudObjid;
    }

    public void setIcloudObjid(Integer icloudObjid) {
        this.icloudObjid = icloudObjid;
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
    @Column(name = "OBJECT_NAME")
    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    @Basic
    @Column(name = "CLASSIFY_CODE")
    public String getClassifyCode() {
        return classifyCode;
    }

    public void setClassifyCode(String classifyCode) {
        this.classifyCode = classifyCode;
    }

    @Basic
    @Column(name = "DEMAND_COUNT")
    public Double getDemandCount() {
        return demandCount;
    }

    public void setDemandCount(Double demandCount) {
        this.demandCount = demandCount;
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

        SIcloudDemandObj that = (SIcloudDemandObj) o;

        if (icloudObjid != null ? !icloudObjid.equals(that.icloudObjid) : that.icloudObjid != null) return false;
        if (demandId != null ? !demandId.equals(that.demandId) : that.demandId != null) return false;
        if (objectName != null ? !objectName.equals(that.objectName) : that.objectName != null) return false;
        if (classifyCode != null ? !classifyCode.equals(that.classifyCode) : that.classifyCode != null) return false;
        if (demandCount != null ? !demandCount.equals(that.demandCount) : that.demandCount != null) return false;
        if (remark != null ? !remark.equals(that.remark) : that.remark != null) return false;
        if (unit != null ? !unit.equals(that.unit) : that.unit != null) return false;
        if (expectPrice != null ? !expectPrice.equals(that.expectPrice) : that.expectPrice != null) return false;
        if (createrId != null ? !createrId.equals(that.createrId) : that.createrId != null) return false;
        if (createDt != null ? !createDt.equals(that.createDt) : that.createDt != null) return false;
        if (updateDt != null ? !updateDt.equals(that.updateDt) : that.updateDt != null) return false;
        if (deFlag != null ? !deFlag.equals(that.deFlag) : that.deFlag != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = icloudObjid != null ? icloudObjid.hashCode() : 0;
        result = 31 * result + (demandId != null ? demandId.hashCode() : 0);
        result = 31 * result + (objectName != null ? objectName.hashCode() : 0);
        result = 31 * result + (classifyCode != null ? classifyCode.hashCode() : 0);
        result = 31 * result + (demandCount != null ? demandCount.hashCode() : 0);
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        result = 31 * result + (unit != null ? unit.hashCode() : 0);
        result = 31 * result + (expectPrice != null ? expectPrice.hashCode() : 0);
        result = 31 * result + (createrId != null ? createrId.hashCode() : 0);
        result = 31 * result + (createDt != null ? createDt.hashCode() : 0);
        result = 31 * result + (updateDt != null ? updateDt.hashCode() : 0);
        result = 31 * result + (deFlag != null ? deFlag.hashCode() : 0);
        return result;
    }
}
