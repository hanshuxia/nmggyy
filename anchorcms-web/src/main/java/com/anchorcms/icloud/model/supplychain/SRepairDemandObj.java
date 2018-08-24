package com.anchorcms.icloud.model.supplychain;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author zhouyq
 * @Date 2016/12/26
 * @Desc 维修需求对象信息表
 */
@Entity
@Table(name = "s_repair_demand_obj")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE )
public class SRepairDemandObj implements Serializable {
    private static final long serialVersionUID = -7689023511687505129L;
    private String repairObjid; // 唯一标识
    private String repairId; // 维修需求信息ID
    private String repairName; // 维修对象名称
    private String machineType; // 维修对象型号
    private Double repairNum; // 维修数量
    private String addrProvince; // 地址（省）
    private String addrCity; // 地址（地级市）
    private String addrCounty; // 地址（市、县级）
    private String addrStreet; // 地址(街道)
    private String repairRequest; // 维修要求
    private Double expectPrice; // 期望价格
    private String remark; // 备注
    private String createrId; // 创建人
    private Date createDt; // 创建时间
    private Date startDt; // 开始日期
    private Date deadlineDt; // 截止日期
    private String deFlag; // 逻辑删除

    @Id
    @Column(name = "REPAIR_OBJID", nullable = false, length = 32)
    public String getRepairObjid() {
        return repairObjid;
    }

    public void setRepairObjid(String repairObjid) {
        this.repairObjid = repairObjid;
    }

    @Basic
    @Column(name = "REPAIR_ID", nullable = true, length = 32)
    public String getRepairId() {
        return repairId;
    }

    public void setRepairId(String repairId) {
        this.repairId = repairId;
    }

    @Basic
    @Column(name = "REPAIR_NAME", nullable = true, length = 32)
    public String getRepairName() {
        return repairName;
    }

    public void setRepairName(String repairName) {
        this.repairName = repairName;
    }

    @Basic
    @Column(name = "MACHINE_TYPE", nullable = true, length = 32)
    public String getMachineType() {
        return machineType;
    }

    public void setMachineType(String machineType) {
        this.machineType = machineType;
    }

    @Basic
    @Column(name = "REPAIR_NUM", nullable = true, precision = 0)
    public Double getRepairNum() {
        return repairNum;
    }

    public void setRepairNum(Double repairNum) {
        this.repairNum = repairNum;
    }

    @Basic
    @Column(name = "ADDR_PROVINCE", nullable = true, length = 6)
    public String getAddrProvince() {
        return addrProvince;
    }

    public void setAddrProvince(String addrProvince) {
        this.addrProvince = addrProvince;
    }

    @Basic
    @Column(name = "ADDR_CITY", nullable = true, length = 6)
    public String getAddrCity() {
        return addrCity;
    }

    public void setAddrCity(String addrCity) {
        this.addrCity = addrCity;
    }

    @Basic
    @Column(name = "ADDR_COUNTY", nullable = true, length = 6)
    public String getAddrCounty() {
        return addrCounty;
    }

    public void setAddrCounty(String addrCounty) {
        this.addrCounty = addrCounty;
    }

    @Basic
    @Column(name = "ADDR_STREET", nullable = true, length = 100)
    public String getAddrStreet() {
        return addrStreet;
    }

    public void setAddrStreet(String addrStreet) {
        this.addrStreet = addrStreet;
    }

    @Basic
    @Column(name = "REPAIR_REQUEST", nullable = true, length = 128)
    public String getRepairRequest() {
        return repairRequest;
    }

    public void setRepairRequest(String repairRequest) {
        this.repairRequest = repairRequest;
    }

    @Basic
    @Column(name = "EXPECT_PRICE", nullable = true, precision = 0)
    public Double getExpectPrice() {
        return expectPrice;
    }

    public void setExpectPrice(Double expectPrice) {
        this.expectPrice = expectPrice;
    }

    @Basic
    @Column(name = "REMARK", nullable = true, length = 256)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Basic
    @Column(name = "CREATER_ID", nullable = true, length = 32)
    public String getCreaterId() {
        return createrId;
    }

    public void setCreaterId(String createrId) {
        this.createrId = createrId;
    }

    @Basic
    @Column(name = "CREATE_DT", nullable = true)
    public Date getCreateDt() {
        return createDt;
    }

    public void setCreateDt(Date createDt) {
        this.createDt = createDt;
    }

    @Basic
    @Column(name = "START_DT", nullable = true)
    public Date getStartDt() {
        return startDt;
    }

    public void setStartDt(Date startDt) {
        this.startDt = startDt;
    }

    @Basic
    @Column(name = "DEADLINE_DT", nullable = true)
    public Date getDeadlineDt() {
        return deadlineDt;
    }

    public void setDeadlineDt(Date deadlineDt) {
        this.deadlineDt = deadlineDt;
    }

    @Basic
    @Column(name = "DE_FLAG", nullable = true, length = 2)
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

        SRepairDemandObj that = (SRepairDemandObj) o;

        if (repairObjid != null ? !repairObjid.equals(that.repairObjid) : that.repairObjid != null) return false;
        if (repairId != null ? !repairId.equals(that.repairId) : that.repairId != null) return false;
        if (repairName != null ? !repairName.equals(that.repairName) : that.repairName != null) return false;
        if (machineType != null ? !machineType.equals(that.machineType) : that.machineType != null) return false;
        if (repairNum != null ? !repairNum.equals(that.repairNum) : that.repairNum != null) return false;
        if (addrProvince != null ? !addrProvince.equals(that.addrProvince) : that.addrProvince != null) return false;
        if (addrCity != null ? !addrCity.equals(that.addrCity) : that.addrCity != null) return false;
        if (addrCounty != null ? !addrCounty.equals(that.addrCounty) : that.addrCounty != null) return false;
        if (addrStreet != null ? !addrStreet.equals(that.addrStreet) : that.addrStreet != null) return false;
        if (repairRequest != null ? !repairRequest.equals(that.repairRequest) : that.repairRequest != null)
            return false;
        if (expectPrice != null ? !expectPrice.equals(that.expectPrice) : that.expectPrice != null) return false;
        if (remark != null ? !remark.equals(that.remark) : that.remark != null) return false;
        if (createrId != null ? !createrId.equals(that.createrId) : that.createrId != null) return false;
        if (createDt != null ? !createDt.equals(that.createDt) : that.createDt != null) return false;
        if (startDt != null ? !startDt.equals(that.startDt) : that.startDt != null) return false;
        if (deadlineDt != null ? !deadlineDt.equals(that.deadlineDt) : that.deadlineDt != null) return false;
        if (deFlag != null ? !deFlag.equals(that.deFlag) : that.deFlag != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = repairObjid != null ? repairObjid.hashCode() : 0;
        result = 31 * result + (repairId != null ? repairId.hashCode() : 0);
        result = 31 * result + (repairName != null ? repairName.hashCode() : 0);
        result = 31 * result + (machineType != null ? machineType.hashCode() : 0);
        result = 31 * result + (repairNum != null ? repairNum.hashCode() : 0);
        result = 31 * result + (addrProvince != null ? addrProvince.hashCode() : 0);
        result = 31 * result + (addrCity != null ? addrCity.hashCode() : 0);
        result = 31 * result + (addrCounty != null ? addrCounty.hashCode() : 0);
        result = 31 * result + (addrStreet != null ? addrStreet.hashCode() : 0);
        result = 31 * result + (repairRequest != null ? repairRequest.hashCode() : 0);
        result = 31 * result + (expectPrice != null ? expectPrice.hashCode() : 0);
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        result = 31 * result + (createrId != null ? createrId.hashCode() : 0);
        result = 31 * result + (createDt != null ? createDt.hashCode() : 0);
        result = 31 * result + (startDt != null ? startDt.hashCode() : 0);
        result = 31 * result + (deadlineDt != null ? deadlineDt.hashCode() : 0);
        result = 31 * result + (deFlag != null ? deFlag.hashCode() : 0);
        return result;
    }
}

