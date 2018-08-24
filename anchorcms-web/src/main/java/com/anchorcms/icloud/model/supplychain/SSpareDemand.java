package com.anchorcms.icloud.model.supplychain;

import com.anchorcms.cms.model.main.Content;
import com.anchorcms.icloud.model.synergy.SCompany;
import com.anchorcms.icloud.model.synergy.SDemandObj;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by DELL on 2016/12/22.
 */
@Entity
@Table(name = "s_spare_demand")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE )
public class SSpareDemand implements Serializable {
    public static  final String MODEL_PATH = "spareDemand";
    private static final long serialVersionUID = -3646233738844960928L;
    private String demandId;//唯一标识
//    private String contentId;//跟cms关联的字段
    private String requestTheme;//求购主题
    private String isUrgency;//订单紧急程度
    private Double expectPrice;//期望总价
    private String isShow;//是否显示期望报价
    private String dealType;//交易方式
    private String payType;//付款方式
    private String invoiceType;//发票类型
    private String carryType;//配送方式
    private String addrProvince;//地址（省）
    private String addrCity;//地址（地级市）
    private String addrCounty;//地址（市、县级）
    private String addrStreet;//街道信息
    private String mobile;//MOBILE
    private String telephone;//TELEPHONE
    private String fax;//传真
    private String email;//邮箱
    private String reinspection;//是否复验筛选
    private String dpa;//是否DPA
    private String factoryInspection;//是否下厂验收
    private String freightBorne;//运费承担商
    private String releaseId;//发布人id
//    private String companyId;//所属企业id
    private String createrId;//创建人id
    private Date createDt;//创建时间
    private Date updateDt;//更新时间
    private Date releaseDt;//发布时间
    private Date deadlineDt;//求购截止日期
    private Date deliverDt;//要求交货日期
    private String state;//状态
    private String optimalState;//优选状态
    private String deFlag;//逻辑判断
    private String requestType;//求购类型
    private String backReason;//驳回原因

    @Id
    @Column(name = "DEMAND_ID")
    public String getDemandId() {
        return demandId;
    }

    public void setDemandId(String demandId) {
        this.demandId = demandId;
    }

//    @Basic
//    @Column(name = "CONTENT_ID")
//    public String getContentId() { return contentId;}
//
//    public void setContentId(String contentId) { this.contentId = contentId;}

    @Basic
    @Column(name = "REQUEST_THEME")
    public String getRequestTheme() {
        return requestTheme;
    }

    public void setRequestTheme(String requestTheme) {
        this.requestTheme = requestTheme;
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
    @Column(name = "EXPECT_PRICE")
    public Double getExpectPrice() {
        return expectPrice;
    }

    public void setExpectPrice(Double expectPrice) {
        this.expectPrice = expectPrice;
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
    @Column(name = "CARRY_TYPE")
    public String getCarryType() {
        return carryType;
    }

    public void setCarryType(String carryType) {
        this.carryType = carryType;
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
    @Column(name = "REINSPECTION")
    public String getReinspection() {
        return reinspection;
    }

    public void setReinspection(String reinspection) {
        this.reinspection = reinspection;
    }

    @Basic
    @Column(name = "DPA")
    public String getDpa() {
        return dpa;
    }

    public void setDpa(String dpa) {
        this.dpa = dpa;
    }

    @Basic
    @Column(name = "FACTORY_INSPECTION")
    public String getFactoryInspection() {
        return factoryInspection;
    }

    public void setFactoryInspection(String factoryInspection) {
        this.factoryInspection = factoryInspection;
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
    @Column(name = "RELEASE_ID")
    public String getReleaseId() {
        return releaseId;
    }

    public void setReleaseId(String releaseId) {
        this.releaseId = releaseId;
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
    @Column(name = "STATE")
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Basic
    @Column(name = "OPTIMAL_STATE")
    public String getOptimalState() {
        return optimalState;
    }

    public void setOptimalState(String optimalState) {
        this.optimalState = optimalState;
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
    @Column(name = "REQUEST_TYPE")
    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    @Basic
    @Column(name = "BACKREASON")
    public String getBackReason() {
        return backReason;
    }

    public void setBackReason(String backReason) {
        this.backReason = backReason;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SSpareDemand that = (SSpareDemand) o;

        if (demandId != null ? !demandId.equals(that.demandId) : that.demandId != null) return false;
        if (requestTheme != null ? !requestTheme.equals(that.requestTheme) : that.requestTheme != null) return false;
        if (isUrgency != null ? !isUrgency.equals(that.isUrgency) : that.isUrgency != null) return false;
        if (expectPrice != null ? !expectPrice.equals(that.expectPrice) : that.expectPrice != null) return false;
        if (isShow != null ? !isShow.equals(that.isShow) : that.isShow != null) return false;
        if (dealType != null ? !dealType.equals(that.dealType) : that.dealType != null) return false;
        if (payType != null ? !payType.equals(that.payType) : that.payType != null) return false;
        if (invoiceType != null ? !invoiceType.equals(that.invoiceType) : that.invoiceType != null) return false;
        if (carryType != null ? !carryType.equals(that.carryType) : that.carryType != null) return false;
        if (addrProvince != null ? !addrProvince.equals(that.addrProvince) : that.addrProvince != null) return false;
        if (addrCity != null ? !addrCity.equals(that.addrCity) : that.addrCity != null) return false;
        if (addrCounty != null ? !addrCounty.equals(that.addrCounty) : that.addrCounty != null) return false;
        if (addrStreet != null ? !addrStreet.equals(that.addrStreet) : that.addrStreet != null) return false;
        if (mobile != null ? !mobile.equals(that.mobile) : that.mobile != null) return false;
        if (telephone != null ? !telephone.equals(that.telephone) : that.telephone != null) return false;
        if (fax != null ? !fax.equals(that.fax) : that.fax != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (reinspection != null ? !reinspection.equals(that.reinspection) : that.reinspection != null) return false;
        if (dpa != null ? !dpa.equals(that.dpa) : that.dpa != null) return false;
        if (factoryInspection != null ? !factoryInspection.equals(that.factoryInspection) : that.factoryInspection != null)
            return false;
        if (freightBorne != null ? !freightBorne.equals(that.freightBorne) : that.freightBorne != null) return false;
        if (releaseId != null ? !releaseId.equals(that.releaseId) : that.releaseId != null) return false;
//        if (companyId != null ? !companyId.equals(that.companyId) : that.companyId != null) return false;
        if (createrId != null ? !createrId.equals(that.createrId) : that.createrId != null) return false;
        if (createDt != null ? !createDt.equals(that.createDt) : that.createDt != null) return false;
        if (updateDt != null ? !updateDt.equals(that.updateDt) : that.updateDt != null) return false;
        if (releaseDt != null ? !releaseDt.equals(that.releaseDt) : that.releaseDt != null) return false;
        if (deadlineDt != null ? !deadlineDt.equals(that.deadlineDt) : that.deadlineDt != null) return false;
        if (deliverDt != null ? !deliverDt.equals(that.deliverDt) : that.deliverDt != null) return false;
        if (state != null ? !state.equals(that.state) : that.state != null) return false;
        if (optimalState != null ? !optimalState.equals(that.optimalState) : that.optimalState != null) return false;
        if (deFlag != null ? !deFlag.equals(that.deFlag) : that.deFlag != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = demandId != null ? demandId.hashCode() : 0;
        result = 31 * result + (requestTheme != null ? requestTheme.hashCode() : 0);
        result = 31 * result + (isUrgency != null ? isUrgency.hashCode() : 0);
        result = 31 * result + (expectPrice != null ? expectPrice.hashCode() : 0);
        result = 31 * result + (isShow != null ? isShow.hashCode() : 0);
        result = 31 * result + (dealType != null ? dealType.hashCode() : 0);
        result = 31 * result + (payType != null ? payType.hashCode() : 0);
        result = 31 * result + (invoiceType != null ? invoiceType.hashCode() : 0);
        result = 31 * result + (carryType != null ? carryType.hashCode() : 0);
        result = 31 * result + (addrProvince != null ? addrProvince.hashCode() : 0);
        result = 31 * result + (addrCity != null ? addrCity.hashCode() : 0);
        result = 31 * result + (addrCounty != null ? addrCounty.hashCode() : 0);
        result = 31 * result + (addrStreet != null ? addrStreet.hashCode() : 0);
        result = 31 * result + (mobile != null ? mobile.hashCode() : 0);
        result = 31 * result + (telephone != null ? telephone.hashCode() : 0);
        result = 31 * result + (fax != null ? fax.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (reinspection != null ? reinspection.hashCode() : 0);
        result = 31 * result + (dpa != null ? dpa.hashCode() : 0);
        result = 31 * result + (factoryInspection != null ? factoryInspection.hashCode() : 0);
        result = 31 * result + (freightBorne != null ? freightBorne.hashCode() : 0);
        result = 31 * result + (releaseId != null ? releaseId.hashCode() : 0);
//        result = 31 * result + (companyId != null ? companyId.hashCode() : 0);
        result = 31 * result + (createrId != null ? createrId.hashCode() : 0);
        result = 31 * result + (createDt != null ? createDt.hashCode() : 0);
        result = 31 * result + (updateDt != null ? updateDt.hashCode() : 0);
        result = 31 * result + (releaseDt != null ? releaseDt.hashCode() : 0);
        result = 31 * result + (deadlineDt != null ? deadlineDt.hashCode() : 0);
        result = 31 * result + (deliverDt != null ? deliverDt.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (optimalState != null ? optimalState.hashCode() : 0);
        result = 31 * result + (deFlag != null ? deFlag.hashCode() : 0);
        return result;
    }
    private Content content;
    private List<SSpareDemandObj> spareDemandObjLists;
    private SCompany scompany;

//    @OneToOne
//    @JoinColumn(name = "content_id",insertable = false,updatable = false)
//    public Content getContent() {
//        return content;
//    }
//
//    public void setContent(Content content) {
//        this.content = content;
//    }
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "content_id")
    public Content getContent() {return content;}

    public void setContent(Content content) {
        this.content = content;
    }

    @OneToMany(targetEntity = SSpareDemandObj.class, cascade = CascadeType.ALL)
    @JoinColumns(value = {@JoinColumn(name = "DEMAND_ID", referencedColumnName = "DEMAND_ID")})

    public List<SSpareDemandObj> getSpareDemandObjLists() {
        return spareDemandObjLists;
    }

    public void setSpareDemandObjLists(List<SSpareDemandObj> spareDemandObjLists) {
        this.spareDemandObjLists = spareDemandObjLists;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id")

    public SCompany getScompany() {
        return scompany;
    }

    public void setScompany(SCompany scompany) {
        this.scompany = scompany;
    }

    @Transient
    public double getSpareObjNum(){
        List <SSpareDemandObj> list = getSpareDemandObjLists();
        double num = 0;
        if (list != null) {
            for (int i=0;i<list.size();i++){
                num += list.get(i).getRequestNum();
            }
            return num;
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
        cal.setTime(new java.util.Date());
        long time2 = cal.getTimeInMillis();
        long between_days=(time1-time2)/(1000*3600*24)+1;
        return Integer.parseInt(String.valueOf(between_days));
    }
}
