package com.anchorcms.icloud.model.supplychain;

import com.anchorcms.cms.model.main.Content;
import com.anchorcms.icloud.model.synergy.SCompany;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

/**
 * Created by machao on 2016/12/27.
 * 备品备件信息表
 */
@Entity
@Table(name = "s_spare")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE )
public class SSpare implements Serializable {
    public static  final String MODEL_PATH = "spare";
    private static final long serialVersionUID = -8659637876247462569L;
    private String sparepartsId; // 唯一标识
    private String spareType; // 备品分类
    private String spareName; // 备件名称
    private String spareDesc; // 备件描述
    private Double inventCount; // 库存数量
    private String fax; // 传真
    private String email; // email
    private String telephone; // 电话
    private String addrProvince; // 地址（省）
    private String addrCity; // 地址（地级市）
    private String addrCounty; // 地址（市、县级）
    private String addrStreet; // 街道信息
    private String contact; // 联系人
    private String mobile; // 联系电话
    private String source; // 来源
    private String publicType; // 公开情况
    private String putawayType; // 是否下架
    private String createrId; // 创建人
    private Date createDt; // 创建时间
    private Date updateDt; // 更新时间
    private Date releaseDt; // 发布时间
    private Date deadlineDt; // 截止日期
    private String deFlag; // 逻辑删除
    private String contentId;
    private String status;//状态
    private String userType;//用户类型
    private String backReason;
    private Double referPrice; //价格

    @Id
    @Column(name = "SPAREPARTS_ID")
    public String getSparepartsId() {
        return sparepartsId;
    }

    public void setSparepartsId(String sparepartsId) {
        this.sparepartsId = sparepartsId;
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
    @Column(name = "SPARE_DESC")
    public String getSpareDesc() {
        return spareDesc;
    }

    public void setSpareDesc(String spareDesc) {
        this.spareDesc = spareDesc;
    }

    @Basic
    @Column(name = "INVENT_COUNT")
    public Double getInventCount() {
        return inventCount;
    }

    public void setInventCount(Double inventCount) {
        this.inventCount = inventCount;
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
    @Column(name = "TELEPHONE")
    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
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
    @Column(name = "SOURCE")
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Basic
    @Column(name = "PUBLIC_TYPE")
    public String getPublicType() {
        return publicType;
    }

    public void setPublicType(String publicType) {
        this.publicType = publicType;
    }

    @Basic
    @Column(name = "PUTAWAY_TYPE")
    public String getPutawayType() {
        return putawayType;
    }

    public void setPutawayType(String putawayType) {
        this.putawayType = putawayType;
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
    @Column(name = "RELEASE_DT")
    public Date getReleaseDt() {
        return releaseDt;
    }

    public void setReleaseDt(Date releaseDt) {
        this.releaseDt = releaseDt;
    }

    @Basic
    @Column(name = "DEADLINE_DT")
    public Date getDeadlineDt() {
        return deadlineDt;
    }

    public void setDeadlineDt(Date deadlineDt) {
        this.deadlineDt = deadlineDt;
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
    @Column(name = "CONTENT_ID")
    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    @Basic
    @Column(name = "STATUS")
    public String getStatus() { return status;}

    public void setStatus(String status) { this.status = status; }

    @Basic
    @Column(name = "BACKREASON")
    public String getBackReason() { return backReason;}

    public void setBackReason(String backReason) { this.backReason = backReason; }

    @Basic
    @Column(name = "USER_TYPE")
    public String getUserType() { return userType;}

    public void setUserType(String userType) { this.userType = userType; }

    @Basic
    @Column(name = "REFER_PRICE")
    public Double getReferPrice() {
        return referPrice;
    }

    public void setReferPrice(Double referPrice) {
        this.referPrice = referPrice;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SSpare that = (SSpare) o;

        if (sparepartsId != null ? !sparepartsId.equals(that.sparepartsId) : that.sparepartsId != null) return false;
        if (spareType != null ? !spareType.equals(that.spareType) : that.spareType != null) return false;
        if (spareName != null ? !spareName.equals(that.spareName) : that.spareName != null) return false;
        if (spareDesc != null ? !spareDesc.equals(that.spareDesc) : that.spareDesc != null) return false;
        if (inventCount != null ? !inventCount.equals(that.inventCount) : that.inventCount != null) return false;
        if (fax != null ? !fax.equals(that.fax) : that.fax != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (telephone != null ? !telephone.equals(that.telephone) : that.telephone != null) return false;
        if (addrProvince != null ? !addrProvince.equals(that.addrProvince) : that.addrProvince != null) return false;
        if (addrCity != null ? !addrCity.equals(that.addrCity) : that.addrCity != null) return false;
        if (addrCounty != null ? !addrCounty.equals(that.addrCounty) : that.addrCounty != null) return false;
        if (addrStreet != null ? !addrStreet.equals(that.addrStreet) : that.addrStreet != null) return false;
        if (contact != null ? !contact.equals(that.contact) : that.contact != null) return false;
        if (mobile != null ? !mobile.equals(that.mobile) : that.mobile != null) return false;
        if (source != null ? !source.equals(that.source) : that.source != null) return false;
        if (publicType != null ? !publicType.equals(that.publicType) : that.publicType != null) return false;
        if (putawayType != null ? !putawayType.equals(that.putawayType) : that.putawayType != null) return false;
        if (createrId != null ? !createrId.equals(that.createrId) : that.createrId != null) return false;
        if (createDt != null ? !createDt.equals(that.createDt) : that.createDt != null) return false;
        if (updateDt != null ? !updateDt.equals(that.updateDt) : that.updateDt != null) return false;
        if (releaseDt != null ? !releaseDt.equals(that.releaseDt) : that.releaseDt != null) return false;
        if (deadlineDt != null ? !deadlineDt.equals(that.deadlineDt) : that.deadlineDt != null) return false;
        if (deFlag != null ? !deFlag.equals(that.deFlag) : that.deFlag != null) return false;
        if (contentId != null ? !contentId.equals(that.contentId) : that.contentId != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (referPrice != null ? !referPrice.equals(that.referPrice) : that.referPrice != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = sparepartsId != null ? sparepartsId.hashCode() : 0;
        result = 31 * result + (spareType != null ? spareType.hashCode() : 0);
        result = 31 * result + (spareName != null ? spareName.hashCode() : 0);
        result = 31 * result + (spareDesc != null ? spareDesc.hashCode() : 0);
        result = 31 * result + (inventCount != null ? inventCount.hashCode() : 0);
        result = 31 * result + (fax != null ? fax.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (telephone != null ? telephone.hashCode() : 0);
        result = 31 * result + (addrProvince != null ? addrProvince.hashCode() : 0);
        result = 31 * result + (addrCity != null ? addrCity.hashCode() : 0);
        result = 31 * result + (addrCounty != null ? addrCounty.hashCode() : 0);
        result = 31 * result + (addrStreet != null ? addrStreet.hashCode() : 0);
        result = 31 * result + (contact != null ? contact.hashCode() : 0);
        result = 31 * result + (mobile != null ? mobile.hashCode() : 0);
        result = 31 * result + (source != null ? source.hashCode() : 0);
        result = 31 * result + (publicType != null ? publicType.hashCode() : 0);
        result = 31 * result + (putawayType != null ? putawayType.hashCode() : 0);
        result = 31 * result + (createrId != null ? createrId.hashCode() : 0);
        result = 31 * result + (createDt != null ? createDt.hashCode() : 0);
        result = 31 * result + (updateDt != null ? updateDt.hashCode() : 0);
        result = 31 * result + (releaseDt != null ? releaseDt.hashCode() : 0);
        result = 31 * result + (deadlineDt != null ? deadlineDt.hashCode() : 0);
        result = 31 * result + (deFlag != null ? deFlag.hashCode() : 0);
        result = 31 * result + (contentId != null ? contentId.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (referPrice != null ? referPrice.hashCode() : 0);
        return result;
    }


    private Content content;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "content_id",insertable = false,updatable = false)
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
