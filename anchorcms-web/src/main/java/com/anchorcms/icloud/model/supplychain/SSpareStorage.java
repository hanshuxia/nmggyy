package com.anchorcms.icloud.model.supplychain;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by DELL on 2017/6/9.
 * 备品备件库存信息表
 */
@Entity
@Table(name = "s_spare_storage")
public class SSpareStorage {
    private int storageId;//id
    private String spareId;//备品备件id
    private String type;//1.入库  2.出库
    private Double count;//数量
    private String remark;//备注
    private String createId;//操作id
    private Date createDt;//创建时间
    private Date updateDt;//更新时间
    private String deFlag;//逻辑删除

    @Id
    @Column(name = "STORAGE_ID")
    public int getStorageId() {
        return storageId;
    }

    public void setStorageId(int storageId) {
        this.storageId = storageId;
    }

    @Basic
    @Column(name = "SPARE_ID")
    public String getSpareId() {
        return spareId;
    }

    public void setSpareId(String spareId) {
        this.spareId = spareId;
    }

    @Basic
    @Column(name = "TYPE")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Basic
    @Column(name = "COUNT")
    public Double getCount() {
        return count;
    }

    public void setCount(Double count) {
        this.count = count;
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

        SSpareStorage that = (SSpareStorage) o;

        if (storageId != that.storageId) return false;
        if (spareId != null ? !spareId.equals(that.spareId) : that.spareId != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (count != null ? !count.equals(that.count) : that.count != null) return false;
        if (remark != null ? !remark.equals(that.remark) : that.remark != null) return false;
        if (createId != null ? !createId.equals(that.createId) : that.createId != null) return false;
        if (createDt != null ? !createDt.equals(that.createDt) : that.createDt != null) return false;
        if (updateDt != null ? !updateDt.equals(that.updateDt) : that.updateDt != null) return false;
        if (deFlag != null ? !deFlag.equals(that.deFlag) : that.deFlag != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = storageId;
        result = 31 * result + (spareId != null ? spareId.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (count != null ? count.hashCode() : 0);
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        result = 31 * result + (createId != null ? createId.hashCode() : 0);
        result = 31 * result + (createDt != null ? createDt.hashCode() : 0);
        result = 31 * result + (updateDt != null ? updateDt.hashCode() : 0);
        result = 31 * result + (deFlag != null ? deFlag.hashCode() : 0);
        return result;
    }
}
