package com.anchorcms.icloud.model.common;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Author zhouyq
 * @Date 2017/8/12
 * @Desc 会员联系信息实体类
 */
@Entity
@Table(name = "s_member_address")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE )
public class SMemberAddress implements Serializable{
    private static final long serialVersionUID = -2463881652702568118L;
    private Integer addrId; // 地址id
    private Integer userId; // 用户id
    private String userName; // 用户名
    private String province; // 省份
    private String city; // 城市
    private String country; // 区
    private String address; // 详细地址
    private String mobile; // 手机
    private String zip; // 邮编
    private String tel; // 固话
    private String email; // 邮箱
    private String fax; // 传真
    private Integer defAddr; // 是否为默认地址（0否 1是）
    private String shipAddressName; // 地址别名

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ADDR_ID")
    public Integer getAddrId() {
        return addrId;
    }

    public void setAddrId(Integer addrId) {
        this.addrId = addrId;
    }

    @Basic
    @Column(name = "USER_ID")
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "USER_NAME")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Basic
    @Column(name = "PROVINCE")
    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    @Basic
    @Column(name = "CITY")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Basic
    @Column(name = "COUNTRY")
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Basic
    @Column(name = "ADDRESS")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
    @Column(name = "ZIP")
    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    @Basic
    @Column(name = "TEL")
    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    @Basic
    @Column(name = "FAX")
    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
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
    @Column(name = "DEF_ADDR")
    public Integer getDefAddr() {
        return defAddr;
    }

    public void setDefAddr(Integer defAddr) {
        this.defAddr = defAddr;
    }

    @Basic
    @Column(name = "SHIPADDRESSNAME")
    public String getShipAddressName() {
        return shipAddressName;
    }

    public void setShipAddressName(String shipAddressName) {
        this.shipAddressName = shipAddressName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SMemberAddress that = (SMemberAddress) o;

        if (addrId != null ? !addrId.equals(that.addrId) : that.addrId != null) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (userName != null ? !userName.equals(that.userName) : that.userName != null) return false;
        if (province != null ? !province.equals(that.province) : that.province != null) return false;
        if (city != null ? !city.equals(that.city) : that.city != null) return false;
        if (country != null ? !country.equals(that.country) : that.country != null) return false;
        if (address != null ? !address.equals(that.address) : that.address != null) return false;
        if (mobile != null ? !mobile.equals(that.mobile) : that.mobile != null) return false;
        if (zip != null ? !zip.equals(that.zip) : that.zip != null) return false;
        if (tel != null ? !tel.equals(that.tel) : that.tel != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (fax != null ? !fax.equals(that.fax) : that.fax != null) return false;
        if (defAddr != null ? !defAddr.equals(that.defAddr) : that.defAddr != null) return false;
        if (shipAddressName != null ? !shipAddressName.equals(that.shipAddressName) : that.shipAddressName != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = addrId != null ? addrId.hashCode() : 0;
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (province != null ? province.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (mobile != null ? mobile.hashCode() : 0);
        result = 31 * result + (zip != null ? zip.hashCode() : 0);
        result = 31 * result + (tel != null ? tel.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (fax != null ? fax.hashCode() : 0);
        result = 31 * result + (defAddr != null ? defAddr.hashCode() : 0);
        result = 31 * result + (shipAddressName != null ? shipAddressName.hashCode() : 0);
        return result;
    }
}
