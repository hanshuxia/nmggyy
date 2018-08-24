package com.anchorcms.icloud.model.synergy;

import com.anchorcms.cms.model.main.Content;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

/**
 * @Author 阁楼麻雀
 * @Email netuser.orz@icloud.com
 * @Date 2016/12/19
 * @Desc 能力询价信息表
 */
@Entity
@Table(name = "s_ability_inquiry")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE )
public class SAbilityInquiry implements Serializable{
    private static final long serialVersionUID = 960185127443824413L;
    private int inquiryId;
    private int createrId;
    private Double quotePrice;
    private String inquiryTheme;
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
    //private String companyId;
    private String operatorId;
    private String classifyType;
    private String deliverType;
    private String taskType;
    private String isShow;
    private String colulsFshow;
    private String isFshow;
    private String dealType;
    private String payType;
    private String reason;
    private String statusType;
    private Date deadlineDt;
    private Date createDt;
    private Date updateDt;
    private Date releaseDt;
    private Date deliverDt;
    private String deFlag;
    private String postCode;//邮编
    private String invoiceCompany;//发票公司名称
    private String taxRegNo;//纳税人识别号
    private String registeredAddress;//注册地址
    private String bankInfo;//银行信息
    //邮编
    @Basic
    @Column(name = "POSTCODE")
    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }
    @Id
    @Column(name = "INQUIRY_ID")
    public int getInquiryId() {
        return inquiryId;
    }

    public void setInquiryId(int inquiryId) {
        this.inquiryId = inquiryId;
    }

    @Basic
    @Column(name = "QUOTE_PRICE")
    public Double getQuotePrice() {
        return quotePrice;
    }

    public void setQuotePrice(Double quotePrice) {
        this.quotePrice = quotePrice;
    }

    @Basic
    @Column(name = "INQUIRY_THEME")
    public String getInquiryTheme() {
        return inquiryTheme;
    }

    public void setInquiryTheme(String inquiryTheme) {
        this.inquiryTheme = inquiryTheme;
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

    @Transient
    public String getCompanyId() {
        return company==null?null:(company.getCompanyId());
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
    @Column(name = "COLULS_FSHOW")
    public String getColulsFshow() {
        return colulsFshow;
    }

    public void setColulsFshow(String colulsFshow) {
        this.colulsFshow = colulsFshow;
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
    @Column(name = "PAY_TYPE")
    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
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
    @Column(name = "DELIVER_DT")
    public Date getDeliverDt() {
        return deliverDt;
    }

    public void setDeliverDt(Date deliverDt) {
        this.deliverDt = deliverDt;
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
    public int getCreaterId() {
        return createrId;
    }

    public void setCreaterId(int createrId) {
        this.createrId = createrId;
    }


    @Basic
    @Column(name = "INVOICECOMPANY")
    public String getInvoiceCompany() {
        return invoiceCompany;
    }

    public void setInvoiceCompany(String invoiceCompany) {
        this.invoiceCompany = invoiceCompany;
    }

    @Basic
    @Column(name = "TAXREGNO")
    public String getTaxRegNo() {
        return taxRegNo;
    }

    public void setTaxRegNo(String taxRegNo) {
        this.taxRegNo = taxRegNo;
    }

    @Basic
    @Column(name = "REGISTEREDADDRESS")
    public String getRegisteredAddress() {
        return registeredAddress;
    }

    public void setRegisteredAddress(String registeredAddress) {
        this.registeredAddress = registeredAddress;
    }

    @Basic
    @Column(name = "BANKINFO")
    public String getBankInfo() {
        return bankInfo;
    }

    public void setBankInfo(String bankInfo) {
        this.bankInfo = bankInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SAbilityInquiry that = (SAbilityInquiry) o;
        if (postCode != null ? !postCode.equals(that.postCode) : that.postCode != null) return false;
        if (inquiryId != that.inquiryId) return false;
        if (createrId != that.createrId) return false;
        if (inquiryTheme != null ? !inquiryTheme.equals(that.inquiryTheme) : that.inquiryTheme != null) return false;
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
        if (operatorId != null ? !operatorId.equals(that.operatorId) : that.operatorId != null) return false;
        if (classifyType != null ? !classifyType.equals(that.classifyType) : that.classifyType != null) return false;
        if (deliverType != null ? !deliverType.equals(that.deliverType) : that.deliverType != null) return false;
        if (taskType != null ? !taskType.equals(that.taskType) : that.taskType != null) return false;
        if (isShow != null ? !isShow.equals(that.isShow) : that.isShow != null) return false;
        if (colulsFshow != null ? !colulsFshow.equals(that.colulsFshow) : that.colulsFshow != null) return false;
        if (isFshow != null ? !isFshow.equals(that.isFshow) : that.isFshow != null) return false;
        if (dealType != null ? !dealType.equals(that.dealType) : that.dealType != null) return false;
        if (payType != null ? !payType.equals(that.payType) : that.payType != null) return false;
        if (reason != null ? !reason.equals(that.reason) : that.reason != null) return false;
        if (statusType != null ? !statusType.equals(that.statusType) : that.statusType != null) return false;
        if (deadlineDt != null ? !deadlineDt.equals(that.deadlineDt) : that.deadlineDt != null) return false;
        if (createDt != null ? !createDt.equals(that.createDt) : that.createDt != null) return false;
        if (updateDt != null ? !updateDt.equals(that.updateDt) : that.updateDt != null) return false;
        if (releaseDt != null ? !releaseDt.equals(that.releaseDt) : that.releaseDt != null) return false;
        if (deliverDt != null ? !deliverDt.equals(that.deliverDt) : that.deliverDt != null) return false;
        if (deFlag != null ? !deFlag.equals(that.deFlag) : that.deFlag != null) return false;
        if (content != null ? !content.equals(that.content) : that.content != null) return false;
        if (invoiceCompany != null ? !invoiceCompany.equals(that.invoiceCompany) : that.invoiceCompany != null)
            return false;
        if (taxRegNo != null ? !taxRegNo.equals(that.taxRegNo) : that.taxRegNo != null)
            return false;
        if (registeredAddress != null ? !registeredAddress.equals(that.registeredAddress) : that.registeredAddress != null)
            return false;
        if (bankInfo != null ? !bankInfo.equals(that.bankInfo) : that.bankInfo != null)
            return false;
        return ability != null ? ability.equals(that.ability) : that.ability == null;

    }

    @Override
    public int hashCode() {
        int result = inquiryId;
        result = 31 * result + (postCode != null ? postCode.hashCode() : 0);
        result = 31 * result + createrId;
        result = 31 * result + (inquiryTheme != null ? inquiryTheme.hashCode() : 0);
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
        result = 31 * result + (operatorId != null ? operatorId.hashCode() : 0);
        result = 31 * result + (classifyType != null ? classifyType.hashCode() : 0);
        result = 31 * result + (deliverType != null ? deliverType.hashCode() : 0);
        result = 31 * result + (taskType != null ? taskType.hashCode() : 0);
        result = 31 * result + (isShow != null ? isShow.hashCode() : 0);
        result = 31 * result + (colulsFshow != null ? colulsFshow.hashCode() : 0);
        result = 31 * result + (isFshow != null ? isFshow.hashCode() : 0);
        result = 31 * result + (dealType != null ? dealType.hashCode() : 0);
        result = 31 * result + (payType != null ? payType.hashCode() : 0);
        result = 31 * result + (reason != null ? reason.hashCode() : 0);
        result = 31 * result + (statusType != null ? statusType.hashCode() : 0);
        result = 31 * result + (deadlineDt != null ? deadlineDt.hashCode() : 0);
        result = 31 * result + (createDt != null ? createDt.hashCode() : 0);
        result = 31 * result + (updateDt != null ? updateDt.hashCode() : 0);
        result = 31 * result + (releaseDt != null ? releaseDt.hashCode() : 0);
        result = 31 * result + (deliverDt != null ? deliverDt.hashCode() : 0);
        result = 31 * result + (deFlag != null ? deFlag.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (ability != null ? ability.hashCode() : 0);
        result = 31 * result + (invoiceCompany != null ? invoiceCompany.hashCode() : 0);
        result = 31 * result + (taxRegNo != null ? taxRegNo.hashCode() : 0);
        result = 31 * result + (registeredAddress != null ? registeredAddress.hashCode() : 0);
        result = 31 * result + (bankInfo != null ? bankInfo.hashCode() : 0);
        return result;
    }


    private Content content;

    private SAbility ability;

    //这个company存的是询价的公司的，不是能力的公司
    private SCompany company;

    @ManyToOne(targetEntity = SCompany.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "COMPANY_ID")
    public SCompany getCompany() {
        return company;
    }

    public void setCompany(SCompany company) {
        this.company = company;
    }

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "content_id")
    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    @ManyToOne
    @JoinColumn(name = "ability_id")
    @NotFound(action= NotFoundAction.IGNORE)
    public SAbility getAbility() {
        return ability;
    }

    public void setAbility(SAbility ability) {
        this.ability = ability;
    }
}

