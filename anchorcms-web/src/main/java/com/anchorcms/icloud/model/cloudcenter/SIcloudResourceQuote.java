package com.anchorcms.icloud.model.cloudcenter;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

/**
 * @anther 李利民
 * @descript
 * @data 2017/1/178:50
 */
@Entity
@Table(name = "s_icloud_resource_quote")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE )
public class SIcloudResourceQuote implements Serializable {
    private int resourceQuoteId;
    private Integer managerId;
    private String offerExplan;
    private String telephone;
    private String contact;
    private String mobile;
    private String email;
    private String fax;
    private Double price;
    private String companyId;
    private String operatorId;
    private Date updateDt;
    private String createrId;
    private Date releaseDt;
    private Date deadlineDt;
    private Date createDt;
    private String deFlag;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RESOURCEQUOTE_ID")
    public int getResourceQuoteId() {
        return resourceQuoteId;
    }

    public void setResourceQuoteId(int resourceQuoteId) {
        this.resourceQuoteId = resourceQuoteId;
    }

    @Basic
    @Column(name = "MANAGER_Id")
    public Integer getManagerId() {
        return managerId;
    }

    public void setManagerId(Integer managerId) {
        this.managerId = managerId;
    }

    @Basic
    @Column(name = "OFFER_EXPLAN")
    public String getOfferExplan() {
        return offerExplan;
    }

    public void setOfferExplan(String offerExplan) {
        this.offerExplan = offerExplan;
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
    @Column(name = "FAX")
    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
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
    @Column(name = "COMPANY_ID")
    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    @Basic
    @Column(name = "OPERATOR_ID")
    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
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
    @Column(name = "CREATER_ID")
    public String getCreaterId() {
        return createrId;
    }

    public void setCreaterId(String createrId) {
        this.createrId = createrId;
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
    @Column(name = "CREATE_DT")
    public Date getCreateDt() {
        return createDt;
    }

    public void setCreateDt(Date createDt) {
        this.createDt = createDt;
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

        SIcloudResourceQuote that = (SIcloudResourceQuote) o;

        if (resourceQuoteId != that.resourceQuoteId) return false;
        if (managerId != null ? !managerId.equals(that.managerId) : that.managerId != null) return false;
        if (offerExplan != null ? !offerExplan.equals(that.offerExplan) : that.offerExplan != null) return false;
        if (telephone != null ? !telephone.equals(that.telephone) : that.telephone != null) return false;
        if (contact != null ? !contact.equals(that.contact) : that.contact != null) return false;
        if (mobile != null ? !mobile.equals(that.mobile) : that.mobile != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (fax != null ? !fax.equals(that.fax) : that.fax != null) return false;
        if (price != null ? !price.equals(that.price) : that.price != null) return false;
        if (companyId != null ? !companyId.equals(that.companyId) : that.companyId != null) return false;
        if (operatorId != null ? !operatorId.equals(that.operatorId) : that.operatorId != null) return false;
        if (updateDt != null ? !updateDt.equals(that.updateDt) : that.updateDt != null) return false;
        if (createrId != null ? !createrId.equals(that.createrId) : that.createrId != null) return false;
        if (releaseDt != null ? !releaseDt.equals(that.releaseDt) : that.releaseDt != null) return false;
        if (deadlineDt != null ? !deadlineDt.equals(that.deadlineDt) : that.deadlineDt != null) return false;
        if (createDt != null ? !createDt.equals(that.createDt) : that.createDt != null) return false;
        if (deFlag != null ? !deFlag.equals(that.deFlag) : that.deFlag != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = resourceQuoteId;
        result = 31 * result + (managerId != null ? managerId.hashCode() : 0);
        result = 31 * result + (offerExplan != null ? offerExplan.hashCode() : 0);
        result = 31 * result + (telephone != null ? telephone.hashCode() : 0);
        result = 31 * result + (contact != null ? contact.hashCode() : 0);
        result = 31 * result + (mobile != null ? mobile.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (fax != null ? fax.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (companyId != null ? companyId.hashCode() : 0);
        result = 31 * result + (operatorId != null ? operatorId.hashCode() : 0);
        result = 31 * result + (updateDt != null ? updateDt.hashCode() : 0);
        result = 31 * result + (createrId != null ? createrId.hashCode() : 0);
        result = 31 * result + (releaseDt != null ? releaseDt.hashCode() : 0);
        result = 31 * result + (deadlineDt != null ? deadlineDt.hashCode() : 0);
        result = 31 * result + (createDt != null ? createDt.hashCode() : 0);
        result = 31 * result + (deFlag != null ? deFlag.hashCode() : 0);
        return result;
    }
}
