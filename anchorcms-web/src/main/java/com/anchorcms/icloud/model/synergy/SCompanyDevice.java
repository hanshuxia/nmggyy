package com.anchorcms.icloud.model.synergy;

import com.anchorcms.cms.model.main.Content;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

/**
 * @Author 阁楼麻雀
 * @Email netuser.orz@icloud.com
 * @Date 2016/12/29
 * @Desc
 */
@Entity
@Table(name = "s_company_device")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE )
public class SCompanyDevice implements Serializable{
    private static final long serialVersionUID = -6984725817040057426L;
    private int deviceId;
//    private String companyId;
    private String deviceType;
    private String deviceName;
    private String deviceCode;
    private Double requestNum;
    private String deviceDesc;
    private String unit;
    private Double expectPrice;
    private String createrId;
    private Date createDt;
    private Date updateDt;
    private String deFlag;
    private String contact;
    private String mobile;

    @Basic
    @Column(name = "CONTACT")
    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    @Basic
    @Column(name = "MOBILE")
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    @Id
    @Column(name = "DEVICE_ID")
    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

//    @Basic
//    @Column(name = "COMPANY_ID")
//    public String getCompanyId() {
//        return companyId;
//    }
//
//    public void setCompanyId(String companyId) {
//        this.companyId = companyId;
//    }

    @Basic
    @Column(name = "DEVICE_TYPE")
    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    @Basic
    @Column(name = "DEVICE_NAME")
    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    @Basic
    @Column(name = "DEVICE_CODE")
    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
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
    @Column(name = "DEVICE_DESC")
    public String getDeviceDesc() {
        return deviceDesc;
    }

    public void setDeviceDesc(String deviceDesc) {
        this.deviceDesc = deviceDesc;
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

        SCompanyDevice device = (SCompanyDevice) o;

        if (deviceId != device.deviceId) return false;
//        if (companyId != null ? !companyId.equals(device.companyId) : device.companyId != null) return false;
        if (contact != null ? !contact.equals(device.contact) : device.contact != null) return false;
        if (mobile != null ? !mobile.equals(device.mobile) : device.mobile != null) return false;
        if (deviceType != null ? !deviceType.equals(device.deviceType) : device.deviceType != null) return false;
        if (deviceName != null ? !deviceName.equals(device.deviceName) : device.deviceName != null) return false;
        if (deviceCode != null ? !deviceCode.equals(device.deviceCode) : device.deviceCode != null) return false;
        if (requestNum != null ? !requestNum.equals(device.requestNum) : device.requestNum != null) return false;
        if (deviceDesc != null ? !deviceDesc.equals(device.deviceDesc) : device.deviceDesc != null) return false;
        if (unit != null ? !unit.equals(device.unit) : device.unit != null) return false;
        if (expectPrice != null ? !expectPrice.equals(device.expectPrice) : device.expectPrice != null) return false;
        if (createrId != null ? !createrId.equals(device.createrId) : device.createrId != null) return false;
        if (createDt != null ? !createDt.equals(device.createDt) : device.createDt != null) return false;
        if (updateDt != null ? !updateDt.equals(device.updateDt) : device.updateDt != null) return false;
        if (deFlag != null ? !deFlag.equals(device.deFlag) : device.deFlag != null) return false;
        return content != null ? content.equals(device.content) : device.content == null;

    }

    @Override
    public int hashCode() {
        int result = deviceId;
//        result = 31 * result + (companyId != null ? companyId.hashCode() : 0);
        result = 31 * result + (contact != null ? contact.hashCode() : 0);
        result = 31 * result + (mobile != null ? mobile.hashCode() : 0);
        result = 31 * result + (deviceType != null ? deviceType.hashCode() : 0);
        result = 31 * result + (deviceName != null ? deviceName.hashCode() : 0);
        result = 31 * result + (deviceCode != null ? deviceCode.hashCode() : 0);
        result = 31 * result + (requestNum != null ? requestNum.hashCode() : 0);
        result = 31 * result + (deviceDesc != null ? deviceDesc.hashCode() : 0);
        result = 31 * result + (unit != null ? unit.hashCode() : 0);
        result = 31 * result + (expectPrice != null ? expectPrice.hashCode() : 0);
        result = 31 * result + (createrId != null ? createrId.hashCode() : 0);
        result = 31 * result + (createDt != null ? createDt.hashCode() : 0);
        result = 31 * result + (updateDt != null ? updateDt.hashCode() : 0);
        result = 31 * result + (deFlag != null ? deFlag.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        return result;
    }

    private Content content;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CONTENT_ID")
    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    private SCompany company;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id")
    public SCompany getCompany() {
        return company;
    }

    public void setCompany(SCompany company) {
        this.company = company;
    }
}
