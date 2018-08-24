package com.anchorcms.icloud.model.cloudcenter;

import com.anchorcms.cms.model.main.Content;
import com.anchorcms.icloud.model.synergy.SCompany;
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
 * @Date 2017/1/3
 * @Desc 云需求信息表
 */
@Entity
@Table(name = "s_icloud_demand")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE )
public class SIcloudDemand implements Serializable{
    private static final long serialVersionUID = 8131990666685653141L;
    private Integer demandId;
    private String demandName;
    private String serverType;
    private Integer count;
    private String dealType;
    private String payType;
    private String invoiceType;
    private String releaseType;
    private String contact;
    private String mobile;
    private String telephone;
    private String fax;
    private String email;
    private String createrId;
    private Date deadlineDt;
    private Date deliverDt;
    private Date createDt;
    private String deFlag;
    private String freightBorne;
    private String addrProvince;
    private String addrCity;
    private String addrCounty;
    private String addrStreet;
    private String inquiryComp;
    private double expect_price;
    private String unit;
    private String classify_code;
  /*  private String companyId;*/
    private String operatorId;
    private Date updateDt;
    private Date releaseDt;
    private String status;
    private String backReason;
    private String invoiceCompany;//发票公司名称
    private String taxRegNo;//纳税人识别号
    private String registeredAddress;//注册地址
    private String bankInfo;//银行信息
    @Basic
    @Column(name = "BACKREASON")
    public String getBackReason() {
        return backReason;
    }

    public void setBackReason(String backReason) {
        this.backReason = backReason;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DEMAND_ID")
    public Integer getDemandId() {
        return demandId;
    }

    public void setDemandId(Integer demandId) {
        this.demandId = demandId;
    }

    @Basic
    @Column(name = "EXPECT_PRICE")
    public double getExpect_price() {
        return expect_price;
    }

    public void setExpect_price(double expect_price) {
        this.expect_price = expect_price;
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
    @Column(name = "CLASSIFY_CODE")
    public String getClassify_code() {
        return classify_code;
    }

    public void setClassify_code(String classify_code) {
        this.classify_code = classify_code;
    }

    @Basic
    @Column(name = "DEMAND_NAME")
    public String getDemandName() {
        return demandName;
    }

    public void setDemandName(String demandName) {
        this.demandName = demandName;
    }

    @Basic
    @Column(name = "SERVER_TYPE")
    public String getServerType() {
        return serverType;
    }

    public void setServerType(String serverType) {
        this.serverType = serverType;
    }

    @Basic
    @Column(name = "COUNT")
    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
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
    @Column(name = "CREATER_ID")
    public String getCreaterId() {
        return createrId;
    }

    public void setCreaterId(String createrId) {
        this.createrId = createrId;
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
    @Column(name = "STATUS")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

        SIcloudDemand that = (SIcloudDemand) o;

        if (demandId != null ? !demandId.equals(that.demandId) : that.demandId != null) return false;
        if (demandName != null ? !demandName.equals(that.demandName) : that.demandName != null) return false;
        if (serverType != null ? !serverType.equals(that.serverType) : that.serverType != null) return false;
        if (count != null ? !count.equals(that.count) : that.count != null) return false;
        if (dealType != null ? !dealType.equals(that.dealType) : that.dealType != null) return false;
        if (payType != null ? !payType.equals(that.payType) : that.payType != null) return false;
        if (invoiceType != null ? !invoiceType.equals(that.invoiceType) : that.invoiceType != null) return false;
        if (releaseType != null ? !releaseType.equals(that.releaseType) : that.releaseType != null) return false;
        if (contact != null ? !contact.equals(that.contact) : that.contact != null) return false;
        if (mobile != null ? !mobile.equals(that.mobile) : that.mobile != null) return false;
        if (telephone != null ? !telephone.equals(that.telephone) : that.telephone != null) return false;
        if (fax != null ? !fax.equals(that.fax) : that.fax != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (createrId != null ? !createrId.equals(that.createrId) : that.createrId != null) return false;
        if (deadlineDt != null ? !deadlineDt.equals(that.deadlineDt) : that.deadlineDt != null) return false;
        if (deliverDt != null ? !deliverDt.equals(that.deliverDt) : that.deliverDt != null) return false;
        if (createDt != null ? !createDt.equals(that.createDt) : that.createDt != null) return false;
        if (deFlag != null ? !deFlag.equals(that.deFlag) : that.deFlag != null) return false;
        if (invoiceCompany != null ? !invoiceCompany.equals(that.invoiceCompany) : that.invoiceCompany != null)
            return false;
        if (taxRegNo != null ? !taxRegNo.equals(that.taxRegNo) : that.taxRegNo != null)
            return false;
        if (registeredAddress != null ? !registeredAddress.equals(that.registeredAddress) : that.registeredAddress != null)
            return false;
        if (bankInfo != null ? !bankInfo.equals(that.bankInfo) : that.bankInfo != null)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = demandId != null ? demandId.hashCode() : 0;
        result = 31 * result + (demandName != null ? demandName.hashCode() : 0);
        result = 31 * result + (serverType != null ? serverType.hashCode() : 0);
        result = 31 * result + (count != null ? count.hashCode() : 0);
        result = 31 * result + (dealType != null ? dealType.hashCode() : 0);
        result = 31 * result + (payType != null ? payType.hashCode() : 0);
        result = 31 * result + (invoiceType != null ? invoiceType.hashCode() : 0);
        result = 31 * result + (releaseType != null ? releaseType.hashCode() : 0);
        result = 31 * result + (contact != null ? contact.hashCode() : 0);
        result = 31 * result + (mobile != null ? mobile.hashCode() : 0);
        result = 31 * result + (telephone != null ? telephone.hashCode() : 0);
        result = 31 * result + (fax != null ? fax.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (createrId != null ? createrId.hashCode() : 0);
        result = 31 * result + (deadlineDt != null ? deadlineDt.hashCode() : 0);
        result = 31 * result + (deliverDt != null ? deliverDt.hashCode() : 0);
        result = 31 * result + (createDt != null ? createDt.hashCode() : 0);
        result = 31 * result + (deFlag != null ? deFlag.hashCode() : 0);
        result = 31 * result + (invoiceCompany != null ? invoiceCompany.hashCode() : 0);
        result = 31 * result + (taxRegNo != null ? taxRegNo.hashCode() : 0);
        result = 31 * result + (registeredAddress != null ? registeredAddress.hashCode() : 0);
        result = 31 * result + (bankInfo != null ? bankInfo.hashCode() : 0);
        return result;
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
    @Column(name = "INQUIRY_COMP")
    public String getInquiryComp() {
        return inquiryComp;
    }

    public void setInquiryComp(String inquiryComp) {
        this.inquiryComp = inquiryComp;
    }

 /*   @Basic
    @Column(name = "COMPANY_ID")
    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }*/

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
    @Column(name = "RELEASE_DT")
    public Date getReleaseDt() {
        return releaseDt;
    }

    public void setReleaseDt(Date releaseDt) {
        this.releaseDt = releaseDt;
    }
    private Content content;
    private List<SIcloudDemandObj> SIcloudDemandObjList;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "content_id")
    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }
//    @OneToMany(targetEntity = SIcloudDemandObj.class, cascade = CascadeType.ALL)
//    @JoinColumns(value = {@JoinColumn(name = "DEMAND_ID", referencedColumnName = "DEMAND_ID")})
//    public List<SIcloudDemandObj> getSIcloudDemandObjList() {
//        return SIcloudDemandObjList;
//    }

//    public void setSIcloudDemandObjList(List<SIcloudDemandObj> SIcloudDemandObjList) {
//        this.SIcloudDemandObjList = SIcloudDemandObjList;
//    }
    private SCompany company;
    @ManyToOne(targetEntity = SCompany.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "COMPANY_ID")
    public SCompany getCompany() { return company; }

    public void setCompany(SCompany company) { this.company = company; }


    private  List<SIcloudDemandQuote> demandQuotes;


    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn( name = "DEMAND_ID")
    public List<SIcloudDemandQuote> getDemandQuotes() {
        return demandQuotes;
    }
    public void setDemandQuotes(List<SIcloudDemandQuote> demandQuotes) {
        this.demandQuotes = demandQuotes;
    }


    @Transient
    public Integer getQuoteCount(){
        List <SIcloudDemandQuote> list = getDemandQuotes();
        if (list != null) {
            return list.size();
        }else{
            return 0;
        }
    }
    //查询剩余天数
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
