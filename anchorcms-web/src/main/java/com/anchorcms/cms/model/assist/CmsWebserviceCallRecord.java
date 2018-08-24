package com.anchorcms.cms.model.assist;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by smt on 2016/11/17.
 */
@Entity
@Table(name = "c_webservice_call_record")
public class CmsWebserviceCallRecord implements Serializable{
    private static final long serialVersionUID = -6550185442544343941L;
    private Integer recordId;
    private String serviceCode;
    private Date recordTime;

    @Id
    @Column(name = "record_id", nullable = false)
    public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    @Basic
    @Column(name = "service_code", nullable = false, length = 50)
    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    @Basic
    @Column(name = "record_time", nullable = false)
    public Date getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(Date recordTime) {
        this.recordTime = recordTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CmsWebserviceCallRecord that = (CmsWebserviceCallRecord) o;

        if (recordId != null ? !recordId.equals(that.recordId) : that.recordId != null) return false;
        if (serviceCode != null ? !serviceCode.equals(that.serviceCode) : that.serviceCode != null) return false;
        if (recordTime != null ? !recordTime.equals(that.recordTime) : that.recordTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = recordId != null ? recordId.hashCode() : 0;
        result = 31 * result + (serviceCode != null ? serviceCode.hashCode() : 0);
        result = 31 * result + (recordTime != null ? recordTime.hashCode() : 0);
        return result;
    }

    private CmsWebserviceAuth auth;

    @ManyToOne
    @JoinColumn(name = "auth_id",insertable = false,updatable = false)
    public CmsWebserviceAuth getAuth() {
        return auth;
    }

    public void setAuth(CmsWebserviceAuth auth) {
        this.auth = auth;
    }
}
