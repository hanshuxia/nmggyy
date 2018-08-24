package com.anchorcms.icloud.model.synergy;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright @ 2016 Shandong jxdinfo software co,ltd.
 * All right reserved.
 * Created by Gao Jiangning
 *
 * @Date 2016/12/29 0029
 * @Desc 报价信息表
 */
@Entity
@Table(name = "s_demand_quote")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE )
public class SDemandQuote {
    private int demandQuoteId;
    //private int demandId;
    private String offerExplan;
    private String contact;
    private String telephone;
    private String mobile;
    private String email;
    private String fax;
    //private String companyId; 直接映射为Company类，同时getCompanyId方法改为Transient
    private String operatorId;
    private Date updateDt;
    private String createrId;
    private Date releaseDt;
    private Date deadlineDt;
    private Date createDt;
    private String selectedStatus;
    private String deFlag;
    private String postCode;//邮编
    private String addrProvince;
    private String addrCity;
    private String addrCounty;
    private String addrStreet;
    @Basic
    @Column(name = "POSTCODE")
    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }
    @Basic
    @Column(name = "ABILITY_PROV")
    public String getAddrProvince() {
        return addrProvince;
    }

    public void setAddrProvince(String addrProvince) {
        this.addrProvince = addrProvince;
    }

    @Basic
    @Column(name = "ABILITY_CITY")
    public String getAddrCity() {
        return addrCity;
    }

    public void setAddrCity(String addrCity) {
        this.addrCity = addrCity;
    }

    @Basic
    @Column(name = "ABILITY_AREA")
    public String getAddrCounty() {
        return addrCounty;
    }

    public void setAddrCounty(String addrCounty) {
        this.addrCounty = addrCounty;
    }

    @Basic
    @Column(name = "ABILITY_ADDRESS")
    public String getAddrStreet() {
        return addrStreet;
    }

    public void setAddrStreet(String addrStreet) {
        this.addrStreet = addrStreet;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DEMAND_QUOTE_ID")
    public int getDemandQuoteId() {
        return demandQuoteId;
    }

    public void setDemandQuoteId(int demandQuoteId) {
        this.demandQuoteId = demandQuoteId;
    }

    @Transient
    public int getDemandId() {
        return demand.getDemandId();
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
    @Column(name = "TELEPHONE")
    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
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
    @Column(name = "SELECTED_STATUS")
    public String getSelectedStatus() {
        return selectedStatus;
    }

    public void setSelectedStatus(String selectedStatus) {
        this.selectedStatus = selectedStatus;
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

        SDemandQuote that = (SDemandQuote) o;

        if (demandQuoteId != that.demandQuoteId) return false;
        //if (demandId != that.demandId) return false;
        if (offerExplan != null ? !offerExplan.equals(that.offerExplan) : that.offerExplan != null) return false;
        if (contact != null ? !contact.equals(that.contact) : that.contact != null) return false;
        if (mobile != null ? !mobile.equals(that.mobile) : that.mobile != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (fax != null ? !fax.equals(that.fax) : that.fax != null) return false;
        //if (companyId != null ? !companyId.equals(that.companyId) : that.companyId != null) return false;
        if (operatorId != null ? !operatorId.equals(that.operatorId) : that.operatorId != null) return false;
        if (updateDt != null ? !updateDt.equals(that.updateDt) : that.updateDt != null) return false;
        if (createrId != null ? !createrId.equals(that.createrId) : that.createrId != null) return false;
        if (releaseDt != null ? !releaseDt.equals(that.releaseDt) : that.releaseDt != null) return false;
        if (deadlineDt != null ? !deadlineDt.equals(that.deadlineDt) : that.deadlineDt != null) return false;
        if (createDt != null ? !createDt.equals(that.createDt) : that.createDt != null) return false;
        if (deFlag != null ? !deFlag.equals(that.deFlag) : that.deFlag != null) return false;
        if (postCode != null ? !postCode.equals(that.postCode) : that.postCode != null) return false;
        if (addrProvince != null ? !addrProvince.equals(that.addrProvince) : that.addrProvince != null) return false;
        if (addrCity != null ? !addrCity.equals(that.addrCity) : that.addrCity != null) return false;
        if (addrCounty != null ? !addrCounty.equals(that.addrCounty) : that.addrCounty != null) return false;
        if (addrStreet != null ? !addrStreet.equals(that.addrStreet) : that.addrStreet != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = demandQuoteId;
        //result = 31 * result + demandId;
        result = 31 * result + (offerExplan != null ? offerExplan.hashCode() : 0);
        result = 31 * result + (contact != null ? contact.hashCode() : 0);
        result = 31 * result + (mobile != null ? mobile.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (fax != null ? fax.hashCode() : 0);
        //result = 31 * result + (companyId != null ? companyId.hashCode() : 0);
        result = 31 * result + (operatorId != null ? operatorId.hashCode() : 0);
        result = 31 * result + (updateDt != null ? updateDt.hashCode() : 0);
        result = 31 * result + (createrId != null ? createrId.hashCode() : 0);
        result = 31 * result + (releaseDt != null ? releaseDt.hashCode() : 0);
        result = 31 * result + (deadlineDt != null ? deadlineDt.hashCode() : 0);
        result = 31 * result + (createDt != null ? createDt.hashCode() : 0);
        result = 31 * result + (deFlag != null ? deFlag.hashCode() : 0);
        result = 31 * result + (postCode != null ? postCode.hashCode() : 0);
        result = 31 * result + (addrProvince != null ? addrProvince.hashCode() : 0);
        result = 31 * result + (addrCity != null ? addrCity.hashCode() : 0);
        result = 31 * result + (addrCounty != null ? addrCounty.hashCode() : 0);
        result = 31 * result + (addrStreet != null ? addrStreet.hashCode() : 0);
        return result;
    }

    private List<SDemandQuoteObj> demandQuoteObjList;

    private SCompany company;

    private SDemand demand;

    private Map<Integer, Double> quotePriceMap;

    private Map<Integer, Double> quoteMountedMap;

    @ElementCollection
    @CollectionTable(name = "s_demand_quote_obj",joinColumns={ @JoinColumn(nullable=false, name="demand_quote_id")})
    @MapKeyColumn(name="DEMAND_OBJID")//指定map的key生成的列
    @Column(name ="DISTRIBUTION_AMOUNT")
    public Map<Integer, Double> getQuoteMountedMap() {
        return quoteMountedMap;
    }

    public void setQuoteMountedMap(Map<Integer, Double> quoteMountedMap) {
        this.quoteMountedMap = quoteMountedMap;
    }

    @ElementCollection
    @CollectionTable(name = "s_demand_quote_obj",joinColumns={ @JoinColumn(nullable=false, name="demand_quote_id")})
    @MapKeyColumn(name="DEMAND_OBJID")//指定map的key生成的列
    @Column(name ="OFFER")
    public Map<Integer, Double> getQuotePriceMap() {
        return quotePriceMap;
    }

    public void setQuotePriceMap(Map<Integer, Double> quotePriceMap) {
        this.quotePriceMap = quotePriceMap;
    }

    @ManyToOne(targetEntity = SDemand.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "DEMAND_ID")
    public SDemand getDemand() {
        return demand;
    }

    public void setDemand(SDemand demand) {
        this.demand = demand;
    }

    @ManyToOne(targetEntity = SCompany.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "COMPANY_ID")
    public SCompany getCompany() { return company; }

    public void setCompany(SCompany company) { this.company = company; }

    @OneToMany(targetEntity = SDemandQuoteObj.class, cascade = CascadeType.ALL)
    @JoinColumns(value = {@JoinColumn(name = "DEMAND_QUOTE_ID", referencedColumnName = "DEMAND_QUOTE_ID")})
    public List<SDemandQuoteObj> getDemandQuoteObjList() {
        return demandQuoteObjList;
    }

    public void setDemandQuoteObjList(List<SDemandQuoteObj> demandQuoteObjList) {
        this.demandQuoteObjList = demandQuoteObjList;
    }

    @Transient
    public Map<String, Double> getQuotePriceStrkeyMap(){
        List<SDemandQuoteObj> sDemandQuoteObjList = this.demandQuoteObjList;
        Map<String, Double> strKeyMap = new HashMap<String, Double>();
        for(SDemandQuoteObj o : sDemandQuoteObjList){
            strKeyMap.put("id"+o.getDemandObjid(),o.getOffer());
        }
        return strKeyMap;
    }
    @Transient
    public Map<String, Double> getQuoteMountedStrkeyMap(){
        List<SDemandQuoteObj> sDemandQuoteObjList = this.demandQuoteObjList;
        Map<String, Double> strKeyMap = new HashMap<String, Double>();
        for(SDemandQuoteObj o : sDemandQuoteObjList){
            strKeyMap.put("id"+o.getDemandObjid(),o.getDistributionAmount());
        }
        return strKeyMap;
    }
}
