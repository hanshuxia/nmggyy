package com.anchorcms.icloud.model.cloudcenter;

import com.anchorcms.cms.model.main.Content;
import com.anchorcms.icloud.model.synergy.SCompany;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

/**
 * @Author 阁楼麻雀
 * @Email netuser.orz@icloud.com
 * @Date 2017/1/3
 * @Desc 云计算中心资源管理表
 */
@Entity
@Table(name = "s_icloud_manage")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE )
public class SIcloudManage implements Serializable{
    private static final long serialVersionUID = -2563479956568774615L;
    private Integer managerId;
//    private String centerId;
    private String center_name;
    private String release_people;
    private String resourceName;
    private String resourceType;
    private String specVersion;
    private String parameter;
    private Double price;//资源价值
    private String unit;//租赁单位
    private String rentPrice;//出租单价
    private String contact;//联系人
    private String mobile;//手机
    private String email;//邮箱
    //private Double priceUnit;
    private String addrProvince;
    private String addrCity;
    private String addrCounty;
    private String addrStreet;
    private String createrId;
    private Date createDt;
    private Date releaseDt;
    private String state;
    private String deFlag;
    private String capacityUnit;//容量
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MANAGER_ID")
    public Integer getManagerId() {
        return managerId;
    }
    public void setManagerId(Integer managerId) {
        this.managerId = managerId;
    }
    @Basic
    @Column(name = "CENTER_NAME")
    public String getCenter_name() {
        return center_name;
    }

    public void setCenter_name(String center_name) {
        this.center_name = center_name;
    }
    @Basic
    @Column(name = "RELEASE_PEOPLE")
    public String getRelease_people() {
        return release_people;
    }

    public void setRelease_people(String release_people) {
        this.release_people = release_people;
    }

//    @Basic
//    @Column(name = "CENTER_ID")
//    public String getCenterId() {
//        return centerId;
//    }
//
//    public void setCenterId(String centerId) {
//        this.centerId = centerId;
//    }

    @Basic
    @Column(name = "RESOURCE_NAME")
    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    @Basic
    @Column(name = "RESOURCE_TYPE")
    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    @Basic
    @Column(name = "SPEC_VERSION")
    public String getSpecVersion() {
        return specVersion;
    }

    public void setSpecVersion(String specVersion) {
        this.specVersion = specVersion;
    }

    @Basic
    @Column(name = "PARAMETER")
    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    @Basic
    @Column(name = "PRICE")
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
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
    @Column(name = "RENT_PRICE")
    public String getRentPrice() {
        return rentPrice;
    }

    public void setRentPrice(String rentPrice) {
        this.rentPrice = rentPrice;
    }

//    @Basic
//    @Column(name = "PRICE_UNIT")
//    public Double getPriceUnit() {
//        return priceUnit;
//    }
//
//    public void setPriceUnit(Double priceUnit) {
//        this.priceUnit = priceUnit;
//    }

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
    @Column(name = "EMAIL")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
    @Column(name = "RELEASE_DT")
    public Date getReleaseDt () {
        return releaseDt;
    }

    public void setReleaseDt(Date releaseDt) {
        this.releaseDt = releaseDt;
    }

    @Basic
    @Column(name = "STATE")
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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
    @Column(name = "CAPACITY_UNIT")
    public String getCapacityUnit() {
        return capacityUnit;
    }

    public void setCapacityUnit(String capacityUnit) {
        this.capacityUnit = capacityUnit;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SIcloudManage that = (SIcloudManage) o;

        if (managerId != null ? !managerId.equals(that.managerId) : that.managerId != null) return false;
//        if (centerId != null ? !centerId.equals(that.centerId) : that.centerId != null) return false;
        if (center_name != null ? !center_name.equals(that.center_name) : that.center_name != null) return false;
        if (release_people != null ? !release_people.equals(that.release_people) : that.release_people != null)
            return false;
        if (resourceName != null ? !resourceName.equals(that.resourceName) : that.resourceName != null) return false;
        if (resourceType != null ? !resourceType.equals(that.resourceType) : that.resourceType != null) return false;
        if (specVersion != null ? !specVersion.equals(that.specVersion) : that.specVersion != null) return false;
        if (parameter != null ? !parameter.equals(that.parameter) : that.parameter != null) return false;
        if (price != null ? !price.equals(that.price) : that.price != null) return false;
        if (unit != null ? !unit.equals(that.unit) : that.unit != null) return false;
        if (rentPrice != null ? !rentPrice.equals(that.rentPrice) : that.rentPrice != null) return false;
        //if (priceUnit != null ? !priceUnit.equals(that.priceUnit) : that.priceUnit != null) return false;
        if (contact != null ? !contact.equals(that.contact) : that.contact != null) return false;
        if (mobile != null ? !mobile.equals(that.mobile) : that.mobile != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (addrProvince != null ? !addrProvince.equals(that.addrProvince) : that.addrProvince != null) return false;
        if (addrCity != null ? !addrCity.equals(that.addrCity) : that.addrCity != null) return false;
        if (addrCounty != null ? !addrCounty.equals(that.addrCounty) : that.addrCounty != null) return false;
        if (addrStreet != null ? !addrStreet.equals(that.addrStreet) : that.addrStreet != null) return false;
        if (createrId != null ? !createrId.equals(that.createrId) : that.createrId != null) return false;
        if (createDt != null ? !createDt.equals(that.createDt) : that.createDt != null) return false;
        if (releaseDt != null ? !releaseDt.equals(that.releaseDt) : that.releaseDt != null) return false;
        if (state != null ? !state.equals(that.state) : that.state != null) return false;
        if (deFlag != null ? !deFlag.equals(that.deFlag) : that.deFlag != null) return false;
        return content != null ? content.equals(that.content) : that.content == null;

    }

    @Override
    public int hashCode() {
        int result = managerId != null ? managerId.hashCode() : 0;
//        result = 31 * result + (centerId != null ? centerId.hashCode() : 0);
        result = 31 * result + (center_name != null ? center_name.hashCode() : 0);
        result = 31 * result + (release_people != null ? release_people.hashCode() : 0);
        result = 31 * result + (resourceName != null ? resourceName.hashCode() : 0);
        result = 31 * result + (resourceType != null ? resourceType.hashCode() : 0);
        result = 31 * result + (specVersion != null ? specVersion.hashCode() : 0);
        result = 31 * result + (parameter != null ? parameter.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (unit != null ? unit.hashCode() : 0);
        result = 31 * result + (rentPrice != null ? rentPrice.hashCode() : 0);
        //result = 31 * result + (priceUnit != null ? priceUnit.hashCode() : 0);
        result = 31 * result + (contact != null ? contact.hashCode() : 0);
        result = 31 * result + (mobile != null ? mobile.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (addrProvince != null ? addrProvince.hashCode() : 0);
        result = 31 * result + (addrCity != null ? addrCity.hashCode() : 0);
        result = 31 * result + (addrCounty != null ? addrCounty.hashCode() : 0);
        result = 31 * result + (addrStreet != null ? addrStreet.hashCode() : 0);
        result = 31 * result + (createrId != null ? createrId.hashCode() : 0);
        result = 31 * result + (createDt != null ? createDt.hashCode() : 0);
        result = 31 * result + (releaseDt != null ? releaseDt.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (deFlag != null ? deFlag.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        return result;
    }

    private Content content;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "content_id")
    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    private SCompany company;
    @ManyToOne(targetEntity = SCompany.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "COMPANY_ID")
    public SCompany getCompany() { return company; }

    public void setCompany(SCompany company) { this.company = company; }

    private SIcloudCenter sIcloudCenter;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CENTER_ID")
    public SIcloudCenter getsIcloudCenter() {
        return sIcloudCenter;
    }

    public void setsIcloudCenter(SIcloudCenter sIcloudCenter) {
        this.sIcloudCenter = sIcloudCenter;
    }
}
