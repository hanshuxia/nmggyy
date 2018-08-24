package com.anchorcms.icloud.model.supplychain;

import com.anchorcms.cms.model.main.Content;
import com.anchorcms.icloud.model.synergy.SCompany;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

/**
 * Created by machao on 2016/12/27.
 * 维修资源询价表
 */
@Entity
@Table(name = "s_repair_inquiry")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE )
public class SRepairInquiry implements Serializable {
    private static final long serialVersionUID = 4580039419059779833L;
    private String inquiryId;
    private String contentId;
    //    private String inquiryInfoId;
    private String inquiryTheme;
    private String repairType;
    private String address;
    private String invoiceType;
    private String freightBorne;
    private String addrProvince;
    private String addrCity;
    private String addrCounty;
    private String addrStreet;
    private String instruction;
    private String releaseType;
    private String contact;
    private String mobile;
    private String telephone;
    private String email;
    private String fax;
    private String isUrgency;
    private Double demandCount;
    private Double expectPrice;
    //    private String companyId;
    private String operatorId;
    private String classifyType;
    private String deliverType;
    private String taskType;
    private String isShow;
    private String coluIsFshow;
    private String isFshow;
    private String dealType;
    private String reason;
    private String statusType;
    private Date deadlineDt;
    private Date createDt;
    private Date updateDt;
    private Date releaseDt;
    private String deFlag;
    private String createrId;
    private Double offer;
    private String evaluate;
    public Double getOffer() {
        return offer;
    }

    public void setOffer(Double offer) {
        this.offer = offer;
    }

    @Id
    @Column(name = "INQUIRY_ID")
    public String getInquiryId() {
        return inquiryId;
    }

    public void setInquiryId(String inquiryId) {
        this.inquiryId = inquiryId;
    }

//    @Basic
//    @Column(name = "CONTENT_ID")
//    public String getContentId() {
//        return contentId;
//    }
//
//    public void setContentId(String contentId) {
//        this.contentId = contentId;
//    }

   /* @Basic
    @Column(name = "INQUIRY_INFO_ID")
    public String getInquiryInfoId() {
        return inquiryInfoId;
    }

    public void setInquiryInfoId(String inquiryInfoId) {
        this.inquiryInfoId = inquiryInfoId;
    }*/

    @Basic
    @Column(name = "INQUIRY_THEME")
    public String getInquiryTheme() {
        return inquiryTheme;
    }

    public void setInquiryTheme(String inquiryTheme) {
        this.inquiryTheme = inquiryTheme;
    }

    @Basic
    @Column(name = "REPAIR_TYPE")
    public String getRepairType() {
        return repairType;
    }

    public void setRepairType(String repairType) {
        this.repairType = repairType;
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
    @Column(name = "INVOICE_TYPE")
    public String getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }

    @Basic
    @Column(name = "FREIGHT_BORNE")
    public String getFreightBorne() {
        return freightBorne;
    }

    public void setFreightBorne(String freightBorne) {
        this.freightBorne = freightBorne;
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
    @Column(name = "INSTRUCTION")
    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    @Basic
    @Column(name = "RELEASE_TYPE")
    public String getReleaseType() {
        return releaseType;
    }

    public void setReleaseType(String releaseType) {
        this.releaseType = releaseType;
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
    @Column(name = "TELEPHONE")
    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
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
    @Column(name = "IS_URGENCY")
    public String getIsUrgency() {
        return isUrgency;
    }

    public void setIsUrgency(String isUrgency) {
        this.isUrgency = isUrgency;
    }

    @Basic
    @Column(name = "DEMAND_COUNT")
    public Double getDemandCount() {
        return demandCount;
    }

    public void setDemandCount(Double demandCount) {
        this.demandCount = demandCount;
    }

    @Basic
    @Column(name = "EXPECT_PRICE")
    public Double getExpectPrice() {
        return expectPrice;
    }

    public void setExpectPrice(Double expectPrice) {
        this.expectPrice = expectPrice;
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
    @Column(name = "OPERATOR_ID")
    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    @Basic
    @Column(name = "CLASSIFY_TYPE")
    public String getClassifyType() {
        return classifyType;
    }

    public void setClassifyType(String classifyType) {
        this.classifyType = classifyType;
    }

    @Basic
    @Column(name = "DELIVER_TYPE")
    public String getDeliverType() {
        return deliverType;
    }

    public void setDeliverType(String deliverType) {
        this.deliverType = deliverType;
    }

    @Basic
    @Column(name = "TASK_TYPE")
    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    @Basic
    @Column(name = "IS_SHOW")
    public String getIsShow() {
        return isShow;
    }

    public void setIsShow(String isShow) {
        this.isShow = isShow;
    }

    @Basic
    @Column(name = "ColuIS_FSHOW")
    public String getColuIsFshow() {
        return coluIsFshow;
    }

    public void setColuIsFshow(String coluIsFshow) {
        this.coluIsFshow = coluIsFshow;
    }

    @Basic
    @Column(name = "IS_FSHOW")
    public String getIsFshow() {
        return isFshow;
    }

    public void setIsFshow(String isFshow) {
        this.isFshow = isFshow;
    }

    @Basic
    @Column(name = "DEAL_TYPE")
    public String getDealType() {
        return dealType;
    }

    public void setDealType(String dealType) {
        this.dealType = dealType;
    }

    @Basic
    @Column(name = "REASON")
    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Basic
    @Column(name = "STATUS_TYPE")
    public String getStatusType() {
        return statusType;
    }

    public void setStatusType(String statusType) {
        this.statusType = statusType;
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
    @Column(name = "DE_FLAG")
    public String getDeFlag() {
        return deFlag;
    }

    public void setDeFlag(String deFlag) {
        this.deFlag = deFlag;
    }

    @Basic
    @Column(name = "creater_ID")
    public String getCreaterId() {
        return createrId;
    }

    public void setCreaterId(String createrId) {
        this.createrId = createrId;
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

        SRepairInquiry that = (SRepairInquiry) o;

        if (inquiryId != null ? !inquiryId.equals(that.inquiryId) : that.inquiryId != null) return false;
        if (contentId != null ? !contentId.equals(that.contentId) : that.contentId != null) return false;
//        if (inquiryInfoId != null ? !inquiryInfoId.equals(that.inquiryInfoId) : that.inquiryInfoId != null)
//            return false;
        if (inquiryTheme != null ? !inquiryTheme.equals(that.inquiryTheme) : that.inquiryTheme != null) return false;
        if (repairType != null ? !repairType.equals(that.repairType) : that.repairType != null) return false;
        if (address != null ? !address.equals(that.address) : that.address != null) return false;
        if (invoiceType != null ? !invoiceType.equals(that.invoiceType) : that.invoiceType != null) return false;
        if (freightBorne != null ? !freightBorne.equals(that.freightBorne) : that.freightBorne != null) return false;
        if (addrProvince != null ? !addrProvince.equals(that.addrProvince) : that.addrProvince != null) return false;
        if (addrCity != null ? !addrCity.equals(that.addrCity) : that.addrCity != null) return false;
        if (addrCounty != null ? !addrCounty.equals(that.addrCounty) : that.addrCounty != null) return false;
        if (addrStreet != null ? !addrStreet.equals(that.addrStreet) : that.addrStreet != null) return false;
        if (instruction != null ? !instruction.equals(that.instruction) : that.instruction != null) return false;
        if (releaseType != null ? !releaseType.equals(that.releaseType) : that.releaseType != null) return false;
        if (contact != null ? !contact.equals(that.contact) : that.contact != null) return false;
        if (mobile != null ? !mobile.equals(that.mobile) : that.mobile != null) return false;
        if (telephone != null ? !telephone.equals(that.telephone) : that.telephone != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (fax != null ? !fax.equals(that.fax) : that.fax != null) return false;
        if (isUrgency != null ? !isUrgency.equals(that.isUrgency) : that.isUrgency != null) return false;
        if (demandCount != null ? !demandCount.equals(that.demandCount) : that.demandCount != null) return false;
        if (expectPrice != null ? !expectPrice.equals(that.expectPrice) : that.expectPrice != null) return false;
//        if (companyId != null ? !companyId.equals(that.companyId) : that.companyId != null) return false;
        if (operatorId != null ? !operatorId.equals(that.operatorId) : that.operatorId != null) return false;
        if (classifyType != null ? !classifyType.equals(that.classifyType) : that.classifyType != null) return false;
        if (deliverType != null ? !deliverType.equals(that.deliverType) : that.deliverType != null) return false;
        if (taskType != null ? !taskType.equals(that.taskType) : that.taskType != null) return false;
        if (isShow != null ? !isShow.equals(that.isShow) : that.isShow != null) return false;
        if (coluIsFshow != null ? !coluIsFshow.equals(that.coluIsFshow) : that.coluIsFshow != null) return false;
        if (isFshow != null ? !isFshow.equals(that.isFshow) : that.isFshow != null) return false;
        if (dealType != null ? !dealType.equals(that.dealType) : that.dealType != null) return false;
        if (reason != null ? !reason.equals(that.reason) : that.reason != null) return false;
        if (statusType != null ? !statusType.equals(that.statusType) : that.statusType != null) return false;
        if (deadlineDt != null ? !deadlineDt.equals(that.deadlineDt) : that.deadlineDt != null) return false;
        if (createDt != null ? !createDt.equals(that.createDt) : that.createDt != null) return false;
        if (updateDt != null ? !updateDt.equals(that.updateDt) : that.updateDt != null) return false;
        if (releaseDt != null ? !releaseDt.equals(that.releaseDt) : that.releaseDt != null) return false;
        if (deFlag != null ? !deFlag.equals(that.deFlag) : that.deFlag != null) return false;
        if (createrId != null ? !createrId.equals(that.createrId) : that.createrId != null) return false;
        if (offer != null ? !offer.equals(that.offer) : that.offer != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = inquiryId != null ? inquiryId.hashCode() : 0;
        result = 31 * result + (contentId != null ? contentId.hashCode() : 0);
//        result = 31 * result + (inquiryInfoId != null ? inquiryInfoId.hashCode() : 0);
        result = 31 * result + (inquiryTheme != null ? inquiryTheme.hashCode() : 0);
        result = 31 * result + (repairType != null ? repairType.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (invoiceType != null ? invoiceType.hashCode() : 0);
        result = 31 * result + (freightBorne != null ? freightBorne.hashCode() : 0);
        result = 31 * result + (addrProvince != null ? addrProvince.hashCode() : 0);
        result = 31 * result + (addrCity != null ? addrCity.hashCode() : 0);
        result = 31 * result + (addrCounty != null ? addrCounty.hashCode() : 0);
        result = 31 * result + (addrStreet != null ? addrStreet.hashCode() : 0);
        result = 31 * result + (instruction != null ? instruction.hashCode() : 0);
        result = 31 * result + (releaseType != null ? releaseType.hashCode() : 0);
        result = 31 * result + (contact != null ? contact.hashCode() : 0);
        result = 31 * result + (mobile != null ? mobile.hashCode() : 0);
        result = 31 * result + (telephone != null ? telephone.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (fax != null ? fax.hashCode() : 0);
        result = 31 * result + (isUrgency != null ? isUrgency.hashCode() : 0);
        result = 31 * result + (demandCount != null ? demandCount.hashCode() : 0);
        result = 31 * result + (expectPrice != null ? expectPrice.hashCode() : 0);
//        result = 31 * result + (companyId != null ? companyId.hashCode() : 0);
        result = 31 * result + (operatorId != null ? operatorId.hashCode() : 0);
        result = 31 * result + (classifyType != null ? classifyType.hashCode() : 0);
        result = 31 * result + (deliverType != null ? deliverType.hashCode() : 0);
        result = 31 * result + (taskType != null ? taskType.hashCode() : 0);
        result = 31 * result + (isShow != null ? isShow.hashCode() : 0);
        result = 31 * result + (coluIsFshow != null ? coluIsFshow.hashCode() : 0);
        result = 31 * result + (isFshow != null ? isFshow.hashCode() : 0);
        result = 31 * result + (dealType != null ? dealType.hashCode() : 0);
        result = 31 * result + (reason != null ? reason.hashCode() : 0);
        result = 31 * result + (statusType != null ? statusType.hashCode() : 0);
        result = 31 * result + (deadlineDt != null ? deadlineDt.hashCode() : 0);
        result = 31 * result + (createDt != null ? createDt.hashCode() : 0);
        result = 31 * result + (updateDt != null ? updateDt.hashCode() : 0);
        result = 31 * result + (releaseDt != null ? releaseDt.hashCode() : 0);
        result = 31 * result + (deFlag != null ? deFlag.hashCode() : 0);
        result = 31 * result + (createrId != null ? createrId.hashCode() : 0);
        result = 31 * result + (offer != null ? offer.hashCode() : 0);
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
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id")
    @NotFound(action= NotFoundAction.IGNORE)
    public SCompany getCompany() {
        return company;
    }

    public void setCompany(SCompany company) {
        this.company = company;
    }

    private SRepairRes sRepairRes;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "inquiry_info_id")
    @NotFound(action= NotFoundAction.IGNORE)
    public SRepairRes getsRepairRes() {
        return sRepairRes;
    }

    public void setsRepairRes(SRepairRes sRepairRes) {
        this.sRepairRes = sRepairRes;
    }
}
