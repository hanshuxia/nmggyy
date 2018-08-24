package com.anchorcms.icloud.model.synergy;

import com.anchorcms.cms.model.main.Content;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * @Author 阁楼麻雀
 * @Email netuser.orz@icloud.com
 * @Date 2016/12/23
 * @Desc 需求信息表
 */
@Entity
@Table(name = "s_demand")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE )
public class SDemand implements Serializable{
    private static final long serialVersionUID = 5417160529555188547L;
    public static  final String MODEL_PATH = "demand";
    private int demandId;
    private String classifyType;
    private String inquiryTheme;
    private String isShow;
    private String dealType;
    private String payType;
    private String invoiceType;
    private String freightBorne;
    private String addrProvince;
    private String addrCity;
    private String addrCounty;
    private String addrStreet;
    private String releaseType;
    private String contact;
    private String mobile;
    private String telephone;
    private String email;
    private String fax;
    private String inquiryComp;
    //private String companyId; 直接映射为Company类，同时getCompanyId方法改为Transient
    private String operatorId;
    private String createrId;
    private Date createDt;
    private Date updateDt;
    private Date releaseDt;
    private Date deadlineDt;
    private Date deliverDt;
    private String status;
    private String deFlag;
    private String statusType;
    private String recommendedType;
    private String backReason;
    private String postCode;//邮编
    private String invoiceCompany;//发票公司名称
    private String taxRegNo;//纳税人识别号
    private String registeredAddress;//注册地址
    private String bankInfo;//银行信息


    @Basic
    @Column(name = "POSTCODE")
    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DEMAND_ID")
    public int getDemandId() {
        return demandId;
    }

    public void setDemandId(int demandId) {
        this.demandId = demandId;
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
    @Column(name = "INQUIRY_THEME")
    public String getInquiryTheme() {
        return inquiryTheme;
    }

    public void setInquiryTheme(String inquiryTheme) {
        this.inquiryTheme = inquiryTheme;
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
    @Column(name = "INQUIRY_COMP")
    public String getInquiryComp() {
        return inquiryComp;
    }

    public void setInquiryComp(String inquiryComp) {
        this.inquiryComp = inquiryComp;
    }

    @Transient
    public String getCompanyId() {
        return this.getCompany().getCompanyId();
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
    @Column(name = "DELIVER_DT")
    public Date getDeliverDt() {
        return deliverDt;
    }

    public void setDeliverDt(Date deliverDt) {
        this.deliverDt = deliverDt;
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
    @Column(name = "STATUS")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
    @Column(name = "RECOMMENDED_TYPE")
    public String getRecommendedType() {
        return recommendedType;
    }

    public void setRecommendedType(String recommendedType) {
        this.recommendedType = recommendedType;
    }

    @Basic
    @Column(name = "BACKREASON")
    public String getBackReason() {
        return backReason;
    }

    public void setBackReason(String backReason) {
        this.backReason = backReason;
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

        SDemand sDemand = (SDemand) o;

        if (demandId != sDemand.demandId) return false;
        if (classifyType != null ? !classifyType.equals(sDemand.classifyType) : sDemand.classifyType != null)
            return false;
        if (inquiryTheme != null ? !inquiryTheme.equals(sDemand.inquiryTheme) : sDemand.inquiryTheme != null)
            return false;
        if (postCode != null ? !postCode.equals(sDemand.postCode) : sDemand.postCode != null)
            return false;
        if (isShow != null ? !isShow.equals(sDemand.isShow) : sDemand.isShow != null) return false;
        if (dealType != null ? !dealType.equals(sDemand.dealType) : sDemand.dealType != null) return false;
        if (payType != null ? !payType.equals(sDemand.payType) : sDemand.payType != null) return false;
        if (invoiceType != null ? !invoiceType.equals(sDemand.invoiceType) : sDemand.invoiceType != null) return false;
        if (freightBorne != null ? !freightBorne.equals(sDemand.freightBorne) : sDemand.freightBorne != null)
            return false;
        if (addrProvince != null ? !addrProvince.equals(sDemand.addrProvince) : sDemand.addrProvince != null)
            return false;
        if (addrCity != null ? !addrCity.equals(sDemand.addrCity) : sDemand.addrCity != null) return false;
        if (addrCounty != null ? !addrCounty.equals(sDemand.addrCounty) : sDemand.addrCounty != null) return false;
        if (addrStreet != null ? !addrStreet.equals(sDemand.addrStreet) : sDemand.addrStreet != null) return false;
        if (releaseType != null ? !releaseType.equals(sDemand.releaseType) : sDemand.releaseType != null) return false;
        if (contact != null ? !contact.equals(sDemand.contact) : sDemand.contact != null) return false;
        if (mobile != null ? !mobile.equals(sDemand.mobile) : sDemand.mobile != null) return false;
        if (telephone != null ? !telephone.equals(sDemand.telephone) : sDemand.telephone != null) return false;
        if (email != null ? !email.equals(sDemand.email) : sDemand.email != null) return false;
        if (fax != null ? !fax.equals(sDemand.fax) : sDemand.fax != null) return false;
        if (inquiryComp != null ? !inquiryComp.equals(sDemand.inquiryComp) : sDemand.inquiryComp != null) return false;
        //if (companyId != null ? !companyId.equals(sDemand.companyId) : sDemand.companyId != null) return false;
        if (operatorId != null ? !operatorId.equals(sDemand.operatorId) : sDemand.operatorId != null) return false;
        if (createrId != null ? !createrId.equals(sDemand.createrId) : sDemand.createrId != null) return false;
        if (createDt != null ? !createDt.equals(sDemand.createDt) : sDemand.createDt != null) return false;
        if (updateDt != null ? !updateDt.equals(sDemand.updateDt) : sDemand.updateDt != null) return false;
        if (releaseDt != null ? !releaseDt.equals(sDemand.releaseDt) : sDemand.releaseDt != null) return false;
        if (deadlineDt != null ? !deadlineDt.equals(sDemand.deadlineDt) : sDemand.deadlineDt != null) return false;
        if (deliverDt != null ? !deliverDt.equals(sDemand.deliverDt) : sDemand.deliverDt != null) return false;
        if (status != null ? !status.equals(sDemand.status) : sDemand.status != null) return false;
        if (deFlag != null ? !deFlag.equals(sDemand.deFlag) : sDemand.deFlag != null) return false;
        if (invoiceCompany != null ? !invoiceCompany.equals(sDemand.invoiceCompany) : sDemand.invoiceCompany != null)
            return false;
        if (taxRegNo != null ? !taxRegNo.equals(sDemand.taxRegNo) : sDemand.taxRegNo != null)
            return false;
        if (registeredAddress != null ? !registeredAddress.equals(sDemand.registeredAddress) : sDemand.registeredAddress != null)
            return false;
        if (bankInfo != null ? !bankInfo.equals(sDemand.bankInfo) : sDemand.bankInfo != null)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = demandId;
        result = 31 * result + (classifyType != null ? classifyType.hashCode() : 0);
        result = 31 * result + (postCode != null ? postCode.hashCode() : 0);
        result = 31 * result + (inquiryTheme != null ? inquiryTheme.hashCode() : 0);
        result = 31 * result + (isShow != null ? isShow.hashCode() : 0);
        result = 31 * result + (dealType != null ? dealType.hashCode() : 0);
        result = 31 * result + (payType != null ? payType.hashCode() : 0);
        result = 31 * result + (invoiceType != null ? invoiceType.hashCode() : 0);
        result = 31 * result + (freightBorne != null ? freightBorne.hashCode() : 0);
        result = 31 * result + (addrProvince != null ? addrProvince.hashCode() : 0);
        result = 31 * result + (addrCity != null ? addrCity.hashCode() : 0);
        result = 31 * result + (addrCounty != null ? addrCounty.hashCode() : 0);
        result = 31 * result + (addrStreet != null ? addrStreet.hashCode() : 0);
        result = 31 * result + (releaseType != null ? releaseType.hashCode() : 0);
        result = 31 * result + (contact != null ? contact.hashCode() : 0);
        result = 31 * result + (mobile != null ? mobile.hashCode() : 0);
        result = 31 * result + (telephone != null ? telephone.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (fax != null ? fax.hashCode() : 0);
        result = 31 * result + (inquiryComp != null ? inquiryComp.hashCode() : 0);
        //result = 31 * result + (companyId != null ? companyId.hashCode() : 0);
        result = 31 * result + (operatorId != null ? operatorId.hashCode() : 0);
        result = 31 * result + (createrId != null ? createrId.hashCode() : 0);
        result = 31 * result + (createDt != null ? createDt.hashCode() : 0);
        result = 31 * result + (updateDt != null ? updateDt.hashCode() : 0);
        result = 31 * result + (releaseDt != null ? releaseDt.hashCode() : 0);
        result = 31 * result + (deadlineDt != null ? deadlineDt.hashCode() : 0);
        result = 31 * result + (deliverDt != null ? deliverDt.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (deFlag != null ? deFlag.hashCode() : 0);
        result = 31 * result + (invoiceCompany != null ? invoiceCompany.hashCode() : 0);
        result = 31 * result + (taxRegNo != null ? taxRegNo.hashCode() : 0);
        result = 31 * result + (registeredAddress != null ? registeredAddress.hashCode() : 0);
        result = 31 * result + (bankInfo != null ? bankInfo.hashCode() : 0);
        return result;
    }

    private Content content;

    private List<SDemandObj> demandObjList;

    private SCompany company;

    private List<SDemandQuote> demandQuoteList;

    private List<SBigdataDemandQuote> bigQuoteList;

    @ManyToOne(targetEntity = SCompany.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "COMPANY_ID")
    public SCompany getCompany() { return company; }

    public void setCompany(SCompany company) { this.company = company; }

    @OneToMany(targetEntity = SDemandObj.class, cascade = CascadeType.ALL)
    @JoinColumns(value = {@JoinColumn(name = "DEMAND_ID", referencedColumnName = "DEMAND_ID")})
    public List<SDemandObj> getDemandObjList() {
        return demandObjList;
    }

    public void setDemandObjList(List<SDemandObj> demandObjList) {
        this.demandObjList = demandObjList;
    }


    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn( name = "DEMAND_ID")
    public List<SDemandQuote> getDemandQuoteList() {
        return demandQuoteList;
    }



    public void setDemandQuoteList(List<SDemandQuote> SDemandQuoteList) {
        this.demandQuoteList = SDemandQuoteList;
    }

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn( name = "DEMAND_ID")
    public List<SBigdataDemandQuote> getBigQuoteList() {
        return bigQuoteList;
    }

    public void setBigQuoteList(List<SBigdataDemandQuote> sBigdataDemandQuote) {
        this.bigQuoteList = sBigdataDemandQuote;
    }

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "content_id")
    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    @Transient
    public Integer getQuoteCount(){
       List <SDemandQuote> list = getDemandQuoteList();
        if (list != null) {
        return list.size();
    }else{
        return 0;
        }
    }

    @Transient
    public Integer getBigdataQuoteCount(){
        List <SBigdataDemandQuote> list = getBigQuoteList();
        if (list != null) {
            return list.size();
        }else{
            return 0;
        }
    }
   //还剩天数
    @Transient
    public Integer getBetween() throws ParseException {
        java.util.Date date = getDeadlineDt();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        long time1 = cal.getTimeInMillis();
        cal.setTime(new java.sql.Date(System.currentTimeMillis()));
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        long time2 = cal.getTimeInMillis();
        long between_days=(time1-time2)/(1000*3600);
        if(between_days>=24){
            between_days=(time1-time2)/(1000*3600*24);
        }else {
            between_days=0;
        }
        return Integer.parseInt(String.valueOf(between_days));
    }
}
