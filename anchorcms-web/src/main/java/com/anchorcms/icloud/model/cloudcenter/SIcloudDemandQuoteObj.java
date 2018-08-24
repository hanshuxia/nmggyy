package com.anchorcms.icloud.model.cloudcenter;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

/**
 * @Author 阁楼麻雀
 * @Email netuser.orz@icloud.com
 * @Date 2017/1/3
 * @Desc 云资源需求对象报价信息表
 */
@Entity
@Table(name = "s_icloud_demand_quote_obj")
public class SIcloudDemandQuoteObj implements Serializable{
    private static final long serialVersionUID = 6819015805016172537L;
    private String demandObjId;
    private String demandId;
    private String demandRequestId;
    private Double offer;
    private String createId;
    private Date createDt;

    @Id
    @Column(name = "DEMAND_OBJ_ID")
    public String getDemandObjId() {
        return demandObjId;
    }

    public void setDemandObjId(String demandObjId) {
        this.demandObjId = demandObjId;
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
    @Column(name = "DEMAND_REQUEST_ID")
    public String getDemandRequestId() {
        return demandRequestId;
    }

    public void setDemandRequestId(String demandRequestId) {
        this.demandRequestId = demandRequestId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SIcloudDemandQuoteObj that = (SIcloudDemandQuoteObj) o;

        if (demandObjId != null ? !demandObjId.equals(that.demandObjId) : that.demandObjId != null) return false;
        if (demandId != null ? !demandId.equals(that.demandId) : that.demandId != null) return false;
        if (demandRequestId != null ? !demandRequestId.equals(that.demandRequestId) : that.demandRequestId != null)
            return false;
        if (offer != null ? !offer.equals(that.offer) : that.offer != null) return false;
        if (createId != null ? !createId.equals(that.createId) : that.createId != null) return false;
        if (createDt != null ? !createDt.equals(that.createDt) : that.createDt != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = demandObjId != null ? demandObjId.hashCode() : 0;
        result = 31 * result + (demandId != null ? demandId.hashCode() : 0);
        result = 31 * result + (demandRequestId != null ? demandRequestId.hashCode() : 0);
        result = 31 * result + (offer != null ? offer.hashCode() : 0);
        result = 31 * result + (createId != null ? createId.hashCode() : 0);
        result = 31 * result + (createDt != null ? createDt.hashCode() : 0);
        return result;
    }
}
