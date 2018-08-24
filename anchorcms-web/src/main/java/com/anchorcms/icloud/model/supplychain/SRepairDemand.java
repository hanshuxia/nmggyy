package com.anchorcms.icloud.model.supplychain;

import com.anchorcms.cms.model.main.Content;
import com.anchorcms.icloud.model.synergy.SCompany;
import com.anchorcms.icloud.model.synergy.SDemandQuote;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * @Author zhouyq
 * @Date 2016/12/26
 * @Desc 维修需求信息表
 */
@Entity
@Table(name = "s_repair_demand")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE )
public class SRepairDemand implements Serializable {
    public static  final String MODEL_PATH = "repairDemand";
    private static final long serialVersionUID = 7568092733222853313L;
    private String repairId; // 唯一标识
    private String repairType; // 维修类型
    private String repairName; // 维修需求名称
    private String addrProvince; // 地址（省）
    private String addrCity; // 地址（地级市）
    private String addrCounty; // 地址（市、县级）
    private String addrStreet; // 地址(街道)
    private String isUrgency; // 订单紧急程度
    private Double expectPrice; // 期望总价
    private String isShow; // 是否显示期望价格
    private String description; // 补充说明
    private String dealType; // 交易方式
    private String payType; // 付款方式
    private String invoiceType; // 发票类型
    private String freightBorne; // 运费承担商
    private String releaseType; // 发布方式
    private String contact; // 联系人
    private String mobile; // 联系电话
    private String telephone; // 固定电话
    private String fax; // 传真
    private String email; // 邮箱
    private String releaseId; // 发布人
    private Date releaseDt; // 发布日期
    private String createrId; // 创建人
    private Date createDt; // 创建时间
    private Date startDt; // 开始日期
    private Date deadlineDt; // 截止日期
    private String state; // 状态
    private String deFlag; // 逻辑删除
    private String contentId;
    private String backReason;
    // private String companyId; getCompanyId为 @Transient


    @Id
    @Column(name = "REPAIR_ID", nullable = false, length = 32)
    public String getRepairId() {
        return repairId;
    }

    public void setRepairId(String repairId) {
        this.repairId = repairId;
    }

    @Basic
    @Column(name = "REPAIR_TYPE", nullable = true)
    public String getRepairType() {
        return repairType;
    }

    public void setRepairType(String repairType) {
        this.repairType = repairType;
    }

    @Basic
    @Column(name = "REPAIR_NAME", nullable = true)
    public String getRepairName() {
        return repairName;
    }

    public void setRepairName(String repairName) {
        this.repairName = repairName;
    }

    @Basic
    @Column(name = "ADDR_PROVINCE", nullable = true)
    public String getAddrProvince() {
        return addrProvince;
    }

    public void setAddrProvince(String addrProvince) {
        this.addrProvince = addrProvince;
    }

    @Basic
    @Column(name = "ADDR_CITY", nullable = true)
    public String getAddrCity() {
        return addrCity;
    }

    public void setAddrCity(String addrCity) {
        this.addrCity = addrCity;
    }

    @Basic
    @Column(name = "ADDR_COUNTY", nullable = true)
    public String getAddrCounty() {
        return addrCounty;
    }

    public void setAddrCounty(String addrCounty) {
        this.addrCounty = addrCounty;
    }

    @Basic
    @Column(name = "ADDR_STREET", nullable = true)
    public String getAddrStreet() {
        return addrStreet;
    }

    public void setAddrStreet(String addrStreet) {
        this.addrStreet = addrStreet;
    }

    @Basic
    @Column(name = "IS_URGENCY", nullable = true)
    public String getIsUrgency() {
        return isUrgency;
    }

    public void setIsUrgency(String isUrgency) {
        this.isUrgency = isUrgency;
    }

    @Basic
    @Column(name = "EXPECT_PRICE", nullable = true, precision = 0)
    public Double getExpectPrice() {
        return expectPrice;
    }

    public void setExpectPrice(Double expectPrice) {
        this.expectPrice = expectPrice;
    }

    @Basic
    @Column(name = "IS_SHOW", nullable = true, length = 2)
    public String getIsShow() {
        return isShow;
    }

    public void setIsShow(String isShow) {
        this.isShow = isShow;
    }

    @Basic
    @Column(name = "DESCRIPTION", nullable = true, length = 1024)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "DEAL_TYPE", nullable = true, length = 4)
    public String getDealType() {
        return dealType;
    }

    public void setDealType(String dealType) {
        this.dealType = dealType;
    }

    @Basic
    @Column(name = "PAY_TYPE", nullable = true, length = 4)
    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    @Basic
    @Column(name = "INVOICE_TYPE", nullable = true, length = 2)
    public String getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }

    @Basic
    @Column(name = "FREIGHT_BORNE", nullable = true, length = 2)
    public String getFreightBorne() {
        return freightBorne;
    }

    public void setFreightBorne(String freightBorne) {
        this.freightBorne = freightBorne;
    }

    @Basic
    @Column(name = "RELEASE_TYPE", nullable = true, length = 2)
    public String getReleaseType() {
        return releaseType;
    }

    public void setReleaseType(String releaseType) {
        this.releaseType = releaseType;
    }

    @Basic
    @Column(name = "CONTACT", nullable = true, length = 32)
    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    @Basic
    @Column(name = "MOBILE", nullable = true, length = 16)
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Basic
    @Column(name = "TELEPHONE", nullable = true, length = 32)
    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Basic
    @Column(name = "FAX", nullable = true, length = 32)
    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    @Basic
    @Column(name = "EMAIL", nullable = true, length = 64)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "RELEASE_ID", nullable = true, length = 32)
    public String getReleaseId() {
        return releaseId;
    }

    public void setReleaseId(String releaseId) {
        this.releaseId = releaseId;
    }

    @Basic
    @Column(name = "RELEASE_DT", nullable = true)
    public Date getReleaseDt() {
        return releaseDt;
    }

    public void setReleaseDt(Date releaseDt) {
        this.releaseDt = releaseDt;
    }

    @Basic
    @Column(name = "CREATER_ID", nullable = true, length = 32)
    public String getCreaterId() {
        return createrId;
    }

    public void setCreaterId(String createrId) {
        this.createrId = createrId;
    }

    @Basic
    @Column(name = "CREATE_DT", nullable = true)
    public Date getCreateDt() {
        return createDt;
    }

    public void setCreateDt(Date createDt) {
        this.createDt = createDt;
    }

    @Basic
    @Column(name = "START_DT", nullable = true)
    public Date getStartDt() {
        return startDt;
    }

    public void setStartDt(Date startDt) {
        this.startDt = startDt;
    }

    @Basic
    @Column(name = "DEADLINE_DT", nullable = true)
    public Date getDeadlineDt() {
        return deadlineDt;
    }

    public void setDeadlineDt(Date deadlineDt) {
        this.deadlineDt = deadlineDt;
    }

    @Basic
    @Column(name = "STATE", nullable = true, length = 2)
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Basic
    @Column(name = "DE_FLAG", nullable = true, length = 2)
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
    @Column(name = "BACKREASON")
    public String getBackReason() {
        return backReason;
    }

    public void setBackReason(String backReason) {
        this.backReason = backReason;
    }

    @Transient
    public String getCompanyId() {
        return this.getCompany().getCompanyId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SRepairDemand that = (SRepairDemand) o;

        if (repairId != null ? !repairId.equals(that.repairId) : that.repairId != null) return false;
        if (repairType != null ? !repairType.equals(that.repairType) : that.repairType != null) return false;
        if (repairName != null ? !repairName.equals(that.repairName) : that.repairName != null) return false;
        if (addrProvince != null ? !addrProvince.equals(that.addrProvince) : that.addrProvince != null) return false;
        if (addrCity != null ? !addrCity.equals(that.addrCity) : that.addrCity != null) return false;
        if (addrCounty != null ? !addrCounty.equals(that.addrCounty) : that.addrCounty != null) return false;
        if (addrStreet != null ? !addrStreet.equals(that.addrStreet) : that.addrStreet != null) return false;
        if (isUrgency != null ? !isUrgency.equals(that.isUrgency) : that.isUrgency != null) return false;
        if (expectPrice != null ? !expectPrice.equals(that.expectPrice) : that.expectPrice != null) return false;
        if (isShow != null ? !isShow.equals(that.isShow) : that.isShow != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (dealType != null ? !dealType.equals(that.dealType) : that.dealType != null) return false;
        if (payType != null ? !payType.equals(that.payType) : that.payType != null) return false;
        if (invoiceType != null ? !invoiceType.equals(that.invoiceType) : that.invoiceType != null) return false;
        if (freightBorne != null ? !freightBorne.equals(that.freightBorne) : that.freightBorne != null) return false;
        if (releaseType != null ? !releaseType.equals(that.releaseType) : that.releaseType != null) return false;
        if (contact != null ? !contact.equals(that.contact) : that.contact != null) return false;
        if (mobile != null ? !mobile.equals(that.mobile) : that.mobile != null) return false;
        if (telephone != null ? !telephone.equals(that.telephone) : that.telephone != null) return false;
        if (fax != null ? !fax.equals(that.fax) : that.fax != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (releaseId != null ? !releaseId.equals(that.releaseId) : that.releaseId != null) return false;
        if (releaseDt != null ? !releaseDt.equals(that.releaseDt) : that.releaseDt != null) return false;
        if (createrId != null ? !createrId.equals(that.createrId) : that.createrId != null) return false;
        if (createDt != null ? !createDt.equals(that.createDt) : that.createDt != null) return false;
        if (startDt != null ? !startDt.equals(that.startDt) : that.startDt != null) return false;
        if (deadlineDt != null ? !deadlineDt.equals(that.deadlineDt) : that.deadlineDt != null) return false;
        if (state != null ? !state.equals(that.state) : that.state != null) return false;
        if (deFlag != null ? !deFlag.equals(that.deFlag) : that.deFlag != null) return false;
        if (contentId != null ? !contentId.equals(that.contentId) : that.contentId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = repairId != null ? repairId.hashCode() : 0;
        result = 31 * result + (repairType != null ? repairType.hashCode() : 0);
        result = 31 * result + (repairName != null ? repairName.hashCode() : 0);
        result = 31 * result + (addrProvince != null ? addrProvince.hashCode() : 0);
        result = 31 * result + (addrCity != null ? addrCity.hashCode() : 0);
        result = 31 * result + (addrCounty != null ? addrCounty.hashCode() : 0);
        result = 31 * result + (addrStreet != null ? addrStreet.hashCode() : 0);
        result = 31 * result + (isUrgency != null ? isUrgency.hashCode() : 0);
        result = 31 * result + (expectPrice != null ? expectPrice.hashCode() : 0);
        result = 31 * result + (isShow != null ? isShow.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (dealType != null ? dealType.hashCode() : 0);
        result = 31 * result + (payType != null ? payType.hashCode() : 0);
        result = 31 * result + (invoiceType != null ? invoiceType.hashCode() : 0);
        result = 31 * result + (freightBorne != null ? freightBorne.hashCode() : 0);
        result = 31 * result + (releaseType != null ? releaseType.hashCode() : 0);
        result = 31 * result + (contact != null ? contact.hashCode() : 0);
        result = 31 * result + (mobile != null ? mobile.hashCode() : 0);
        result = 31 * result + (telephone != null ? telephone.hashCode() : 0);
        result = 31 * result + (fax != null ? fax.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (releaseId != null ? releaseId.hashCode() : 0);
        result = 31 * result + (releaseDt != null ? releaseDt.hashCode() : 0);
        result = 31 * result + (createrId != null ? createrId.hashCode() : 0);
        result = 31 * result + (createDt != null ? createDt.hashCode() : 0);
        result = 31 * result + (startDt != null ? startDt.hashCode() : 0);
        result = 31 * result + (deadlineDt != null ? deadlineDt.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (deFlag != null ? deFlag.hashCode() : 0);
        result = 31 * result + (contentId != null ? contentId.hashCode() : 0);
        return result;
    }

    private  Content content;

    @OneToOne(cascade = CascadeType.ALL )
    @JoinColumn(name = "content_id",insertable = false,updatable = false)
    public  Content getContent(){
        return content;
    }
    public void setContent(Content content){
        this.content = content;
    }

    private List<SRepairDemandObj> sRepairDemandObj;

    @OneToMany(targetEntity = SRepairDemandObj.class, cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumns(value = {@JoinColumn(name = "REPAIR_ID", referencedColumnName = "REPAIR_ID")})
    public List<SRepairDemandObj> getsRepairDemandObj() {
        return sRepairDemandObj;
    }
    public void setsRepairDemandObj(List<SRepairDemandObj> sRepairDemandObj) {
        this.sRepairDemandObj = sRepairDemandObj;
    }

    private  List<SRepairQuote> sRepairQuote;

    @OneToMany(targetEntity = SRepairQuote.class, fetch = FetchType.EAGER)
    @JoinColumns(value = {@JoinColumn(name = "DEMAND_ID", referencedColumnName = "REPAIR_ID")})
    public List<SRepairQuote> getsRepairQuote() {
        return sRepairQuote;
    }
    public void setsRepairQuote(List<SRepairQuote> sRepairQuote) {
        this.sRepairQuote = sRepairQuote;
    }

    private SCompany company;

    @ManyToOne(targetEntity = SCompany.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "COMPANY_ID")
    public SCompany getCompany() { return company; }

    public void setCompany(SCompany company) { this.company = company; }

    @Transient
    public Integer getQuoteCount(){
        List <SRepairQuote> list = getsRepairQuote();
        if (list != null) {
            return list.size();
        }else{
            return 0;
        }
    }


    @Transient
    public double getRepairObjNum(){
        List <SRepairDemandObj> list = getsRepairDemandObj();
        double num = 0;
        if (list != null) {
            for (int i=0;i<list.size();i++){
                num += list.get(i).getRepairNum();
            }
            return num;
        }else{
            return 0;
        }
    }


    @Transient
    public Integer getBetween() throws ParseException {
        java.util.Date date = getDeadlineDt();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        long time1 = cal.getTimeInMillis();
        cal.setTime(new java.util.Date());
        long time2 = cal.getTimeInMillis();
        long between_days=(time1-time2)/(1000*3600*24)+1;
        return Integer.parseInt(String.valueOf(between_days));
    }

}

