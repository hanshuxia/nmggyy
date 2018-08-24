package com.anchorcms.icloud.model.supplychain;

import com.anchorcms.icloud.model.synergy.SCompany;
import com.anchorcms.icloud.model.synergy.SDemand;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by machao on 2016/12/27.
 * 维修资源报价表
 */
@Entity
@Table(name = "s_repair_quote")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE )
public class SRepairQuote implements Serializable {
    private static final long serialVersionUID = -9117921504614766905L;
    private String demandObjId;
    private String contentId;
    private String demandId;
    private String offerExplan;
    private String contact;
    private String mobile;
    private String email;
    private String fax;
    // private String companyId;
    private String operatorId;
    private Date updateDt;
    private String createrId;
    private Date releaseDt;
    private Date deadlineDt;
    private Date createDt;
    private String deFlag;
    private String selectStatus;
    private String evaluate;


    @Id
    @Column(name = "DEMAND_OBJ_ID")
    public String getDemandObjId() {
        return demandObjId;
    }

    public void setDemandObjId(String demandObjId) {
        this.demandObjId = demandObjId;
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
    @Column(name = "DEMAND_ID")
    public String getDemandId() {
        return demandId;
    }

    public void setDemandId(String demandId) {
        this.demandId = demandId;
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

    @Transient
    public String getCompanyId() {
        return this.getCompany().getCompanyId();
    }
    // public void setCompanyId(String companyId) {
    //     this.companyId = companyId;
    // }

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
    @Basic
    @Column(name = "SELECT_STATUS")
    public String getSelectStatus() {
        return selectStatus;
    }
    public void setSelectStatus(String selectStatus) {
        this.selectStatus = selectStatus;
    }


    @Basic
    @Column(name = "EVALUATE")
    public String getEvaluate() {
        return evaluate;
    }
    public void setEvaluate(String evaluate) {
        this.evaluate = evaluate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SRepairQuote that = (SRepairQuote) o;

        if (demandObjId != null ? !demandObjId.equals(that.demandObjId) : that.demandObjId != null) return false;
        if (contentId != null ? !contentId.equals(that.contentId) : that.contentId != null) return false;
        if (demandId != null ? !demandId.equals(that.demandId) : that.demandId != null) return false;
        if (offerExplan != null ? !offerExplan.equals(that.offerExplan) : that.offerExplan != null) return false;
        if (contact != null ? !contact.equals(that.contact) : that.contact != null) return false;
        if (mobile != null ? !mobile.equals(that.mobile) : that.mobile != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (fax != null ? !fax.equals(that.fax) : that.fax != null) return false;
        // if (companyId != null ? !companyId.equals(that.companyId) : that.companyId != null) return false;
        if (operatorId != null ? !operatorId.equals(that.operatorId) : that.operatorId != null) return false;
        if (updateDt != null ? !updateDt.equals(that.updateDt) : that.updateDt != null) return false;
        if (createrId != null ? !createrId.equals(that.createrId) : that.createrId != null) return false;
        if (releaseDt != null ? !releaseDt.equals(that.releaseDt) : that.releaseDt != null) return false;
        if (deadlineDt != null ? !deadlineDt.equals(that.deadlineDt) : that.deadlineDt != null) return false;
        if (createDt != null ? !createDt.equals(that.createDt) : that.createDt != null) return false;
        if (deFlag != null ? !deFlag.equals(that.deFlag) : that.deFlag != null) return false;
        if (evaluate != null ? !evaluate.equals(that.evaluate) : that.evaluate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = demandObjId != null ? demandObjId.hashCode() : 0;
        result = 31 * result + (contentId != null ? contentId.hashCode() : 0);
        result = 31 * result + (demandId != null ? demandId.hashCode() : 0);
        result = 31 * result + (offerExplan != null ? offerExplan.hashCode() : 0);
        result = 31 * result + (contact != null ? contact.hashCode() : 0);
        result = 31 * result + (mobile != null ? mobile.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (fax != null ? fax.hashCode() : 0);
        // result = 31 * result + (companyId != null ? companyId.hashCode() : 0);
        result = 31 * result + (operatorId != null ? operatorId.hashCode() : 0);
        result = 31 * result + (updateDt != null ? updateDt.hashCode() : 0);
        result = 31 * result + (createrId != null ? createrId.hashCode() : 0);
        result = 31 * result + (releaseDt != null ? releaseDt.hashCode() : 0);
        result = 31 * result + (deadlineDt != null ? deadlineDt.hashCode() : 0);
        result = 31 * result + (createDt != null ? createDt.hashCode() : 0);
        result = 31 * result + (deFlag != null ? deFlag.hashCode() : 0);
        result = 31 * result + (evaluate != null ? evaluate.hashCode() : 0);
        return result;
    }

    private List<SRepairAbility> sRepairAbility;
    @OneToMany(targetEntity = SRepairAbility.class, cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumns(value = {@JoinColumn(name = "DEMAND_REQUEST_ID", referencedColumnName = "DEMAND_OBJ_ID")})
    public List<SRepairAbility> getsRepairAbility() {
        return sRepairAbility;
    }
    public void setsRepairAbility(List<SRepairAbility> sRepairAbility) {
        this.sRepairAbility = sRepairAbility;
    }

    private SCompany company;
    @ManyToOne(targetEntity = SCompany.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "COMPANY_ID")
    public SCompany getCompany() { return company; }
    public void setCompany(SCompany company) { this.company = company; }

    private SRepairDemand demand;

    @ManyToOne(targetEntity = SRepairDemand.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "DEMAND_ID", insertable=false,updatable=false)
    public SRepairDemand getDemand() {
        return demand;
    }

    public void setDemand(SRepairDemand demand) {
        this.demand = demand;
    }

}
