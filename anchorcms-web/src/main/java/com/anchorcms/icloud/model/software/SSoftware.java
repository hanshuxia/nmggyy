package com.anchorcms.icloud.model.software;

import com.anchorcms.cms.model.main.Content;
import com.anchorcms.core.model.CmsUser;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

/**
 * Created by ly on 2017/1/4.
 */
@Entity
@Table(name = "s_software")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE )
public class SSoftware implements Serializable{
    private static final long serialVersionUID = 3001134401487697594L;
    private int softwareId;
    private String softwareName;
    private String companyName;
    private String softwareType;
    private String payType;
    private double price;
    private String isOnline;
    private String softwareSize;
    private String softwareHref;
    private String addrProvince;
    private String addrCity;
    private String addrCounty;
    private String addrStreet;
    private String contact;
    private String mobile;
    private String status;
//    private String createrId;
    private Date createDt;
    private Date updateDt;
    private String deFlag;
    private int browse;
    private int download;
    private String agencyFlag; // 是否平台代理


    @Id
    @Column(name = "SOFTWARE_ID")
    public int getSoftwareId() {
        return softwareId;
    }

    public void setSoftwareId(int softwareId) {
        this.softwareId = softwareId;
    }

    @Basic
    @Column(name = "SOFTWARE_NAME")
    public String getSoftwareName() {
        return softwareName;
    }

    public void setSoftwareName(String softwareName) {
        this.softwareName = softwareName;
    }

    @Basic
    @Column(name = "COMPANY_NAME")
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Basic
    @Column(name = "SOFTWARE_TYPE")
    public String getSoftwareType() {
        return softwareType;
    }

    public void setSoftwareType(String softwareType) {
        this.softwareType = softwareType;
    }

    @Basic
    @Column(name = "PAY_TYPE")
    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    @Basic
    @Column(name = "PRICE")
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    @Basic
    @Column(name = "IS_ONLINE")
    public String getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(String isOnline) {
        this.isOnline = isOnline;
    }

    @Basic
    @Column(name = "SOFTWARE_SIZE")
    public String getSoftwareSize() {
        return softwareSize;
    }

    public void setSoftwareSize(String softwareSize) {
        this.softwareSize = softwareSize;
    }

    @Basic
    @Column(name = "SOFTWARE_HREF")
    public String getSoftwareHref() {
        return softwareHref;
    }

    public void setSoftwareHref(String softwareHref) {
        this.softwareHref = softwareHref;
    }

    @Basic
    @Column(name = "ADDR_PROVINCE")
    public String getAddrProvince() {
        return addrProvince;
    }

    public void setAddrProvince(String addrProvince) {
        this.addrProvince = addrProvince;
    }

    @Basic
    @Column(name = "ADDR_CITY")
    public String getAddrCity() {
        return addrCity;
    }

    public void setAddrCity(String addrCity) {
        this.addrCity = addrCity;
    }

    @Basic
    @Column(name = "ADDR_COUNTY")
    public String getAddrCounty() {
        return addrCounty;
    }

    public void setAddrCounty(String addrCounty) {
        this.addrCounty = addrCounty;
    }

    @Basic
    @Column(name = "ADDR_STREET")
    public String getAddrStreet() {
        return addrStreet;
    }

    public void setAddrStreet(String addrStreet) {
        this.addrStreet = addrStreet;
    }

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

    @Basic
    @Column(name = "STATUS")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    @Basic
    @Column(name = "BROWSE")
    public int getBrowse() { return browse;}

    public void setBrowse(int browse) { this.browse = browse;}

    @Basic
    @Column(name = "DOWNLOAD")
    public int getDownload() {return download;}

    public void setDownload(int download) {this.download = download;}

    @Basic
    @Column(name = "AGENCY_FLAG")
    public String getAgencyFlag() {
        return agencyFlag;
    }

    public void setAgencyFlag(String agencyFlag) {
        this.agencyFlag = agencyFlag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SSoftware software = (SSoftware) o;

        if (softwareId != software.softwareId) return false;
        if (softwareName != null ? !softwareName.equals(software.softwareName) : software.softwareName != null)
            return false;
        if (companyName != null ? !companyName.equals(software.companyName) : software.companyName != null)
            return false;
        if (softwareType != null ? !softwareType.equals(software.softwareType) : software.softwareType != null)
            return false;
        if (payType != null ? !payType.equals(software.payType) : software.payType != null) return false;
        if (isOnline != null ? !isOnline.equals(software.isOnline) : software.isOnline != null) return false;
        if (softwareSize != null ? !softwareSize.equals(software.softwareSize) : software.softwareSize != null)
            return false;
        if (softwareHref != null ? !softwareHref.equals(software.softwareHref) : software.softwareHref != null)
            return false;
        if (addrProvince != null ? !addrProvince.equals(software.addrProvince) : software.addrProvince != null)
            return false;
        if (addrCity != null ? !addrCity.equals(software.addrCity) : software.addrCity != null) return false;
        if (addrCounty != null ? !addrCounty.equals(software.addrCounty) : software.addrCounty != null) return false;
        if (addrStreet != null ? !addrStreet.equals(software.addrStreet) : software.addrStreet != null) return false;
        if (contact != null ? !contact.equals(software.contact) : software.contact != null) return false;
        if (mobile != null ? !mobile.equals(software.mobile) : software.mobile != null) return false;
        if (status != null ? !status.equals(software.status) : software.status != null) return false;
        if (createDt != null ? !createDt.equals(software.createDt) : software.createDt != null) return false;
        if (updateDt != null ? !updateDt.equals(software.updateDt) : software.updateDt != null) return false;
        if (deFlag != null ? !deFlag.equals(software.deFlag) : software.deFlag != null) return false;
        if (agencyFlag != null ? !agencyFlag.equals(software.agencyFlag) : software.agencyFlag != null) return false;
        return content != null ? content.equals(software.content) : software.content == null;

    }

    @Override
    public int hashCode() {
        int result = softwareId;
        result = 31 * result + (softwareName != null ? softwareName.hashCode() : 0);
        result = 31 * result + (companyName != null ? companyName.hashCode() : 0);
        result = 31 * result + (softwareType != null ? softwareType.hashCode() : 0);
        result = 31 * result + (payType != null ? payType.hashCode() : 0);
        result = 31 * result + (isOnline != null ? isOnline.hashCode() : 0);
        result = 31 * result + (softwareSize != null ? softwareSize.hashCode() : 0);
        result = 31 * result + (softwareHref != null ? softwareHref.hashCode() : 0);
        result = 31 * result + (addrProvince != null ? addrProvince.hashCode() : 0);
        result = 31 * result + (addrCity != null ? addrCity.hashCode() : 0);
        result = 31 * result + (addrCounty != null ? addrCounty.hashCode() : 0);
        result = 31 * result + (addrStreet != null ? addrStreet.hashCode() : 0);
        result = 31 * result + (contact != null ? contact.hashCode() : 0);
        result = 31 * result + (mobile != null ? mobile.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (createDt != null ? createDt.hashCode() : 0);
        result = 31 * result + (updateDt != null ? updateDt.hashCode() : 0);
        result = 31 * result + (deFlag != null ? deFlag.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (agencyFlag != null ? agencyFlag.hashCode() : 0);
        return result;
    }

    private Content content;
    private CmsUser user;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CONTENT_ID")
    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CREATER_ID")
    public CmsUser getUser() {
        return user;
    }

    public void setUser(CmsUser user) {
        this.user = user;
    }


}
